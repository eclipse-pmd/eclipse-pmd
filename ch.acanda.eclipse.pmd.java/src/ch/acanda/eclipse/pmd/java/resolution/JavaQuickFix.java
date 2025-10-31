package ch.acanda.eclipse.pmd.java.resolution;

import java.lang.reflect.ParameterizedType;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IMarker;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.SubMonitor;
import org.eclipse.jdt.core.ICompilationUnit;
import org.eclipse.jdt.core.IJavaElement;
import org.eclipse.jdt.core.JavaCore;
import org.eclipse.jdt.core.dom.ASTNode;
import org.eclipse.jdt.core.dom.ASTParser;
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.text.BadLocationException;
import org.eclipse.jface.text.IDocument;
import org.eclipse.jface.text.Position;
import org.eclipse.jface.text.source.Annotation;
import org.eclipse.jface.text.source.IAnnotationModel;
import org.eclipse.swt.graphics.Image;
import org.eclipse.text.edits.MalformedTreeException;
import org.eclipse.ui.texteditor.MarkerAnnotation;
import org.eclipse.ui.views.markers.WorkbenchMarkerResolution;

import ch.acanda.eclipse.pmd.java.PMDJavaPlugin;
import ch.acanda.eclipse.pmd.marker.PMDMarker;
import ch.acanda.eclipse.pmd.ui.util.PMDPluginImages;

/**
 * Base class for a Java quick fix.
 *
 * @param <T> The type of AST node that will be passed to {@link #apply(ASTNode)}.
 */
@SuppressWarnings({ "PMD.ExcessiveImports", "PMD.CouplingBetweenObjects" })
public abstract class JavaQuickFix<T extends ASTNode> extends WorkbenchMarkerResolution {

    private static final IMarker[] NO_OTHER_MARKERS = new IMarker[0];
    protected final PMDMarker marker;

    protected JavaQuickFix(final PMDMarker marker) {
        this.marker = marker;
    }

    @Override
    public final Image getImage() {
        return PMDPluginImages.get(getImageDescriptor());
    }

    /**
     * Returns the image descriptor for the image to be displayed in the list of resolutions.
     *
     * @return The image descriptor for the image to be shown. Must not return <code>null</code>.
     */
    protected abstract ImageDescriptor getImageDescriptor();

    /**
     * Returns other markers with the same rule id as the marker of this quick fix. This allows to fix multiple PMD
     * problems of the same type all at once.
     */
    @Override
    public IMarker[] findOtherMarkers(final IMarker[] markers) {
        final IMarker[] result;
        if (markers.length > 1) {
            return Stream.of(markers)
                    .filter(marker::isOtherWithSameRuleId)
                    .toArray(IMarker[]::new);
        } else {
            result = NO_OTHER_MARKERS;
        }
        return result;
    }

    @Override
    public void run(final IMarker[] markers, final IProgressMonitor monitor) {
        final Map<IFile, List<IMarker>> map = createMarkerMap(markers);
        monitor.beginTask(getLabel(), (map.keySet().size() * 2 + markers.length) * 100);
        try {
            for (final Map.Entry<IFile, List<IMarker>> entry : map.entrySet()) {
                fixMarkersInFile(entry.getKey(), entry.getValue(), monitor);
            }
        } finally {
            monitor.done();
        }
    }

    /**
     * @return A map grouping the markers by their file.
     */
    private Map<IFile, List<IMarker>> createMarkerMap(final IMarker... markers) {
        return Stream.of(markers)
                .filter(marker -> {
                    final IResource resource = marker.getResource();
                    return resource instanceof IFile && resource.isAccessible();
                })
                .collect(Collectors.groupingBy(marker -> (IFile) marker.getResource()));
    }

    @Override
    public void run(final IMarker marker) {
        final IResource resource = marker.getResource();
        if (resource instanceof final IFile file && resource.isAccessible()) {
            fixMarkersInFile(file, Arrays.asList(marker), null);
        }
    }

    /**
     * Fixes all provided markers in a file.
     *
     * @param markers The markers to fix. There is at least one marker in this collection and all markers can be fixed
     *     by this quick fix.
     */
    protected void fixMarkersInFile(final IFile file, final List<IMarker> markers, final IProgressMonitor monitor) {

        final Optional<ICompilationUnit> optionalCompilationUnit = getCompilationUnit(file);

        if (!optionalCompilationUnit.isPresent()) {
            return;
        }

        final SubMonitor subMonitor = SubMonitor.convert(monitor, file.getFullPath().toOSString(), 100);

        final ICompilationUnit compilationUnit = optionalCompilationUnit.get();

        try (FileBuffer buffer = new FileBuffer(file)) {
            final CompilationUnit ast = buffer.getAst(needsTypeResolution(), compilationUnit, subMonitor.split(5));
            fixMarkers(markers, subMonitor.split(95), compilationUnit, buffer, ast);

        } catch (CoreException | MalformedTreeException | BadLocationException e) {
            PMDJavaPlugin.getLogger().error("Error processing quickfix", e);

        } finally {
            subMonitor.setWorkRemaining(0);
        }
    }

    private void fixMarkers(
            final List<IMarker> markers, final IProgressMonitor monitor, final ICompilationUnit compilationUnit,
            final FileBuffer buffer, final CompilationUnit ast
    )
            throws CoreException, BadLocationException {
        startFixingMarkers(ast);

        final SubMonitor markerMonitor = SubMonitor.convert(monitor, markers.size());
        final Map<?, ?> options = compilationUnit.getJavaProject().getOptions(true);
        final IDocument document = buffer.getDocument();
        final IAnnotationModel annotationModel = buffer.getAnnotationModel();
        for (final IMarker marker : markers) {
            try {
                final MarkerAnnotation annotation = getMarkerAnnotation(annotationModel, marker);
                // if the annotation is null it means that is was deleted by a previous quick fix
                if (annotation != null) {
                    fixMarker(annotation, annotationModel, ast, document, options);
                }
            } finally {
                markerMonitor.worked(1);
            }
        }

        finishFixingMarkers(ast, document, options);
    }

    private void fixMarker(
            final MarkerAnnotation annotation,
            final IAnnotationModel annotationModel,
            final CompilationUnit ast,
            final IDocument document,
            final Map<?, ?> options
    ) throws CoreException {
        final Optional<T> node = getNodeFinder(annotationModel.getPosition(annotation)).findNode(ast);
        if (node.isPresent()) {
            final boolean isSuccessful = fixMarker(node.get(), document, options);
            if (isSuccessful) {
                annotation.getMarker().delete();
            }
        }
    }

    /**
     * Returns {@code true} if the quick fix needs resolved types. Type resolution comes at a considerable cost in both
     * time and space, however, and should not be requested frivolously. The additional space is not reclaimed until the
     * AST, all its nodes, and all its bindings become garbage. So it is very important to not retain any of these
     * objects longer than absolutely necessary.
     *
     * @see ASTParser#setResolveBindings(boolean)
     */
    protected boolean needsTypeResolution() {
        return false;
    }

    /**
     * Prepares the quick fix for fixing the markers. This method is guaranteed to be invoked before
     * {@link #fixMarker(ASTNode, IDocument, Map)} and {@link #finishFixingMarkers(CompilationUnit, IDocument, Map).
     */
    protected abstract void startFixingMarkers(CompilationUnit ast);

    /**
     * Fixes a single marker. The marker is already resolve to its corresponding node in the AST. This method is
     * guaranteed to be invoked before {@link #finishFixingMarkers(CompilationUnit, IDocument, Map).
     *
     * @param node The marker's corresponding node in the AST.
     * @param document The document representing the Java file.
     * @param options The project's Java options.
     * @return {@code true} iff the quick fix was applied successfully, i.e. the PMD problem was resolved. If
     * {@code false} is returned then the AST must not have been modified in any way.
     * @throws CoreException Thrown when the AST has already been modified but the fix could not have been successfully
     *     applied. Throwing this exception will abort all quick fixes for this file. Any already successfully applied
     *     quick fixes will not be committed.
     */
    protected abstract boolean fixMarker(T node, IDocument document, Map<?, ?> options) throws CoreException;

    /**
     * Finishes fixing the markers. After this method the document should have its final form before being committed.
     *
     * @param ast The document's AST.
     * @param document The document representing the Java file.
     * @param options The project's Java options.
     * @throws BadLocationException Thrown when the fixing cannot be finished properly. The already applied quick fixes
     *     will not be committed.
     */
    protected abstract void finishFixingMarkers(CompilationUnit ast, IDocument document, Map<?, ?> options)
            throws BadLocationException;

    /**
     * @param position The position of the marker.
     * @return The node finder that will be used to search for the node which will be passed to the quick fix.
     */
    protected abstract NodeFinder<CompilationUnit, T> getNodeFinder(Position position);

    /**
     * Returns the type of the AST node that will be used to find the node that will be used as an argument when
     * invoking {@link #apply(ASTNode)}. This method takes the type from the type parameter of this class.
     *
     * @return The type of the node that will be used when invoking {@link #apply(ASTNode)}.
     */
    @SuppressWarnings("unchecked")
    protected Class<T> getNodeType() {
        // This works only if 'this' is a direct subclass of ASTQuickFix.
        final ParameterizedType genericSuperclass = (ParameterizedType) getClass().getGenericSuperclass();
        return (Class<T>) genericSuperclass.getActualTypeArguments()[0];
    }

    private Optional<ICompilationUnit> getCompilationUnit(final IFile file) {
        final IJavaElement element = JavaCore.create(file);
        return element instanceof final ICompilationUnit cu ? Optional.of(cu) : Optional.<ICompilationUnit>empty();
    }

    private MarkerAnnotation getMarkerAnnotation(final IAnnotationModel annotationModel, final IMarker marker) {
        final Iterator<Annotation> annotations = annotationModel.getAnnotationIterator();
        while (annotations.hasNext()) {
            final Annotation annotation = annotations.next();
            if (annotation instanceof final MarkerAnnotation markerAnnotation) {
                final IMarker annotationMarker = markerAnnotation.getMarker();
                if (annotationMarker.equals(marker)) {
                    return markerAnnotation;
                }
            }
        }
        return null;
    }

}
