/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.pomodoroswing.test;
import com.mycompany.pomodoroswing.model.Allapot;
import com.mycompany.pomodoroswing.model.PomodoroModel;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
/**
 *
 * @author mcsab
 */
class PomodoroModelTest {

    @Test
    void kezdetbenMunkaAllapotbanVan() {
        PomodoroModel model = new PomodoroModel();

        assertEquals(Allapot.MUNKA, model.getAllapot());
        assertEquals(1500, model.getHatralevoMasodperc());
        assertEquals(0, model.getBefejezettKorok());
    }

    @Test
    void tickCsokkentiAzIdot() {
        PomodoroModel model = new PomodoroModel();

        model.tick();

        assertEquals(1499, model.getHatralevoMasodperc());
    }

    @Test
    void szuneteltetesNemCsokkentiAzIdot() {
        PomodoroModel model = new PomodoroModel();

        model.szunet();
        model.tick();

        assertEquals(Allapot.SZUNETEL, model.getAllapot());
        assertEquals(1500, model.getHatralevoMasodperc());
    }

    @Test
    void folytatasVisszaallitjaAzElozoAllapotot() {
        PomodoroModel model = new PomodoroModel();

        model.szunet();
        model.start();

        assertEquals(Allapot.MUNKA, model.getAllapot());
    }

    @Test
    void resetVisszaallitjaAzIdotEsAKoroket() {
        PomodoroModel model = new PomodoroModel();

        model.tick();
        model.reset();

        assertEquals(Allapot.MUNKA, model.getAllapot());
        assertEquals(1500, model.getHatralevoMasodperc());
        assertEquals(0, model.getBefejezettKorok());
    }

    @Test
    void elsoMunkakorUtanRovidSzunetJon() {
        PomodoroModel model = new PomodoroModel();
        model.beallitasok(1, 1, 1);

        for (int i = 0; i < 60; i++) {
            model.tick();
        }

        assertEquals(Allapot.ROVID_SZUNET, model.getAllapot());
        assertEquals(1, model.getBefejezettKorok());
        assertEquals(60, model.getHatralevoMasodperc());
    }

    @Test
    void negyedikMunkakorUtanHosszuSzunetJon() {
        PomodoroModel model = new PomodoroModel();
        model.beallitasok(1, 1, 1);

        for (int kor = 0; kor < 4; kor++) {
            for (int i = 0; i < 60; i++) {
                model.tick();
            }

            if (kor < 3) {
                for (int i = 0; i < 60; i++) {
                    model.tick();
                }
            }
        }

        assertEquals(Allapot.HOSSZU_SZUNET, model.getAllapot());
        assertEquals(4, model.getBefejezettKorok());
        assertEquals(60, model.getHatralevoMasodperc());
    }

    @Test
    void ervenytelenBeallitasHibatDob() {
        PomodoroModel model = new PomodoroModel();

        assertThrows(IllegalArgumentException.class,
                () -> model.beallitasok(0, 5, 15));
    }
}
