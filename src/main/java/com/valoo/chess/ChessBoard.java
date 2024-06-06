package com.valoo.chess;

import com.valoo.chess.controller.MainController;
import com.valoo.chess.fonctionnalites.Bot;
import com.valoo.chess.fonctionnalites.FichierCoup;
import com.valoo.chess.pieces.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

public class ChessBoard {
    private VBox board;
    private Piece[][] matPiece;
    private Piece selectedPiece;
    private int tour;
    private StringBuilder coups = new StringBuilder();
    private int couleurBot;
    private Bot bot;

    private MainController mainController;
    private FichierCoup fichierCoup;


    /**
     * @param couleurBot couleur du bot
     * @param mainController controleur principal
     * Crée un plateau de jeu d'échec
     */
    public ChessBoard(int couleurBot, MainController mainController) {
        fichierCoup = new FichierCoup();
        this.mainController = mainController;
        board = new VBox();
        tour = 0;
        if(couleurBot == 0 || couleurBot == 1) {
            this.couleurBot = couleurBot;
            bot = new Bot(true);
        } else if (couleurBot == 2) {
            bot = new Bot(false);
        }
        createBoard();
        placePieces();
    }

    // La méthode resetBoard permet de réinitialiser le plateau de jeu
    public void resetBoard(){
        placePieces();
    }

    /**
     * @param couleurBot couleur du bot
     */
    public void setCouleurBot(int couleurBot) {
        this.couleurBot = couleurBot;
    }

    /**
     * @param tour tour
     */
    public void setTour(int tour) {
        this.tour = tour;
    }

    /**
     * Crée le plateau de jeu
     */
    private void createBoard() {
        for (int i = 0; i < 8; i++) {
            HBox row = new HBox();
            for (int j = 0; j < 8; j++) {
                StackPane square = new StackPane();
                square.setMinHeight(90);
                square.setMinWidth(90);
                if ((i + j) % 2 == 0) {
                    square.setStyle("-fx-background-color: #EEEED5");
                } else {
                    square.setStyle("-fx-background-color: #7D945D");
                }
                int x = j;
                int y = i;
                square.setOnMouseClicked(event -> handleSquareClick(x, y));
                row.getChildren().add(square);
            }
            board.getChildren().add(row);
            board.getStyleClass().add("board");
        }
    }

    /**
     * @param x position x
     * @param y position y
     * Gère le clic sur une case du plateau
     */
    private void handleSquareClick(int x, int y) {
        Piece clickedPiece = getPiece(x, y);

        if (clickedPiece != null && clickedPiece.getCouleur() == tour) {
            if (selectedPiece != null && movePiece(selectedPiece.getX(), selectedPiece.getY(), x, y)) {
                selectedPiece = null;
                updateBoard();
                colorBoard();
                if(tour == 0) tour = 1;
                else tour = 0;
                return;
            }
        }

        if (selectedPiece == null) {
            selectPiece(x, y);
        } else {
            if (movePiece(selectedPiece.getX(), selectedPiece.getY(), x, y)) {
                selectedPiece = null;
                mainController.switchActivePlayer();
                updateBoard();
                colorBoard();
                if(tour == 0) tour = 1;
                else if(tour == 1) tour = 0;

                if(bot.isActivated && couleurBot == 1 && tour == 1) {
                    tour = 0;
                    bot.play(this, 1);
                    updateBoard();
                    mainController.switchActivePlayer();

                } else if (bot.isActivated && couleurBot == 0 && tour == 0) {
                    tour = 1;
                    bot.play(this, 0);
                    updateBoard();
                    mainController.switchActivePlayer();
                }

            } else {
                selectedPiece = null;
                colorBoard();
            }
        }
    }

    /**
     * Place les pièces sur le plateau dans le bon ordre et les affiche
     */
    private void placePieces() {
        matPiece = new Piece[][]{
                {new Tour("noir", "tour", 1, 0, 0), new Cavalier("noir", "cavalier", 1, 1, 0), new Fou("noir", "fou", 1, 2, 0), new Reine("noir", "reine", 1, 3, 0), new Roi("noir", "roi", 1, 4, 0), new Fou("noir", "fou", 1, 5, 0), new Cavalier("noir", "cavalier", 1, 6, 0), new Tour("noir", "tour", 1, 7, 0)},
                {new Pion("noir", "pion", 1, 0, 1), new Pion("noir", "pion", 1, 1, 1), new Pion("noir", "pion", 1, 2, 1), new Pion("noir", "pion", 1, 3, 1), new Pion("noir", "pion", 1, 4, 1), new Pion("noir", "pion", 1, 5, 1), new Pion("noir", "pion", 1, 6, 1), new Pion("noir", "pion", 1, 7, 1)},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {new Pion("blanc", "pion", 0, 0, 6), new Pion("blanc", "pion", 0, 1, 6), new Pion("blanc", "pion", 0, 2, 6), new Pion("blanc", "pion", 0, 3, 6), new Pion("blanc", "pion", 0, 4, 6), new Pion("blanc", "pion", 0, 5, 6), new Pion("blanc", "pion", 0, 6, 6), new Pion("blanc", "pion", 0, 7, 6)},
                {new Tour("blanc", "tour", 0, 0, 7), new Cavalier("blanc", "cavalier", 0, 1, 7), new Fou("blanc", "fou", 0, 2, 7), new Reine("blanc", "reine", 0, 3, 7), new Roi("blanc", "roi", 0, 4, 7), new Fou("blanc", "fou", 0, 5, 7), new Cavalier("blanc", "cavalier", 0, 6, 7), new Tour("blanc", "tour", 0, 7, 7)}
        };


        updateBoard();
    }

    /**
     * Met à jour l'affichage du plateau
     * Affiche les pièces aux bonnes positions
     */
    public void updateBoard() {
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

    /**
     * @param x position x
     * @param y position y
     * @return pièce à la position x, y
     */
    public Piece getPiece(int x, int y) {
        return matPiece[y][x];
    }

    /**
     * @param x position x
     * @param y position y
     */
    public void selectPiece(int x, int y) {
        Piece piece = getPiece(x, y);

        if (piece != null && piece.getCouleur() == tour) {
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

    public void finDePartie(int color) {
        mainController.showMessageEnding(color);
        mainController.freezeTimers();
        tour = 2;

    }

    public boolean movePiece(int currentX, int currentY, int targetX, int targetY) {
        Piece piece = getPiece(currentX, currentY);
        if (piece != null) {
            int[][] validMoves = piece.validMoves(this);
            for (int[] move : validMoves) {
                if (move[0] == targetX && move[1] == targetY) {
                    Piece targetPiece = getPiece(targetX, targetY);
                    if (targetPiece == null || targetPiece.getCouleur() != piece.getCouleur()) {
                        // On stocke dans coups les coups joués avec le modèle suivant : currentX currentY targetX targetY
                        fichierCoup.ecrireCoup(currentX, currentY, targetX, targetY);
                        if (piece instanceof Roi && Math.abs(currentX - targetX) == 2) {
                            int tourX = targetX == 6 ? 7 : 0;
                            int tourY = piece.getCouleur() == 0 ? 0 : 7;
                            Piece tour = getPiece(tourX, tourY);
                            matPiece[tourY][tourX] = null;
                            matPiece[tourY][targetX == 6 ? 5 : 3] = tour;
                            tour.setX(targetX == 6 ? 5 : 3);
                            tour.setY(tourY);
                        }

                        if (targetPiece instanceof Roi) {
                            finDePartie(targetPiece.getCouleur() == 1 ? 0 : 1);
                        }

                        matPiece[targetY][targetX] = piece;
                        matPiece[currentY][currentX] = null;

                        if (piece instanceof Pion && (targetY == 0 || targetY == 7)) {
                            matPiece[targetY][targetX] = new Reine(piece.getCouleur() == 0 ? "blanc" : "noir", "reine", piece.getCouleur(), targetX, targetY);
                            coups.append(currentX).append(currentY).append(targetX).append(targetY);
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
                    square.setStyle("-fx-background-color: #EEEED5");
                } else {
                    square.setStyle("-fx-background-color: #7D945D");
                }
            }
        }
    }

    // Fonction qui prend en paramètre le nom d'un fichier, et qui joue tous les coups du fichier
    public void jouerPartie(String fileName) {
        fichierCoup.jouerPartie(this, fileName);
    }

//    public void annulerCoup(String filename) {
//        fichierCoup.annulerCoup(this, filename);
//    }

    public void coupSuivant(String filename) {
    }

    public VBox getBoard() {
        return board;
    }

    public Image getIcon1() {
        return new Image(getClass().getResourceAsStream("/icone.jpg"));
    }

    public Image getIcon2() {
        return new Image(getClass().getResourceAsStream("/icone2.png"));
    }

    public Bot getBot() {
        return bot;
    }
}