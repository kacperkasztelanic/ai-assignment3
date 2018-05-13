package com.ai.game.terminal;

import java.util.ArrayList;
//import java.util.List;
import java.util.Random;

public class MovesManager {

    int boardSide;
    MovePair[] arr;
//    List<MovePair> list;
    int capacity;
    int size;
    Random rand = new Random();

    public MovesManager(int boardSide) {
        this.boardSide = boardSide;
        capacity = boardSide * boardSide;
//        list = new ArrayList<>(capacity);
//        for (int i = 0; i < capacity; i++) {
//        	list.add(new MovePair(i / boardSide, i % boardSide));
//        }
        arr = new MovePair[capacity];
        for (int i = 0; i < capacity; i++) {
            arr[i] = new MovePair(i / boardSide, i % boardSide);
        }
        size = capacity;
    }

    public boolean removePair(MovePair other) {
        boolean result = false;
        for (int i = 0; !result && i < size; i++) {
            result = arr[i].equals(other);
            if (result) {
                arr[i] = arr[--size];
            }
        }
        return result;
//    	return list.remove(other);
    }

    // IntPair drawPair() {
    // int index = rand.nextInt(size);
    // IntPair result = arr[index];
    // arr[index] = arr[--size];
    // return result;
    // }

    public ArrayList<MovePair> getUnused() {
        ArrayList<MovePair> list = new ArrayList<>(size);
        for (int i = 0; i < size; i++) {
            list.add(arr[i]);
        }
        return list;
//    	return new ArrayList<>(list);
    }
    
    public ArrayList<MovePair> getRandomizedUnused() {
        ArrayList<MovePair> list = getUnused();
        for (int i = 0; i < size; i++) {
        	int index = rand.nextInt(size);
        	MovePair temp = list.get(index);
        	list.set(index, list.get(i));
        	list.set(i, temp);
        }
        return list;
    } 
}
