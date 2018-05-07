package com.ai.game.terminal;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;

public class Writer {
	
	Game game;
	String filename;
	PrintWriter pw;
	AbstractAIPlayer player1;
	AbstractAIPlayer player2;
	
	public Writer(String filename) {
		this.filename = filename;
		try {
			pw = new PrintWriter(new File("files/" + filename + ".csv"));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	public Writer(Game game, String filename) {
		this(filename);
		this.game = game;
		player1 = (AbstractAIPlayer)game.player1;
		player2 = (AbstractAIPlayer)game.player2;
	}

	public void write() {
		writeTimes(player1);
		writeTimes(player2);
		writeRecursions(player1);
		writeRecursions(player2);
	}
	
	private void writeTimes(AbstractAIPlayer p) {
		 String res = p.moveTimes.toString();
		 System.out.println(res);
		 res = res.substring(1, res.length() - 1) + '\n';
		 pw.write(res);
	}
	
	private void writeRecursions(Player p) {
		 String res = ((AbstractAIPlayer)p).recursions.toString();
		 System.out.println(res);
		 res = res.substring(1, res.length() - 1) + '\n';
		 pw.write(res);
	}
	
	public void writeList(String str) {
		str = str.substring(1, str.length() - 1) + '\n';
		pw.write(str);
	}
	
	public void close() {
		pw.close();
	}

	public void setGame(Game game) {
		this.game = game;
		player1 = (AbstractAIPlayer)game.player1;
		player2 = (AbstractAIPlayer)game.player2;
	}
}
