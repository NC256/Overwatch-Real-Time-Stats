package com.github.NC256.overwatchstats.spreadsheets;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.List;

/**
 * Parses a spreadsheet and maps text input to its data values
 */
public class SpreadsheetLayoutParser {
    public SpreadsheetLayout parseSpreadsheet (File f) throws IOException {
        String file = new String(Files.readAllBytes(f.toPath()), StandardCharsets.UTF_8);
        String[] rows = file.split("\\R");
        String[][] cells = new String[rows.length][];
        int columnCount = 0;
        for (int i = 0; i < rows.length; i++) {
            cells[i] = rows[i].split(",");
            if (cells[i].length > columnCount){
                columnCount = cells[i].length;
            }
        }
        List<List<Object>> data = SpreadsheetUtils.constructShapedList(rows.length,columnCount);

        for (int i = 0; i < cells.length; i++) {
            for (int j = 0; j < cells[i].length; j++) {
                //TODO parse every cell
            }
        }

        return null;
    }
    
}
