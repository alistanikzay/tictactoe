package org.example.tictactoe.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Model {
    private final List<String> board;
    private int xScore = 0;
    private int oScore = 0;
    private final Random RANDOM = new Random();

    public Model() {
        board = new ArrayList<>();
        for (int i = 0; i < 9; i++) {
            board.add(""); // Initiera med tomma strängar
        }
    }

    // Spelarens drag
    public boolean playerMove(int index) {
        if (index < 0 || index >= 9 || !board.get(index).isEmpty()) {
            return false; // Ogiltigt drag
        }
        board.set(index, "O");
        return true;
    }

    // Datorns drag
    public void computerMove() {
        List<Integer> availableCells = new ArrayList<>();
        for (int i = 0; i < 9; i++) {
            if (board.get(i).isEmpty()) {
                availableCells.add(i);
            }
        }
        if (!availableCells.isEmpty()) {
            int randomIndex = RANDOM.nextInt(availableCells.size());
            board.set(availableCells.get(randomIndex), "X");
        }
    }

    // Kontrollera vinnare
    public String checkWinner() {
        String[][] winPatterns = {
                // Rader
                {board.get(0), board.get(1), board.get(2)},
                {board.get(3), board.get(4), board.get(5)},
                {board.get(6), board.get(7), board.get(8)},
                // Kolumner
                {board.get(0), board.get(3), board.get(6)},
                {board.get(1), board.get(4), board.get(7)},
                {board.get(2), board.get(5), board.get(8)},
                // Diagonaler
                {board.get(0), board.get(4), board.get(8)},
                {board.get(2), board.get(4), board.get(6)}
        };

        for (String[] pattern : winPatterns) {
            if ("OOO".equals(String.join("", pattern))) return "O";
            if ("XXX".equals(String.join("", pattern))) return "X";
        }

        return null; // Ingen vinnare
    }

    public boolean isBoardFull() {
        return board.stream().noneMatch(String::isEmpty);
    }

    // Återställ spelplanen
    public void reset() {
        for (int i = 0; i < board.size(); i++) {
            board.set(i, "");
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

    public List<String> getBoard() {
        return board;
    }
}

