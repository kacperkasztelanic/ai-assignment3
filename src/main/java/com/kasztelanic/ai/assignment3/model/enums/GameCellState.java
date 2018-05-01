package com.kasztelanic.ai.assignment3.model.enums;

public enum GameCellState {

    EMPTY(0), Player1(1), Player2(2);

    private final int code;

    private GameCellState(int code) {
        this.code = code;
    }

    public int toInt() {
        return code;
    }

    public static GameCellState fromInt(int code) {
        switch (code) {
        case 1:
            return GameCellState.Player1;
        case 2:
            return GameCellState.Player2;
        case 0:
        default:
            return GameCellState.EMPTY;
        }
    }
}