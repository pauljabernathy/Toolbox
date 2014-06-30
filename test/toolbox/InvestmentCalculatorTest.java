/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package toolbox;

import toolbox.util.*;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

import org.apache.log4j.*;
import toolbox.stats.*;
import java.util.List;
import java.util.ArrayList;
import java.io.IOException;


/**
 *
 * @author paul
 */
public class InvestmentCalculatorTest {
    
    private static Logger logger;
    
    public InvestmentCalculatorTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
        logger = ListArrayUtil.getLogger(InvestmentCalculatorTest.class, Level.DEBUG);
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
    public void testMain() {
    }

    @Test
    public void testGetDistribution() {
        logger.info("\ntesting getDistributions()");
        InvestmentCalculator instance = new InvestmentCalculator();
        ProbDist result = instance.getDistribution("sp500.csv", 4);
        logger.debug(result.getValues().size());
    }
    
    @Test
    public void testGetRatios() {
        logger.info("\ntesting getRatios()");
        InvestmentCalculator instance = new InvestmentCalculator();
        List<Double> ratios = null;
        try {
            ratios = instance.getRatios(null);
            assertEquals(0, ratios.size());
        } catch(IOException e) {
            logger.error(e.getMessage() + " in testGetRatios() trying to load null file:  " + e.getMessage());
        }
        try {
            ratios = instance.getRatios("");
            assertEquals(0, ratios.size());
        } catch(IOException e) {
            logger.error(e.getMessage() + " in testGetRatios() trying to load empty file:  " + e.getMessage());
        }
        try {
            ratios = instance.getRatios("nonexistentfile.something");
            fail("should have thrown an exception but did not");
        } catch(IOException e) {
            logger.debug("correctly threw and exception in testGetRatios() trying to load non existent file:  " + e.getMessage());
        }
        
        try {
            ratios = instance.getRatios("sp500.csv");
            assertEquals(16172, ratios.size());
        } catch(IOException e) {
            logger.error(e.getMessage() + " in testGetRatios() trying to load empty file:  " + e.getMessage());
        }
    }
    
    @Test
    public void testGetTotal() {
        logger.info("\ntesting getTotal()");
        InvestmentCalculator instance = new InvestmentCalculator();
        List<Double> ratios = null;
        double result = -1.0;
        result = instance.getTotal(0.0, ratios, 0, 1);
        assertEquals(0.0, result, 0.0);
        
        ratios = new ArrayList<Double>();
        result = instance.getTotal(0.0, ratios, 0, 1);
        assertEquals(0.0, result, 0.0);
        
        ratios.add(1.1);
        ratios.add(1.1);
        ratios.add(1.1);
        result = instance.getTotal(1.0, ratios, 0, 1000);
        assertEquals(1.331, result, 0.0001);
        
        ratios.add(1.1);
        ratios.add(1.1);
        ratios.add(1.1);
        ratios.add(1.1);
        ratios.add(1.1);
        ratios.add(1.1);
        ratios.add(1.1);
        result = instance.getTotal(1.0, ratios, 0, 1000);
        assertEquals(2.593742, result, 0.0001);
        
        ratios.add(1.1);
        ratios.add(1.1);
        result = instance.getTotal(1.0, ratios, 0, 1000);
        assertEquals(3.138428, result, 0.0001);
        
        result = instance.getTotal(1.0, ratios, 1, 3);
        //int interval = 3;
        //double[] bins = instance.getBins(ratios, interval);
        //double[] cumBins = MathUtil.cumProd(bins, true);
        assertEquals(8.598937, result, 0.0001);
        
        result = instance.getTotal(1.0, ratios, 3, 3);
        assertEquals(19.51995, result, 0.0001);
        
        result = instance.getTotal(1.0, ratios, 0, 3);
        assertEquals(3.138428, result, 0.0001);
        
        //with initial investment of 0
        assertEquals(5.460509, instance.getTotal(0.0, ratios, 1.0, 3), 0.0001);
        
        //TODO:  most testing with different initial investments, intervals, etc.
        
        try {
            ratios = instance.getRatios("sp500.csv");
            //bins = instance.getBins(ratios, interval);
            //cumBins = MathUtil.cumProd(bins, true);
            result = instance.getTotal(16.66, ratios, 0, 100);
            assertEquals(1815.69, result, 0.00001);
        } catch(IOException e) {
            logger.error(e.getClass() + " trying to get the ratios in testGetTotal():  " + e.getMessage());
        }
    }
    
    @Test
    public void testGetBins() {
        logger.info("\ntesting getBins()");
        InvestmentCalculator instance = new InvestmentCalculator();
        List<Double> input = null;
        double[] result = null;
        assertEquals(0, instance.getBins(input, 3).length);
        
        input = new ArrayList<Double>();
        assertEquals(0, instance.getBins(input, 3).length);
        
        input.add(1.0);
        input.add(2.0);
        input.add(3.0);
        input.add(4.0);
        input.add(5.0);
        input.add(6.0);
        input.add(7.0);
        input.add(8.0);
        input.add(9.0);
        int interval = 3;
        result = instance.getBins(input, 3);
        assertEquals(3, result.length);
        logger.debug(ListArrayUtil.arrayToString(result));
        logger.debug(toolbox.util.MathUtil.prod(input, 0 * interval, 0 * interval + interval - 1));
        logger.debug(toolbox.util.MathUtil.prod(input, 1 * interval, 1 * interval + interval - 1));
        logger.debug(toolbox.util.MathUtil.prod(input, 2 * interval, 2 * interval + interval - 1));
        logger.debug(0 * interval + " " + (0 * interval + interval - 1));
        logger.debug(1 * interval + " " + (1 * interval + interval - 1));
        logger.debug(2 * interval + " " + (2 * interval + interval - 1));
        assertEquals(6.0, result[0], 0.0);
        assertEquals(120.0, result[1], 0.0);
        assertEquals(504.0, result[2], 0.0);
        
        input = new ArrayList<Double>();
        input.add(1.1);
        input.add(1.1);
        input.add(1.1);
        input.add(1.1);
        input.add(1.1);
        input.add(1.1);
        input.add(1.1);
        input.add(1.1);
        input.add(1.1);
        input.add(1.1);
        input.add(1.1);
        input.add(1.1);
        result = instance.getBins(input, 3);
        logger.debug(ListArrayUtil.arrayToString(result));
        
        
        //Now let's look at different bin sizes
        input = new ArrayList<Double>();
        input.add(1.0);
        input.add(2.0);
        input.add(3.0);
        
        //size 1 - just repeat the original input
        result = instance.getBins(input, 1);
        logger.debug(ListArrayUtil.arrayToString(result));
        assertEquals(3, result.length);
        assertEquals(1.0, result[0], 0.0);
        assertEquals(2.0, result[1], 0.0);
        assertEquals(3.0, result[2], 0.0);
        
        result = instance.getBins(input, 2);
        logger.debug(ListArrayUtil.arrayToString(result));
        assertEquals(2, result.length);
        assertEquals(2.0, result[0], 0.0);
        assertEquals(3.0, result[1], 0.0);
        
        result = instance.getBins(input, 3);
        logger.debug(ListArrayUtil.arrayToString(result));
        assertEquals(1, result.length);
        assertEquals(6.0, result[0], 0.0);
        
        //size greater than the input length - should be the same as when the size is the same as the input length
        result = instance.getBins(input, 4);
        logger.debug(ListArrayUtil.arrayToString(result));
        assertEquals(1, result.length);
        assertEquals(6.0, result[0], 0.0);
    }
    
    @Test
    public void testGetOneTargetContribution() {
        logger.info("\ntesting getOneTargetContribution(double initial, List<Double> ratios, double targetEnd, int interval)");
        InvestmentCalculator instance = new InvestmentCalculator();
        try {
            instance.getOneTargetContribution(0.0, null, 100, 10);
        } catch(NullPointerException e) {
            logger.debug("correctly threw a NullPointerException for a null ratios");
        }
        
        double result = 0.0;
        List<Double> ratios = new ArrayList<Double>();
        //result = instance.getOneTargetContribution(0.0, ratios, 100, 10);
        //assertEquals(100.0, result, 0.0);
        assertEquals(100.0, instance.getOneTargetContribution(0.0, ratios, 100, 10), 0.0);
        assertEquals(50, instance.getOneTargetContribution(50.0, ratios, 100, 10), 0.0001);
        assertEquals(0.0, instance.getOneTargetContribution(100.0, ratios, 100, 10), 0.0);
        
        ratios.add(1.1);
        ratios.add(1.1);
        ratios.add(1.1);
        double[] bins = instance.getBins(ratios, 10);
        logger.debug(ListArrayUtil.arrayToString(bins));
        double[] cumBins = MathUtil.cumProd(bins, true);
        logger.debug(ListArrayUtil.arrayToString(cumBins));
        logger.debug(MathUtil.sum(cumBins, 1, cumBins.length - 1));
        assertEquals(100.0, instance.getOneTargetContribution(0.0, ratios, 100, 10), 0.0);
        assertEquals(33.45, instance.getOneTargetContribution(50.0, ratios, 100, 10), 0.00001);
        assertEquals(0.175, instance.getOneTargetContribution(75, ratios, 100, 10), 0.00001);
        
        //try when we have to take money out
        assertEquals(-33.1, instance.getOneTargetContribution(100.0, ratios, 100, 10), 0.0001);
        
        //now with an input longer than the interval
        ratios.add(1.1);
        ratios.add(1.1);
        ratios.add(1.1);
        ratios.add(1.1);
        ratios.add(1.1);
        ratios.add(1.1);
        ratios.add(1.1);
        ratios.add(1.1);
        ratios.add(1.1);
        assertEquals(10.0, instance.getOneTargetContribution(0.0, ratios, 54.60509, 3), 0.00001);
        assertEquals(10.0, instance.getOneTargetContribution(10.0, ratios, 85.98937, 3), 0.00001);
    }
}