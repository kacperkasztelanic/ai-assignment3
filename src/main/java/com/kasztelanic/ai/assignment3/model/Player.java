package com.kasztelanic.ai.assignment3.model;

import com.kasztelanic.ai.assignment3.model.enums.PlayerType;

import javafx.beans.property.ReadOnlyIntegerProperty;
import javafx.beans.property.ReadOnlyIntegerWrapper;
import javafx.beans.property.ReadOnlyObjectProperty;
import javafx.beans.property.ReadOnlyObjectWrapper;

public class Player {

    protected final Game game;
    protected final String name;
    protected final ReadOnlyObjectWrapper<PlayerType> type;
    protected final ReadOnlyIntegerWrapper points;
    protected final String color;

    public Player(Game game, String name, PlayerType playerType, String color) {
        this.game = game;
        this.name = name;
        this.type = new ReadOnlyObjectWrapper<>(playerType);
        this.color = color;
        this.points = new ReadOnlyIntegerWrapper();
    }

    public String getName() {
        return name;
    }

    public String getColor() {
        return color;
    }

    public ReadOnlyObjectProperty<PlayerType> getTypeProperty() {
        return type.getReadOnlyProperty();
    }

    public PlayerType getType() {
        return type.get();
    }

    public ReadOnlyIntegerProperty getPointsProperty() {
        return points.getReadOnlyProperty();
    }

    public int getPoints() {
        return points.get();
    }

    public void addPoints(int p) {
        points.set(points.get() + p);
    }

    @Override
    public String toString() {
        return name;
    }

    public void move() {
        updatePoints();
    }

    protected void updatePoints() {
        // calculates points for current player
        int pointsToAdd = 5;
        addPoints(pointsToAdd);
    }
}
