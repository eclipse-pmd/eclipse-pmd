package ch.acanda.eclipse.pmd.logging;

import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Plugin;

public abstract class Logger {

    public static Logger forActivePlugin(final Plugin plugin, final String pluginId) {
        return new PluginLogger(plugin, pluginId);
    }

    public static Logger forInactivePlugin(final String pluginId) {
        return new JulLogger(pluginId);
    }

    /**
     * Logs a status to the platform, i.e. it will be visible in the Error Log view and distributed to the log
     * listeners.
     *
     * @return An error status containing the error message.
     */
    public abstract IStatus log(IStatus status);

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

    protected abstract IStatus log(int severity, String message, Throwable throwable);


}
