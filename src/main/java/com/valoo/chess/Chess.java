
package com.valoo.chess;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;

public class   Chess extends Application {

    private ChessBoard chessBoard;

    /**
     * Cette fonction permet de lancer l'application
     * @param primaryStage fenêtre principale
     */
    public void start(Stage primaryStage) {

        BorderPane root = new BorderPane();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/valoo/chess/main-view.fxml"));
        Parent helloView = null;

        try {
            helloView = loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        VBox chessBoardContainer = (VBox) loader.getNamespace().get("chessBoardContainer");


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