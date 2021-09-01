package com.github.NC256.overwatchstats.concurrency;

import com.github.NC256.overwatchstats.gamedata.GameMatch;
import com.github.NC256.overwatchstats.spreadsheets.SpreadsheetInstance;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;

public class ConcurrentSpreadsheetUpdater implements Runnable{

    final GameMatch match;
    final SpreadsheetInstance sheet;
    int transmitIntervalMilliseconds;
    private final Logger logger = LogManager.getLogger(this);


    public ConcurrentSpreadsheetUpdater (GameMatch match, SpreadsheetInstance sheet, int transmitIntervalMilliseconds){
        this.match = match;
        this.sheet = sheet;
        this.transmitIntervalMilliseconds = transmitIntervalMilliseconds;
    }

    //TODO might be better to use a ScheduledExecutorService?
    // Seems fine for a minimum viable though
    @Override
    public void run() {
        logger.debug("Entering run()");
        while (true) {
            try {
                Thread.sleep(transmitIntervalMilliseconds);
                synchronized (match){
                    sheet.collectAndTransmit(match);
                }
            }
            catch (InterruptedException e){
                logger.warn(e);
                return;
            }
            catch (IOException ioException){
                logger.error("Unable to transmit to spreadsheet");
                logger.error(ioException);
            }
        }
    }
}
