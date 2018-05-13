package com.ai.game.terminal;

public enum MoveIfSameValues {

    Default(0), Random(1);

    private final int code;

    private MoveIfSameValues(int code) {
        this.code = code;
    }

    public int toInt() {
        return code;
    }

    public static MoveIfSameValues fromInt(int code) {
        switch (code) {
        case 1:
            return MoveIfSameValues.Random;
        case 0:
        default:
            return MoveIfSameValues.Default;
        }
    }
}