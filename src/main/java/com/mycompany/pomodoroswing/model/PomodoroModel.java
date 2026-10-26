/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.pomodoroswing.model;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author mcsab
 */
public class PomodoroModel {

    private final List<PomodoroFigyelo> figyelok = new ArrayList<>();

    private int munkaIdo = 25 * 60;
    private int rovidSzünetIdo = 5 * 60;
    private int hosszuSzünetIdo = 15 * 60;

    private int hatralevoMasodperc = munkaIdo;
    private int befejezettKorok = 0;

    private Allapot allapot = Allapot.MUNKA;
    private Allapot elozoAllapot = Allapot.MUNKA;

    public void feliratkozik(PomodoroFigyelo figyelo) {
        if (figyelo != null && !figyelok.contains(figyelo)) {
            figyelok.add(figyelo);
            figyelo.tikk(hatralevoMasodperc);
            figyelo.allapotValtozott(allapot);
        }
    }

    public void leiratkozik(PomodoroFigyelo figyelo) {
        figyelok.remove(figyelo);
    }

    public void start() {
        if (allapot == Allapot.SZUNETEL) {
            allapot = elozoAllapot;
            ertesitAllapotrol();
        }
    }

    public void szunet() {
        if (allapot != Allapot.SZUNETEL) {
            elozoAllapot = allapot;
            allapot = Allapot.SZUNETEL;
            ertesitAllapotrol();
        }
    }

    public void reset() {
        befejezettKorok = 0;
        elozoAllapot = Allapot.MUNKA;
        allapot = Allapot.MUNKA;
        hatralevoMasodperc = munkaIdo;

        ertesitAllapotrol();
        ertesitIdorol();
    }

    public void tick() {
        if (allapot == Allapot.SZUNETEL) {
            return;
        }

        if (hatralevoMasodperc > 0) {
            hatralevoMasodperc--;
        }

        if (hatralevoMasodperc == 0) {
            kovetkezoAllapot();
        } else {
            ertesitIdorol();
        }
    }

    private void kovetkezoAllapot() {
        if (allapot == Allapot.MUNKA) {
            befejezettKorok++;

            if (befejezettKorok % 4 == 0) {
                allapot = Allapot.HOSSZU_SZUNET;
                hatralevoMasodperc = hosszuSzünetIdo;
            } else {
                allapot = Allapot.ROVID_SZUNET;
                hatralevoMasodperc = rovidSzünetIdo;
            }
        } else {
            allapot = Allapot.MUNKA;
            hatralevoMasodperc = munkaIdo;
        }

        elozoAllapot = allapot;
        ertesitAllapotrol();
        ertesitIdorol();
    }

    public void beallitasok(int munkaPerc, int rovidPerc, int hosszuPerc) {
        if (munkaPerc < 1 || rovidPerc < 1 || hosszuPerc < 1) {
            throw new IllegalArgumentException(
                    "Minden időtartamnak legalább 1 percnek kell lennie."
            );
        }

        munkaIdo = munkaPerc * 60;
        rovidSzünetIdo = rovidPerc * 60;
        hosszuSzünetIdo = hosszuPerc * 60;

        reset();
    }

    private void ertesitIdorol() {
        for (PomodoroFigyelo figyelo : figyelok) {
            figyelo.tikk(hatralevoMasodperc);
        }
    }

    private void ertesitAllapotrol() {
        for (PomodoroFigyelo figyelo : figyelok) {
            figyelo.allapotValtozott(allapot);
        }
    }

    public int getHatralevoMasodperc() {
        return hatralevoMasodperc;
    }

    public int getBefejezettKorok() {
        return befejezettKorok;
    }

    public Allapot getAllapot() {
        return allapot;
    }

    public int getMunkaIdo() {
        return munkaIdo;
    }

    public int getRovidSzünetIdo() {
        return rovidSzünetIdo;
    }

    public int getHosszuSzünetIdo() {
        return hosszuSzünetIdo;
    }
}
