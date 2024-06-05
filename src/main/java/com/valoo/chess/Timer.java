package com.valoo.chess;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;

public class Timer {
    private IntegerProperty timeBlanc;

    public Timer(int timeBlanc) {
        this.timeBlanc = new SimpleIntegerProperty(timeBlanc);
    }

    public int getTimeBlanc() {
        return timeBlanc.get();
    }

    public IntegerProperty timeBlancProperty() {
        return timeBlanc;
    }

    public void tpsDecr(int decrement) {
        timeBlanc.set(timeBlanc.get() - decrement);
    }
}