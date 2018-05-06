package com.ai.game.model;

public class Turn {

    private int row;
    private int column;

    public static Turn of(int row, int column) {
        return new Turn(row, column);
    }

    public Turn(int row, int column) {
        this.row = row;
        this.column = column;
    }

    public int getRow() {
        return row;
    }

    public void setRow(int row) {
        this.row = row;
    }

    public int getColumn() {
        return column;
    }

    public void setColumn(int column) {
        this.column = column;
    }

    @Override
    public String toString() {
        return String.format("Turn(%d, %d)", row, column);
    }
}
