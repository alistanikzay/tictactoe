package org.example.tictactoe.model;

import javafx.scene.control.Button;
import javafx.scene.paint.Color;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Model {
    private final List<Button> BUTTONS;
    private final List<String> board; // Gör denna mutable
    private int xScore = 0;
    private int oScore = 0;
    private final Random RANDOM = new Random();

    public Model(List<Button> buttons) {
        this.BUTTONS = buttons;
        this.board = new ArrayList<>(); // Använd ArrayList istället
        for (int i = 0; i < 9; i++) {
            board.add(""); // Initiera med tomma strängar
        }
    }

    public void playerMove(Button button) {
        int index = BUTTONS.indexOf(button);
        if (button.isDisable()) return;

        button.setText("O");
        button.setDisable(true);
        button.setTextFill(Color.RED);
        board.set(index, "O"); // Nu fungerar set korrekt
    }

    public void computerMove() {
        var allEnabledButtons = BUTTONS.stream().filter(b -> !b.isDisable()).toList();
        if (allEnabledButtons.isEmpty()) return;

        Button button = allEnabledButtons.get(RANDOM.nextInt(allEnabledButtons.size()));
        int index = BUTTONS.indexOf(button);

        button.setText("X");
        button.setDisable(true);
        button.setTextFill(Color.BLUE);
        board.set(index, "X"); // Uppdatera board korrekt
    }

    public String winStates() {
        String[] winPatterns = {
                board.get(0) + board.get(1) + board.get(2),
                board.get(3) + board.get(4) + board.get(5),
                board.get(6) + board.get(7) + board.get(8),
                board.get(0) + board.get(3) + board.get(6),
                board.get(1) + board.get(4) + board.get(7),
                board.get(2) + board.get(5) + board.get(8),
                board.get(0) + board.get(4) + board.get(8),
                board.get(2) + board.get(4) + board.get(6),
        };

        for (String pattern : winPatterns) {
            if ("OOO".equals(pattern)) return "O";
            if ("XXX".equals(pattern)) return "X";
        }

        return null;
    }

    public void reset() {
        BUTTONS.forEach(b -> {
            b.setDisable(false);
            b.setText("");
        });
        for (int i = 0; i < board.size(); i++) {
            board.set(i, ""); // Återställ board korrekt
        }
    }

    public void incrementScore(String player) {
        if ("X".equals(player)) {
            xScore++;
        } else if ("O".equals(player)) {
            oScore++;
        }
    }

    public int getXScore() {
        return xScore;
    }

    public int getOScore() {
        return oScore;
    }

    public boolean allButtonsDisabled() {
        return BUTTONS.stream().allMatch(Button::isDisable);
    }
}
