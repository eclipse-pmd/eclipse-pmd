package ch.acanda.eclipse.pmd.logging;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.junit.jupiter.api.Test;

public class LoggerTest {

    @Test
    public void shouldLogErrors() {
        final TestLogger logger = new TestLogger();

        logger.error("A");
        assertEquals("[4] A: null", logger.getEntry(), "error without exception");

        logger.error("A", new Exception("B"));
        assertEquals("[4] A: B", logger.getEntry(), "error with exception");
    }

    @Test
    public void shouldLogWarnings() {
        final TestLogger logger = new TestLogger();

        logger.warn("A", new Exception("B"));
        assertEquals("[2] A: B", logger.getEntry(), "warning with exception");
    }

    @Test
    public void shouldLogInfos() {
        final TestLogger logger = new TestLogger();

        logger.info("A");
        assertEquals("[1] A: null", logger.getEntry(), "info without exception");

        logger.info("A", new Exception("B"));
        assertEquals("[1] A: B", logger.getEntry(), "info with exception");
    }

    @Test
    public void shouldLogStatus() {
        final TestLogger logger = new TestLogger();

        logger.log(new Status(IStatus.CANCEL, "ID", "A", new Exception("B")));
        assertEquals("[8] A: B", logger.getEntry(), "info without exception");
    }

    private final static class TestLogger extends Logger {

        private String entry;

        @Override
        public IStatus log(final IStatus status) {
            return log(status.getSeverity(), status.getMessage(), status.getException());
        }

        @Override
        protected IStatus log(final int severity, final String message, final Throwable throwable) {
            entry = "[" + severity + "] " + message + ": " + (throwable == null ? null : throwable.getMessage());
            return null;
        }

        public String getEntry() {
            return entry;
        }

    }

}
