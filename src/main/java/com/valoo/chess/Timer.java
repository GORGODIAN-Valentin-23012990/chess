package com.valoo.chess;

public class Timer {
    int timeBlanc;
    int timeNoir;
    int timeMax;

    public Timer(int timeMax){
        this.timeMax = timeMax;
        this.timeBlanc = timeMax;
        this.timeNoir = timeMax;
    }

    public void setTimeMax(int i){
        this.timeMax = i;
    }

    public void tpsDecr(int couleur) {
        if (couleur == 1) {
            while (timeBlanc > 0) {
                timeBlanc--;
                System.out.println("Temps restant : " + timeBlanc + " secondes");
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        } else if (couleur == 2) {
            while (timeNoir > 0) {
                timeNoir--;
                System.out.println("Temps restant : " + timeNoir + " secondes");
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}