package com.valoo.chess;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;

public class Timer {
    private IntegerProperty timeBlanc;
    private IntegerProperty timeNoir;
    private int timeMax;

    public Timer(int timeMax){
        this.timeMax = timeMax;
        this.timeBlanc = new SimpleIntegerProperty(timeMax);
        this.timeNoir = new SimpleIntegerProperty(timeMax);
    }

    public IntegerProperty timeBlancProperty() {
        return timeBlanc;
    }

    public IntegerProperty timeNoirProperty() {
        return timeNoir;
    }

    public void setTimeMax(int i){
        this.timeMax = i;
    }

    public void tpsDecr(int couleur) {
        if (couleur == 1) {
            while (timeBlanc.get() > 0) {
                timeBlanc.set(timeBlanc.get() - 1);
                System.out.println("Temps restant : " + timeBlanc.get() + " secondes");
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        } else if (couleur == 2) {
            while (timeNoir.get() > 0) {
                timeNoir.set(timeNoir.get() - 1);
                System.out.println("Temps restant : " + timeNoir.get() + " secondes");
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}