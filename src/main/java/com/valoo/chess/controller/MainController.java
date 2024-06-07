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
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.util.Duration;

import java.io.File;
import java.util.ArrayList;

public class MainController {

    @FXML
    private Button btnTournoi;
    @FXML
    private VBox listeParties;
    @FXML
    private VBox menuPartieHBox;
    @FXML
    private VBox listeProblemes;
    @FXML
    private VBox espace;
    @FXML
    private Button btnJouer2;
    @FXML
    private TextField prenomField;
    @FXML
    private TextField nomField;
    @FXML
    private Button btnValider;
    @FXML
    private Button btnClearParties;
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
    VBox menuJoueur;
    @FXML
    VBox menuPartie;
    @FXML
    VBox menuPrincipal;
    @FXML
    private ImageView iconBottomRight;

    @FXML
    private Button btnPrec;
    @FXML
    private Button btnSuiv;

    @FXML
    Button NVPartie;
    @FXML
    Button Partie;
    @FXML
    Button Joueurs;
    @FXML
    private VBox listeFichiersParties;
    @FXML
    private Button btnAffichageParties;
    @FXML
    private Button btnAffichageProblemes;
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


    @FXML
    private Button btnValiderTournoi;
    private String partieChargee;

    boolean partiesChargees = false;

    ArrayList<Node> Menu = new ArrayList<Node>();
    ArrayList<Node> Menu2 = new ArrayList<Node>();
    ArrayList<Node> Menu3 = new ArrayList<Node>();
    ArrayList<Node> MainMenu = new ArrayList<Node>();
    @FXML
    private HBox choixJeuBox;
    private Timer timer1;
    private Timer timer2;
    private ChessBoard chessBoard;
    int activePlayer; // 1 for player 1, 2 for player 2

    private Timeline timeline;
    private int seconds;
    @FXML
    private VBox listeFichiersProblemes;
    String nomJ1;
    String nomJ2;
    String prenomJ1;
    String prenomJ2;



    public void initialize() {
        menuJoueur.setVisible(false);
        menuPartie.setVisible(false);
        menuJoueur2.setVisible(false);
        menuTournoi.setVisible(false);
        choixJeuBox.setVisible(false);
        listeFichiersProblemes.setVisible(false);

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
        btnValiderTournoi.setOnAction(event -> handleValiderTournoi());
        btnClearParties.setOnAction(event -> handleClearParties());


        Partie.setOnAction(event -> {
            showVBox(menuPartie);
            resetButtonStyles(Partie, Joueurs, NVPartie);
            Partie.setStyle("-fx-background-color: #21201D; -fx-text-fill: white;");

        });

        Joueurs.setOnAction(event -> {
            showVBox(menuJoueur);
            resetButtonStyles(Joueurs, Partie, NVPartie);
            Joueurs.setStyle("-fx-background-color: #21201D; -fx-text-fill: white;");
        });

        NVPartie.setOnAction(event -> {
            showVBox(menuPrincipal);
            resetButtonStyles(NVPartie, Joueurs, Partie);
            NVPartie.setStyle("-fx-background-color: #21201D; -fx-text-fill: white;");
        });

        btnValiderTournoi.setOnAction(event -> handleValiderTournoi());

        handleChargerPartie();

        btnPrec.setOnAction(event -> handlePrec());
        btnSuiv.setOnAction(event -> handleSuiv());
        btnAffichageParties.setOnAction(event -> handleChargerPartie());
        btnAffichageProblemes.setOnAction(event -> handleChargerProblemes());

        btnJouer.setOnAction(event -> handleJouerButtonAction());


    }

    public void handleChargerProblemes() {
        menuPartieHBox.setVisible(false);
        listeProblemes.setVisible(true);
        String nomBtn;
        listeFichiersProblemes.getChildren().clear();
        File directory = new File("src/main/resources/problemes/");
        File[] files = directory.listFiles();
        if (files.length > 0) {
            for (File file : files) {
                if (file.isFile()) {
                    Button fileButton = new Button(file.getName().substring(0, file.getName().length() - 4));
                    fileButton.setPrefWidth(200);
                    fileButton.setOnAction(event -> {
                        partieChargee = fileButton.getText() + ".txt";
                        timer1 = new Timer(30);
                        timer2 = new Timer(30);
                        if(chessBoard == null) {
                            chessBoard = new ChessBoard(2, this);
                            chessBoardContainer.getChildren().add(chessBoard.getBoard());
                        } else {
                            chessBoard.resetBoard();
                        }
                        chessBoard.jouerPartie(file.getName());
                    });
                    listeFichiersProblemes.getChildren().add(fileButton);
                }
            }
            // Forcer la mise à jour de l'interface utilisateur
            listeFichiersProblemes.layout();
        }
    }

    // Méthode pour réinitialiser les styles des boutons
    private void resetButtonStyles(Button activeButton, Button... buttons) {
        for (Button button : buttons) {
            if (button != activeButton) {
                button.setStyle("-fx-background-color: black; -fx-text-fill: white;");
            }
        }
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
        Joueur joueur = new Joueur(nomJ1, prenomJ1);
    }
    public void actionBtnValider2() {
        prenomJ2 = prenomField2.getText();
        nomJ2 = nomField2.getText();
        bindJ2(nomJ2, prenomJ2);
        menuJoueur2.setVisible(false);
        menuPrincipal.setVisible(true);
        Joueur joueur = new Joueur(nomJ2, prenomJ2);
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
                        String.format("%02d:%02d", timer2.getTimeBlanc() / 60, timer2.getTimeBlanc() % 60),
                timer2.timeBlancProperty()
        );
        labelTime1.textProperty().bind(timeBinding1);

        StringBinding timeBinding2 = Bindings.createStringBinding(() ->
                        String.format("%02d:%02d", timer1.getTimeBlanc() / 60, timer1.getTimeBlanc() % 60),
                timer1.timeBlancProperty()
        );

        timer1.timeBlancProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue.intValue() == 0) {
                timer2.reset(seconds);
                if (activePlayer == 1) {
                    activePlayer = 2;
                    if (chessBoard != null) {
                        chessBoard.getBot().play(chessBoard, 0);
                        chessBoard.updateBoard();
                    }
                }
            }
        });

        // Quand le timer 2 arrive à zero, on le reintialise et on fait jouer le bot pour ce coup
        timer2.timeBlancProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue.intValue() == 0) {
                timer1.reset(seconds);
                if (activePlayer == 2) {
                    activePlayer = 1;
                    if (chessBoard != null) {
                        chessBoard.getBot().play(chessBoard, 1);
                        chessBoard.updateBoard();
                    }
                }
            }
        });

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
        handleChargerPartie();
        partieChargee = chessBoard.getFileName().substring(27, chessBoard.getFileName().length());
        chessBoardContainer.getChildren().add(chessBoard.getBoard());
        switchActivePlayer();
    }

    public void handleValiderTournoi() {
        String nom = nomField.getText();
        String prenom = prenomField.getText();
        Joueur joueur = new Joueur(nom, prenom);

        System.out.println("Joueur: " + joueur.getNom() + " " + joueur.getPrenom());
    }

    public void freezeTimers() {
        timeline.stop();
    }

    @FXML
    private void handleJouerButtonAction() {
        if(choixJeuBox.isVisible()) {
            choixJeuBox.setVisible(false);
            espace.setVisible(true);
        } else {
            choixJeuBox.setVisible(true);
            espace.setVisible(false);
        }
    }

    @FXML
    private void handleJouer2ButtonAction() {
        gameCode(2);
        endgameMessage.setText("Partie en cours...");
        if (endgameMessage.getStyleClass().contains("afterMatch")) {
            endgameMessage.getStyleClass().remove("afterMatch");
        }
        // set the iconBottomRight image to the winner's icon
        iconBottomRight.setImage(null);
    }

    @FXML
    public void handleAjoutJoueur() {
        String nom = nomField.getText();
        String prenom = prenomField.getText();
        Joueur joueur = new Joueur(nom, prenom);
        System.out.println("grzzz");
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

    public void handleChargerPartie() {
        listeFichiersParties.setVisible(true);
        listeProblemes.setVisible(false);
        listeParties.getChildren().clear();
        String nomBtn;
        File directory = new File("src/main/resources/parties/");
        File[] files = directory.listFiles();
        if (files.length > 0) {
            for (File file : files) {
                if (file.isFile()) {
                    Button fileButton = new Button(file.getName().substring(0, file.getName().length() - 4));
                    fileButton.setPrefWidth(200);
                    fileButton.setOnAction(event -> {
                        partieChargee = fileButton.getText() + ".txt";
                        timer1 = new Timer(30);
                        timer2 = new Timer(30);
                        if(chessBoard == null) {
                            chessBoard = new ChessBoard(2, this);
                            chessBoardContainer.getChildren().add(chessBoard.getBoard());
                        } else {
                            chessBoard.resetBoard();
                        }
                        chessBoard.jouerPartie(file.getName());
                    });
                    listeParties.getChildren().add(fileButton);
                }
            }
            // Forcer la mise à jour de l'interface utilisateur
            listeParties.layout();
        } else {
            handleClearParties();
        }

    }



    @FXML
    public void handlePrec(){
        if(partieChargee == null) {
            return;
        } else {
            chessBoard.annulerCoup("src/main/resources/parties/" + partieChargee);

        }
    }

    @FXML
    public void handleSuiv() {
        if (partieChargee == null) {
            return;
        } else {
            chessBoard.coupSuivant("src/main/resources/parties/" + partieChargee);
        }
    }

    @FXML
    public void handleClearParties() {
        File directory = new File("src/main/resources/parties/");
        File[] files = directory.listFiles();
        if (files != null) {
            for (File file : files) {
                if (file.isFile()) {
                    file.delete();
                }
            }
        }
        listeFichiersParties.getChildren().clear();
        listeFichiersParties.getChildren().add(new Label("Aucune partie enregistrée"));
    }



}
