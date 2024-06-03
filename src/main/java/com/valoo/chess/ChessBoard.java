package com.valoo.chess;

import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

public class ChessBoard {
    private VBox board;

    public ChessBoard() {
        board = new VBox();
        createBoard();
        placePieces();
    }

    private void createBoard() {
        for (int i = 0; i < 8; i++) {
            HBox row = new HBox();
            for (int j = 0; j < 8; j++) {
                StackPane square = new StackPane();
                square.setPrefSize(100, 100);
                if ((i + j) % 2 == 0) {
                    square.setStyle("-fx-background-color: #7D945D");
                } else {
                    square.setStyle("-fx-background-color: #EEEED5");
                }
                row.getChildren().add(square);
            }
            board.getChildren().add(row);
        }
    }

    private void placePieces() {
        Piece[][] pieces = {
                {new Tour("blanc", "tour", 0, 0, 0), new Cavalier("blanc", "cavalier", 0, 1, 0), new Fou("blanc", "fou", 0, 2, 0), new Reine("blanc", "reine", 0, 3, 0), new Roi("blanc", "roi", 0, 4, 0), new Fou("blanc", "fou", 0, 5, 0), new Cavalier("blanc", "cavalier", 0, 6, 0), new Tour("blanc", "tour", 0, 7, 0)},
                {new Pion("blanc", "pion", 0, 0, 1), new Pion("blanc", "pion", 0, 1, 1), new Pion("blanc", "pion", 0, 2, 1), new Pion("blanc", "pion", 0, 3, 1), new Pion("blanc", "pion", 0, 4, 1), new Pion("blanc", "pion", 0, 5, 1), new Pion("blanc", "pion", 0, 6, 1), new Pion("blanc", "pion", 0, 7, 1)},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {new Pion("noir", "pion", 1, 0, 6), new Pion("noir", "pion", 1, 1, 6), new Pion("noir", "pion", 1, 2, 6), new Pion("noir", "pion", 1, 3, 6), new Pion("noir", "pion", 1, 4, 6), new Pion("noir", "pion", 1, 5, 6), new Pion("noir", "pion", 1, 6, 6), new Pion("noir", "pion", 1, 7, 6)},
                {new Tour("noir", "tour", 1, 0, 7), new Cavalier("noir", "cavalier", 1, 1, 7), new Fou("noir", "fou", 1, 2, 7), new Reine("noir", "reine", 1, 3, 7), new Roi("noir", "roi", 1, 4, 7), new Fou("noir", "fou", 1, 5, 7), new Cavalier("noir", "cavalier", 1, 6, 7), new Tour("noir", "tour", 1, 7, 7)}
        };

        for (int i = 0; i < 8; i++) {
            HBox row = (HBox) board.getChildren().get(i);
            for (int j = 0; j < 8; j++) {
                StackPane square = (StackPane) row.getChildren().get(j);
                Piece piece = pieces[i][j];
                if (piece != null) {
                    ImageView pieceView = new ImageView(piece.getImagePiece());
                    pieceView.setFitWidth(90);
                    pieceView.setFitHeight(90);
                    square.getChildren().add(pieceView);
                }
            }
        }

    }

    // methode getPiece qui renvoie la piece aux coordonnées x et y fournies en paramètre
    public Piece getPiece(int x, int y) {
        HBox row = (HBox) board.getChildren().get(y);
        StackPane square = (StackPane) row.getChildren().get(x);
        if (square.getChildren().size() == 0) {
            return null;
        }
        ImageView pieceView = (ImageView) square.getChildren().get(0);
        return (Piece) pieceView.getUserData();
    }


    public VBox getBoard() {
        return board;
    }
}
