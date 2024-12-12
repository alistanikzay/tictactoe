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
    private List<String> board;

    public void initialize() {
        List<Button> buttons = new ArrayList<>(Arrays.asList(button1, button2, button3, button4, button5, button6, button7, button8, button9));
        board = new ArrayList<>(Arrays.asList("", "", "", "", "", "", "", "", ""));
        model = new Model(buttons);
    }

    @FXML
    private void clickedButton(ActionEvent actionEvent) {
        Button button = (Button) actionEvent.getSource();
        int index = getButtonIndex(button);

        // Spelaren gör ett drag
        model.playerMove(button);
        board.set(index, "O");

        // Kontrollera vinnare
        String winner = checkWinner();
        if (winner == null) {
            // Datorn gör ett drag
            model.computerMove();
            updateBoardAfterComputerMove();

            winner = checkWinner();
        }

        updateScores(winner);
    }

    private int getButtonIndex(Button button) {
        return Arrays.asList(button1, button2, button3, button4, button5, button6, button7, button8, button9).indexOf(button);
    }

    private void updateBoardAfterComputerMove() {
        for (int i = 0; i < 9; i++) {
            if ("X".equals(getButtonText(i))) {
                board.set(i, "X");
            }
        }
    }

    private String getButtonText(int index) {
        Button button = Arrays.asList(button1, button2, button3, button4, button5, button6, button7, button8, button9).get(index);
        return button.getText();
    }

    private String checkWinner() {
        String[] winPatterns = {
                board.get(0) + board.get(1) + board.get(2),
                board.get(3) + board.get(4) + board.get(5),
                board.get(6) + board.get(7) + board.get(8),
                board.get(0) + board.get(3) + board.get(6),
                board.get(1) + board.get(4) + board.get(7),
                board.get(2) + board.get(5) + board.get(8),
                board.get(0) + board.get(4) + board.get(8),
                board.get(2) + board.get(4) + board.get(6)
        };

        for (String pattern : winPatterns) {
            if ("OOO".equals(pattern)) return "O";
            if ("XXX".equals(pattern)) return "X";
        }
        return null;
    }

    private void updateScores(String winner) {
        if ("X".equals(winner) || "O".equals(winner)) {
            model.incrementScore(winner);
        }

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
        board = new ArrayList<>(Arrays.asList("", "", "", "", "", "", "", "", ""));
        statusLabel.setText("");
        updateScores(null);
    }
}
