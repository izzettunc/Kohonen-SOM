package com.company;

import java.io.Console;

public class neuron {

    double[] weights;
    public neuron(int attribute_count,double[] max,double[] min)
    {
        weights = new double[attribute_count];
        for (int i = 0; i < attribute_count; i++) {
            weights[i] = som.rand.nextDouble()*(max[i]-min[i])+min[i];
        }

    }
}
