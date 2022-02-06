package com.github.NC256.overwatchstats.spreadsheets;

import com.github.NC256.overwatchstats.gamedata.GameMatch;
import com.github.NC256.overwatchstats.gamedata.Player;
import com.google.api.services.sheets.v4.model.UpdateValuesResponse;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.util.List;

public class SpreadsheetInstance {

    private static final Logger logger = LogManager.getLogger(SpreadsheetInstance.class);

    private String spreadsheetID;
    private String worksheetname;
    private String range;

    public SpreadsheetInstance(String spreadsheetID, String worksheetName, String range){
        this.spreadsheetID = spreadsheetID;
        this.worksheetname = worksheetName;
        this.range = range;
        if (range.charAt(0) != '!'){ //TODO parse elsewhere
            this.range = "!" + range;
        }
    }

    public void collectAndTransmit(GameMatch match) throws IOException {
        List<List<Object>> data = SpreadsheetUtils.constructShapedList(7,12);

        List<Player> players = match.getPlayers();
        try {
            for (int i = 0; i < 12; i++) {
                data.get(0).add(i, players.get(i).getName());
                data.get(1).add(i, players.get(i).getTotalKills());
                data.get(2).add(i, players.get(i).getTotalDeaths());
                data.get(3).add(i, players.get(i).getTotalFinalBlows());
                data.get(4).add(i, Math.round(players.get(i).getTotalDamageDone()));
                data.get(5).add(i, Math.round(players.get(i).getTotalHealingDone()));
            }
            data.get(6).add(0, System.currentTimeMillis());
        }
        catch (Exception e){
            logger.error("Error packing data for spreadsheet transmission!");
            logger.error(e);
            return;
        }
        TalkToGoogle.sendStats(spreadsheetID, worksheetname+range,data);
    }

    public void testTransmission() throws IOException {
        List<List<Object>> testData = SpreadsheetUtils.constructShapedList(3,3);
        for (List<Object> list : testData) {
            for (Object o : list) {
                o = "TEST";
            }
        }
        UpdateValuesResponse uvr = TalkToGoogle.sendStats(spreadsheetID, range, testData);
    }


    public String getSpreadsheetID() {
        return spreadsheetID;
    }

    public void setSpreadsheetID(String spreadsheetID) {
        this.spreadsheetID = spreadsheetID;
    }

    public String getWorksheetname() {
        return worksheetname;
    }

    public void setWorksheetname(String worksheetname) {
        this.worksheetname = worksheetname;
    }

    public String getRange() {
        return range;
    }

    public void setRange(String range) {
        this.range = range;
    }
}
