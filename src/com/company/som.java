package com.company;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
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
    public double init_rad;
    public int max_hit=Integer.MIN_VALUE;

    public som(int nodes_i,int nodes_j,double learning_rate_start,double learning_rate_end,neighbour_func neigh_func,double init_rad,int iteration)
    {
        x=nodes_i;
        y=nodes_j;
        this.init_rad=(x+y)/2;
        if(init_rad>0) this.init_rad=init_rad;
        this.learning_rate_start =learning_rate_start;
        this.learning_rate_end =learning_rate_end;
        neuronCount=x*y;
        neighbourhood_func = neigh_func;
        rand = new Random();
        this.iteration=0;
        max_iter=iteration;
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
        double euc_distance;
        //Look for each neuron and find closes to you
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
        //Closest neuron is best matching unit
        return  closest_node_index;
    }

    private void update_weights(int bmu_i,int bmu_j,point input)
    {
        //Update weight of every neuron according to formula
        for (int i = 0; i < x; i++) {
            for (int j = 0; j < y; j++) {
                neurons[i][j].update(formulas.euclidian_distance(neurons[i][j].weights,neurons[bmu_i][bmu_j].weights),input);
            }
        }
    }

    public void train()
    {
        //Train the map by selecting a random point for each iteration
        for (iteration = 0; iteration < max_iter; iteration++) {
            int selected_point=som.rand.nextInt(points.length);
            int[] bmu=find_BMU(selected_point);
            update_weights(bmu[0],bmu[1],points[selected_point]);
        }
    }

    //Creates a matrix for hexagon representation of U matrix
    //https://stackoverflow.com/questions/13631673/how-do-i-make-a-u-matrix
    public double[][] get_u_matrix()
    {
        int n=x+(x-1),m=y+(y-1);
        double[][] u_matrix = new double[n][m];
        int r_i=0,r_j=0;
        boolean flag=true;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                if(j%2==1) r_j++;
                if(i%2==0 && j%2==0)
                {}//These must be calculated later because these are basically average of other cells
                else if(i%2==0 && j%2!=0)
                {
                    int[] first_neuron=formulas.point_to_index_converter(r_i*y+r_j,y);
                    int[] second_neuron =formulas.point_to_index_converter(r_i*y+r_j-1,y);
                    u_matrix[i][j]=formulas.euclidian_distance(neurons[first_neuron[0]][first_neuron[1]].weights,neurons[second_neuron[0]][second_neuron[1]].weights);
                }
                else if(i%2!=0 && j%2==0)
                {
                    int[] first_neuron=formulas.point_to_index_converter((r_i+1)*y+r_j,y);
                    int[] second_neuron =formulas.point_to_index_converter(r_i*y+r_j,y);
                    u_matrix[i][j]=formulas.euclidian_distance(neurons[first_neuron[0]][first_neuron[1]].weights,neurons[second_neuron[0]][second_neuron[1]].weights);
                }
                else
                {
                    if(flag)
                    {
                        int[] first_neuron=formulas.point_to_index_converter((r_i+1)*y+(r_j-1),y);
                        int[] second_neuron =formulas.point_to_index_converter((r_i*y)+r_j,y);
                        u_matrix[i][j]=formulas.euclidian_distance(neurons[first_neuron[0]][first_neuron[1]].weights,neurons[second_neuron[0]][second_neuron[1]].weights);
                        flag=false;
                    }
                    else
                    {
                        int[] first_neuron=formulas.point_to_index_converter((r_i+1)*y+(r_j),y);
                        int[] second_neuron =formulas.point_to_index_converter((r_i*y)+r_j-1,y);
                        u_matrix[i][j]=formulas.euclidian_distance(neurons[first_neuron[0]][first_neuron[1]].weights,neurons[second_neuron[0]][second_neuron[1]].weights);
                        flag=true;
                    }
                }
            }
            r_j=0;
            if(i%2==1) r_i++;
        }
        r_i=0;
        r_j=0;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                if(j%2==1) r_j++;
                if(i%2==0 && j%2==0)
                {
                    int c=0;
                    if(r_j%2==1)//upper
                    {
                        if(i-1>=0)
                        {
                            u_matrix[i][j]+=u_matrix[i-1][j];
                            c++;
                        }
                        if(i+1<x)
                        {
                            u_matrix[i][j]+=u_matrix[i+1][j];
                            c++;
                            if(j-1>=0)
                            {
                                u_matrix[i][j]+=u_matrix[i+1][j-1];
                                c++;
                            }
                            if(j+1<y)
                                {u_matrix[i][j]+=u_matrix[i+1][j+1];c++;}
                        }
                        if(j-1>=0)
                            {u_matrix[i][j]+=u_matrix[i][j-1];c++;}
                        if(j+1<y)
                            {u_matrix[i][j]+=u_matrix[i][j+1];c++;}
                        u_matrix[i][j]/=c;
                    }
                    else//lower
                    {
                        if(i+1<x)
                            {u_matrix[i][j]+=u_matrix[i+1][j];c++;}
                        if(i-1>=0)
                        {
                            {u_matrix[i][j]+=u_matrix[i-1][j];c++;}
                            if(j-1>=0)
                                {u_matrix[i][j]+=u_matrix[i-1][j-1];c++;}
                            if(j+1<y)
                                {u_matrix[i][j]+=u_matrix[i-1][j+1];c++;}
                        }
                        if(j-1>=0)
                            {u_matrix[i][j]+=u_matrix[i][j-1];c++;}
                        if(j+1<y)
                            {u_matrix[i][j]+=u_matrix[i][j+1];c++;}
                        u_matrix[i][j]/=c;
                    }
                }
            }
            r_j=0;
            if(i%2==1) r_i++;
        }
        return u_matrix;
    }

    public void print_weights()
    {
        try {
            FileWriter fw=null;
            File f = new File("results\\");
            if(!f.exists())
                f.mkdir();
            fw = new FileWriter("results\\weights.csv");
            fw.write("i,j,");
            for (int i = 0; i < neurons[0][0].weights.length; i++) {
                if(i+1<neurons[0][0].weights.length)
                    fw.write("col"+i+",");
                else
                    fw.write("col"+i+"\n");
            }
            for (int i = 0; i < x; i++) {
                for (int j = 0; j < y; j++) {
                    neuron selected = neurons[i][j];
                    fw.write(i+","+j+",");
                    for (int k = 0; k < selected.weights.length; k++) {
                        if(k+1<selected.weights.length)
                            fw.write(selected.weights[k]+",");
                        else
                            fw.write(selected.weights[k]+"\n");
                    }
                }
            }
            fw.flush();
            fw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void print_points_bmu()
    {
        try {
            FileWriter fw=null;
            File f = new File("results\\");
            if(!f.exists())
                f.mkdir();
            fw = new FileWriter("results\\points_bmu.csv");
            fw.write("point,neuron_i,neuron_j\n");
            for (int i = 0; i < points.length; i++) {
                int[] bmu=find_BMU(i);
                fw.write(i+","+bmu[0]+","+bmu[1]+"\n");
            }
            fw.flush();
            fw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void print_results()
    {
        print_weights();
        print_points_bmu();
    }

    //Counts of how many times each neuron become a bmu
    public double[][] get_hit_matrix()
    {
        double[][] result = new double[x][y];
        for (int i = 0; i < points.length; i++) {
            int[] bmu=find_BMU(i);
            result[bmu[0]][bmu[1]]++;
            if(result[bmu[0]][bmu[1]]>max_hit)
                max_hit=(int)result[bmu[0]][bmu[1]];
        }
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
