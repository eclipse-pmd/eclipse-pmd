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
 *
 * @author Philip Graf
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
        final Path absoluteFile = file.toAbsolutePath();
        listeners.computeIfAbsent(absoluteFile, f -> new ArrayList<>()).add(listener);

        final Path absoluteDirectory = file.getParent();
        watchedFiles.computeIfAbsent(absoluteDirectory, d -> new ArrayList<>()).add(absoluteFile);

        if (!watchKeys.containsKey(absoluteDirectory)) {
            final WatchKey watchKey = absoluteDirectory.register(watchService, ENTRY_MODIFY, ENTRY_DELETE);
            watchKeys.put(absoluteDirectory, watchKey);
            if (watchKeys.size() == 1) {
                startWatcher();
            }
        }

        return new Subscription() {
            @Override
            public void cancel() {
                removeFrom(listeners, absoluteFile, listener);
                removeFrom(watchedFiles, absoluteDirectory, absoluteFile);
                if (!watchedFiles.containsKey(absoluteFile)) {
                    watchedFiles.remove(absoluteDirectory);
                    watchKeys.remove(absoluteDirectory);
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
                            if (event.kind() != OVERFLOW) {
                                final String filename = event.context().toString();
                                final Path file = directory.resolve(filename);
                                PMDPlugin.getDefault().info(event.kind() + ": " + file);
                                for (final FileChangedListener listener : listeners.get(file)) {
                                    listener.fileChanged(file);
                                }
                            }
                        }
                    }
                    watchKey.reset();
                }
            } catch (final InterruptedException | ClosedWatchServiceException e) {
                PMDPlugin.getDefault().info(getName() + " stopped");
            }
        }

    }

}
