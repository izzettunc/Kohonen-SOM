package com.company;

import java.io.Console;

public class neuron {

    public som map;
    public double[] weights;

    public neuron(int attribute_count, double[] max, double[] min, data_types[] column_type)
    {
        weights = new double[attribute_count];
        for (int i = 0; i < attribute_count; i++) {
            switch (column_type[i])
            {
                case INTEGER:
                    weights[i] = som.rand.nextInt((int) (max[i]-min[i]+1))+(int)min[i];
                    break;
                case DOUBLE:
                    weights[i] = som.rand.nextDouble()*(max[i]-min[i])+min[i];
                    break;
                case BOOL:
                    weights[i] = som.rand.nextInt(2);
                    break;
            }

        }

    }

    public void update()
    {
        //update weight of node using the formula
        //use som for iteration,initial decay and time constant
    }
}
