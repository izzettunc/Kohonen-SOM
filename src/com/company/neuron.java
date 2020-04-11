package com.company;

import java.io.Console;
import java.util.ArrayList;
import java.util.Comparator;

public class neuron{

    public som map;
    public double[] weights;
    private Object[] min_val;

    public neuron(int attribute_count, ArrayList<Double> max, ArrayList<Double> min, data_types[] column_type)
    {
        weights = new double[attribute_count];
        min_val=min.toArray();
        //Random initializing
        for (int i = 0; i < attribute_count; i++) {
            switch (column_type[i])
            {
                case INTEGER:
                    weights[i] = som.rand.nextInt((int) (max.get(i)-min.get(i)+1))+(min.get(i)).intValue();
                    break;
                case DOUBLE:
                    weights[i] = som.rand.nextDouble()*(max.get(i)-min.get(i))+min.get(i);
                    break;
                case BOOL:
                    weights[i] = som.rand.nextInt(2);
                    break;
            }
        }
        boolean flag=false;
        int start_index=0;
        //Unique initialization of categorical value so the neuron can't be categort 1 and 2 at the same time
        for (int i = 0; i < attribute_count; i++) {
            if (column_type[i]==data_types.CATEGORICAL && !flag)
            {
                start_index=i;
                flag=true;
            }
            else if(column_type[i]!=data_types.CATEGORICAL && flag)
            {
                flag=false;
                int rand_index=som.rand.nextInt(i-start_index+1)+start_index;
                if(rand_index!=i)
                {
                    weights[rand_index] = 1;
                }
            }
        }

    }

    public void update(double distance,point input)
    {
        int iter=map.getIteration();
        int max_iter = map.getMax_iter();
        double lr_start=map.getLearning_rate_start();
        double lr_end=map.getLearning_rate_end();
        double decayed_lr = formulas.exp_decay(iter,max_iter,lr_start,lr_start/lr_end);
        double neigh_val;
        if(map.getNeighbourhood_func() == neighbour_func.GAUSSIAN)
            neigh_val=formulas.gaussian_neighbour(iter,map.init_rad,max_iter,distance,map.init_rad);
        else
            neigh_val=formulas.mexican_hat_neighbour(iter,map.init_rad,max_iter,distance,map.init_rad);
        //Update weights according to formula
        for (int i = 0; i < weights.length ; i++) {
            weights[i]+=((input.getValues()[i]-weights[i])*decayed_lr*neigh_val);
        }
    }

}
