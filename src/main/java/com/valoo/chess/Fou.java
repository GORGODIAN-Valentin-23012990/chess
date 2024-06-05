package com.valoo.chess;

public class Fou extends Piece {

    /**
     * @param color couleur du fou
     * @param type type du fou
     * @param couleur couleur du fou
     * @param x position x
     * @param y position y
     */
    public Fou(String color, String type, int couleur, int x, int y) {
        super(color, type, couleur, x, y);
    }

    /**
     * @param board plateau de jeu
     * @return tableau de coordonnées de toutes les positions possibles
     */
    // La fonction validMoves renvoie un tableau de coordonnées de toutes les positions possibles
    // Exemple de renvoi [ [1, 2], [3, 4], [5, 6] ]
    public int[][] validMoves (ChessBoard board) {
        int[][] moves = new int[64][2]; // Le fou peut théoriquement avoir jusqu'à 64 mouvements possibles
        int i = 0;

        int[] directions = {1, -1}; // Pour se déplacer dans les directions diagonales

        // Vérifier les mouvements diagonaux
        for (int dirX : directions) {
            for (int dirY : directions) {
                int x = getX();
                int y = getY();
                while (true) {
                    x += dirX;
                    y += dirY;
                    if (x < 0 || x >= 8 || y < 0 || y >= 8) break; // Sortir de l'échiquier
                    if (board.getPiece(x, y) == null) {
                        moves[i][0] = x;
                        moves[i][1] = y;
                        i++;
                    } else {
                        if (!estMemeCouleur(board.getPiece(x, y))) {
                            moves[i][0] = x;
                            moves[i][1] = y;
                            i++;
                        }
                        break; // Bloqué par une pièce, qu'elle soit de la même couleur ou adverse
                    }
                }
            }
        }

        // Réduire la taille du tableau pour n'inclure que les mouvements réellement trouvés
        int[][] validMoves = new int[i][2];
        System.arraycopy(moves, 0, validMoves, 0, i);

        return validMoves;
    }


    /**
     * @return chemin d'acces de l'image du fou
     */
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
