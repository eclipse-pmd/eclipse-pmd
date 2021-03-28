package ch.acanda.eclipse.pmd.unsupported;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

/**
 * Unit tests for {@link ProjectPropertyTester}.
 */
public class ProjectPropertyTesterTest {

    private static final String JAVA_VERSION_LESS_THAN = "javaVersionLessThan";
    private static final String JAVA_VERSION = "java.version";
    private static String javaVersion;

    @BeforeAll
    public static void beforeClass() {
        javaVersion = System.getProperty(JAVA_VERSION);
    }

    @AfterAll
    public static void afterClass() {
        System.setProperty(JAVA_VERSION, javaVersion);
    }

    @Test
    public void javaVersion6LessThan7() {
        System.setProperty(JAVA_VERSION, "1.6.0_23");
        final ProjectPropertyTester tester = new ProjectPropertyTester();
        final boolean result = tester.test(null, JAVA_VERSION_LESS_THAN, new String[] { "1.7" }, null);
        assertTrue(result, "Version 1.6.0_23 should be less than 1.7");
    }

    @Test
    public void javaVersion7LessThan7() {
        System.setProperty(JAVA_VERSION, "1.7.0_45");
        final ProjectPropertyTester tester = new ProjectPropertyTester();
        final boolean result = tester.test(null, JAVA_VERSION_LESS_THAN, new String[] { "1.7" }, null);
        assertFalse(result, "Version 1.7.0_45 should not be less than 1.7");
    }

    @Test
    public void javaVersionLessThanExactMatch() {
        System.setProperty(JAVA_VERSION, "1.7.0_45");
        final ProjectPropertyTester tester = new ProjectPropertyTester();
        final boolean result = tester.test(null, JAVA_VERSION_LESS_THAN, new String[] { "1.7.0_45" }, null);
        assertFalse(result, "Version 1.7.0_45 should not be less than 1.7.0_45");
    }

    @Test
    public void java8EarlyAccess() {
        System.setProperty(JAVA_VERSION, "1.8.0-ea");
        final ProjectPropertyTester tester = new ProjectPropertyTester();
        final boolean result = tester.test(null, JAVA_VERSION_LESS_THAN, new String[] { "1.7.0_51" }, null);
        assertFalse(result, "Version 1.7.0_51 should not be less than 1.8.0-ea");
    }

    @Test
    public void invalidJVMVersion() {
        System.setProperty(JAVA_VERSION, "x.y.z");
        final ProjectPropertyTester tester = new ProjectPropertyTester();
        final boolean result = tester.test(null, JAVA_VERSION_LESS_THAN, new String[] { "1.7.0_51" }, null);
        assertFalse(result, "The tester should return false as the JVM version is invalid");
    }

    @Test
    public void invalidArgumentVersion() {
        System.setProperty(JAVA_VERSION, "1.7.0_51");
        final ProjectPropertyTester tester = new ProjectPropertyTester();
        final boolean result = tester.test(null, JAVA_VERSION_LESS_THAN, new String[] { "x.y.z" }, null);
        assertFalse(result, "The tester should return false as the argument version is invalid");
    }
}
