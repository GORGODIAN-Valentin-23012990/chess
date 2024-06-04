package com.valoo.chess;

import javafx.scene.Node;
import javafx.scene.image.ImageView;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class Chess extends Application {

    private int selectedX = -1;
    private int selectedY = -1;
    public void start(Stage primaryStage) {
        BorderPane root = new BorderPane();
        ChessBoard chessBoard = new ChessBoard();

        root.setCenter(chessBoard.getBoard());

        // Sélectionner la piece au premier clic, et la deplacer au deixieme clic
        for (Node node : chessBoard.getBoard().lookupAll(".stack-pane")) {
            StackPane square = (StackPane) node;
            square.setOnMouseClicked(e -> {
                int x = GridPane.getColumnIndex(square);
                int y = GridPane.getRowIndex(square);
                if (selectedX == -1 && selectedY == -1) {
                    if (chessBoard.getPiece(x, y) != null) {
                        selectedX = x;
                        selectedY = y;
                    }
                } else {
                    if (chessBoard.movePiece(selectedX, selectedY, x, y)) {
                        selectedX = -1;
                        selectedY = -1;
                    }
                }
            });
        }

        Scene scene = new Scene(root);
        primaryStage.setTitle("Chess");
        primaryStage.setWidth(800);
        primaryStage.setHeight(800);
        primaryStage.setScene(scene);
        primaryStage.setResizable(false);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
