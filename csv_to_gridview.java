import javax.swing.*;
import javax.swing.event.*;
import javax.swing.table.*;
import javax.swing.filechooser.FileFilter;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.table.AbstractTableModel;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.io.File;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.StringTokenizer;

public class csv_to_gridview {

    public static String getFile(){

        String csv = "";
        JFileChooser jfc = new JFileChooser();
        jfc.addChoosableFileFilter(new FileFilter() {

        @Override
            public boolean accept(File f) {
            return f.isDirectory() || f.getName().toLowerCase().endsWith(".csv");
            }

        @Override
            public String getDescription() {
            return "csv (*.csv)";
            }

        @Override
            public String toString() {
            return getDescription();
            }
        });
        try {
             UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
             SwingUtilities.updateComponentTreeUI(jfc);
            } catch (Exception e) {
              e.printStackTrace();
            }

        int returnValue = jfc.showOpenDialog(null); 
        if (returnValue == JFileChooser.APPROVE_OPTION) {
            csv = jfc.getSelectedFile().getAbsolutePath();
            if (csv == null ) {
                csv="";  
            }

        }
       return csv;
   }

   
    public static void main(String args[]) {

        String[][] data = { { "" } };
        String[] columnNames = { "" };
        String csvfile = getFile();

        // JOptionPane.showMessageDialog(null,csvfile);  

        JFrame frame = new JFrame("Java");

        if (csvfile  == "") {

            // Show empty table

            // JTable t = new JTable(data, columnNames);
            // t.setBounds(30, 40, 200, 300);
            // frame.add(t);

            }
        else {

            // Read csv data

            DefaultTableModel model = new DefaultTableModel();
            String firstRow;
            Vector<Vector<String>> vectorVectorStringsData = new Vector<Vector<String>>();
            Vector<String> vectorStrings = new Vector<String>();
            Vector<String> vectorColumnIdentifiers = new Vector<String>();
            String[] columnIdentifiers;
            File inputFile;
            inputFile = new File(csvfile);

            try (FileReader fr = new FileReader(inputFile);
                BufferedReader br = new BufferedReader(fr)) {

                firstRow = br.readLine().trim();
                if (firstRow != null) {

                // headers:
                columnIdentifiers = firstRow.split(",");
                vectorColumnIdentifiers = new Vector<String>();
                for (int j =0; j < columnIdentifiers.length; j++) {
                    vectorColumnIdentifiers.add(columnIdentifiers[j]);
                    }

                }

                // rows
                Object[] tableLines = br.lines().toArray();

                // data rows
                for (int i = 0; i < tableLines.length; i++) {
                    String line = tableLines[i].toString().trim();
                    String[] dataRow = line.split(",");
                    vectorStrings = new Vector<String>();
                    for (int j =0; j < dataRow.length; j++) {
                        vectorStrings.add(dataRow[j]);
                    }
                    vectorVectorStringsData.add(vectorStrings);
                }

                fr.close();

                }

            catch (IOException ioe) {
                JOptionPane.showMessageDialog(null,"Error : " + ioe.getMessage());

            }

            //Display Content
            model.setDataVector(vectorVectorStringsData, vectorColumnIdentifiers);
            JTable t = new JTable(model);
            t.setBounds(30, 40, 200, 300);
            frame.add(t);

            }

        // JScrollPane sp = new JScrollPane(t);
        // frame.add(sp);
        frame.setSize(700, 400);
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }

}

