package ch.acanda.eclipse.pmd.file;

import java.nio.file.Path;

/**
 * A {@code FileChangeListener} receives a notification when a file has been change when it's registered with a
 * {@link FileWatcher}.
 */
public interface FileChangedListener {

    void fileChanged(Path file);

}
