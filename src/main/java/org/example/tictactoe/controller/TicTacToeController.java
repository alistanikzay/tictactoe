package org.example.tictactoe.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

import org.example.tictactoe.model.Model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class TicTacToeController {

    @FXML
    private Button button1, button2, button3, button4, button5, button6, button7, button8, button9;
    @FXML
    private Label statusLabel, OScore, XScore;

    private Model model;

    public void initialize() {
        List<Button> buttons = new ArrayList<>(Arrays.asList(button1, button2, button3, button4, button5, button6, button7, button8, button9));
        model = new Model(buttons);
    }

    @FXML
    private void clickedButton(ActionEvent actionEvent) {
        Button button = (Button) actionEvent.getSource();
        model.playerMove(button);

        String winner = model.winStates();
        if (winner == null) {
            model.computerMove();
            winner = model.winStates();
        }
        updateScores(winner);
    }

    private void updateScores(String winner) {
        XScore.setText("Score: " + model.getXScore());
        OScore.setText("Score: " + model.getOScore());

        if (winner != null) {
            statusLabel.setText(winner + " wins!");
        } else if (model.allButtonsDisabled()) {
            statusLabel.setText("Draw!");
        }
    }

    public void reset() {
        model.reset();
        statusLabel.setText("");
        updateScores(null);
    }
}
