package com.github.line.sheduleupdateapi.apache;

import javafx.util.Pair;
import org.apache.poi.ss.usermodel.*;

import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

public final class WorkbookHelper {
    private WorkbookHelper() {
        throw new AssertionError();
    }

    public static Optional<Sheet> getSheetFromXlsFile(File file, int sheetIndex) {
        try {
            Workbook workbook = WorkbookFactory.create(file);
            Sheet sheet = workbook.getSheetAt(sheetIndex);
            return Optional.of(sheet);
        } catch (IOException e) {
            e.printStackTrace();
            return Optional.empty();
        }
    }

    public static List<Cell> getColumnFromSheet(Sheet sheet, int columnIndex) {
        List<Cell> column = new ArrayList<>();

        for (Row row: sheet) {
            column.add(row.getCell(columnIndex));
        }

        return column;
    }

    public static List<String> getCellContent(Iterable<Cell> cells) {
        List<String> cellContent = new ArrayList<>();

        for (Cell cell: cells) {
            cellContent.add(cell.getStringCellValue());
        }

        return cellContent;
    }

    public static Map<Integer, Pair<Integer, Integer>> getAddressAndRangeMergedCellsInColumn(Sheet sheet, int columnIndex) {
         return sheet.getMergedRegions().stream()
                .filter(cellAddress -> columnIndex == cellAddress.getFirstColumn())
                .collect(Collectors.toMap(
                        cellAddresses -> cellAddresses.getFirstRow(),
                        cellAddresses -> new Pair<>(cellAddresses.getLastRow() - cellAddresses.getFirstRow()+1,
                                                    cellAddresses.getLastColumn() - cellAddresses.getFirstColumn()+1)
                ));
    }

    public static List<List<String>> splitMergedCells( List<Pair<Map<Integer, Pair<Integer, Integer>>, List<String>>>  addressRangeContent) {
        //groups extraction.

            //  <<row index, <length, width>>, <cells content from single column>>
            Pair<Map<Integer, Pair<Integer, Integer>>, List<String>> group1 = addressRangeContent.get(0);
            Pair<Map<Integer, Pair<Integer, Integer>>, List<String>> group2 = addressRangeContent.get(1);
            Pair<Map<Integer, Pair<Integer, Integer>>, List<String>> group3 = addressRangeContent.get(2);
            Pair<Map<Integer, Pair<Integer, Integer>>, List<String>> group4 = addressRangeContent.get(3);


        //splitting values between lists (1 column)
        for (Integer rowIndex: group1.getKey().keySet()) {
            int length = group1.getKey().get(rowIndex).getKey();
            while(length > 0) {
                //
                int width = group1.getKey().get(rowIndex).getValue();
                String cellContent = group1.getValue().get(rowIndex);

                if (width == 4) {
                    group2.getValue().add(rowIndex, cellContent);
                    group3.getValue().add(rowIndex, cellContent);
                    group4.getValue().add(rowIndex, cellContent);
                } else if (width == 2) {
                    group2.getValue().add(rowIndex, cellContent);
                }
                //
                rowIndex++;
                length--;
            }
        }

        //splitting values between lists (3 column)
        for (Integer rowIndex: group3.getKey().keySet()) {
            int length = group3.getKey().get(rowIndex).getKey();
            while(length > 0) {
                //
                int width = group3.getKey().get(rowIndex).getValue();
                String cellContent = group3.getValue().get(rowIndex);

                if (width == 2) {
                    group4.getValue().add(rowIndex, cellContent);
                }
                //
                rowIndex++;
                length--;
            }
        }

        List<List<String>> splittedCellsContent = new ArrayList<>();
        splittedCellsContent.add(group1.getValue());
        splittedCellsContent.add(group2.getValue());
        splittedCellsContent.add(group3.getValue());
        splittedCellsContent.add(group4.getValue());

        return Collections.unmodifiableList(splittedCellsContent);
    }
}
