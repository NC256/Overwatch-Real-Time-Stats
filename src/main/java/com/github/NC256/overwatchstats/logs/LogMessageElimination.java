package com.github.NC256.overwatchstats.logs;

import com.github.NC256.overwatchstats.gamedata.GameMatch;

import java.util.Scanner;

public class LogMessageElimination extends LogMessage{
    String attacker;
    String receiver;

    protected LogMessageElimination(int millisecondsSinceMatchStart, LogMessageType eventType, Scanner remainingLine) {
        super(millisecondsSinceMatchStart, eventType);
        attacker = remainingLine.next();
        receiver = remainingLine.next();
    }

    @Override
    public void parseStats(GameMatch match) {
        match.getPlayer(attacker).incrementEliminations();
    }
}
