package com.ai.game.terminal;

public enum Strategy {

    MaximalizeDifferencePoints(0), NotLastButOneInLine(1);

    private final int code;

    private Strategy(int code) {
        this.code = code;
    }

    public int toInt() {
        return code;
    }

    public static Strategy fromInt(int code) {
        switch (code) {
        case 1:
            return Strategy.NotLastButOneInLine;
        case 0:
        default:
            return Strategy.MaximalizeDifferencePoints;
        }
    }
}