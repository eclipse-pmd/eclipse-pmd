package ch.acanda.eclipse.pmd.java.resolution;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.eclipse.core.filebuffers.ITextFileBuffer;
import org.eclipse.core.filebuffers.ITextFileBufferManager;
import org.eclipse.core.filebuffers.LocationKind;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.Path;
import org.eclipse.jface.text.Document;
import org.eclipse.jface.text.IDocument;
import org.eclipse.ui.IEditorReference;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.part.FileEditorInput;
import org.eclipse.ui.texteditor.ResourceMarkerAnnotationModel;
import org.junit.Test;

public class FileBufferTest {

    @Test
    public void savesFileWhenNotOpen() throws CoreException {
        final IDocument document = new Document("public class World {}");
        final ITextFileBuffer buffer = mock(ITextFileBuffer.class);
        final IFile file = mock(IFile.class);
        final ResourceMarkerAnnotationModel annotationModel = new ResourceMarkerAnnotationModel(file);

        try (FileBuffer fileBuffer = createFileBuffer(document, buffer, file, annotationModel, false)) {
            assertEquals("File buffer document", document, fileBuffer.getDocument());
            assertEquals("File buffer annotation model", annotationModel, fileBuffer.getAnnotationModel());
        }
        verify(buffer).commit(null, false);
    }

    @Test
    public void doesNotSaveFileWhenOpen() throws CoreException {
        final IDocument document = new Document("public class World {}");
        final ITextFileBuffer buffer = mock(ITextFileBuffer.class);
        final IFile file = mock(IFile.class);
        final ResourceMarkerAnnotationModel annotationModel = new ResourceMarkerAnnotationModel(file);

        try (FileBuffer fileBuffer = createFileBuffer(document, buffer, file, annotationModel, true)) {
            assertEquals("File buffer document", document, fileBuffer.getDocument());
            assertEquals("File buffer annotation model", annotationModel, fileBuffer.getAnnotationModel());
        }
        verify(buffer, never()).commit(null, false);
    }

    private FileBuffer createFileBuffer(final IDocument document, final ITextFileBuffer buffer, final IFile file,
            final ResourceMarkerAnnotationModel annotationModel, final boolean isOpen) throws CoreException {
        final Path path = Path.forPosix("/hello/World.java");
        when(file.getFullPath()).thenReturn(path);
        when(buffer.getDocument()).thenReturn(document);
        when(buffer.getAnnotationModel()).thenReturn(annotationModel);
        final ITextFileBufferManager manager = mock(ITextFileBufferManager.class);
        when(manager.getTextFileBuffer(path, LocationKind.IFILE)).thenReturn(buffer);
        final IWorkbench workbench = mock(IWorkbench.class);

        final IWorkbenchWindow[] windows = isOpen ? createWindow(file) : new IWorkbenchWindow[0];
        when(workbench.getWorkbenchWindows()).thenReturn(windows);
        return new FileBuffer(file, manager, workbench);
    }

    private IWorkbenchWindow[] createWindow(final IFile file) throws PartInitException {
        final IWorkbenchWindow window = mock(IWorkbenchWindow.class);
        final IWorkbenchPage page = mock(IWorkbenchPage.class);
        when(window.getPages()).thenReturn(new IWorkbenchPage[] { page });
        final IEditorReference editorRef = mock(IEditorReference.class);
        when(page.getEditorReferences()).thenReturn(new IEditorReference[] { editorRef });
        when(editorRef.getEditorInput()).thenReturn(new FileEditorInput(file));
        return new IWorkbenchWindow[] { window };
    }

}
