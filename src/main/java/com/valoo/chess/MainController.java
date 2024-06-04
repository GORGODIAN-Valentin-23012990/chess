package com.valoo.chess;

import javafx.beans.binding.Bindings;
import javafx.beans.binding.StringBinding;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

public class MainController {
    @FXML
    private ComboBox myComboBox;
    @FXML
    private Label labelTime1;
    @FXML
    private VBox chessBoardContainer;
    @FXML
    private Label labelTime2;

    private Timer timer;

    public void initialize() {
        timer = new Timer(10); // Replace 10 with your desired initial time

        StringBinding timeBlancBinding = Bindings.createStringBinding(() ->
                String.valueOf(timer.timeBlancProperty().get()), timer.timeBlancProperty());

        StringBinding timeNoirBinding = Bindings.createStringBinding(() ->
                String.valueOf(timer.timeNoirProperty().get()), timer.timeNoirProperty());

        labelTime1.textProperty().bind(timeBlancBinding);
        labelTime2.textProperty().bind(timeNoirBinding);
    }
}
