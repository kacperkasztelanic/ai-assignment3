package com.ai.game.terminal;

public enum MoveOrderType {

    Default(0), PreferMiddleOfBoard(1), PreferBordersOfBoard(2), RandomIfSameValue(3);

    private final int code;

    private MoveOrderType(int code) {
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
        case 3:
        	return MoveOrderType.RandomIfSameValue;
        case 0:
        default:
            return MoveOrderType.Default;
        }
    }
}