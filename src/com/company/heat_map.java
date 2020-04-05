package com.company;

import org.tc33.jheatchart.HeatChart;

import javax.swing.*;
import java.awt.*;

public class heat_map extends JPanel {

    private HeatChart my_chart;
    private int z_len;
    public heat_map(double[][] zValues,int min,int max)
    {
        z_len=zValues.length;
        my_chart = new HeatChart(zValues,min,max);
    }

    protected void paintComponent(Graphics g) {
        my_chart.setAxisValuesFont(new Font("Verdana", Font.BOLD, 24));
        double w=(this.getWidth()*1.0)/z_len;
        double h=(this.getHeight()*1.0)/z_len;
        my_chart.setCellSize(new Dimension((int)Math.ceil(w),(int)Math.ceil(h)));
        Image heatMap = my_chart.getChartImage().getScaledInstance(this.getWidth(),this.getHeight(), Image.SCALE_SMOOTH);
        g.drawImage(heatMap,0,0,this);
    }
}
