package com.ai.game.model.enums;

public enum Heuristic {

    DEFAULT(0), PREFER_BOARD_CENTER(1), PREFER_BOARD_EDGES(2);

    private final int code;

    private Heuristic(int code) {
        this.code = code;
    }

    public int toInt() {
        return code;
    }

    public static Heuristic fromInt(int code) {
        switch (code) {
        case 1:
            return Heuristic.PREFER_BOARD_CENTER;
        case 2:
            return Heuristic.PREFER_BOARD_EDGES;
        case 0:
        default:
            return Heuristic.DEFAULT;
        }
    }
}
