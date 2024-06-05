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

    @FXML
    private Label endgameMessage;

    private Timer timer1;
    private Timer timer2;
    private ChessBoard chessBoard;
    private int activePlayer; // 1 for player 1, 2 for player 2

    private Timeline timeline;
    private int seconds;

    public void initialize() {
        timeline = new Timeline(new KeyFrame(Duration.seconds(1), event -> {
            if (activePlayer == 1) {
                timer1.tpsDecr(1);
            } else {
                timer2.tpsDecr(1);
            }
        }));
        timeline.setCycleCount(Timeline.INDEFINITE);

        btnJouer.setOnAction(event -> handleJouerButtonAction());
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
        System.out.println("Jeu réinitialisé !");
        switchActivePlayer();
    }

    public void freezeTimers() {
        timeline.stop();
    }

    @FXML
    private void handleJouerButtonAction() {
        gameCode(2);
        endgameMessage.setText("Partie en cours...");
    }

    @FXML
    public void handleBotButtonAction() {
        gameCode(1);
    }

    public void switchActivePlayer() {
        activePlayer = activePlayer == 1 ? 2 : 1;
        timer1.reset(seconds);
        timer2.reset(seconds);
    }

    public void showMessageEnding(int color) {
        if (color == 0) {
            endgameMessage.setText("Le joueur 1 a gagné !");
        } else {
            endgameMessage.setText("Le joueur 2 a gagné !");
        }
        freezeTimers();
    }

}
