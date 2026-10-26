/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.mycompany.pomodoroswing;

import com.mycompany.pomodoroswing.controller.PomodoroController;
import com.mycompany.pomodoroswing.model.PomodoroModel;
import com.mycompany.pomodoroswing.view.PomodoroView;

import javax.swing.SwingUtilities;

/**
 *
 * @author mcsab
 */
public class Main {

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            PomodoroModel model = new PomodoroModel();
            PomodoroView view = new PomodoroView();

            new PomodoroController(model, view);

            view.setVisible(true);
        });
    }
}
