package com.valoo.chess.controller;

import com.valoo.chess.ChessBoard;
import com.valoo.chess.fonctionnalites.Joueur;
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
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.util.Duration;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class MainController {

    @FXML
    private Button btnTournoi;
    @FXML
    private TextField prenomField;
    @FXML
    private TextField nomField;
    @FXML
    private Button btnValider;
    @FXML
    Label J1Label;
    @FXML
    Label J2Label;
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
    @FXML
    private VBox listeFichiersParties;
    @FXML
    Button btnCreer;
    @FXML
    VBox menuJoueur2;
    @FXML
    Button btnValider2;
    @FXML
    TextField prenomField2;
    @FXML
    TextField nomField2;
    @FXML
    VBox menuTournoi;

    boolean partiesChargees = false;

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
    private String nomJ1;
    private String nomJ2;
    private String prenomJ1;
    private String prenomJ2;



    public void initialize() {
        menuJoueur.setVisible(false);
        menuPartie.setVisible(false);
        menuJoueur2.setVisible(false);
        menuTournoi.setVisible(false);
        listeFichiersParties = new VBox();

        timeline = new Timeline(new KeyFrame(Duration.seconds(1), event -> {
            if (activePlayer == 1) {
                timer1.tpsDecr(1);
            } else {
                timer2.tpsDecr(1);
            }
        }));
        btnTournoi.setOnAction(event -> actionBtnTournoi());
        timeline.setCycleCount(Timeline.INDEFINITE);
        btnValider.setOnAction(event -> actionBtnValider());
        btnValider2.setOnAction(event -> actionBtnValider2());
        Partie.setOnAction(event -> showVBox(menuPartie));
        Joueurs.setOnAction(event -> showVBox(menuJoueur));
        NVPartie.setOnAction(event -> showVBox(menuPrincipal));
        btnJouer.setOnAction(event -> handleJouerButtonAction());
        btnCreer.setOnAction(event -> handleChargerPartie());
    }

    public void actionBtnTournoi() {
         menuPrincipal.setVisible(false);
         menuTournoi.setVisible(true);
    }
    public void actionBtnValider(){
        prenomJ1 = prenomField.getText();
        nomJ1 = nomField.getText();
        bindJ1(nomJ1, prenomJ1);
        menuJoueur.setVisible(false);
        menuJoueur2.setVisible(true);
    }
    public void actionBtnValider2() {
        prenomJ2 = prenomField2.getText();
        nomJ2 = nomField2.getText();
        bindJ2(nomJ2, prenomJ2);
        menuJoueur2.setVisible(false);
        menuPrincipal.setVisible(true);
    }

    public void bindJ1(String nom, String prenom) {
        String nomJ1 = nom+" "+prenom;
        J1Label.setText(nomJ1);
    }

    public void bindJ2(String nom, String prenom) {
        String nomj2 = nom+" "+prenom;

        J2Label.setText(nomj2);
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

        handleChargerPartie();

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
    public Joueur getJoueur1() {
        String nom = nomJ1;
        String prenom = prenomJ1;
        return new Joueur(nomJ1, prenomJ1);
    }
    public Joueur getJoueur2() {
        String nom = nomJ2;
        String prenom = prenomJ2;
        return new Joueur(nomJ2, prenomJ2);
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
        if (!partiesChargees) {
            File directory = new File("src/main/resources/parties/");
            File[] files = directory.listFiles();
            if (files != null) {
                for (File file : files) {
                    if (file.isFile()) {
                        System.out.println(file.getName());
                        Button fileButton = new Button(file.getName());
                        fileButton.setStyle("-fx-background-color: #f4f4f4; -fx-border-color: #000000; -fx-border-width: 1px; -fx-padding: 5px;");
                        fileButton.setOnAction(event -> {
                            // Ajoutez le code pour charger la partie ici
                            if(chessBoard == null) {
                                chessBoard = new ChessBoard(2, this);
                                chessBoardContainer.getChildren().add(chessBoard.getBoard());
                            } else {
                                chessBoard.resetBoard();
                            }
                            System.out.println("Chargement de la partie: " + file.getName());
                            chessBoard.jouerPartie(file.getName());
                        });
                        listeFichiersParties.getChildren().add(fileButton);
                    }
                }
                // Forcer la mise à jour de l'interface utilisateur
                listeFichiersParties.layout();
            }
            partiesChargees = true;
        }
    }



    @FXML
    public void handlePrec(){
//        chessBoard.annulerCoup("parties/Partie35.txt");
    }

    @FXML
    public void handleSuiv() {
        chessBoard.coupSuivant("parties/Partie35.txt");
        chessBoard.updateBoard();
    }


}
