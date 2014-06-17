/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package toolbox.information;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

import org.apache.log4j.*;
import java.util.ArrayList;
import java.util.List;
import toolbox.util.ListArrayUtil;
import toolbox.stats.*;

/**
 *
 * @author paul
 */
public class ShannonTest {
    
    private static Logger logger;
    private static Logger sameLineLogger;
    
    public ShannonTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
        logger = toolbox.util.ListArrayUtil.getLogger(ShannonTest.class, Level.DEBUG);
        sameLineLogger = ListArrayUtil.getSameLineLogger(ShannonTest.class, Level.INFO);
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
    public void testGetEntropy_Array() {
        logger.info("\ntesting getEntropy(T[] input)");
        
        Integer[] inputInt = new Integer[] { 1, 2, 3, 1 }; 
        assertEquals(1.5, Shannon.getEntropy(inputInt), 0.0000001);
        
        String[] inputStr = new String[] { "strawberry", "strawberry", "raspberry", "blueberry" };
        assertEquals(1.5, Shannon.getEntropy(inputStr), 0.000001);
    }

    @Test
    public void testGetEntropy_List() {
        logger.info("\ntesting getEntropy(List<T> input)");
        List input = new ArrayList<Integer>();
        input.add(1);
        input.add(2);
        input.add(3);
        input.add(1);
        
        assertEquals(1.5, Shannon.getEntropy(input), 0.000001);
        
        input = new ArrayList<String>();
        input.add("strawberry");
        input.add("strawberry");
        input.add("raspberry");
        input.add("blueberry");
        
        assertEquals(1.5, Shannon.getEntropy(input), 0.0000001);
    }

    @Test
    public void testGetMutualInformation() {
        logger.info("\ntesting getMutualInformation()");
        ArrayList left = new ArrayList<Double>();
        left.add(1.0);
        left.add(2.0);
        left.add(3.0);
        left.add(4.0);
        
        ArrayList right = new ArrayList<Double>();
        right.add(5.0);
        right.add(6.0);
        right.add(7.0);
        right.add(8.0);
        
        ProbDist<Double> A = new Histogram(left).getProbDist();
        ProbDist<Double> B = new Histogram(right).getProbDist();
        logger.debug(A.getEntropy());
        logger.debug(B.getEntropy());
        assertEquals(2.0, Shannon.getMutualInformation(left, right), 0.00000001);
        
        logger.debug("-");
        right = new ArrayList<Double>();
        right.add(1.0);
        right.add(2.0);
        right.add(3.0);
        right.add(4.0);
        A = new Histogram(left).getProbDist();
        B = new Histogram(right).getProbDist();
        logger.debug(A.getEntropy());
        logger.debug(B.getEntropy());
        logger.debug(Shannon.getMutualInformation(left, right));
        assertEquals(2.0, Shannon.getMutualInformation(left, right), 0.00000001);
        
        logger.debug("-");
        right = new ArrayList<Double>();
        right.add(2.0);
        right.add(2.0);
        right.add(2.0);
        right.add(2.0);
        A = new Histogram(left).getProbDist();
        B = new Histogram(right).getProbDist();
        logger.debug(A.getEntropy());
        logger.debug(B.getEntropy());
        logger.debug(Shannon.getMutualInformation(left, right));
        assertEquals(0.0, Shannon.getMutualInformation(left, right), 0.00000001);
        
        logger.debug("-");
        left = new ArrayList<Double>();
        left.add(2.0);
        left.add(3.0);
        left.add(5.0);
        left.add(2.0);
        
        right = new ArrayList<Double>();
        right.add(4.0);
        right.add(3.0);
        right.add(8.0);
        right.add(4.0);
        
        logger.debug(Shannon.getMutualInformation(left, right));
        
        logger.debug("-");
        left = new ArrayList<Double>();
        left.add(1.0);
        left.add(2.0);
        left.add(3.0);
        left.add(4.0);
        
        right = new ArrayList<Double>();
        right.add(5.0);
        right.add(5.0);
        right.add(6.0);
        right.add(6.0);
        logger.debug(Shannon.getMutualInformation(left, right));
        assertEquals(1.0, Shannon.getMutualInformation(left, right), .0000001);
        
        logger.debug("-");
        left = new ArrayList<String>();
        right = new ArrayList<String>();
        left.add("1.0");
        left.add("2.0");
        left.add("3.0");
        left.add("4.0");
        
        right.add(5.0);
        right.add(5.0);
        right.add(6.0);
        right.add(6.0);
        //TODO:  Figure out what class is being used above.  String or Double?
        logger.debug(Shannon.getMutualInformation(left, right));
        assertEquals(1.0, Shannon.getMutualInformation(left, right), .0000001);
    }
    
    @Test
    public void testGetCombinedList() {
        logger.info("\ntesting getCombinedList()");
        
        List<List<Double>> result = null;
        assertEquals(0, Shannon.getCombinedList(null, null).size());
        
        ArrayList<Double> left = new ArrayList<Double>();
        left.add(2.0);
        left.add(3.0);
        left.add(5.0);
        
        ArrayList<Double> right = new ArrayList<Double>();
        right.add(4.0);
        right.add(3.0);
        right.add(8.0);
        right.add(4.0);
        
        assertEquals(0, Shannon.getCombinedList(left, right).size());
        
        left.add(2.0);
        result = Shannon.getCombinedList(left, right);
        assertEquals(4, result.size());
        assertEquals(2, result.get(0).get(0), 0.0);
        assertEquals(4, result.get(0).get(1), 0.0);
        assertEquals(3, result.get(1).get(0), 0.0);
        assertEquals(3, result.get(1).get(1), 0.0);
        assertEquals(5, result.get(2).get(0), 0.0);
        assertEquals(8, result.get(2).get(1), 0.0);
        assertEquals(2, result.get(3).get(0), 0.0);
        assertEquals(4, result.get(3).get(1), 0.0);
        for(List<Double> list : result) {
            for(Double d : list) {
                sameLineLogger.debug(d + " ");
            }
            sameLineLogger.debug("\n");
        }
    }
}