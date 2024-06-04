package com.valoo.chess;

public class Pion extends Piece {

    public Pion(String color, String type, int couleur, int x, int y) {
        super(color, type, couleur, x, y);
    }

    // La fonction validMoves renvoie un tableau de coordonnées de toutes les positions possibles
    // Exemple de renvoi [ [1, 2], [3, 4], [5, 6] ]
    public int[][] validMoves(ChessBoard board) {
        int[][] moves = new int[4][2]; // Un pion peut se déplacer sur un maximum de 4 cases (avancée de deux cases initiale + captures diagonales)
        int i = 0;

        int x = getX();
        int y = getY();

        // Déplacement vers l'avant pour un pion blanc
        if (getCouleur() == 0) {
            // Déplacement d'une case vers l'avant
            if (y < 7 && board.getPiece(x, y + 1) == null) {
                moves[i][0] = x;
                moves[i][1] = y + 1;
                i++;

                // Déplacement initial de deux cases
                if (y == 1 && board.getPiece(x, y + 2) == null && board.getPiece(x, y + 1) == null) {
                    moves[i][0] = x;
                    moves[i][1] = y + 2;
                    i++;
                }
            }

            // Capture diagonale droite pour un pion blanc
            if (x < 7 && y < 7 && board.getPiece(x + 1, y + 1) != null && !estMemeCouleur(board.getPiece(x + 1, y + 1))) {
                moves[i][0] = x + 1;
                moves[i][1] = y + 1;
                i++;
            }
            System.out.println(board.getPiece(x + 1, y + 1));
            // Capture diagonale gauche pour un pion blanc
            if (x > 0 && y < 7 && board.getPiece(x - 1, y + 1) != null && !estMemeCouleur(board.getPiece(x - 1, y + 1))) {
                moves[i][0] = x - 1;
                moves[i][1] = y + 1;
                i++;
            }
        } else {
            // Déplacement d'une case vers l'avant pour un pion noir
            if (y > 0 && board.getPiece(x, y - 1) == null) {
                moves[i][0] = x;
                moves[i][1] = y - 1;
                i++;

                // Déplacement initial de deux cases
                if (y == 6 && board.getPiece(x, y - 2) == null && board.getPiece(x, y - 1) == null) {
                    moves[i][0] = x;
                    moves[i][1] = y - 2;
                    i++;
                }
            }

            // Capture diagonale droite pour un pion noir
            if (x < 7 && y > 0 && board.getPiece(x + 1, y - 1) != null && !estMemeCouleur(board.getPiece(x + 1, y - 1))) {
                moves[i][0] = x + 1;
                moves[i][1] = y - 1;
                i++;
            }

            // Capture diagonale gauche pour un pion noir
            if (x > 0 && y > 0 && board.getPiece(x - 1, y - 1) != null && !estMemeCouleur(board.getPiece(x - 1, y - 1))) {
                moves[i][0] = x - 1;
                moves[i][1] = y - 1;
                i++;
            }
        }

        // Réduire la taille du tableau pour n'inclure que les mouvements réellement trouvés
        int[][] validMoves = new int[i][2];
        System.arraycopy(moves, 0, validMoves, 0, i);

        return validMoves;
    }

    public String getImage() {
        if (getCouleur() == 0) {
            return "/pion_noir.png";
        } else {
            return "/pion_blanc.png";
        }
    }

    public String toString() {
        return "Pion[x=" + getX() + ",y=" + getY() + "]";
    }
}
