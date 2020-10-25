package com.company;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Random;
import javax.swing.JPanel;

public class Canvas extends JPanel {
    double[] xArray;
    double[] yArray;
    int borderWidth = 10;
    Color graphColor;
    BasicStroke graphStroke;

    public Canvas() {
        int nPoints = 720;
        double step = 0.1;
        int zoom = 35;
        xArray = new double[nPoints + 1];
        yArray = new double[nPoints + 1];
        graphColor = new Color(52, 146, 235);
        graphStroke = new BasicStroke(
                3,
                BasicStroke.CAP_BUTT,
                BasicStroke.JOIN_BEVEL,
                0,
                new float[]{9},
                0
        );

        int a = 5;
        double m = 6. / 7;

        for (int i = 0; i < nPoints; ++i) {
            double t = step * i;
            double r = a * Math.pow(Math.cos(t * m), Math.pow(m, -1));
            xArray[i] = (r * Math.cos(t)) * zoom;
            yArray[i] = (r * Math.sin(t)) * zoom;
        }

        addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent me) {
                var rand = new Random();
                graphColor = new Color(
                        rand.nextInt(256),
                        rand.nextInt(256),
                        rand.nextInt(256)
                );
                if (rand.nextInt(2) == 0) {
                    graphStroke = new BasicStroke(
                            1 + rand.nextInt(5),
                            BasicStroke.CAP_BUTT,
                            BasicStroke.JOIN_BEVEL,
                            0,
                            new float[]{(float)(4 + rand.nextInt(11))},
                            0
                    );
                }
                else {
                    graphStroke = new BasicStroke(1 + rand.nextInt(5));
                }
                repaint();
            }
        });
    }

    int getCenterX() {
        return getWidth() / 2;
    }

    int getCenterY() {
        return getHeight() / 2;
    }

    double getScaleX() {
        return getWidth() / 640.;
    }

    double getScaleY() {
        return getHeight() / 480.;
    }

    void drawAxis(Graphics2D g) {
        g.setFont(new Font("Arial", Font.PLAIN, 20));

        g.setStroke(new BasicStroke(2));
        g.setColor(Color.WHITE);
        g.fillRect(borderWidth, borderWidth, getWidth() - borderWidth * 2, getHeight() - borderWidth * 2);
        g.setColor(Color.BLACK);
        g.drawRect(borderWidth, borderWidth, getWidth() - borderWidth * 2, getHeight() - borderWidth * 2);

        g.setStroke(new BasicStroke(1));
        g.setColor(Color.BLACK);
        g.drawLine(borderWidth, getCenterY(), getWidth() - borderWidth, getCenterY());
        g.drawString("x", getWidth() - 30, getCenterY() + 15);

        g.drawLine(getCenterX(), getHeight() - borderWidth, getCenterX(), borderWidth);
        g.drawString("y", getCenterX() - 15, 30);
    }

    void drawPlot(Graphics2D g) {
        double scale = getScaleX();
        if (getScaleY() < scale) {
            scale = getScaleY();
        }

        g.setStroke(graphStroke);
        g.setColor(graphColor);

        int[] xArrayInt = new int[xArray.length];
        int[] yArrayInt = new int[yArray.length];

        for (int i = 0; i < xArray.length; ++i) {
            xArrayInt[i] = (int)(xArray[i] * scale + getCenterX());
            yArrayInt[i] = (int)(yArray[i] * scale + getCenterY());

            if (i > 0 && xArrayInt[i-1] > 0 && yArrayInt[i-1] > 0 && xArrayInt[i] > 0 && yArrayInt[i] > 0) {
                g.drawLine(xArrayInt[i - 1], yArrayInt[i - 1], xArrayInt[i], yArrayInt[i]);
            }
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        var g2d = (Graphics2D)g;

        drawAxis(g2d);
        drawPlot(g2d);
    }
}