package com.company;

import java.util.Random;

public class som {

    static public Random rand;
    private int x,y;
    private int neuronCount;
    private neuron[][] neurons;
    private point[] points;

    public som(int nodes_x,int nodes_y)
    {
        x=nodes_x;
        y=nodes_y;
        neuronCount=x*y;
        rand = new Random();
    }


    public void initializeNeurons()
    {
        neurons = new neuron[x][y];
        for (int i = 0; i < x; i++) {
            for (int j = 0; j < y; j++) {
                //neurons[i][j] = new neuron(2,d,d);
            }
        }
    }

}
