package com.valoo.chess;

import javafx.beans.binding.Bindings;
import javafx.beans.binding.StringBinding;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

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

    private Timer timer;
    private ChessBoard chessBoard;

    public void initialize() {
        timer = new Timer(10); // Replace 10 with your desired initial time
        chessBoard = new ChessBoard(); // Assurez-vous que cette instance est correctement initialisée

        StringBinding timeBlancBinding = Bindings.createStringBinding(() ->
                String.valueOf(timer.timeBlancProperty().get()), timer.timeBlancProperty());

        StringBinding timeNoirBinding = Bindings.createStringBinding(() ->
                String.valueOf(timer.timeNoirProperty().get()), timer.timeNoirProperty());

        labelTime1.textProperty().bind(timeBlancBinding);
        labelTime2.textProperty().bind(timeNoirBinding);

        // Ajouter le gestionnaire d'événements pour le bouton "Jouer"
        btnJouer.setOnAction(event -> handleJouerButtonAction());
    }

    @FXML
    private void handleJouerButtonAction() {

    }
}
