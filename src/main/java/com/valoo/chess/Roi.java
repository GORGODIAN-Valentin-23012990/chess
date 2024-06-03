package com.valoo.chess;

public class Roi extends Piece {

    public Roi(String color, String type, int couleur, int x, int y) {
        super(color, type, couleur, x, y);
    }

    // La fonction validMoves renvoie un tableau de coordonnées de toutes les positions possibles
    // Exemple de renvoi [ [1, 2], [3, 4], [5, 6] ]
    public int[][] validMoves (ChessBoard board) {
        int[][] moves = new int[8][2];
        int i = 0;
        for (int x = getX() - 1; x <= getX() + 1; x++) {
            for (int y = getY() - 1; y <= getY() + 1; y++) {
                if (x >= 0 && x <= 7 && y >= 0 && y <= 7) {
                    if (board.getPiece(x, y) == null || !estMemeCouleur(board.getPiece(x, y))) {
                        moves[i][0] = x;
                        moves[i][1] = y;
                        i++;
                    }
                }
            }
        }
        return moves;
    }

    public String getImage() {
        if (getCouleur() == 0) {
            return "/roi_noir.png";
        }
        else {
            return "/roi_blanc.png";
        }
    }

    public String toString() {
        return "Roi[x=" + getX() + ",y=" + getY() + "]";
    }

}
