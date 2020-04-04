package com.company;

import java.util.Hashtable;

public class Main {

    public static void main(String[] args) {
        //Read data
        String user_directory = System.getProperty("user.dir");
        data_types[] my_types = new data_types[]{data_types.DOUBLE,data_types.DOUBLE};
        data my_data = new data(user_directory + "\\data\\double_taurus.csv", true,my_types,null);
//        data_types[] my_types = new data_types[]{data_types.DOUBLE, data_types.DOUBLE, data_types.DOUBLE, data_types.DOUBLE, data_types.CATEGORICAL, data_types.INTEGER, data_types.DOUBLE, data_types.DOUBLE};
//        data my_data = new data(user_directory + "\\data\\data.csv", true,my_types,null);
//        data_types[] my_types = new data_types[]{data_types.DOUBLE,data_types.DOUBLE};
//        data my_data = new data(user_directory + "\\data\\u_matrix_test.csv", true,my_types,null);
        my_data.convert_to_numbers();

        //Initialize SOM
        som my_som = new som(13, 13, 0.1,0.01,neighbour_func.GAUSSIAN,-1,1500,10);
        my_som.setPoints(my_data.get_data_points());
        my_som.initializeNeurons(my_data.getColumnCount(), my_data.getMaximum(), my_data.getMinimum(),my_data.column_type);

        //Train
        my_som.train();

        //Print Results;
        my_som.print_results();
        double[][] u_matrix = my_som.get_u_matrix();

        gui my_gui = new gui();
        my_gui.plotUMatrix(u_matrix,color_gradients.COLD_SCALE);

        /*gui my_gui = new gui();
        int index=1;
        my_gui.plotHeatMap(my_som.get_results(index),0,my_som.max_hit);*/
        //
        /*System.out.print("\nas");*/
        /*double[][] zValues = new double[][]{
                {1.2, 1.3, 1.5},
                {1.0, 1.1, 1.6}
        };
        System.out.print(zValues.length+" "+zValues[0].length);*/
        /*double[][] zValues = new double[][]{
                {1.0,0.5},
                {0.2,0.6},
                {1,0.25}
        };*/
        /*double[][] zValues = new double[][]{
                {1.0,0.75,0.5,0.77,1.0,0.75,0.5,1.0,0.75,0.5,0.77,1.0,0.75,0.5},
                {0.2,0.6,0.4,0.65,1.0,0.75,0.5,0.2,0.6,0.4,0.65,1.0,0.75,0.5},
                {1,0.25,0.33,1,1.0,0.75,0.5,1,0.25,0.33,1,1.0,0.75,0.5},
                {1.0,0.75,0.5,0.77,1.0,0.75,0.5,1.0,0.75,0.5,0.77,1.0,0.75,0.5},
                {0.2,0.6,0.4,0.65,1.0,0.75,0.5,0.2,0.6,0.4,0.65,1.0,0.75,0.5},
                {1,0.25,0.33,1,1.0,0.75,0.5,1,0.25,0.33,1,1.0,0.75,0.5}
        };*/
        /*double[][] zValues = new double[][]{
                {1.0,0.75,0.5,0.77,1.0,0.75,0.5},
                {0.2,0.6,0.4,0.65,1.0,0.75,0.5},
                {1,0.25,0.33,1,1.0,0.75,0.5}
        };*/
        /*double[][] zValues = new double[][]{
                {1.0,0.5,0.3,0.2},
                {1.0,0.5,0.3,0.2}
        };*/
       /* gui my_gui = new gui();
        my_gui.plotUMatrix(zValues,color_gradients.GRAY_SCALE);*/
      /* int n=5,m=9;
       int r_i=0,r_j=0;
       boolean flag=true;
        for (int i = 0; i < (n+(n-1)); i++) {
            for (int j = 0; j < (m+(m-1)); j++) {
                if(j%2==1) r_j++;
                if(i%2==0 && j%2==0)
                    System.out.print(""+(r_i*m+r_j)+",");
                else if(i%2==0 && j%2!=0)
                    System.out.print((r_i*m+r_j-1)+"."+(r_i*m+r_j)+",");
                else if(i%2!=0 && j%2==0)
                    System.out.print(((r_i+1)*m+r_j)+"."+(r_i*m+r_j)+",");
                else
                {
                    if(flag)
                    {
                        System.out.print(      ((r_i+1)*m+(r_j-1))+"."+((r_i*m)+r_j)+","           );
                        flag=false;
                    }
                    else
                    {
                        System.out.print(      ((r_i+1)*m+(r_j))+"."+((r_i*m)+r_j-1)+","           );
                        flag=true;
                    }
                }
            }
            r_j=0;
            System.out.println("");
            if(i%2==1) r_i++;
        }*/

    }
}
