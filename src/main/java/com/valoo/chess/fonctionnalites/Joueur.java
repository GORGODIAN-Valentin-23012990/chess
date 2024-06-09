package com.valoo.chess.fonctionnalites;

import java.io.File;
import java.io.FileWriter;
import java.util.Scanner;

public class Joueur {
    private String nom;
    private String prenom;
    private int partieGagnee;
    private int partieJouee;
    /**
     * @param nom  nom du joueur
     * @param prenom prenom du joueur
     */
    public Joueur(String nom, String prenom) {
        this.nom = nom;
        this.prenom = prenom;
        this.partieGagnee = 0;
        this.partieJouee = 0;
        System.out.println(getJoueur(nom, prenom));

    }

    /**
     * Cette fonction permet de récupérer le nombre de parties jouées
     * @param partieGagnee nombre de parties gagnées
     */
    public void setPartieGagnee(int partieGagnee) {
        this.partieGagnee = partieGagnee;
    }
    /**
     * @return nombre de parties gagnées
     */
    public String toString() {
        return "["+nom +","+ prenom + " , " + partieGagnee + "," + partieJouee + " ]";
    }

    /**
     * Cette fonction permet d'incrémenter le nombre de parties jouées
     */
    public void gagne() {
        partieGagnee++;
        partieJouee++;
    }

    public String getNom() {
        return nom;
    }
    public String getPrenom() {
        return prenom;
    }

    /**
     * Cette fonction permet d'ajouter un joueur au fichier Joueurs.txt si il n'y est pas encore puis renvoie ses statistiques
     * @param nom nom du joueur
     * @param prenom prenom du joueur
     * @return les statistiques du joueur
     */
    public String getJoueur(String nom, String prenom) {
        try (Scanner scanner = new Scanner(new File("src/main/resources/stockage/Joueurs.txt"))) {
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                String[] parts = line.split(",");
                if (parts[0].equals(nom) && parts[1].equals(prenom)) {
                    return line;
                }
            }
        } catch (Exception e) {
            System.err.println("Error reading file: " + e.getMessage());
        }

        try (FileWriter fileWriter = new FileWriter("src/main/resources/stockage/Joueurs.txt", true)) {
            fileWriter.write(nom + "," + prenom + ",0,0\n");
        } catch (Exception e) {
            System.err.println("Error writing to file: " + e.getMessage());
        }

        return nom + "," + prenom + ",0,0";
    }

}