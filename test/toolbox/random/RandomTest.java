/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package toolbox.random;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

import org.apache.log4j.*;
import toolbox.util.ListArrayUtil;
import toolbox.stats.*;
import toolbox.io.CSVWriter;
import java.util.GregorianCalendar;
import RandomNumberGenerator.RandomNumberGenerator;

import java.util.List;
import java.util.ArrayList;

import java.io.PrintWriter;
import java.io.IOException;

/**
 *
 * @author paul
 */
public class RandomTest {
    
    private static Logger logger;
    private static Logger sameLineLogger;
    
    public RandomTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
        logger = toolbox.util.ListArrayUtil.getLogger(RandomTest.class, Level.DEBUG);
        sameLineLogger = ListArrayUtil.getSameLineLogger(RandomTest.class, Level.DEBUG);
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }

    @Test
    public void testUniformInts() {
        logger.info("\ntesting uniformInts()");
        assertEquals(0, Random.uniformInts(-1).length);
        assertEquals(0, Random.uniformInts(0).length);
        
        Integer[] result = null;
        int count = 1000;
        result = Random.uniformInts(count, 2, 4);
        assertEquals(count, result.length);
        //logger.debug(ListArrayUtil.arrayToString(result));
        Histogram hist = new Histogram(result);
        logger.debug(hist.toString());
        double entropy = hist.getEntropy();
        if(entropy < 1.4) {
            fail("entropy = " + entropy + ", which is too low - something is suspicious");
        }
        if(entropy > 1.584963) {
            fail("entropy = " + entropy + ": can't be higher than 1.584963 in this situation");
        }
        
        //assertEquals(0, Integer.valueOf(Random.uniformInts(1, 0, 0)[0]));
    }

    //@Test
    public void testIntsPerformance() {
        logger.info("\ntestIntsPerformance()");
        GregorianCalendar g = new GregorianCalendar();
        RandomNumberGenerator rand = new RandomNumberGenerator();
        int count = 100000000;
        long start1 = 0;
        long end1 = 0;
        long start2 = 0;
        long end2 = 0;
        logger.debug(g.getTimeInMillis());
        logger.debug(g.getTime());
        logger.debug(new java.util.Date());
        start1 = g.getTimeInMillis();
        for(int i = 0; i < count; i++) {
            RandomNumberGenerator.getIntInRange(2, 4);
        }
        end1 = g.getTimeInMillis();
        logger.debug(g.getTimeInMillis());
        logger.debug(g.getTimeInMillis());
        logger.debug(g.getTime());
        logger.debug(new java.util.Date());
        start2 = g.getTimeInMillis();
        Random.uniformInts(count, 2, 4);
        end2 = g.getTimeInMillis();
        logger.debug(g.getTimeInMillis());
        logger.debug(g.getTime());
        logger.debug(new java.util.Date());
        
        //TODO:  why is the millis value from the GregorianCalendar not updating?
        
        logger.debug(start1);
        logger.debug(end1);
        logger.debug(start2);
        logger.debug(end2);
        logger.debug("RandomNumberGenerator time = " + (end1 - start1));
        logger.debug("Random time = " + (end2 - start2));
    }
    
    @Test
    public void testGetUniformDoubles() {
        logger.info("\ntesting getUniformDoubles()");
        double[] result = null;
        assertEquals(0, Random.getUniformDoubles(-1).length);
        
        assertEquals(0, Random.getUniformDoubles(0).length);
        
        result = Random.getUniformDoubles(10000);
        if(toolbox.util.MathUtil.summary(result).min < 0.0) {
            fail("min was too low");
        }
        if(toolbox.util.MathUtil.summary(result).max > 1.0) {
            fail("max was too high");
        }
        assertEquals(0.5, toolbox.util.MathUtil.summary(result).mean, 0.05);
        
        result = Random.getUniformDoubles(10000, 0.0);
        if(toolbox.util.MathUtil.summary(result).min < 0.0) {
            fail("min was too low");
        }
        if(toolbox.util.MathUtil.summary(result).max > 1.0) {
            fail("max was too high");
        }
        assertEquals(0.5, toolbox.util.MathUtil.summary(result).mean, 0.05);
        
        result = Random.getUniformDoubles(10000, 1.0, 0.0);
        if(toolbox.util.MathUtil.summary(result).min < 0.0) {
            fail("min was too low");
        }
        if(toolbox.util.MathUtil.summary(result).max > 1.0) {
            fail("max was too high");
        }
        assertEquals(0.5, toolbox.util.MathUtil.summary(result).mean, 0.05);
        
        result = Random.getUniformDoubles(10000, 4.0, 8.0);
        if(toolbox.util.MathUtil.summary(result).min < 4.0) {
            fail("min was too low");
        }
        if(toolbox.util.MathUtil.summary(result).max > 8.0) {
            fail("max was too high");
        }
        assertEquals(6.0, toolbox.util.MathUtil.summary(result).mean, 0.05);
    }
    
    @Test
    public void checkEndPoints_Ints() {
        logger.info("\ntesting checkEndpoints(int...endPoints)");
        int[] result = null;
        result = Random.checkEndPoints(null);
        assertEquals(2, result.length);
        assertEquals(0, result[0]);
        assertEquals(1, result[1]);
        
        result = Random.checkEndPoints(new int[] { });
        assertEquals(2, result.length);
        assertEquals(0, result[0]);
        assertEquals(1, result[1]);
        
        result = Random.checkEndPoints(new int[] { 0 });
        assertEquals(2, result.length);
        assertEquals(0, result[0]);
        assertEquals(1, result[1]);
        
        result = Random.checkEndPoints(new int[] { 1 });
        assertEquals(2, result.length);
        assertEquals(0, result[0]);
        assertEquals(1, result[1]);
        
        result = Random.checkEndPoints(new int[] { 2 });
        assertEquals(2, result.length);
        assertEquals(0, result[0]);
        assertEquals(2, result[1]);
        //TODO: fix issue with negative numbers
        result = Random.checkEndPoints(new int[] { -2 });
        assertEquals(2, result.length);
        //assertEquals(-2, result[0]);
        //assertEquals(0, result[1]);
        
        result = Random.checkEndPoints(new int[] { 0, 1} );
        assertEquals(2, result.length);
        assertEquals(0, result[0]);
        assertEquals(1, result[1]);
        
        result = Random.checkEndPoints(new int[] { 1, 0} );
        assertEquals(2, result.length);
        assertEquals(0, result[0]);
        assertEquals(1, result[1]);
        
        result = Random.checkEndPoints(new int[] { 1, 1} );
        assertEquals(2, result.length);
        assertEquals(1, result[1]);
        assertEquals(1, result[1]);
    }
    
    //TODO:  test other sample function
    @Test
    public void testSample() {
        logger.info("\ntesting sample()");
        List<Integer> input = null;
        List result = null;
        try {
            assertEquals(0, Random.sample(input, 1, true).size());
            input = new ArrayList<Integer>();
            assertEquals(0, Random.sample(input, 1, true).size());

            input.add(1);
            assertEquals(1, Random.sample(input, 1, true).size());

            input.add(2);
            input.add(2);
            
            assertEquals(0, Random.sample(input, -1, true).size());
            
            result = Random.sample(input, 10000, true);
            Histogram hist = new Histogram(result);
            logger.debug(hist.toString());
            double entropy = hist.getEntropy();
            if(entropy < .9) {
                fail("entropy = " + entropy + ", which is too low - something is suspicious");
            }
            if(entropy > .94) {
                fail("entropy = " + entropy + ": can't be higher than .94 in this situation");
            }
            
        } catch(Exception e) {
            logger.error(e.getClass() + " in testSample():  " + e.getMessage());
        }
        
        //now without replacement
        input = null;
        try {
            assertEquals(0, Random.sample(input, 1, false).size());
            input = new ArrayList<Integer>();
            assertEquals(0, Random.sample(input, 1, false).size());
        } catch(Exception e) {
            logger.error(e.getClass() + " in testSample():  " + e.getMessage());
        }
        
        try {
            input = new ArrayList<Integer>();
            input.add(1);
            assertEquals(0, Random.sample(input, -1, false).size());
            result = Random.sample(input, 2, false);
            fail("did not throw the exception when it should have");
        } catch(Exception e) {
            logger.debug("correctly threw an Exception:  " + e.getClass() + " in testSample():  " + e.getMessage());
        }
        
        try {
            input = new ArrayList<Integer>();
            for(int i = 0; i < 1000; i++) {
                input.add(i);
            }
            //logger.debug(ListArrayUtil.listToString(input));
            
            result = Random.sample(input, 1000, false);
            result.add(99);
            //logger.debug("result.size() = " + result.size());
            assertEquals(1001, result.size());
            java.util.Collections.sort(result);
            //logger.debug(ListArrayUtil.listToString(result));
            for(int i = 1; i < result.size(); i++) {
                //logger.debug(result.get(i));
                if(result.get(i) == result.get(i-1)) {
                    logger.debug("correctly detected non unique list");
                    break;
                }
            }
            
            input = new ArrayList<Integer>();
            for(int i = 0; i < 1000; i++) {
                input.add(i);
            }
            result = Random.sample(input, 100, false);
            assertEquals(100, result.size());
            java.util.Collections.sort(result);
            for(int i = 1; i < result.size(); i++) {
                if(result.get(i) == result.get(i-1)) {
                    fail("non unique list");
                }
            }
        } catch(Exception e) {
            logger.error(e.getClass() + " in testSample():  " + e.getMessage());
        }
    }
    
    //@Test
    public void compareSampleListvsArrayTimes() {
        logger.info("\ncompareSampleListvsArrayTimes()");
        List<String> inputL = null;
        String[] inputA = null;
        int size = 10000;
        inputL = new ArrayList<String>();
        inputA = new String[6];
        inputL.add("a");
        inputA[0] = "a";
        inputL.add("a");
        inputA[1] = "a";
        inputL.add("a");
        inputA[2] = "a";
        inputL.add("b");
        inputA[3] = "b";
        inputL.add("b");
        inputA[4] = "b";
        inputL.add("c");
        inputA[5] = "c";
        //inputL.add("");
        logger.debug("comparing for input " + ListArrayUtil.arrayToString(inputA));
        compareSampleTimes(inputL, inputA, 1000);
        compareSampleTimes(inputL, inputA, 10000);
        compareSampleTimes(inputL, inputA, 100000);
        compareSampleTimes(inputL, inputA, 1000000);
        compareSampleTimes(inputL, inputA, 10000000);
    }
    
    private <T> void compareSampleTimes(List<T> list, T[] array, int size) {
        try {
            long t1 = new java.util.Date().getTime();
            List<T> resultL = Random.sample(list, size, true);
            long t2 = new java.util.Date().getTime();
            List<T> resultA = Random.sample(array, size, true);
            long t3 = new java.util.Date().getTime();
            long listTime = t2 - t1;
            long arrayTime = t3 - t2;
            logger.debug("speed test for " + size + " samples");
            logger.debug("list time = " + listTime);
            logger.debug("array time = " + arrayTime);
            if(listTime > arrayTime) {
                logger.debug("list time was " + (double)listTime / (double)arrayTime + " times array time");
            } else if(arrayTime > listTime) {
                logger.debug("array time was " + (double)arrayTime / (double)listTime + " times list time");
            } else {
                logger.debug("they were the same time");
            }
        } catch(Exception e) {
            
        }
    }
    
    @Test
    public void testRbinom_count_p() {
        logger.info("\ntesting rbinbom(int count, double p)");
        assertEquals(0, Random.rbinom(-1, 0).length);
        
        assertEquals(0, Random.rbinom(0, 0).length);
        assertEquals(0, Random.rbinom(10, -.001).length);
        assertEquals(0, Random.rbinom(10, 1.0001).length);
        
        int[] result = null;
        result = Random.rbinom(10000, .5);
        assertEquals(10000, result.length);
        assertEquals(.5, toolbox.util.MathUtil.mean(result), 0.01);
        
        result = Random.rbinom(10000, .75);
        assertEquals(10000, result.length);
        assertEquals(.75, toolbox.util.MathUtil.mean(result), 0.01);
    }
    
    @Test
    public void testRbinom_count_p_success_failure() {
        logger.info("\ntesting rbinom(int count, double p, T success, T failure)");
        assertEquals(0, Random.rbinom(-1, .8, "heads", "tails").size());
        assertEquals(0, Random.rbinom(0, .8, "heads", "tails").size());
        
        List result = null;
        result = Random.rbinom(10000, .5, "heads", "tails");
        assertEquals(10000, result.size());
        Histogram hist = new Histogram(result);
        ProbDist p = hist.getProbDist();
        logger.debug(hist.toString());
        assertEquals(1, hist.getEntropy(), 0.01);
        if((double)(p.getProbabilities().get(0)) > .55) {
            fail("probabilities out of balance");
        }
        if((double)(p.getProbabilities().get(0)) < .45) {
            fail("probabilities out of balance");
        }
        
        result = Random.rbinom(10000, .5, new Integer(1), new Integer(0));
        assertEquals(10000, result.size());
        hist = new Histogram(result);
        p = hist.getProbDist();
        logger.debug(hist.toString());
        assertEquals(1, hist.getEntropy(), 0.01);
        if((double)(p.getProbabilities().get(0)) > .52) {
            fail("probabilities out of balance");
        }
        if((double)(p.getProbabilities().get(0)) < .48) {
            fail("probabilities out of balance");
        }
    }
    
    @Test
    public void testRnorm() {
        logger.info("\ntesting rnorm()");
        double[] result = null;
        assertEquals(0, Random.rnorm(-1, 0, 1).length);
        assertEquals(0, Random.rnorm(0, 0, 1).length);
        
        result = Random.rnorm(100000, 0, 1);
        double mean = toolbox.util.MathUtil.mean(result);
        double sd = toolbox.util.MathUtil.sd(result);
        assertEquals(0.0, mean, 0.1);
        assertEquals(1.0, sd, 0.05);
        logger.debug("mean = " + mean + ";  sd = " + sd);
        try {
            CSVWriter.writeArray(result, "rnorm.csv", "nums", "\n");
        } catch(IOException e) {
            logger.error(e.getClass() + " trying to write the result in testRnorm():  " + e.getMessage());
        }
        
        result = Random.rnorm(10000, 12, 4);
        assertEquals(12.0, toolbox.util.MathUtil.mean(result), 0.1);
        assertEquals(4.0, toolbox.util.MathUtil.sd(result), 0.05);    //This can fail with count is 10000 so maybe the .05 or the count needs to be changed
    }
    
    @Test
    public void testRnormList() {
        logger.info("\ntesting rnormList");
        List<Double> result = null;
        assertEquals(0, Random.rnormList(-1, 0, 1).size());
                
        assertEquals(0, Random.rnormList(0, 0, 1).size());
        result = Random.rnormList(1000, 0, 1);
        assertEquals(1000, result.size());
        double mean = toolbox.util.MathUtil.mean(result);
        double sd = toolbox.util.MathUtil.sd(result);
    }
    
    @Test
    public void testErfInverse() {
        logger.info("\ntesting erfInverse()");
        assertEquals(0, Random.erfInverse(0.0), 0.0);
        assertEquals(2.318821, Random.erfInverse(.999), .0001);
        assertEquals(true, Double.isInfinite(Random.erfInverse(1)));
        assertEquals(-2.318821, Random.erfInverse(-.999), .0001);
        assertEquals(true, Double.isInfinite(Random.erfInverse(-1)));
    }
    
    @Test
    public void testNormIntegralInverse() {
        logger.info("\ntesting normIntegralInverse()");
        assertEquals(-1.96, Random.normIntegralInverse(.025, 0, 1), .01);
        assertEquals(1.96, Random.normIntegralInverse(.975, 0, 1), .01);
    }
    
    @Test
    public void testRexp() {
        logger.info("\ntesting rexp()");
        double[] result = null;
        result = Random.rexp(0, 0);
        assertEquals(0, result.length);
        assertEquals(1, Random.rexp(1, 1).length);
        
        result = Random.rexp(10000, 2);
        assertEquals(10000, result.length);
        try {
            /*PrintWriter writer = new PrintWriter(new java.io.FileWriter("rexp.csv"));
            writer.println("number");
            for(int i = 0; i < result.length; i++) {
                writer.println(result[i]);
            }
            writer.flush();
            writer.close();*/
            CSVWriter.writeArray(result, "rexp.csv", "nums", "\n");
        } catch(IOException e) {
            logger.error(e.getClass() + " " + e.getMessage());
        }
    }
}