package com.valoo.chess.controller;

import com.valoo.chess.controller.MainController;
import com.valoo.chess.fonctionnalites.Joueur;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import org.junit.jupiter.api.Test;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

class MainControllerTest {
    @Test
    void shouldGetCorrectJoueur() {
        MainController controller = new MainController();
        controller.nomJ1 = "Alice";
        controller.prenomJ1 = "Smith";
        controller.nomJ2 = "Bob";
        controller.prenomJ2 = "Johnson";

        Joueur joueur1 = controller.getJoueur1();
        Joueur joueur2 = controller.getJoueur2();

        assertEquals("Alice", joueur1.getNom());
        assertEquals("Smith", joueur1.getPrenom());
        assertEquals("Bob", joueur2.getNom());
        assertEquals("Johnson", joueur2.getPrenom());
    }
}