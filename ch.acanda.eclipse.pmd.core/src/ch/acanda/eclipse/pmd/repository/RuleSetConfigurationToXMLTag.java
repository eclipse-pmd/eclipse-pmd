package ch.acanda.eclipse.pmd.repository;

import static ch.acanda.eclipse.pmd.repository.ProjectModelSerializer.ATTRIBUTE_NAME_NAME;
import static ch.acanda.eclipse.pmd.repository.ProjectModelSerializer.ATTRIBUTE_NAME_REF;
import static ch.acanda.eclipse.pmd.repository.ProjectModelSerializer.ATTRIBUTE_NAME_REFCONTEXT;
import static ch.acanda.eclipse.pmd.repository.ProjectModelSerializer.ATTRIBUTE_VALUE_FILESYSTEM;
import static ch.acanda.eclipse.pmd.repository.ProjectModelSerializer.ATTRIBUTE_VALUE_PROJECT;
import static ch.acanda.eclipse.pmd.repository.ProjectModelSerializer.ATTRIBUTE_VALUE_REMOTE;
import static ch.acanda.eclipse.pmd.repository.ProjectModelSerializer.ATTRIBUTE_VALUE_WORKSPACE;
import static ch.acanda.eclipse.pmd.repository.ProjectModelSerializer.TAG_NAME_RULESET;
import static com.google.common.base.Strings.nullToEmpty;

import java.util.Locale;
import java.util.function.Function;

import com.google.common.escape.Escaper;
import com.google.common.xml.XmlEscapers;

import ch.acanda.eclipse.pmd.domain.RuleSetModel;

/**
 * This function transforms a rule set configuration into an XML tag suitable as a child of the tag {@code rulesets}.
 *
 * @see ProjectModelSerializer
 *
 * @author Philip Graf
 */
final class RuleSetConfigurationToXMLTag implements Function<RuleSetModel, String> {

    @Override
    public String apply(final RuleSetModel config) {
        final Escaper escaper = XmlEscapers.xmlAttributeEscaper();
        final String name = escaper.escape(nullToEmpty(config.getName()));
        final String ref = escaper.escape(nullToEmpty(config.getLocation().getPath()));
        final String refcontext = getContext(config);
        return String.format(Locale.ENGLISH, "<%s %s=\"%s\" %s=\"%s\" %s=\"%s\" />",
                TAG_NAME_RULESET, ATTRIBUTE_NAME_NAME, name, ATTRIBUTE_NAME_REF, ref, ATTRIBUTE_NAME_REFCONTEXT, refcontext);
    }

    private String getContext(final RuleSetModel ruleSet) {
        final String value;
        switch (ruleSet.getLocation().getContext()) {
            case WORKSPACE:
                value = ATTRIBUTE_VALUE_WORKSPACE;
                break;
            case PROJECT:
                value = ATTRIBUTE_VALUE_PROJECT;
                break;
            case FILE_SYSTEM:
                value = ATTRIBUTE_VALUE_FILESYSTEM;
                break;
            case REMOTE:
                value = ATTRIBUTE_VALUE_REMOTE;
                break;
            default:
                throw new IllegalArgumentException("Unexpected location context: " + ruleSet.getLocation().getContext());
        }
        return value;
    }

}
