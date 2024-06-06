package com.valoo.chess.pieces;

import com.valoo.chess.ChessBoard;

public class Roi extends Piece {

    private int nbCoups;

    /**
     * @param color couleur du roi
     * @param type type du roi
     * @param couleur couleur du roi
     * @param x  position x
     * @param y  position y
     */
    public Roi(String color, String type, int couleur, int x, int y) {
        super(color, type, couleur, x, y);
        nbCoups = 0;
    }

    /**
     * @return nombre de coups du roi
     */
    public int getNbCoups() {
        return nbCoups;
    }

    /**
     * Incrémente le nombre de coups joués par le roi
     */
    public void jouerRoi() {
        nbCoups++;
    }

    /**
     * @param board plateau de jeu
     * @return tableau de coordonnées de toutes les positions possibles
     */
    // La fonction validMoves renvoie un tableau de coordonnées de toutes les positions possibles
    // Exemple de renvoi [ [1, 2], [3, 4], [5, 6] ]
    public int[][] validMoves (ChessBoard board) {
        int[][] moves = new int[10][2]; // Le roi peut se déplacer sur 10 cases maximum avec le grand et petit roque
        int i = 0;

        // Les déplacements possibles pour un roi avec le roque et petit roque
        int[][] possibleMoves = {
                {getX() + 1, getY()},     // Droite
                {getX() - 1, getY()},     // Gauche
                {getX(), getY() + 1},     // Haut
                {getX(), getY() - 1},     // Bas
                {getX() + 1, getY() + 1}, // Haut-Droite
                {getX() - 1, getY() + 1}, // Haut-Gauche
                {getX() + 1, getY() - 1}, // Bas-Droite
                {getX() - 1, getY() - 1},  // Bas-Gauche
                {getX() + 2, getY()},     // Petit Roque
                {getX() - 2, getY()}      // Grand Roque
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

            // Pour les blancs, on vérifie que le roi est à la case 4,0 et que la tour est à la case 0,0 pour le grand roque
            // On vérifie aussi que les cases entre le roi et la tour sont vides
            if (getCouleur() == 1 && x == 2 && y == 0 && board.getPiece(0, 0) != null && board.getPiece(0, 0) instanceof Tour && board.getPiece(0, 0).getCouleur() == 1 && board.getPiece(1, 0) == null && board.getPiece(2, 0) == null && board.getPiece(3, 0) == null) {
                moves[i][0] = x;
                moves[i][1] = y;
                i++;
            }

            // On fait maintenant la même chose pour le petit roque
            if (getCouleur() == 1 && x == 6 && y == 0 && board.getPiece(7, 0) != null && board.getPiece(7, 0) instanceof Tour && board.getPiece(7, 0).getCouleur() == 1 && board.getPiece(5, 0) == null && board.getPiece(6, 0) == null) {
                moves[i][0] = x;
                moves[i][1] = y;
                i++;
            }

            // Pour les noirs, on vérifie que le roi est à la case 4,7 et que la tour est à la case 0,7 pour le grand roque
            // On vérifie aussi que les cases entre le roi et la tour sont vides
            if (getCouleur() == 0 && x == 2 && y == 7 && board.getPiece(0, 7) != null && board.getPiece(0, 7) instanceof Tour && board.getPiece(0, 7).getCouleur() == 0 && board.getPiece(1, 7) == null && board.getPiece(2, 7) == null && board.getPiece(3, 7) == null) {
                moves[i][0] = x;
                moves[i][1] = y;
                i++;
            }

            // On fait maintenant la même chose pour le petit roque
            if (getCouleur() == 0 && x == 6 && y == 7 && board.getPiece(7, 7) != null && board.getPiece(7, 7) instanceof Tour && board.getPiece(7, 7).getCouleur() == 0 && board.getPiece(5, 7) == null && board.getPiece(6, 7) == null) {
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


    /**
     * @return image du roi
     */
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
