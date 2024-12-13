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

                for (Cellule celluleVoisine : grille.getVoisines(i, j)) {
                    celluleVoisine.setVoisinsMines(celluleVoisine.getVoisinsMines() + 1);
                }
            }
        }
    }

    public void handleClick(int i, int j) {
        if (estTermine) return;

        Cellule cellule = grille.getCellule(i, j);
        Button button = cellule.getButton();

        // Si la cellule contient un drapeau, retirer le drapeau au clic gauche
        if (cellule.isEstDrapeau()) {
            cellule.setEstDrapeau(false);
            button.setStyle("");
            return;
        }

        // Si la cellule est une mine, la fin du jeu
        if (cellule.isEstMinee()) {
            gameOver();
        } else {
            cellule.getButton().setText(String.valueOf(cellule.getVoisinsMines()));
            if (cellule.getVoisinsMines() == 0) {
                revealNeighbours(i, j);
            }
        }

        // Ne désactivez le bouton que si ce n'est pas un drapeau
        if (!cellule.isEstDrapeau()) {
            gameController.updateGrid(i, j);
        }
        checkVictory();
    }


    public void handleRightClick(int i, int j) {
        if (estTermine) return;

        Cellule cellule = grille.getCellule(i, j);
        Button button = cellule.getButton();

        if (button.isDisable()) {
            return;
        }

        // Si la cellule a un drapeau, on le retire, sinon on le place
        if (cellule.isEstDrapeau()) {
            cellule.setEstDrapeau(false);
            button.setStyle("");
        } else {
            cellule.setEstDrapeau(true);
            button.setStyle("-fx-background-color: blue;");
        }
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
                        neighbourButton.setDisable(true);

                        // révéler ses voisins aussi si 0
                        if (neighbour.getVoisinsMines() == 0) {
                            revealNeighbours(x, y);
                        }
                    }
                }
            }
        }
    }

    private void gameOver() {
        estTermine = true;
    }

    private void checkVictory() {
        boolean allCellsRevealed = true;
        for (int row = 0; row < grille.getLargeur(); row++) {
            for (int col = 0; col < grille.getHauteur(); col++) {
                Cellule cell = grille.getCellule(row, col);
                Button button = cell.getButton();

                // Vérifiez si la cellule n'est pas une mine et n'a pas encore été révélée
                if (!cell.isEstMinee() && !button.isDisable()) {
                    allCellsRevealed = false;
                    break;
                }
            }
        }

        if (allCellsRevealed) {
            gameController.showVictoryMessage("Félicitations! Vous avez gagné.");
            showAvoidedMines();
        }
    }

    private void showAvoidedMines() {
        for (int row = 0; row < grille.getLargeur(); row++) {
            for (int col = 0; col < grille.getHauteur(); col++) {
                Cellule cell = grille.getCellule(row, col);
                Button button = cell.getButton();

                if (cell.isEstMinee() && !button.isDisable()) {
                    button.setStyle("-fx-background-color: red;");
                }
            }
        }
    }

    public Grille getGrille() {
        return grille;
    }
}
