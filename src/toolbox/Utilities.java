/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package toolbox;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.*;

/**
 *
 * @author paul
 */
public class Utilities {
    
    public static void showList(List input) {
        if(input == null) {
            return;
        }
        for(Object current : input) {
            System.out.println(current);
        }
    }
    
    public static String listToString(List input) {
        if(input == null || input.isEmpty()) {
            return "";
        }
        StringBuilder sb = new StringBuilder();
        for(Object current : input) {
            sb.append(current.toString()).append(" ");
        }
        return sb.toString();
    }
    public static void showArray(int[] a) {
        if(a == null) {
            return;
        }
        for(int i = 0; i < a.length; i++) {
            System.out.print(a[i] + " ");
        }
        //System.out.println(a[a.length - 1]);
        System.out.println();
    }
    
    public static String arrayToString(int[] a) {
        StringBuilder s = new StringBuilder();
        if(a == null || a.length == 0) {
            return s.toString();
        }
        s.append("{ ");
        for(int i = 0; i < a.length - 1; i++) {
            s.append(a[i]).append(", ");
        }
        s.append(a[a.length - 1]).append(" }");
        return s.toString();
    }
    
    public static String arrayToString(double[] a) {
        StringBuilder s = new StringBuilder();
        if(a == null || a.length == 0) {
            return s.toString();
        }
        s.append("{ ");
        for(int i = 0; i < a.length - 1; i++) {
            s.append(a[i]).append(", ");
        }
        s.append(a[a.length - 1]).append(" }");
        return s.toString();
    }
    
    public static <T> String arrayToString(T[] a) {
        StringBuilder s = new StringBuilder();
        if(a == null || a.length == 0) {
            return s.toString();
        }
        s.append("{ ");
        for(int i = 0; i < a.length - 1; i++) {
            s.append(a[i]).append(", ");
        }
        s.append(a[a.length - 1]).append(" }");
        return s.toString();
    }
    
    public static boolean contains(String[] array, String value) {
        if(array == null || array.length == 0) {
            return false;
        }
        for(String current : array) {
            if(value != null && current != null && current.equals(value)) {
                return true;
            } else if(value == null && current == null) {
                return true;
            }
        }
        return false;
    }
    
    //TODO:  there is a defect here where if 0 is included in the array, the combinations where it should be will be removed even if the user wants zero there
    //this is due to the fact that this uses a logical AND as part of the process
    public static List<int[]> getCondensedPermutations(int[] input) {
        if(input == null || input.length == 0) {
            return new ArrayList<int[]>();
        }
        //int exponent = (int)logBase2(input.length) - 1;
        int from = (int)Math.pow(2, input.length - 2);
        int to = (int)Math.pow(2, input.length );
        ArrayList<int[]> result = new ArrayList<int[]>();
        for(int i = 1; i < to; i++) {         
            //Utilities.showArray(Utilities.toBinaryArray(i, input.length));
            //Utilities.showArray(Utilities.and(input, Utilities.toBinaryArray(i, input.length)));
            result.add(Utilities.andAndCondense(input, Utilities.toBinaryArray(i, input.length)));
        }
        return result;
    }
    
    public static int[][] getPermutations(int[] input, int num) {
        int[][] permutations = getPermutations(input);
        
        return null;
    }

    public static int[][] getPermutations(int[] input) {
        if(input == null || input.length == 0) {
            return new int[0][0];
        }
        //int exponent = (int)logBase2(input.length) - 1;
        int from = (int)Math.pow(2, input.length - 2);
        int to = (int)Math.pow(2, input.length );
        int[][] result = new int[to][input.length];
        for(int i = 0; i < to; i++) {
            result[i] = Utilities.and(input, Utilities.toBinaryArray(i, input.length));
        }
        return result;
    }
    
    public static int[] toBinaryArray(int source) {
        return toBinaryArray(source, 1);
    }

    /**
     * Converts the given integer into the binary representation given as an array of 1s and 0s
     * @param source the number to convert into binary
     * @param minLength the minimum length of the array (in case leading zeros are wanted)
     * @return an array of 1s and 0s
     */
    public static int[] toBinaryArray(int source, int minLength) {
        if (source < 0) {
            return new int[minLength];
        } else if (source == 0) {
            return new int[minLength];
        }
        double log = logBase2(source);
        int exp = (int) log;
        if (exp < minLength - 1) {
            exp = minLength - 1;
        }
        int length = (int) exp + 1;
        int[] result = new int[length];
        //Utilities.showArray(result);
        int index = 1;
        int remainder = source - (int) Math.pow(2.0, (int) exp);
        if ((int) Math.pow(2.0, exp) <= source) {
            result[0] = 1;
        } else {
            result[0] = 0;
            remainder = source;
        }
        exp--;
        while (exp >= 0) {
            if ((int) Math.pow(2.0, exp) <= remainder) {
                result[index] = 1;
                remainder = remainder - (int) Math.pow(2, exp);
            } else {
                result[index] = 0;
            }
            index++;
            exp--;
        }
        return result;
    }

    /**
     * 
     * @param x The number to take the logarithm of.
     * @return The base two logarithm of x, using the formula return value = 3.32193 * Math.log10(x).  Apparently, Java does not have a built in function for base two logarithm.
     */
    public static double logBase2(double x) {
        return 3.3219281 * Math.log10(x);
    }

    /**
     * 
     * @param source
     * @param bits
     * @return The first array ("source") anded with the second ("bit").  For each index, if either element is 0, zero is placed at that index in the return array.  If both elements are non zero, that element will be the value of source at that index.
     * If the arrays do not have the same length or either is null, an empty array is returned.
     */
    public static int[] and(int[] source, int[] bits) {
        if (source == null || bits == null || source.length != bits.length) {
            return new int[]{};
        }
        int[] result = new int[source.length];
        for (int i = 0; i < source.length; i++) {
            if (bits[i] == 0) {
                result[i] = 0;
            } else {
                result[i] = source[i];
            }
        }
        return result;
    }

    /**
     * 
     * @param source
     * @param bits
     * @return the source array anded with the bits array (as in and()) but omitting elements with zero as the value.
     */
    public static int[] andAndCondense(int[] source, int[] bits) {
        if (source == null || bits == null || source.length != bits.length) {
            return new int[]{};
        }
        int[] anded = and(source, bits);
        ArrayList<Integer> list = new ArrayList<Integer>();
        for (int i = 0; i < anded.length; i++) {
            if (anded[i] != 0) {
                list.add(anded[i]);
            }
        }
        int[] result = new int[list.size()];
        for (int j = 0; j < list.size(); j++) {
            result[j] = list.get(j);
        }
        return result;
    }
    
    public static int[] or(int[] left, int[] right) {
        if((left == null || left.length == 0) && (right == null || right.length == 0)) {
                return new int[0];
        }
        int length = (left.length > right.length)?left.length:right.length;
        System.out.println("length should be " + length);
        int[] result = new int[length];
        int[] temp = new int[length];
        if(left.length < length) {
                for(int i = length - left.length; i < length; i++) {
                        temp[i] = left[i - (right.length - left.length)];
                }
                left = temp;
        } else {
                for(int i = length - right.length; i < length; i++) {
                        temp[i] = right[i - (left.length - right.length)];
                }
                right = temp;
        }

        for(int i = 0; i < length; i++) {
                if(left[i] > 0 || right[i] > 0) {
                        result[i] = 1;
                }
        }
        return result;
    }
    
    public static Logger getLogger(Class clazz, Level level) {
        Logger logger = Logger.getLogger(clazz);
        logger.addAppender(new ConsoleAppender(new PatternLayout(Constants.DEFAULT_LOG_FORMAT)));
        logger.setLevel(level);
        return logger;
    }
    
    public static Logger getSameLineLogger(Class clazz, Level level) {
        Logger logger = Logger.getLogger(clazz + " same line");
        logger.addAppender(new ConsoleAppender(new PatternLayout(Constants.SAME_LINE_LOG_FORMAT)));
        logger.setLevel(level);
        return logger;
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
        if(input == null || input.length <= 1) {
            return new double[] {};
        }
        double[] result = new double[input.length - 1];
        for(int i = 1; i < input.length; i++) {
            result[i - 1] = input[i] / input[i - 1];
        }
        return result;
    }
    
    public static double[] diffRatios(List<Double> input) {
        if(input == null || input.size() <= 1) {
            return new double[] {};
        }
        double[] result = new double[input.size() - 1];
        for(int i = 1; i < input.size(); i++) {
            result[i - 1] = input.get(i) / input.get(i - 1);
        }
        return result;
    }
    
    public static List<Double> diffRatiosList(double[] input) {
        if(input == null || input.length <= 1) {
            return new ArrayList<Double>();
        }
        ArrayList<Double> result = new ArrayList<Double>();
        for(int i = 1; i < input.length; i++) {
            result.add(input[i] / input[i - 1]);
        }
        return result;
    }
    
    public static List<Double> diffRatiosList(List<Double> input) {
        if(input == null || input.size() <= 1) {
            return new ArrayList<Double>();
        }
        ArrayList<Double> result = new ArrayList<Double>();
        for(int i = 1; i < input.size(); i++) {
            result.add(input.get(i) / input.get(i - 1));
        }
        return result;
    }
}
