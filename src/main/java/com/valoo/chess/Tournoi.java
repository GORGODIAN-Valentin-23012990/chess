package com.valoo.chess;

import com.valoo.chess.fonctionnalites.Joueur;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

public class Tournoi {

    private Joueur joueur1;
    private Joueur joueur2;

    public Joueur getJoueur1() {
        return joueur1;
    }

    /**
     * @return second joueur du duel
     */
    public Joueur getJoueur2() {
        return joueur2;
    }

    //puni de scanner
    private List<Joueur> joueurs = new ArrayList<>();
    private void addJoueurs(String nom , String prenom) {
        Joueur joueur = new Joueur(nom, prenom);
        joueurs.add(joueur);

    }

    public void tournoyer() {
        for (int i = 0; i < joueurs.size(); i++) {
            for (int j = i + 1; j < joueurs.size(); j++) {
                System.out.println("Match: " + joueurs.get(i).getNom() + " vs " + joueurs.get(j).getNom());




            }
        }
    }


    }


