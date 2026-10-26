/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.pomodoroswing.view;

import com.mycompany.pomodoroswing.model.Allapot;
import com.mycompany.pomodoroswing.model.PomodoroFigyelo;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

/**
 *
 * @author mcsab
 */
public class PomodoroView extends JFrame implements PomodoroFigyelo {

    private final JPanel panel = new JPanel();
    private final JLabel idoLabel = new JLabel("25:00", SwingConstants.CENTER);
    private final JLabel allapotLabel =
            new JLabel("Munka", SwingConstants.CENTER);
    private final JLabel korLabel =
            new JLabel("Befejezett körök: 0", SwingConstants.CENTER);

    private final JButton startGomb = new JButton("Start");
    private final JButton szunetGomb = new JButton("Szünet");
    private final JButton resetGomb = new JButton("Reset");
    private final JButton beallitasGomb = new JButton("Beállítások");

    private Allapot aktualisAllapot = Allapot.MUNKA;

    public PomodoroView() {
        setTitle("Pomodoro");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(420, 300);
        setLocationRelativeTo(null);

        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        idoLabel.setFont(new Font("Arial", Font.BOLD, 52));
        allapotLabel.setFont(new Font("Arial", Font.BOLD, 22));

        allapotLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        idoLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        korLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        beallitasGomb.setAlignmentX(Component.CENTER_ALIGNMENT);

        panel.add(allapotLabel);
        panel.add(Box.createVerticalStrut(10));
        panel.add(idoLabel);
        panel.add(korLabel);
        panel.add(Box.createVerticalStrut(15));

        JPanel gombPanel = new JPanel(new FlowLayout());
        gombPanel.add(startGomb);
        gombPanel.add(szunetGomb);
        gombPanel.add(resetGomb);

        panel.add(gombPanel);
        panel.add(beallitasGomb);

        add(panel);
        frissitSzin();
    }

    public void setStartListener(ActionListener listener) {
        startGomb.addActionListener(listener);
    }

    public void setSzunetListener(ActionListener listener) {
        szunetGomb.addActionListener(listener);
    }

    public void setResetListener(ActionListener listener) {
        resetGomb.addActionListener(listener);
    }

    public void setBeallitasListener(ActionListener listener) {
        beallitasGomb.addActionListener(listener);
    }

    public int[] megjelenitBeallitasokat(
            int munka, int rovid, int hosszu) {

        BeallitasDialog dialog =
                new BeallitasDialog(this, munka, rovid, hosszu);

        return dialog.megjelenit();
    }

    public void frissitKorok(int korok) {
        korLabel.setText("Befejezett körök: " + korok);
    }

    private void frissitSzin() {
        switch (aktualisAllapot) {
            case MUNKA ->
                panel.setBackground(new Color(255, 220, 220));
            case ROVID_SZUNET ->
                panel.setBackground(new Color(215, 245, 220));
            case HOSSZU_SZUNET ->
                panel.setBackground(new Color(200, 230, 255));
            case SZUNETEL ->
                panel.setBackground(Color.LIGHT_GRAY);
        }
    }

    @Override
    public void tikk(int hatralevoMasodperc) {
        int perc = hatralevoMasodperc / 60;
        int masodperc = hatralevoMasodperc % 60;

        idoLabel.setText(String.format("%02d:%02d", perc, masodperc));
    }

    @Override
    public void allapotValtozott(Allapot uj) {
        aktualisAllapot = uj;

        switch (uj) {
            case MUNKA -> allapotLabel.setText("Munka");
            case ROVID_SZUNET -> allapotLabel.setText("Rövid szünet");
            case HOSSZU_SZUNET -> allapotLabel.setText("Hosszú szünet");
            case SZUNETEL -> allapotLabel.setText("Szüneteltetve");
        }

        frissitSzin();
    }
}
