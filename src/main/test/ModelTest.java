package org.example.tictactoe.model;

import javafx.application.Platform;
import javafx.scene.control.Button;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;

import static org.junit.jupiter.api.Assertions.*;

class ModelTest {

    private Model model;
    private List<Button> buttons;

    @BeforeAll
    static void initJavaFX() throws InterruptedException {
        CountDownLatch latch = new CountDownLatch(1);
        Platform.startup(latch::countDown);
        latch.await();
    }

    @BeforeEach
    void setUp() throws InterruptedException {
        buttons = new ArrayList<>();
        Platform.runLater(() -> {
            for (int i = 0; i < 9; i++) {
                buttons.add(new Button());
            }
        });

        CountDownLatch latch = new CountDownLatch(1);
        Platform.runLater(latch::countDown);
        try {
            latch.await();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        model = new Model(buttons);
    }

    @Test
    void whenPlayerMakesAMove() {
        Button button = buttons.get(0);  // Ändra från 'getFirst' till 'get(0)'
        model.playerMove(button);

        assertEquals("O", button.getText(), "The button text should be set to 'O' after player move");
        assertTrue(button.isDisable(), "The button should be disabled after player move");

        // Test för att säkerställa att knappens text inte ändras om den klickas igen
        model.playerMove(button);
        assertEquals("O", button.getText(), "Button text should remain 'O' if clicked again");
    }

    @Test
    void whenComputerMakesAMove() throws InterruptedException {
        // Kör `computerMove` på JavaFX-tråden och logga knapparnas status
        Platform.runLater(() -> {
            model.computerMove();
            buttons.forEach(b -> System.out.println("Button text: " + b.getText() + ", disabled: " + b.isDisable()));
        });

        // Vänta tills `computerMove` är klar
        CountDownLatch latch = new CountDownLatch(1);
        Platform.runLater(latch::countDown);
        latch.await();

        // Kontrollera att exakt en knapp har markerats som "X" efter datorspel
        long xCount = buttons.stream().filter(b -> "X".equals(b.getText()) && b.isDisable()).count();
        assertEquals(1, xCount, "Exactly one button should be marked as 'X' after computer move");
    }

    @Test
    void whenThereIsThreeInARow() {
        // Simulera tre-i-rad för "O"
        buttons.get(0).setText("O");
        buttons.get(1).setText("O");
        buttons.get(2).setText("O");

        String winner = model.winStates();
        assertEquals("O", winner, "The model should recognize 'O' as the winner");
    }
}


