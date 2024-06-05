package com.valoo.chess;

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

class Joueur {
    String nom;

    Joueur(String nom) {
        this.nom = nom;
    }

    @Override
    public String toString() {
        return nom;
    }
}

public class Tournoi {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // Inscription des joueurs
        List<Joueur> joueurs = new ArrayList<>();
        System.out.println("Entrez le nombre de joueurs :");
        int nombreDeJoueurs = 0;

        while (true) {
            nombreDeJoueurs = scanner.nextInt();
            scanner.nextLine();
            if (nombreDeJoueurs % 2 != 0) {
                System.out.println("Le nombre de joueurs doit être pair. Veuillez réessayer.");
            } else {
                break;
            }
        }

        for (int i = 0; i < nombreDeJoueurs; i++) {
            System.out.println("Entrez le nom du joueur " + (i + 1) + " :");
            String nom = scanner.nextLine();
            joueurs.add(new Joueur(nom));
        }


        // Premier tour
        List<Joueur> premierTour = jouerTour(joueurs, scanner, "Premier tour");

        // Demi-finales
        List<Joueur> demiFinales = jouerTour(premierTour, scanner, "Demi-finales");

        // Finale
        List<Joueur> finale = jouerTour(demiFinales, scanner, "Finale");

        // Champion
        System.out.println("\nChampion du tournoi : " + finale.get(0));

        scanner.close();
    }

    // Organiser les matchs et jouer un tour
    private static List<Joueur> jouerTour(List<Joueur> joueurs, Scanner scanner, String nomDuTour) {
        System.out.println("\n" + nomDuTour);
        List<Joueur> gagnants = new ArrayList<>();

        for (int i = 0; i < joueurs.size(); i += 2) {
            if (i + 1 < joueurs.size()) {
                Joueur joueur1 = joueurs.get(i);
                Joueur joueur2 = joueurs.get(i + 1);
                System.out.println(joueur1 + " vs " + joueur2);

                Joueur gagnant = determinerGagnant(joueur1, joueur2, scanner);
                System.out.println("Gagnant : " + gagnant);
                gagnants.add(gagnant);
            }
        }

        return gagnants;
    }

    // Déterminer le gagnant du match
    private static Joueur determinerGagnant(Joueur joueur1, Joueur joueur2, Scanner scanner) {
        while (true) {
            System.out.println("Qui a gagné ? (1 pour " + joueur1.nom + ", 2 pour " + joueur2.nom + ") :");
            try {
                int resultat = scanner.nextInt();
                if (resultat == 1) {
                    return joueur1;
                } else if (resultat == 2) {
                    return joueur2;
                } else {
                    System.out.println("Entrée invalide. Veuillez entrer 1 ou 2.");
                }
            } catch (InputMismatchException e) {
                System.out.println("Entrée invalide. Veuillez entrer un nombre entier.");
                scanner.next();
            }
        }
    }
}
