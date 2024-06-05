package com.valoo.chess;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.beans.binding.Bindings;
import javafx.beans.binding.StringBinding;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.util.Duration;

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

    private Timer timer1;
    private Timer timer2;
    private ChessBoard chessBoard;
    private int activePlayer; // 1 pour le joueur 1, 2 pour le joueur 2

    private Timeline timeline;

    public void initialize() {
        timeline = new Timeline(new KeyFrame(Duration.seconds(1), event -> {
            if (activePlayer == 1) {
                timer1.tpsDecr(1);
            } else {
                timer2.tpsDecr(1);
            }
        }));
        timeline.setCycleCount(Timeline.INDEFINITE);
        // Ajouter le gestionnaire d'événements pour le bouton "Jouer"
        btnJouer.setOnAction(event -> handleJouerButtonAction());

    }

    @FXML
    private void handleJouerButtonAction() {

        String selectedTime = myComboBox.getSelectionModel().getSelectedItem();
        int seconds = 0;

        if (selectedTime != null) {
            if (selectedTime.equals("30 secondes")) {
                seconds = 30;
            } else if (selectedTime.equals("1 minute")) {
                seconds = 60;
            } else if (selectedTime.equals("3 minutes")) {
                seconds = 180;
            }
        }

        timer1 = new Timer(seconds);
        timer2 = new Timer(seconds);
        activePlayer = 2; // Le joueur 1 commence

        StringBinding timeBinding1 = Bindings.createStringBinding(() -> {
            return String.format("%02d:%02d", timer1.getTimeBlanc() / 60, timer1.getTimeBlanc() % 60);
        }, timer1.timeBlancProperty());
        labelTime1.textProperty().bind(timeBinding1);

        StringBinding timeBinding2 = Bindings.createStringBinding(() -> {
            return String.format("%02d:%02d", timer2.getTimeBlanc() / 60, timer2.getTimeBlanc() % 60);
        }, timer2.timeBlancProperty());
        labelTime2.textProperty().bind(timeBinding2);

        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play();

        if (chessBoard != null) {
            chessBoardContainer.getChildren().remove(chessBoard.getBoard());
        }
        chessBoard = new ChessBoard(2, this);
        chessBoardContainer.getChildren().add(chessBoard.getBoard());
        System.out.println("Jeu réinitialisé !");
        switchActivePlayer();
    }

    public void switchActivePlayer() {
        activePlayer = activePlayer == 1 ? 2 : 1;
    }
}