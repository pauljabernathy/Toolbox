/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package toolbox.io;

import java.io.*;
import java.util.List;
import toolbox.util.ListArrayUtil;

/**
 *
 * @author paul
 */
public class CSVWriter {
    
    public static void writeArray(double[] nums, String filename, String header, String separator) throws IOException {
        PrintWriter writer = new PrintWriter(new FileWriter(filename));
        if(header != null && !header.equals("")) {
            writer.println(header);
        }
        for(double num : nums) {
            writer.print(num);
            writer.print(separator);
        }
        writer.flush();
        writer.close();
    }
    
    //TODO: unit test
    public static void writeArray(int[] nums, String filename, String header, String separator) throws IOException {
        PrintWriter writer = new PrintWriter(new FileWriter(filename));
        if(header != null && !header.equals("")) {
            writer.println(header);
        }
        for(double num : nums) {
            writer.print(num);
            writer.print(separator);
        }
        writer.flush();
        writer.close();
    }
    
    //TODO: unit test
    public static <T> void writeArray(T[] array, String filename, String header, String separator) throws IOException {
        PrintWriter writer = new PrintWriter(new FileWriter(filename));
        if(header != null && !header.equals("")) {
            writer.println(header);
        }
        for(T current : array) {
            writer.print(current);
            writer.print(separator);
        }
        writer.flush();
        writer.close();
    }
    
    public static <T> void writeList(List<T> list, String filename, String header, String separator) throws IOException {
        T[] array = (T[])list.toArray();
        writeArray(array, filename, header, separator);
    }
    
    public static void writeToFile(List<List> data, String filename, String header, String separator, boolean byRow) throws IOException {
        if(byRow) {
            writeRowsToFile(data, filename, header, separator);
        }
        else {
            writeColumnsToFile(data, filename, header, separator);
        }
    }
    
    public static void writeRowsToFile(List<List> data, String filename, String header, String separator) throws IOException {
        //TODO:  fill in
    }
    
    public static void writeColumnsToFile(List<List> data, String filename, String header, String separator) throws IOException {
        if(data == null) {
            return;
        }
        if(separator == null || separator.equals("")) {
            return;
        }
        PrintWriter writer = new PrintWriter(new FileWriter(filename));
        if(header != null && !header.equals("")) {
            writer.println(header);
        }
        int[] dim = ListArrayUtil.dim(data, false);
        int numRows = dim[0];
        int numCols = dim[1];
        StringBuilder row = new StringBuilder();
        for(int j = 0; j < numRows; j++) {
            row = new StringBuilder();
            for(int i = 0; i < numCols; i++) {
                if(data.get(i) == null || data.get(i).get(j) == null) {
                    row.append("").append(separator);
                } else {
                    row.append(data.get(i).get(j)).append(separator);
                }
            }
            row.append("\n");
            writer.print(row.toString());
        }
        writer.flush();
        writer.close();
    }
}
