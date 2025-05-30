package ch.acanda.eclipse.pmd.preferences;

import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.Enumeration;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.eclipse.jface.preference.PreferencePage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Link;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPreferencePage;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.browser.IWebBrowser;

import ch.acanda.eclipse.pmd.PMDPlugin;
import ch.acanda.eclipse.pmd.swtbot.SWTBotID;
import ch.acanda.eclipse.pmd.ui.util.SelectionAdapter;

public class PMDPreferencePage extends PreferencePage implements IWorkbenchPreferencePage {

    private static final int WRAP_TO_PARENT_WITH = 0;
    private static final int VERTICAL_IDENT = 20;

    @Override
    public void init(final IWorkbench workbench) {
        noDefaultAndApplyButton();
    }

    @Override
    @SuppressWarnings("PMD.NcssCount")
    protected Control createContents(final Composite parent) {
        final Composite composite = new Composite(parent, SWT.NONE);
        final GridLayout layout = new GridLayout(2, false);
        layout.verticalSpacing = 2;
        layout.marginHeight = 0;
        layout.marginWidth = 0;
        composite.setLayout(layout);

        final String pmdVersion = getPmdVersion();

        final Label pmdVersionLabel = new Label(composite, SWT.NONE);
        pmdVersionLabel.setLayoutData(new GridData(SWT.LEFT, SWT.CENTER, false, false, 2, 1));
        pmdVersionLabel.setText("This plugin uses PMD version " + pmdVersion + ".");
        SWTBotID.set(pmdVersionLabel, SWTBotID.PMD_VERSION);

        final Label instructionLabel = new Label(composite, SWT.WRAP);
        final GridData instructionLabelData = new GridData(SWT.FILL, SWT.TOP, true, false, 2, 1);
        instructionLabelData.widthHint = WRAP_TO_PARENT_WITH;
        instructionLabelData.verticalIndent = VERTICAL_IDENT;
        instructionLabel.setLayoutData(instructionLabelData);
        instructionLabel.setText("Use the property dialog of your project(s) to activate and configure eclipse-pmd:");

        final Label step1 = new Label(composite, SWT.NONE);
        step1.setLayoutData(new GridData(SWT.LEFT, SWT.TOP, false, false, 1, 1));
        step1.setText("1.");

        final Link step1Link = new Link(composite, SWT.WRAP);
        final GridData step1LabelData = new GridData(SWT.FILL, SWT.TOP, true, false, 1, 1);
        step1LabelData.widthHint = WRAP_TO_PARENT_WITH;
        step1Link.setLayoutData(step1LabelData);
        step1Link.setText("<a>Create a new PMD rule set</a> if you do not already have one.");
        step1Link.addSelectionListener(new SelectionAdapter() {
            @Override
            public void widgetSelected(final SelectionEvent event) {
                openInBrowser("https://pmd.github.io/pmd-" + pmdVersion + "/pmd_userdocs_making_rulesets.html");
            }
        });

        final Label step2 = new Label(composite, SWT.NONE);
        step2.setLayoutData(new GridData(SWT.LEFT, SWT.TOP, false, false, 1, 1));
        step2.setText("2.");

        final Label step2Label = new Label(composite, SWT.WRAP);
        final GridData step2LabelData = new GridData(SWT.FILL, SWT.TOP, true, false, 1, 1);
        step2LabelData.widthHint = WRAP_TO_PARENT_WITH;
        step2Label.setLayoutData(step2LabelData);
        step2Label.setText("Open the property dialog of your project.");

        final Label step3 = new Label(composite, SWT.NONE);
        step3.setLayoutData(new GridData(SWT.LEFT, SWT.TOP, false, false, 1, 1));
        step3.setText("3.");

        final Label step3Label = new Label(composite, SWT.WRAP);
        final GridData step3LabelData = new GridData(SWT.FILL, SWT.TOP, true, false, 1, 1);
        step3LabelData.widthHint = WRAP_TO_PARENT_WITH;
        step3Label.setLayoutData(step3LabelData);
        step3Label.setText("Select the PMD property page.");

        final Label step4 = new Label(composite, SWT.NONE);
        step4.setLayoutData(new GridData(SWT.LEFT, SWT.TOP, false, false, 1, 1));
        step4.setText("4.");

        final Label step4Label = new Label(composite, SWT.WRAP);
        final GridData step4LabelData = new GridData(SWT.FILL, SWT.TOP, false, false, 1, 1);
        step4LabelData.widthHint = WRAP_TO_PARENT_WITH;
        step4Label.setLayoutData(step4LabelData);
        step4Label.setText("Check the \"Enable PMD for this project\" checkbox.");

        final Label step5 = new Label(composite, SWT.NONE);
        step5.setLayoutData(new GridData(SWT.LEFT, SWT.TOP, false, false, 1, 1));
        step5.setText("5.");

        final Label step5Label = new Label(composite, SWT.WRAP);
        final GridData step5LabelData = new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1);
        step5LabelData.widthHint = WRAP_TO_PARENT_WITH;
        step5Label.setLayoutData(step5LabelData);
        step5Label.setText("Add one or more rule sets by clicking the \"Add...\" button.");

        final Label step6 = new Label(composite, SWT.NONE);
        step6.setLayoutData(new GridData(SWT.LEFT, SWT.TOP, false, false, 1, 1));
        step6.setText("6.");

        final Label step6Label = new Label(composite, SWT.WRAP);
        final GridData step6LabelData = new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1);
        step6LabelData.widthHint = WRAP_TO_PARENT_WITH;
        step6Label.setLayoutData(step6LabelData);
        step6Label.setText("Close the property dialog. Your project will now be analysed by PMD.");

        final Link sponsorLink = new Link(composite, SWT.WRAP);
        final GridData sponsorLinkData = new GridData(SWT.FILL, SWT.TOP, true, false, 2, 1);
        sponsorLinkData.widthHint = WRAP_TO_PARENT_WITH;
        sponsorLinkData.verticalIndent = VERTICAL_IDENT;
        sponsorLink.setLayoutData(sponsorLinkData);
        sponsorLink.setText("Support the development of eclipse-pmd by <a>becoming a sponsor</a>.");
        sponsorLink.addSelectionListener(new SelectionAdapter() {
            @Override
            public void widgetSelected(final SelectionEvent event) {
                openInBrowser("https://github.com/sponsors/eclipse-pmd");
            }
        });

        return composite;
    }

    private String getPmdVersion() {
        final Enumeration<URL> entries = PMDPlugin.getDefault().getBundle().findEntries("lib", "pmd-core-*.jar", false);
        while (entries.hasMoreElements()) {
            final String path = entries.nextElement().getPath();
            final Pattern pattern = Pattern.compile("pmd-core-(\\d+[\\.\\d]{0,10}(?:-rc\\d+)?)\\.jar$");
            final Matcher matcher = pattern.matcher(path);
            if (matcher.find()) {
                return matcher.group(1);
            }
        }
        return "[unknown]";
    }

    private void openInBrowser(final String url) {
        try {
            final IWebBrowser browser = PlatformUI.getWorkbench().getBrowserSupport().getExternalBrowser();
            browser.openURL(new URI(url).toURL());
        } catch (final PartInitException | MalformedURLException | URISyntaxException e) {
            PMDPlugin.getLogger().error("Failed to open sponsor URL in browser: " + url, e);
        }
    }

}
