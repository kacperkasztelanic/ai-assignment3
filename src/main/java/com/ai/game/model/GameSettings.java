package com.ai.game.model;

import com.ai.game.model.enums.PlayerType;

public class GameSettings {

    private int boardSize;
    private int player1TreeDepth;
    private int player2TreeDepth;
    private PlayerType player1Type;
    private PlayerType player2Type;
    private boolean player1AlphaBetaPruning;
    private boolean player2AlphaBetaPruning;

    public GameSettings() {
    }

    public GameSettings(int boardSize, int player1TreeDepth, int player2TreeDepth, PlayerType player1Type,
            PlayerType player2Type, boolean player1AlphaBetaPruning, boolean player2AlphaBetaPruning) {
        this.boardSize = boardSize;
        this.player1TreeDepth = player1TreeDepth;
        this.player1Type = player1Type;
        this.player2Type = player2Type;
        this.player1AlphaBetaPruning = player1AlphaBetaPruning;
        this.player2AlphaBetaPruning = player2AlphaBetaPruning;
    }

    public int getBoardSize() {
        return boardSize;
    }

    public void setBoardSize(int boardSize) {
        this.boardSize = boardSize;
    }

    public int getPlayer1TreeDepth() {
        return player1TreeDepth;
    }

    public void setPlayer1TreeDepth(int player1TreeDepth) {
        this.player1TreeDepth = player1TreeDepth;
    }

    public int getPlayer2TreeDepth() {
        return player2TreeDepth;
    }

    public void setPlayer2TreeDepth(int player2TreeDepth) {
        this.player2TreeDepth = player2TreeDepth;
    }

    public PlayerType getPlayer1Type() {
        return player1Type;
    }

    public void setPlayer1Type(PlayerType player1Type) {
        this.player1Type = player1Type;
    }

    public PlayerType getPlayer2Type() {
        return player2Type;
    }

    public void setPlayer2Type(PlayerType player2Type) {
        this.player2Type = player2Type;
    }

    public boolean isPlayer1AlphaBetaPruning() {
        return player1AlphaBetaPruning;
    }

    public void setPlayer1AlphaBetaPruning(boolean player1AlphaBetaPruning) {
        this.player1AlphaBetaPruning = player1AlphaBetaPruning;
    }

    public boolean isPlayer2AlphaBetaPruning() {
        return player2AlphaBetaPruning;
    }

    public void setPlayer2AlphaBetaPruning(boolean player2AlphaBetaPruning) {
        this.player2AlphaBetaPruning = player2AlphaBetaPruning;
    }

    @Override
    public String toString() {
        return "GameSettings [boardSize=" + boardSize + ", player1TreeDepth=" + player1TreeDepth + ", player2TreeDepth="
                + player2TreeDepth + ", player1Type=" + player1Type + ", player2Type=" + player2Type
                + ", player1AlphaBetaPruning=" + player1AlphaBetaPruning + ", player2AlphaBetaPruning="
                + player2AlphaBetaPruning + "]";
    }
}
