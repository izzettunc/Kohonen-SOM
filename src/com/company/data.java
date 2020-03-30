package com.company;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Dictionary;
import java.util.HashSet;
import java.util.Hashtable;

public class data {

    private point[] points;
    private int row;
    private int column;
    private ArrayList<String[]> raw_data;
    private String[] labels;
    private double[][] processed_data;

    public data(String path,boolean contains_label)
    {
        row=0;
        column=0;
        raw_data = new ArrayList<String[]>();
        try {
            read_csv(path,contains_label);
        } catch (IOException e) {
            e.printStackTrace();
        }
        processed_data = new double[row][column];
        process();
        points = new point[row];
        for (int i = 0; i < row ; i++) {
            points[i] = new point(processed_data[i]);
        }
    }

    private void process()
    {
        Hashtable<String,Integer> notebook = new Hashtable<String, Integer>();
        for (int j = 0; j < column; j++) {
            for (int i = 0; i < row; i++) {
                try {
                    processed_data[i][j] = Double.parseDouble(raw_data.get(i)[j]);
                }
                catch (NumberFormatException e)
                {
                    if(notebook.containsKey(raw_data.get(i)[j]))
                    {
                        processed_data[i][j]=notebook.get(raw_data.get(i)[j]);
                    }
                    else
                    {
                        processed_data[i][j]=notebook.size();
                        notebook.put(raw_data.get(i)[j],notebook.size());
                    }
                }
            }
            notebook.clear();
        }
    }
    private void read_csv(String path,boolean contains_label)throws IOException {
        String row = null;
        BufferedReader csvReader = null;
        try {
            csvReader = new BufferedReader(new FileReader(path));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        while (true) {
            try {
                row = csvReader.readLine();
                if(this.row==0 && contains_label && row!=null)
                {
                    String[] elements = row.split(",");
                    labels=elements;
                    row = csvReader.readLine();
                }

            } catch (IOException e) {
                e.printStackTrace();
            }
            if(row == null) break;
            String[] elements = row.split(",");
            raw_data.add(elements);
            this.row++;
        }
        if(this.row > 0) column=raw_data.get(0).length;
        csvReader.close();
    }
}
