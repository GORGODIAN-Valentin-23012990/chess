
package com.valoo.chess;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;

public class Chess extends Application {

    private ChessBoard chessBoard;

    public void start(Stage primaryStage) {
        BorderPane root = new BorderPane();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/valoo/chess/hello-view.fxml"));
        Parent helloView = null;

        try {
            helloView = loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        VBox chessBoardContainer = (VBox) loader.getNamespace().get("chessBoardContainer");


        // Create the chess board and add it to the VBox
        ChessBoard chessBoard = new ChessBoard();
        chessBoardContainer.getChildren().add(chessBoard.getBoard());

        chessBoard.activateBot(true);
        System.out.println("true");

        root.setLeft(helloView);

        Scene scene = new Scene(root);
        primaryStage.setTitle("Chess");
        // On règle la taille de la fenêtre pour qu'elle prenne automatiquement toute la largeur de l'écran
        primaryStage.setWidth(1200);
        primaryStage.setHeight(900);
        primaryStage.setScene(scene);
        primaryStage.setResizable(false);
        primaryStage.show();
    }

    public void setChessBoard(ChessBoard chessBoard) {
        this.chessBoard = chessBoard;
    }

    public static void main(String[] args) {
        launch(args);
    }
}