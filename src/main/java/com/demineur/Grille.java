package com.demineur;

import java.util.ArrayList;
import java.util.List;

public class Grille {

    private final int largeur;
    private final int hauteur;
    private final Cellule[][] cellules;

    public Grille(int largeur, int hauteur) {
        this.largeur = largeur;
        this.hauteur = hauteur;
        this.cellules = new Cellule[largeur][hauteur];

        for (int i = 0; i < largeur; i++) {
            for (int j = 0; j < hauteur; j++) {
                cellules[i][j] = new Cellule(i, j);
            }
        }
    }

    public Cellule getCellule(int i, int j) {
        return cellules[i][j];
    }

    public int getLargeur() {
        return largeur;
    }

    public int getHauteur() {
        return hauteur;
    }

    public List<Cellule> getVoisines(int i, int j) {
        List<Cellule> voisines = new ArrayList<>();
        for (int x = -1; x <= 1; x++) {
            for (int y = -1; y <= 1; y++) {
                int newX = i + x;
                int newY = j + y;
                if (newX >= 0 && newX < largeur && newY >= 0 && newY < hauteur && !(x == 0 && y == 0)) {
                    voisines.add(cellules[newX][newY]);
                }
            }
        }
        return voisines;
    }
}
