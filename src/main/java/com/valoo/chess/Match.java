package com.valoo.chess;

import com.valoo.chess.controller.MainController;
import com.valoo.chess.fonctionnalites.Joueur;

public class Match {
    private Joueur player1;
    private Joueur player2;
    private Joueur winner;
    private boolean isMatchOver = false;
    private MainController mainController;

    /**
     * Constructeur de la classe Match
     * @param player1
     * @param player2
     * @param mainController
     */
    public Match(Joueur player1, Joueur player2, MainController mainController) {
        this.player1 = player1;
        this.player2 = player2;
        this.mainController = mainController;
    }

    /**
     * Cette méthode permet de démarrer un match
     */
    public void startMatch() {
        mainController.gameCode(2);
        mainController.J1Label.setText(player1.getNom());
        mainController.J2Label.setText(player2.getNom());
    }

    /**
     * @param player1 joueur 1
     */
    public void setJoueur1(Joueur player1) {
        this.player1 = player1;
    }

    public Joueur getJoueur1() {
        return player1;
    }

    public Joueur getJoueur2() {
        return player2;
    }

    public Joueur getWinner() {
        return winner;
    }

    public void setWinner(Joueur winner) {
        this.winner = winner;
    }

    /**
     * @return true si le match est terminé
     */
    public boolean isMatchOver() {
        return mainController.isGameOver();
    }

    /**
     * @param matchOver true si le match est terminé
     */
    public void setMatchOver(boolean matchOver) {
        isMatchOver = matchOver;
    }
}