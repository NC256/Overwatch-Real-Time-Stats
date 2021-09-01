package com.github.NC256.overwatchstats;

import com.github.NC256.overwatchstats.concurrency.ConcurrentFileReader;
import com.github.NC256.overwatchstats.concurrency.ConcurrentFileWatcher;
import com.github.NC256.overwatchstats.concurrency.ConcurrentLogParser;
import com.github.NC256.overwatchstats.concurrency.ConcurrentSpreadsheetUpdater;
import com.github.NC256.overwatchstats.gamedata.GameMatch;
import com.github.NC256.overwatchstats.spreadsheets.TalkToGoogle;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.core.config.Configurator;

import java.io.File;
import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.concurrent.ConcurrentLinkedQueue;

public class Main {

    private static final Logger logger = LogManager.getLogger(Main.class);

    public static void main(String[] args) throws IOException, InterruptedException, GeneralSecurityException {

        Configurator.setRootLevel(Level.ALL);

        logger.trace("Trace Message!");
        logger.debug("Debug Message!");
        logger.info("Info Message!");
        logger.warn("Warn Message!");
        logger.error("Error Message!");
        logger.fatal("Fatal Message!");
        System.out.println("Logger should have already logged");

        boolean liveMode = true; // Always automatically switch to newly created files.
        boolean googleSheetsLive = true; // Transmit to Google Sheets
        if (googleSheetsLive){
            TalkToGoogle.initalize();
        }

        //File logDirectory = new File("C:\\Users\\" + System.getProperty("user.home") + "\\Documents\\Overwatch\\Workshop\\"); // where the files are
        File logDirectory = new File("C:\\Users\\Nicholas\\Documents\\Overwatch\\Workshop\\"); // where the files are
        File latestLog;
        ConcurrentLinkedQueue<String> logStrings = new ConcurrentLinkedQueue<>();
        GameMatch currentMatch = new GameMatch();


        while (true) {
            // 1. Start the watcher so no files can slip through our net
            ConcurrentFileWatcher watcher = new ConcurrentFileWatcher(logDirectory.toPath());
            Thread watcherThread = new Thread(watcher);
            logger.debug("Starting Thread for ConcurrentFileWatcher");
            watcherThread.start();

            // 2. Manually find the latest file
            latestLog = getLatestLogFile(logDirectory);

            // 3. Setup parsing system
            ConcurrentFileReader reader = new ConcurrentFileReader(latestLog,logStrings);
            Thread readerThread = new Thread(reader);
            logger.debug("Starting Thread for ConcurrentFileReader");
            readerThread.start();

            ConcurrentLogParser parser = new ConcurrentLogParser(logStrings, currentMatch);
            Thread parserThread = new Thread(parser);
            logger.debug("Starting Thread for ConcurrentLogParser");
            parserThread.start();

            // 4. Setup spreadsheet access
            ConcurrentSpreadsheetUpdater spreadsheetUpdater = new ConcurrentSpreadsheetUpdater(currentMatch,10000);
            Thread spreadsheetThread = new Thread(spreadsheetUpdater);
            logger.debug("Starting thread for spreadsheet Communication");
            spreadsheetThread.start();

            while (!watcher.hasEventOccurred()){ // While no new file has appeared on sensors
                // threads are doing everything for us now...

                //TODO Main needs to know if any of the threads have crashed or encountered errors
                Thread.sleep(2000);
            }
            // New file must have appeared, lets reboot things
            logger.info("Interrupting all the threads.");
            watcherThread.interrupt();
            readerThread.interrupt();
            parserThread.interrupt();
            spreadsheetThread.interrupt();

            logger.info("Waiting for all threads to join()");
            watcherThread.join();
            readerThread.join();
            parserThread.join();
            spreadsheetThread.join();
            logger.info("Main loop restarting!");
        }
        //logger.info("Program exiting!");
    }

    static File getLatestLogFile (File logDirectory){
        File[] files = logDirectory.listFiles(File::isFile);
        long lastModifiedTime = Long.MIN_VALUE;
        File latest = null;

        if (files != null) {
            for (File file : files) {
                if (file.lastModified() > lastModifiedTime) {
                    latest = file;
                    lastModifiedTime = file.lastModified();
                }
            }
        }
        if (latest == null){
            System.out.println("Couldn't find the latest File.");
            System.exit(-1);
        }
        return latest;
    }

}


