package com.valoo.chess;

import com.valoo.chess.controller.MainController;
import com.valoo.chess.fonctionnalites.Bot;
import com.valoo.chess.fonctionnalites.FichierCoup;
import com.valoo.chess.fonctionnalites.Joueur;
import com.valoo.chess.pieces.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

import java.util.ArrayList;
import java.util.List;

public class ChessBoard {
    public boolean isGameOver = false;
    private VBox board;
    private Piece[][] matPiece;
    private Piece selectedPiece;
    private int tour;
    private int couleurBot;
    private Bot bot;
    private StringBuilder coupsJoues;

    private List<Joueur> joueurs = new ArrayList<>();
    private MainController mainController;
    private FichierCoup fichierCoup;

    public void JoueurTournoi(List<Joueur> joueurs){
        this.joueurs = joueurs;
        joueurs.add(new Joueur(this.mainController.getJoueur1().getNom(), this.mainController.getJoueur1().getPrenom()));
        joueurs.add(new Joueur(this.mainController.getJoueur2().getNom(), this.mainController.getJoueur2().getPrenom()));
        System.out.println(joueurs);
    }

    /**
     * @param couleurBot couleur du bot
     * @param mainController controleur principal
     * Crée un plateau de jeu d'échec
     */
    public ChessBoard(int couleurBot, MainController mainController) {
        fichierCoup = new FichierCoup();
        this.mainController = mainController;
        board = new VBox();
        coupsJoues = new StringBuilder();
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

    public ChessBoard(int couleurBot, MainController mainController, String fileName) {
        fichierCoup = new FichierCoup(fileName);
        this.mainController = mainController;
        board = new VBox();
        coupsJoues = new StringBuilder();
        tour = 0;
        if(couleurBot == 0 || couleurBot == 1) {
            this.couleurBot = couleurBot;
            bot = new Bot(true);
        } else if (couleurBot == 2) {
            bot = new Bot(false);
        }
        createBoard();
        jouerPartie(fileName);
    }

    // La méthode resetBoard permet de réinitialiser le plateau de jeu
    public void resetBoard(){
        placePieces();
        updateBoard();
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
            if (selectedPiece != null && movePiece(selectedPiece.getX(), selectedPiece.getY(), x, y, true)) {
                selectedPiece = null;
                updateBoard();
                colorBoard();
                // On attend un peu si le bot est activé pour simuler une réflexion
                if(tour == 0) tour = 1;
                else tour = 0;
                return;
            }
        }

        if (selectedPiece == null) {
            selectPiece(x, y);
        } else {
            if (movePiece(selectedPiece.getX(), selectedPiece.getY(), x, y, true)) {
                selectedPiece = null;
                mainController.switchActivePlayer();
                updateBoard();
                colorBoard();
                if(tour == 0) tour = 1;
                else if(tour == 1) tour = 0;

                if(bot.isActivated && couleurBot == 1 && tour == 1) {
                    updateBoard();
                    colorBoard();
                    tour = 0;
                    bot.play(this, 1);
                    updateBoard();
                    mainController.switchActivePlayer();

                } else if (bot.isActivated && couleurBot == 0 && tour == 0) {
                    updateBoard();
                    colorBoard();
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
        // detect if tournanement is started and setMatchOver for the match
        isGameOver = true;
        tour = 2;

    }


    public boolean movePiece(int currentX, int currentY, int targetX, int targetY, boolean haveToWrite) {
        Piece piece = getPiece(currentX, currentY);
        if (piece != null) {
            int[][] validMoves = piece.validMoves(this);
            for (int[] move : validMoves) {
                if (move[0] == targetX && move[1] == targetY) {
                    Piece targetPiece = getPiece(targetX, targetY);
                    if (targetPiece == null || targetPiece.getCouleur() != piece.getCouleur()) {
                        // On stocke dans coups les coups joués avec le modèle suivant : currentX currentY targetX targetY
                        if(haveToWrite) {
                            fichierCoup.ecrireCoup(currentX, currentY, targetX, targetY);
                            fichierCoup.setIndexHistorique(fichierCoup.getIndexHistorique() + 1);
                        }

                        // On regarde si la pièce qui bouge est un roi et si la case de destination utilise le grand roque
                        if (piece instanceof Roi && targetX - currentX == 2) {
                            Piece tour = getPiece(7, targetY);
                            matPiece[targetY][targetX - 1] = tour;
                            matPiece[targetY][7] = null;
                            tour.setX(targetX - 1);
                            tour.setY(targetY);
                        }

                        // On regarde si la pièce qui bouge est un roi et si la case de destination utilise le petit roque
                        if (piece instanceof Roi && currentX - targetX == 2) {
                            Piece tour = getPiece(0, targetY);
                            matPiece[targetY][targetX + 1] = tour;
                            matPiece[targetY][0] = null;
                            tour.setX(targetX + 1);
                            tour.setY(targetY);
                        }

                        if (targetPiece instanceof Roi) {
                            finDePartie(targetPiece.getCouleur() == 1 ? 0 : 1);
                        }

                        matPiece[targetY][targetX] = piece;
                        matPiece[currentY][currentX] = null;

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

    public void annulerCoup(String filename) {
        fichierCoup.annulerCoup(this, filename);
    }

    public void coupSuivant(String filename) {
        fichierCoup.coupSuivant(this, filename);
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
    public String getFileName() {
        return fichierCoup.getFileName();
    }
}