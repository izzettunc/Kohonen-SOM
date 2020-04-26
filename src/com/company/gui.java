package com.company;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import org.tc33.jheatchart.HeatChart;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.image.ImageObserver;
import java.io.File;
import java.text.AttributedCharacterIterator;

public class gui {

    private JFrame window_Umatrix;
    private JFrame window_heat_map;
    private JFrame window_main;

    public void plotUMatrix(double[][] zValues,color_gradients colormap)
    {
        //Create window
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        window_Umatrix = new JFrame("U-Matrix");
        window_Umatrix.setPreferredSize(new Dimension(614,637));
        window_Umatrix.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        window_Umatrix.setVisible(true);
        window_Umatrix.setResizable(false);
        window_Umatrix.pack();
        window_Umatrix.setLocationRelativeTo(null);
        //Create content
        hexagonal_grid grid = new hexagonal_grid(zValues,colormap);
        grid.setBackground(new Color(0, 55, 41));
        //Show it on window
        window_Umatrix.add(grid);
    }

    public void plotHeatMap(double[][] zValues,int min,int max)
    {
        //Create window
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        window_heat_map = new JFrame("Hit Matrix");
        window_heat_map.setPreferredSize(new Dimension(614,637));
        window_heat_map.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        window_heat_map.setVisible(true);
        window_heat_map.setResizable(false);
        window_heat_map.pack();
        window_heat_map.setLocationRelativeTo(null);
        //Create content
        heat_map map = new heat_map(zValues,min,max);
        map.setBackground(new Color(0, 55, 41));
        //Show it on window
        window_heat_map.add(map);
    }

    public void createMainWindow()
    {
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        window_main = new JFrame("Kohonen SOM");
        window_main.setPreferredSize(new Dimension(614,637));
        window_main.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        window_main.setLayout(new GridLayout(3,1));

        JPanel data_preprocess = new JPanel(null);
        JPanel som_settings = new JPanel(null);
        JPanel reporting_settings = new JPanel(null);

        //Creating data_preprocess UI

        JLabel dpHeader_label = new JLabel("Data Preprocess");
        dpHeader_label.setFont(new Font(dpHeader_label.getFont().getName(),3,20));
        dpHeader_label.setForeground(Color.BLACK);
        dpHeader_label.setBounds(220,10,370,30);

        JButton data_ofd = new JButton("Select Train Data");
        data_ofd.setBounds(10,50,200,30);

        JLabel data_label = new JLabel("File Name : Not selected");
        data_label.setBounds(220,50,370,30);

        JButton type_ofd = new JButton("Select Data Types");
        type_ofd.setBounds(10,90,200,30);

        JLabel type_label = new JLabel("File Name : Not selected");
        type_label.setBounds(220,90,370,30);

        JButton ordinal_ofd = new JButton("Select Ordinal Table");
        ordinal_ofd.setBounds(10,130,200,30);

        JLabel ordinal_label = new JLabel("File Name : Not selected");
        ordinal_label.setBounds(220,130,370,30);

        JCheckBox conLabel_cbox = new JCheckBox("Contains Label");
        conLabel_cbox.setBounds(7,170,110,20);

        JCheckBox normalize_cbox = new JCheckBox("Normalize");
        normalize_cbox.setBounds(218,170,85,20);

        //Listeners

        data_ofd.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                    JFileChooser data_fc = new JFileChooser();
                    data_fc.setCurrentDirectory(new File(System.getProperty("user.dir")));
                    int result = data_fc.showOpenDialog(data_preprocess);
                    if (result == JFileChooser.APPROVE_OPTION) {
                        File selectedFile = data_fc.getSelectedFile();
                        data_label.setText("File Name : " + selectedFile.getName());
                        data_label.setToolTipText(selectedFile.getAbsolutePath());
                        settings.data_path=selectedFile.getAbsolutePath();
                    }
            }
        });

        type_ofd.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFileChooser type_fc = new JFileChooser();
                type_fc.setCurrentDirectory(new File(System.getProperty("user.dir")));
                int result = type_fc.showOpenDialog(data_preprocess);
                if (result == JFileChooser.APPROVE_OPTION) {
                    File selectedFile = type_fc.getSelectedFile();
                    type_label.setText("File Name : " + selectedFile.getName());
                    type_label.setToolTipText(selectedFile.getAbsolutePath());
                    settings.type_path=selectedFile.getAbsolutePath();
                }
            }
        });

        ordinal_ofd.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFileChooser ordinal_fc = new JFileChooser();
                ordinal_fc.setCurrentDirectory(new File(System.getProperty("user.dir")));
                int result = ordinal_fc.showOpenDialog(data_preprocess);
                if (result == JFileChooser.APPROVE_OPTION) {
                    File selectedFile = ordinal_fc.getSelectedFile();
                    ordinal_label.setText("File Name : " + selectedFile.getName());
                    ordinal_label.setToolTipText(selectedFile.getAbsolutePath());
                    settings.ordinal_path=selectedFile.getAbsolutePath();
                }
            }
        });

        conLabel_cbox.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                settings.contains_label=!settings.contains_label;
            }
        });

        normalize_cbox.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                settings.normalize=!settings.normalize;
            }
        });


        data_preprocess.add(dpHeader_label);
        data_preprocess.add(data_ofd);
        data_preprocess.add(data_label);
        data_preprocess.add(type_ofd);
        data_preprocess.add(type_label);
        data_preprocess.add(ordinal_ofd);
        data_preprocess.add(ordinal_label);
        data_preprocess.add(conLabel_cbox);
        data_preprocess.add(normalize_cbox);

        //Creating som_settings UI

        JLabel ssHeader_label = new JLabel("Settings");
        ssHeader_label.setFont(new Font(ssHeader_label.getFont().getName(),3,20));
        ssHeader_label.setForeground(Color.BLACK);
        ssHeader_label.setBounds(260,10,370,30);

        JLabel somx_label = new JLabel("Map width:");
        somx_label.setBounds(10,50,75,30);

        JTextField somx_textfield = new JTextField();
        somx_textfield.setBounds(95,57,115,20);

        JLabel somy_label = new JLabel("Map height:");
        somy_label.setBounds(385,50,75,30);

        JTextField somy_textfield = new JTextField();
        somy_textfield.setBounds(475,57,115,20);

        JLabel nfunc_label = new JLabel("Neighbourhood Function:");
        nfunc_label.setBounds(10,90,200,30);

        JComboBox nfunc_cmbbox = new JComboBox(new String[] {"Gaussian Function","Mexican Hat Function"});
        nfunc_cmbbox.setBounds(10,120,200,20);

        JLabel initRad_label = new JLabel("Initial Radius:");
        initRad_label.setBounds(385,115,80,30);

        JTextField initRad_textfield = new JTextField();
        initRad_textfield.setBounds(475,122,115,20);

        JLabel lrs_label = new JLabel("Initial Learning Rate:");
        lrs_label.setBounds(10,160,120,30);

        JTextField lrs_textfield = new JTextField();
        lrs_textfield.setBounds(140,167,70,20);

        JLabel lre_label = new JLabel("Final Learning Rate:");
        lre_label.setBounds(240,160,115,30);

        JTextField lre_textfield = new JTextField();
        lre_textfield.setBounds(365,167,70,20);

        JLabel iter_label = new JLabel("Iteration:");
        iter_label.setBounds(460,160,60,30);

        JTextField iter_textfield = new JTextField();
        iter_textfield.setBounds(520,167,70,20);

        nfunc_cmbbox.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                if (e.getStateChange() == ItemEvent.SELECTED) {
                    Object item = e.getItem();
                    if(item.toString().equals("Gaussian Function"))
                        settings.func=neighbour_func.GAUSSIAN;
                    else
                        settings.func=neighbour_func.MEXICAN_HAT;
                }
            }
        });

        som_settings.add(ssHeader_label);
        som_settings.add(somx_label);
        som_settings.add(somx_textfield);
        som_settings.add(somy_label);
        som_settings.add(somy_textfield);
        som_settings.add(nfunc_label);
        som_settings.add(nfunc_cmbbox);
        som_settings.add(initRad_label);
        som_settings.add(initRad_textfield);
        som_settings.add(lrs_label);
        som_settings.add(lrs_textfield);
        som_settings.add(lre_label);
        som_settings.add(lre_textfield);
        som_settings.add(iter_label);
        som_settings.add(iter_textfield);

        //Creating reporting_settings UI

        JLabel vsHeader_label = new JLabel("Reporting");
        vsHeader_label.setFont(new Font(vsHeader_label.getFont().getName(),3,20));
        vsHeader_label.setForeground(Color.BLACK);
        vsHeader_label.setBounds(255,10,370,30);

        JCheckBox hitmap_cbox = new JCheckBox("Plot Hit-Map");
        hitmap_cbox.setBounds(32,50,100,20);

        JCheckBox uMatrix_cbox = new JCheckBox("Plot U-Matrix");
        uMatrix_cbox.setBounds(235,50,100,20);

        JCheckBox report_cbox = new JCheckBox("Print report");
        report_cbox.setBounds(32,90,100,20);

        JLabel colorScale_label = new JLabel("U-Matrix Color Scale:");
        colorScale_label.setBounds(440,50,125,30);
        colorScale_label.setVisible(false);

        JComboBox colorScale_cmbbox = new JComboBox(new String[] {"Gray scale","Hot scale","Cold scale", "Five color"});
        colorScale_cmbbox.setBounds(440,80,125,20);
        colorScale_cmbbox.setVisible(false);

        JButton run_button = new JButton("Run");
        run_button.setBounds(440,140,125,50);

        JButton credits_button = new JButton("Info");
        credits_button.setBounds(35,140,125,50);

        JButton def_button = new JButton("Default Settings");
        def_button.setBounds(238,140,125,50);

        hitmap_cbox.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                settings.hitmap=!settings.hitmap;
            }
        });

        uMatrix_cbox.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                settings.umatrix=!settings.umatrix;
                colorScale_cmbbox.setVisible(!colorScale_cmbbox.isVisible());
                colorScale_label.setVisible(!colorScale_label.isVisible());
            }
        });

        report_cbox.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                settings.report=!settings.report;
            }
        });

        credits_button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String credits="I didn't write code of the heatmap myself. It is a library called JHeatChart\n" +
                        "And this is the link for it\n\n" +
                        "http://www.javaheatmap.com/\n" +
                        "\n\nAlso I take the mexican hat function from a book called\nNeural Networks and Statistical Learning by Ke-lin Du and M. N. S. Swamy\n"+
                        "It's link in the formulas.java file at row 37\n\n\n"+
                        "And lastly this is my github account:"+
                        "\n\nhttps://github.com/izzettunc";
                JOptionPane.showMessageDialog(reporting_settings,credits,"Credits",JOptionPane.INFORMATION_MESSAGE);
            }
        });

        def_button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                normalize_cbox.setSelected(false);
                somx_textfield.setText("10");
                somy_textfield.setText("10");
                nfunc_cmbbox.setSelectedIndex(0);
                initRad_textfield.setText("10");
                lrs_textfield.setText("0.1");
                lre_textfield.setText("0.01");
                iter_textfield.setText("1000");
                hitmap_cbox.setSelected(true);
                uMatrix_cbox.setSelected(true);
                report_cbox.setSelected(true);
                colorScale_cmbbox.setSelectedIndex(0);
            }
        });

        colorScale_cmbbox.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                if (e.getStateChange() == ItemEvent.SELECTED) {
                    Object item = e.getItem();
                    if(item.toString().equals("Gray scale"))
                        settings.color= color_gradients.GRAY_SCALE;
                    else if(item.toString().equals("Hot scale"))
                        settings.color=color_gradients.HOT_SCALE;
                    else if(item.toString().equals("Cold scale"))
                        settings.color=color_gradients.COLD_SCALE;
                    else
                        settings.color=color_gradients.FIVE_COLOR;

                }
            }
        });

        run_button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                boolean all_inputs_are_valid=true;
                String tempVal = somx_textfield.getText();
                if(!(isInteger(tempVal) && Integer.parseInt(tempVal)>1))
                {
                    JOptionPane.showMessageDialog(reporting_settings,"Map width should be an integer that is bigger than 1","Error : Invalid value error",JOptionPane.ERROR_MESSAGE);
                    somx_textfield.setText("");
                    all_inputs_are_valid=false;
                }
                tempVal = somy_textfield.getText();
                if(!(isInteger(tempVal) && Integer.parseInt(tempVal)>1))
                {
                    JOptionPane.showMessageDialog(reporting_settings,"Map height should be an integer that is bigger than 1","Error : Invalid value error",JOptionPane.ERROR_MESSAGE);
                    somy_textfield.setText("");
                    all_inputs_are_valid=false;
                }
                tempVal = initRad_textfield.getText();
                if(!(isDouble(tempVal) && Double.parseDouble(tempVal)>=0))
                {
                    JOptionPane.showMessageDialog(reporting_settings,"Initial radius should be a double that is not a negative value","Error : Invalid value error",JOptionPane.ERROR_MESSAGE);
                    initRad_textfield.setText("");
                    all_inputs_are_valid=false;
                }
                tempVal = lrs_textfield.getText();
                if(!(isDouble(tempVal) && Double.parseDouble(tempVal)>0 && Double.parseDouble(tempVal)<1))
                {
                    JOptionPane.showMessageDialog(reporting_settings,"Initial Learning rate should be a double that is between zero and one","Error : Invalid value error",JOptionPane.ERROR_MESSAGE);
                    lrs_textfield.setText("");
                    all_inputs_are_valid=false;
                }
                tempVal = lre_textfield.getText();
                if(!(isDouble(tempVal) && Double.parseDouble(tempVal)>0 && Double.parseDouble(tempVal)<1))
                {
                    JOptionPane.showMessageDialog(reporting_settings,"Initial Learning rate should be a double that is between zero and one","Error : Invalid value error",JOptionPane.ERROR_MESSAGE);
                    lre_textfield.setText("");
                    all_inputs_are_valid=false;
                }
                tempVal = iter_textfield.getText();
                if(!(isInteger(tempVal) && Integer.parseInt(tempVal)>0))
                {
                    JOptionPane.showMessageDialog(reporting_settings,"Iteration should be an integer that is bigger than 0","Error : Invalid value error",JOptionPane.ERROR_MESSAGE);
                    iter_textfield.setText("");
                    all_inputs_are_valid=false;
                }
                if(settings.data_path==null)
                {
                    JOptionPane.showMessageDialog(reporting_settings,"Please select a data file","Error : Null data error",JOptionPane.ERROR_MESSAGE);
                    all_inputs_are_valid=false;
                }
                if(settings.type_path==null)
                {
                    JOptionPane.showMessageDialog(reporting_settings,"Please select a data type file","Error : Null data type error",JOptionPane.ERROR_MESSAGE);
                    all_inputs_are_valid=false;
                }
                if(all_inputs_are_valid)
                {
                    settings.iter=Integer.parseInt(iter_textfield.getText());
                    settings.lrs=Double.parseDouble(lrs_textfield.getText());
                    settings.lre=Double.parseDouble(lre_textfield.getText());
                    settings.init_rad=Double.parseDouble(initRad_textfield.getText());
                    settings.somx=Integer.parseInt(somx_textfield.getText());
                    settings.somy=Integer.parseInt(somy_textfield.getText());
                    settings.run();
                }

            }

            boolean isDouble(String x)
            {
                try
                {
                    double res = Double.parseDouble(x);
                    return true;
                }
                catch (Exception e)
                {
                    return false;
                }
            }

            boolean isInteger(String x)
            {
                try
                {
                    double res = Integer.parseInt(x);
                    return true;
                }
                catch (Exception e)
                {
                    return false;
                }
            }

        });

        reporting_settings.add(colorScale_cmbbox);
        reporting_settings.add(credits_button);
        reporting_settings.add(def_button);
        reporting_settings.add(run_button);
        reporting_settings.add(hitmap_cbox);
        reporting_settings.add(colorScale_label);
        reporting_settings.add(uMatrix_cbox);
        reporting_settings.add(report_cbox);
        reporting_settings.add(vsHeader_label);

        window_main.add(data_preprocess);
        window_main.add(som_settings);
        window_main.add(reporting_settings);

        window_main.setVisible(true);
        window_main.setResizable(false);
        window_main.pack();
        window_main.setLocationRelativeTo(null);
    }

    static void show_error_message(String message,String title)
    {
        JOptionPane.showMessageDialog(null,message,"Error : "+title,JOptionPane.ERROR_MESSAGE);
    }
}
