package com.github.line.sheduleupdateapi.apache;

import javafx.util.Pair;
import org.apache.poi.ss.usermodel.*;

import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

import static org.apache.poi.ss.usermodel.CellType.*;
import static org.apache.poi.ss.usermodel.CellType.NUMERIC;

public final class WorkbookHelper {
    private WorkbookHelper() {
        throw new AssertionError();
    }

    //works
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

    //works
    public static List<Cell> getColumnFromSheet(Sheet sheet, int columnIndex) {
        List<Cell> column = new ArrayList<>();

        for (Row row: sheet) {
            column.add(row.getCell(columnIndex));
        }

        return column;
    }

    //works
    public static List<String> getCellContent(List<Cell> cells) {
        DataFormatter df = new DataFormatter();
        List<String> cellContent = new ArrayList<>();

        for (Cell cell: cells) {
            cellContent.add(df.formatCellValue(cell));
        }

        return cellContent;
    }

    public static Map<Integer, Pair<Integer, Integer>> getAddressAndRangeMergedCellsInColumn(Sheet sheet, int columnIndex) {
        return sheet.getMergedRegions().stream()
                .filter(cellAddress ->
                        columnIndex == cellAddress.getFirstColumn())
                .collect(Collectors.toMap(
                        cellAddress -> cellAddress.getFirstRow(),
                        cellAddress -> new Pair<>(cellAddress.getLastRow() - cellAddress.getFirstRow() + 1,
                                cellAddress.getLastColumn() - cellAddress.getFirstColumn() + 1)
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
            if(length > 0) {
                int width = group1.getKey().get(rowIndex).getValue();
                String cellContent = group1.getValue().get(rowIndex);

                if (width == 4) {
                    group2.getValue().set(rowIndex, cellContent);
                    group3.getValue().set(rowIndex, cellContent);
                    group4.getValue().set(rowIndex, cellContent);
                } else if (width == 2) {
                    group2.getValue().set(rowIndex, cellContent);
                }
            }
        }

        //splitting values between lists (3 column)
        for (Integer rowIndex: group3.getKey().keySet()) {
            int length = group3.getKey().get(rowIndex).getKey();
            if(length > 0) {
                int width = group3.getKey().get(rowIndex).getValue();
                String cellContent = group3.getValue().get(rowIndex);

                if (width == 2) {
                    group4.getValue().set(rowIndex, cellContent);
                }
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
