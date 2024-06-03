package com.valoo.chess;

public class Roi extends Piece {

    public Roi(String color, String type, int couleur, int x, int y) {
        super(color, type, couleur, x, y);
    }

    // La fonction validMoves renvoie un tableau de coordonnées de toutes les positions possibles
    // Exemple de renvoi [ [1, 2], [3, 4], [5, 6] ]
    public int[][] validMoves (ChessBoard board) {
        int[][] moves = new int[8][2]; // Le roi peut se déplacer sur 8 cases maximum
        int i = 0;

        // Les déplacements possibles pour un roi
        int[][] possibleMoves = {
                {getX() + 1, getY()},     // Droite
                {getX() - 1, getY()},     // Gauche
                {getX(), getY() + 1},     // Haut
                {getX(), getY() - 1},     // Bas
                {getX() + 1, getY() + 1}, // Haut-Droite
                {getX() - 1, getY() + 1}, // Haut-Gauche
                {getX() + 1, getY() - 1}, // Bas-Droite
                {getX() - 1, getY() - 1}  // Bas-Gauche
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
