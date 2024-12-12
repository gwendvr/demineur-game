package com.demineur;

import javafx.scene.control.Button;

public class Cellule {

    private final int x;
    private final int y;
    private boolean estMinee;
    private int voisinsMines;
    private Button button;
    private boolean estDrapeau = false;

    public boolean isEstDrapeau() {
        return estDrapeau;
    }

    public void setEstDrapeau(boolean estDrapeau) {
        this.estDrapeau = estDrapeau;
    }

    public Cellule(int x, int y) {
        this.x = x;
        this.y = y;
        this.estMinee = false;
        this.voisinsMines = 0;
    }

    public boolean isEstMinee() {
        return estMinee;
    }

    public void setEstMinee(boolean estMinee) {
        this.estMinee = estMinee;
    }

    public int getVoisinsMines() {
        return voisinsMines;
    }

    public void setVoisinsMines(int voisinsMines) {
        this.voisinsMines = voisinsMines;
    }

    public Button getButton() {
        return button;
    }

    public void setButton(Button button) {
        this.button = button;
    }
}
