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
            gameOver(); // Fin de jeu si une mine est touchée
        } else {
            cellule.getButton().setText(String.valueOf(cellule.getVoisinsMines())); // Afficher le nombre de mines voisines
            if (cellule.getVoisinsMines() == 0) {
                revealNeighbours(i, j); // Révéler les voisins si la cellule est vide
            }
        }

        // Ne désactivez le bouton que si ce n'est pas un drapeau
        if (!cellule.isEstDrapeau()) {
            gameController.updateGrid(i, j); // Mise à jour de la grille après chaque clic
        }
        checkVictory();  // Vérifier la victoire après chaque clic
    }

    public void handleRightClick(int i, int j) {
        if (estTermine) return; // Ne rien faire si le jeu est déjà terminé

        Cellule cellule = grille.getCellule(i, j);
        Button button = cellule.getButton();

        // On vérifie que la cellule n'est pas déjà révélée (d'où isDisable()).
        if (cellule.isEstDrapeau() || button.isDisable()) {
            return; // Si la cellule est déjà marquée d'un drapeau ou révélée, ne rien faire
        }

        // Si la cellule est une mine, on permet de placer un drapeau dessus
        if (!cellule.isEstMinee()) {
            return; // Ne pas mettre de drapeau si ce n'est pas une mine
        }

        // Si la cellule a un drapeau, on le retire, sinon on le place
        if (cellule.isEstDrapeau()) {
            cellule.setEstDrapeau(false);  // Retirer le drapeau
            button.setStyle(""); // Réinitialiser le style
        } else {
            cellule.setEstDrapeau(true);   // Placer le drapeau
            button.setStyle("-fx-background-color: blue;"); // Colorier la cellule en bleu
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
            // Afficher le message de victoire si toutes les cellules non-minées sont révélées
            gameController.showVictoryMessage("Félicitations! Vous avez gagné.");
            showAvoidedMines();
        }
    }

    private void showAvoidedMines() {
        for (int row = 0; row < grille.getLargeur(); row++) {
            for (int col = 0; col < grille.getHauteur(); col++) {
                Cellule cell = grille.getCellule(row, col);
                Button button = cell.getButton();

                // Si c'est une mine et que la cellule n'a pas été révélée (non désactivée)
                if (cell.isEstMinee() && !button.isDisable()) {
                    button.setStyle("-fx-background-color: red;");  // Colorier la mine en rouge
                }
            }
        }
    }

    public Grille getGrille() {
        return grille;
    }
}
