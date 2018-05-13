package com.ai.game.model;

import java.util.ArrayList;

public class TurnManager {

    private Turn[] arr;
    private int capacity;
    private int size;

    public TurnManager(int boardSize) {
        capacity = boardSize * boardSize;
        arr = new Turn[capacity];
        for (int i = 0; i < capacity; i++) {
            arr[i] = new Turn(i / boardSize, i % boardSize);
        }
        size = capacity;
    }

    public boolean removePair(Turn other) {
        boolean result = false;
        for (int i = 0; !result && i < size; i++) {
            result = arr[i].equals(other);
            if (result) {
                arr[i] = arr[--size];
            }
        }
        System.out.println("Removing");
        return result;
    }

    public ArrayList<Turn> getUnused() {
        ArrayList<Turn> list = new ArrayList<>(size);
        for (int i = 0; i < size; i++) {
            list.add(arr[i]);
        }
        return list;
    }

    @Override
    public String toString() {
        String res = "[";
        for (int i = 0; i < size; i++) {
            res += arr[i];
            res += '\n';
        }
        return res + "]";
    }
}
