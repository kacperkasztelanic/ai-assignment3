package com.ai.game.terminal;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;

public class Writer {
	
	public static void writeTimes(Player p, String filename) throws FileNotFoundException{
		 PrintWriter pw = new PrintWriter(new File("files/" + filename + ".csv"));
		 pw.write(((AbstractAIPlayer)p).moveTimes.toString());
		 pw.close();
	}
	
    public static void main(String[]args) throws FileNotFoundException{
        PrintWriter pw = new PrintWriter(new File("test.csv"));
        StringBuilder sb = new StringBuilder();
        sb.append("id");
        sb.append(',');
        sb.append("Name");
        sb.append('\n');

        sb.append("1");
        sb.append(',');
        sb.append("Prashant Ghimire");
        sb.append('\n');

        pw.write(sb.toString());
        pw.close();
        System.out.println("done!");
    }
}
