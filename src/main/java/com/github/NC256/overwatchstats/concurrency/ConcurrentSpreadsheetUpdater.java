package com.github.NC256.overwatchstats.concurrency;

import com.github.NC256.overwatchstats.gamedata.GameMatch;
import com.github.NC256.overwatchstats.spreadsheets.SpreadsheetHQ;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;

public class ConcurrentSpreadsheetUpdater implements Runnable{

    final GameMatch match;
    int transmitIntervalMilliseconds;
    private final Logger logger = LogManager.getLogger(this);


    public ConcurrentSpreadsheetUpdater (GameMatch match, int transmitIntervalMilliseconds){
        this.match = match;
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
                    SpreadsheetHQ.collectAndTransmit(match);
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
