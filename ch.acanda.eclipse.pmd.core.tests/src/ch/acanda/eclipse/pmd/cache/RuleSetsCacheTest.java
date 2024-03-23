package ch.acanda.eclipse.pmd.cache;

import static org.junit.jupiter.api.Assertions.assertSame;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;

import com.google.common.cache.CacheLoader;

import ch.acanda.eclipse.pmd.domain.Location;
import ch.acanda.eclipse.pmd.domain.LocationContext;
import ch.acanda.eclipse.pmd.domain.ProjectModel;
import ch.acanda.eclipse.pmd.domain.RuleSetModel;
import ch.acanda.eclipse.pmd.domain.WorkspaceModel;
import net.sourceforge.pmd.lang.apex.rule.design.ExcessiveClassLengthRule;
import net.sourceforge.pmd.lang.apex.rule.design.ExcessivePublicCountRule;
import net.sourceforge.pmd.lang.java.rule.design.ExcessiveImportsRule;
import net.sourceforge.pmd.lang.java.rule.design.ExcessiveParameterListRule;
import net.sourceforge.pmd.lang.rule.Rule;
import net.sourceforge.pmd.lang.rule.RuleSet;

/**
 * Unit tests for {@link RuleSetsCache}.
 */
@SuppressWarnings("PMD.SignatureDeclareThrowsException")
public final class RuleSetsCacheTest {

    private static final String PROJECT_NAME_1 = "Foo";
    private static final String PROJECT_NAME_2 = "Bar";

    private static final List<RuleSet> RULE_SETS_FOO_1 = createRuleSets(new ExcessiveClassLengthRule());
    private static final List<RuleSet> RULE_SETS_FOO_2 = createRuleSets(new ExcessiveParameterListRule());
    private static final List<RuleSet> RULE_SETS_BAR_1 = createRuleSets(new ExcessiveImportsRule());
    private static final List<RuleSet> RULE_SETS_BAR_2 = createRuleSets(new ExcessivePublicCountRule());

    /**
     * Verifies that the first cache access loads the rule sets from the cache loader.
     */
    @Test
    public void firstGetLoadsFromCache() throws Exception {
        final RuleSetsCache cache = new RuleSetsCache(getCacheLoaderMock(), getWorkspaceModel());

        final List<RuleSet> actualRuleSets = cache.getRuleSets(PROJECT_NAME_1);

        assertSame(RULE_SETS_FOO_1, actualRuleSets, "First cache access should return rule sets from loader");
    }

    /**
     * Verifies that the second cache access returns the cached rule sets.
     */
    @Test
    public void secondGetDoesNotLoad() throws Exception {
        final RuleSetsCache cache = new RuleSetsCache(getCacheLoaderMock(), getWorkspaceModel());

        cache.getRuleSets(PROJECT_NAME_1);
        final List<RuleSet> actualRuleSets = cache.getRuleSets(PROJECT_NAME_1);

        assertSame(RULE_SETS_FOO_1, actualRuleSets, "Second cache access should return cached rule sets");
    }

    /**
     * Verifies that the second cache access loads the rule sets if the project model's rule sets have been changed
     * after the first access.
     */
    @Test
    public void secondGetLoadsWhenProjectRuleSetsWereChanged() throws Exception {
        final WorkspaceModel workspaceModel = getWorkspaceModel();
        final RuleSetsCache cache = new RuleSetsCache(getCacheLoaderMock(), workspaceModel);
        cache.getRuleSets(PROJECT_NAME_1);
        final RuleSetModel ruleSetModel = new RuleSetModel("abc", new Location("path", LocationContext.WORKSPACE));
        workspaceModel.getOrCreateProject(PROJECT_NAME_1).setRuleSets(Arrays.asList(ruleSetModel));

        final List<RuleSet> actualRuleSets = cache.getRuleSets(PROJECT_NAME_1);

        assertSame(RULE_SETS_FOO_2, actualRuleSets, "Second cache access should reload rule sets");
    }

    /**
     * Verifies that the second cache access loads the rule sets if the project model's rule sets have been changed
     * after the first access. In this case, the project model was added after the rule sets cache was created.
     */
    @Test
    public void secondGetLoadsWhenLaterAddedProjectRuleSetsWereChanged() throws Exception {
        final WorkspaceModel workspaceModel = getWorkspaceModel();
        final RuleSetsCache cache = new RuleSetsCache(getCacheLoaderMock(), workspaceModel);
        workspaceModel.add(new ProjectModel(PROJECT_NAME_2));
        cache.getRuleSets(PROJECT_NAME_2);
        final RuleSetModel ruleSetModel = new RuleSetModel("abc", new Location("path", LocationContext.WORKSPACE));
        workspaceModel.getOrCreateProject(PROJECT_NAME_2).setRuleSets(Arrays.asList(ruleSetModel));

        final List<RuleSet> actualRuleSets = cache.getRuleSets(PROJECT_NAME_2);

        assertSame(RULE_SETS_BAR_2, actualRuleSets, "Second cache access should reload rule sets");
    }

    /**
     * Verifies that the second cache access loads the rule sets if the project model has been removed and added after
     * the first access.
     */
    @Test
    public void secondGetLoadsWhenProjectWasRemovedAndAddedAfterFirstGet() throws Exception {
        final WorkspaceModel workspaceModel = getWorkspaceModel();
        final RuleSetsCache cache = new RuleSetsCache(getCacheLoaderMock(), workspaceModel);
        cache.getRuleSets(PROJECT_NAME_1);
        workspaceModel.remove(PROJECT_NAME_1);
        workspaceModel.add(new ProjectModel(PROJECT_NAME_1));

        final List<RuleSet> actualRuleSets = cache.getRuleSets(PROJECT_NAME_1);

        assertSame(RULE_SETS_FOO_2, actualRuleSets, "Second cache access should reload rule sets");
    }

    @SuppressWarnings("java:S112")
    private CacheLoader<String, List<RuleSet>> getCacheLoaderMock() throws Exception {
        @SuppressWarnings("unchecked")
        final CacheLoader<String, List<RuleSet>> loader = mock(CacheLoader.class);
        doReturn(RULE_SETS_FOO_1, RULE_SETS_FOO_2).when(loader).load(PROJECT_NAME_1);
        doReturn(RULE_SETS_BAR_1, RULE_SETS_BAR_2).when(loader).load(PROJECT_NAME_2);
        return loader;
    }

    private WorkspaceModel getWorkspaceModel() {
        final WorkspaceModel workspaceModel = new WorkspaceModel();
        workspaceModel.add(new ProjectModel(PROJECT_NAME_1));
        return workspaceModel;
    }

    private static List<RuleSet> createRuleSets(final Rule rule) {
        return List.of(RuleSet.forSingleRule(rule));
    }
}
