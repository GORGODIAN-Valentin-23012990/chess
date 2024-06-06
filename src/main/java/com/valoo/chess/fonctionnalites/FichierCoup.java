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
        int random = (int) (Math.random() * 1000);
        this.fileName = String.format("src/main/resources/parties/partie%d.txt", random);
        System.out.println("File name: " + fileName);
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
        System.out.println("Writing move to file: " + fileName);
        try (PrintWriter printWriter = new PrintWriter(new FileWriter(fileName, true))) {
            printWriter.println(String.format("%d%d%d%d", xAvant, yAvant, xApres, yApres));
        } catch (IOException e) {
            System.err.println("Error writing move to file: " + e.getMessage());
        }
    }

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

    public void jouerPartie(ChessBoard board, String fileName) {
        try (Scanner input = new Scanner(new File(fileName))) {
            while (input.hasNext()) {
                String line = input.nextLine();
                int xAvant = Integer.parseInt(line.substring(0, 1));
                int yAvant = Integer.parseInt(line.substring(1, 2));
                int xApres = Integer.parseInt(line.substring(2, 3));
                int yApres = Integer.parseInt(line.substring(3, 4));
                board.movePiece(xAvant, yAvant, xApres, yApres);
            }
        } catch (Exception e) {
            System.err.println("Error playing game from file: " + e.getMessage());
        }
    }

    public void annulerCoup(ChessBoard board, String fileName) {
        try (Scanner input = new Scanner(new File(fileName));
             PrintWriter printWriter = new PrintWriter(new FileWriter("parties/Historique.txt"))) {

            while (input.hasNext()) {
                String line = input.nextLine();
                if (input.hasNext()) {
                    printWriter.println(line);
                    int xAvant = Integer.parseInt(line.substring(0, 1));
                    int yAvant = Integer.parseInt(line.substring(1, 2));
                    int xApres = Integer.parseInt(line.substring(2, 3));
                    int yApres = Integer.parseInt(line.substring(3, 4));
                    board.movePiece(xAvant, yAvant, xApres, yApres);
                }
            }
            board.updateBoard();
        } catch (Exception e) {
            System.err.println("Error undoing move: " + e.getMessage());
        }
    }
}
