package com.company;

import java.util.*;

public class som {

    static public Random rand;
    private int x,y;
    private double learning_rate_start;
    private double learning_rate_end;
    private int neuronCount;
    private neuron[][] neurons;
    private point[] points;
    private neighbour_func neighbourhood_func;
    private int iteration;
    private int max_iter;
    private int epoch;
    public double init_rad;
    public int max_hit=Integer.MIN_VALUE;

    public som(int nodes_x,int nodes_y,double learning_rate_start,double learning_rate_end,neighbour_func neigh_func,double init_rad,int iteration,int epoch)
    {
        x=nodes_x;
        y=nodes_y;
        this.init_rad=(x+y)/2;
        if(init_rad>0) this.init_rad=init_rad;
        this.learning_rate_start =learning_rate_start;
        this.learning_rate_end =learning_rate_end;
        neuronCount=x*y;
        neighbourhood_func = neigh_func;
        rand = new Random();
        this.iteration=0;
        max_iter=iteration;
        this.epoch=epoch;
    }


    public void shufflePoints()
    {
        List<point> pointList = Arrays.asList(points);
        Collections.shuffle(pointList);
        pointList.toArray(points);
    }

    public void initializeNeurons(int feature_count, ArrayList<Double> max_values, ArrayList<Double> min_values, data_types[] col_types)
    {
        neurons = new neuron[x][y];
        for (int i = 0; i < x; i++) {
            for (int j = 0; j < y; j++) {
                neurons[i][j] = new neuron(feature_count,max_values,min_values,col_types);
                neurons[i][j].map=this;
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

    private void update_weights(int bmu_i,int bmu_j,point input)
    {
        for (int i = 0; i < x; i++) {
            for (int j = 0; j < y; j++) {
                neurons[i][j].update(formulas.euclidian_distance(neurons[i][j].weights,neurons[bmu_i][bmu_j].weights),input);
            }
        }
    }

    public void train()
    {
        //max_iter=points.length;
        for (int i = 0; i < epoch; i++) {
            for (iteration = 0; iteration < max_iter; iteration++) {
                int selected_point=som.rand.nextInt(points.length);
                //int selected_point=iteration;
                int[] bmu=find_BMU(selected_point);
                update_weights(bmu[0],bmu[1],points[selected_point]);
            }
        }
    }

    public void print_results()
    {
        for (int i = 0; i < x; i++) {
            for (int j = 0; j < y; j++) {
                System.out.print((int)neurons[i][j].weights[0]+","+(int)neurons[i][j].weights[1]+",");
            }
        }
    }

    public double[][] get_results(int col_index)
    {
        double[][] result = new double[x][y];
        for (int i = 0; i < points.length; i++) {
            int[] bmu=find_BMU(i);
            result[bmu[0]][bmu[1]]++;
            if(result[bmu[0]][bmu[1]]>max_hit)
                max_hit=(int)result[bmu[0]][bmu[1]];
        }
        /*neuron[] neuronArray = new neuron[x*y];
        for (int i = 0; i < x; i++) {
            for (int j = 0; j < y; j++) {
                neuronArray[i*x+j]=neurons[i][j];
            }
        }
        Arrays.sort(neuronArray,neuron.neuronComparator);
        for (int i = 0; i < x; i++) {
            for (int j = 0; j < y; j++) {
                result[i][j]=neuronArray[i*x+j].weights[col_index];
            }
        }*/
        return  result;
    }

    public void setPoints(point[] points) {
        this.points = points;
    }

    public int getIteration() {
        return iteration;
    }

    public int getMax_iter() {
        return max_iter;
    }

    public double getLearning_rate_start() {
        return learning_rate_start;
    }

    public double getLearning_rate_end() {
        return learning_rate_end;
    }

    public neighbour_func getNeighbourhood_func() {
        return neighbourhood_func;
    }


}
