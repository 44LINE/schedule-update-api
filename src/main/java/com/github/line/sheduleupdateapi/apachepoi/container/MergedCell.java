package com.github.line.sheduleupdateapi.apachepoi.container;

public class MergedCell {
    private final int rowIndex;
    private final int width;
    private final int length;

    public MergedCell(int rowIndex, int width, int length) {
        this.rowIndex = rowIndex;
        this.width = width;
        this.length = length;
    }

    public int getRowIndex() {
        return rowIndex;
    }

    public int getWidth() {
        return width;
    }

    public int getLength() {
        return length;
    }
}
