package com.company;

import javax.swing.*;
import javax.swing.JFrame;
import java.awt.*;

public class Window extends JFrame {
    private JPanel canvas;

    public Window() {
        this.setTitle("Бегей | Варіант: 14");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(640,480);
        this.setMinimumSize(new Dimension(320, 240));
        canvas = new Canvas();
        this.add(canvas);
        this.setVisible(true);
    }
}