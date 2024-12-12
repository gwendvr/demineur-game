package com.demineur;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;

public class DemineurController {

    @FXML
    private GridPane gridPane;

    private Game game;

    public void setGridSize(int rows, int columns, int numMines) {
        game = new Game(rows, columns, numMines);
        initializeGrid(rows, columns);
    }

    private void initializeGrid(int rows, int columns) {
        gridPane.getChildren().clear();

        for (int row = 0; row < rows; row++) {
            for (int col = 0; col < columns; col++) {
                Button cellButton = new Button();
                cellButton.setPrefSize(30, 30);

                int finalRow = row;
                int finalCol = col;

                // Ajouter un événement de clic
                cellButton.setOnAction(event -> {
                    game.handleClick(finalRow, finalCol);
                    updateGrid();
                });

                gridPane.add(cellButton, col, row);
                game.getGrille().getCellule(row, col).setButton(cellButton);
            }
        }
    }

    private void updateGrid() {
        for (int row = 0; row < game.getGrille().getLargeur(); row++) {
            for (int col = 0; col < game.getGrille().getHauteur(); col++) {
                Cellule cell = game.getGrille().getCellule(row, col);
                if (cell.getButton() != null && !cell.getButton().getText().isEmpty()) {
                    cell.getButton().setDisable(true); // Désactiver les cellules déjà cliquées
                }
            }
        }
    }
}
