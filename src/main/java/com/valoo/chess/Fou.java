package com.valoo.chess;

public class Fou extends Piece {

    public Fou(String color, String type, int couleur, int x, int y) {
        super(color, type, couleur, x, y);
    }

    // La fonction validMoves renvoie un tableau de coordonnées de toutes les positions possibles
    // Exemple de renvoi [ [1, 2], [3, 4], [5, 6] ]
    public int[][] validMoves (ChessBoard board) {
        int[][] moves = new int[13][2];
        int i = 0;
        for (int x = getX() + 1, y = getY() + 1; x < 8 && y < 8; x++, y++) {
            if (board.getPiece(x, y) == null) {
                moves[i][0] = x;
                moves[i][1] = y;
                i++;
            }
            else if (!estMemeCouleur(board.getPiece(x, y))) {
                moves[i][0] = x;
                moves[i][1] = y;
                i++;
                break;
            }
            else break;
        }
        for (int x = getX() - 1, y = getY() + 1; x >= 0 && y < 8; x--, y++) {
            if (board.getPiece(x, y) == null) {
                moves[i][0] = x;
                moves[i][1] = y;
                i++;
            }
            else if (!estMemeCouleur(board.getPiece(x, y))) {
                moves[i][0] = x;
                moves[i][1] = y;
                i++;
                break;
            }
            else break;
        }
        for (int x = getX() + 1, y = getY() - 1; x < 8 && y >= 0; x++, y--) {
            if (board.getPiece(x, y) == null) {
                moves[i][0] = x;
                moves[i][1] = y;
                i++;
            }
            else if (!estMemeCouleur(board.getPiece(x, y))) {
                moves[i][0] = x;
                moves[i][1] = y;
                i++;
                break;
            }
            else break;
        }
        for (int x = getX() - 1, y = getY() - 1; x >= 0 && y >= 0; x--, y--) {
            if (board.getPiece(x, y) == null) {
                moves[i][0] = x;
                moves[i][1] = y;
                i++;
            }
            else if (!estMemeCouleur(board.getPiece(x, y))) {
                moves[i][0] = x;
                moves[i][1] = y;
                i++;
                break;
            }
            else break;
        }
        return moves;
    }

    public String getImage() {
        if (getCouleur() == 0) {
            return "/fou_noir.png";
        }
        else {
            return "/fou_blanc.png";
        }
    }

    public String toString() {
        return "Fou[x=" + getX() + ",y=" + getY() + "]";
    }

}
