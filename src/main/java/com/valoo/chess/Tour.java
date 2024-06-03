package com.valoo.chess;

public class Tour extends Piece {

    public Tour (String color, String type, int couleur, int x, int y) {
        super(color, type, couleur, x, y);
    }

    // La fonction validMoves renvoie un tableau de coordonnées de toutes les positions possibles
    // Exemple de renvoi [ [1, 2], [3, 4], [5, 6] ]
    public int[][] validMoves (ChessBoard board) {
        int[][] moves = new int[14][2];
        int i = 0;
        for (int x = getX() + 1; x < 8; x++) {
            if (board.getPiece(x, getY()) == null) {
                moves[i][0] = x;
                moves[i][1] = getY();
                i++;
            }
            else if (!estMemeCouleur(board.getPiece(x, getY()))) {
                moves[i][0] = x;
                moves[i][1] = getY();
                i++;
                break;
            }
            else break;
        }
        for (int x = getX() - 1; x >= 0; x--) {
            if (board.getPiece(x, getY()) == null) {
                moves[i][0] = x;
                moves[i][1] = getY();
                i++;
            }
            else if (!estMemeCouleur(board.getPiece(x, getY()))) {
                moves[i][0] = x;
                moves[i][1] = getY();
                i++;
                break;
            }
            else break;
        }
        for (int y = getY() + 1; y < 8; y++) {
            if (board.getPiece(getX(), y) == null) {
                moves[i][0] = getX();
                moves[i][1] = y;
                i++;
            }
            else if (!estMemeCouleur(board.getPiece(getX(), y))) {
                moves[i][0] = getX();
                moves[i][1] = y;
                i++;
                break;
            }
            else break;
        }
        for (int y = getY() - 1; y >= 0; y--) {
            if (board.getPiece(getX(), y) == null) {
                moves[i][0] = getX();
                moves[i][1] = y;
                i++;
            }
            else if (!estMemeCouleur(board.getPiece(getX(), y))) {
                moves[i][0] = getX();
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
            return "/tour_noir.png";
        }
        else {
            return "/tour_blanc.png";
        }
    }

    public String toString() {
        return "Tour[x=" + getX() + ",y=" + getY() + "]";
    }

}
