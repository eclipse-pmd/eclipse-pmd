package ch.acanda.eclipse.pmd.file;

import static java.nio.file.StandardWatchEventKinds.ENTRY_DELETE;
import static java.nio.file.StandardWatchEventKinds.ENTRY_MODIFY;
import static java.nio.file.StandardWatchEventKinds.OVERFLOW;

import java.io.IOException;
import java.nio.file.ClosedWatchServiceException;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.nio.file.WatchEvent;
import java.nio.file.WatchKey;
import java.nio.file.WatchService;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import ch.acanda.eclipse.pmd.PMDPlugin;

/**
 * Watches files and notifies registered listeners when they have changed.
 */
public final class FileWatcher {

    private final WatchService watchService;

    /**
     * Maps an absolute directory path to its watch key.
     */
    private final Map<Path, WatchKey> watchKeys = new HashMap<>();

    /**
     * Maps an absolute file path to its listeners.
     */
    private final Map<Path, List<FileChangedListener>> listeners = new HashMap<>();

    /**
     * Maps an absolute directory path to its absolute file paths that are being watched.
     */
    private final Map<Path, List<Path>> watchedFiles = new HashMap<>();

    private WatcherThread watcherThread;

    public FileWatcher() throws IOException {
        watchService = FileSystems.getDefault().newWatchService();
    }

    public Subscription subscribe(final Path file, final FileChangedListener listener) throws IOException {
        final Path canonicalFile = file.toRealPath();
        listeners.computeIfAbsent(canonicalFile, f -> new ArrayList<>()).add(listener);

        final Path canonicalDirectory = canonicalFile.getParent();
        watchedFiles.computeIfAbsent(canonicalDirectory, d -> new ArrayList<>()).add(canonicalFile);

        if (!watchKeys.containsKey(canonicalDirectory)) {
            final WatchKey watchKey = canonicalDirectory.register(watchService, ENTRY_MODIFY, ENTRY_DELETE);
            watchKeys.put(canonicalDirectory, watchKey);
            if (watchKeys.size() == 1) {
                startWatcher();
            }
        }

        return new Subscription() {
            @Override
            public void cancel() {
                removeFrom(listeners, canonicalFile, listener);
                removeFrom(watchedFiles, canonicalDirectory, canonicalFile);
                if (!watchedFiles.containsKey(canonicalFile)) {
                    watchedFiles.remove(canonicalDirectory);
                    watchKeys.remove(canonicalDirectory);
                    if (watchKeys.isEmpty()) {
                        stopWatcher();
                    }
                }
            }

            private <K, V> void removeFrom(final Map<K, List<V>> map, final K key, final V value) {
                final List<V> list = map.get(key);
                if (list != null) {
                    list.remove(value);
                    if (list.isEmpty()) {
                        map.remove(key);
                    }
                }
            }
        };
    }

    private void startWatcher() {
        watcherThread = new WatcherThread();
        watcherThread.start();
    }

    @SuppressWarnings("PMD.NullAssignment")
    private void stopWatcher() {
        if (watcherThread != null) {
            watcherThread.interrupt();
            watcherThread = null;
        }
    }

    private final class WatcherThread extends Thread {

        public WatcherThread() {
            super("eclipse-pmd RuleSetWatcher");
        }

        @Override
        public void run() {
            try {
                while (true) {
                    final WatchKey watchKey = watchService.take();
                    if (watchKey.isValid()) {
                        final Path directory = (Path) watchKey.watchable();
                        for (final WatchEvent<?> event : watchKey.pollEvents()) {
                            handleEvent(event, directory);
                        }
                    }
                    watchKey.reset();
                }
            } catch (final ClosedWatchServiceException e) {
                PMDPlugin.getLogger().info(getName() + " stopped");
            } catch (final InterruptedException e) {
                PMDPlugin.getLogger().info(getName() + " interrupted");
                currentThread().interrupt();
            }
        }

        private void handleEvent(final WatchEvent<?> event, final Path directory) {
            if (!OVERFLOW.equals(event.kind())) {
                final String filename = event.context().toString();
                final Path file = directory.resolve(filename);
                final List<FileChangedListener> fileListeners = listeners.get(file);
                if (fileListeners != null) {
                    for (final FileChangedListener listener : fileListeners) {
                        listener.fileChanged(file);
                    }
                }
            }
        }

    }

}
