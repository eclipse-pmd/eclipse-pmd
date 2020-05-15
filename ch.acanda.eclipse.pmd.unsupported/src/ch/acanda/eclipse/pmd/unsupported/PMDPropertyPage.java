package ch.acanda.eclipse.pmd.unsupported;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.program.Program;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Link;
import org.eclipse.ui.dialogs.PropertyPage;

/**
 * This property page is shown when the JVM does not meet the requirement of eclipse-pmd.
 */
public class PMDPropertyPage extends PropertyPage {

    private static final int MIN_WIDTH = 200;

    @Override
    protected Control createContents(final Composite parent) {
        final Composite contents = new Composite(parent, SWT.NONE);
        contents.setLayoutData(new GridData(SWT.FILL, SWT.TOP, false, false));
        contents.setLayout(new GridLayout(1, false));

        final Link unsupported = new Link(contents, SWT.WRAP);
        final GridData unsupportedLayoutData = new GridData(SWT.FILL, SWT.TOP, true, false);
        unsupportedLayoutData.widthHint = MIN_WIDTH;
        unsupported.setLayoutData(unsupportedLayoutData);
        unsupported.setText(
                "eclipse-pmd requires that Eclipse runs on a JVM with version 11 or later but it"
                + " currently runs on a JVM with version " + System.getProperty("java.version", "[unknown]") + ".");

        final Link link = new Link(contents, SWT.WRAP);
        final GridData linkLayoutData = new GridData(SWT.FILL, SWT.TOP, true, true);
        linkLayoutData.widthHint = MIN_WIDTH;
        link.setLayoutData(linkLayoutData);
        link.setText("To change the JVM, you have to set the parameter -vm in your eclipse.ini accordingly."
                + " The <a href=\"http://wiki.eclipse.org/Eclipse.ini#Specifying_the_JVM\">Eclipse Wiki</a>"
                + " explains how to set up the JVM correctly.");
        link.addSelectionListener(new SelectionAdapter() {
            @Override
            public void widgetSelected(final SelectionEvent event) {
                Program.launch(event.text);
            }
        });

        noDefaultAndApplyButton();

        return contents;
    }

}
