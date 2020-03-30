package com.company;

import javax.swing.JFrame;
import javax.swing.JPanel;
import org.tc33.jheatchart.HeatChart;

import java.awt.*;
import java.awt.image.ImageObserver;
import java.text.AttributedCharacterIterator;

public class gui {

    private JFrame window;
    private int windowWidth;
    private int windowHeight;
    public gui(int width,int height)
    {
        windowHeight=height;
        windowWidth=width;
        window = new JFrame("Kohonen SOM");
        window.setSize(windowWidth,windowHeight);
        window.setLocationRelativeTo(null);
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    public void plotHeatMap(double[][] zValues)
    {
        show();
        HeatChart my_chart = new HeatChart(zValues);
        Graphics g = window.getGraphics();
        Image heatMap = my_chart.getChartImage().getScaledInstance(windowWidth,windowHeight, Image.SCALE_SMOOTH);
        g.drawImage(heatMap,0,0,window);
    }

    public void show()
    {
        window.setVisible(true);
    }

    public void hide()
    {
        window.setVisible(false);
    }
}
