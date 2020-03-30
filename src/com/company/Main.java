package com.company;

public class Main {

    public static void main(String[] args) {
        String user_directory=System.getProperty("user.dir");
        data my_data = new data(user_directory+"\\data\\data.csv",true);
        //System.out.print("as");
        double[][] zValues = new double[][]{
                {1.2, 1.3, 1.5},
                {1.0, 1.1, 1.6},
                {0.7, 0.9, 1.3}
        };
        gui my_gui = new gui(400,400);
        my_gui.plotHeatMap(zValues);
    }
}
