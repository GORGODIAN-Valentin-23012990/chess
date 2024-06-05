package com.valoo.chess.controller;

import com.valoo.chess.ChessBoard;
import com.valoo.chess.fonctionnalites.Timer;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.beans.binding.Bindings;
import javafx.beans.binding.StringBinding;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.util.Duration;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

public class MainController {
    @FXML
    private ComboBox<String> myComboBox;
    @FXML
    private Label labelTime1;
    @FXML
    private VBox chessBoardContainer;
    @FXML
    private Label labelTime2;
    @FXML
    private Button btnJouer;

    @FXML
    private Label endgameMessage;
    @FXML
    private VBox menuJoueur;
    @FXML
    VBox menuPartie;
    @FXML
    VBox menuPrincipal;
    @FXML
    private ImageView iconBottomRight;

    @FXML
    Button NVPartie;
    @FXML
    Button Partie;
    @FXML
    Button Joueurs;

    ArrayList<Node> Menu = new ArrayList<Node>();
    ArrayList<Node> Menu2 = new ArrayList<Node>();
    ArrayList<Node> Menu3 = new ArrayList<Node>();
    ArrayList<Node> MainMenu = new ArrayList<Node>();
    private Timer timer1;
    private Timer timer2;
    private ChessBoard chessBoard;
    private int activePlayer; // 1 for player 1, 2 for player 2

    private Timeline timeline;
    private int seconds;

    public void initialize() {
        menuJoueur.setVisible(false);
        menuPartie.setVisible(false);
        timeline = new Timeline(new KeyFrame(Duration.seconds(1), event -> {
            if (activePlayer == 1) {
                timer1.tpsDecr(1);
            } else {
                timer2.tpsDecr(1);
            }
        }));
        timeline.setCycleCount(Timeline.INDEFINITE);


        Partie.setOnAction(event -> showVBox(menuPartie));
        Joueurs.setOnAction(event -> showVBox(menuJoueur));
        NVPartie.setOnAction(event -> showVBox(menuPrincipal));
        btnJouer.setOnAction(event -> handleJouerButtonAction());
    }
    private void showVBox(VBox vbox) {
        menuJoueur.setVisible(false);
        menuPrincipal.setVisible(false);
        menuPartie.setVisible(false);
        vbox.setVisible(true);
    }



    private void gameCode(int bot) {
        String selectedTime = myComboBox.getSelectionModel().getSelectedItem();
        seconds = 0;

        if (selectedTime != null) {
            switch (selectedTime) {
                case "30 secondes":
                    seconds = 30;
                    break;
                case "1 minute":
                    seconds = 60;
                    break;
                case "3 minutes":
                    seconds = 180;
                    break;
                default:
                    seconds = 30;
                    break;
            }
        } else {
            seconds = 30;
        }

        timer1 = new Timer(seconds);
        timer2 = new Timer(seconds);
        activePlayer = 2; // Player 1 starts

        StringBinding timeBinding1 = Bindings.createStringBinding(() ->
                        String.format("%02d:%02d", timer1.getTimeBlanc() / 60, timer1.getTimeBlanc() % 60),
                timer1.timeBlancProperty()
        );
        labelTime1.textProperty().bind(timeBinding1);

        StringBinding timeBinding2 = Bindings.createStringBinding(() ->
                        String.format("%02d:%02d", timer2.getTimeBlanc() / 60, timer2.getTimeBlanc() % 60),
                timer2.timeBlancProperty()
        );
        labelTime2.textProperty().bind(timeBinding2);

        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play();

        timer1.timeBlancProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue.intValue() == 0) {
                timer2.reset(seconds);
                if (activePlayer == 1) {
                    activePlayer = 2;
                    if (chessBoard != null) {
                        chessBoard.getBot().play(chessBoard, 0);
                    }
                }
            }
        });

        timer2.timeBlancProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue.intValue() == 0) {
                timer1.reset(seconds);
                if (activePlayer == 2) {
                    activePlayer = 1;
                    if (chessBoard != null) {
                        chessBoard.getBot().play(chessBoard, 1);
                    }
                }
            }
        });

        if (chessBoard != null) {
            chessBoardContainer.getChildren().remove(chessBoard.getBoard());
        }
        chessBoard = new ChessBoard(bot, this);
        chessBoardContainer.getChildren().add(chessBoard.getBoard());
        switchActivePlayer();
    }

    public void freezeTimers() {
        timeline.stop();
    }

    @FXML
    private void handleJouerButtonAction() {
        gameCode(2);
        endgameMessage.setText("Partie en cours...");
        if (endgameMessage.getStyleClass().contains("afterMatch")) {
            endgameMessage.getStyleClass().remove("afterMatch");
        }
        // set the iconBottomRight image to the winner's icon
        iconBottomRight.setImage(null);
    }

    @FXML
    public void handleBotButtonAction() {
        gameCode(1);
        endgameMessage.setText("Partie en cours...");
        if (endgameMessage.getStyleClass().contains("afterMatch")) {
            endgameMessage.getStyleClass().remove("afterMatch");
        }
        // set the iconBottomRight image to the winner's icon
        iconBottomRight.setImage(null);
    }

    public void switchActivePlayer() {
        activePlayer = activePlayer == 1 ? 2 : 1;
        timer1.reset(seconds);
        timer2.reset(seconds);
    }

    public void showMessageEnding(int color) {
        endgameMessage.getStyleClass().add("afterMatch");
        // set the iconBottomRight image to the winner's icon
        iconBottomRight.setImage(color == 0 ? chessBoard.getIcon1() : chessBoard.getIcon2());
        if (color == 0) {
            endgameMessage.setText("Le joueur 1 a gagné !");
        } else {
            endgameMessage.setText("Le joueur 2 a gagné !");
        }
        freezeTimers();
    }

    @FXML
    public void handleChargerPartie() {
        chessBoard.jouerPartie("coups.txt");
        chessBoard.updateBoard();
    }


}
