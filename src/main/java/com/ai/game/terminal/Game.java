package com.ai.game.terminal;

import java.io.FileNotFoundException;
import java.util.Arrays;

public class Game {
    final static int EMPTY = 0;
    final static int PLAYER1 = 1;
    final static int PLAYER2 = 2;

    private int[][] board;
    private int depth1 = 1;
    private int depth2 = 1;
    private int tour = 0;
    private int movesToDo;
    private int movesDone = 0;
    private PairManager pairManager;
    private Player player1;
    private Player player2;

    public Game(int size, int depth1, int depth2) {
    	// default board value is 0, same as EMPTY
        board = new int[size][size];
        this.depth1 = depth1;
        this.depth2 = depth2;
        pairManager = new PairManager(size);
        movesToDo = size * size;
        setPlayers();
    }

    private void setPlayers() {
//         player1 = new HumanPlayer(1, board, pairManager);
//         player2 = new HumanPlayer(2, board, pairManager);
        player1 = new MinMaxPlayer(1, board, pairManager, depth1);
//        player2 = new MinMaxPlayer(2, board, pairManager, depth);
        player2 = new AlphaBetaPlayer(2, board, pairManager, depth2);
    }

    public void run() {
        printBoard();
        while (movesDone < movesToDo) {
            player1.move(movesDone);
            movesDone++;
            if (movesDone < movesToDo) {
                player2.move(movesDone);
                movesDone++;
            }
            ++tour;
            printBoard();
        }
        System.out.println("OVER");
    }

    public void printBoard() {
        for (int[] row : board) {
            System.out.println(Arrays.toString(row));
        }
        System.out.println("Tour: " + tour);
        System.out.println("P1: " + player1.points);
        System.out.println("P2: " + player2.points);
    }

    public void printMoveTimes(Player p) {
    	 System.out.println(((AbstractAIPlayer)p).moveTimes.toString());
    }
    
    public void printRcersions(Player p) {
   	 System.out.println(((AbstractAIPlayer)p).recursions.toString());
   }
    
    public static void main(String[] args) throws FileNotFoundException {
//        PrintStream out;
//		try {
//			out = new PrintStream(new FileOutputStream("output.txt"));
//	        System.setOut(out);
//		} catch (FileNotFoundException e) {
//			e.printStackTrace();
//		}
    	int size = 11;
    	int depth1 = 2;
    	int depth2 = 3;
    	String type1 = "alpha-beta";
    	String type2 = "minmax";
        Game game = new Game(size, depth1, depth2);
        game.run();
        Writer.writeTimes(game.player1, "size" + "_" + size + "_" + type1 + "_" + 
        depth1 + "_" + type2 + "_" + depth2);
//        game.printMoveTimes(game.player1);
//        game.printRcersions(game.player1);
//        game.printMoveTimes(game.player2);
//        game.printRcersions(game.player2);
    }
}
