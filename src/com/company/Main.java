package com.company;

import java.util.Hashtable;

public class Main {

    public static void main(String[] args) {
        //Read data
        String user_directory = System.getProperty("user.dir");
//        data_types[] my_types = new data_types[]{data_types.DOUBLE,data_types.DOUBLE};
//        data my_data = new data(user_directory + "\\data\\double_taurus.csv", true,my_types,null);
        data_types[] my_types = new data_types[]{data_types.DOUBLE, data_types.DOUBLE, data_types.DOUBLE, data_types.DOUBLE, data_types.CATEGORICAL, data_types.INTEGER, data_types.DOUBLE, data_types.DOUBLE};
        data my_data = new data(user_directory + "\\data\\data.csv", true,my_types,null);
        my_data.convert_to_numbers();

        //Initialize SOM
        som my_som = new som(25, 25, 0.1,0.01,neighbour_func.GAUSSIAN,-1,1500,10);
        my_som.setPoints(my_data.get_data_points());
        my_som.initializeNeurons(my_data.getColumnCount(), my_data.getMaximum(), my_data.getMinimum(),my_data.column_type);

        //Train
        my_som.train();
        my_som.print_results();
        gui my_gui = new gui();
        int index=1;
        my_gui.plotHeatMap(my_som.get_results(index),0,my_som.max_hit);
        //
        System.out.print("\nas");
       /* double[][] zValues = new double[][]{
                {1.2, 1.3, 1.5},
                {1.0, 1.1, 1.6},
                {0.7, 0.9, 1.3}
        };
        */
    }
}
