package com.kasztelanic.ai.assignment3.model;

import com.kasztelanic.ai.assignment3.model.enums.PlayerType;

public class GameSettings {

    private int boardSize;
    private int treeDepth;
    private PlayerType player1;
    private PlayerType player2;
    private boolean alphaBetaPruning;

    public GameSettings() {
    }

    public GameSettings(int boardSize, int treeDepth, PlayerType player1, PlayerType player2,
            boolean alphaBetaPruning) {
        this.boardSize = boardSize;
        this.treeDepth = treeDepth;
        this.player1 = player1;
        this.player2 = player2;
        this.alphaBetaPruning = alphaBetaPruning;
    }

    public int getBoardSize() {
        return boardSize;
    }

    public void setBoardSize(int boardSize) {
        this.boardSize = boardSize;
    }

    public int getTreeDepth() {
        return treeDepth;
    }

    public void setTreeDepth(int treeDepth) {
        this.treeDepth = treeDepth;
    }

    public PlayerType getPlayer1() {
        return player1;
    }

    public void setPlayer1(PlayerType player1) {
        this.player1 = player1;
    }

    public PlayerType getPlayer2() {
        return player2;
    }

    public void setPlayer2(PlayerType player2) {
        this.player2 = player2;
    }

    public boolean isAlphaBetaPruning() {
        return alphaBetaPruning;
    }

    public void setAlphaBetaPruning(boolean alphaBetaPruning) {
        this.alphaBetaPruning = alphaBetaPruning;
    }

    @Override
    public String toString() {
        return "GameSettings [boardSize=" + boardSize + ", treeDepth=" + treeDepth + ", player1=" + player1
                + ", player2=" + player2 + ", alphaBetaPruning=" + alphaBetaPruning + "]";
    }
}
