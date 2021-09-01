package com.github.NC256.overwatchstats.logs;

import com.github.NC256.overwatchstats.gamedata.GameMatch;

import java.util.Scanner;

public abstract class LogMessage {


    private int millisecondsSinceMatchStart;
    private LogMessageType eventType;
    private Scanner remainingLine;

    /**
     * Updates the GameMatch object with the new data contained in this LogMessage Object
     * @param match
     */
    public abstract void parseStats (GameMatch match);

    protected LogMessage(int millisecondsSinceMatchStart, LogMessageType eventType){
        this.millisecondsSinceMatchStart = millisecondsSinceMatchStart;
        this.eventType = eventType;
    }

    public int getMillisecondsSinceMatchStart() {
        return millisecondsSinceMatchStart;
    }

    public void setMillisecondsSinceMatchStart(int millisecondsSinceMatchStart) {
        this.millisecondsSinceMatchStart = millisecondsSinceMatchStart;
    }

    public LogMessageType getEventType() {
        return eventType;
    }

    public void setEventType(LogMessageType eventType) {
        this.eventType = eventType;
    }

    public Scanner getRemainingLine() {
        return remainingLine;
    }

    public void setRemainingLine(Scanner remainingLine) {
        this.remainingLine = remainingLine;
    }
}
