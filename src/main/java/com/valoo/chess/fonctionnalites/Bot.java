package com.valoo.chess.fonctionnalites;

import com.valoo.chess.ChessBoard;
import com.valoo.chess.pieces.Piece;

public class Bot {

    public boolean isActivated;

    public Bot(boolean isActivated) {
        this.isActivated = isActivated;
    }



    // Cette fonction prend en paramètre un chessboard et une couleur, puis il choisit une pièce aléatoire et la déplace aléatoirement
    public void play(ChessBoard board, int couleur) {
        while (true) {
            int x = (int) (Math.random() * 8);
            int y = (int) (Math.random() * 8);
            Piece piece = board.getPiece(x, y);
            if (piece != null && piece.getCouleur() == couleur) {
                int[][] moves = piece.validMoves(board);
                if (moves.length > 0) {
                    int moveIndex = (int) (Math.random() * moves.length);
                    int[] move = moves[moveIndex];
                    board.movePiece(x, y, move[0], move[1]);
                    break;
                }
            }
        }
    }

}
