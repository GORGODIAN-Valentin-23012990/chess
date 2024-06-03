package com.valoo.chess;

import javafx.scene.image.Image;

public class Piece {
    private Image image;

    public Piece(String color, String type) {
        String imagePath = String.format("/%s_%s.png", type, color);
        image = new Image(getClass().getResource(imagePath).toString());
    }

    public Image getImage() {
        return image;
    }
}
