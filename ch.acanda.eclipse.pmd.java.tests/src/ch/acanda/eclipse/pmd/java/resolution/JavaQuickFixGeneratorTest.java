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

package ch.acanda.eclipse.pmd.java.resolution;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.Collection;
import java.util.List;

import org.eclipse.ui.IMarkerResolution;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;
import org.osgi.framework.Version;

import ch.acanda.eclipse.pmd.java.resolution.codestyle.ExtendsObjectQuickFix;
import ch.acanda.eclipse.pmd.marker.PMDMarker;

@RunWith(value = Parameterized.class)
public class JavaQuickFixGeneratorTest {

    private final String ruleId;
    private final String javaVersion;
    private final Class<? extends IMarkerResolution>[] expectedQuickFixClasses;

    private PMDMarker marker;
    private JavaQuickFixContext context;

    @SafeVarargs
    @SuppressWarnings("PMD.ArrayIsStoredDirectly")
    public JavaQuickFixGeneratorTest(final String ruleId, final int javaVersion,
            final Class<? extends IMarkerResolution>... expectedQuickFixClasses) {
        this.ruleId = ruleId;
        this.javaVersion = "1." + javaVersion + ".0";
        this.expectedQuickFixClasses = expectedQuickFixClasses;
    }

    @Parameters
    public static Collection<Object[]> getTestData() {
        return List.of(
                createTestData("java.code style.ExtendsObject", 4, ExtendsObjectQuickFix.class),
                createTestData("java.code style.ExtendsObject", 8, SuppressWarningsQuickFix.class),
                createTestData("java.code style.ExtendsObject", 5, ExtendsObjectQuickFix.class, SuppressWarningsQuickFix.class));
    }

    @SafeVarargs
    private static Object[] createTestData(final String ruleId, final int javaVersion,
            final Class<? extends IMarkerResolution>... classes) {
        return new Object[] { ruleId, javaVersion, classes };
    }

    @Before
    public void setUp() {
        marker = mock(PMDMarker.class);
        when(marker.getRuleId()).thenReturn(ruleId);
        context = new JavaQuickFixContext(new Version(javaVersion));
    }

    @Test
    public void hasQuickFixes() {
        final boolean hasQuickFixes = new JavaQuickFixGenerator().hasQuickFixes(marker, context);

        assertEquals("hasQuickFixes should return whether the generator has quick fixes for the rule " + ruleId
                + " and java version: " + javaVersion, expectedQuickFixClasses.length > 0, hasQuickFixes);
    }

    @Test
    public void testGetQuickFixes() {
        final Class<?>[] actualQuickFixClasses =
                new JavaQuickFixGenerator().getQuickFixes(marker, context).stream().map(IMarkerResolution::getClass).toArray(Class[]::new);

        assertArrayEquals("Quick fixes for rule " + ruleId + " and java version " + javaVersion, expectedQuickFixClasses,
                actualQuickFixClasses);
    }

}
