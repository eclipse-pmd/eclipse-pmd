package ch.acanda.eclipse.pmd.repository;

import static java.nio.charset.StandardCharsets.UTF_8;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.StringReader;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import javax.xml.XMLConstants;
import javax.xml.transform.Source;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Validator;

import org.junit.jupiter.api.Test;
import org.xml.sax.SAXException;

import ch.acanda.eclipse.pmd.domain.Location;
import ch.acanda.eclipse.pmd.domain.LocationContext;
import ch.acanda.eclipse.pmd.domain.ProjectModel;
import ch.acanda.eclipse.pmd.domain.RuleSetModel;

/**
 * Unit tests for {@link ProjectModelSerializer}.
 */
@SuppressWarnings("PMD.AvoidDuplicateLiterals")
public final class ProjectModelSerializerTest {

    /**
     * Verifies that {@link ProjectModelSerializer#serialize(ProjectModel)} serializes a {@link ProjectModel} correctly.
     */
    @Test
    public void serialize() throws SAXException, IOException {
        final ProjectModel projectModel = new ProjectModel("TestProjectName");
        projectModel.setPMDEnabled(true);
        projectModel.setRuleSets(createRuleSets());

        final String actual = new ProjectModelSerializer().serialize(projectModel);

        final String expected = createXmlConfiguration();
        assertEquals(expected, actual, "Serialized project model");
        assertValid(actual);
    }

    /**
     * Verifies that {@link ProjectModelSerializer#serialize(ProjectModel)} serializes a {@link ProjectModel} without
     * rule sets correctly, i.e. without a {@code <rulesets>} tag.
     */
    @Test
    public void serializeWithoutRuleSets() throws SAXException, IOException {
        final ProjectModel projectModel = new ProjectModel("TestProjectName");
        projectModel.setPMDEnabled(false);

        final String actual = new ProjectModelSerializer().serialize(projectModel);

        final String expected = createXmlConfigurationWithoutRuleSets();
        assertEquals(expected, actual, "Serialized project model");
        assertValid(actual);
    }

    private String createXmlConfiguration() {
        return "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n"
                + "<eclipse-pmd xmlns=\"http://acanda.ch/eclipse-pmd/0.8\""
                + " xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\""
                + " xsi:schemaLocation=\"http://acanda.ch/eclipse-pmd/0.8 http://acanda.ch/eclipse-pmd/eclipse-pmd-0.8.xsd\">\n"
                + "  <analysis enabled=\"true\" />\n"
                + "  <rulesets>\n"
                + "    <ruleset name=\"Project Rule Set\" ref=\"pmd.xml\" refcontext=\"project\" />\n"
                + "    <ruleset name=\"Workspace Rule Set\" ref=\"Projext X/pmd.xml\" refcontext=\"workspace\" />\n"
                + "    <ruleset name=\"Filesystem Rule Set\" ref=\"x:\\pmx.xml\" refcontext=\"filesystem\" />\n"
                + "    <ruleset name=\"Remote Rule Set\" ref=\"http://example.org/pmd.xml\" refcontext=\"remote\" />\n"
                + "  </rulesets>\n"
                + "</eclipse-pmd>";
    }

    private String createXmlConfigurationWithoutRuleSets() {
        return "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n"
                + "<eclipse-pmd xmlns=\"http://acanda.ch/eclipse-pmd/0.8\""
                + " xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\""
                + " xsi:schemaLocation=\"http://acanda.ch/eclipse-pmd/0.8 http://acanda.ch/eclipse-pmd/eclipse-pmd-0.8.xsd\">\n"
                + "  <analysis enabled=\"false\" />\n"
                + "</eclipse-pmd>";
    }

    private Iterable<RuleSetModel> createRuleSets() {
        return Arrays.asList(new RuleSetModel("Project Rule Set", new Location("pmd.xml", LocationContext.PROJECT)),
                new RuleSetModel("Workspace Rule Set", new Location("Projext X/pmd.xml", LocationContext.WORKSPACE)),
                new RuleSetModel("Filesystem Rule Set", new Location("x:\\pmx.xml", LocationContext.FILE_SYSTEM)),
                new RuleSetModel("Remote Rule Set", new Location("http://example.org/pmd.xml", LocationContext.REMOTE)));
    }

    @SuppressWarnings("java:S2755" /* false positive */)
    private void assertValid(final String actual) throws SAXException, IOException {
        final SchemaFactory schemaFactory = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
        schemaFactory.setFeature("http://apache.org/xml/features/disallow-doctype-decl", true);
        final Source schemaSource = new StreamSource(ProjectModelSerializerTest.class.getResourceAsStream("eclipse-pmd-0.8.xsd"));
        final Schema schema = schemaFactory.newSchema(schemaSource);
        final Validator validator = schema.newValidator();
        final Source xmlSource = new StreamSource(new StringReader(actual));
        validator.validate(xmlSource);
    }

    /**
     * Verifies that {@link ProjectModelSerializer#deserialize(java.io.InputStream, String)} deserializes the attributes
     * of {@link ProjectModel} correctly.
     */
    @Test
    public void deserializeProjectModel() throws IOException {
        final ByteArrayInputStream stream = new ByteArrayInputStream(createXmlConfiguration().getBytes(UTF_8));

        final ProjectModel projectModel = new ProjectModelSerializer().deserialize(stream, "TestProjectName");

        assertEquals("TestProjectName", projectModel.getProjectName(), "Project name");
        assertTrue(projectModel.isPMDEnabled(), "PMD should be enabled");
        assertEquals(4, projectModel.getRuleSets().size(), "Number of rule sets");
    }

    /**
     * Verifies that {@link ProjectModelSerializer#deserialize(java.io.InputStream, String)} deserializes the attributes
     * of {@link ProjectModel} correctly.
     */
    @Test
    public void deserializeProjectModelWithoutRuleSets() throws IOException {
        final ByteArrayInputStream stream = new ByteArrayInputStream(createXmlConfigurationWithoutRuleSets().getBytes(UTF_8));

        final ProjectModel projectModel = new ProjectModelSerializer().deserialize(stream, "TestProjectName");

        assertEquals("TestProjectName", projectModel.getProjectName(), "Project name");
        assertFalse(projectModel.isPMDEnabled(), "PMD should be disabled");
        assertEquals(0, projectModel.getRuleSets().size(), "Number of rule sets");
    }

    /**
     * Verifies that {@link ProjectModelSerializer#deserialize(java.io.InputStream, String)} deserializes the attributes
     * of a project {@link RuleSetModel} correctly.
     */
    @Test
    public void deserializeProjectRuleSetModel() throws IOException {
        final ByteArrayInputStream stream = new ByteArrayInputStream(createXmlConfiguration().getBytes(UTF_8));

        final ProjectModel projectModel = new ProjectModelSerializer().deserialize(stream, "TestProjectName");

        assertRuleSetModel(projectModel, LocationContext.PROJECT, "Project Rule Set", "pmd.xml");
    }

    /**
     * Verifies that {@link ProjectModelSerializer#deserialize(java.io.InputStream, String)} deserializes the attributes
     * of a workspace {@link RuleSetModel} correctly.
     */
    @Test
    public void deserializeWorkspaceRuleSetModel() throws IOException {
        final ByteArrayInputStream stream = new ByteArrayInputStream(createXmlConfiguration().getBytes(UTF_8));

        final ProjectModel projectModel = new ProjectModelSerializer().deserialize(stream, "TestProjectName");

        assertRuleSetModel(projectModel, LocationContext.WORKSPACE, "Workspace Rule Set", "Projext X/pmd.xml");
    }

    /**
     * Verifies that {@link ProjectModelSerializer#deserialize(java.io.InputStream, String)} deserializes the attributes
     * of a filesystem {@link RuleSetModel} correctly.
     */
    @Test
    public void deserializeFilesystemRuleSetModel() throws IOException {
        final ByteArrayInputStream stream = new ByteArrayInputStream(createXmlConfiguration().getBytes(UTF_8));

        final ProjectModel projectModel = new ProjectModelSerializer().deserialize(stream, "TestProjectName");

        assertRuleSetModel(projectModel, LocationContext.FILE_SYSTEM, "Filesystem Rule Set", "x:\\pmx.xml");
    }

    /**
     * Verifies that {@link ProjectModelSerializer#deserialize(java.io.InputStream, String)} deserializes the attributes
     * of a remote {@link RuleSetModel} correctly.
     */
    @Test
    public void deserializeRemoteRuleSetModel() throws IOException {
        final ByteArrayInputStream stream = new ByteArrayInputStream(createXmlConfiguration().getBytes(UTF_8));

        final ProjectModel projectModel = new ProjectModelSerializer().deserialize(stream, "TestProjectName");

        assertRuleSetModel(projectModel, LocationContext.REMOTE, "Remote Rule Set", "http://example.org/pmd.xml");
    }

    private void assertRuleSetModel(final ProjectModel projectModel, final LocationContext context, final String name, final String path) {
        final RuleSetModel remoteRuleSet = extractRuleSetModel(projectModel, context);
        assertEquals(name, remoteRuleSet.getName(), "Name of the " + context + " rule set");
        assertEquals(path, remoteRuleSet.getLocation().getPath(), "Path of the " + context + " rule set");
    }

    /**
     * Extracts a rule set model from a project model depending on its location context. This method also verifies that
     * only one rule set model with the provided location context exists and throws an {@code NoSuchElementException} if
     * there is more than one model with the provided location context).
     */
    private RuleSetModel extractRuleSetModel(final ProjectModel model, final LocationContext context) {
        final List<RuleSetModel> models =
                model.getRuleSets().stream()
                        .filter(rs -> rs.getLocation().getContext() == context)
                        .collect(Collectors.toList());
        assertEquals(1, models.size(), "There should be exactly one model matching the context " + context);
        return models.get(0);
    }

}
