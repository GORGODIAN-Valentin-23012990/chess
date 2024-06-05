package com.valoo.chess;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

public class FichierCoup {
    private String fileName;

    public FichierCoup(String fileName) {
        this.fileName = fileName;
    }

    public void enregistrerCoup(String coup) {
        try (PrintWriter out = new PrintWriter(new FileWriter(fileName, true))) {
            out.println(coup);
        } catch (IOException e) {
            System.out.println("Une erreur s'est produite lors de l'écriture dans le fichier.");
            e.printStackTrace();
        } catch (Exception e) {
            System.out.println("Une erreur inattendue s'est produite.");
            e.printStackTrace();
        }
    }

    public void effacerFichier() {
        try (PrintWriter out = new PrintWriter(fileName)) {
            out.print("");
        } catch (IOException e) {
            System.out.println("Une erreur s'est produite lors de l'écriture dans le fichier.");
            e.printStackTrace();
        }
    }

    // On lit le fichier en allant directement au numéro de ligne passé en paramètre
    public void lireCoup(int numeroLigne) {
        java.util.Scanner scanner = null;
        try {
            scanner = new java.util.Scanner(new java.io.File(fileName));
        } catch (java.io.FileNotFoundException e) {
            System.out.println("File not found.");
            e.printStackTrace();
        }
        for (int i = 0; i < numeroLigne; i++) {
            scanner.nextLine();
        }
        while (scanner.hasNext() && !scanner.next().equals(";")) {
            System.out.println("Coup joué : x=" + scanner.next() + " y=" + scanner.next() + " x2=" + scanner.next() + " y2=" + scanner.next());
        }
    }
}