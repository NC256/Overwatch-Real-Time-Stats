package com.github.NC256.overwatchstats.spreadsheets;

import com.github.NC256.overwatchstats.gamedata.GameMatch;
import com.github.NC256.overwatchstats.gamedata.Player;

import java.io.IOException;
import java.util.List;

public class SpreadsheetHQ {

    public static final String spreadsheetID = "1iWptCYpfXHBQk6ywsYMzx0kdGqrfIj_pbXlePLtLpFQ";
    public static final String sheetName = "Sheet1";
    public static final String range = "!B2";

    public static void collectAndTransmit(GameMatch match) throws IOException {
        List<List<Object>> data = SpreadsheetUtils.constructShapedList(4,12);

        List<Player> players = match.getPlayers();
        try {
            for (int i = 0; i < 12; i++) {
                data.get(0).add(i, players.get(i).getName());
                data.get(1).add(i, players.get(i).getTotalKills());
                data.get(2).add(i, players.get(i).getTotalDeaths());
            }
            data.get(3).add(0, System.currentTimeMillis());
        }
        catch (Exception e){
            System.out.println("Error packing data for spreadsheet!");
            System.out.println(e);
            return;
        }
        TalkToGoogle.sendStats(spreadsheetID, sheetName+range,data);
    }
}
