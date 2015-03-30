/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package toolbox.random;

import java.util.List;
import java.util.ArrayList;

import static java.lang.Math.*;

/**
 *
 * @author paul
 */
public class Random {
  
    //TODO:  why I am returning and Integer[] here but a double[] from getUniformDoubles?  Figure out the reasoning or consider changing it.
    public static Integer[] uniformInts(int count, int...endPoints ) {
        if(count < 1) {
            return new Integer[0];
        }
        endPoints = checkEndPoints(endPoints);
        int lower = endPoints[0];
        int upper = endPoints[1];
        
        upper = upper + 1;      //because upper is inclusive and we chop off the decimals a few lines down
        int range = upper - lower;
        Integer[] result = new Integer[count];
        for(int i = 0; i < count; i++) {
            double rand = Math.random();
            rand = (double)lower + (range) * rand;  //adjust and scale to the appropriate range
            result[i] = (int)rand;
        }
        return result;
    }
    
    public static double[] getUniformDoubles(int count, double...endPoints) {
        if(count < 1) {
            return new double[0];
        }
        double lower = 0;
        double upper = 1;
        if(endPoints != null && endPoints.length > 0) {     //need to test for length > 0 because if you do getUniformDoubles(count) it considers endPoints an empty array and will throw an ArrayIndexOutOfBoundsException
            if(endPoints.length == 1 & endPoints[0] > lower) {
                upper = endPoints[0];
            } else if(endPoints.length == 1 & endPoints[0] < upper) {
                lower = endPoints[0];
            } else if(endPoints.length > 1) {
                if(endPoints[1] < endPoints[0]) {
                    lower = endPoints[0];
                    upper = endPoints[1];
                } else if(endPoints[0] < endPoints[1]) {
                    lower = endPoints[0];
                    upper = endPoints[1];
                } else if(endPoints[0] == endPoints[1]) {
                    //both numbers are equal; weird input, but we'll give them what they ask for
                    double[] result = new double[count];
                    for(int i = 0; i < count; i++) {
                        result[i] = endPoints[0];
                        return result;
                    }
                }
            }
        }
        
        double range = upper - lower;
        double[] result = new double[count];
        for(int i = 0; i < count; i++) {
            double rand = Math.random();
            rand = (double)lower + (range) * rand;  //adjust and scale to the appropriate range
            result[i] = rand;
        }
        return result;
    }
    
    public static List<Double> getUniformDoublesList(int count, double...endPoints) {
        double lower = 0;
        double upper = 1;
        if(endPoints != null && endPoints.length > 0) {     //need to test for length > 0 because if you do getUniformDoubles(count) it considers endPoints an empty array and will throw an ArrayIndexOutOfBoundsException
            if(endPoints.length == 1 & endPoints[0] > lower) {
                upper = endPoints[0];
            } else if(endPoints.length == 1 & endPoints[0] < upper) {
                lower = endPoints[0];
            } else if(endPoints.length > 1) {
                if(endPoints[1] < endPoints[0]) {
                    lower = endPoints[0];
                    upper = endPoints[1];
                } else if(endPoints[0] < endPoints[1]) {
                    lower = endPoints[0];
                    upper = endPoints[1];
                } else if(endPoints[0] == endPoints[1]) {
                    //both numbers are equal; weird input, but we'll give them what they ask for
                    List<Double> result = new ArrayList<Double>();
                    for(int i = 0; i < count; i++) {
                        result.add(endPoints[0]);
                        return result;
                    }
                }
            }
        }
        
        double range = upper - lower;
        List<Double> result = new ArrayList<Double>();
        for(int i = 0; i < count; i++) {
            double rand = Math.random();
            rand = (double)lower + (range) * rand;  //adjust and scale to the appropriate range
            result.add(rand);
        }
        return result;
    }
    //TODO:  refactor to use MathUtil.checkEndPoints
    protected static int[] checkEndPoints(int...endPoints) {
        //int[] result = new int[] { 0, 1 };
        int lower = 0;
        int upper = 1;
        if(endPoints != null) {
            if(endPoints.length == 0) {
                return new int[] { 0, 1 };
            }
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
        }
        return new int[] { lower, upper };
    }
    
    public static <T> List<T> sample(List<T> bag, int size, boolean replace) throws Exception {
        List<T> result = new ArrayList<T>();
        if(bag == null || bag.isEmpty()) {
            return result;
        }
        if(replace == true) {
            Integer[] indeces = uniformInts(size, 0, bag.size() - 1);
            for(Integer index : indeces) {
                result.add(bag.get(index));
            }
        } else {
            //replace is false, so once we select something from the input we can't select it again
            if(size > bag.size()) {
                throw new Exception("cannot sample without replacement more elements than there are in the inuput");
            }
            //if size == bag size, the below algorithm should work here but we can do it more quickly
            for(int i = 0; i < size; i++) {
                int index = uniformInts(size, 0, bag.size() - 1)[0];
                result.add(bag.get(index));
                bag.remove(index);
                //TODO:  may want to come up with a more efficient algorithm because remove() may be O(n) if List uses as array
            }
        }
        return result;
    }
    
    public static <T> List<T> sample(T[] bag, int size, boolean replace) throws Exception {
        List<T> result = new ArrayList<T>();
        if(bag == null || bag.length == 0) {
            return result;
        }
        if(replace == true) {
            Integer[] indeces = uniformInts(size, 0, bag.length - 1);
            for(Integer index : indeces) {
                result.add(bag[index]);
            }
        } else {
            List<T> list = new ArrayList<T>();
            for(int i = 0; i < bag.length; i++) {
                list.add(bag[i]);
            }
            result = sample(list, size, replace);
        }
        return result;
    }
    
    public static int[] rbinom(int count, double p) {
        if(count <= 0 || p < 0.0 || p > 1.0) {
            return new int[0];
        }
        int[] result = new int[count];
        for(int i = 0; i < count; i++) {
            if(random() < p) {
                result[i] = 1;
            } else {
                result[i] = 0;
            }
        }
        return result;
    }
    
    public static <T> List<T> rbinom(int count, double p, T success, T failure) {
        int[] ints = rbinom(count, p);
        List<T> result = new ArrayList<T>();
        for(int i : ints) {
            if(i == 1) {
                result.add(success);
            } else {
                result.add(failure);
            }
        }
        return result;
    }
    
    /** Returns an array of approximately normally distributed numbers.
     * The normal random number generation is somewhat of an approximation.  
     * From http://en.wikipedia.org/wiki/Normal_distribution we see that the cummulative probability distribution function of the Gaussian is .5 + .5 * erf((x - mu)/(sigma * square root of two)).  
     * But at http://en.wikipedia.org/wiki/Error_function we see that the erf function and its inverse are hard to calculate exactly so we can use an approximation.
     * 
     * @param count how many numbers to generate
     * @param mu the mean
     * @param sigma the standard deviation
     * @return an array of random values, which should be approximately normally distributed
     */
    public static double[] rnorm(int count, double mu, double sigma) {
        if(count <= 0) {
            return new double[0];
        }
        double[] result = new double[count];
        double current = 0;
        for(int i = 0; i < count; i++) {
            current = normIntegralInverse(Math.random(), mu, sigma);
            result[i] = current;
        }
        return result;
    }
    
    public static List<Double> rnormList(int count, double mu, double sigma) {
        List<Double> result = new ArrayList<Double>();
        double current = 0;
        for(int i = 0; i < count; i++) {
            current = normIntegralInverse(Math.random(), mu, sigma);
            result.add(current);
        }
        return result;
    }
    
    public static double normIntegralInverse(double alpha, double mu, double sigma) {
        return erfInverse(2 * alpha - 1) * sigma * sqrt(2) + mu;
    }

    //not quite as close an approximation as I would like but OK
    //PI and log are from the static import of java.lang.Math
    public static double erfInverse(double x) {
      double a = 8*(PI - 3) / (3* PI*(4 - PI));
      double part1 = pow((2/(PI * a) + .5*log(1 - pow(x, 2))), 2);
      double part2 = (log(1 - pow(x, 2)))/a;
      double part3 = 2/(PI * a) + .5*log(1 - pow(x, 2)); //just the square root of part1

      double result = sgn(x) * sqrt(sqrt(part1 - part2) - part3);
      return result;
    }
    
    public static double sgn(double x) {
        if(x < 0) {
            return -1;
        } else if(x == 0) {
            return 0;
        } else {
            return 1;
        }
    }
    
    public static double[] rexp(int count, double lambda) {
        
        double[] result = new double[count];
        double current = 0;
        for(int i = 0; i < count; i++) {
            current = (-1 / lambda) * Math.log(1 - Math.random());
            result[i] = current;
        }
        return result;
    }
    
    public static List<Double> rexpList(int count, double lambda) {
        
        List<Double> result = new ArrayList<Double>();
        double current = 0;
        for(int i = 0; i < count; i++) {
            current = (-1 * lambda) * Math.log(1 - Math.random());
            result.add(current);
        }
        return result;
    }
}
