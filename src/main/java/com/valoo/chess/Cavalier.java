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

        // Les déplacements possibles pour un cavalier
        int[][] possibleMoves = {
                {getX() + 2, getY() + 1},
                {getX() + 2, getY() - 1},
                {getX() - 2, getY() + 1},
                {getX() - 2, getY() - 1},
                {getX() + 1, getY() + 2},
                {getX() + 1, getY() - 2},
                {getX() - 1, getY() + 2},
                {getX() - 1, getY() - 2}
        };

        // Vérifier chaque mouvement possible
        for (int[] move : possibleMoves) {
            int x = move[0];
            int y = move[1];

            // Vérifier si le mouvement est valide et que la case est vide ou contient une pièce adverse
            if (x >= 0 && x < 8 && y >= 0 && y < 8 && (board.getPiece(x, y) == null || !estMemeCouleur(board.getPiece(x, y)))) {
                moves[i][0] = x;
                moves[i][1] = y;
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
