package main.java.com.ssu.artemiy_dobrynin.snake.model.snake;

import main.java.com.ssu.artemiy_dobrynin.snake.model.Entity;
import javafx.util.Pair;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by DobryninAM on 17.02.2017.
 */
public class Snake extends Entity {
    public int direction = 3;
    public int length = 3;
    public List<Pair<Integer, Integer>> snakeArray = new ArrayList<>();

    public Snake() {

    }

    public void move() {

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
        // перемещение самого тела
        for (int i = length; i > 0; i--) {
            snakeArray.set(i, new Pair<>(snakeArray.get(i - 1).getKey(), snakeArray.get(i - 1).getValue()));
        }
    }

    @Override
    public void run() {
        move();
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    // Если голова пересекает тело
    public boolean isBitten() {
        for (int i = length; i > 0; i--) {
            if (snakeArray.get(i) == snakeArray.get(0)) {
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
