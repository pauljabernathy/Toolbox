/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package toolbox.stats;

import java.util.Arrays;
import java.util.List;
import java.util.ArrayList;

/**
 *
 * @author paul
 */
public class Utilities {
    
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
