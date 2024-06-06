package com.valoo.chess;

import javafx.scene.image.Image;


public abstract class Piece {
    private Image image;
    private int couleur;
    private int x;
    private int y;

    private String type;

    /**
     * Constructeur de la classe Piece
     * @param color couleur de la pièce
     * @param type type de la pièce
     */
    public Piece(String color, String type) {
        String imagePath = String.format("/%s_%s.png", type, color);
        image = new Image(getClass().getResource(imagePath).toString());
    }

    /**
     * Constructeur de la classe Piece
     * @param color couleur de la pièce
     * @param type type de la pièce
     * @param couleur couleur de la pièce
     * @param x position x
     * @param y position y
     */
    public Piece(String color, String type, int couleur, int x, int y) {
        this.couleur = couleur;
        this.x = x;
        this.y = y;
        this.type = type;
        String imagePath = String.format("/%s_%s.png", type, color);
        image = new Image(getClass().getResource(imagePath).toString());
    }


    /**
     *
     * @return image de la pièce
     *
     */
    public Image getImagePiece() {
        return image;
    }

    /**
     * @param board plateau de jeu
     * @return tableau de coordonnées de toutes les positions possibles
     */
    public abstract int[][] validMoves(ChessBoard board);


    /**
     * @return couleur de la pièce
     */
    public int getCouleur() {
        return couleur;
    }

    public boolean estMemeCouleur(Piece piece) {
        return this.getCouleur() == piece.getCouleur();
    }

    /**
     * @return position x
     */
    public int getX() {
        return x;
    }

    /**
     * @return position y
     */
    public int getY() {
        return y;
    }

    /**
     * @return type de la pièce
     */
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
