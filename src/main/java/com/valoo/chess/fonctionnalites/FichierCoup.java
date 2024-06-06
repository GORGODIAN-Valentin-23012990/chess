package com.valoo.chess.fonctionnalites;

import com.valoo.chess.ChessBoard;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;

public class FichierCoup {
    private String fileName;

    public FichierCoup() {
        File directory = new File("src/main/resources/parties/");
        File[] files = directory.listFiles();
        int fileCount = files != null ? files.length : 0;
        fileName = "src/main/resources/parties/Partie" + (fileCount + 1) + ".txt";
    }

    public FichierCoup(String fileName) {
        this.fileName = "src/main/resources/parties/" + fileName;
    }

    public String getFileName() {
        return fileName;
    }

    private int countFilesInDirectory(String directoryPath) {
        File directory = new File(directoryPath);
        File[] files = directory.listFiles();
        return files != null ? files.length : 0;
    }

    public void ecrireCoup(int xAvant, int yAvant, int xApres, int yApres) {
        try (PrintWriter printWriter = new PrintWriter(new FileWriter(fileName, true))) {
            printWriter.println(String.format("%d%d%d%d", xAvant, yAvant, xApres, yApres));
        } catch (IOException e) {
            System.err.println("Error writing move to file: " + e.getMessage());
        }
    }

    // Cette fonction prend en parametre un index et affiche le coup correspondant dans le fichier
    public void lireCoup(int index) {
        try (Scanner input = new Scanner(new File(fileName))) {
            for (int i = 0; input.hasNext(); i++) {
                String line = input.nextLine();
                if (i == index) {
                    System.out.println(line);
                    return;
                }
            }
        } catch (Exception e) {
            System.err.println("Error reading move from file: " + e.getMessage());
        }
    }

    // Cette fonction prend en parametre un chessboard et le nom d'un fichier, clear le chessboard et joue tous les coups stockés dans le fichier à l'aide de la fonction lireCoup
    public void jouerPartie(ChessBoard board, String fileName) {
        try (Scanner input = new Scanner(new File("src/main/resources/parties/" + fileName))) {
            board.resetBoard();
            while (input.hasNext()) {
                String line = input.nextLine();
                int xAvant = Character.getNumericValue(line.charAt(0));
                int yAvant = Character.getNumericValue(line.charAt(1));
                int xApres = Character.getNumericValue(line.charAt(2));
                int yApres = Character.getNumericValue(line.charAt(3));
                board.movePiece(xAvant, yAvant, xApres, yApres);
            }
        } catch (Exception e) {
            System.err.println("Error playing game from file: " + e.getMessage());
        }
    }
}
