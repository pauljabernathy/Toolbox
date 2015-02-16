/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package toolbox.util;

import java.util.ArrayList;
import java.util.List;
import java.util.Collections;
import org.apache.log4j.*;
import toolbox.Constants;

/**
 *
 * @author paul
 */
public class ListArrayUtil {
    
    public static final String DEFAULT_LIST_TO_STRING_OPEN = "< ";
    public static final String DEFAULT_LIST_TO_STRING_CLOSE = " >";
    public static final String DEFAULT_LIST_TO_STRING_SEP = ", ";
    
    public static void showList(List input) {
        if(input == null) {
            return;
        }
        for(Object current : input) {
            System.out.println(current);
        }
    }
    
    public static String listToString(List input) {
        return listToString(input, DEFAULT_LIST_TO_STRING_SEP);
    }
    
    public static String listToString(List input, String sep) {
        return listToString(input, sep, DEFAULT_LIST_TO_STRING_OPEN, DEFAULT_LIST_TO_STRING_CLOSE);
    }
    
    public static String listToString(List input, String sep, String open, String close) {
        if(open == null) {
            open = DEFAULT_LIST_TO_STRING_OPEN;
        }
        if(close == null) {
            close = DEFAULT_LIST_TO_STRING_CLOSE;
        }
        if(input == null || input.isEmpty()) {
            return open + " " + close;
        }
        if(sep == null) {
            sep = DEFAULT_LIST_TO_STRING_SEP;
        }
        if(input.size() == 1) {
            return open + input.get(0) + close;
        }
        StringBuilder sb = new StringBuilder();
        sb.append(open);
        for(int i = 0; i < input.size() - 1; i++) {
            Object current = input.get(i);
            sb.append(current.toString()).append(sep);
        }
        sb.append(input.get(input.size() - 1));
        sb.append(close);
        return sb.toString();
    }
    //TODO:  remove this
    /**
     * @deprecated 
     * @param a 
     */
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
    
    public static boolean contains(int[] array, int value) {
        if(array == null || array.length == 0) {
            return false;
        }
        for(int current : array) {
            if(current == value) {
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
            result.add(ListArrayUtil.andAndCondense(input, ListArrayUtil.toBinaryArray(i, input.length)));
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
            result[i] = ListArrayUtil.and(input, ListArrayUtil.toBinaryArray(i, input.length));
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
        double log = MathUtil.logBase2(source);
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
    
    /**
     * gives the dimensions of a list of list as { num rows, num columns }
     * @param data
     * @return 
     */
    public static int[] dim(List<List> data) {
        return dim(data, true);
    }
    
    public static int[] dim(List<List> data, boolean byRow) {
        if(data == null || data.size() == 0) {
            return new int[] { 0, 0 };
        }
        int x = data.size();
        int[] ys = new int[x];
        for(int i = 0; i < x; i++) {
            ys[i] = data.get(i).size();
        }
        int y = MathUtil.max(ys);
        if(byRow) {
            return new int[] { x, y };
        } else {
            return new int[] { y, x };
        }
    }
    
    /**
     * checks element by element to see if the elements in the two arrays are the same (that is, checks not just that the values from one array are in the other, but that they are in the same order)
     * @param left
     * @param right
     * @return 
     */
    public static boolean haveSameElements(double[] left, double[] right) {
        if(left == null && right == null) {
            return true;
        }
        //if one is null but the other is not, they are different
        if((left == null && right != null) || (left != null && right == null)) {
            return false;
        }
        //if they are different sizes, they are different
        if(left.length != right.length) {
            return false;
        }
        for(int i = 0; i < left.length; i++) {
            if(left[i] != right[i]) {
                return false;
            }
        }
        return true;
    }
    
    public static boolean haveSameElements(int[] left, int[] right) {
        if(left == null && right == null) {
            return true;
        }
        //if one is null but the other is not, they are different
        if((left == null && right != null) || (left != null && right == null)) {
            return false;
        }
        //if they are different sizes, they are different
        if(left.length != right.length) {
            return false;
        }
        for(int i = 0; i < left.length; i++) {
            if(left[i] != right[i]) {
                return false;
            }
        }
        return true;
    }
    
    public static <T> boolean haveSameElements(T[] left, T[] right) {
        if(left == null && right == null) {
            return true;
        }
        //if one is null but the other is not, they are different
        if((left == null && right != null) || (left != null && right == null)) {
            return false;
        }
        //if they are different sizes, they are different
        if(left.length != right.length) {
            return false;
        }
        for(int i = 0; i < left.length; i++) {
            if(!left[i].equals(right[i])) {
                return false;
            }
        }
        return true;
    }
    
    public static boolean haveSameElements(List left, List right) {
        if(left == null && right == null) {
            return true;
        }
        
        //if one is null but the other is not, they are different
        if((left == null && right != null) || (left != null && right == null)) {
            return false;
        }
        //if they are different sizes, they are different
        if(left.size() != right.size()) {
            return false;
        }
        for(int i = 0; i < left.size(); i++) {
            if((left.get(i) == null && right.get(i) != null) || !left.get(i).equals(right.get(i))) {
                return false;
            }
        }
        return true;
    }
    
    public static int findNumDiffs(List a, List b) {
        //if both are null and/or empty, return 0
        if(a == null && b == null) {
            return 0;
        } else if(a == null && b != null) {
            return b.size();
        } else if(b == null && a != null) {
            return a.size();
        }
        int numDiffs = 0;
        for(int i = 0; i < a.size() && i < b.size(); i++) {
            if(a.get(i).getClass() == b.get(i).getClass()) {
                if(!a.get(i).equals(b.get(i))) {
                    numDiffs++;
                }
            }
        }
        if(a.size() < b.size()) {
            for(int i = a.size(); i < b.size(); i++) {
                numDiffs++;
            }
        } else if(a.size() > b.size()) {
            for(int i = b.size(); i < a.size(); i++) {
                numDiffs++;
            }
        }
        return numDiffs;
    }
    
    public static int findNumDiffs(int[] a, int[] b) {
        int numDiffs = 0;
        if(a == null && b == null) {
            return 0;
        } else if(a == null && b != null) {
            return b.length;
        } else if(b == null && a != null) {
            return a.length;
        }
        for(int i = 0; i < a.length && i < b.length; i++) {
            if(a[i] != b[i]) {
                numDiffs++;
            }
        }
        if(a.length < b.length) {
            for(int i = a.length; i < b.length; i++) {
                numDiffs++;
            }
        } else if(a.length > b.length) {
            for(int i = b.length; i < a.length; i++) {
                numDiffs++;
            }
        }
        return numDiffs;
    }
    
    public static <T extends Comparable> List<T> merge(List<T> left, List<T> right) {
        List<T> result = new ArrayList<>();
        if(left == null || left.isEmpty()) {
            if(right == null || right.isEmpty()) {
                return result;
            } else {
                return right;
            }
        } else if(right == null || right.isEmpty()) {
            if(left == null || left.isEmpty()) {
                return result;
            } else {
                return right;
            }
        }
        Collections.sort(left);
        Collections.sort(right);
        int l = 0;
        int r = 0;
        int comparison = 0;
        while(l < left.size() && r < right.size()) {
            comparison = left.get(l).compareTo(right.get(r));
            if(comparison < 0) {
                result.add(left.get(l));
                l++;
            } else if(comparison == 0) {
                result.add(left.get(l));
                result.add(right.get(r));
                l++;
                r++;
            } else if(comparison > 0) {
                result.add(right.get(r));
                r++;
            }
        }
        if(l < left.size()) {
            for(int i = l; i < left.size(); i++) {
                result.add(left.get(i));
            }
        } else if(r < right.size()) {
            for(int i = r; i < right.size(); i++) {
                result.add(right.get(r));
            }
        }
        return result;
    }
    
    //TODO:  move somewhere else!
    public static Logger getLogger(Class clazz, Level level) {
        Logger logger = Logger.getLogger(clazz);
        logger.removeAllAppenders();
        logger.addAppender(new ConsoleAppender(new PatternLayout(Constants.DEFAULT_LOG_FORMAT)));
        logger.setLevel(level);
        return logger;
    }
    
    public static Logger getSameLineLogger(Class clazz, Level level) {
        Logger logger = Logger.getLogger(clazz + " same line");
        logger.removeAllAppenders();
        logger.addAppender(new ConsoleAppender(new PatternLayout(Constants.SAME_LINE_LOG_FORMAT)));
        logger.setLevel(level);
        return logger;
    }
}
