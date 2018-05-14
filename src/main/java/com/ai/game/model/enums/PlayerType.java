package com.ai.game.model.enums;

public enum PlayerType {

    HUMAN(false), MAX_POINTS_GAP_AND_CENTER(true), MAX_POINTS_GAP_AND_EDGES(true), NOT_LAST_BUT_ONE_IN_LINE_AND_RSEQ(true);

    private final boolean aiPlayer;

    private PlayerType(boolean aiPlayer) {
        this.aiPlayer = aiPlayer;
    }

    public boolean isAiPlayer() {
        return aiPlayer;
    }
}
