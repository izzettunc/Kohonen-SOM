package com.company;

import javax.swing.JFrame;
import javax.swing.JPanel;
import org.tc33.jheatchart.HeatChart;

import java.awt.*;
import java.awt.image.ImageObserver;
import java.text.AttributedCharacterIterator;

public class gui {

    private JFrame window_Umatrix;
    private JFrame window_heat_map;

    public void plotUMatrix(double[][] zValues,color_gradients colormap)
    {
        //Create window
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        window_Umatrix = new JFrame("Kohonen SOM");
        window_Umatrix.setPreferredSize(new Dimension(614,637));
        window_Umatrix.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window_Umatrix.setVisible(true);
        window_Umatrix.setResizable(false);
        window_Umatrix.pack();
        //Create content
        hexagonal_grid grid = new hexagonal_grid(zValues,colormap);
        grid.setBackground(new Color(0, 55, 41));
        //Show it on window
        window_Umatrix.add(grid);
    }

    public void plotHeatMap(double[][] zValues,int min,int max)
    {
        //Create window
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        window_heat_map = new JFrame("Kohonen SOM");
        window_heat_map.setPreferredSize(new Dimension(614,637));
        window_heat_map.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window_heat_map.setVisible(true);
        window_heat_map.setResizable(false);
        window_heat_map.pack();
        //Create content
        heat_map map = new heat_map(zValues,min,max);
        map.setBackground(new Color(0, 55, 41));
        //Show it on window
        window_heat_map.add(map);
    }
}
