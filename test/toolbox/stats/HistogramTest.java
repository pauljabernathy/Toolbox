/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package toolbox.stats;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

import java.util.List;
import java.util.ArrayList;
import org.apache.log4j.*;
import toolbox.Utilities;

/**
 *
 * @author paul
 */
public class HistogramTest {
    
    private static Logger logger;
    
    public HistogramTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
        logger = Logger.getLogger(HistogramTest.class);
        logger.addAppender(new ConsoleAppender(new PatternLayout(toolbox.Constants.DEFAULT_LOG_FORMAT)));
        logger.setLevel(Level.INFO);
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
    public void testSetDataList() {
        logger.info("\ntesting setDataList()");
        Histogram instance = new Histogram();
        DataList<Double> d = new DataList<Double>();
        d.add(5.0).add(2.0).add(5.0);
        instance.setDataList(d);
        List<Double> values = instance.getValues();
        assertEquals(2, values.size());
        if(!values.contains(5.0)) {
            fail("does not contain 5.0");
        }
        if(!values.contains(2.0)) {
            fail("does not contain 2.0");
        }
        int i = values.indexOf(5.0);
        List<Integer> counts = instance.getCounts();
        assertEquals(2, (int)counts.get(i));
        //instance.display();
        
        ArrayList<ArrayList<Double>> d2 = new ArrayList<ArrayList<Double>>();
        ArrayList<Double> l1 = new ArrayList<Double>();
        l1.add(2.0);
        l1.add(3.0);
        
        ArrayList<Double> l2 = new ArrayList<Double>();
        l2.add(2.0);
        l2.add(4.0);
        
        ArrayList<Double> l3 = new ArrayList<Double>();
        l3.add(2.0);
        l3.add(3.0);
        
        d2.add(l1);
        d2.add(l2);
        d2.add(l3);
        
        instance.setDataList(d2);
        //instance.display();
        assertEquals(2, instance.size());
    }

    @Test
    public void testGetProbDist() {
        logger.info("\ntesting getProbDist()");
        Histogram instance = new Histogram();
        DataList<String> list = new DataList<String>();
        list.add("a").add("b").add("b").add("a").add("b");
        instance.setDataList(list);
        logger.debug(Utilities.listToString(instance.getValues()));
        logger.debug(Utilities.listToString(instance.getCounts()));
        logger.debug(Utilities.listToString(instance.getProbabilities()));
        ProbDist pd = instance.getProbDist();
        List<Double> probs = pd.getProbabilities();
        List<String> values = pd.getValues();
        int aIndex = values.indexOf("a");
        assertEquals(probs.get(aIndex), .4, .0001);
        int bIndex = values.indexOf("b");
        assertEquals(probs.get(bIndex), .6, .0001);
        logger.debug(pd.toString());
    }
    
    @Test
    public void testSize() {
        logger.info("\ntesting size()");
    }
}