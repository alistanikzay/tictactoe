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
    private Model model;
    private List<Button> buttons;
    private List<String> board;

    @FXML
    private void initialize() {
        buttons = Arrays.asList(button1, button2, button3, button4, button5, button6, button7, button8, button9);
        board = Arrays.asList("", "", "", "", "", "", "", "", "");
        model = new Model();  // Använd standardkonstruktorn

    }

    @FXML
    private Button button1;
    @FXML
    private Button button2;
    @FXML
    private Button button3;
    @FXML
    private Button button4;
    @FXML
    private Button button5;
    @FXML
    private Button button6;
    @FXML
    private Button button7;
    @FXML
    private Button button8;
    @FXML
    private Button button9;

    @FXML
    private Label XScore;
    @FXML
    private Label OScore;
    @FXML
    private Label statusLabel;

    @FXML
    private void clickedButton(ActionEvent event) {
        // Din logik här
    }

    @FXML
    private void reset(ActionEvent event) {
        model.reset();       // Återställ spelbrädet i Model
        updateView();        // Uppdatera knapparna i GUI
        statusLabel.setText("");  // Töm statuslabeln
    }



    @FXML
    private void handleButtonClick(ActionEvent event) {
        Button clickedButton = (Button) event.getSource();
        int index = buttons.indexOf(clickedButton);

        System.out.println("Button clicked by player at index: " + index);  // Debug print

        if (model.playerMove(index)) {
            clickedButton.setText("O");
            clickedButton.setDisable(true);

            String winner = model.checkWinner();
            System.out.println("Winner after player move: " + winner);

            if ("O".equals(winner)) {
                displayWinner("Player");
            } else if ("X".equals(winner)) {
                displayWinner("Computer");
            } else if (allButtonsDisabled()) {
                displayWinner("Draw");
            } else {
                model.computerMove();
                System.out.println("Computer made a move");
                updateView();

                winner = model.checkWinner();
                System.out.println("Winner after computer move: " + winner);

                if ("X".equals(winner)) {
                    displayWinner("Computer");
                }
            }
        } else {
            System.out.println("Cell already occupied.");
        }
    }


    private boolean allButtonsDisabled() {
        return buttons.stream().allMatch(button -> button.isDisable());
    }

    private void updateView() {
        for (int i = 0; i < buttons.size(); i++) {
            buttons.get(i).setText(model.getBoard().get(i));  // Uppdatera knapparna med spelbrädet
            buttons.get(i).setDisable(!model.getBoard().get(i).isEmpty());  // Aktivera/Deaktivera knappar efter spelstatus
        }
        statusLabel.setText("");  // Töm statuslabel för en ny omgång
    }


    private void displayWinner(String winner) {
        statusLabel.setText(winner + " won the game!");

        if ("Player".equals(winner)) {
            model.incrementScore("O");
            XScore.setText("X: " + model.getXScore());
            OScore.setText("O: " + model.getOScore());
        } else if ("Computer".equals(winner)) {
            model.incrementScore("X");
            XScore.setText("X: " + model.getXScore());
            OScore.setText("O: " + model.getOScore());
        } else if ("Draw".equals(winner)) {
            statusLabel.setText("Game ended in a Draw!");
        }

        // Inaktivera alla knappar eftersom spelet är över
        buttons.forEach(button -> button.setDisable(true));
    }
}
