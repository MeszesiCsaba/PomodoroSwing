/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.pomodoroswing.view;

import javax.swing.*;
import java.awt.*;

/**
 *
 * @author mcsab
 */
public class BeallitasDialog extends JDialog {

    private final JSpinner munka;
    private final JSpinner rovid;
    private final JSpinner hosszu;

    private int[] eredmeny;

    public BeallitasDialog(
            JFrame parent, int munkaPerc, int rovidPerc, int hosszuPerc) {

        super(parent, "Pomodoro beállítások", true);

        munka = new JSpinner(
                new SpinnerNumberModel(munkaPerc, 1, 180, 1));
        rovid = new JSpinner(
                new SpinnerNumberModel(rovidPerc, 1, 60, 1));
        hosszu = new JSpinner(
                new SpinnerNumberModel(hosszuPerc, 1, 120, 1));

        setLayout(new GridLayout(4, 2, 10, 10));

        add(new JLabel("Munka (perc):"));
        add(munka);

        add(new JLabel("Rövid szünet (perc):"));
        add(rovid);

        add(new JLabel("Hosszú szünet (perc):"));
        add(hosszu);

        JButton mentes = new JButton("Mentés");
        JButton megse = new JButton("Mégse");

        add(mentes);
        add(megse);

        mentes.addActionListener(e -> {
            eredmeny = new int[]{
                (int) munka.getValue(),
                (int) rovid.getValue(),
                (int) hosszu.getValue()
            };
            dispose();
        });

        megse.addActionListener(e -> dispose());

        pack();
        setResizable(false);
        setLocationRelativeTo(parent);
    }

    public int[] megjelenit() {
        setVisible(true);
        return eredmeny;
    }
}