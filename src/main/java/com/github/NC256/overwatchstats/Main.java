package com.github.NC256.overwatchstats;

import com.github.NC256.overwatchstats.concurrency.ConcurrentFileReader;
import com.github.NC256.overwatchstats.concurrency.ConcurrentFileWatcher;
import com.github.NC256.overwatchstats.concurrency.ConcurrentLogParser;
import com.github.NC256.overwatchstats.concurrency.ConcurrentSpreadsheetUpdater;
import com.github.NC256.overwatchstats.gamedata.CanonicalGameData;
import com.github.NC256.overwatchstats.gamedata.CanonicalHero;
import com.github.NC256.overwatchstats.gamedata.CanonicalMode;
import com.github.NC256.overwatchstats.gamedata.GameMatch;
import com.github.NC256.overwatchstats.gamedata.ParseHeroData;
import com.github.NC256.overwatchstats.gamedata.ParseModeData;
import com.github.NC256.overwatchstats.spreadsheets.SpreadsheetInstance;
import com.github.NC256.overwatchstats.spreadsheets.TalkToGoogle;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.core.config.Configurator;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.LinkedBlockingQueue;

public class Main {

    private static final Logger logger = LogManager.getLogger(Main.class);

    public static void main(String[] args) throws IOException, InterruptedException, GeneralSecurityException {

        //TODO if the INITIAL log message is not the first message, then prior messages could be parsed before
        // player names have been ingested, leading to errors when trying to parse ANYTHING

        Configurator.setRootLevel(Level.WARN);

        logger.trace("Trace Message!");
        logger.debug("Debug Message!");
        logger.info("Info Message!");
        logger.warn("Warn Message!");
        logger.error("Error Message!");
        logger.fatal("Fatal Message!");
        System.out.println("Logger should have already logged");

        boolean googleSheetsLive = true; // Transmit to Google Sheets
        if (googleSheetsLive){
            TalkToGoogle.initalize();
        }

        if (!new File("properties.properties").exists()) {
            generatePropertiesFile();
            logger.fatal("Properties file not found, generating one. Please set it's values now.");
            System.exit(-1);
        }
        Properties preferences = loadAndValidateProperties();

        List<CanonicalHero> heroData = ParseHeroData.parse(); // Canonical game reference data
        if (heroData == null){
            logger.fatal("Unable to parse hero reference data.");
            System.exit(-1);
        }

        List<CanonicalMode> modeData = ParseModeData.parse();
        if (modeData == null){
            logger.fatal("Unable to parse mode reference data.");
            System.exit(-1);
        }

        boolean liveMode = true; // Always automatically switch to newly created files.


        //File logDirectory = new File("C:\\Users\\" + System.getProperty("user.home") + "\\Documents\\Overwatch\\Workshop\\"); // where the files are
        //File logDirectory = new File("C:\\Users\\Nicholas\\Documents\\Overwatch\\Workshop\\"); // where the files are
        File logDirectory = new File(preferences.getProperty("Overwatch_Log_Directory"));
        File latestLog;
        LinkedBlockingQueue<String> logStrings = new LinkedBlockingQueue<>();
        GameMatch currentMatch = new GameMatch(new CanonicalGameData(heroData, modeData));


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
            SpreadsheetInstance sheetInstance = new SpreadsheetInstance(preferences.getProperty("Google_Sheet_ID"),
                    preferences.getProperty("Google_Sheet_Current_Worksheet"),
                    preferences.getProperty("Google_Sheet_Insertion_Cell"));
            ConcurrentSpreadsheetUpdater spreadsheetUpdater = new ConcurrentSpreadsheetUpdater(currentMatch, sheetInstance,5000);
            Thread spreadsheetThread = new Thread(spreadsheetUpdater);
            logger.debug("Starting thread for spreadsheet Communication");
            spreadsheetThread.start();

            while (!watcher.hasEventOccurred()){ // While no new file has appeared on sensors
                // threads are doing everything for us now...

                //TODO Thread overhaul with a Pool and/or Futures of some kind along with error handling. Something to keep Main from busy waiting
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

    static void generatePropertiesFile () throws IOException {
        Properties preferences = new Properties();
        preferences.setProperty("Google_Sheet_ID","");
        preferences.setProperty("Google_Sheet_Current_Worksheet", "Sheet1");
        preferences.setProperty("Google_Sheet_Insertion_Cell", "B2");
        preferences.setProperty("Overwatch_Log_Directory", "C:\\Users\\USERNAME\\Documents\\Overwatch\\Workshop\\");
        preferences.store(new FileWriter(new File("properties.properties")), "Documentation on Github.");
        return;
    }

    static Properties loadAndValidateProperties() throws IOException {
        Properties preferences = new Properties();
        preferences.load(new FileReader(new File("properties.properties")));
        if (preferences.stringPropertyNames().size() != 4){
            logger.fatal("Invalid number of properties, is properties file out of date? Perhaps delete it and let the program generate a new one.");
            System.exit(-1);
        }
        if (!new File(preferences.getProperty("Overwatch_Log_Directory")).exists()){
            logger.fatal("Cannot find that log directory! Check your formatting.");
            System.exit(-1);
        }
        //TODO how to test API communication is working?
        return preferences;
    }


}


