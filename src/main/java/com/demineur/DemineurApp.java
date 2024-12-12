package com.demineur;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class DemineurApp extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        // Charger le menu
        FXMLLoader menuLoader = new FXMLLoader(getClass().getResource("menu.fxml"));
        Scene menuScene = new Scene(menuLoader.load());

        // Charger la grille de jeu
        FXMLLoader gameLoader = new FXMLLoader(getClass().getResource("main.fxml"));
        Scene gameScene = new Scene(gameLoader.load());
        DemineurController gameController = gameLoader.getController();

        // Configurer le contrôleur du menu pour basculer entre les scènes
        DemineurMenuController menuController = menuLoader.getController();
        menuController.setGameScene(gameScene, primaryStage, gameController);

        // Démarrer avec la scène du menu
        primaryStage.setScene(menuScene);
        primaryStage.setTitle("Démineur");
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
