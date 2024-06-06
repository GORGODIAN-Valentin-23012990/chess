package com.valoo.chess.pieces;

import com.valoo.chess.ChessBoard;
import com.valoo.chess.pieces.Piece;

public class Reine extends Piece {

    /**
     * @param color  couleur de la reine
     * @param type  type de la reine
     * @param couleur couleur de la reine
     * @param x position x
     * @param y position y
     */
    public Reine(String color, String type, int couleur, int x, int y) {
        super(color, type, couleur, x, y);
    }

    /**
     * @param board plateau de jeu
     * @return tableau de coordonnées de toutes les positions possibles
     */
    // La fonction validMoves renvoie un tableau de coordonnées de toutes les positions possibles
    // Exemple de renvoi [ [1, 2], [3, 4], [5, 6] ]
    public int[][] validMoves (ChessBoard board) {
        int[][] moves = new int[27][2]; // La reine peut théoriquement avoir jusqu'à 27 mouvements possibles
        int i = 0;

        // Directions de déplacement pour une reine
        int[][] directions = {
                {1, 0},   // Bas
                {-1, 0},  // Haut
                {0, 1},   // Droite
                {0, -1},  // Gauche
                {1, 1},   // Bas-Droite
                {-1, 1},  // Haut-Droite
                {1, -1},  // Bas-Gauche
                {-1, -1}  // Haut-Gauche
        };

        // Vérifier chaque direction de déplacement
        for (int[] dir : directions) {
            int x = getX();
            int y = getY();

            // Continuer à se déplacer dans la direction jusqu'à ce que l'on sorte de l'échiquier ou que l'on rencontre une pièce
            while (true) {
                x += dir[0];
                y += dir[1];

                // Vérifier si le mouvement est en dehors de l'échiquier
                if (x < 0 || x >= 8 || y < 0 || y >= 8) {
                    break;
                }

                // Vérifier si la case est vide ou contient une pièce adverse
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
                    break; // Bloqué par une pièce
                }
            }
        }

        // Réduire la taille du tableau pour n'inclure que les mouvements réellement trouvés
        int[][] validMoves = new int[i][2];
        System.arraycopy(moves, 0, validMoves, 0, i);

        return validMoves;
    }


    /**
     * @return chemin d'acces de l'image de la reine
     */
    public String getImage() {
        if (getCouleur() == 0) {
            return "/reine_noir.png";
        }
        else {
            return "/reine_blanc.png";
        }
    }

    public String toString() {
        return "Reine[x=" + getX() + ",y=" + getY() + "]";
    }

}
