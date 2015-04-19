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
        //TODO: bitwise calculation if possible
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
    
    public static Summary summary(int[] array) {
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
    
    public static Summary summary(List input) {
        if(input == null || input.isEmpty()) {
            return new Summary();
        }
        List<Double> list = new ArrayList<Double>();
        for(int i = 0; i < input.size(); i++) {
            if(input.get(i) instanceof Double) {
                list.add((Double)input.get(i));
            } else if(input.get(i) instanceof Integer) {
                //list.add(new Double(input.get(i)));
                Integer value = (Integer)input.get(i);
                list.add(new Double(value));
            } else if(input.get(i) instanceof Long) {
                //list.add(new Double(input.get(i)));
                Long value = (Long)input.get(i);
                list.add(new Double(value));
            } else if(input.get(i) instanceof Short) {
                //list.add(new Double(input.get(i)));
                Short value = (Short)input.get(i);
                list.add(new Double(value));
            } else {
                //dont' add it
            }
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
    
    //TODO:  how to handle null and empty input
    public static int min(int[] array) {
        int min = array[0];
        for(int i = 1; i < array.length; i++) {
            if(array[i] < min) {
                min = array[i];
            }
        }
        return min;
    }
    
    //TODO:  how to handle null and empty input
    public static int max(int[] array) {
        int max = array[0];
        for(int i = 1; i < array.length; i++) {
            if(array[i] > max) {
                max = array[i];
            }
        }
        return max;
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
    
    //TODO:  complete, and test
    public static double sum(List<Double> list, int...endPoints) {
        if(list == null || list.isEmpty()) {
            return 0;
        }
        double sum = 0.0;
        for(Double d : list) {
            sum += d;
        }
        return sum;
    }
    
    /**
     * Gives the sum bounded by the (optional) endpoints.  If the endpoints are reversed, it reverses them.
     * @param input the array to take the sum of
     * @param endPoints (optional) the first and last indeces to include in the calculation
     * @return the sum of the array
     */
    public static double sum(double[] input, int...endPoints) {
        if(input == null || input.length == 0) {
            return 0;
        }
        endPoints = checkEndPoints(0, input.length - 1, endPoints);
        int start = endPoints[0];
        int end = endPoints[1];
        double sum = 0.0;
        for(int i = start; i <= end; i++) {
            sum += input[i];
        }
        return sum;
    }
    
    /**
     * Gives the sum bounded by the (optional) endpoints.  If the endpoints are reversed, it reverses them.
     * @param input the array to sum up
     * @param endPoints (optional) the first and last elements to sum
     * @return the sum of the array
     */
    public static int sum(int[] input, int...endPoints) {
        if(input == null || input.length == 0) {
            return 0;
        }
        endPoints = checkEndPoints(0, input.length - 1, endPoints);
        int start = endPoints[0];
        int end = endPoints[1];
        int sum = 0;
        for(int i = start; i <= end; i++) {
            sum += input[i];
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
     * @param list the list to take the product of
     * @param start the first element to use
     * @param end the last element to use
     * @return the product of multiplying all the given elements
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
    
    public static double prod(double[] input) {
        if(input == null || input.length == 0) {
            return 0.0;
        }
        double sum = 1.0;
        for(Double d : input) {
            sum *= d;
        }
        return sum;
    }
    
    /**
     * calculates the product of the input, from the start of the index labeled "start" to the end of the index labeled "end"
     * If start and end are beyond the bounds of the input (less than 0 or greater than input.size() - 1), it adjusts start and end to fit the bounds of the input.
     * If start is greater than end, start and end are switched.
     * @param input the array to take the product of
     * @param start the first element to use
     * @param end the last element to use
     * @return the product
     */
    public static double prod(double[] input, int start, int end) {
        if(input == null || input.length == 0) {
            return 0.0;
        }
        int[] endPoints = checkEndPoints(0, input.length - 1, start, end);
        start = endPoints[0];
        end = endPoints[1];
        double sum = 1.0;
        for(int i = start; i <= end; i++) {
            sum *= input[i];
        }
        return sum;
    }
    
    public static double[] cumProd(double[] input, boolean...reverse) {
        if(input == null || input.length == 0) {
            return new double[0];
        }
        double[] result = new double[input.length];
        boolean backwards = false;
        if(reverse != null && reverse.length > 0) {
            backwards = reverse[0];
        }
        if(!backwards) {
            int start = 0;
            int end = input.length - 1;
            result[start] = input[start];
            for(int i = start + 1; i <= end; i++) {
                result[i] = result[i - 1] * input[i];
            }
        } else {
            int start = input.length - 1;
            int end = 0;
            result[input.length - 1] = input[input.length - 1];
            for(int i = input.length - 2; i >= 0; i--) {
                result[i] = result[i + 1] * input[i];
            }
        }
        return result;
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
     * @param input the array to take the difference ratios of
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
     * @param input the array to take the standard deviation of
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
    
    public static double sd(int[] input) {
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
     
     public static int[] checkEndPoints(int defaultLower, int defaultUpper, int...endPoints) {
        //int[] result = new int[] { 0, 1 };
        int lower = defaultLower;
        int upper = defaultUpper;
        if(defaultLower > defaultUpper) {
            lower = defaultUpper;
            upper = defaultLower;
        }
        if(endPoints == null || endPoints.length == 0) {
            return new int[] { lower, upper };
        }
        //not null and not empty
        if(endPoints.length == 1 & endPoints[0] > lower) {
            upper = endPoints[0];
        } else if(endPoints.length == 1 & endPoints[0] < upper) {
            lower = endPoints[0];
        } else if(endPoints.length > 1) {
            if(endPoints[1] < endPoints[0]) {
                //the user put them in reverse order
                lower = endPoints[1];
                upper = endPoints[0];
            } else if(endPoints[0] <= endPoints[1]) {
                lower = endPoints[0];
                upper = endPoints[1];
            } 
        }
        if(lower < defaultLower) {
            lower = defaultLower;
        }
        if(upper > defaultUpper) {
            upper = defaultUpper;
        }
        if(lower > defaultUpper) {
            lower = defaultLower;
        }
        if(upper < defaultLower) {
            upper = defaultUpper;
        }
        if(lower > upper) {
            int temp = upper;
            upper = lower;
            lower = temp;
        }
        return new int[] { lower, upper };
    }
    
    public static double setDecimalPlaces(double amount, int places) {
        if(places < 1) {
            return amount;
        }
        return (double)((int)(amount * Math.pow(10, places))) / Math.pow(10, places);
    }
    
    //TODO:  seq double functions
    
    public static int[] seqInt(int start, int end) {
        int length = Math.abs(end - start) + 1;
        int[] result = new int[length];
        if(start < end) {
            for(int i = 0; i < length; i++) {
                result[i] = i + start;
            }
        } else if(start == end){
            result = new int[] { start };
        } else {
            for(int i = length - 1; i >= 0; i--) {
                result[i] = start - i;
            }
        }
        return result;
    }
    
    public static int[] seqInt(int start, int end, int increment) {
        return null;
    }
    
    public static Integer[] seqInteger(int start, int end) {
        int length = Math.abs(end - start) + 1;
        Integer[] result = new Integer[length];
        if(start < end) {
            for(int i = 0; i < length; i++) {
                result[i] = i + start;
            }
        } else if(start == end){
            result = new Integer[] { start };
        } else {
            for(int i = length - 1; i >= 0; i--) {
                result[i] = start - i;
            }
        }
        return result;
    }
    
    //TODO: unit test
    public static double[] intToDouble(int[] input) {
        if(input == null) {
            return new double[0];
        }
        int length = input.length;
        double[] output = new double[length];
        for(int i = 0; i < length; i++) {
            output[i] = (double)input[i];
        }
        return output;
    }
}
