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

        // Charger la scène de jeu avec StackPane (plutôt que GridPane)
        FXMLLoader gameLoader = new FXMLLoader(getClass().getResource("main.fxml"));
        StackPane gameRoot = gameLoader.load();
        DemineurController gameController = gameLoader.getController();

        // Vérifier si le contrôleur du jeu est correctement chargé
        if (gameController == null) {
            System.err.println("Erreur : gameController est null à l'initialisation !");
            return;
        }

        // Passer le Stage au contrôleur de jeu
        gameController.setStage(primaryStage);

        // Créer une scène pour le menu
        Scene mainScene = new Scene(menuRoot, 800, 600);

        // Passer le root du jeu et le stage au contrôleur du menu
        DemineurMenuController menuController = menuLoader.getController();
        menuController.setGameRoot(gameRoot, primaryStage, gameController);

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
