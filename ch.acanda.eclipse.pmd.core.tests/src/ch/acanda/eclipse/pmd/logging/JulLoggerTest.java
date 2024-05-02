package ch.acanda.eclipse.pmd.logging;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.LogRecord;

import org.eclipse.core.runtime.IStatus;
import org.junit.jupiter.api.Test;

public class JulLoggerTest {

    @Test
    public void shouldLog() {
        final RecordingHandler handler = new RecordingHandler();
        java.util.logging.Logger.getLogger("ID").addHandler(handler);

        final Logger logger = Logger.forInactivePlugin("ID");
        logger.log(IStatus.WARNING, "A", new Exception("B"));

        final LogRecord record = handler.getRecord();
        assertEquals(Level.WARNING, record.getLevel(), "log level");
        assertEquals("A", record.getMessage(), "log message");
        assertEquals("B", record.getThrown().getMessage(), "exception message");
    }

    private static final class RecordingHandler extends Handler {

        private LogRecord record;

        @Override
        public void publish(final LogRecord record) {
            this.record = record;
        }

        @Override
        public void flush() {
            // nothing to do
        }

        @Override
        public void close() {
            // nothing to do
        }

        public LogRecord getRecord() {
            return record;
        }

    }

}
