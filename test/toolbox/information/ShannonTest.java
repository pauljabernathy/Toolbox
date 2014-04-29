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
import toolbox.Utilities;
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
        logger = toolbox.Utilities.getLogger(ShannonTest.class, Level.DEBUG);
        sameLineLogger = Utilities.getSameLineLogger(ShannonTest.class, Level.DEBUG);
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
    public void testGetEntropy_doubleArr() {
    }

    @Test
    public void testGetEntropy_List() {
    }

    @Test
    public void testGetMutualInformation() {
        logger.info("\ntesting getMutualInformation()");
        ArrayList<Double> left = new ArrayList<Double>();
        left.add(1.0);
        left.add(2.0);
        left.add(3.0);
        left.add(4.0);
        
        ArrayList<Double> right = new ArrayList<Double>();
        right.add(5.0);
        right.add(6.0);
        right.add(7.0);
        right.add(8.0);
        
        ProbDist<Double> A = new Histogram(left).getProbDist();
        ProbDist<Double> B = new Histogram(right).getProbDist();
        logger.debug(A.getEntropy());
        logger.debug(B.getEntropy());
        logger.debug(ProbDist.getMutualInformation(A, B));
        assertEquals(0.0, Shannon.getMutualInformation(left, right), 0.00000001);
        
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
        
        //List<List<Double>> input = Shannon.getCombinedList(left, right);
        logger.debug(Shannon.getMutualInformation(left, right));
    }
    
    //@Test
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
        for(List<Double> list : result) {
            for(Double d : list) {
                sameLineLogger.debug(d + " ");
            }
            logger.debug("");
        }
    }
}