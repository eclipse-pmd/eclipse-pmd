package ch.acanda.eclipse.pmd.ui.util;

import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;

/**
 * Default implementation of {@link SelectionListener} with {@link #widgetSelected(SelectionEvent)} calling
 * {@link #widgetDefaultSelected(SelectionEvent)} and vice versa so only one of them needs to be implemented.
 */
public class SelectionAdapter implements SelectionListener {

    @Override
    public void widgetSelected(final SelectionEvent e) {
        widgetDefaultSelected(e);
    }

    @Override
    public void widgetDefaultSelected(final SelectionEvent e) {
        widgetSelected(e);
    }

}
