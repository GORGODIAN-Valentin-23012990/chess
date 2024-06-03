package com.valoo.chess;

public class Pion extends Piece {

    public Pion(String color, String type, int couleur, int x, int y) {
        super(color, type, couleur, x, y);
    }


    // La fonction validMoves renvoie un tableau de coordonnées de toutes les positions possibles
    // Exemple de renvoi [ [1, 2], [3, 4], [5, 6] ]
    public int[][] validMoves (ChessBoard board) {
        int[][] moves = new int[2][2];
        int i = 0;
        if (getCouleur() == 0) {
            if (getY() == 1) {
                if (board.getPiece(getX(), 2) == null && board.getPiece(getX(), 3) == null) {
                    moves[i][0] = getX();
                    moves[i][1] = 3;
                    i++;
                }
            }
            if (getY() < 7) {
                if (board.getPiece(getX(), getY() + 1) == null) {
                    moves[i][0] = getX();
                    moves[i][1] = getY() + 1;
                    i++;
                }
            }
            if (getX() > 0 && getY() < 7) {
                if (board.getPiece(getX() - 1, getY() + 1) != null && !estMemeCouleur(board.getPiece(getX() - 1, getY() + 1))) {
                    moves[i][0] = getX() - 1;
                    moves[i][1] = getY() + 1;
                    i++;
                }
            }
            if (getX() < 7 && getY() < 7) {
                if (board.getPiece(getX() + 1, getY() + 1) != null && !estMemeCouleur(board.getPiece(getX() + 1, getY() + 1))) {
                    moves[i][0] = getX() + 1;
                    moves[i][1] = getY() + 1;
                    i++;
                }
            }
        }
        else {
            if (getY() == 6) {
                if (board.getPiece(getX(), 5) == null && board.getPiece(getX(), 4) == null) {
                    moves[i][0] = getX();
                    moves[i][1] = 4;
                    i++;
                }
            }
            if (getY() > 0) {
                if (board.getPiece(getX(), getY() - 1) == null) {
                    moves[i][0] = getX();
                    moves[i][1] = getY() - 1;
                    i++;
                }
            }
            if (getX() > 0 && getY() > 0) {
                if (board.getPiece(getX() - 1, getY() - 1) != null && !estMemeCouleur(board.getPiece(getX() - 1, getY() - 1))) {
                    moves[i][0] = getX() - 1;
                    moves[i][1] = getY() - 1;
                    i++;
                }
            }
            if (getX() < 7 && getY() > 0) {
                if (board.getPiece(getX() + 1, getY() - 1) != null && !estMemeCouleur(board.getPiece(getX() + 1, getY() - 1))) {
                    moves[i][0] = getX() + 1;
                    moves[i][1] = getY() - 1;
                    i++;
                }
            }
        }
        return moves;
    }

    public String getImage() {
        if (getCouleur() == 0) {
            return "/pion_noir.png";
        }
        else {
            return "/pion_blanc.png";
        }
    }

    public String toString() {
        return "Pion[x=" + getX() + ",y=" + getY() + "]";
    }

}
