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
    private Frog greenFrog;
    private Frog blueFrog;
    private Frog redFrog;
    private int score;
    private String gameOverMessage;


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

    public String getGameOverMessage() {
        return gameOverMessage;
    }

    public void setGameOverMessage(String gameOverMessage) {
        this.gameOverMessage = gameOverMessage;
    }

    public Snake getSnake() {
        return snake;
    }

    public void setSnake(Snake snake) {
        this.snake = snake;
    }

    public Frog getGreenFrog() {
        return greenFrog;
    }

    public void setGreenFrog(Frog greenFrog) {
        this.greenFrog = greenFrog;
    }

    public Frog getBlueFrog() {
        return blueFrog;
    }

    public void setBlueFrog(Frog blueFrog) {
        this.blueFrog = blueFrog;
    }

    public Frog getRedFrog() {
        return redFrog;
    }

    public void setRedFrog(Frog redFrog) {
        this.redFrog = redFrog;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public int getWorldWidth() {
        return worldWidth;
    }

    public int getWorldHeight() {
        return worldHeight;
    }

    public int getFrogCount() {
        return frogCount;
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

        this.worldWidth = worldWidth;
        this.worldHeight = worldHeight;
        this.frogCount = frogCount;

        this.snake = new Snake(worldWidth, worldHeight);
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
        placeFrogs();
    }

    public void addFrog(Type frogType) {
        //Init field placement
        for (int i = 0; i < worldWidth; i++)
            for (int j = 0; j < worldHeight; j++) {
                field[i][j] = false;
            }

        for (int i = 0; i < snake.getLength(); i++) {
            for (int j = 0; j < snake.getLength(); j++) {
                field[i][j] = true;
            }
        }

        if (greenFrog != null) {
            field[greenFrog.getPosX()][greenFrog.getPosY()] = true;
        }

        if (redFrog != null) {
            field[redFrog.getPosX()][redFrog.getPosY()] = true;
        }
        if (blueFrog != null) {
            field[blueFrog.getPosX()][blueFrog.getPosY()] = true;
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

        switch (frogType) {
            case RED:
                redFrog = new Frog(frogX, frogY, frogType);
                break;
            case GREEN:
                greenFrog = new Frog(frogX, frogY, frogType);
                break;
            case BLUE:
                blueFrog = new Frog(frogX, frogY, frogType);
                break;
        }

    }

    public void update() {
        if (isGameOver) {
            return;
        }
        if (isPlaying) {
            try {
                if (frogCount <= 0) {
                    gameOverMessage = "YOU WIN! All Frogs Are Eaten!";
                    isGameOver = true;
                    return;
                }
                snake.move();
                Thread.sleep(gameDelay);
                if (snake.isBitten()) {
                    isGameOver = true;
                    gameOverMessage = "You Bit Yourself";
                    return;
                }
                if (random.nextInt(100) < 5){
                    placeFrogs();
                }
                if (greenFrog != null) {
                    if (snake.getSnakeArray().get(0).getKey() > greenFrog.getCoordinates().getKey() - 3
                            && snake.getSnakeArray().get(0).getKey() < greenFrog.getCoordinates().getKey() + 3
                            && snake.getSnakeArray().get(0).getValue() > greenFrog.getCoordinates().getValue() - 3
                            && snake.getSnakeArray().get(0).getValue() < greenFrog.getCoordinates().getValue() + 3
                            && random.nextInt(100) < 15) {
                        greenFrog = null;
                        addFrog(Type.GREEN);
                    } else if (snake.getSnakeArray().get(0).equals(greenFrog.getCoordinates())) {
                        score += scoreModifier;
                        frogCount--;
                        snake.eatNormal();
                        placeFrogs();
                        greenFrog = null;

                        if (snake.getLength() == worldHeight * worldWidth) {
                            isGameOver = true;
                            gameOverMessage = "YOU WIN!";
                            return;
                        } else {
                            addFrog(Type.GREEN);
                        }
                    }
                }
                if (redFrog != null) {
                    if (snake.getSnakeArray().get(0).getKey() > redFrog.getCoordinates().getKey() - 3
                            && snake.getSnakeArray().get(0).getKey() < redFrog.getCoordinates().getKey() + 3
                            && snake.getSnakeArray().get(0).getValue() > redFrog.getCoordinates().getValue() - 3
                            && snake.getSnakeArray().get(0).getValue() < redFrog.getCoordinates().getValue() + 3
                            && random.nextInt(100) < 25) {
                        redFrog = null;
                        addFrog(Type.RED);
                    } else if (snake.getSnakeArray().get(0).equals(redFrog.getCoordinates())) {
                        score += scoreModifier * 2;
                        frogCount--;
                        snake.eatBonus();
                        placeFrogs();
                        redFrog = null;

                        if (snake.getLength() < 3) {
                            isGameOver = true;
                            gameOverMessage = "Your Length Is Minimal";
                            return;
                        } else {
                            addFrog(Type.RED);
                        }
                    }
                }
                if (blueFrog != null && snake.getSnakeArray().get(0).equals(blueFrog.getCoordinates())) {
                    isGameOver = true;
                    gameOverMessage = "Poisonous Frog Eaten";
                    return;
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public void placeFrogs() {
        if (blueFrog == null && random.nextInt(100) < 15) {
            addFrog(Type.BLUE);
        }
        if (redFrog == null && random.nextInt(100) < 35) {
            addFrog(Type.RED);
        }
        if (greenFrog == null) {
            addFrog(Type.GREEN);
        }
    }
}
