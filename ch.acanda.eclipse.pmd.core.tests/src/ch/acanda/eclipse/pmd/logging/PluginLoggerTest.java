package ch.acanda.eclipse.pmd.logging;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.eclipse.core.runtime.ILog;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Plugin;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

public class PluginLoggerTest {

    @Test
    public void shouldLog() {

        final Plugin plugin = mock(Plugin.class);
        final ILog log = mock(ILog.class);
        when(plugin.getLog()).thenReturn(log);

        final Logger logger = Logger.forActivePlugin(plugin, "ID");
        logger.log(IStatus.WARNING, "A", new Exception("B"));

        final ArgumentCaptor<IStatus> captor = ArgumentCaptor.forClass(IStatus.class);
        verify(log).log(captor.capture());

        final IStatus status = captor.getValue();
        assertEquals(2, status.getSeverity(), "severity");
        assertEquals("A", status.getMessage(), "log message");
        assertEquals("B", status.getException().getMessage(), "exception message");
    }

}
