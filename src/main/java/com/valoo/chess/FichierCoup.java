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
}