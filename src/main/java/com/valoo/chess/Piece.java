package com.valoo.chess;

import javafx.scene.image.Image;

public abstract class Piece {
    private Image image;
    private int couleur;
    private int x;
    private int y;

    private String type;

    public Piece(String color, String type) {
        String imagePath = String.format("/%s_%s.png", type, color);
        image = new Image(getClass().getResource(imagePath).toString());
    }

    public Piece(String color, String type, int couleur, int x, int y) {
        this.couleur = couleur;
        this.x = x;
        this.y = y;
        this.type = type;
        String imagePath = String.format("/%s_%s.png", type, color);
        image = new Image(getClass().getResource(imagePath).toString());
    }

    public Image getImagePiece() {
        return image;
    }

    public abstract int[][] validMoves(ChessBoard board);


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

    public String getCouleurString() {
        return type;
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }

}
