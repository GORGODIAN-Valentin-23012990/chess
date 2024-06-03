package com.valoo.chess;

import javafx.scene.image.Image;

public class Piece {
    private Image image;
    private int couleur;
    private int x;
    private int y;

    public Piece(String color, String type) {
        String imagePath = String.format("/%s_%s.png", type, color);
        image = new Image(getClass().getResource(imagePath).toString());
    }

    public Piece(String color, String type, int couleur, int x, int y) {
        this.couleur = couleur;
        this.x = x;
        this.y = y;
        String imagePath = String.format("/%s_%s.png", type, color);
        image = new Image(getClass().getResource(imagePath).toString());
    }

    public Image getImagePiece() {
        return image;
    }


    public int getCouleur() {
        return couleur;
    }

    public boolean estMemeCouleur(Piece piece) {
        return this.getCouleur() == piece.getCouleur();
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }

}
