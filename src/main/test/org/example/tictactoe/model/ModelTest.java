package org.example.tictactoe.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ModelTest {
    private Model model;

    @BeforeEach
    void setUp() {
        model = new Model();
    }

    @Test
    void playerMoveUpdatesBoardCorrectly() {
        boolean result = model.playerMove(0);
        List<String> board = model.getBoard();

        assertTrue(result, "Player move should be successful");
        assertEquals("O", board.get(0), "The board should reflect the player's move");
    }

    @Test
    void playerCannotMoveToOccupiedCell() {
        model.playerMove(0);
        boolean result = model.playerMove(0);

        assertFalse(result, "Player move should fail if cell is occupied");
    }

    @Test
    void computerMoveUpdatesBoardCorrectly() {
        model.computerMove();
        List<String> board = model.getBoard();

        long xCount = board.stream().filter(cell -> cell.equals("X")).count();
        assertEquals(1, xCount, "Computer should place exactly one 'X' on the board");
    }

    @Test
    void checkWinnerIdentifiesRowWinner() {
        model.playerMove(0);
        model.playerMove(1);
        model.playerMove(2);

        String winner = model.checkWinner();
        assertEquals("O", winner, "The model should recognize 'O' as the winner for a row");
    }

    @Test
    void checkWinnerIdentifiesColumnWinner() {
        model.playerMove(0);
        model.playerMove(3);
        model.playerMove(6);

        String winner = model.checkWinner();
        assertEquals("O", winner, "The model should recognize 'O' as the winner for a column");
    }

    @Test
    void checkWinnerIdentifiesDiagonalWinner() {
        model.playerMove(0);
        model.playerMove(4);
        model.playerMove(8);

        String winner = model.checkWinner();
        assertEquals("O", winner, "The model should recognize 'O' as the winner for a diagonal");
    }

    @Test
    void noWinnerIfBoardIsEmpty() {
        String winner = model.checkWinner();
        assertNull(winner, "There should be no winner on an empty board");
    }

    @Test
    void isBoardFullDetectsFullBoard() {
        for (int i = 0; i < 9; i++) {
            model.playerMove(i);
        }
        assertTrue(model.isBoardFull(), "The board should be full after all cells are occupied");
    }

    @Test
    void resetClearsTheBoard() {
        model.playerMove(0);
        model.reset();

        List<String> board = model.getBoard();
        assertTrue(board.stream().allMatch(String::isEmpty), "All cells should be empty after reset");
    }
}
