package com.company;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class som {

    static public Random rand;
    private int x,y;
    private double alpha;
    private int neuronCount;
    private neuron[][] neurons;
    private point[] points;
    private neighbour_func neighbourhood_func;
    private int iteration;
    private int max_iter;
    private int epoch;
    public som(int nodes_x,int nodes_y,double learning_rate,neighbour_func neigh_func,int epoch)
    {
        x=nodes_x;
        y=nodes_y;
        alpha=learning_rate;
        neuronCount=x*y;
        neighbourhood_func = neigh_func;
        rand = new Random();
        iteration=0;
        this.epoch=epoch;
    }

    public void setPoints(point[] points) {
        this.points = points;
    }

    public void shufflePoints()
    {
        List<point> pointList = Arrays.asList(points);
        Collections.shuffle(pointList);
        pointList.toArray(points);
    }

    public void initializeNeurons(int feature_count, double[] max_values, double[] min_values, data_types[] col_types)
    {
        neurons = new neuron[x][y];
        for (int i = 0; i < x; i++) {
            for (int j = 0; j < y; j++) {
                neurons[i][j] = new neuron(feature_count,max_values,min_values,col_types);
            }
        }
    }

    //BMU => Best matching unit
    private int[] find_BMU(int point_index)
    {
        double min_distance=Double.MAX_VALUE;
        int[] closest_node_index= new int[]{-1,-1};
        double euc_distance=Double.MAX_VALUE;

        for (int i = 0; i < x ; i++) {
            for (int j = 0; j < y; j++) {

                euc_distance = formulas.euclidian_distance(points[point_index].getValues(),neurons[i][j].weights);

                if(euc_distance<min_distance)
                {
                    min_distance=euc_distance;

                    closest_node_index[0]=i;
                    closest_node_index[1]=j;
                }

            }
        }
        return  closest_node_index;
    }

    private void update_weights(int bmu_i,int bmu_j)
    {
        for (int i = 0; i < x; i++) {
            for (int j = 0; j < y; j++) {
                //Traverse all data calc distance and update weight accordin to it
            }
        }
    }

    public void train(int epoch)
    {
        for (int i = 0; i < epoch; i++) {
            for (int j = 0; j < points.length; j++) {

            }
        }
    }

}
