package com.company;

import java.util.Hashtable;

public class Main {

    public static void main(String[] args) {
        //Read data
        String user_directory = System.getProperty("user.dir");
        data_types[] my_types = new data_types[]{data_types.DOUBLE, data_types.DOUBLE, data_types.DOUBLE, data_types.DOUBLE, data_types.CATEGORICAL, data_types.INTEGER, data_types.DOUBLE, data_types.DOUBLE};
        data my_data = new data(user_directory + "\\data\\data.csv", true,my_types,null);
        my_data.convert_to_numbers();

        //Initialize SOM
        /*som my_som = new som(10, 10, 0.1,neighbour_func.GAUSSIAN);
        my_som.setPoints(my_data.getPoints());
        my_som.initializeNeurons(my_data.getColumnCount(), my_data.getMaximum(), my_data.getMinimum(),my_data.column_type);*/


        //Train

        //my_som.shufflePoints();
        System.out.print("as");
       /* double[][] zValues = new double[][]{
                {1.2, 1.3, 1.5},
                {1.0, 1.1, 1.6},
                {0.7, 0.9, 1.3}
        };
        gui my_gui = new gui(400,400);
        my_gui.plotHeatMap(zValues);*/
    }
}
