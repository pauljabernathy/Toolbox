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
    public void testSummary() {
        logger.info("\ntesting summary()");
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