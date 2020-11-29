package com.github.line.sheduleupdateapi.apachepoi;

import com.github.line.sheduleupdateapi.apachepoi.container.MergedCell;
import com.github.line.sheduleupdateapi.apachepoi.container.SheetContentHolder;
import org.apache.poi.ss.usermodel.*;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.logging.Logger;
import java.util.stream.Collectors;

public final class WorkbookHelper {
    private static final Logger logger = Logger.getLogger(WorkbookHelper.class.getName());
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

    public static List<String> getCellContent(List<Cell> cells) {
        DataFormatter df = new DataFormatter();
        return cells.stream()
                    .map(df::formatCellValue)
                    .collect(Collectors.toList());
    }

    public static Queue<MergedCell> getAddressAndRangeMergedCellsInColumn(Sheet sheet, int columnIndex) {
        return sheet.getMergedRegions().stream()
                .filter(cellAddress ->
                        columnIndex == cellAddress.getFirstColumn())
                .map(cellAddress -> {
                    return new MergedCell(cellAddress.getFirstRow(),
                            cellAddress.getLastColumn() - cellAddress.getFirstColumn() + 1,
                            cellAddress.getLastRow() - cellAddress.getFirstRow() + 1); })
                .collect(Collectors.toCollection(ConcurrentLinkedQueue::new));
    }

    public static List<List<String>> splitMergedCells(SheetContentHolder content) {
        content.getMergedCellsInColumn(0).stream().forEach(
                mergedCell -> {
                    //logger.info("Length: " + String.valueOf(mergedCell.getLength()));
                    if (mergedCell.getLength() > 0) {
                        int width = mergedCell.getWidth();
                        int rowId = mergedCell.getRowIndex();
                        String cellContent = content.getColumnCellContent(0).get(rowId);
                        //logger.info("Width: " + width + " RowId: " + rowId + " Content: " + cellContent);
                        if (width == 4) {
                            content.setCellContent(1, rowId, cellContent);
                            content.setCellContent(2, rowId, cellContent);
                            content.setCellContent(3, rowId, cellContent);
                        } else if (width == 2) {
                            content.setCellContent(1, rowId, cellContent);
                        }
                    }
                }
        );

        content.getMergedCellsInColumn(2).stream().forEach(
                mergedCell -> {
                    if (mergedCell.getLength() > 0) {
                        int rowId = mergedCell.getRowIndex();
                        if (mergedCell.getWidth() == 2) {
                            content.setCellContent(3, rowId, content.getColumnCellContent(2).get(rowId));
                        }
                    }
                }
        );
        return content.getColumnsContentCollection();
    }

}
