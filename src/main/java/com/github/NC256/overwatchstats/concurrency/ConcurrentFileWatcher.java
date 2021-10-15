package com.github.NC256.overwatchstats.concurrency;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.StandardWatchEventKinds;
import java.nio.file.WatchKey;
import java.nio.file.WatchService;

/**
 * This class watches a folder for new files being created using Java's built-in
 * Watchservice classes.
 * When it is notified by an event it will set its own flag to true.
 * No other logic or processing occurs here.
 */
public class ConcurrentFileWatcher implements Runnable {

    private volatile boolean serviceEvent = false;
    private final Path folder;
    private final Logger logger = LogManager.getLogger(this);


    public ConcurrentFileWatcher(Path folder) {
        logger.info("Instantiated with folder: " + folder.toString());
        this.folder = folder;
    }


    @Override
    public void run() {
        logger.info("Entering run()");
        try {
            WatchService ws = folder.getFileSystem().newWatchService();
            folder.register(ws, StandardWatchEventKinds.ENTRY_CREATE); //watching for new files
            WatchKey wk = null;
            while (wk == null) {
                wk = ws.take(); // Can be interrupted
            }
            serviceEvent = true;
            // In theory we could parse the event(s), but it's safer to just alert main
            // And let it fire up a new service, and verify the newest file
            // Don't want any weird concurrency bugs where a new file gets created in between
            // watch services being in existence and we miss it or something silly like that

            //TODO there has got to be a better way to architecture this
            while (true) { // Spin wheels while waiting for main to read our status?
                if (Thread.currentThread().isInterrupted()) {
                    throw new InterruptedException();
                }
            }
        } catch (IOException | InterruptedException e) {
            logger.warn(e);
        }
        logger.info("Exiting run()");
    }

    public boolean hasEventOccurred() {
        return serviceEvent;
    }
}
