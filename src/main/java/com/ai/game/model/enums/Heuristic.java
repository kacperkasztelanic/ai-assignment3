package com.ai.game.model.enums;

import com.ai.game.terminal.MoveOrderType;

public enum Heuristic {

    DEFAULT(0), PREFER_BOARD_CENTER(1), PREFER_BOARD_EDGES(2);

    private final int code;

    private Heuristic(int code) {
        this.code = code;
    }

    public int toInt() {
        return code;
    }

    public static MoveOrderType fromInt(int code) {
        switch (code) {
        case 1:
            return MoveOrderType.PreferMiddleOfBoard;
        case 2:
            return MoveOrderType.PreferBordersOfBoard;
        case 0:
        default:
            return MoveOrderType.Default;
        }
    }
}
