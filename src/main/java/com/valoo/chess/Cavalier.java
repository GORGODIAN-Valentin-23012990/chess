package com.valoo.chess;
public class Cavalier extends Piece {

    public Cavalier(String color, String type, int couleur, int x, int y) {
        super(color, type, couleur, x, y);
    }

    // La fonction validMoves renvoie un tableau de coordonnées de toutes les positions possibles
    // Exemple de renvoi [ [1, 2], [3, 4], [5, 6] ]
    public int[][] validMoves (ChessBoard board) {
        int[][] moves = new int[8][2];
        int i = 0;
        if (getX() + 2 < 8 && getY() + 1 < 8) {
            if (board.getPiece(getX() + 2, getY() + 1) == null || !estMemeCouleur(board.getPiece(getX() + 2, getY() + 1))) {
                moves[i][0] = getX() + 2;
                moves[i][1] = getY() + 1;
                i++;
            }
        }
        if (getX() + 2 < 8 && getY() - 1 >= 0) {
            if (board.getPiece(getX() + 2, getY() - 1) == null || !estMemeCouleur(board.getPiece(getX() + 2, getY() - 1))) {
                moves[i][0] = getX() + 2;
                moves[i][1] = getY() - 1;
                i++;
            }
        }
        if (getX() - 2 >= 0 && getY() + 1 < 8) {
            if (board.getPiece(getX() - 2, getY() + 1) == null || !estMemeCouleur(board.getPiece(getX() - 2, getY() + 1))) {
                moves[i][0] = getX() - 2;
                moves[i][1] = getY() + 1;
                i++;
            }
        }
        if (getX() - 2 >= 0 && getY() - 1 >= 0) {
            if (board.getPiece(getX() - 2, getY() - 1) == null || !estMemeCouleur(board.getPiece(getX() - 2, getY() - 1))) {
                moves[i][0] = getX() - 2;
                moves[i][1] = getY() - 1;
                i++;
            }
        }
        if (getX() + 1 < 8 && getY() + 2 < 8) {
            if (board.getPiece(getX() + 1, getY() + 2) == null || !estMemeCouleur(board.getPiece(getX() + 1, getY() + 2))) {
                moves[i][0] = getX() + 1;
                moves[i][1] = getY() + 2;
                i++;
            }
        }
        if (getX() + 1 < 8 && getY() - 2 >= 0) {
            if (board.getPiece(getX() + 1, getY() - 2) == null || !estMemeCouleur(board.getPiece(getX() + 1, getY() - 2))) {
                moves[i][0] = getX() + 1;
                moves[i][1] = getY() - 2;
                i++;
            }
        }
        if (getX() - 1 >= 0 && getY() + 2 < 8) {
            if (board.getPiece(getX() - 1, getY() + 2) == null || !estMemeCouleur(board.getPiece(getX() - 1, getY() + 2))) {
                moves[i][0] = getX() - 1;
                moves[i][1] = getY() + 2;
                i++;
            }
        }
        if (getX() - 1 >= 0 && getY() - 2 >= 0) {
            if (board.getPiece(getX() - 1, getY() - 2) == null || !estMemeCouleur(board.getPiece(getX() - 1, getY() - 2))) {
                moves[i][0] = getX() - 1;
                moves[i][1] = getY() - 2;
                i++;
            }
        }
        return moves;
    }

    public String getImage() {
        if (getCouleur() == 0) {
            return "/cavalier_noir.png";
        }
        else {
            return "/cavalier_blanc.png";
        }
    }

    public String toString() {
        return "Cavalier[x=" + getX() + ",y=" + getY() + "]";
    }

}
