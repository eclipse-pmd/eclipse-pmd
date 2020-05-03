// =====================================================================
//
// Copyright (C) 2012 - 2020, Philip Graf
//
// All rights reserved. This program and the accompanying materials
// are made available under the terms of the Eclipse Public License v1.0
// which accompanies this distribution, and is available at
// http://www.eclipse.org/legal/epl-v10.html
//
// =====================================================================

package ch.acanda.eclipse.pmd.swtbot.bot;

import org.eclipse.swt.widgets.Shell;
import org.eclipse.swtbot.eclipse.finder.SWTWorkbenchBot;
import org.eclipse.swtbot.swt.finder.exceptions.WidgetNotFoundException;

public final class PMDPreferenceDialogBot extends DialogBot {

    private PMDPreferenceDialogBot(final Shell shell) throws WidgetNotFoundException {
        super(shell);
    }

    public static PMDPreferenceDialogBot open() {
        final SWTWorkbenchBot bot = new SWTWorkbenchBot();
        bot.menu("Window").menu("Preferences").click();
        final PMDPreferenceDialogBot preferenceBot = new PMDPreferenceDialogBot(bot.shell("Preferences").widget);
        preferenceBot.bot().tree().getTreeItem("PMD").select();
        return preferenceBot;
    }

}
