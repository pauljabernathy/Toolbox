/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package toolbox.io;

import toolbox.Constants;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import toolbox.stats.Histogram;
import toolbox.stats.ProbDist;
import toolbox.information.Shannon;

import toolbox.stats.*;
import toolbox.util.ListArrayUtil;
import toolbox.util.MathUtil;

/**
 *
 * @author paul
 */
public class CSVReader {

    
    public static List<? extends List> getRowsAsLists(String filename, int maxLines) {
        List<ArrayList> result = new ArrayList<ArrayList>();
        int lineNum = -1;
        String line = "";
        try {
            BufferedReader reader = new BufferedReader(new FileReader(filename));
            if (reader.ready()) {
                reader.readLine();
            }
            while (reader.ready() & lineNum < maxLines) {
                lineNum++;
                line = reader.readLine();
                String[] array = line.split(Constants.DEFAULT_SEPARATOR);
                if (array == null) {
                    System.out.println("null array at line " + lineNum + ":  " + line);
                } else if (array.length == 0) {
                    System.out.println("empty array at line " + lineNum + ":  " + line);
                } else {
                    ArrayList<String> current = new ArrayList<String>();
                    for (String s : array) {
                        current.add(s);
                    }
                    result.add(current);
                }
            }
        } catch (IOException e) {
            System.out.println(e.getClass() + " in classify(" + filename + ") at line " + lineNum + ":  " + e.getMessage());
        }
        return result;
    }
    
    public static List<List<String>> getRowsAsLists(String filename, int[] columns, int maxLines) throws IOException {
        List<List<String>> result = new ArrayList<List<String>>();
        if(filename == null || filename.equals("")) {
            return result;
        }
        int lineNum = 0;
        String line = "";
        //try {
            BufferedReader reader = new BufferedReader(new FileReader(filename));
            if (reader.ready()) {
                reader.readLine();
            }
            while (reader.ready() & lineNum < maxLines) {
                //lineNum++;
                line = reader.readLine();
                String[] array = line.split(Constants.DEFAULT_SEPARATOR);
                if (array == null) {
                    System.out.println("null array at line " + lineNum + ":  " + line);
                } else if (array.length == 0) {
                    System.out.println("empty array at line " + lineNum + ":  " + line);
                } else {
                    ArrayList<String> current = new ArrayList<String>();
                    for (int i = 0; i < array.length; i++) {
                        if(ListArrayUtil.contains(columns, i)) {
                            current.add(array[i]);
                        }
                    }
                    //TODO:  maybe an AND operation on the two arrays
                    result.add(current);
                }
                lineNum++;
            }
        /*} catch (IOException e) {
            System.out.println(e.getClass() + " in classify(" + filename + ") at line " + lineNum + ":  " + e.getMessage());
        }*/
        return result;
    }
    
    /*public static List<String> getRowsAsStrings(String filename, int maxLines) throws IOException {
        List<String> result = new ArrayList<String>();
        BufferedReader reader = new BufferedReader(new FileReader(filename));
        int lineCount = 0;
        String line = "";
        while(reader.ready() && lineCount < maxLines) {
            line = reader.readLine();
            if(line != null) {
                result.add(line);
            }
        }
        return result;
    }*/
/*
    public static ProbDist<Classification> getDistributions(String filename, int classColumn, int[] featureColumns, String columnSeparator) throws IOException {
        ProbDist<String> classDist = getClassificationDists(filename, classColumn, columnSeparator);
        List<String> classNames = classDist.getValues();
        List<ProbDist> dists = null;
        ProbDist<Classification> result = new ProbDist<Classification>();
        for (String currentName : classNames) {
            try {//TODO: remove magic values
                dists = getFeatureDists("titanic.csv", 1, currentName, featureColumns, Constants.DEFAULT_SEPARATOR);
                Classification c = new Classification(currentName);
                c.setFeatureDists(dists);
                result.add(c, classDist.probatilityOf(currentName));
            } catch (IOException e) {
                throw e;
            } catch (Exception e) {
                System.err.println(e.getClass() + " " + e.getMessage());
            }
        }
        return result;
    }
    */

    public static String parseLine(String line, int column, String columnSeparator) {
        if (column == -1) {
            return Constants.UNKNOWN;
        }
        int[] dummy = new int[1];
        dummy[0] = column;
        List<String> value = parseLine(line, dummy, columnSeparator);
        if (value != null && value.size() > 0) {
            return value.get(0);
        } else {
            return Constants.UNKNOWN;
        }
    }
    
    /**
     *
     * @param line the lines of the csv file, which should be in csv format
     * @param columns an array of column numbers to read
     * @param columnSeparator what separates columns (most likely a comma) must be an non empty String; will return an empty List if this is null or empty
     * @return a list of String, each element being the value of input line at the index of the corresponding column from the columns array
     */
    public static List<String> parseLine(String line, int[] columns, String columnSeparator) {
        ArrayList<String> row = new ArrayList<String>();
        if (line == null || columns == null || columns.length < 1 || columnSeparator == null || columnSeparator.equals("")) {
            return row;
        }
        String[] vars = line.split(columnSeparator);
        if (vars == null || vars.length - 1 < MathUtil.max(columns)) {
            return row;
        }
        for (int column : columns) {
            row.add(scrub(vars[column]));
        }
        return row;
    }
    
    /**
     *
     * @param filename the name of the file
     * @param classColumn the index of the column you are trying to classify
     * @param classification the name of the classification you are looking at
     * @param featureColumns a list of which columns will be used to classify the column indexed by classColumn
     * @param columnSeparator what separates columns; typically a comma
     * @return a list of the distributions for the given classification name
     * @throws IOException if the file cannot be read
     */
    public static List<ProbDist> getFeatureDists(String filename, int classColumn, String classification, int[] featureColumns, String columnSeparator) throws IOException {
        verifyParameters(filename, classColumn, classification, featureColumns, columnSeparator);
        List<Histogram> histList = getFeatureHistograms(filename, classColumn, classification, featureColumns, columnSeparator);
        ArrayList<ProbDist> result = new ArrayList<ProbDist>();
        for (Histogram hist : histList) {
            ProbDist dist = hist.getProbDist();
            result.add(dist);
        }
        return result;
    }

    public static List<Histogram> getFeatureHistograms(String filename, int classColumn, String classification, int[] featureColumns, String columnSeparator) throws IOException {
        verifyParameters(filename, classColumn, classification, featureColumns, columnSeparator);
        List<List<String>> data = (List<List<String>>) parseFile(filename, classColumn, classification, featureColumns, columnSeparator);
        ArrayList<Histogram> result = new ArrayList<Histogram>();
        for (int i = 0; i < featureColumns.length; i++) {
            Histogram hist = new Histogram(data.get(i));
            result.add(hist);
        }
        return result;
    }

    //TODO: change name or move to the project that uses this
    /**
     *
     * @param filename the name of the file
     * @param classColumn the index of the column you are trying to classify
     * @param classification the name of the classification you are looking at
     * @param featureColumns a list of which columns will be used to classify the column indexed by classColumn
     * @param columnSeparator what separates columns; typically a comma
     * @return a two dimensional list containing the data from the specified columns when the classification name matched the specified classification name
     * @throws IOException if the file cannot be read
     */
    public static List<? extends List<String>> parseFile(String filename, int classColumn, String classification, int[] featureColumns, String columnSeparator) throws IOException {
        verifyParameters(filename, classColumn, classification, featureColumns, columnSeparator);
        BufferedReader reader = new BufferedReader(new FileReader(filename));
        if (reader.ready()) {
            reader.readLine();
        }
        ArrayList<ArrayList<String>> data = new ArrayList<ArrayList<String>>();
        for (int i = 0; i < featureColumns.length; i++) {
            data.add(new ArrayList<String>());
        }
        String line = "";
        List<String> curFeatureValues = null;
        String className = "";
        while (reader.ready()) {
            line = reader.readLine();
            curFeatureValues = parseLine(line, featureColumns, columnSeparator);
            className = parseLine(line, classColumn, columnSeparator);
            for (int i = 0; i < featureColumns.length; i++) {
                if (classification.equals(className)) {
                    data.get(i).add(curFeatureValues.get(i));
                }
            }
        }
        return data;
    }

    public static boolean verifyParameters(String filename, int classColumn, String classification, int[] featureColumns, String columnSeparator) throws IOException {
        if(filename == null) {
            throw new IOException(Constants.FILENAME_CANNOT_BE_NULL);
        } else if(filename.equals("")) {
            throw new IOException(Constants.FILENAME_CANNOT_BE_EMPTY);
        } else if (featureColumns == null) {
            throw new IOException(Constants.FEATURE_COLUMNS_CANNOT_BE_NULL);
        } else if(featureColumns.length == 0) {
            throw new IOException(Constants.FEATURE_COLUMNS_CANNOT_BE_EMPTY);
        }
        return true;
    }
    
    /**
     * Returns a single column from the csv file.
     * @param filename the name of the file
     * @param column the index of the column you are getting
     * @param columnSeparator what separates columns (probably a comma)
     * @return a DataList representing the one column
     * @throws IOException if the file cannot be read
     */
    public static DataList getSingleColumn(String filename, int column, String columnSeparator) throws IOException {
        if(filename == null || filename.equals("") || column < 0 || columnSeparator == null || columnSeparator.equals("")) {
            return new DataList<String>();
        }
        BufferedReader reader = new BufferedReader(new FileReader(filename));
        reader.readLine();//for header
        String line = "";
        String value = "";
        DataList<String> data = new DataList<String>();
        while(reader.ready()) {
            line = reader.readLine();
            value = parseLine(line, column, columnSeparator);
            data.add(value);
        }
        return data;
    }
    
    /**
     * Returns the specified columns of the csv file, where each List of Strings is a column, instead of a row as is typical of database queries.
     * @param filename the name of the file
     * @param columns the index of the column you are getting
     * @param columnSeparator what separates columns (probably a comma)
     * @return a List of Lists of Strings, each list representing one column
     * @throws IOException if the file cannot be read
     */
    public static List<List<String>> getColumns(String filename, int[] columns, String columnSeparator) throws IOException {
        if(filename == null || filename.equals("") || columns == null || columns.length < 1 || columnSeparator == null || columnSeparator.equals("")) {
            return new ArrayList<List<String>>();
        }
        for(int i = 0; i < columns.length; i++) {
            if(columns[i] < 0) {
                return new ArrayList<List<String>>();
            }
        }
        BufferedReader reader = new BufferedReader(new FileReader(filename));
        reader.readLine();//for header
        String line = "";
        String value = "";
        List<String> currentValues = null;
        ArrayList<List<String>> data = new ArrayList<List<String>>();
        for(int i = 0; i < columns.length; i++) {
            data.add(new ArrayList<String>());
        }
        while(reader.ready()) {
            line = reader.readLine();
            currentValues = parseLine(line, columns, columnSeparator);
            if(currentValues.size() != columns.length) {
                //TODO:  what to do here?
                //should always be the same
            }
            for(int i = 0; i < currentValues.size() && i < columns.length; i++) {
                 data.get(i).add(currentValues.get(i));
            }
        }
        reader.close();
        return data;
    }
    
    /** gets all columns, where each List of Strings is a column, instead of a row as is typical of database queries.
     * @param filename the name of the file
     * @param columnSeparator what separates columns (probably a comma)
     * @return a List of Lists of Strings, each list representing one column
     * @throws IOException if the file cannot be read
     */
    public static List<List<String>> getAllColumns(String filename, String columnSeparator) throws IOException {
        if(filename == null || filename.equals("") || columnSeparator == null || columnSeparator.equals("")) {
            return new ArrayList<List<String>>();
        }
        
        BufferedReader reader = new BufferedReader(new FileReader(filename));
        reader.readLine();//to skip header
        String line = reader.readLine();
        String[] tokens = line.split(columnSeparator);
        for(int i = 0; i < tokens.length; i++) {
            System.out.println(tokens[i]);
        }
        int numColumns = tokens.length;
        int[] columns = new int[numColumns];
        for(int i = 0; i < numColumns; i++) {
            columns[i] = i;
        }
        reader.close();
        return getColumns(filename, columns, columnSeparator);
    }
    
    /**
     * Gets a histogram for a single variable (column) for the entire file.  Returns and empty histogram if the filename or column separator are null or empty or if the column is less than 0.
     * @param filename the name of the file
     * @param column the index of the column to get a Histogram of
     * @param columnSeparator what separates columns (probably a comma)
     * @return a Histogram of the data in the given column
     * @throws IOException if the file cannot be read
     */
    public static Histogram getSingleHistogram(String filename, int column, String columnSeparator) throws IOException {
        
        if(filename == null || filename.equals("") || column < 0 || columnSeparator == null || columnSeparator.equals("")) {
            return new Histogram();
        }
        BufferedReader reader = new BufferedReader(new FileReader(filename));
        reader.readLine();//for header
        String line = "";
        String value = "";
        ArrayList<String> data = new ArrayList<String>();
        while(reader.ready()) {
            line = reader.readLine();
            value = parseLine(line, column, columnSeparator);
            data.add(value);
        }
        Histogram h = new Histogram(data);
        return h;
    }
    
    public static ProbDist getSingleDistribution(String filename, int column, String columnSeparator) throws IOException {
        return getSingleHistogram(filename, column, columnSeparator).getProbDist();
    }
    
    public static Histogram getJointHistogram(String filename, int[] columns, String columnSeparator) throws IOException {
        if(filename == null || filename.equals("") || columns.length < 0 || columnSeparator == null || columnSeparator.equals("")) {
            return new Histogram();
        }
        for(int i = 0; i < columns.length; i++) {
            if(columns[i] < 0) {
                return new Histogram();
            }
        }
        BufferedReader reader = new BufferedReader(new FileReader(filename));
        reader.readLine();//for header
        String line = "";
        List<String> values = null;
        ArrayList<List<String>> data = new ArrayList<List<String>>();
        while(reader.ready()) {
            line = reader.readLine();
            values = parseLine(line, columns, columnSeparator);
            data.add(values);
        }
        return new Histogram(data);
    }
    
    public static ProbDist getJointDistribution(String filename, int[] columns, String columnSeparator) throws IOException {
        return getJointHistogram(filename, columns, columnSeparator).getProbDist();
    }

    public static double getMutualInformation(String filename, int column1, int column2, String columnSeparator) throws IOException {
        /*double HXY = getJointDistribution(filename, new int[] { column1, column2}, columnSeparator).getEntropy();
        //System.out.println("HXY = " + HXY);
        double HX = getSingleDistribution(filename, column1, columnSeparator).getEntropy();
        //System.out.println("HX = " + HX);
        double HY = getSingleDistribution(filename, column2, columnSeparator).getEntropy();
        //System.out.println("HY = " + HY);
        return HX + HY - HXY;*/
        DataList col1 = getSingleColumn(filename, column1, columnSeparator);
        DataList col2 = getSingleColumn(filename, column2, columnSeparator);
        return Shannon.getMutualInformation(col1.getData(), col2.getData());
    }
    
    protected static String scrub(String input) {
        return input.replace("\"", "");
    }
    
}
