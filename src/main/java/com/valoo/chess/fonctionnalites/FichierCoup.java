package com.valoo.chess.fonctionnalites;

import com.valoo.chess.ChessBoard;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

public class FichierCoup {
    private String fileName;

    public FichierCoup() {
        // On génère un nombre au hasard pour le nom du fichier
        int random = (int) (Math.random() * 1000);
        this.fileName = "Partie" + random + ".txt";
    }

    private int countFilesInDirectory(String directoryPath) {
        File directory = new File(directoryPath);
        File[] files = directory.listFiles();
        if (files != null) {
            return files.length;
        }
        return 0;
    }

    // Cette fonction recoit en paramètre les valeurs x et y avant le coup et x et y après le coup
    // Elle écrit ces 4 valeurs à la suite dans le fichier fileName
    public void ecrireCoup(int xAvant, int yAvant, int xApres, int yApres) {
        try {
            // On ouvre le fichier en écriture
            FileWriter fileWriter = new FileWriter(fileName, true);
            PrintWriter printWriter = new PrintWriter(fileWriter);
            // On écrit les 4 valeurs à la suite
            printWriter.println(xAvant + "" + yAvant + "" + xApres + "" + yApres);
            printWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Cette fonction recoit en paramètre un nom de fichier, un index, et elle renvoie le coup à l'index donné
    public void lireCoup(int index) {
        try {
            // On ouvre le fichier en lecture
            java.io.File file = new java.io.File(fileName);
            java.util.Scanner input = new java.util.Scanner(file);
            int i = 0;
            while (input.hasNext()) {
                // On lit chaque ligne du fichier
                String line = input.nextLine();
                // Si l'index de la ligne est égal à l'index donné en paramètre
                if (i == index) {
                    // On affiche la ligne
                    System.out.println(line);
                    break;
                }
                i++;
            }
            input.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Cette fonction prend en paramètre un chessboard, et effectue tous les coups du fichier fileName
    public void jouerPartie(ChessBoard board){
        try {
            // On ouvre le fichier en lecture
            java.io.File file = new java.io.File(fileName);
            java.util.Scanner input = new java.util.Scanner(file);
            while (input.hasNext()) {
                // On lit chaque ligne du fichier
                String line = input.nextLine();
                // On récupère les valeurs x et y avant le coup et x et y après le coup
                int xAvant = Integer.parseInt(line.substring(0, 1));
                int yAvant = Integer.parseInt(line.substring(1, 2));
                int xApres = Integer.parseInt(line.substring(2, 3));
                int yApres = Integer.parseInt(line.substring(3, 4));
                // On effectue le coup
                board.movePiece(xAvant, yAvant, xApres, yApres);
            }
            input.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}