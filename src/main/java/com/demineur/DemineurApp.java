package com.demineur;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public class DemineurApp extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        // Charger le menu
        FXMLLoader menuLoader = new FXMLLoader(getClass().getResource("menu.fxml"));
        Scene menuScene = new Scene(menuLoader.load());

        // Charger le fichier FXML de la grille
        FXMLLoader gridLoader = new FXMLLoader(getClass().getResource("main.fxml"));
        GridPane gridRoot = gridLoader.load();  // Assurez-vous que c'est un GridPane
        DemineurController gameController = gridLoader.getController();  // Récupérer le contrôleur du jeu
        Scene gridScene = new Scene(gridRoot, 800, 600);

        // Passer à la scène du menu lorsque l'application démarre
        primaryStage.setScene(menuScene);
        primaryStage.setTitle("Démineur");

        // Gérer les événements du menu
        DemineurMenuController menuController = menuLoader.getController();
        menuController.setGameScene(gridScene, primaryStage);  // Assurez-vous que vous avez cette méthode dans le contrôleur du menu

        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
