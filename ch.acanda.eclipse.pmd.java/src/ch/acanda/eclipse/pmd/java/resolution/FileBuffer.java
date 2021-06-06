package ch.acanda.eclipse.pmd.java.resolution;

import java.lang.reflect.Field;

import org.eclipse.core.filebuffers.FileBuffers;
import org.eclipse.core.filebuffers.ITextFileBuffer;
import org.eclipse.core.filebuffers.ITextFileBufferManager;
import org.eclipse.core.filebuffers.LocationKind;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.jdt.core.ICompilationUnit;
import org.eclipse.jdt.core.IJavaProject;
import org.eclipse.jdt.core.JavaCore;
import org.eclipse.jdt.core.dom.AST;
import org.eclipse.jdt.core.dom.ASTParser;
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jface.text.IDocument;
import org.eclipse.jface.text.source.IAnnotationModel;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IEditorReference;
import org.eclipse.ui.IFileEditorInput;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;

class FileBuffer implements AutoCloseable {

    private final IFile file;
    private final ITextFileBufferManager bufferManager;
    private final ITextFileBuffer textFileBuffer;
    private final IWorkbench workbench;

    public FileBuffer(final IFile file) throws CoreException {
        this(file, FileBuffers.getTextFileBufferManager(), PlatformUI.getWorkbench());
    }

    public FileBuffer(final IFile file, final ITextFileBufferManager bufferManager, final IWorkbench workbench) throws CoreException {
        this.file = file;
        this.bufferManager = bufferManager;
        this.workbench = workbench;
        bufferManager.connect(file.getFullPath(), LocationKind.IFILE, null);
        textFileBuffer = bufferManager.getTextFileBuffer(file.getFullPath(), LocationKind.IFILE);
    }

    public IDocument getDocument() {
        return textFileBuffer.getDocument();
    }

    public IAnnotationModel getAnnotationModel() {
        return textFileBuffer.getAnnotationModel();
    }

    public CompilationUnit getAst(final boolean needsTypeResolution, final ICompilationUnit source, final IProgressMonitor monitor) {
        final ASTParser astParser = ASTParser.newParser(getApiLevel(file));
        astParser.setKind(ASTParser.K_COMPILATION_UNIT);
        astParser.setResolveBindings(needsTypeResolution);
        astParser.setSource(source);
        return (CompilationUnit) astParser.createAST(monitor);
    }

    private int getApiLevel(final IFile file) {
        // try to infer API level from compiler source settings in project
        final IProject project = file.getProject();
        if (project instanceof IJavaProject) {
            final IJavaProject javaProject = (IJavaProject) project;
            if (javaProject.getOption(JavaCore.COMPILER_SOURCE, false) != null) {
                return AST.newAST(javaProject.getOptions(false)).apiLevel();
            }
        }

        // try to read the private field AST.JLS_Latest which holds the latest API level supported by the current
        // Eclipse release
        try {
            final Field jlsLatest = AST.class.getDeclaredField("JLS_Latest");
            jlsLatest.setAccessible(true);
            return jlsLatest.getInt(null);
        } catch (final NoSuchFieldException | SecurityException | IllegalArgumentException | IllegalAccessException e) {
            // failed to read the field
        }

        // use the latest API level supported by the oldest supported Eclipse release
        return AST.JLS13;
    }

    @Override
    public void close() throws CoreException {
        // commit changes to underlying file if it is not opened in an editor
        if (!isOpen(file)) {
            textFileBuffer.commit(null, false);
        }
        bufferManager.disconnect(file.getFullPath(), LocationKind.IFILE, null);
    }

    @SuppressWarnings("PMD.CognitiveComplexity")
    private boolean isOpen(final IFile file) {
        for (final IWorkbenchWindow window : workbench.getWorkbenchWindows()) {
            for (final IWorkbenchPage page : window.getPages()) {
                for (final IEditorReference reference : page.getEditorReferences()) {
                    try {
                        final IEditorInput input = reference.getEditorInput();
                        if (input instanceof IFileEditorInput && file.equals(((IFileEditorInput) input).getFile())) {
                            return true;
                        }
                    } catch (final PartInitException e) {
                        // cannot get editor input -> ignore
                    }
                }
            }
        }
        return false;
    }

}
