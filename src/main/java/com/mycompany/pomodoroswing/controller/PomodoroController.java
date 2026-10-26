/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.pomodoroswing.controller;

import com.mycompany.pomodoroswing.model.PomodoroModel;
import com.mycompany.pomodoroswing.view.PomodoroView;

import javax.swing.Timer;
/**
 *
 * @author mcsab
 */
public class PomodoroController {

    private final PomodoroModel model;
    private final PomodoroView view;
    private final Timer timer;

    public PomodoroController(PomodoroModel model, PomodoroView view) {
        this.model = model;
        this.view = view;

        model.feliratkozik(view);

        timer = new Timer(1000, e -> {
            model.tick();
            view.frissitKorok(model.getBefejezettKorok());
        });

        view.setStartListener(e -> {
            model.start();
            timer.start();
        });

        view.setSzunetListener(e -> {
            model.szunet();
            timer.stop();
        });

        view.setResetListener(e -> {
            timer.stop();
            model.reset();
            view.frissitKorok(model.getBefejezettKorok());
        });

        view.setBeallitasListener(e -> beallitasokMegnyitasa());
    }

    private void beallitasokMegnyitasa() {
        boolean futott = timer.isRunning();
        timer.stop();

        int[] eredmeny = view.megjelenitBeallitasokat(
                model.getMunkaIdo() / 60,
                model.getRovidSzünetIdo() / 60,
                model.getHosszuSzünetIdo() / 60
        );

        if (eredmeny != null) {
            model.beallitasok(eredmeny[0], eredmeny[1], eredmeny[2]);
            view.frissitKorok(model.getBefejezettKorok());
        }

        if (futott) {
            timer.start();
        }
    }
}
