package com.valoo.chess.fonctionnalites;

import com.valoo.chess.fonctionnalites.Joueur;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class JoueurTest {

    @Test
    void shouldInitializeWithCorrectNameAndSurname() {
        Joueur joueur = new Joueur("Alice", "Smith");
        assertEquals("Alice", joueur.getNom());
        assertEquals("Smith", joueur.getPrenom());
    }

    @Test
    void shouldIncrementGamesWonAndPlayed() {
        Joueur joueur = new Joueur("Alice", "Smith");
        joueur.gagne();
        assertEquals("[Alice,Smith , 1,1 ]", joueur.toString());
    }

    @Test
    void shouldNotIncrementGamesWonWhenNotCalled() {
        Joueur joueur = new Joueur("Alice", "Smith");
        assertEquals("[Alice,Smith , 0,0 ]", joueur.toString());
    }
}