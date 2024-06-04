package com.valoo.chess;

public class Tour extends Piece {

    public Tour (String color, String type, int couleur, int x, int y) {
        super(color, type, couleur, x, y);
    }

    // La fonction validMoves renvoie un tableau de coordonnées de toutes les positions possibles
    // Exemple de renvoi [ [1, 2], [3, 4], [5, 6] ]
    public int[][] validMoves (ChessBoard board) {
        int[][] moves = new int[64][2]; // La tour peut théoriquement avoir jusqu'à 64 mouvements possibles
        int i = 0;

        int[] directions = {1, -1}; // Pour se déplacer dans les directions positive et négative

        // Vérifier les mouvements horizontaux (à gauche et à droite)
        for (int dir : directions) {
            int x = getX();
            while (true) {
                x += dir;
                if (x < 0 || x >= 8) break; // Sortir de l'échiquier
                if (board.getPiece(x, getY()) == null) {
                    moves[i][0] = x;
                    moves[i][1] = getY();
                    i++;
                } else {
                    if (!estMemeCouleur(board.getPiece(x, getY()))) {
                        moves[i][0] = x;
                        moves[i][1] = getY();
                        i++;
                    }
                    break; // Bloqué par une pièce, qu'elle soit de la même couleur ou adverse
                }
            }
        }

        // Vérifier les mouvements verticaux (vers le haut et vers le bas)
        for (int dir : directions) {
            int y = getY();
            while (true) {
                y += dir;
                if (y < 0 || y >= 8) break; // Sortir de l'échiquier
                if (board.getPiece(getX(), y) == null) {
                    moves[i][0] = getX();
                    moves[i][1] = y;
                    i++;
                } else {
                    if (!estMemeCouleur(board.getPiece(getX(), y))) {
                        moves[i][0] = getX();
                        moves[i][1] = y;
                        i++;
                    }
                    break; // Bloqué par une pièce, qu'elle soit de la même couleur ou adverse
                }
            }
        }

        // Réduire la taille du tableau pour n'inclure que les mouvements réellement trouvés
        int[][] validMoves = new int[i][2];
        System.arraycopy(moves, 0, validMoves, 0, i);

        return validMoves;
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
