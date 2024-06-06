package com.valoo.chess.fonctionnalites;

import com.valoo.chess.fonctionnalites.Timer;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class TimerTest {

    @Test
    void shouldInitializeWithCorrectTime() {
        Timer timer = new Timer(300);
        assertEquals(300, timer.getTimeBlanc());
    }

    @Test
    void shouldDecrementTime() {
        Timer timer = new Timer(300);
        timer.tpsDecr(60);
        assertEquals(240, timer.getTimeBlanc());
    }


    @Test
    void shouldResetToInitialTime() {
        Timer timer = new Timer(300);
        timer.tpsDecr(60);
        timer.reset(300);
        assertEquals(300, timer.getTimeBlanc());
    }
}