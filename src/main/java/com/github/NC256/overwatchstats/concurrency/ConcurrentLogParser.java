package com.github.NC256.overwatchstats.concurrency;

import com.github.NC256.overwatchstats.gamedata.GameMatch;
import com.github.NC256.overwatchstats.logs.LogPatternParser;
import com.github.NC256.overwatchstats.logs.patterns.LogPattern;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.concurrent.LinkedBlockingQueue;

public class ConcurrentLogParser implements Runnable{

    private final LinkedBlockingQueue<String> strings;
    private final GameMatch match;
    private final Logger logger = LogManager.getLogger(this);


    public ConcurrentLogParser(LinkedBlockingQueue<String> strings, GameMatch match){
        logger.info("Instantiated with queue and GameMatch instance.");
        this.strings = strings;
        this.match = match;
    }

    public GameMatch getGameInstance (){
        return match;
    }

    @Override
    public void run() {
        logger.debug("Entering run()");
        try {
            String line = null;
            LogPattern message = null;
            while (true) {
                line = strings.take(); // can be interrupted
                message = LogPatternParser.parseLine(line);
                if (message != null){
                    synchronized (match){ // Need to make sure nothing is reading data while we are in the middle of writing it
                        try {
                            message.updateStats(match);
                        }
                        catch (Exception e){
                            logger.warn("Unable to update stats for the following: " + line);
                        }
                    }
                }
            }
        }
        catch (InterruptedException e){
            logger.warn(e);
        }
    }
}
