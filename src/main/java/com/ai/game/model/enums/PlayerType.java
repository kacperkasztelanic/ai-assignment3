package com.ai.game.model.enums;

public enum PlayerType {

    HUMAN(false), MAX_POINT_GAP_AND_CENTER(true), MAX_POINT_GAP_AND_EDGES(true), MAX_POINT_GAP_AND_SEQ(true), RANDOM(
            false);

    private final boolean aiPlayer;

    private PlayerType(boolean aiPlayer) {
        this.aiPlayer = aiPlayer;
    }

    public boolean isAiPlayer() {
        return aiPlayer;
    }
}
