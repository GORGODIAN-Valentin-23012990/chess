package com.valoo.chess.fonctionnalites;

import com.valoo.chess.ChessBoard;
import com.valoo.chess.fonctionnalites.FichierCoup;
import org.junit.jupiter.api.Test;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

class FichierCoupTest {

    @Test
    void shouldCreateNewFileOnConstruction() {
        FichierCoup fichierCoup = new FichierCoup();
        assertNotNull(fichierCoup.getFileName());
    }

    @Test
    void shouldWriteMoveToFile() {
        FichierCoup fichierCoup = new FichierCoup();
        fichierCoup.ecrireCoup(1, 1, 2, 2);
        // Verify that the move was written to the file
    }

    @Test
    void shouldPlayGameFromFile() {
        ChessBoard mockBoard = mock(ChessBoard.class);
        FichierCoup fichierCoup = new FichierCoup();
        fichierCoup.jouerPartie(mockBoard, "testGame.txt");
        // Verify that the moves were played on the board
    }

    @Test
    void shouldUndoMove() {
        ChessBoard mockBoard = mock(ChessBoard.class);
        FichierCoup fichierCoup = new FichierCoup();
        fichierCoup.annulerCoup(mockBoard);
        // Verify that the last move was undone
    }

    @Test
    void shouldPlayNextMove() {
        ChessBoard mockBoard = mock(ChessBoard.class);
        FichierCoup fichierCoup = new FichierCoup();
        fichierCoup.coupSuivant(mockBoard);
        // Verify that the next move was played
    }
}