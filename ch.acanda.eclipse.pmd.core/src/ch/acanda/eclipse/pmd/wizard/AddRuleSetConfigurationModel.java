package ch.acanda.eclipse.pmd.wizard;

import static ch.acanda.eclipse.pmd.ui.model.ValidationUtil.errorIfBlank;

import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.Platform;

import ch.acanda.eclipse.pmd.PMDPlugin;
import ch.acanda.eclipse.pmd.builder.LocationResolver;
import ch.acanda.eclipse.pmd.domain.Location;
import ch.acanda.eclipse.pmd.domain.LocationContext;
import ch.acanda.eclipse.pmd.ui.model.ValidationProblem;
import ch.acanda.eclipse.pmd.ui.model.ValidationProblem.Severity;
import ch.acanda.eclipse.pmd.ui.model.ValidationResult;
import ch.acanda.eclipse.pmd.ui.model.ViewModel;
import net.sourceforge.pmd.lang.rule.Rule;
import net.sourceforge.pmd.lang.rule.RuleSet;
import net.sourceforge.pmd.lang.rule.RuleSetLoader;

/**
 * View model for the wizard page to add a new file system rule set configuration.
 */
class AddRuleSetConfigurationModel extends ViewModel {

    public static final String FILE_SYSTEM_TYPE_SELECTED = "fileSystemTypeSelected";
    public static final String WORKSPACE_TYPE_SELECTED = "workspaceTypeSelected";
    public static final String PROJECT_TYPE_SELECTED = "projectTypeSelected";
    public static final String REMOTE_TYPE_SELECTED = "remoteTypeSelected";
    public static final String BROWSE_ENABLED = "browseEnabled";
    public static final String LOCATION = "location";
    public static final String NAME = "name";
    public static final String RULES = "rules";

    private final IProject project;

    private String name;
    private String location;
    private boolean isFileSystemTypeSelected;
    private boolean isWorkspaceTypeSelected;
    private boolean isProjectTypeSelected;
    private boolean isRemoteTypeSelected;
    private boolean isBrowseEnabled;

    /**
     * This property is derived from {@link #location}. If {@link #location} is valid this list contains the rules of
     * the selected rule set, otherwise it is empty. It is never {@code null}.
     */
    private List<Rule> rules = List.of();

    public AddRuleSetConfigurationModel(final IProject project) {
        this.project = project;
    }

    @Override
    protected boolean updateDirty() {
        return !(isNullOrEmpty(name) && isNullOrEmpty(location));
    }

    private static boolean isNullOrEmpty(final String s) {
        return s == null || s.isEmpty();
    }

    protected void reset() {
        setName(null);
        setLocation(null);
        setWorkspaceTypeSelected(true);
        setProjectTypeSelected(false);
        setFileSystemTypeSelected(false);
    }

    public String getName() {
        return name;
    }

    public void setName(final String name) {
        setProperty(NAME, this.name, this.name = name);
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(final String location) {
        setProperty(LOCATION, this.location, this.location = location);
    }

    public List<Rule> getRules() {
        return rules;
    }

    private void setRules(final List<Rule> rules) {
        assert rules != null;
        setProperty(RULES, this.rules, this.rules = rules);
    }

    public boolean isFileSystemTypeSelected() {
        return isFileSystemTypeSelected;
    }

    public void setFileSystemTypeSelected(final boolean isFileSystemTypeSelected) {
        setProperty(FILE_SYSTEM_TYPE_SELECTED, this.isFileSystemTypeSelected, this.isFileSystemTypeSelected = isFileSystemTypeSelected);
        updateBrowseEnabled();
    }

    public boolean isWorkspaceTypeSelected() {
        return isWorkspaceTypeSelected;
    }

    public void setWorkspaceTypeSelected(final boolean isWorkspaceTypeSelected) {
        setProperty(WORKSPACE_TYPE_SELECTED, this.isWorkspaceTypeSelected, this.isWorkspaceTypeSelected = isWorkspaceTypeSelected);
        updateBrowseEnabled();
    }

    public boolean isProjectTypeSelected() {
        return isProjectTypeSelected;
    }

    public void setProjectTypeSelected(final boolean isProjectTypeSelected) {
        setProperty(PROJECT_TYPE_SELECTED, this.isProjectTypeSelected, this.isProjectTypeSelected = isProjectTypeSelected);
        updateBrowseEnabled();
    }

    public boolean isRemoteTypeSelected() {
        return isRemoteTypeSelected;
    }

    public void setRemoteTypeSelected(final boolean isRemoteTypeSelected) {
        setProperty(REMOTE_TYPE_SELECTED, this.isRemoteTypeSelected, this.isRemoteTypeSelected = isRemoteTypeSelected);
        updateBrowseEnabled();
    }

    private void updateBrowseEnabled() {
        setBrowseEnabled(!isRemoteTypeSelected);
    }

    public boolean isBrowseEnabled() {
        return isBrowseEnabled;
    }

    public void setBrowseEnabled(final boolean isBrowseEnabled) {
        setProperty(BROWSE_ENABLED, this.isBrowseEnabled, this.isBrowseEnabled = isBrowseEnabled);
    }

    @Override
    protected Set<String> createValidatedPropertiesSet() {
        return Set.of(LOCATION, NAME);
    }

    @Override
    protected void validate(final String propertyName, final ValidationResult validationResult) {
        validateName(validationResult);
        validateLocation(propertyName, validationResult);
    }

    /**
     * Validates the location of the rule set configuration and sets or resets the property {@link #rules} depending on
     * whether {@link #location} contains a valid rule set configuration location or not.
     */
    @SuppressWarnings("PMD.AvoidCatchingGenericException")
    private void validateLocation(final String propertyName, final ValidationResult result) {
        final List<Rule> rules = new ArrayList<>();
        String ruleSetName = null;
        if (!errorIfBlank(LOCATION, location, "Please enter the location of the rule set configuration", result)) {
            RuleSet ruleSet = null;
            try {
                final String referenceId;
                if (isRemoteTypeSelected) {
                    referenceId = validateRemoteLocation(result);
                } else {
                    referenceId = validateLocalLocation(result);
                }
                if (referenceId != null) {
                    ruleSet = new RuleSetLoader().loadFromResource(referenceId);
                    ruleSetName = ruleSet.getName();
                    rules.addAll(ruleSet.getRules());
                }
            } catch (final RuntimeException e) {
                // the rule set location is invalid - the validation problem will be added below
            }
            if (ruleSet == null || ruleSet.getRules().isEmpty()) {
                result.add(new ValidationProblem(LOCATION, Severity.ERROR,
                        "The rule set configuration at the given location is invalid"));
            }
        }
        if (LOCATION.equals(propertyName)) {
            setRules(rules);
            setName(ruleSetName == null ? "" : ruleSetName);
        }
    }

    private String validateRemoteLocation(final ValidationResult result) {
        String referenceId = null;
        try {
            final URI uri = new URI(location);
            try (InputStream stream = uri.toURL().openStream()) {
                final Path pluginDir = Platform.getStateLocation(PMDPlugin.getDefault().getBundle()).toFile().toPath();
                final Path tempFile = Files.createTempFile(pluginDir, "eclipse-pmd-remote-", ".xml");
                Files.copy(stream, tempFile, StandardCopyOption.REPLACE_EXISTING);
                tempFile.toFile().deleteOnExit();
                referenceId = tempFile.toString();
            }
        } catch (final URISyntaxException e) {
            result.add(new ValidationProblem(LOCATION, Severity.ERROR, "The location is not a valid URI"));
        } catch (final IOException e) {
            result.add(new ValidationProblem(LOCATION, Severity.ERROR, "The resource at the given URI does not exist"));
        }
        return referenceId;
    }

    private String validateLocalLocation(final ValidationResult result) {
        String referenceId = null;
        final Optional<Path> absoluteLocation = getAbsoluteLocation();
        if (absoluteLocation.isPresent()) {
            if (Files.exists(absoluteLocation.get())) {
                referenceId = absoluteLocation.get().toString();
            } else {
                final String msg = "The location {0} which resolves to {1} does not point to an existing file";
                result.add(new ValidationProblem(LOCATION, Severity.ERROR, MessageFormat.format(msg, location, absoluteLocation.get())));
            }
        } else {
            final String msg = "The location {0} cannot be resolved";
            result.add(new ValidationProblem(LOCATION, Severity.ERROR, MessageFormat.format(msg, location)));
        }
        return referenceId;
    }

    private Optional<Path> getAbsoluteLocation() {
        final LocationContext locationContext;
        if (isWorkspaceTypeSelected) {
            locationContext = LocationContext.WORKSPACE;
        } else if (isProjectTypeSelected) {
            locationContext = LocationContext.PROJECT;
        } else if (isFileSystemTypeSelected) {
            locationContext = LocationContext.FILE_SYSTEM;
        } else {
            throw new IllegalStateException("Unknown location type");
        }
        final Optional<String> resolvedLocation = LocationResolver.resolveIfExists(new Location(location, locationContext), project);
        if (resolvedLocation.isPresent()) {
            return Optional.of(Paths.get(resolvedLocation.get()));
        }
        return Optional.empty();
    }

    private void validateName(final ValidationResult result) {
        errorIfBlank(NAME, name, "Please enter a name for this rule set configuration", result);
    }

}
