
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

