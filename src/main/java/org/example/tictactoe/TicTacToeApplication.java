
package org.example.tictactoe;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class TicTacToeApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {

        FXMLLoader fxmlLoader = new FXMLLoader(TicTacToeApplication.class.getResource("tictactoe-view.fxml"));

        Scene scene = new Scene(fxmlLoader.load(), 545, 400);

        stage.setTitle("Tic Tac Toe");
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}
