package com.demineur;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

import java.io.IOException;

public class DemineurMenuController {

    private Stage stage;
    private DemineurController gameController;
    private StackPane gameRoot;

    // Méthode pour initialiser le stage dans le contrôleur
    public void setStage(Stage stage) {
        this.stage = stage;
    }

    // Méthode pour initialiser la racine de la scène du jeu, le stage et le contrôleur du jeu
    public void setGameRoot(StackPane gameRoot, Stage primaryStage, DemineurController gameController) {
        System.out.println("Débogage : Stage avant affectation : " + primaryStage);
        this.stage = primaryStage;
        this.gameRoot = gameRoot;
        this.gameController = gameController;

        System.out.println("Débogage : Stage après affectation : " + this.stage);
        gameController.setStage(primaryStage);
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

    // Méthode pour démarrer le jeu avec les paramètres spécifiés
    private void startGame(int largeur, int hauteur, int nombreDeMines) {
        if (stage == null) {
            System.err.println("Erreur : le stage est null !");
            return;
        }

        try {
            FXMLLoader gameLoader = new FXMLLoader(getClass().getResource("main.fxml"));
            StackPane gameRoot = gameLoader.load();

            DemineurController gameController = gameLoader.getController();
            if (gameController != null) {
                gameController.setStage(stage);
                gameController.setGridSize(largeur, hauteur, nombreDeMines);

                Scene gameScene = new Scene(gameRoot, 800, 600);
                stage.setScene(gameScene);
                stage.show();
            } else {
                System.err.println("Erreur : le contrôleur du jeu n'a pas été initialisé !");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
