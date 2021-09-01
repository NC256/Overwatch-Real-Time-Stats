package com.github.NC256.overwatchstats.concurrency;

import com.github.NC256.overwatchstats.gamedata.GameMatch;
import com.github.NC256.overwatchstats.logs.LogMessage;
import com.github.NC256.overwatchstats.logs.LogMessageParser;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.concurrent.ConcurrentLinkedQueue;

public class ConcurrentLogParser implements Runnable{

    private final ConcurrentLinkedQueue<String> strings;
    private final GameMatch match;
    private final Logger logger = LogManager.getLogger(this);


    public ConcurrentLogParser(ConcurrentLinkedQueue<String> strings, GameMatch match){
        logger.info("Instantiated with queue and GameMatch instance.");
        this.strings = strings;
        this.match = match;
    }

    public ConcurrentLogParser(ConcurrentLinkedQueue<String> strings){
        logger.info("Instantiated with one queue.");
        this.strings = strings;
        this.match = new GameMatch();
    }

    public GameMatch getGameInstance (){
        return match;
    }

    @Override
    public void run() {
        logger.debug("Entering run()");
        String line = null;
        LogMessage message = null;
        while(true){
            //TODO this line is causing high CPU usage, responsible for ~99% of the program's total CPU cycles
            // during one profiler run - need some kind of blocking queue?
            line = strings.poll();
            if (line != null){
                message = LogMessageParser.parseLine(line);
                if (message != null){
                    synchronized (match){ // Need to make sure nothing is reading data while we are in the middle of writing it
                        message.parseStats(match);
                    }
                }
            }
            if (Thread.currentThread().isInterrupted()){
                logger.debug("Received Interrupt!");
                return;
            }
        }
    }
}
