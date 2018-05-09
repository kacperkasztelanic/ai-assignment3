package com.ai.game.terminal;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

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
    public Player player1;
    public Player player2;

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
//        player2 = new MinMaxPlayer(2, board, pairManager, depth);
//        player1 = new MinMaxPlayer(1, board, pairManager, depth1);
        player2 = new AlphaBetaPlayer(2, board, pairManager, depth2, 1);
        player1 = new AlphaBetaPlayer(1, board, pairManager, depth1, 2);
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

    public void runFstMove() {
        printBoard();
        player1.move(movesDone);
        movesDone++;
        player2.move(movesDone);
        movesDone++;
        ++tour;
        printBoard();
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
    
	public static void playOnce() {
    	int size = 11;
    	int depth1 = 3;
    	int depth2 = 4;
    	String type1 = "minmax";
    	String type2 = "alpha-beta";
    	String filename = "size" + "_" + size + "_" + type1 + "_" + depth1 + "_" + type2 + "_" + depth2;
        Game game = new Game(size, depth1, depth2);
        game.run();
        filename = filename + "_pts_" + game.player1.getPoints() + "_" + game.player2.getPoints();
        Writer writer = new Writer(game, filename);
        writer.write();
    	writer.close();
	}
	
	public static void playTenTimes() {
    	int size = 13;
    	int depth1 = 4;
    	int depth2 = 5;
    	String type1 = "minmax";
    	String type2 = "alpha-beta";
    	String filename = "10_times_size" + "_" + size + "_" + type1 + "_" + depth1 + "_" + type2 + "_" + depth2;
    	Game game = null;
    	Writer writer = new Writer(filename);
    	for (int i=0; i<10; i++) {
	        game = new Game(size, depth1, depth2);
	        game.run();
	        writer.setGame(game);
	        writer.write();
    	}
    	writer.close();
    	if (game != null) {
    		System.out.println(filename + "_pts_" + game.player1.getPoints() + "_" + game.player2.getPoints());
    	}
	}
	
	public static void fstMoveTenTimes() {    
		List<Long> moveTimes1 = new ArrayList<>();
		List<Long> moveTimes2 = new ArrayList<>();
		List<Integer> recursions1 = new ArrayList<>();
		List<Integer> recursions2 = new ArrayList<>();
    	int size = 3;
    	int depth1 = 3;
    	int depth2 = 3;
    	String type1 = "minmax";
    	String type2 = "alpha-beta";
    	String filename = "fst_move_10_times_" + type1 + "_" + depth1 + "_" + type2 + "_" + depth2;
    	Game game = null;
    	Writer writer = new Writer(filename);
    	for (int i=0; i<10; ++i) {
    		moveTimes1.clear();
    		moveTimes2.clear();
    		recursions1.clear();
    		recursions2.clear();
    		size = 3;
        	while (size < 14) {
    	        game = new Game(size, depth1, depth2);
    	        game.runFstMove();
    	        moveTimes1.add(((AbstractAIPlayer)game.player1).moveTimes.get(0));
    	        moveTimes2.add(((AbstractAIPlayer)game.player2).moveTimes.get(0));
    	        recursions1.add(((AbstractAIPlayer)game.player1).recursions.get(0));
    	        recursions2.add(((AbstractAIPlayer)game.player2).recursions.get(0));
        		size++;
        	}
	        writer.writeList(moveTimes1.toString());
	        writer.writeList(moveTimes2.toString());
	        writer.writeList(recursions1.toString());
	        writer.writeList(recursions2.toString());
    	}
    	writer.close();
    	if (game != null) {
    		System.out.println(filename + "_pts_" + game.player1.getPoints() + "_" + game.player2.getPoints());
    	}
	}
	
	public static void pointsSizes() {    
		List<Integer> points1 = new ArrayList<>();
		List<Integer> points2 = new ArrayList<>();
    	int size = 3;
    	int depth1 = 2;
    	int depth2 = 3;
    	String filename = "2_1_points_sizes_" + depth1 + "_" + depth2;
    	Game game = null;
    	Writer writer = new Writer(filename);
    	while (size < 10) {
	        game = new Game(size, depth1, depth2);
	        game.run();
	        points1.add(game.player1.getPoints());
	        points2.add(game.player2.getPoints());
    		size++;
    	}
        writer.writeList(points1.toString());
        writer.writeList(points2.toString());
    	writer.close();
    	if (game != null) {
    		System.out.println(filename + "_pts_" + game.player1.getPoints() + "_" + game.player2.getPoints());
    	}
	}
	
    public static void main(String[] args) throws FileNotFoundException {
//    	playOnce();
//    	playTenTimes();
//    	fstMoveTenTimes();
    	pointsSizes();
    }
}
