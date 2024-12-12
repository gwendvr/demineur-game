package com.demineur;

import javafx.scene.control.Button;

import java.util.Random;

public class Game {

    private final Grille grille;
    private DemineurController gameController; // Référence au contrôleur
    private boolean estTermine = false;

    // Constructeur modifié pour inclure le contrôleur
    public Game(int largeur, int hauteur, int nombreDeMines, DemineurController gameController) {
        this.gameController = gameController;
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
        if (estTermine) return; // Ne rien faire si le jeu est déjà terminé

        Cellule cellule = grille.getCellule(i, j);
        if (cellule.isEstMinee()) {
            // Fin de jeu si une mine est touchée
            gameOver();
        } else {
            // Afficher le nombre de mines voisines
            cellule.getButton().setText(String.valueOf(cellule.getVoisinsMines()));

            // Si la cellule a zéro mine voisine, on révèle ses voisins
            if (cellule.getVoisinsMines() == 0) {
                revealNeighbours(i, j); // Révéler les voisins si la cellule est vide
            }
        }

        // Mettre à jour la grille après chaque clic
        gameController.updateGrid(i, j);
    }

    private void revealNeighbours(int i, int j) {
        // Parcourir les cellules voisines
        for (int x = i - 1; x <= i + 1; x++) {
            for (int y = j - 1; y <= j + 1; y++) {
                if (x >= 0 && x < grille.getLargeur() && y >= 0 && y < grille.getHauteur()) {
                    Cellule neighbour = grille.getCellule(x, y);
                    Button neighbourButton = neighbour.getButton();

                    // S'assurer que la cellule voisine n'est pas déjà révélée
                    if (!neighbourButton.isDisable()) {
                        neighbourButton.setText(String.valueOf(neighbour.getVoisinsMines()));
                        neighbourButton.setDisable(true); // Désactiver la cellule après la révéler

                        // Si cette cellule a zéro mine voisine, révéler ses voisins aussi
                        if (neighbour.getVoisinsMines() == 0) {
                            revealNeighbours(x, y); // Appel récursif pour révéler les voisins
                        }
                    }
                }
            }
        }
    }


    private void gameOver() {
        estTermine = true;
        // Vous pouvez réinitialiser le jeu ici ou proposer une option pour recommencer
    }

    public Grille getGrille() {
        return grille;
    }
}
