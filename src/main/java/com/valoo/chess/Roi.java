package com.valoo.chess;

public class Roi extends Piece {

    private int nbCoups;

    public Roi(String color, String type, int couleur, int x, int y) {
        super(color, type, couleur, x, y);
        nbCoups = 0;
    }

    public int getNbCoups() {
        return nbCoups;
    }

    public void jouerRoi() {
        nbCoups++;
    }

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

            // On vérifie le nombre de coups du roi et de la tour pour le roque
            if (move[0] == getX() + 2 && move[1] == getY() && getNbCoups() == 0) {
                Piece tour = board.getPiece(7, getY());
                if (tour != null && tour instanceof Tour && ((Tour) tour).getNbCoups() == 0) {
                    if (board.getPiece(getX() + 1, getY()) == null && board.getPiece(getX() + 2, getY()) == null) {
                        moves[i][0] = x;
                        moves[i][1] = y;
                        i++;
                    }
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
