package com.demineur;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;

public class DemineurController {

    private Game game;

    @FXML
    public void initializeGrid(GridPane gridPane) {
        // Vérifiez que le gridPane est correctement initialisé
        if (gridPane == null) {
            System.out.println("Erreur : gridPane est nul");
            return;
        }

        for (int i = 0; i < game.getGrille().getLargeur(); i++) {
            for (int j = 0; j < game.getGrille().getHauteur(); j++) {
                String buttonId = "button" + i + j;  // Création d'un identifiant unique pour chaque bouton
                Button button = (Button) gridPane.lookup("#" + buttonId);

                // Vérifiez si le bouton a été trouvé
                if (button == null) {
                    System.out.println("Le bouton avec l'ID " + buttonId + " n'a pas été trouvé.");
                    continue;  // Passez au bouton suivant
                }

                // Ajoutez l'action sur le bouton
                final int finalI = i;
                final int finalJ = j;
                button.setOnAction(e -> game.handleClick(finalI, finalJ));
                Cellule cellule = game.getGrille().getCellule(i, j);
                cellule.setButton(button);  // Associer le bouton à la cellule
            }
        }
    }

    public void setGame(Game game) {
        this.game = game;
    }
}
