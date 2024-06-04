package com.valoo.chess;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class Chess extends Application {
    public void start(Stage primaryStage) {
        BorderPane root = new BorderPane();
        ChessBoard chessBoard = new ChessBoard();

        root.setCenter(chessBoard.getBoard());

        // check les moves possibles du cavalier

        Reine reine = new Reine("blanc", "reine", 0, 3, 0);
        int[][] moves = reine.validMoves(chessBoard);
        for (int[] move : moves) {
            System.out.println(move[0] + " " + move[1]);
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
