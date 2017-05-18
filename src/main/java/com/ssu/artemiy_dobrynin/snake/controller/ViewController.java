package com.ssu.artemiy_dobrynin.snake.controller;

import com.ssu.artemiy_dobrynin.snake.model.frog.Frog;
import com.ssu.artemiy_dobrynin.snake.model.frog.Type;
import com.ssu.artemiy_dobrynin.snake.model.snake.Snake;
import javafx.animation.AnimationTimer;
import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.util.Pair;


/**
 * Created by User on 12.05.2017.
 */
public class ViewController {

    private static final String START = "Start";
    private static final String STOP = "Stop";
    private static final String PAUSE = "Pause";
    private static final String RESUME = "Resume";

    private final int CELL_SIZE = 32;
    private int currentWidth;
    private int currentHeight;
    private int currentFrogCount;
    private int currentDifficulty;

    private GraphicsContext gc;
    private Canvas canvas;
    private AnimationTimer timer;

    private GameController game;
    private int score;
    private Snake snake;

    @FXML
    private AnchorPane gamePane;
    @FXML
    private AnchorPane controlPane;
    @FXML
    private Label scoreLabel;
    @FXML
    private Label gfwLabel;
    @FXML
    private Label gfhLabel;
    @FXML
    private Label fcLabel;
    @FXML
    private Label diffLabel;
    @FXML
    private ChoiceBox gfwChooser;
    @FXML
    private ChoiceBox gfhChooser;
    @FXML
    private ChoiceBox fcChooser;
    @FXML
    private ChoiceBox diffChooser;
    @FXML
    private Button applyButton;
    @FXML
    private Button optionsButton;
    @FXML
    private Button pauseButton;
    @FXML
    private Button startButton;


    public void initialize() {
        initializeStartScreen();
        pauseButton.setDisable(true);

        diffChooser.getItems().addAll("Easy", "Medium", "Hard");
        diffChooser.setValue("Medium");

        for (int i = 16; i <= 48; i++) {
            gfhChooser.getItems().add(i);
        }
        for (int i = 16; i <= 48; i++) {
            gfwChooser.getItems().add(i);
        }
        for (int i = 5; i <= 100; i++) {
            fcChooser.getItems().add(i);
        }

        gfhChooser.setValue(16);
        gfwChooser.setValue(16);
        fcChooser.setValue(9);

        initializeStartScreen();
    }

    public void initializeStartScreen() {
        scoreLabel.setVisible(false);
        gfwLabel.setVisible(false);
        gfhLabel.setVisible(false);
        fcLabel.setVisible(false);
        diffLabel.setVisible(false);
        gfwChooser.setVisible(false);
        gfhChooser.setVisible(false);
        fcChooser.setVisible(false);
        diffChooser.setVisible(false);
        applyButton.setVisible(false);
        optionsButton.setDisable(false);
    }

    public void initializeOptions() {
        scoreLabel.setVisible(false);
        gfwLabel.setVisible(true);
        gfhLabel.setVisible(true);
        fcLabel.setVisible(true);
        diffLabel.setVisible(true);
        gfwChooser.setVisible(true);
        gfhChooser.setVisible(true);
        fcChooser.setVisible(true);
        diffChooser.setVisible(true);
        applyButton.setVisible(true);
        optionsButton.setDisable(true);
        if (gc != null) {
            gc.clearRect(0, 0, CELL_SIZE * currentWidth, CELL_SIZE * currentHeight);
        }
    }

    public void initializeGame() {
        scoreLabel.setVisible(true);
        gfwLabel.setVisible(false);
        gfhLabel.setVisible(false);
        fcLabel.setVisible(false);
        diffLabel.setVisible(false);
        gfwChooser.setVisible(false);
        gfhChooser.setVisible(false);
        fcChooser.setVisible(false);
        diffChooser.setVisible(false);
        applyButton.setVisible(false);
        optionsButton.setDisable(true);
    }

    @FXML
    protected void startButtonClicked() {

        if (startButton.getText().equals(START)) {
            initializeGame();
            readProperties();
            initGraphics();
            game = new GameController(currentWidth, currentHeight, currentFrogCount, currentDifficulty);
            snake = game.getSnake();
            game.addFrog(Type.GREEN);
            score = game.getScore();
            timer.start();
            startButton.setText(STOP);
            pauseButton.setDisable(false);
        }
        //stopping the game
        else {
            game.setGameOver(true);
            timer.stop();
            initializeStartScreen();
            startButton.setText(START);
            pauseButton.setDisable(true);
            pauseButton.setText(PAUSE);
        }
    }

    private void initGraphics() {
        canvas = new Canvas(currentWidth * CELL_SIZE, currentHeight * CELL_SIZE);
        gc = canvas.getGraphicsContext2D();
        gamePane.getChildren().add(canvas);

        canvas.setOnMouseClicked(event -> mouseClicked(event.getButton().toString()));

        timer = new AnimationTimer() {
            public void handle(long currentNanoTime) {
                gc.clearRect(0, 0, CELL_SIZE * currentWidth, CELL_SIZE * currentHeight);
                gc.setFill(Color.BLACK);
                gc.fillRect(0, 0, currentWidth * CELL_SIZE, currentHeight * CELL_SIZE);
                renderSnake();
                for (int i = 0; i < game.getFrogs().size(); i++) {
                    renderFrog(game.getFrogs().get(i));
                }

                updateScoreLabel();
                game.update();

                if (game.isGameOver()) {
                    timer.stop();
                    initializeStartScreen();
                    startButton.setText(START);
                    pauseButton.setText(PAUSE);
                    pauseButton.setDisable(true);
                }
            }
        };
    }

    private void readProperties() {
        currentHeight = (int) gfhChooser.getValue();
        currentWidth = (int) gfwChooser.getValue();
        currentFrogCount = (int) fcChooser.getValue();
        switch ((String) diffChooser.getValue()) {
            case "Easy":
                currentDifficulty = 1;
                break;
            case "Medium":
                currentDifficulty = 2;
                break;
            case "Hard":
                currentDifficulty = 3;
                break;
        }
    }

    @FXML
    private void applyButtonPressed() {
        readProperties();
    }

    public void updateScoreLabel() {
        int newScore = game.getScore();
        if (newScore > score) score = newScore;
        scoreLabel.setText("Score: " + Integer.toString(score));
    }

    public void renderSnake() {
        for (Pair<Integer, Integer> coords: snake.getSnakeArray()) {
            gc.setFill(Color.YELLOW);
            gc.fillOval(coords.getKey() * CELL_SIZE, coords.getValue() * CELL_SIZE, CELL_SIZE, CELL_SIZE);
            System.out.println(coords.getKey() + " " + coords.getValue());
        }
    }

    public void renderFrog(Frog frog) {
        switch (frog.getFrogType()) {
            case BLUE:
                gc.setFill(Color.BLUE);
                break;
            case GREEN:
                gc.setFill(Color.GREEN);
                break;
            case RED:
                gc.setFill(Color.RED);
        }
        gc.fillOval(frog.getPosX() * CELL_SIZE, frog.getPosY() * CELL_SIZE,
                CELL_SIZE, CELL_SIZE);
    }

    public void mouseClicked(String button) {
        if (button.equals("PRIMARY")) {
            snake.turnLeft();
            System.out.println("left");
        }
        if (button.equals("SECONDARY")) {
            snake.turnRight();
            System.out.println("right");
        }
    }
}