package ch.acanda.eclipse.pmd.java;

import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Plugin;
import org.eclipse.core.runtime.Status;
import org.osgi.framework.BundleContext;

public final class PMDJavaPlugin extends Plugin {

    public static final String ID = "ch.acanda.eclipse.pmd.java";

    private static PMDJavaPlugin plugin;

    @Override
    @SuppressWarnings({ "PMD.SignatureDeclareThrowsException", "java:S2696" })
    public void start(final BundleContext context) throws Exception {
        super.start(context);
        plugin = this;
    }

    @Override
    @SuppressWarnings({ "PMD.SignatureDeclareThrowsException", "PMD.NullAssignment", "java:S2696" })
    public void stop(final BundleContext context) throws Exception {
        plugin = null;
        super.stop(context);
    }

    public static PMDJavaPlugin getDefault() {
        return plugin;
    }

    /**
     * Logs an error message to the platform, i.e. it will be visible in the Error Log view and distributed to the log
     * listeners.
     *
     * @return An error status containing the error message.
     */
    public IStatus error(final String message) {
        return log(IStatus.ERROR, message, null);
    }

    /**
     * Logs an error message and a {@code Throwable} to the platform, i.e. it will be visible in the Error Log view and
     * distributed to the log listeners.
     *
     * @return An error status containing the error message and throwable.
     */
    public IStatus error(final String message, final Throwable throwable) {
        return log(IStatus.ERROR, message, throwable);
    }

    /**
     * Logs a warning message and a {@code Throwable} to the platform, i.e. it will be visible in the Error Log view and
     * distributed to the log listeners.
     *
     * @return A warning status containing the warning message and throwable.
     */
    public IStatus warn(final String message, final Throwable throwable) {
        return log(IStatus.WARNING, message, throwable);
    }

    /**
     * Logs an info message to the platform, i.e. it will be visible in the Error Log view and distributed to the log
     * listeners.
     *
     * @return An info status containing the message.
     */
    public IStatus info(final String message) {
        return log(IStatus.INFO, message, null);
    }

    /**
     * Logs an info message and a {@code Throwable} to the platform, i.e. it will be visible in the Error Log view and
     * distributed to the log listeners.
     *
     * @return An info status containing the message and throwable.
     */
    public IStatus info(final String message, final Throwable throwable) {
        return log(IStatus.INFO, message, throwable);
    }

    private IStatus log(final int severity, final String message, final Throwable throwable) {
        final IStatus status = new Status(severity, ID, message, throwable);
        getLog().log(status);
        return status;
    }

}
