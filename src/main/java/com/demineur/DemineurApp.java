package com.demineur;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class DemineurApp extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        // Charger le menu
        FXMLLoader menuLoader = new FXMLLoader(getClass().getResource("menu.fxml"));
        VBox menuRoot = menuLoader.load();

        // Charger la grille de jeu
        FXMLLoader gameLoader = new FXMLLoader(getClass().getResource("main.fxml"));
        GridPane gameRoot = gameLoader.load();
        DemineurController gameController = gameLoader.getController();

        // Créer une seule scène avec le menu comme contenu initial
        Scene mainScene = new Scene(menuRoot, 800, 600);

        // Configurer le contrôleur de menu pour basculer les contenus
        DemineurMenuController menuController = menuLoader.getController();
        menuController.setGameRoot(gameRoot, mainScene, primaryStage, gameController);

        primaryStage.setScene(mainScene);
        primaryStage.setTitle("Démineur");

        // Activer le plein écran
        primaryStage.setFullScreen(true);

        primaryStage.show();
    }



    public static void main(String[] args) {
        launch(args);
    }
}
