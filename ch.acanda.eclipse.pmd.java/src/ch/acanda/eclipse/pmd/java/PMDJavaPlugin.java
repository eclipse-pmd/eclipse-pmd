package ch.acanda.eclipse.pmd.java;

import org.eclipse.core.runtime.Plugin;
import org.osgi.framework.BundleContext;

import ch.acanda.eclipse.pmd.logging.Logger;

public final class PMDJavaPlugin extends Plugin {

    public static final String ID = "ch.acanda.eclipse.pmd.java";

    private static Logger logger = Logger.forInactivePlugin(ID);

    private static PMDJavaPlugin plugin;

    @Override
    @SuppressWarnings({ "PMD.SignatureDeclareThrowsException", "java:S2696" })
    public void start(final BundleContext context) throws Exception {
        super.start(context);
        plugin = this;
        logger = Logger.forActivePlugin(plugin, ID);
    }

    @Override
    @SuppressWarnings({ "PMD.SignatureDeclareThrowsException", "PMD.NullAssignment", "java:S2696" })
    public void stop(final BundleContext context) throws Exception {
        logger = Logger.forInactivePlugin(ID);
        plugin = null;
        super.stop(context);
    }

    public static PMDJavaPlugin getDefault() {
        return plugin;
    }

    public static Logger getLogger() {
        return logger;
    }

}
