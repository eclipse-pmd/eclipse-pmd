package ch.acanda.eclipse.pmd.repository;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.function.Function;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;

import ch.acanda.eclipse.pmd.PMDPlugin;
import ch.acanda.eclipse.pmd.domain.ProjectModel;
import ch.acanda.eclipse.pmd.domain.RuleSetModel;

/**
 * <pre>
 * {@code
 * <?xml version="1.0" encoding="UTF-8"?>
 * <eclipse-pmd xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
 *              xsi:noNamespaceSchemaLocation="http://www.acanda.ch/eclipse-pmd/eclipse-pmd_0.8.0.xsd">
 *   <analysis enabled="true" />
 *   <rulesets>
 *     <ruleset name="Rules for this specific project"
 *              ref="pmd.xml" refcontext="project" />
 *     <ruleset name="Rules for all projects"
 *              ref="ch.acanda.pmd/pmd.xml" refcontext="workspace" />
 *     <ruleset name="Company rules"
 *              ref="e:\pmd.xml" refcontext="filesystem" />
 *     <ruleset name="Sonar"
 *              ref="http://example.org/profiles/export?format=pmd&language=java&name=MyProfile" refcontext="remote" />
 *   </rulesets>
 * </eclipse-pmd>
 * }
 * </pre>
 */
public class ProjectModelSerializer {

    static final String TAG_NAME_ECLIPSE_PMD = "eclipse-pmd";
    static final String TAG_NAME_ANALYSIS = "analysis";
    static final String TAG_NAME_RULESETS = "rulesets";
    static final String TAG_NAME_RULESET = "ruleset";
    static final String ATTRIBUTE_VALUE_REMOTE = "remote";
    static final String ATTRIBUTE_VALUE_FILESYSTEM = "filesystem";
    static final String ATTRIBUTE_VALUE_PROJECT = "project";
    static final String ATTRIBUTE_VALUE_WORKSPACE = "workspace";
    static final String ATTRIBUTE_NAME_ENABLED = "enabled";
    static final String ATTRIBUTE_NAME_REF = "ref";
    static final String ATTRIBUTE_NAME_REFCONTEXT = "refcontext";
    static final String ATTRIBUTE_NAME_NAME = "name";
    static final String SCHEMA_VERSION = "0.8";

    public static final Charset ENCODING = StandardCharsets.UTF_8;

    private static final Function<RuleSetModel, String> TO_XML_TAGS = new RuleSetConfigurationToXMLTag();

    public String serialize(final ProjectModel model) {
        final StringWriter result = new StringWriter(1024);
        final PrintWriter writer = new PrintWriter(result);
        writer.format("<?xml version=\"1.0\" encoding=\"%s\"?>\n", ENCODING);
        writer.format("<%s xmlns=\"http://acanda.ch/eclipse-pmd/%2$s\""
                + " xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\""
                + " xsi:schemaLocation=\"http://acanda.ch/eclipse-pmd/%2$s"
                + " http://acanda.ch/eclipse-pmd/eclipse-pmd-%2$s.xsd\">\n",
                TAG_NAME_ECLIPSE_PMD, SCHEMA_VERSION);
        writer.format("  <%s %s=\"%b\" />\n", TAG_NAME_ANALYSIS, ATTRIBUTE_NAME_ENABLED, model.isPMDEnabled());
        if (!model.getRuleSets().isEmpty()) {
            writer.format("  <%s>\n", TAG_NAME_RULESETS);
            model.getRuleSets().stream().map(TO_XML_TAGS).forEachOrdered(tag -> writer.append("    ").append(tag).append('\n'));
            writer.format("  </%s>\n", TAG_NAME_RULESETS);
        }
        writer.format("</%s>", TAG_NAME_ECLIPSE_PMD);
        return result.toString();
    }

    public ProjectModel deserialize(final InputStream configurationStream, final String projectName) throws IOException {

        final ProjectConfigurationContentHandler contentHandler = new ProjectConfigurationContentHandler();
        try {
            final SAXParserFactory factory = SAXParserFactory.newInstance();
            factory.setNamespaceAware(true);
            factory.setFeature("http://apache.org/xml/features/disallow-doctype-decl", true);
            final XMLReader reader = factory.newSAXParser().getXMLReader();
            reader.setContentHandler(contentHandler);
            reader.parse(new InputSource(configurationStream));
        } catch (final SAXException | ParserConfigurationException e) {
            PMDPlugin.getDefault().error("Cannot read eclipse-pmd project configuration", e);
        }

        return contentHandler.getProjectModel(projectName);

    }
}
