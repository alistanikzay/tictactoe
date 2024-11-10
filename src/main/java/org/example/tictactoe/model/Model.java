
package org.example.tictactoe.model;

import javafx.scene.control.Button;
import javafx.scene.paint.Color;

import java.util.List;
import java.util.Random;

public class Model {
    private final List<Button> BUTTONS;
    private int xScore = 0;
    private int oScore = 0;
    private final Random RANDOM = new Random();

    public Model(List<Button> buttons) {
        this.BUTTONS = buttons;
    }

    public void playerMove(Button button) {
        if (button.isDisable() || winStates() != null) return;

        button.setText("O");
        button.setDisable(true);
        button.setTextFill(Color.RED);
    }

    public void computerMove() {
        if (winStates() != null || allButtonsDisabled()) return;

        var allEnabledButtons = BUTTONS.stream().filter(b -> !b.isDisable()).toList();
        if (allEnabledButtons.isEmpty()) return;

        Button button = allEnabledButtons.get(RANDOM.nextInt(allEnabledButtons.size()));
        button.setText("X");
        button.setDisable(true);
        button.setTextFill(Color.BLUE);
    }

    public boolean allButtonsDisabled() {
        return BUTTONS.stream().allMatch(Button::isDisable);
    }

    public void reset() {
        BUTTONS.forEach(b -> {
            b.setDisable(false);
            b.setText("");
        });
    }

    public String winStates() {
        for (int a = 0; a < 8; a++) {
            String line = switch (a) {
                case 0 -> BUTTONS.get(0).getText() + BUTTONS.get(1).getText() + BUTTONS.get(2).getText();
                case 1 -> BUTTONS.get(3).getText() + BUTTONS.get(4).getText() + BUTTONS.get(5).getText();
                case 2 -> BUTTONS.get(6).getText() + BUTTONS.get(7).getText() + BUTTONS.get(8).getText();
                case 3 -> BUTTONS.get(0).getText() + BUTTONS.get(3).getText() + BUTTONS.get(6).getText();
                case 4 -> BUTTONS.get(1).getText() + BUTTONS.get(4).getText() + BUTTONS.get(7).getText();
                case 5 -> BUTTONS.get(2).getText() + BUTTONS.get(5).getText() + BUTTONS.get(8).getText();
                case 6 -> BUTTONS.get(0).getText() + BUTTONS.get(4).getText() + BUTTONS.get(8).getText();
                case 7 -> BUTTONS.get(2).getText() + BUTTONS.get(4).getText() + BUTTONS.get(6).getText();
                default -> null;
            };

            String winner = winnerIs(line);
            if (winner != null) {
                return winner;
            }
        }
        return null;
    }

    private String winnerIs(String line) {
        if (line.equals("OOO")) {
            updateScore("O");
            disableAllButtons();
            return "O";
        } else if (line.equals("XXX")) {
            updateScore("X");
            disableAllButtons();
            return "X";
        }
        return null;
    }

    private void updateScore(String player) {
        if (player.equals("O")) {
            xScore++;
        } else if (player.equals("X")) {
            oScore++;
        }
    }

    private void disableAllButtons() {
        BUTTONS.forEach(b -> b.setDisable(true));
    }

    public int getXScore() {
        return xScore;
    }

    public int getOScore() {
        return oScore;
    }
}