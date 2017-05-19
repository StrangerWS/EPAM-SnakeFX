package com.ssu.artemiy_dobrynin.snake.model.snake;

import com.ssu.artemiy_dobrynin.snake.model.Entity;
import javafx.util.Pair;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by DobryninAM on 17.02.2017.
 */
public class Snake extends Entity {
    private int worldWidth;
    private int worldHeight;
    private int direction;
    private int length;
    private List<Pair<Integer, Integer>> snakeArray = new ArrayList<>();

    public int getDirection() {
        return direction;
    }

    public void setDirection(int direction) {
        this.direction = direction;
    }

    public int getLength() {
        return length;
    }

    public List<Pair<Integer, Integer>> getSnakeArray() {
        return snakeArray;
    }

    public void setSnakeArray(List<Pair<Integer, Integer>> snakeArray) {
        this.snakeArray = snakeArray;
    }

    public Snake(int worldWidth, int worldHeight) {
        snakeArray.add(new Pair<>(2, 0));
        snakeArray.add(new Pair<>(1, 0));
        snakeArray.add(new Pair<>(0, 0));
        direction = 0;
        length = snakeArray.size();

        this.worldWidth = worldWidth;
        this.worldHeight = worldHeight;
    }

    public void move() {
        // перемещение самого тела
        for (int i = snakeArray.size() - 1; i > 0; --i) {
            snakeArray.set(i, new Pair<>(snakeArray.get(i - 1).getKey(), snakeArray.get(i - 1).getValue()));
        }

        // перемещение по осям
        if (direction == 0) {
            snakeArray.set(0, new Pair<>(snakeArray.get(0).getKey() + 1, snakeArray.get(0).getValue()));
        } else if (direction == 1) {
            snakeArray.set(0, new Pair<>(snakeArray.get(0).getKey(), snakeArray.get(0).getValue() + 1));
        } else if (direction == 2) {
            snakeArray.set(0, new Pair<>(snakeArray.get(0).getKey() - 1, snakeArray.get(0).getValue()));
        } else if (direction == 3) {
            snakeArray.set(0, new Pair<>(snakeArray.get(0).getKey(), snakeArray.get(0).getValue() - 1));
        }


        // warp
        if (snakeArray.get(0).getKey() < 0) {
            snakeArray.set(0, new Pair<>(worldWidth - 1, snakeArray.get(0).getValue()));
            System.out.println("left -> right");
        }
        if (snakeArray.get(0).getKey() > worldWidth - 1) {
            snakeArray.set(0, new Pair<>(0, snakeArray.get(0).getValue()));
            System.out.println("right -> left");
        }
        if (snakeArray.get(0).getValue() < 0) {
            snakeArray.set(0, new Pair<>(snakeArray.get(0).getKey(), worldHeight - 1));
            System.out.println("up -> down");
        }
        if (snakeArray.get(0).getValue() > worldHeight- 1) {
            snakeArray.set(0, new Pair<>(snakeArray.get(0).getKey(), 0));
            System.out.println("down -> up");
        }
    }

    public void turnRight() {
        if (direction == 3) {
            direction = 0;
        } else direction++;
    }

    public void turnLeft() {
        if (direction == 0) {
            direction = 3;
        } else direction--;
    }

    @Override
    public void run() {
        move();
    }

    // Если голова пересекает тело
    public boolean isBitten() {
        for (int i = snakeArray.size() - 1; i > 0; --i) {
            if (snakeArray.get(i).equals(snakeArray.get(0))) {
                return true;
            }
        }
        return false;
    }

    public void eatNormal() {
        snakeArray.add(snakeArray.get(snakeArray.size() - 1));
        length = snakeArray.size();
    }

    public void eatBonus() {
        snakeArray.remove(snakeArray.size() - 1);
        length = snakeArray.size();
    }
}
