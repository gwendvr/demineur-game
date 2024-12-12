package com.demineur;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;

public class DemineurController {

    @FXML
    private GridPane gridPane;  // Déclaration du GridPane

    public void setGridSize(int rows, int columns, int numMines) {
        // Appelez la méthode initializeGrid ici
        initializeGrid(rows, columns);
    }


    // Cette méthode est appelée pour initialiser la grille
    private void initializeGrid(int rows, int columns) {
        if (gridPane == null) {
            System.err.println("Le GridPane est nul !");
            return;
        }
        gridPane.getChildren().clear();  // Effacer les anciennes cellules de la grille

        // Créez les cellules du jeu ici (ajouter des boutons, etc.)
        for (int row = 0; row < rows; row++) {
            for (int col = 0; col < columns; col++) {
                // Créez et ajoutez les boutons à la grille, par exemple
                Button cell = new Button();
                gridPane.add(cell, col, row);
            }
        }
    }
}
