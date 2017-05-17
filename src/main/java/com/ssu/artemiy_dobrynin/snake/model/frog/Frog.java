package com.ssu.artemiy_dobrynin.snake.model.frog;


import com.ssu.artemiy_dobrynin.snake.model.Entity;
import javafx.util.Pair;

/**
 * Created by DobryninAM on 17.02.2017.
 */
public abstract class Frog extends Entity {
    private int posX;
    private int posY;
    private Type frogType;

    public int getPosX() {
        return posX;
    }

    public int getPosY() {
        return posY;
    }

    public Pair<Integer, Integer> getCoordinates() {
        return new Pair<>(posX, posY);
    }

    public Type getFrogType() {
        return frogType;
    }

    public Frog(int posX, int posY, Type frogType) {
        this.posX = posX;
        this.posY = posY;
        this.frogType = frogType;
    }
}