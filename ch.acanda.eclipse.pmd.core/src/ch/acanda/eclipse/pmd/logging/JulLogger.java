package ch.acanda.eclipse.pmd.logging;

import java.util.logging.Level;

import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;

/**
 * This logger does not require a running Eclipse platform and is used for tests that do not need an activated plugin.
 */
public class JulLogger extends Logger {

    private final java.util.logging.Logger log;

    JulLogger(final String pluginId) {
        log = java.util.logging.Logger.getLogger(pluginId);
    }

    @Override
    protected IStatus log(final int severity, final String message, final Throwable throwable) {
        return log(new Status(severity, log.getName(), message, throwable));
    }

    @Override
    public IStatus log(final IStatus status) {
        log.log(getLevel(status.getSeverity()), status.getMessage(), status.getException());
        return status;
    }

    private Level getLevel(final int severity) {
        if (severity == IStatus.ERROR) {
            return Level.SEVERE;
        } else if (severity == IStatus.WARNING) {
            return Level.WARNING;
        }
        return Level.INFO;
    }

}
