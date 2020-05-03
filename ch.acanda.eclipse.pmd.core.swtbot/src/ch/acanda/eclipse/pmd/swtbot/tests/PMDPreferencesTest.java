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

package ch.acanda.eclipse.pmd.swtbot.tests;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.net.URL;
import java.util.Enumeration;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.eclipse.swtbot.swt.finder.widgets.SWTBotLabel;
import org.junit.Test;

import ch.acanda.eclipse.pmd.PMDPlugin;
import ch.acanda.eclipse.pmd.swtbot.SWTBotID;
import ch.acanda.eclipse.pmd.swtbot.bot.PMDPreferenceDialogBot;

public class PMDPreferencesTest extends GUITestCase {

    @Test
    public void preferencePageShouldShowPMDVersion() {
        final PMDPreferenceDialogBot bot = PMDPreferenceDialogBot.open();
        final SWTBotLabel pmdVersionLabel = bot.bot().labelWithId(SWTBotID.PMD_VERSION.name());
        final String expectedVersion = getPmdVersion();
        assertTrue("\"" + pmdVersionLabel.getText() + "\" should contain " + expectedVersion,
                pmdVersionLabel.getText().contains(expectedVersion));
        bot.close();
    }

    private static String getPmdVersion() {
        final Enumeration<URL> entries = PMDPlugin.getDefault().getBundle().findEntries("lib", "pmd-core-*.jar", false);
        while (entries.hasMoreElements()) {
            final String path = entries.nextElement().getPath();
            final Pattern pattern = Pattern.compile("pmd-core-(\\d+(?:\\.\\d+)*).jar$");
            final Matcher matcher = pattern.matcher(path);
            if (matcher.find()) {
                return matcher.group(1);
            }
        }
        fail("Unable to retrieve PMD version");
        return null;
    }

}
