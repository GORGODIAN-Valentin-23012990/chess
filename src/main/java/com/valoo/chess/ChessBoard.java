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
                {new Piece("noir", "tour"), new Piece("noir", "cavalier"), new Piece("noir", "fou"), new Piece("noir", "reine"), new Piece("noir", "roi"), new Piece("noir", "fou"), new Piece("noir", "cavalier"), new Piece("noir", "tour")},
                {new Piece("noir", "pion"), new Piece("noir", "pion"), new Piece("noir", "pion"), new Piece("noir", "pion"), new Piece("noir", "pion"), new Piece("noir", "pion"), new Piece("noir", "pion"), new Piece("noir", "pion")},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {new Piece("blanc", "pion"), new Piece("blanc", "pion"), new Piece("blanc", "pion"), new Piece("blanc", "pion"), new Piece("blanc", "pion"), new Piece("blanc", "pion"), new Piece("blanc", "pion"), new Piece("blanc", "pion")},
                {new Piece("blanc", "tour"), new Piece("blanc", "cavalier"), new Piece("blanc", "fou"), new Piece("blanc", "reine"), new Piece("blanc", "roi"), new Piece("blanc", "fou"), new Piece("blanc", "cavalier"), new Piece("blanc", "tour")}
        };

        for (int i = 0; i < 8; i++) {
            HBox row = (HBox) board.getChildren().get(i);
            for (int j = 0; j < 8; j++) {
                StackPane square = (StackPane) row.getChildren().get(j);
                Piece piece = pieces[i][j];
                if (piece != null) {
                    ImageView pieceView = new ImageView(piece.getImage());
                    pieceView.setFitWidth(90);
                    pieceView.setFitHeight(90);
                    square.getChildren().add(pieceView);
                }
            }
        }
    }

    public VBox getBoard() {
        return board;
    }
}
