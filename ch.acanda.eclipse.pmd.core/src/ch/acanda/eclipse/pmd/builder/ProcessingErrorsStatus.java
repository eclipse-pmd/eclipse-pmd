package ch.acanda.eclipse.pmd.builder;

import java.util.List;

import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.MultiStatus;
import org.eclipse.core.runtime.Status;

import ch.acanda.eclipse.pmd.PMDPlugin;
import net.sourceforge.pmd.Report.ProcessingError;

public class ProcessingErrorsStatus extends MultiStatus {

    public ProcessingErrorsStatus(final List<ProcessingError> errors) {
        super(PMDPlugin.ID, IStatus.WARNING, "Failed to run PMD.");
        errors.forEach(error -> {
            final String msg = error.getFile() + ": " + error.getMsg();
            final IStatus childStatus = new Status(IStatus.WARNING, PMDPlugin.ID, msg, error.getError());
            add(childStatus);
        });
    }

}
