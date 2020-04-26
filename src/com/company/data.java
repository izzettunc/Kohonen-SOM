package com.company;

import javax.lang.model.type.NullType;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class data {

    private int row;
    private int column;
    private ArrayList<String[]> raw_data;
    private ArrayList<String> labels;
    private ArrayList<Double> maximum;
    private ArrayList<Double> minimum;
    private ArrayList<ArrayList<Double>> processed_data;
    private Hashtable<Integer,Hashtable<String,Double>> ordinal_lookup_table;
    public data_types[] column_type;
    private boolean contains_label;

    public data(String path, boolean contains_label,data_types[] column_type,Hashtable<Integer,Hashtable<String,Double>> ordinal_lookup_table)
    {
        row=0;
        column=0;
        labels=new ArrayList<String>();
        raw_data = new ArrayList<String[]>();
        this.contains_label = contains_label;
        read_csv(path);
        maximum = new ArrayList<Double>();
        minimum = new ArrayList<Double>();
        this.column_type=column_type;
        this.ordinal_lookup_table=ordinal_lookup_table;
        processed_data = new ArrayList<ArrayList<Double>>();
    }

    public void convert_to_numbers()
    {
        int processed_data_index=0;
        int increased_column = column;
        ArrayList<String> new_labels = new ArrayList<String>();
        //Processes the data according to its type and alters the indexing if a new column added
        for (int i = 0; i < column; i++) {
            switch (column_type[processed_data_index])
            {
                case DOUBLE:
                    process_double_column(processed_data_index,i);
                    processed_data_index++;
                    if(contains_label)
                        new_labels.add(labels.get(i));
                    break;
                case INTEGER:
                    process_double_column(processed_data_index,i);
                    processed_data_index++;
                    if(contains_label)
                        new_labels.add(labels.get(i));
                    break;
                case BOOL:
                    process_bool_column(processed_data_index,i);
                    processed_data_index++;
                    if(contains_label)
                        new_labels.add(labels.get(i));
                    break;
                case CATEGORICAL:
                    int temp=process_categorical_column(processed_data_index,i);
                    List<data_types> types = new ArrayList<data_types>();
                    for (int j = 0; j < processed_data_index; j++) {
                        types.add(column_type[j]);
                    }
                    for (int j = 0; j < temp; j++) {
                        types.add(data_types.CATEGORICAL);
                        if(contains_label)
                            new_labels.add(labels.get(i)+"_"+j);
                    }
                    for (int j = processed_data_index+1; j < increased_column; j++) {
                        types.add(column_type[j]);
                    }
                    processed_data_index+=temp;
                    increased_column+=temp-1;
                    column_type = new data_types[types.size()];
                    types.toArray(column_type);
                    break;
                case ORDINAL:
                    process_ordinal_column(processed_data_index,i);
                    processed_data_index++;
                    if(contains_label)
                        new_labels.add(labels.get(i));
                    break;
            }
        }
        column=increased_column;
        if(contains_label)
            labels=new_labels;
        find_max_min_values();
    }

    public void normalize()
    {
        for (int i = 0; i < row ; i++) {
            for (int j = 0; j < column; j++) {
                double data=processed_data.get(j).get(i);
                processed_data.get(j).set(i,((data-minimum.get(j))/(maximum.get(j)-minimum.get(j))));
            }
        }
        //Because of the values changed finds and re-assignes max and min values
        find_max_min_values();
    }

    private void find_max_min_values()
    {
        minimum.clear();
        maximum.clear();
        for (int i = 0; i < column; i++) {
            minimum.add(Collections.min(processed_data.get(i)));
            maximum.add(Collections.max(processed_data.get(i)));
        }
    }

    private void process_ordinal_column(int index,int raw_data_index)
    {
        //Changes values according to look up table
        processed_data.add(new ArrayList<Double>());
        for (int i = 0; i < row; i++) {
            double val=ordinal_lookup_table.get(raw_data_index).get(raw_data.get(i)[raw_data_index]);
            processed_data.get(index).add(val);
        }
    }

    //Uses one-hot encoding
    private int process_categorical_column(int index,int raw_data_index)
    {
        Hashtable<String,Integer> notebook = new Hashtable<String, Integer>();
        int dummy_var_index=0;
        for (int i = 0; i < row; i++) {
            if(!notebook.containsKey(raw_data.get(i)[raw_data_index])) {
                notebook.put(raw_data.get(i)[raw_data_index], dummy_var_index);
                dummy_var_index++;
            }
        }
        //Number of columns that is going to be added
        //Avoidance dummy variable trap
        int col_count=notebook.size()-1;
        for (int i = 0; i < col_count; i++)
            processed_data.add(new ArrayList<Double>());
        for (int i = 0; i < row; i++) {
                for (int j = 0; j < col_count; j++) {
                    if(notebook.get(raw_data.get(i)[raw_data_index])==j)
                        processed_data.get(index+j).add(1.0);
                    else
                        processed_data.get(index+j).add(0.0);
                }
        }
        return col_count;

    }

    private void process_bool_column(int index,int raw_data_index)
    {
        processed_data.add(new ArrayList<Double>());
        for (int i = 0; i < row; i++) {
            if(raw_data.get(i)[raw_data_index].equalsIgnoreCase("true"))
                processed_data.get(index).add(1.0);
            else
                processed_data.get(index).add(0.0);
        }
    }

    private void process_double_column(int index,int raw_data_index)
    {
        processed_data.add(new ArrayList<Double>());
        for (int i = 0; i < row; i++) {
            processed_data.get(index).add(Double.parseDouble(raw_data.get(i)[raw_data_index]));
        }
    }

    private void read_csv(String path){
        String row = null;
        BufferedReader csvReader = null;
        try {
            csvReader = new BufferedReader(new FileReader(path));
        } catch (FileNotFoundException e) {
            gui.show_error_message(e.getMessage(),"FileNotFoundException");
        }
        while (true) {
            try {
                row = csvReader.readLine();
                if(this.row==0 && contains_label && row!=null)
                {
                    String[] elements = row.split(",");
                    for (int i = 0; i < elements.length ; i++) {
                        labels.add(elements[i]);
                    }
                    row = csvReader.readLine();
                }

            } catch (IOException e) {
                gui.show_error_message(e.getMessage(),"IOException");
            }
            if(row == null) break;
            String[] elements = row.split(",");
            raw_data.add(elements);
            this.row++;
        }
        if(this.row > 0) column=raw_data.get(0).length;
        try {
            csvReader.close();
        } catch (IOException e) {
            gui.show_error_message(e.getMessage(),"IOException");
        }
    }

    public point[] get_data_points()
    {
        point[] data_points = new point[processed_data.get(0).size()];
        for (int i = 0; i < data_points.length; i++) {
            double[] val = new double[processed_data.size()];
            for (int j = 0; j < val.length; j++) {
                val[j]=processed_data.get(j).get(i);
            }
            data_points[i] = new point(val);
        }
        return  data_points;
    }

    public ArrayList<Double> getMaximum() {
        return maximum;
    }

    public ArrayList<Double> getMinimum() {
        return minimum;
    }

    public int getRowCount() {
        return row;
    }

    public int getColumnCount() {
        return column;
    }

    public static data_types[] get_dataTypes_from_file(String path)
    {
        data_types[] my_data_types;
        String row = null;
        BufferedReader csvReader = null;
        try {
            csvReader = new BufferedReader(new FileReader(path));
        } catch (FileNotFoundException e) {
            gui.show_error_message(e.getMessage(),"FileNotFoundException");
        }
        try {
            row = csvReader.readLine();
            if(row == null)
                throw new IOException("File is empty");
        } catch (IOException e) {
            gui.show_error_message(e.getMessage(),"IOExcepiton");
        }
        String[] elements = row.split(",");
        my_data_types = new data_types[elements.length];
        try {
            for (int i = 0; i < elements.length; i++) {
                if (elements[i].equals("INTEGER"))
                    my_data_types[i] = data_types.INTEGER;
                else if (elements[i].equals("DOUBLE"))
                    my_data_types[i] = data_types.DOUBLE;
                else if (elements[i].equals("CATEGORICAL"))
                    my_data_types[i] = data_types.CATEGORICAL;
                else if (elements[i].equals("ORDINAL"))
                    my_data_types[i] = data_types.ORDINAL;
                else if (elements[i].equals("BOOL"))
                    my_data_types[i] = data_types.BOOL;
                else
                    throw new IOException("Invalid data type : " + elements[i]);
            }
        }
        catch (IOException e)
        {
            gui.show_error_message(e.getMessage(),"IOException");
        }
        try {
            csvReader.close();
        } catch (IOException e) {
            gui.show_error_message(e.getMessage(),"IOException");
        }
        return my_data_types;
    }

    public static Hashtable<Integer,Hashtable<String,Double>> get_ordinalTable_from_file(String path)
    {
        if(path==null) return null;
        Hashtable<Integer,Hashtable<String,Double>> my_ordinal_table = new Hashtable<>();
        String row = null;
        BufferedReader csvReader = null;
        try {
            csvReader = new BufferedReader(new FileReader(path));
        } catch (FileNotFoundException e) {
            gui.show_error_message(e.getMessage(),"FileNotFoundException");
        }
        try {while (true) {
            try {
                row = csvReader.readLine();
            } catch (IOException e) {
                gui.show_error_message(e.getMessage(),"IOException");
            }
                if (row == null) break;
                Hashtable<String, Double> table = new Hashtable<>();
                String[] elements = row.split(",");
                if (elements.length < 3 && elements.length % 2 == 0)
                    throw new IOException("Ordinal table data structure must be like this:\ncolumn index,string,double,string,double,...,...,string,double");
                for (int i = 1; i < elements.length; i += 2) {
                    table.put(elements[i], Double.parseDouble(elements[i + 1]));
                }
                my_ordinal_table.put(Integer.parseInt(elements[0]),table);
            }
        }
        catch (IOException e)
        {
            gui.show_error_message(e.getMessage(),"Invalid data");
        }
        catch (NumberFormatException e)
        {
            gui.show_error_message(e.getMessage(),"NumberFormatException");
        }
        try {
            csvReader.close();
        } catch (IOException e) {
            gui.show_error_message(e.getMessage(),"IOException");
        }
        return my_ordinal_table;
    }
}
