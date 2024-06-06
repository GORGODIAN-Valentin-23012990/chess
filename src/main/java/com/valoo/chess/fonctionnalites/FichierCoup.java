package com.valoo.chess.fonctionnalites;

import com.valoo.chess.ChessBoard;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class FichierCoup {
    private String fileName;
    private List<String> coups;
    private int indexHistorique;

    public FichierCoup() {
        File directory = new File("src/main/resources/parties/");
        File[] files = directory.listFiles();
        int fileCount = files != null ? files.length : 0;
        fileName = "src/main/resources/parties/Partie" + (fileCount + 1) + ".txt";
        coups = new ArrayList<>();
        indexHistorique = 0;

        // On vide le fichier Historique.txt et on y copie tous les coups de fileName
        try (PrintWriter printWriter = new PrintWriter(new FileWriter("src/main/resources/Historique.txt"))) {
            // Vider le fichier Historique.txt
            printWriter.print("");
            // Ajouter le fichier de la nouvelle partie
            printWriter.println(fileName);
        } catch (IOException e) {
            System.err.println("Error writing move to file: " + e.getMessage());
        }

        // Initialisation de coups
        try (Scanner scanner = new Scanner(new File(fileName))) {
            while (scanner.hasNextLine()) {
                coups.add(scanner.nextLine());
            }
        } catch (IOException e) {
            System.err.println("Error reading moves from file: " + e.getMessage());
        }
    }

    public FichierCoup(String fileName) {
        this.fileName = "src/main/resources/parties/" + fileName;
        coups = new ArrayList<>();
        indexHistorique = 0;

        // Initialisation de coups
        try (Scanner scanner = new Scanner(new File(this.fileName))) {
            while (scanner.hasNextLine()) {
                coups.add(scanner.nextLine());
            }
        } catch (IOException e) {
            System.err.println("Error reading moves from file: " + e.getMessage());
        }
    }

    public String getFileName() {
        return fileName;
    }

    public void ecrireCoup(int xAvant, int yAvant, int xApres, int yApres) {
        String coup = String.format("%d%d%d%d", xAvant, yAvant, xApres, yApres);
        coups.add(coup);
        indexHistorique++;
        try (PrintWriter printWriter = new PrintWriter(new FileWriter(fileName, true))) {
            printWriter.println(coup);
        } catch (IOException e) {
            System.err.println("Error writing move to file: " + e.getMessage());
        }
    }

    public void jouerPartie(ChessBoard board, String fileName) {
        int xApres = 0, yApres = 0;
        System.out.println("Jouer partie: " + fileName);
        try (Scanner input = new Scanner(new File("src/main/resources/parties/" + fileName))) {
            board.resetBoard();
            while (input.hasNext()) {
                String line = input.nextLine();
                int xAvant = Character.getNumericValue(line.charAt(0));
                int yAvant = Character.getNumericValue(line.charAt(1));
                xApres = Character.getNumericValue(line.charAt(2));
                yApres = Character.getNumericValue(line.charAt(3));
                board.movePiece(xAvant, yAvant, xApres, yApres);
            }
            // On regarde si la dernière pièce bougée est noire ou blanche et on change le tour en conséquence
            if (board.getPiece(xApres, yApres).getCouleur() == 0) {
                board.setTour(1);
            } else {
                board.setTour(0);
            }
            board.updateBoard();
        } catch (Exception e) {
            System.err.println("Error playing game from file: " + e.getMessage());
        }
    }

    public void annulerCoup(ChessBoard board) {
        if (indexHistorique <= 0) {
            System.err.println("No moves to undo.");
            return;
        }

        indexHistorique--;
        rejouerCoups(board);
    }

    public void coupSuivant(ChessBoard board) {
        if (indexHistorique >= coups.size()) {
            System.err.println("No moves to redo.");
            return;
        }

        indexHistorique++;
        rejouerCoups(board);
    }

    private void rejouerCoups(ChessBoard board) {
        board.resetBoard();
        for (int i = 0; i < indexHistorique; i++) {
            String line = coups.get(i);
            int xAvant = Character.getNumericValue(line.charAt(0));
            int yAvant = Character.getNumericValue(line.charAt(1));
            int xApres = Character.getNumericValue(line.charAt(2));
            int yApres = Character.getNumericValue(line.charAt(3));
            board.movePiece(xAvant, yAvant, xApres, yApres);
        }
        board.updateBoard();
    }
}
