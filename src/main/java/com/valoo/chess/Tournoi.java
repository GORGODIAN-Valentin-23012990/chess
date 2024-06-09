package com.valoo.chess;

import com.valoo.chess.controller.MainController;
import com.valoo.chess.fonctionnalites.Joueur;
import javafx.application.Platform;
import javafx.concurrent.Service;
import javafx.concurrent.Task;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Tournoi {
    private List<Match> matches = new ArrayList<>();
    private MainController mainController;

    /**
     * Cette méthode permet de créer un tournoi
     * @param players       liste des joueurs
     * @param mainController contrôleur principal
     */
    public Tournoi(List<Joueur> players, MainController mainController) {
        this.mainController = mainController;
        for (int i = 0; i < players.size(); i += 2) {
            matches.add(new Match(players.get(i), players.get(i + 1), mainController));
        }
    }

    /**
     * Cette méthode permet de mélanger les joueurs
     */
    public void printTournoiTree() {
        System.out.println("Tournoi tree:");
        for (int i = 0; i < matches.size(); i++) {
            System.out.println("Match " + (i + 1) + ": " + matches.get(i).getJoueur1().getNom() + " vs " + matches.get(i).getJoueur2().getNom());
        }
    }

    /**
     * Cette méthode permet de commencer le tournoi
     */
    public void startTournament() {
        Service<Void> service = new Service<>() {
            @Override
            protected Task<Void> createTask() {
                return new Task<>() {
                    @Override
                    protected Void call() {
                        while (!matches.isEmpty()) {
                            Match match = matches.get(0); // Get the first match
                            Platform.runLater(() -> {
                                mainController.setCurrentMatch(match);
                                match.startMatch();
                            });
                            while (!match.isMatchOver()) {
                                try {
                                    Thread.sleep(1000); // wait for 1 second before checking again
                                } catch (InterruptedException e) {
                                    e.printStackTrace();
                                }
                            }
                            // Remove the match from the list
                            matches.remove(match);
                            // Add the winner to the next match if there is one
                            if (!matches.isEmpty()) {
                                matches.get(0).setJoueur1(match.getWinner());
                            }
                        }
                        return null;
                    }
                };
            }
        };
        service.start();
    }
}