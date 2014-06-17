/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package toolbox.util;

import java.util.Arrays;
import java.util.List;
import java.util.ArrayList;
import toolbox.stats.Summary;

/**
 *
 * @author paul
 */
public class MathUtil {
    
    /**
     * @param x The number to take the logarithm of.
     * @return The base two logarithm of x, using the formula return value = 3.32193 * Math.log10(x).  Apparently, Java does not have a built in function for base two logarithm.
     */
    public static double logBase2(double x) {
        return 3.3219281 * Math.log10(x);
    }
    
    public static Summary summary(double[] array) {
        Arrays.sort(array);
        Summary s = new Summary();
        s.min = array[0];
        //TODO: firstQ and thirdQ
        if(array.length % 2 == 1) {
            s.median = array[array.length / 2];
        } else {
            s.median = .5 * array[array.length / 2 - 1] + .5 * array[array.length / 2];
        }
        s.mean = mean(array);
        s.max = array[array.length - 1];
        s.sd = sd(array);
        return s;
    }
    
    public static Summary summary(List<Double> list) {
        if(list == null || list.isEmpty()) {
            return new Summary();
        }
        Summary s = new Summary();
        java.util.Collections.sort(list);
        s.min = list.get(0);
        if(list.size() % 2 == 1) {
            s.median = list.get(list.size() / 2);
        } else {
            s.median = .5 * list.get(list.size() / 2 - 1) + .5 * list.get(list.size() / 2);
        }
        s.mean = mean(list);
        s.max = list.get(list.size() - 1);
        s.sd = sd(list);
        return s;
    }
    
    public static double sum(List<Double> list) {
        if(list == null || list.isEmpty()) {
            return 0;
        }
        double sum = 0.0;
        for(Double d : list) {
            sum += d;
        }
        return sum;
    }
    
    public static double[] cumsum(double[] input) {
        //TODO:  fill in
        return new double[] {};
    }
    
    public static double[] cumum(List<Double> input) {
        //TODO:  fill in
        return new double[]{};
    }
    
    public static int[] cumsum(int[] input) {
        //TODO:  fill in
        return null;
    }
    
    public static int[] cumsum(List<Integer> input) {
        //TODO:  fill in
        return null;
    }
    
    public static List<Double> cumsumList(double[] input) {
        //TODO:  fill in
        return null;
    }
    
    public static List<Double> cumsumList(List<Double> input) {
        if(input == null || input.isEmpty()) {
            return new ArrayList<Double>();
        }
        double sum = 0.0;
        ArrayList<Double> result = new ArrayList<Double>();
        for(Double d : input) {
            sum += d;
            result.add(sum);
        }
        return result;
    }
    
    public static List<Integer> cumsumList(int[] input) {
        //TODO:  fill in
        return null;
    }
    
    /*public static List<Integer> cumsumList(List<Integer> input) {
        //TODO:  fill in
        return null;
    }*/
    
    public static double prod(List<Double> list) {
        if(list == null || list.isEmpty()) {
            return 0.0;
        }
        double sum = 1.0;
        for(Double d : list) {
            sum *= d;
        }
        return sum;
    }
    
    /**
     * calculates the product of the list, from the start of the index labeled "start" to the end of the index labeled "end"
     * If start and end are beyond the bounds of the input (less than 0 or greater than list.size() - 1), it adjusts start and end to fit the bounds of the input.
     * If start is greater than end, start and end are switched.
     * @param list
     * @param start
     * @param end
     * @return 
     */
    public static double prod(List<Double> list, int start, int end) {
        if(list == null || list.isEmpty()) {
            return 0.0;
        }
        if(start > end) {
            int temp = start;
            start = end;
            end = temp;
        }
        if(start < 0) {
            start = 0;
        }
        if(end > list.size() - 1) {
            end = list.size() - 1;
        }
        
        double sum = 1.0;
        for(int i = start; i <= end; i++) {
            sum *= list.get(i);
        }
        return sum;
    }
    
    public static List<Double> diffRatiosList(List<Double> input) {
        if (input == null || input.size() <= 1) {
            return new ArrayList<Double>();
        }
        ArrayList<Double> result = new ArrayList<Double>();
        for (int i = 1; i < input.size(); i++) {
            result.add(input.get(i) / input.get(i - 1));
        }
        return result;
    }

    public static List<Double> diffRatiosList(double[] input) {
        if (input == null || input.length <= 1) {
            return new ArrayList<Double>();
        }
        ArrayList<Double> result = new ArrayList<Double>();
        for (int i = 1; i < input.length; i++) {
            result.add(input[i] / input[i - 1]);
        }
        return result;
    }

    public static double[] diffRatios(List<Double> input) {
        if (input == null || input.size() <= 1) {
            return new double[]{};
        }
        double[] result = new double[input.size() - 1];
        for (int i = 1; i < input.size(); i++) {
            result[i - 1] = input.get(i) / input.get(i - 1);
        }
        return result;
    }

    //TODO:  what to do with elements of 0? the JVM records a value of "Infinity" instead of throwing an exception, but this may not be what we want...
    //Should the user check for that or not?
    /**
     * Calculates the ratios of each element of the input to the previous element.  For example, if the input is 1.0, 2.0, 1.0, 5.0, the result would be 2.0, 0.5, 5.0.
     * Currently does not check for 0s, so the user should check for it and act accordingly.
     * @param input
     * @return an array of length one less than the input length
     */
    public static double[] diffRatios(double[] input) {
        if (input == null || input.length <= 1) {
            return new double[]{};
        }
        double[] result = new double[input.length - 1];
        for (int i = 1; i < input.length; i++) {
            result[i - 1] = input[i] / input[i - 1];
        }
        return result;
    }
    
    public static double mean(double[] input) {
        if(input == null || input.length == 0) {
            return 0.0;
        }
        double sum = 0.0;
        for(int i = 0; i < input.length; i++) {
            sum += input[i];
        }
        return sum / input.length;
    }
    
    public static double mean(List<Double> input) {
        if(input == null || input.isEmpty()) {
            return 0.0;
        }
        double sum = 0.0;
        for(int i = 0; i < input.size(); i++) {
            sum += input.get(i);
        }
        return sum / input.size();
    }
    
    public static double mean(int[] input) {
        if(input == null || input.length == 0) {
            return 0.0;
        }
        double sum = 0.0;
        for(int i = 0; i < input.length; i++) {
            sum += input[i];
        }
        return (double)sum / (double)input.length;
    }
    
    /**
     * Gives the sample standard deviation (divide by n - 1 instead of n)
     * @param input
     * @return 
     */
    public static double sd(double[] input) {
        if(input == null || input.length == 0 || input.length == 1) {
            return 0.0;
        }
        double mean = mean(input);
        double[] squaredDiffs = new double[input.length];
        for(int i = 0; i < input.length; i++) {
            squaredDiffs[i] = Math.pow((input[i] - mean), 2.0);
        }
        double sum = 0.0;
        for(double d : squaredDiffs) {
            sum += d;
        }
        return Math.sqrt(sum / (double)(squaredDiffs.length - 1));
    }
    
     public static double sd(List<Double> input) {
         if(input == null || input.size() == 0 || input.size() == 1) {
             return 0.0;
         }
         double mean = mean(input);
         List<Double> squaredDiffs = new ArrayList<Double>();
         for(int i = 0; i < input.size(); i++) {
            squaredDiffs.add(Math.pow((input.get(i) - mean), 2.0));
        }
         double sum = 0.0;
        for(double d : squaredDiffs) {
            sum += d;
        }
        return Math.sqrt(sum / (double)(squaredDiffs.size() - 1));
     }
}
