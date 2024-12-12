package com.demineur;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.control.Label;
import javafx.scene.paint.Color;

public class DemineurController {

    @FXML
    private GridPane gridPane;

    @FXML
    private VBox messageBox;  // Pour afficher le message

    private Game game;

    // Cette méthode est appelée pour initialiser la grille du jeu
    public void setGridSize(int rows, int columns, int numMines) {
        game = new Game(rows, columns, numMines, this); // Passer "this" comme contrôleur
        initializeGrid(rows, columns);
    }

    // Cette méthode crée et initialise les boutons de la grille
    private void initializeGrid(int rows, int columns) {
        gridPane.getChildren().clear(); // On efface la grille précédente

        for (int row = 0; row < rows; row++) {
            for (int col = 0; col < columns; col++) {
                Button cellButton = new Button();
                cellButton.setPrefSize(30, 30);

                int finalRow = row;
                int finalCol = col;

                // Ajouter un événement de clic sur chaque bouton
                cellButton.setOnAction(event -> {
                    game.handleClick(finalRow, finalCol);
                });

                gridPane.add(cellButton, col, row);
                game.getGrille().getCellule(row, col).setButton(cellButton);
            }
        }
    }

    // Met à jour la grille après chaque clic
    public void updateGrid(int row, int col) {
        Cellule cell = game.getGrille().getCellule(row, col);
        Button button = cell.getButton();

        // Si une mine est détectée, colorer cette seule cellule en rouge
        if (cell.isEstMinee()) {
            button.setStyle("-fx-background-color: red;");
            showGameOverMessage("Oh non ! Vous avez perdu.");
            return; // Terminer immédiatement la mise à jour
        }

        // Afficher le nombre de mines voisines
        if (cell.getVoisinsMines() > 0) {
            button.setText(String.valueOf(cell.getVoisinsMines()));
        } else {
            button.setText(""); // Afficher rien si aucune mine voisine
        }

        button.setDisable(true); // Désactiver le bouton après un clic
    }

    // Affiche un message de fin de jeu
    public void showGameOverMessage(String message) {
        messageBox.getChildren().clear();  // Effacer tout autre contenu
        Label gameOverLabel = new Label(message);
        gameOverLabel.setTextFill(Color.WHITE);
        gameOverLabel.setStyle("-fx-font-size: 25px; -fx-font-weight: bold;");

        messageBox.getChildren().add(gameOverLabel); // Ajouter le message à l'interface
        messageBox.setVisible(true);  // Rendre le message visible
    }

    // Affiche un message de victoire
    public void showVictoryMessage(String message) {
        messageBox.getChildren().clear();  // Effacer tout autre contenu
        Label victoryLabel = new Label(message);
        victoryLabel.setTextFill(Color.WHITE);

        // Appliquer les styles ici pour un message plus grand et en gras
        victoryLabel.setStyle("-fx-font-size: 25px; -fx-font-weight: bold;");

        messageBox.getChildren().add(victoryLabel);
        messageBox.setVisible(true);  // Rendre le message visible
    }
}
