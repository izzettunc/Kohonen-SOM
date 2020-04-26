package com.company;

public class settings {
    static String data_path;
    static String type_path;
    static String ordinal_path;
    static boolean contains_label=false;
    static boolean normalize=false;
    static int somx;
    static int somy;
    static neighbour_func func=neighbour_func.GAUSSIAN;
    static double init_rad;
    static double lrs;
    static double lre;
    static int iter;
    static color_gradients color=color_gradients.GRAY_SCALE;
    static boolean umatrix=false;
    static boolean hitmap=false;
    static boolean report=false;

    static void run()
    {
        //Prepares the data

        data my_data= new data(data_path,contains_label,data.get_dataTypes_from_file(type_path),data.get_ordinalTable_from_file(ordinal_path));
        my_data.convert_to_numbers();
        if(normalize)
            my_data.normalize();

        //Initializes the SOM
        som my_som= new som(somy,somx,lrs,lre,func,init_rad,iter);
        //Initializes the data inside the SOM
        my_som.setPoints(my_data.get_data_points());
        //Initializes neurons
        my_som.initializeNeurons(my_data.getColumnCount(), my_data.getMaximum(), my_data.getMinimum(),my_data.column_type);

        //Trains
        my_som.train();

        //Reporting
        if(report)
            my_som.print_results();
        if(umatrix)
        {
            gui my_gui = new gui();
            my_gui.plotUMatrix(my_som.get_u_matrix(),color);
        }
        if(hitmap)
        {
            gui my_gui = new gui();
            my_gui.plotHeatMap(my_som.get_hit_matrix(),0,my_som.max_hit);
        }

    }
}
