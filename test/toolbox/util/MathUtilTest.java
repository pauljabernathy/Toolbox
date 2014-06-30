/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package toolbox.util;

import toolbox.util.MathUtil;
import java.util.ArrayList;
import java.util.List;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

import org.apache.log4j.*;
import toolbox.stats.Summary;
import toolbox.util.ListArrayUtil;

/**
 *
 * @author paul
 */
public class MathUtilTest {
    
    private static Logger logger;
    
    public MathUtilTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
        logger = toolbox.util.ListArrayUtil.getLogger(MathUtilTest.class, Level.DEBUG);
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
    public void testLogBase2() {
        logger.debug("\ntesting logBase2()");
        logger.debug(MathUtil.logBase2(1));
        logger.debug(MathUtil.logBase2(2));
        logger.debug(MathUtil.logBase2(3));
        logger.debug(MathUtil.logBase2(4));
        logger.debug(MathUtil.logBase2(5));
        logger.debug(MathUtil.logBase2(6));
        logger.debug(MathUtil.logBase2(7));
        logger.debug(MathUtil.logBase2(8));
        logger.debug(MathUtil.logBase2(9));
    }
    
    @Test
    public void testSummary_double_array() {
        logger.info("\ntesting summary(double[] array)");
        double[] input = null;
        
        input = new double[] { 1, 2, 3, 4 };
        Summary result = MathUtil.summary(input);
        assertEquals(1.0, result.min, 0.0);
        assertEquals(2.5, result.median, 0.0);
        assertEquals(2.5, result.mean, 0.0);
        assertEquals(4, result.max, 0.0);
        assertEquals(1.290994, result.sd, 0.0001);
    }
    
    @Test
    public void testSummary_List_Double() {
        logger.info("\ntesting summary(List<Double> list)");
        List<Double> input = null;
        Summary result = null;
        result = MathUtil.summary(input);
        if(result == null) {
            fail("summary was null");
        }
        /*public double min;
        public double firstQ;
        public double median;
        public double mean;
        public double thirdQ;
        public double max;
        public double sd*/
        assertEquals(0.0, result.min, 0.0);
        assertEquals(0.0, result.firstQ, 0.0);
        assertEquals(0.0, result.median, 0.0);
        assertEquals(0.0, result.mean, 0.0);
        assertEquals(0.0, result.thirdQ, 0.0);
        assertEquals(0.0, result.max, 0.0);
        assertEquals(0.0, result.sd, 0.0);
        
        input = new ArrayList<Double>();
        result = MathUtil.summary(input);
        assertEquals(0.0, result.min, 0.0);
        assertEquals(0.0, result.firstQ, 0.0);
        assertEquals(0.0, result.median, 0.0);
        assertEquals(0.0, result.mean, 0.0);
        assertEquals(0.0, result.thirdQ, 0.0);
        assertEquals(0.0, result.max, 0.0);
        assertEquals(0.0, result.sd, 0.0);
        
        input.add(1.0);
        input.add(2.0);
        input.add(3.0);
        input.add(4.0);
        result = MathUtil.summary(input);
        assertEquals(1.0, result.min, 0.0);
        //assertEquals(1.75, result.firstQ, 0.0);
        assertEquals(2.5, result.median, 0.0);
        assertEquals(2.5, result.mean, 0.0);
        //assertEquals(3.25, result.thirdQ, 0.0);
        assertEquals(4.0, result.max, 0.0);
        assertEquals(1.290994, result.sd, 0.0001);
    }
    
    @Test
    public void testMin_Int_Array() {
        logger.info("\ntesting min(int[] array)");
        //TODO:  fill in
    }
    
    @Test
    public void testMax_Int_Array() {
        logger.info("\ntesting max(int[] array)");
        //TODO:  fill in
    }
    
    @Test
    public void testSum_List_Double() {
        logger.info("\ntesting sum(List<Double> list)");
        List<Double> l = new ArrayList<Double>();
        assertEquals(0.0, MathUtil.sum(null), 0.0);
        assertEquals(0.0, MathUtil.sum(l), 0.0);
        l.add(27.0);
        assertEquals(27.0, MathUtil.sum(l), 0.0);
        l.add(-5.0);
        assertEquals(22.0, MathUtil.sum(l), 0.0);
        l.remove(27.0);
        assertEquals(-5.0, MathUtil.sum(l), 0.0);
        l.add(1.0);
        l.add(2.0);
        l.add(48.0);
        assertEquals(46.0, MathUtil.sum(l), 0.0);
    }
    
    @Test
    public void testSum_double_array() {
        logger.info("\ntesting sum(double[] input, int...endPoints)");
        double[] input = null;
        assertEquals(0.0, MathUtil.sum(input), 0.0);
        assertEquals(0.0, MathUtil.sum(input, 0), 0.0);
        assertEquals(0.0, MathUtil.sum(input, 0, 57, 3), 0.0);
        
        input = new double[] { 1.0, 2.0, 3.0, 4.0 };
        assertEquals(10.0, MathUtil.sum(input), 0.0);
        assertEquals(3.0, MathUtil.sum(input, 0, 1), 0.0);
        assertEquals(6.0, MathUtil.sum(input, 0, 2), 0.0);
        assertEquals(10.0, MathUtil.sum(input, 0, 3), 0.0);
        assertEquals(10.0, MathUtil.sum(input, 0, 4), 0.0);
        
        assertEquals(2.0, MathUtil.sum(input, 1, 1), 0.0);
        assertEquals(5.0, MathUtil.sum(input, 1, 2), 0.0);
        assertEquals(9.0, MathUtil.sum(input, 1, 3), 0.0);
        assertEquals(9.0, MathUtil.sum(input, 1, 4), 0.0);
        
        assertEquals(9.0, MathUtil.sum(input, 4, 1), 0.0);
        
        input = new double[] { 1.3310000000000004, 1.3310000000000004, 1.3310000000000004, 1.3310000000000004 };
        double[] cumBins = MathUtil.cumProd(input, true);
        logger.debug(ListArrayUtil.arrayToString(cumBins));
        int regularContribution = 1;
        logger.debug(cumBins[0] + regularContribution * MathUtil.sum(cumBins, 1, cumBins.length - 1));
    }

    @Test
    public void testCumSum_doubleArray() {
        //TODO:  fill in
    }
    
    @Test
    public void testCumSum_DoubleList() {
        //TODO:  fill in
    }
    
    @Test
    public void testCumSum_intArray() {
        //TODO:  fill in
    }
    
    @Test
    public void testCumSum_IntegerList() {
        //TODO:  fill in
    }
    
    @Test
    public void testCumSumList_doubleArray() {
        //TODO:  fill in
    }
    
    @Test
    public void testCumSumList_DoubleList() {
        logger.info("\ntesting cumsumList(List<Double> input)");
        List<Double> result = null;
        List<Double> input = null;
        assertEquals(0, MathUtil.cumsumList(input).size());
        assertEquals(true, MathUtil.cumsumList(input).isEmpty());
        input = new ArrayList<Double>();
        assertEquals(0, MathUtil.cumsumList(input).size());
        assertEquals(true, MathUtil.cumsumList(input).isEmpty());
        
        input.add(4.0);
        result = MathUtil.cumsumList(input);
        assertEquals(1, result.size());
        assertEquals(4.0, result.get(0), 0.0);
        
        input.add(1.0);
        input.add(8.0);
        input.add(-3.0);
        result = MathUtil.cumsumList(input);
        assertEquals(4, result.size());
        assertEquals(4.0, result.get(0), 0.0);
        assertEquals(5.0, result.get(1), 0.0);
        assertEquals(13.0, result.get(2), 0.0);
        assertEquals(10.0, result.get(3), 0.0);
        
        List input2 = new ArrayList<Integer>();
        input2.add(1);
        input2.add(2);
        //List result2 = ListArrayUtil.cumsumList(input2);
        //logger.debug(toolbox.ListArrayUtil.listToString(input2));
    }
    
    @Test
    public void testCumSumList_intArray() {
        //TODO:  fill in
    }
    
    @Test
    public void testCumSumList_IntegerList() {
        //TODO:  fill in
    }
    
    @Test
    public void testProd_List_Double() {
        logger.info("\ntesting prod(List<Double> list)");
        List<Double> input = null;
        assertEquals(0.0, MathUtil.prod(input), 0.0);
        
        input = new ArrayList<Double>();
        assertEquals(0.0, MathUtil.prod(input), 0.0);
        
        input.add(4.0);
        assertEquals(4.0, MathUtil.prod(input), 0.0);
        input.add(1.0);
        assertEquals(4.0, MathUtil.prod(input), 0.0);
        input.add(.5);
        assertEquals(2.0, MathUtil.prod(input), 0.0);
        input.add(27.0);
        assertEquals(54.0, MathUtil.prod(input), 0.0);
    }
    
    @Test
    public void testProd_List_Double_Range() {
        logger.info("\ntesting prod(List<Double> list, int start, int end)");
        
        List<Double> input = null;
        assertEquals(0.0, MathUtil.prod(input, 0, 0), 0.0);
        
        input = new ArrayList<Double>();
        assertEquals(0.0, MathUtil.prod(input, 0, 0), 0.0);
        
        input.add(4.0);
        input.add(1.0);
        input.add(.5);
        input.add(27.0);
        assertEquals(4.0, MathUtil.prod(input, 0, 0), 0.0);
        assertEquals(4.0, MathUtil.prod(input, 0, 1), 0.0);
        assertEquals(2.0, MathUtil.prod(input, 0, 2), 0.0);
        assertEquals(54.0, MathUtil.prod(input, 0, 3), 0.0);
        
        assertEquals(1.0, MathUtil.prod(input, 1, 1), 0.0);
        assertEquals(0.5, MathUtil.prod(input, 1, 2), 0.0);
        assertEquals(13.5, MathUtil.prod(input, 1, 3), 0.0);
        
        assertEquals(54.0, MathUtil.prod(input, -1, 4), 0.0);
        assertEquals(54.0, MathUtil.prod(input, 0, 4), 0.0);
        
        assertEquals(54.0, MathUtil.prod(input, 3, 0), 0.0);
        assertEquals(54.0, MathUtil.prod(input, 4, -1), 0.0);
    }
    
    @Test
    public void testProd_double_array() {
        logger.info("\ntesting prod(List<Double> list)");
        double[] input = null;
        assertEquals(0.0, MathUtil.prod(input), 0.0);
        
        input = new double[] { };
        assertEquals(0.0, MathUtil.prod(input), 0.0);
        
        input = new double[] { 4 };
        assertEquals(4.0, MathUtil.prod(input), 0.0);
        input = new double[] { 4, 1.0 };
        assertEquals(4.0, MathUtil.prod(input), 0.0);
        input = new double[] { 4, 1.0, 0.5 };
        assertEquals(2.0, MathUtil.prod(input), 0.0);
        input = new double[] { 4, 1.0, 0.5, 27.0 };
        assertEquals(54.0, MathUtil.prod(input), 0.0);
    }
    
    @Test
    public void testProd_double_array_Range() {
        logger.info("\ntesting prod(List<Double> list, int start, int end)");
        
        double[] input = null;
        assertEquals(0.0, MathUtil.prod(input), 0.0);
        
        input = new double[] { };
        assertEquals(0.0, MathUtil.prod(input), 0.0);
        
        input = new double[] { 4, 1.0, 0.5, 27.0 };
        assertEquals(4.0, MathUtil.prod(input, 0, 0), 0.0);
        assertEquals(4.0, MathUtil.prod(input, 0, 1), 0.0);
        assertEquals(2.0, MathUtil.prod(input, 0, 2), 0.0);
        assertEquals(54.0, MathUtil.prod(input, 0, 3), 0.0);
        
        assertEquals(1.0, MathUtil.prod(input, 1, 1), 0.0);
        assertEquals(0.5, MathUtil.prod(input, 1, 2), 0.0);
        assertEquals(13.5, MathUtil.prod(input, 1, 3), 0.0);
        
        assertEquals(54.0, MathUtil.prod(input, -1, 4), 0.0);
        assertEquals(54.0, MathUtil.prod(input, 0, 4), 0.0);
        
        assertEquals(54.0, MathUtil.prod(input, 3, 0), 0.0);
        assertEquals(54.0, MathUtil.prod(input, 4, -1), 0.0);
    }
    
    @Test
    public void testCumprod_double_array() {
        logger.info("\n testing cumProd(double[] array, boolean...reverse)");
        double[] input = null;
        double[] result = null;
        result = MathUtil.cumProd(input);
        assertEquals(0, result.length);
        
        input = new double[] { };
        assertEquals(0, MathUtil.cumProd(input).length);
        
        input = new double[] { 1.0, 2.0, 3.0, 1.0, 2.0, -1.0, -1.0 };
        result = MathUtil.cumProd(input);
        assertEquals(7, result.length);
        assertEquals(1.0, result[0], 1.0);
        assertEquals(2.0, result[1], 1.0);
        assertEquals(6.0, result[2], 1.0);
        assertEquals(6.0, result[3], 1.0);
        assertEquals(12.0, result[4], 1.0);
        assertEquals(-12.0, result[5], 1.0);
        assertEquals(12.0, result[6], 1.0);
        
        result = MathUtil.cumProd(input, true);
        assertEquals(7, result.length);
        assertEquals(-1.0, result[6], 1.0);
        assertEquals(1.0, result[5], 1.0);
        assertEquals(2.0, result[4], 1.0);
        assertEquals(2.0, result[3], 1.0);
        assertEquals(6.0, result[2], 1.0);
        assertEquals(12.0, result[1], 1.0);
        assertEquals(12.0, result[0], 1.0);
        
        input = new double[] { 1.3310000000000004, 1.3310000000000004, 1.3310000000000004, 1.3310000000000004 };
        logger.debug(ListArrayUtil.arrayToString(MathUtil.cumProd(input, true)));
    }
    
    @Test
    public void testDiffRatios() {
        logger.info("\ntesting diffRatios()");
        double[] input = null;
        double[] result = null;
        assertEquals(0, MathUtil.diffRatios(input).length);
        input = new double[] { };
        assertEquals(0, MathUtil.diffRatios(input).length);
        input = new double[] { 1 };
        assertEquals(0, MathUtil.diffRatios(input).length);
        
        input = new double[] { 1, 2 };
        result = MathUtil.diffRatios(input);
        assertEquals(1, result.length);
        assertEquals(2.0, result[0], 0.0);
        
        input = new double[] { 1, 2, 1, 5 };
        result = MathUtil.diffRatios(input);
        assertEquals(3, result.length);
        assertEquals(2.0, result[0], 0.0);
        assertEquals(0.5, result[1], 0.0);
        assertEquals(5.0, result[2], 0.0);
        
        input = new double[] { 1, 2, 0, 5, 8 };
        result = MathUtil.diffRatios(input);
        logger.debug(ListArrayUtil.arrayToString(result));
        assertEquals(true, Double.isInfinite(result[2]));
        assertEquals(false, Double.isNaN(result[2]));
    }
    
    @Test
    public void testMean_doubleArr() {
    }

    @Test
    public void testMean_List() {
    }
    
    @Test
    public void testSD_double_Array() {
        logger.info("\ntesting sd(double[] input)");
        double[] input = null;
        assertEquals(0.0, MathUtil.sd(input), 0.0);
        input = new double[] { 1.0 };
        assertEquals(0.0, MathUtil.sd(input), 0.0);
        input = new double[] { 1.0, 2.0, 3.0, 4.0, 5.0 };
        assertEquals(1.581139, MathUtil.sd(input), 0.0001);
    }
    
    @Test
    public void testSD_List_Double() {
        logger.info("\ntesting sd(List<Double> input)");
        List<Double> input = null;
        assertEquals(0.0, MathUtil.sd(input), 0.0);
        input = new ArrayList<Double>();
        input.add(1.0);
        assertEquals(0.0, MathUtil.sd(input), 0.0);
        input.add(2.0);
        input.add(3.0);
        input.add(4.0);
        input.add(5.0);
        assertEquals(1.581139, MathUtil.sd(input), 0.0001);
    }
    
    @Test
    public void testCheckEndPoints() {
        logger.info("\ntesting checkEndPoints()");
        int[] input = null;
        int[] result = null;
        
        //null and empty arrays
        result = MathUtil.checkEndPoints(0, 1, input);
        assertEquals(2, result.length);
        assertEquals(0, result[0]);
        assertEquals(1, result[1]);
        
        result = MathUtil.checkEndPoints(1, 1, input);
        assertEquals(2, result.length);
        assertEquals(1, result[0]);
        assertEquals(1, result[1]);
        
        result = MathUtil.checkEndPoints(4, 4, input);
        assertEquals(2, result.length);
        assertEquals(4, result[0]);
        assertEquals(4, result[1]);
        
        //TODO:  deal with case where defaults are switched
        //or should it give an error since it is a programmer error
        result = MathUtil.checkEndPoints(2, 1, input);
        assertEquals(2, result.length);
        assertEquals(1, result[0]);
        assertEquals(2, result[1]);
        
        //TODO:  test with array of one element
        
        //array of two elements
        input = new int[] { 4, 8 };
        result = MathUtil.checkEndPoints(4, 10, input);
        assertEquals(2, result.length);
        assertEquals(4, result[0]);
        assertEquals(8, result[1]);
        
        input = new int[] { 4, 8, 20 };//third element should be ignored
        result = MathUtil.checkEndPoints(4, 10, input);
        assertEquals(2, result.length);
        assertEquals(4, result[0]);
        assertEquals(8, result[1]);
        
        input = new int[] { 4, 8 };
        result = MathUtil.checkEndPoints(0, 1, input);
        assertEquals(2, result.length);
        assertEquals(0, result[0]);
        assertEquals(1, result[1]);
        
        input = new int[] { -4, -8 };
        result = MathUtil.checkEndPoints(0, 1, input);
        assertEquals(2, result.length);
        assertEquals(0, result[0]);
        assertEquals(1, result[1]);
        
        input = new int[] { -1, 2 };
        result = MathUtil.checkEndPoints(0, 1, input);
        assertEquals(2, result.length);
        logger.debug(ListArrayUtil.arrayToString(result));
        assertEquals(0, result[0]);
        assertEquals(1, result[1]);
        
        input = new int[] { -1, 2 };
        result = MathUtil.checkEndPoints(1, 0, input);
        assertEquals(2, result.length);
        logger.debug(ListArrayUtil.arrayToString(result));
        assertEquals(0, result[0]);
        assertEquals(1, result[1]);
        
        input = new int[] { 2 -1 };
        result = MathUtil.checkEndPoints(0, 1, input);
        assertEquals(2, result.length);
        assertEquals(0, result[0]);
        assertEquals(1, result[1]);
        
        input = new int[] { 2, -1 };
        result = MathUtil.checkEndPoints(1, 0, input);
        assertEquals(2, result.length);
        assertEquals(0, result[0]);
        assertEquals(1, result[1]);
        
        //TODO: more testing
    }
}