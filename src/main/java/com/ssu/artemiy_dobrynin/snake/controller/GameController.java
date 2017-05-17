package com.ssu.artemiy_dobrynin.snake.controller;

import com.ssu.artemiy_dobrynin.snake.model.frog.Frog;
import com.ssu.artemiy_dobrynin.snake.model.frog.Type;
import com.ssu.artemiy_dobrynin.snake.model.snake.Snake;

import java.util.Random;

/**
 * Created by User on 13.04.2017.
 */
public class GameController {

    // Game objects
    private Snake snake;
    private Frog[] frogs;
    private int score;


    // World properties
    private int worldWidth;
    private int worldHeight;
    private int frogCount;
    private boolean[][] field;
    private Random random;
    private int scoreModifier;

    // World state
    private boolean isGameOver;
    private boolean isPlaying;
    private int gameDelay = 500;

    //Getters & Setters
    public Snake getSnake() {
        return snake;
    }

    public void setSnake(Snake snake) {
        this.snake = snake;
    }

    public Frog[] getFrogs() {
        return frogs;
    }

    public void setFrogs(Frog blueFrog) {
        this.frogs = frogs;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public boolean[][] getField() {
        return field;
    }

    public void setField(boolean[][] field) {
        this.field = field;
    }

    public boolean isGameOver() {
        return isGameOver;
    }

    public void setGameOver(boolean gameOver) {
        isGameOver = gameOver;
    }

    public boolean isPlaying() {
        return isPlaying;
    }

    public void setPlaying(boolean playing) {
        isPlaying = playing;
    }

    public int getGameDelay() {
        return gameDelay;
    }

    public void setGameDelay(int gameDelay) {
        this.gameDelay = gameDelay;
    }

    // Getting parameters from options
    public GameController(int worldWidth, int worldHeight, int frogCount, int difficulty) {
        // difficulty: from 1 to 3, where 1 - easy, 3 - hard.
        // it`s easy to modify difficulty by increasing value
        if (worldHeight >= 8 && worldWidth >= 10 && frogCount >= 5) {
            this.worldWidth = worldWidth;
            this.worldHeight = worldHeight;
            this.frogCount = frogCount;
        }
        else {
            this.worldWidth = 10;
            this.worldHeight = 8;
            this.frogCount = 5;
        }

        this.snake = new Snake();
        Thread snakeThread = new Thread(snake);
        snakeThread.setDaemon(true);
        snakeThread.start();

        this.isGameOver = false;
        this.isPlaying = true;
        this.score = 0;
        this.scoreModifier = difficulty * 30;
        this.field = new boolean[worldWidth][worldHeight];
        this.random = new Random();

        this.gameDelay /= difficulty;
    }

    private void addFrog(Type frogType) {
        frogCount--;

        //Init field placement
        for (int i = 0; i < worldWidth; i++)
            for (int j = 0; j < worldHeight; j++) {
                field[i][j] = false;
            }

        for (int i = 0; i < snake.length; i++) {
            for (int j = 0; j < snake.length; j++) {
                field[i][j] = true;
            }
        }

        for (int i = 0; i < frogs.length; i++) {
            for (int j = 0; j < frogs.length; j++) {
                field[i][j] = true;
            }
        }

        int frogX = random.nextInt(worldWidth);
        int frogY = random.nextInt(worldHeight);

        while (true) {
            if (field[frogX][frogY] == false)
                break;
            frogX += 1;
            if (frogX >= worldWidth) {
                frogX = 0;
                frogY += 1;
                if (frogY >= worldHeight) {
                    frogY = 0;
                }
            }
        }
    }

    public void update() {
        if (isGameOver) {
            return;
        }
        if (isPlaying) {
            try {
                if (searchFrog(Type.BLUE) == null && random.nextInt(100) < 15) {
                    addFrog(Type.BLUE);
                }
                if (searchFrog(Type.RED) == null && random.nextInt(100) < 35) {
                    addFrog(Type.RED);
                }
                if (searchFrog(Type.GREEN) == null) {
                    addFrog(Type.GREEN);
                }

                snake.move();
                Thread.sleep(gameDelay);
                if (snake.isBitten()) {
                    isGameOver = true;
                    return;
                }

                if (snake.snakeArray.get(0) == searchFrog(Type.GREEN).getCoordinates()) {
                    score += scoreModifier;
                    snake.eatNormal();
                    frogs = null;

                    if (snake.length == worldHeight * worldWidth) {
                        isGameOver = true;
                        return;
                    } else {
                        addFrog(Type.GREEN);
                    }

                } else if (snake.snakeArray.get(0) == searchFrog(Type.RED).getCoordinates()) {
                    score += scoreModifier * 2;
                    snake.eatBonus();
                    frogs = null;

                    if (snake.length <= 3) {
                        isGameOver = true;
                        return;
                    } else {
                        addFrog(Type.RED);
                    }
                } else if (snake.snakeArray.get(0) == searchFrog(Type.BLUE).getCoordinates()) {
                    isGameOver = true;
                    return;
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private Frog searchFrog(Type type) {
        for (int i = 0; i < frogs.length; i++) {
            if (frogs[i].getFrogType() == type)
                return frogs[i];
        }
        return null;
    }
}
