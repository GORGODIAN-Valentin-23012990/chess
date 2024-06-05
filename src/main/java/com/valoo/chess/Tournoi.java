package com.valoo.chess;

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

class Joueur0 {
    String nom;

    Joueur0(String nom) {
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
        List<Joueur0> joueurs = new ArrayList<>();
        System.out.println("Entrez le nombre de joueurs :");
        int nombreDeJoueurs = 0;

        while (true) {
            try {
                nombreDeJoueurs = scanner.nextInt();
                scanner.nextLine(); // Consommer la nouvelle ligne
                if (nombreDeJoueurs < 2) {
                    System.out.println("Le nombre de joueurs doit être au moins 2. Veuillez réessayer.");
                } else {
                    break;
                }

            } catch (InputMismatchException e) {
                System.out.println("Entrée invalide. Veuillez entrer un nombre entier.");
                scanner.next(); // Consommer l'entrée invalide
            }
        }

        for (int i = 0; i < nombreDeJoueurs; i++) {
            System.out.println("Entrez le nom du joueur " + (i + 1) + " :");
            String nom = scanner.nextLine();
            joueurs.add(new Joueur0(nom));
        }

        // Phase de qualification
        List<Joueur0> qualification = jouerTour(joueurs, scanner, "Qualifications");

        // Premier tour
        List<Joueur0> premierTour = jouerTour(qualification, scanner, "Premier tour");

        // Demi-finales
        List<Joueur0> demiFinales = jouerTour(premierTour, scanner, "Demi-finales");

        // Finale
        List<Joueur0> finale = jouerTour(demiFinales, scanner, "Finale");

        // Champion
        System.out.println("\nChampion du tournoi : " + finale.get(0));

        scanner.close();
    }

    // Organise les matchs et joue un tour
    private static List<Joueur0> jouerTour(List<Joueur0> joueurs, Scanner scanner, String nomDuTour) {
        System.out.println("\n" + nomDuTour);
        List<Joueur0> gagnants = new ArrayList<>();

        for (int i = 0; i < joueurs.size(); i += 2)
        {
            if (i + 1 < joueurs.size()) // vérifie si le joueur suivant existe pour faire un groupe de 2

            {
                Joueur0 joueur1 = joueurs.get(i);
                Joueur0 joueur2 = joueurs.get(i + 1);
                System.out.println(joueur1 + " vs " + joueur2);

                Joueur0 gagnant = determinerGagnant(joueur1, joueur2, scanner);
                System.out.println("Gagnant : " + gagnant);
                gagnants.add(gagnant);
            } else {
                // Si le nombre de joueurs est impair, le dernier joueur passe automatiquement au tour suivant
                gagnants.add(joueurs.get(i));
            }
        }

        return gagnants;
    }

    // Détermine le gagnant du match
    private static Joueur0 determinerGagnant(Joueur0 joueur1, Joueur0 joueur2, Scanner scanner) {
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
                scanner.next(); // Consommer l'entrée invalide
            }
        }
    }
}
