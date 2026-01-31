package ch.acanda.eclipse.pmd.builder;

import java.util.List;
import java.util.Map;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IResourceDelta;
import org.eclipse.core.resources.IResourceDeltaVisitor;
import org.eclipse.core.resources.IResourceVisitor;
import org.eclipse.core.resources.IncrementalProjectBuilder;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.OperationCanceledException;
import org.eclipse.core.runtime.SubMonitor;
import org.eclipse.core.runtime.jobs.ISchedulingRule;

import ch.acanda.eclipse.pmd.PMDPlugin;
import ch.acanda.eclipse.pmd.cache.RuleSetsCache;
import ch.acanda.eclipse.pmd.cache.RuleSetsCacheLoader;
import ch.acanda.eclipse.pmd.marker.MarkerUtil;
import net.sourceforge.pmd.lang.rule.RuleSet;

/**
 * Builder for PMD enabled projects.
 */
public class PMDBuilder extends IncrementalProjectBuilder {

    public static final String ID = "ch.acanda.eclipse.pmd.builder.PMDBuilder";

    private static final RuleSetsCache CACHE = new RuleSetsCache(new RuleSetsCacheLoader(), PMDPlugin.getDefault().getWorkspaceModel());

    /**
     * Allows to build projects concurrently.
     */
    @Override
    public ISchedulingRule getRule(final int kind, final Map<String, String> args) {
        return getProject();
    }

    @Override
    protected IProject[] build(final int kind, final Map<String, String> args, final IProgressMonitor monitor)
            throws CoreException {
        final IProgressMonitor subMonitor = SubMonitor.convert(monitor);
        if (kind == FULL_BUILD || kind == CLEAN_BUILD) {
            fullBuild(subMonitor);
        } else {
            final IResourceDelta delta = getDelta(getProject());
            if (delta == null) {
                fullBuild(subMonitor);
            } else {
                incrementalBuild(delta, subMonitor);
            }
        }
        return new IProject[] { getProject() };
    }

    protected void fullBuild(final IProgressMonitor monitor) {
        try {
            getProject().accept(new ResourceVisitor(monitor));
        } catch (final CoreException e) {
            PMDPlugin.getLogger().error("Could not run a full PMD build", e);
        }
    }

    protected void incrementalBuild(final IResourceDelta delta, final IProgressMonitor monitor) throws CoreException {
        delta.accept(new DeltaVisitor(monitor));
    }

    @Override
    protected void clean(final IProgressMonitor monitor) throws CoreException {
        final SubMonitor submonitor = SubMonitor.convert(monitor, "Deleting PMD markers", 1);
        MarkerUtil.removeAllMarkers(getProject());
        submonitor.setWorkRemaining(0);
    }

    void analyze(final IResource resource, final boolean includeMembers, final IProgressMonitor monitor) throws CoreException {
        if (resource instanceof final IFile file) {
            monitor.setTaskName("PMD analyzing file: " + file.getProjectRelativePath().toOSString());
            final List<RuleSet> ruleSets = CACHE.getRuleSets(resource.getProject().getName());
            new Analyzer().analyze(file, ruleSets, new ViolationProcessor());

        } else if (resource instanceof final IFolder folder && includeMembers) {
            for (final IResource member : folder.members()) {
                analyze(member, includeMembers, monitor);
            }
        }
    }

    class DeltaVisitor implements IResourceDeltaVisitor {

        private final IProgressMonitor monitor;

        DeltaVisitor(final IProgressMonitor monitor) {
            this.monitor = monitor;
        }

        @Override
        public boolean visit(final IResourceDelta delta) throws CoreException {
            final IResource resource = delta.getResource();
            final int kind = delta.getKind();
            if (kind == IResourceDelta.ADDED || kind == IResourceDelta.CHANGED) {
                analyze(resource, (delta.getFlags() & IResourceDelta.DERIVED_CHANGED) != 0, monitor);
            }
            if (monitor.isCanceled()) {
                throw new OperationCanceledException();
            }
            return true;
        }

    }

    class ResourceVisitor implements IResourceVisitor {

        private final IProgressMonitor monitor;

        ResourceVisitor(final IProgressMonitor monitor) {
            this.monitor = monitor;
        }

        @Override
        public boolean visit(final IResource resource) throws CoreException {
            analyze(resource, false, monitor);
            if (monitor.isCanceled()) {
                throw new OperationCanceledException();
            }
            return true;
        }

    }

}
