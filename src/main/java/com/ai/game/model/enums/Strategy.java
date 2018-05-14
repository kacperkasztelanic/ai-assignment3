package com.ai.game.model.enums;

public enum Strategy {

    MAXIMIZE_POINTS_GAP(0), NOT_LAST_BUT_ONE_IN_LINE(1);

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
            return Strategy.NOT_LAST_BUT_ONE_IN_LINE;
        case 0:
        default:
            return Strategy.MAXIMIZE_POINTS_GAP;
        }
    }
}
