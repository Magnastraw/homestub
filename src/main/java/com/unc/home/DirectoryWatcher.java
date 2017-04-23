package com.unc.home;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.unc.home.requests.HttpRequestManager;
import com.unc.home.smarthome.inventory.Inventory;
import com.unc.home.smarthome.inventory.InventoryObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.file.*;

import static java.nio.file.StandardWatchEventKinds.*;
import static java.nio.file.LinkOption.*;

import java.nio.file.attribute.*;
import java.io.*;
import java.util.*;

public class DirectoryWatcher implements Runnable {
    private static final Logger LOG = LoggerFactory.getLogger(HomestubApplication.class);
    private final WatchService watcher;
    private final Map<WatchKey, Path> keys;
    private Inventory inventory;
    private boolean trace = false;
    private Path dir;
    private String houseId;

    @SuppressWarnings("unchecked")
    static <T> WatchEvent<T> cast(WatchEvent<?> event) {
        return (WatchEvent<T>) event;
    }

    private void register(Path dir) throws IOException {
        WatchKey key = dir.register(watcher, ENTRY_CREATE, ENTRY_DELETE, ENTRY_MODIFY);
        if (trace) {
            Path prev = keys.get(key);
            if (prev == null) {
                System.out.format("register: %s\n", dir);
            } else {
                if (!dir.equals(prev)) {
                    System.out.format("update: %s -> %s\n", prev, dir);
                }
            }
        }
        LOG.info(dir.toString());

        keys.put(key, dir);
    }

    private void registerAll(final Path start) throws IOException {
        Files.walkFileTree(start, new SimpleFileVisitor<Path>() {
            @Override
            public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs)
                    throws IOException {
                register(dir);
                return FileVisitResult.CONTINUE;
            }
        });
    }

    DirectoryWatcher(Path dir, Inventory inventory, String houseId) throws IOException {
        this.watcher = FileSystems.getDefault().newWatchService();
        this.keys = new HashMap<WatchKey, Path>();
        this.inventory = inventory;
        this.dir = dir;
        this.houseId = houseId;
        System.out.format("Scanning %s ...\n", dir);
        registerAll(dir);
        System.out.println("Done.");
        this.trace = true;

    }

    @Override
    public void run() {
        for (; ; ) {

            WatchKey key;
            try {
                key = watcher.take();
            } catch (InterruptedException x) {
                return;
            }

            Path dir = keys.get(key);
            if (dir == null) {
                System.err.println("WatchKey not recognized!!");
                continue;
            }

            for (WatchEvent<?> event : key.pollEvents()) {
                WatchEvent.Kind kind = event.kind();

                if (kind == OVERFLOW) {
                    continue;
                }

                WatchEvent<Path> ev = cast(event);
                Path name = ev.context();
                Path child = dir.resolve(name);

                System.out.format("%s: %s\n", event.kind().name(), child);

                if (kind == ENTRY_CREATE) {
                    try {
                        if (Files.isDirectory(child, NOFOLLOW_LINKS)) {
                            registerAll(child);
                        } else {
                            //TODO:single object add and multithreading
                            inventory.setObjectId(1L);
                            inventory.setInventoryObjectList(new ArrayList<InventoryObject>());
                            inventory.buildInventoryFromDirectory(this.dir.toFile(), 0);
                            HttpRequestManager.postRequestList(inventory.getInventoryObjectList(), "inventories", houseId);

                        }
                    } catch (IOException x) {
                        // ignore to keep sample readbale
                    }
                }
            }

            boolean valid = key.reset();
            if (!valid) {
                keys.remove(key);

                if (keys.isEmpty()) {
                    break;
                }
            }
        }
    }

}
