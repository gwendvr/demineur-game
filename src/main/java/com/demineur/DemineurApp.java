package com.demineur;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class DemineurApp extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        // Charger le menu
        FXMLLoader menuLoader = new FXMLLoader(getClass().getResource("menu.fxml"));
        VBox menuRoot = menuLoader.load();

        // Charger la scène de jeu avec GridPane (plutôt que StackPane)
        FXMLLoader gameLoader = new FXMLLoader(getClass().getResource("main.fxml"));
        StackPane gameRoot = gameLoader.load(); // L'élément racine est StackPane, ce qui est correct ici
        DemineurController gameController = gameLoader.getController();

        if (gameController == null) {
            System.err.println("Erreur : gameController est null à l'initialisation !");
            return;
        }

        gameController.setStage(primaryStage);

        // Créer une scène pour le menu
        Scene mainScene = new Scene(menuRoot, 800, 600);

        // Configurer l'application pour passer au jeu à partir du menu
        DemineurMenuController menuController = menuLoader.getController();
        menuController.setGameRoot(gameRoot, primaryStage, gameController);  // Corrigé le paramètre

        // Afficher la scène du menu
        primaryStage.setScene(mainScene);
        primaryStage.setTitle("Démineur");

        // Configurer l'application en plein écran
        primaryStage.setFullScreen(true);

        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
