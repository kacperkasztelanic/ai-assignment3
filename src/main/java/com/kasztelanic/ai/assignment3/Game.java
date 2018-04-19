package com.kasztelanic.ai.assignment3;

public class Game {

    private int player1Points;
    private int player2Points;
    private boolean isEnd;
    private boolean player1Turn = true;
    private GameSettings gameSettings;

    public static Game newGame(GameSettings gameSettings) {
        return new Game(gameSettings);
    }

    public Game(GameSettings gameSettings) {
        this.gameSettings = gameSettings;
    }

    public int getPlayer1Points() {
        return player1Points;
    }

    public void setPlayer1Points(int player1Points) {
        this.player1Points = player1Points;
    }

    public int getPlayer2Points() {
        return player2Points;
    }

    public void setPlayer2Points(int player2Points) {
        this.player2Points = player2Points;
    }

    public boolean isEnd() {
        return isEnd;
    }

    public void setEnd(boolean isEnd) {
        this.isEnd = isEnd;
    }

    public boolean isPlayer1Turn() {
        return player1Turn;
    }

    public void setPlayer1Turn(boolean player1Turn) {
        this.player1Turn = player1Turn;
    }

    public GameSettings getGameSettings() {
        return gameSettings;
    }

    public void setGameSettings(GameSettings gameSettings) {
        this.gameSettings = gameSettings;
    }

    @Override
    public String toString() {
        return "GameState [player1Points=" + player1Points + ", player2Points=" + player2Points + ", isEnd=" + isEnd
                + ", player1Turn=" + player1Turn + ", gameSettings=" + gameSettings + "]";
    }

    public boolean isGameOver() {
        // TODO Auto-generated method stub
        return false;
    }

    public void boardUpdated() {
        player1Turn = !player1Turn;
        // TODO Auto-generated method stub
    }

    public void nextTurn() {
        // TODO Auto-generated method stub
    }
}
