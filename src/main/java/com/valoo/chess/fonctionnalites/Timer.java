package com.valoo.chess.fonctionnalites;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;

public class Timer {
    private IntegerProperty timeBlanc;

    /**
     * Cette fonction permet de créer un timer de temps timeBlanc
     * @param timeBlanc : int
     */
    public Timer(int timeBlanc) {
        this.timeBlanc = new SimpleIntegerProperty(timeBlanc);
    }

    /**
     * Cette fonction permet de récupérer le temps restant
     * @return le temps restant
     */
    public int getTimeBlanc() {
        return timeBlanc.get();
    }


    public IntegerProperty timeBlancProperty() {
        return timeBlanc;
    }

    /**
     * Cette fonction permet de décrémenter le temps restant
     * @param decrement : int
     */
    public void tpsDecr(int decrement) {
        timeBlanc.set(timeBlanc.get() - decrement);
    }

    /**
     * Cette fonction permet de réinitialiser le temps restant
     * @param seconds : int
     */
    public void reset(int seconds) {
        timeBlanc.set(seconds);
    }

}