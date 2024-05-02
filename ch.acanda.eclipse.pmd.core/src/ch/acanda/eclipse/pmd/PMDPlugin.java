package ch.acanda.eclipse.pmd;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResourceChangeEvent;
import org.eclipse.core.resources.IResourceChangeListener;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.resource.ResourceLocator;
import org.eclipse.ui.plugin.AbstractUIPlugin;
import org.osgi.framework.BundleContext;

import ch.acanda.eclipse.pmd.domain.ProjectModel;
import ch.acanda.eclipse.pmd.domain.WorkspaceModel;
import ch.acanda.eclipse.pmd.logging.Logger;
import ch.acanda.eclipse.pmd.repository.ProjectModelRepository;
import ch.acanda.eclipse.pmd.ui.util.PMDPluginImages;
import net.sourceforge.pmd.lang.LanguageRegistry;

public final class PMDPlugin extends AbstractUIPlugin {

    public static final String ID = "ch.acanda.eclipse.pmd.core";

    private static Logger logger = Logger.forInactivePlugin(ID);

    private static PMDPlugin plugin;

    private WorkspaceModel workspaceModel;

    @Override
    @SuppressWarnings({ "PMD.SignatureDeclareThrowsException", "java:S2696" })
    public void start(final BundleContext context) throws Exception {
        super.start(context);
        plugin = this;
        logger = Logger.forActivePlugin(plugin, ID);
        initWorkspaceModel();
        initPMD();
    }

    @Override
    @SuppressWarnings({ "PMD.SignatureDeclareThrowsException", "PMD.NullAssignment", "java:S2696" })
    public void stop(final BundleContext context) throws Exception {
        PMDPluginImages.dispose();
        logger = Logger.forInactivePlugin(ID);
        plugin = null;
        super.stop(context);
    }

    public static PMDPlugin getDefault() {
        return plugin;
    }

    public static Logger getLogger() {
        return logger;
    }

    private void initPMD() {
        // The PMD languages are made available as services using the java.util.ServiceLoader facility. The following
        // line ensures the services are loaded using a class loader with access to the different service
        // implementations (i.e. languages).
        LanguageRegistry.PMD.getLanguages();
    }

    private void initWorkspaceModel() {
        workspaceModel = new WorkspaceModel();
        final IProject[] projects = ResourcesPlugin.getWorkspace().getRoot().getProjects();
        final ProjectModelRepository projectModelRepository = new ProjectModelRepository();
        for (final IProject project : projects) {
            workspaceModel.add(projectModelRepository.load(project.getName()).orElseGet(() -> new ProjectModel(project.getName())));
        }
        final IResourceChangeListener workspaceChangeListener = new WorkspaceChangeListener(workspaceModel, projectModelRepository);
        ResourcesPlugin.getWorkspace().addResourceChangeListener(workspaceChangeListener, IResourceChangeEvent.POST_CHANGE);
    }

    public WorkspaceModel getWorkspaceModel() {
        return workspaceModel;
    }

    /**
     * Creates and returns a new image descriptor for an image file located within the PMD plug-in.
     *
     * @param path The relative path of the image file, relative to the root of the plug-in; the path must be legal.
     * @return The image descriptor, or <code>null</code> if no image could be found.
     */
    public static ImageDescriptor getImageDescriptor(final String path) {
        return ResourceLocator.imageDescriptorFromBundle(ID, path).orElse(null);
    }

}
