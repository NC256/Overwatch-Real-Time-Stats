package com.github.NC256.overwatchstats.spreadsheets;

import java.util.ArrayList;
import java.util.List;

public class SpreadsheetUtils {

    private static List<List<Object>> constructList(int rowCount) {
        List<List<Object>> megaList = new ArrayList<>();
        for (int i = 0; i < rowCount; i++) {
            megaList.add(new ArrayList<>());
        }
        return megaList;
    }

    public static List<List<Object>> constructShapedList(int rowcount, int columnCount) {
        List<List<Object>> megalist = constructList(rowcount);
        for (List<Object> objects : megalist) {
            for (int i = 0; i < columnCount; i++) {
                objects.add(""); // empty cells need empty strings in them
            }
        }
        return megalist;
    }
}
