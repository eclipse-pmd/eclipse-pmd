package ch.acanda.eclipse.pmd.ui.dialog;

import org.eclipse.core.resources.IContainer;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.jface.viewers.ITreeContentProvider;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.viewers.ViewerComparator;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.dialogs.ElementTreeSelectionDialog;
import org.eclipse.ui.dialogs.ISelectionStatusValidator;
import org.eclipse.ui.model.WorkbenchLabelProvider;

import ch.acanda.eclipse.pmd.PMDPlugin;
import ch.acanda.eclipse.pmd.swtbot.SWTBotID;

/**
 * Selection dialog to select a file on the file system. Use {@link #setInput(Object)} to set input to an
 * {@link IContainer}.
 */
public class FileSelectionDialog extends ElementTreeSelectionDialog {

    public FileSelectionDialog(final Shell parent) {
        super(parent, WorkbenchLabelProvider.getDecoratingWorkbenchLabelProvider(), new FileContentProvider());
    }

    @Override
    protected void configureShell(final Shell shell) {
        super.configureShell(shell);
        setValidator(new FileSelectionValidator());
        setComparator(new FileViewerComparator());
        setShellStyle(getShellStyle() | SWT.SHEET);
        SWTBotID.set(shell, SWTBotID.FILE_SELECTION_DIALOG);
    }

    private static final class FileContentProvider implements ITreeContentProvider {
        private static final Object[] NO_CHILDREN = new Object[0];

        @Override
        public Object[] getChildren(final Object parentElement) {
            if (parentElement instanceof final IContainer container) {
                try {
                    return container.members();
                } catch (final CoreException e) {
                    PMDPlugin.getLogger().warn("Couldn't fetch members of " + parentElement, e);
                }
            }
            return NO_CHILDREN;
        }

        @Override
        public Object getParent(final Object element) {
            if (element instanceof final IResource resource) {
                return resource.getParent();
            }
            return null;
        }

        @Override
        public boolean hasChildren(final Object element) {
            return getChildren(element).length > 0;
        }

        @Override
        public Object[] getElements(final Object element) {
            return getChildren(element);
        }

        @Override
        public void dispose() {
            // nothing to dispose
        }

        @Override
        public void inputChanged(final Viewer viewer, final Object oldInput, final Object newInput) {
            // nothing to do; the content is created on demand
        }
    }

    /**
     * Validates the selection. A selection is valid if it contains a single file.
     */
    private static final class FileSelectionValidator implements ISelectionStatusValidator {
        @Override
        public IStatus validate(final Object[] selection) {
            if (selection.length == 1 && !(selection[0] instanceof IContainer)) {
                return new Status(IStatus.OK, PMDPlugin.ID, OK, "", null);
            }
            return new Status(IStatus.ERROR, PMDPlugin.ID, IStatus.ERROR, "", null);
        }
    }

    /**
     * Sorts the resources alphabetically, folders before files.
     */
    private static final class FileViewerComparator extends ViewerComparator {

        @Override
        public int category(final Object element) {
            return element instanceof IContainer ? 0 : 1;
        }

    }

}
