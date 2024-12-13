package com.demineur;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.control.Label;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.io.IOException;

public class DemineurController {

    @FXML
    private GridPane gridPane;

    @FXML
    private VBox messageBox;

    private Game game;

    private Stage stage;

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    // Cette méthode est appelée pour initialiser la grille du jeu
    public void setGridSize(int rows, int columns, int numMines) {
        game = new Game(rows, columns, numMines, this);
        initializeGrid(rows, columns);
    }

    // Cette méthode crée et initialise les boutons de la grille
    private void initializeGrid(int rows, int columns) {
        gridPane.getChildren().clear();

        for (int row = 0; row < rows; row++) {
            for (int col = 0; col < columns; col++) {
                Button cellButton = new Button();
                cellButton.setPrefSize(30, 30);

                int finalRow = row;
                int finalCol = col;

                // Ajouter un gestionnaire d'événements pour le clic droit et gauche
                cellButton.setOnMouseClicked(event -> {
                    if (!cellButton.isDisable()) {
                        if (event.getButton() == MouseButton.SECONDARY) {
                            game.handleRightClick(finalRow, finalCol);
                        } else if (event.getButton() == MouseButton.PRIMARY) {
                            game.handleClick(finalRow, finalCol);
                        }
                    }
                });

                gridPane.add(cellButton, col, row);
                game.getGrille().getCellule(row, col).setButton(cellButton);
            }
        }
    }

    @FXML
    private void handleBackToMenu() {
        try {
            // Charger la scène du menu
            FXMLLoader menuLoader = new FXMLLoader(getClass().getResource("menu.fxml"));
            VBox menuRoot = menuLoader.load();

            // Obtenez le contrôleur du menu
            DemineurMenuController menuController = menuLoader.getController();
            menuController.setStage(stage);

            // Passer à la scène du menu
            Scene menuScene = new Scene(menuRoot, 800, 600);
            stage.setScene(menuScene);

            // Afficher la scène du menu
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Met à jour la grille après chaque clic
    public void updateGrid(int row, int col) {
        Cellule cell = game.getGrille().getCellule(row, col);
        Button button = cell.getButton();

        // Si une mine est détectée
        if (cell.isEstMinee()) {
            button.setStyle("-fx-background-color: red;");
            showGameOverMessage("Oh non ! Vous avez perdu.");
            return;
        }

        // Afficher le nombre de mines voisines
        if (cell.getVoisinsMines() > 0) {
            button.setText(String.valueOf(cell.getVoisinsMines()));
        } else {
            button.setText("");
        }

        // Désactiver le bouton uniquement si ce n'est pas un drapeau
        if (!cell.isEstDrapeau() && !cell.isEstMinee()) {
            button.setDisable(true);
        }
    }

    // Affiche un message de fin de jeu
    public void showGameOverMessage(String message) {
        messageBox.getChildren().clear();
        Label gameOverLabel = new Label(message);
        gameOverLabel.setTextFill(Color.WHITE);
        gameOverLabel.setStyle("-fx-font-size: 25px; -fx-font-weight: bold;");
        messageBox.getChildren().add(gameOverLabel);

        // Rendre le VBox visible
        messageBox.setVisible(true);

        // Créer et ajouter le bouton
        Button buttonRetourMenu = new Button("Retour au Menu");
        buttonRetourMenu.setOnAction(event -> handleBackToMenu());
        messageBox.getChildren().add(buttonRetourMenu);
    }

    // Affiche un message de victoire
    public void showVictoryMessage(String message) {
        messageBox.getChildren().clear();
        Label victoryLabel = new Label(message);
        victoryLabel.setTextFill(Color.WHITE);

        // Appliquer les styles ici pour un message plus grand et en gras
        victoryLabel.setStyle("-fx-font-size: 25px; -fx-font-weight: bold;");
        messageBox.getChildren().add(victoryLabel);

        // Rendre le VBox visible
        messageBox.setVisible(true);

        // Créer et ajouter le bouton
        Button buttonRetourMenu = new Button("Retour au Menu");
        buttonRetourMenu.setOnAction(event -> handleBackToMenu());
        messageBox.getChildren().add(buttonRetourMenu);
    }

}
