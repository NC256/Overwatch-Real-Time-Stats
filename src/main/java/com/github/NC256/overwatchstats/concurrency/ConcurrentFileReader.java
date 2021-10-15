package com.github.NC256.overwatchstats.concurrency;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.StandardOpenOption;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * This class provides file parsing that happens in real-time.
 * All lines of text from the file are stored into a ConcurrentLinkedQueue for processing elsewhere.
 * No other logic is implemented here. This class can be interrupted and shut down remotely.
 */
public class ConcurrentFileReader implements Runnable {

    private final File log;
    private final LinkedBlockingQueue<String> strings;
    private final Logger logger = LogManager.getLogger(this);

    public ConcurrentFileReader(File log) {
        logger.info("Instantiated pointing to file " + log.getAbsolutePath());
        this.log = log;
        strings = new LinkedBlockingQueue<String>();
    }

    public ConcurrentFileReader(File log, LinkedBlockingQueue<String> strings) {
        logger.info("Instantiated with Queue and pointing to file " + log.getAbsolutePath());
        this.log = log;
        this.strings = strings;
    }

    public LinkedBlockingQueue<String> getSharedQueue() {
        return strings;
    }

    @Override
    //@SuppressWarnings("InfiniteLoopStatement")
    public void run() {
        logger.debug("Entering run() method.");
        try {
            InputStream is = Files.newInputStream(log.toPath(), StandardOpenOption.READ);
            BufferedReader lineReader = new BufferedReader(new InputStreamReader(is, StandardCharsets.UTF_8));
            while (true) {
                String line = lineReader.readLine();
                if (line == null) { // TODO blocking file reads are apparently really difficult, is this the best
                    // solution?
                    Thread.sleep(250);
                    continue;
                }
                if (line != null) {
                    strings.put(line); // can be interrupted
                }
            }
        } catch (IOException | InterruptedException e) {
            logger.warn(e);
        }
        logger.debug("Exiting run() method.");
    }
}
