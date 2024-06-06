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
        // On ouvre le fichier en écriture pour le créer s'il n'existe pas
        try (PrintWriter printWriter = new PrintWriter(new FileWriter(fileName))) {
            printWriter.print("");
        } catch (IOException e) {
            System.err.println("Error writing move to file: " + e.getMessage());
        }

        // indexHistorique prend la valeur du nombre de lignes du fichier
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

        // On ouvre le fichier en écriture pour le créer s'il n'existe pas
        try (PrintWriter printWriter = new PrintWriter(new FileWriter(fileName))) {
            printWriter.println();
        } catch (IOException e) {
            System.err.println("Error writing move to file: " + e.getMessage());
        }


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
        try (PrintWriter printWriter = new PrintWriter(new FileWriter(fileName, true))) {
            printWriter.println(coup);
        } catch (IOException e) {
            System.err.println("Error writing move to file: " + e.getMessage());
        }
    }

    // Cette fonction est similaire à ecrireCoup sauf qu'elle prend en paramètre unn String et l'ajoute directement au fichier
    public void ecrireCoup(String coup) {
        coups.add(coup);
        try (PrintWriter printWriter = new PrintWriter(new FileWriter(fileName, true))) {
            printWriter.println(coup);
        } catch (IOException e) {
            System.err.println("Error writing move to file: " + e.getMessage());
        }
    }

    public void jouerPartie(ChessBoard board, String fileName) {
        indexHistorique = 0;
        int xApres = 0, yApres = 0;
        try (Scanner input = new Scanner(new File("src/main/resources/parties/" + fileName))) {
            if(input == null) {
                return;
                // On vérifie ensuite si le fichier est vide
            } else if (!input.hasNext()) {
                return;
            } else {
                board.resetBoard();
                while (input.hasNext()) {
                    String line = input.nextLine();
                    ++indexHistorique;
                    int xAvant = Character.getNumericValue(line.charAt(0));
                    int yAvant = Character.getNumericValue(line.charAt(1));
                    xApres = Character.getNumericValue(line.charAt(2));
                    yApres = Character.getNumericValue(line.charAt(3));
                    board.movePiece(xAvant, yAvant, xApres, yApres, false);
                }
                // On regarde si la dernière pièce bougée est noire ou blanche et on change le tour en conséquence
                if (board.getPiece(xApres, yApres).getCouleur() == 0) {
                    board.setTour(1);
                } else {
                    board.setTour(0);
                }
                board.updateBoard();
            }
        } catch (Exception e) {
            System.err.println("Error playing game from file: " + e.getMessage());
        }
    }

    public void annulerCoup(ChessBoard board, String fileName) {
        --indexHistorique;
        // on fait le coup de chaque ligne jusquà indexHistorique
        board.resetBoard();
        try (Scanner input = new Scanner(new File(fileName))) {
            if(input == null) {
                return;
            } else {
                System.out.println(indexHistorique);
                for(int i = 0; i < indexHistorique; i++) {
                    String line = input.nextLine();
                    int xAvant = Character.getNumericValue(line.charAt(0));
                    int yAvant = Character.getNumericValue(line.charAt(1));
                    int xApres = Character.getNumericValue(line.charAt(2));
                    int yApres = Character.getNumericValue(line.charAt(3));
                    board.movePiece(xAvant, yAvant, xApres, yApres, false);
                }
                board.updateBoard();
            }
        } catch (Exception e) {
            ++indexHistorique;
        }
    }

    public void coupSuivant(ChessBoard board, String fileName) {
        ++indexHistorique;
        // on fait le coup de chaque ligne jusquà indexHistorique
        try (Scanner input = new Scanner(new File(fileName))) {
            if(input == null) {
                return;
            } else {
                System.out.println(indexHistorique);
                for(int i = 0; i < indexHistorique; i++) {
                    String line = input.nextLine();
                    int xAvant = Character.getNumericValue(line.charAt(0));
                    int yAvant = Character.getNumericValue(line.charAt(1));
                    int xApres = Character.getNumericValue(line.charAt(2));
                    int yApres = Character.getNumericValue(line.charAt(3));
                    board.movePiece(xAvant, yAvant, xApres, yApres, false);
                }
                board.updateBoard();
            }
        } catch (Exception e) {
            indexHistorique--;
        }
    }

    public void setIndexHistorique(int indexHistorique) {
        this.indexHistorique = indexHistorique;
    }
}
