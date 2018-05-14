package com.ai.game.model.enums;

public enum SameValuesBehavior {

    DEFAULT(0), RANDOM(1);

    private final int code;

    private SameValuesBehavior(int code) {
        this.code = code;
    }

    public int toInt() {
        return code;
    }

    public static SameValuesBehavior fromInt(int code) {
        switch (code) {
        case 1:
            return SameValuesBehavior.RANDOM;
        case 0:
        default:
            return SameValuesBehavior.DEFAULT;
        }
    }
}
