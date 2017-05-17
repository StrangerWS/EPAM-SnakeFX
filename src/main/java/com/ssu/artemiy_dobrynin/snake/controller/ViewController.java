package com.ssu.artemiy_dobrynin.snake.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;


/**
 * Created by User on 12.05.2017.
 */
public class ViewController {

    private static final String START = "Start";
    private static final String STOP = "Stop";
    private static final String PAUSE = "Pause";
    private static final String RESUME = "Resume";

    private final int GAME_BOARD_CELL_SIZE = 32;

    @FXML
    Label scoreLabel;
    @FXML
    Label gfwLabel;
    @FXML
    Label gfhLabel;
    @FXML
    Label fcLabel;
    @FXML
    ChoiceBox gfwChooser;
    @FXML
    ChoiceBox gfhChooser;
    @FXML
    ChoiceBox fcChooser;
    @FXML
    Button applyButton;


    public void initialize(){
        initializeStartScreen();
    }

    public void initializeStartScreen() {
        scoreLabel.setVisible(false);
        gfwLabel.setVisible(false);
        gfhLabel.setVisible(false);
        fcLabel.setVisible(false);
        gfwChooser.setVisible(false);
        gfhChooser.setVisible(false);
        fcChooser.setVisible(false);
        applyButton.setVisible(false);
    }

    public void initializeOptions() {
        scoreLabel.setVisible(false);
        gfwLabel.setVisible(true);
        gfhLabel.setVisible(true);
        fcLabel.setVisible(true);
        gfwChooser.setVisible(true);
        gfhChooser.setVisible(true);
        fcChooser.setVisible(true);
        applyButton.setVisible(true);
    }

    public void initializeGame() {
        scoreLabel.setVisible(true);
        gfwLabel.setVisible(false);
        gfhLabel.setVisible(false);
        fcLabel.setVisible(false);
        gfwChooser.setVisible(false);
        gfhChooser.setVisible(false);
        fcChooser.setVisible(false);
        applyButton.setVisible(false);
    }

}