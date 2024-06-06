package com.valoo.chess.fonctionnalites;

import com.valoo.chess.ChessBoard;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;

public class FichierCoup {
    private String fileName;
    private int indexHistorique;

    public FichierCoup() {
        File directory = new File("src/main/resources/parties/");
        File[] files = directory.listFiles();
        int fileCount = files != null ? files.length : 0;
        fileName = "src/main/resources/parties/Partie" + (fileCount + 1) + ".txt";

        // On vide le fichier Historique.txt et on y copie tous les coups de fileName
        try (PrintWriter printWriter = new PrintWriter(new FileWriter("src/main/resources/parties/Historique.txt"))) {
            // Vider le fichier Historique.txt
            printWriter.print("");
            // Ajouter le fichier de la nouvelle partie
            printWriter.println(fileName);
        } catch (IOException e) {
            System.err.println("Error writing move to file: " + e.getMessage());
        }

        // Initialisation de indexHistorique
        try (Scanner scanner = new Scanner(new File("src/main/resources/parties/Historique.txt"))) {
            while (scanner.hasNextLine()) {
                indexHistorique++;
                scanner.nextLine();
            }
        } catch (IOException e) {
            System.err.println("Error reading Historique.txt: " + e.getMessage());
        }
    }

    public FichierCoup(String fileName) {
        this.fileName = "src/main/resources/parties/" + fileName;
    }

    public String getFileName() {
        return fileName;
    }

    public void ecrireCoup(int xAvant, int yAvant, int xApres, int yApres) {
        try (PrintWriter printWriter = new PrintWriter(new FileWriter(fileName, true))) {
            printWriter.println(String.format("%d%d%d%d", xAvant, yAvant, xApres, yApres));
        } catch (IOException e) {
            System.err.println("Error writing move to file: " + e.getMessage());
        }
    }

    public void jouerPartie(ChessBoard board, String fileName) {
        System.out.println("Jouer partie: " + fileName);
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
            board.updateBoard();
        } catch (Exception e) {
            System.err.println("Error reading moves from file: " + e.getMessage());
        }
    }

    public void annulerCoup(ChessBoard board) {
        if (indexHistorique <= 1) {
            System.err.println("No moves to undo.");
            return;
        }

        indexHistorique--;

        try (Scanner input = new Scanner(new File("src/main/resources/parties/Historique.txt"))) {
            board.resetBoard();
            int currentLine = 0;
            while (input.hasNext() && currentLine < indexHistorique) {
                String line = input.nextLine();
                int xAvant = Character.getNumericValue(line.charAt(0));
                int yAvant = Character.getNumericValue(line.charAt(1));
                int xApres = Character.getNumericValue(line.charAt(2));
                int yApres = Character.getNumericValue(line.charAt(3));
                board.movePiece(xAvant, yAvant, xApres, yApres);
                currentLine++;
            }
        } catch (Exception e) {
            System.err.println("Error reading moves from file: " + e.getMessage());
        }

        board.updateBoard();
    }

    public void coupSuivant(ChessBoard board) {
        indexHistorique++;

        try (Scanner input = new Scanner(new File("src/main/resources/parties/Historique.txt"))) {
            board.resetBoard();
            int currentLine = 0;
            while (input.hasNext() && currentLine < indexHistorique) {
                String line = input.nextLine();
                int xAvant = Character.getNumericValue(line.charAt(0));
                int yAvant = Character.getNumericValue(line.charAt(1));
                int xApres = Character.getNumericValue(line.charAt(2));
                int yApres = Character.getNumericValue(line.charAt(3));
                board.movePiece(xAvant, yAvant, xApres, yApres);
                currentLine++;
            }
        } catch (Exception e) {
            System.err.println("Error reading moves from file: " + e.getMessage());
        }

        board.updateBoard();
    }
}