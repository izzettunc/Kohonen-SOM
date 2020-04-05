package com.company;

import javax.swing.*;
import java.awt.*;

public class hexagonal_grid extends JPanel {

    private int x,y;
    private color_gradients cmap;
    private hexagon[][] grid;
    private double[][] zValues;
    private double max,min;

    public hexagonal_grid(double min,double max,double[][] zVals,color_gradients c)
    {
        this.x = zVals.length;
        this.y = zVals[0].length;
        cmap=c;
        zValues=zVals;
        this.max=max;
        this.min=min;
    }

    public hexagonal_grid(double[][] zVals,color_gradients c)
    {
        this.x = zVals.length;
        this.y = zVals[0].length;
        cmap=c;
        zValues=zVals;
        max=Double.MIN_VALUE;
        min=0;
        for (int i = 0; i <x ; i++) {
            for (int j = 0; j < y; j++) {
                if(zVals[i][j]>max) max=zVals[i][j];
            }
        }
    }

    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D)g;

        int windowWidth=this.getWidth();
        int windowHeight=this.getHeight();
        double radius=0;
        int g_i,g_j;
        g_i=x;
        g_j=y;
        int startPoint_X,startPoint_Y;
        int m=0;

        if(g_j>(g_i+2))
        {
            m=g_j;
            m++;
            radius = (windowWidth*2.0)/(3*m+1);
        }
        else
        {
            m=g_i;
            radius = windowHeight/((m+2)*Math.sqrt(3));
        }

        double h=(radius*Math.sqrt(3));
        double halfTriangle=(h/2/Math.sqrt(3));

        startPoint_X = (int)Math.ceil(((windowWidth-((3*g_j+1)*(radius/2)))/2)+radius);
        startPoint_Y = (int)Math.ceil((windowHeight-((g_i)*h))/2);

        Point center=new Point(startPoint_X,startPoint_Y);

        for (int i = 0; i < x; i++) {
            for (int j = 0; j < y; j++) {
                double ratio=(zValues[i][j]-min)/(max-min);
                //1 means longer distance
                //0 means closer so I need to change ratio
                ratio=1-ratio;
                switch (cmap)
                {
                    case GRAY_SCALE:
                        int rgb=(int)Math.ceil((1-ratio)*255);
                        g.setColor(new Color(rgb,rgb,rgb));
                        break;
                    case HOT_SCALE:
                        int green=(int)Math.ceil((1-ratio)*255);
                        g.setColor(new Color(255,green,0));
                        break;
                    case COLD_SCALE:
                        int gr=(int)Math.ceil((1-ratio)*255);
                        g.setColor(new Color(gr,gr,255));
                        break;
                    case FIVE_COLOR:
                        if(ratio<0.25)
                        {
                            double innerRatio= ratio/0.25;
                            int grn=(int)Math.ceil((innerRatio)*255);
                            g.setColor(new Color(0,grn,255));
                        }
                        else if(ratio>=0.25 && ratio<0.5)
                        {
                            double innerRatio= (ratio-0.25)/0.25;
                            int bl=(int)Math.ceil((1-innerRatio)*255);
                            g.setColor(new Color(0,255,bl));
                        }
                        else if(ratio>=0.5 && ratio<0.75)
                        {
                            double innerRatio= (ratio-0.5)/0.25;
                            int red=(int)Math.ceil((innerRatio)*255);
                            g.setColor(new Color(red,255,0));
                        }
                        else
                        {
                            double innerRatio= (ratio-0.75)/0.25;
                            int grd=(int)Math.ceil((1-innerRatio)*255);
                            g.setColor(new Color(255,grd,0));
                        }
                        break;
                }
                hexagon new_hexagon = new hexagon(center,(int)Math.ceil(radius));
                g2d.fillPolygon(new_hexagon.getHexagon());
                int flag=j%4;
                if(flag<2)
                {
                    center.x+=Math.ceil(radius+halfTriangle);
                    center.y=(int)Math.floor(center.y+(h/2));
                }
                else
                {
                    center.y=(int)Math.ceil(center.y-(h/2));
                    center.x+=Math.ceil(radius+halfTriangle);
                }
            }
            center.x=startPoint_X;
            center.y=(int)Math.floor(startPoint_Y+(h*(i+1)));
        }
    }

}
