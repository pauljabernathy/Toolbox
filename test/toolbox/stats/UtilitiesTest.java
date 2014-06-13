/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package toolbox.stats;

import java.util.ArrayList;
import java.util.List;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

import org.apache.log4j.*;

/**
 *
 * @author paul
 */
public class UtilitiesTest {
    
    private static Logger logger;
    
    public UtilitiesTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
        logger = toolbox.Utilities.getLogger(UtilitiesTest.class, Level.INFO);
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
    public void testSum() {
    }
    
    @Test
    public void testSummary_double_array() {
        logger.info("\ntesting summary(double[] array)");
        double[] input = null;
        
        input = new double[] { 1, 2, 3, 4 };
        Summary result = Utilities.summary(input);
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
        result = Utilities.summary(input);
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
        result = Utilities.summary(input);
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
        result = Utilities.summary(input);
        assertEquals(1.0, result.min, 0.0);
        //assertEquals(1.75, result.firstQ, 0.0);
        assertEquals(2.5, result.median, 0.0);
        assertEquals(2.5, result.mean, 0.0);
        //assertEquals(3.25, result.thirdQ, 0.0);
        assertEquals(4.0, result.max, 0.0);
        assertEquals(1.290994, result.sd, 0.0001);
    }
    
    @Test
    public void testSum_List_Double() {
        logger.info("\ntesting sum(List<Double> list)");
        List<Double> l = new ArrayList<Double>();
        assertEquals(0.0, Utilities.sum(null), 0.0);
        assertEquals(0.0, Utilities.sum(l), 0.0);
        l.add(27.0);
        assertEquals(27.0, Utilities.sum(l), 0.0);
        l.add(-5.0);
        assertEquals(22.0, Utilities.sum(l), 0.0);
        l.remove(27.0);
        assertEquals(-5.0, Utilities.sum(l), 0.0);
        l.add(1.0);
        l.add(2.0);
        l.add(48.0);
        assertEquals(46.0, Utilities.sum(l), 0.0);
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
        assertEquals(0, Utilities.cumsumList(input).size());
        assertEquals(true, Utilities.cumsumList(input).isEmpty());
        input = new ArrayList<Double>();
        assertEquals(0, Utilities.cumsumList(input).size());
        assertEquals(true, Utilities.cumsumList(input).isEmpty());
        
        input.add(4.0);
        result = Utilities.cumsumList(input);
        assertEquals(1, result.size());
        assertEquals(4.0, result.get(0), 0.0);
        
        input.add(1.0);
        input.add(8.0);
        input.add(-3.0);
        result = Utilities.cumsumList(input);
        assertEquals(4, result.size());
        assertEquals(4.0, result.get(0), 0.0);
        assertEquals(5.0, result.get(1), 0.0);
        assertEquals(13.0, result.get(2), 0.0);
        assertEquals(10.0, result.get(3), 0.0);
        
        List input2 = new ArrayList<Integer>();
        input2.add(1);
        input2.add(2);
        //List result2 = Utilities.cumsumList(input2);
        //logger.debug(toolbox.Utilities.listToString(input2));
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
    public void testMean_doubleArr() {
    }

    @Test
    public void testMean_List() {
    }
    
    @Test
    public void testSD_double_Array() {
        logger.info("\ntesting sd(double[] input)");
        double[] input = null;
        assertEquals(0.0, Utilities.sd(input), 0.0);
        input = new double[] { 1.0 };
        assertEquals(0.0, Utilities.sd(input), 0.0);
        input = new double[] { 1.0, 2.0, 3.0, 4.0, 5.0 };
        assertEquals(1.581139, Utilities.sd(input), 0.0001);
    }
    
    @Test
    public void testSD_List_Double() {
        logger.info("\ntesting sd(List<Double> input)");
        List<Double> input = null;
        assertEquals(0.0, Utilities.sd(input), 0.0);
        input = new ArrayList<Double>();
        input.add(1.0);
        assertEquals(0.0, Utilities.sd(input), 0.0);
        input.add(2.0);
        input.add(3.0);
        input.add(4.0);
        input.add(5.0);
        assertEquals(1.581139, Utilities.sd(input), 0.0001);
    }
}