package com.valoo.chess.fonctionnalites;

import com.valoo.chess.fonctionnalites.FichierCoup;
import com.valoo.chess.ChessBoard;
import org.junit.jupiter.api.Test;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

class FichierCoupTest {

    @Test
    void shouldCreateNewProblemFileOnConstruction() {
        FichierCoup fichierCoup = new FichierCoup();
        assertTrue(fichierCoup.getFileName().startsWith("src/main/resources/problemes/Problème"));
    }


    @Test
    void shouldNotUndoMoveWhenNoMovesMade() {
        ChessBoard mockBoard = mock(ChessBoard.class);
        FichierCoup fichierCoup = new FichierCoup();
        int initialIndex = fichierCoup.getIndexHistorique();
        fichierCoup.annulerCoup(mockBoard, fichierCoup.getFileName());
        assertEquals(initialIndex, fichierCoup.getIndexHistorique());
    }

}