package com.demineur;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.layout.GridPane;

import java.util.Random;

public class Game {

    private final Grille grille;
    private boolean estTermine = false;

    public Game(int largeur, int hauteur, int nombreDeMines) {
        this.grille = new Grille(largeur, hauteur);
        placerMines(nombreDeMines);
    }

    private void placerMines(int nombreDeMines) {
        Random random = new Random();
        int minesPlacees = 0;

        while (minesPlacees < nombreDeMines) {
            int i = random.nextInt(grille.getLargeur());
            int j = random.nextInt(grille.getHauteur());

            if (!grille.getCellule(i, j).isEstMinee()) {
                grille.getCellule(i, j).setEstMinee(true);
                minesPlacees++;

                // Mise à jour des voisins
                for (Cellule celluleVoisine : grille.getVoisines(i, j)) {
                    celluleVoisine.setVoisinsMines(celluleVoisine.getVoisinsMines() + 1);
                }
            }
        }
    }

    public void handleClick(int i, int j) {
        if (estTermine) return;

        Cellule cellule = grille.getCellule(i, j);
        if (cellule.isEstMinee()) {
            // Fin de jeu si une mine est touchée
            gameOver();
        } else {
            // Afficher le nombre de mines voisines
            cellule.getButton().setText(String.valueOf(cellule.getVoisinsMines()));
        }
    }

    private void gameOver() {
        estTermine = true;
        Alert alert = new Alert(Alert.AlertType.INFORMATION, "Game Over!", ButtonType.OK);
        alert.show();
    }

    public Grille getGrille() {
        return grille;
    }
}
