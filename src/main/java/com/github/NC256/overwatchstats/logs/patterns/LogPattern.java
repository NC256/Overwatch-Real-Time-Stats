package com.github.NC256.overwatchstats.logs.patterns;

import com.github.NC256.overwatchstats.gamedata.GameMatch;
import com.github.NC256.overwatchstats.logs.LogPatternType;

public abstract class LogPattern {


    private final int millisecondsSinceMatchStart;
    private final LogPatternType messageType;
    private String messageText;

    protected LogPattern(int millisecondsSinceMatchStart, LogPatternType messageType){
        this.millisecondsSinceMatchStart = millisecondsSinceMatchStart;
        this.messageType = messageType;
    }


    /**
     * Updates the GameMatch object with the new data contained in this LogMessage Object
     * @param match
     */
    public abstract void updateStats (GameMatch match);

    public String getMessageText() {
        return messageText;
    }

    public int getMillisecondsSinceMatchStart() {
        return millisecondsSinceMatchStart;
    }

    public LogPatternType getMessageType() {
        return messageType;
    }

}
