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
    public gui()
    {
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        windowHeight=(int)screenSize.getHeight()-100;
        windowWidth=(int)screenSize.getHeight();
        window = new JFrame("Kohonen SOM");
        window.setSize(windowWidth,windowHeight);
        window.setLocationRelativeTo(null);
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    public void plotHeatMap(double[][] zValues,int min,int max)
    {
        show();
        HeatChart my_chart = new HeatChart(zValues,min,max);
        my_chart.setAxisValuesFont(new Font("Verdana", Font.BOLD, 24));
        my_chart.setCellSize(new Dimension(windowWidth/zValues.length,windowHeight/zValues.length));
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
