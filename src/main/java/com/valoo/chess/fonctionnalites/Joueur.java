package com.valoo.chess.fonctionnalites;

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
    }

    /**
     * @return nombre de parties gagnées
     */
    public String toString() {
        return "["+nom +","+ prenom + " , " + partieGagnee + "," + partieJouee + " ]";
    }

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

}