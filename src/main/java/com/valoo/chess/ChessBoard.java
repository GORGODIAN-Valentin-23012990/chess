package com.valoo.chess;

import javafx.scene.Node;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

public class ChessBoard {
    private VBox board;
    private Piece[][] matPiece;
    private Piece selectedPiece;
    private int tour;

    public ChessBoard() {
        board = new VBox();
        tour = 0;
        createBoard();
        placePieces();
    }

    private void createBoard() {
        for (int i = 0; i < 8; i++) {
            HBox row = new HBox();
            for (int j = 0; j < 8; j++) {
                StackPane square = new StackPane();
                square.setMinHeight(90);
                square.setMinWidth(90);
                if ((i + j) % 2 == 0) {
                    square.setStyle("-fx-background-color: #7D945D");
                } else {
                    square.setStyle("-fx-background-color: #EEEED5");
                }
                int x = j;
                int y = i;
                square.setOnMouseClicked(event -> handleSquareClick(x, y));
                row.getChildren().add(square);
            }
            board.getChildren().add(row);
        }
    }

    private void handleSquareClick(int x, int y) {
        Piece clickedPiece = getPiece(x, y);

        // Vérifier si le clic est sur une pièce de l'adversaire
        if (clickedPiece != null && clickedPiece.getCouleur() != tour % 2) {
            // Permettre de capturer la pièce
            if (selectedPiece != null && movePiece(selectedPiece.getX(), selectedPiece.getY(), x, y)) {
                selectedPiece = null;
                updateBoard();
                colorBoard();
                tour++;  // Changer de tour après un mouvement valide
                return;
            }
        }

        if (selectedPiece == null) {
            selectPiece(x, y);
        } else {
            if (movePiece(selectedPiece.getX(), selectedPiece.getY(), x, y)) {
                selectedPiece = null;
                updateBoard();
                colorBoard();
                tour++;  // Changer de tour après un mouvement valide
            } else {
                // Mouvement invalide, réinitialiser la sélection
                selectedPiece = null;
                colorBoard();
            }
        }
    }

    private void placePieces() {
        matPiece = new Piece[][]{
                {new Tour("blanc", "tour", 0, 0, 0), new Cavalier("blanc", "cavalier", 0, 1, 0), new Fou("blanc", "fou", 0, 2, 0), new Reine("blanc", "reine", 0, 3, 0), new Roi("blanc", "roi", 0, 4, 0), new Fou("blanc", "fou", 0, 5, 0), new Cavalier("blanc", "cavalier", 0, 6, 0), new Tour("blanc", "tour", 0, 7, 0)},
                {new Pion("blanc", "pion", 0, 0, 1), new Pion("blanc", "pion", 0, 1, 1), new Pion("blanc", "pion", 0, 2, 1), new Pion("blanc", "pion", 0, 3, 1), new Pion("blanc", "pion", 0, 4, 1), new Pion("blanc", "pion", 0, 5, 1), new Pion("blanc", "pion", 0, 6, 1), new Pion("blanc", "pion", 0, 7, 1)},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {new Pion("noir", "pion", 1, 0, 6), new Pion("noir", "pion", 1, 1, 6), new Pion("noir", "pion", 1, 2, 6), new Pion("noir", "pion", 1, 3, 6), new Pion("noir", "pion", 1, 4, 6), new Pion("noir", "pion", 1, 5, 6), new Pion("noir", "pion", 1, 6, 6), new Pion("noir", "pion", 1, 7, 6)},
                {new Tour("noir", "tour", 1, 0, 7), new Cavalier("noir", "cavalier", 1, 1, 7), new Fou("noir", "fou", 1, 2, 7), new Reine("noir", "reine", 1, 3, 7), new Roi("noir", "roi", 1, 4, 7), new Fou("noir", "fou", 1, 5, 7), new Cavalier("noir", "cavalier", 1, 6, 7), new Tour("noir", "tour", 1, 7, 7)}
        };

        updateBoard();
    }

    private void updateBoard() {
        for (int i = 0; i < 8; i++) {
            HBox row = (HBox) board.getChildren().get(i);
            for (int j = 0; j < 8; j++) {
                StackPane square = (StackPane) row.getChildren().get(j);
                square.getChildren().clear();
                Piece piece = matPiece[i][j];
                if (piece != null) {
                    ImageView pieceView = new ImageView(piece.getImagePiece());
                    pieceView.setFitWidth(90);
                    pieceView.setFitHeight(90);
                    pieceView.setUserData(piece);
                    square.getChildren().add(pieceView);
                }
            }
        }
    }

    public Piece getPiece(int x, int y) {
        return matPiece[y][x];
    }

    public void selectPiece(int x, int y) {
        Piece piece = getPiece(x, y);
        if (piece != null && piece.getCouleur() == tour % 2) {
            selectedPiece = piece;
            highlightValidMoves(piece);
        }
    }

    private void highlightValidMoves(Piece piece) {
        int[][] validMoves = piece.validMoves(this);
        for (int[] move : validMoves) {
            int x = move[0];
            int y = move[1];
            StackPane targetSquare = (StackPane) ((HBox) board.getChildren().get(y)).getChildren().get(x);
            targetSquare.setStyle("-fx-background-color: #E2C64B");
        }
    }

    public boolean movePiece(int currentX, int currentY, int targetX, int targetY) {
        Piece piece = getPiece(currentX, currentY);
        if (piece != null) {
            int[][] validMoves = piece.validMoves(this);
            for (int[] move : validMoves) {
                if (move[0] == targetX && move[1] == targetY) {
                    Piece targetPiece = getPiece(targetX, targetY);
                    if (targetPiece == null || targetPiece.getCouleur() != piece.getCouleur()) {
                        // On vérifie si le roi roque ; si c'est le cas la tour doit bouger aussi
                        if (piece instanceof Roi && Math.abs(currentX - targetX) == 2) {
                            int tourX = targetX == 6 ? 7 : 0;
                            int tourY = piece.getCouleur() == 0 ? 0 : 7;
                            Piece tour = getPiece(tourX, tourY);
                            matPiece[tourY][tourX] = null;
                            matPiece[tourY][targetX == 6 ? 5 : 3] = tour;
                            tour.setX(targetX == 6 ? 5 : 3);
                            tour.setY(tourY);
                        }

                        if(targetPiece instanceof Roi) {
                            System.out.println("Partie terminée");
                        }

                        matPiece[targetY][targetX] = piece;
                        matPiece[currentY][currentX] = null;

                        // Gestion de la promotion des pions
                        if (piece instanceof Pion && (targetY == 0 || targetY == 7)) {
                            matPiece[targetY][targetX] = new Reine(piece.getCouleur() == 0 ? "blanc" : "noir", "reine", piece.getCouleur(), targetX, targetY);
                        }

                        piece.setX(targetX);
                        piece.setY(targetY);
                        return true;
                    }
                }
            }
        }
        return false;
    }

    private void colorBoard() {
        for (int i = 0; i < 8; i++) {
            HBox row = (HBox) board.getChildren().get(i);
            for (int j = 0; j < 8; j++) {
                StackPane square = (StackPane) row.getChildren().get(j);
                if ((i + j) % 2 == 0) {
                    square.setStyle("-fx-background-color: #7D945D");
                } else {
                    square.setStyle("-fx-background-color: #EEEED5");
                }
            }
        }
    }

    public VBox getBoard() {
        return board;
    }
}
