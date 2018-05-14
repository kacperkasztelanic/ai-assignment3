package com.ai.game.model.enums;

public enum GameCellState {

    EMPTY(0), PLAYER1(1), PLAYER2(2);

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
            return GameCellState.PLAYER1;
        case 2:
            return GameCellState.PLAYER2;
        case 0:
        default:
            return GameCellState.EMPTY;
        }
    }
}