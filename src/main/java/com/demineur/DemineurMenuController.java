package com.demineur;

import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class DemineurMenuController {

    private Stage stage;
    private DemineurController gameController;
    private StackPane gameRoot;

    public void setGameRoot(StackPane gameRoot, Stage primaryStage, DemineurController gameController) {
        this.stage = primaryStage;
        this.gameRoot = gameRoot;
        this.gameController = gameController;
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
            gameController.setGridSize(largeur, hauteur, nombreDeMines);
            Scene gameScene = new Scene(gameRoot, 800, 600);
            stage.setScene(gameScene);  // Passe à la scène de jeu
        } else {
            System.err.println("Erreur : le contrôleur du jeu est null.");
        }
    }
}
