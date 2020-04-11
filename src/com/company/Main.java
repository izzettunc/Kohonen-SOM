package com.company;

import java.util.Hashtable;

public class Main {

    public static void main(String[] args) {
        //Read data
        String user_directory = System.getProperty("user.dir");
        data_types[] my_types = new data_types[]{data_types.DOUBLE, data_types.DOUBLE, data_types.DOUBLE, data_types.DOUBLE, data_types.CATEGORICAL, data_types.INTEGER, data_types.DOUBLE, data_types.DOUBLE};
        data my_data = new data(user_directory + "\\data\\data.csv", true,my_types,null);
        my_data.convert_to_numbers();
        //my_data.normalize();

        //Initialize SOM
        //There is two type of neighbourhood function Gaussian and mexican hat
        //You can change initial radius by changing init_rad to a positive value (-1 is for default assignment which is (x+y)/2)
        som my_som = new som(10, 10, 0.1,0.01,neighbour_func.GAUSSIAN,-1,1000);
        my_som.setPoints(my_data.get_data_points());
        my_som.initializeNeurons(my_data.getColumnCount(), my_data.getMaximum(), my_data.getMinimum(),my_data.column_type);

        //Train
        my_som.train();

        //Print Results;
        my_som.print_results();
        double[][] u_matrix = my_som.get_u_matrix();
        double[][] hit_matrix = my_som.get_hit_matrix();
        gui my_gui = new gui();
        //I didn't write code of the heatmap myself it is a library called JHeatChart
        //And this is the link for it
        //http://www.javaheatmap.com/
        my_gui.plotHeatMap(hit_matrix,0,my_som.max_hit);
        //There is four types of color gradients which are COLD,HOT,GRAY and FIVE COLOR
        my_gui.plotUMatrix(u_matrix,color_gradients.GRAY_SCALE);
    }
}
