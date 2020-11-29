package com.github.line.sheduleupdateapi.apachepoi.container;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Queue;
import java.util.logging.Logger;

public class SheetContentHolder {
    private final Logger logger = Logger.getLogger(this.getClass().getName());
    private SingleColumnPreSplitContainer[] columns = new SingleColumnPreSplitContainer[4];

    public void addColumnContent(Queue<MergedCell> mergedCellsStack, List<String> cellsContent, int groupId) {
        columns[groupId] = new SingleColumnPreSplitContainer(mergedCellsStack, cellsContent);
    }

    public Queue<MergedCell> getMergedCellsInColumn(int groupId) {
        return columns[groupId].getMergedCellsStack();
    }

    public List<String> getColumnCellContent(int groupId) {
        return columns[groupId].getCellsContent();
    }

    public void setCellContent(int groupId, int rowId, String cellContent) {
        //logger.info("Given: " + "groupId: " + groupId + " RowId: " + rowId + " Content: " + cellContent);
        columns[groupId].setCellContent(rowId, cellContent);
        //logger.info("Result: Content: " + columns[groupId].getCellContent(rowId));
    }

    public List<List<String>> getColumnsContentCollection() {
        return Collections.unmodifiableList(Arrays.asList(
            columns[0].getCellsContent(),
            columns[1].getCellsContent(),
            columns[2].getCellsContent(),
            columns[3].getCellsContent()));
    }

    private class SingleColumnPreSplitContainer {
        private final Queue<MergedCell> mergedCellsStack;
        private List<String> cellsContent;

        public SingleColumnPreSplitContainer(Queue<MergedCell> mergedCellsStack, List<String> cellsContent) {
            this.mergedCellsStack = mergedCellsStack;
            this.cellsContent = cellsContent;
        }

        public void setCellContent(int rowId, String cellContent) {
            cellsContent.set(rowId, cellContent);
        }

        public String getCellContent(int rowId) {
            return cellsContent.get(rowId);
        }

        public Queue<MergedCell> getMergedCellsStack() {
            return mergedCellsStack;
        }

        public List<String> getCellsContent() {
            return cellsContent;
        }
    }
}
