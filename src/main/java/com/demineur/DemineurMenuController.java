package com.demineur;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;

public class DemineurMenuController {

    private Stage stage;
    private DemineurController gameController;
    private StackPane gameRoot;

    public void setGameRoot(StackPane gameRoot, Stage primaryStage, DemineurController gameController) {
        this.stage = primaryStage;
        this.gameRoot = gameRoot;
        this.gameController = gameController;

        gameController.setStage(primaryStage);
    }

    public void handleBackToMenu(Stage stage) {
        try {
            // Charger la scène du menu (menu.fxml)
            FXMLLoader loader = new FXMLLoader(getClass().getResource("menu.fxml"));
            VBox root = loader.load();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void handleEasyButton() {
        System.out.println("Mode facile sélectionné !");
        startGame(10, 10, 5);
    }

    @FXML
    private void handleMediumButton() {
        System.out.println("Mode moyen sélectionné !");
        startGame(15, 15, 30);
    }

    @FXML
    private void handleHardButton() {
        System.out.println("Mode difficile sélectionné !");
        startGame(20, 20, 50);
    }

    private void startGame(int largeur, int hauteur, int nombreDeMines) {
        if (gameController != null) {
            gameController.setGridSize(largeur, hauteur, nombreDeMines);  // Assurez-vous que cette méthode est bien définie
            Scene gameScene = new Scene(gameRoot, 800, 600);
            stage.setScene(gameScene);  // Passe à la scène de jeu
        } else {
            System.err.println("Erreur : le contrôleur du jeu est null.");
        }
    }
}
