package com.demineur;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public class DemineurApp extends Application {

    private Game game;

    @Override
    public void start(Stage primaryStage) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("main.fxml"));
        GridPane gridPane = loader.load();

        // Initialisation du jeu et de la grille
        game = new Game(10, 10, 20); // Grille 10x10 avec 20 mines
        DemineurController controller = loader.getController();
        controller.setGame(game);

        Scene scene = new Scene(gridPane);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Démineur");
        primaryStage.show();

        // Initialisation de la grille et des boutons
        controller.initializeGrid(gridPane);
    }

    public static void main(String[] args) {
        launch(args);
    }
}
