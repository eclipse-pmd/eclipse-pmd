package ch.acanda.eclipse.pmd.logging;

import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Plugin;
import org.eclipse.core.runtime.Status;

/**
 * This logger logs to the Eclipse platform and can only be used with an activated plugin.
 */
public class PluginLogger extends Logger {

    private final Plugin plugin;
    private final String pluginId;

    public PluginLogger(final Plugin plugin, final String pluginId) {
        this.plugin = plugin;
        this.pluginId = pluginId;
    }

    @Override
    protected IStatus log(final int severity, final String message, final Throwable throwable) {
        return log(new Status(severity, pluginId, message, throwable));
    }

    @Override
    public IStatus log(final IStatus status) {
        plugin.getLog().log(status);
        return status;
    }

}
