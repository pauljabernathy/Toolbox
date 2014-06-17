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
import toolbox.util.ListArrayUtil;

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
        logger.setLevel(Level.DEBUG);
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
    public void testConstructor_noArgs() {
        
    }
    
    @Test
    public void testConstructor_array() {
        
    }
    
    @Test
    public void testConstructor_DataList() {
        
    }
    
    @Test
    public void testConstructor_List() {
        
    }
    
    @Test
    public void testConstructor_array_array() {
        logger.info("\ntesting constructor Histogram(T[] values, int[] counts)");
        Histogram instance = null;
        try {
            instance = new Histogram(null, null);
            fail("did not throw an exception when it should have");
        } catch(ProbabilityException e) {
            //if we are here, it executed correctly
        }
        try {
            instance = new Histogram(null, new int[] { });
            fail("did not throw an exception when it should have");
        } catch(ProbabilityException e) {
            //if we are here, it executed correctly
        }
        try {
            instance = new Histogram(null, new int[] { 2, 3 });
            fail("did not throw an exception when it should have");
        } catch(ProbabilityException e) {
            //if we are here, it executed correctly
        }
        try {
            instance = new Histogram(new Integer[] { }, null);
            fail("did not throw an exception when it should have");
        } catch(ProbabilityException e) {
            //if we are here, it executed correctly
        }
        try {
            instance = new Histogram(new Integer[] { }, new int[] { });
            fail("did not throw an exception when it should have");
        } catch(ProbabilityException e) {
            //if we are here, it executed correctly
        }
        try {
            instance = new Histogram(new Integer[] { 2, 3 }, null);
            fail("did not throw an exception when it should have");
        } catch(ProbabilityException e) {
            //if we are here, it executed correctly
        }
        try {
            instance = new Histogram(new Integer[] { 2, 3 }, new int[] { });
            fail("did not throw an exception when it should have");
        } catch(ProbabilityException e) {
            //if we are here, it executed correctly
        }
        
        try {
            instance = new Histogram(new Integer[] { 2, 3 }, new int[] { 5, 7, 9 });
            fail("did not throw an exception when it should have");
        } catch(ProbabilityException e) {
            //if we are here, it executed correctly
        }
        
        try {
            instance = new Histogram(new Integer[] { 1, 2, 3 }, new int[] { 5, 7, 9 });
            assertEquals(3, instance.size());
            List<Double> probs = instance.getProbabilities();
            assertEquals(3, probs.size());
            assertEquals(5.0 / 21.0, probs.get(0), .0000000001);
            assertEquals(7.0 / 21.0, probs.get(1), .0000000001);
            assertEquals(9.0 / 21.0, probs.get(2), .0000000001);
            
            List<Integer> values = instance.getValues();
            assertEquals(new Integer(1), values.get(0));
            assertEquals(new Integer(2), values.get(1));
            assertEquals(new Integer(3), values.get(2));
            assertEquals(3, values.size());
        } catch(ProbabilityException e) {
            fail("threw an exception when the input was good");
        }
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
    public void testSetLabel() {
        logger.info("\ntesting setLabel()");
        Histogram instance = new Histogram();
        DataList<Double> d = new DataList<Double>();
        d.add(5.0).add(2.0).add(5.0);
        instance.setDataList(d);
        
        logger.debug("\nlabel = " + instance.getLabel());
        logger.debug("label length = " + instance.getLabel().length());
        assert(instance.getLabel().equals(""));
        String str = instance.toString();
        assert(str.startsWith("5.0"));
        logger.debug(str);
        
        instance.setLabel("Histogram for 5 and 2");
        assertEquals("Histogram for 5 and 2", instance.getLabel());
        logger.debug("\nlabel = " + instance.getLabel());
        logger.debug("label length = " + instance.getLabel().length());
        assert(instance.toString().startsWith("Histogram for 5 and 2"));
        logger.debug("\n" + instance.toString());
    }
    @Test
    public void testGetProbDist() {
        logger.info("\ntesting getProbDist()");
        Histogram instance = new Histogram();
        DataList<String> list = new DataList<String>();
        list.add("a").add("b").add("b").add("a").add("b");
        instance.setDataList(list);
        logger.debug(ListArrayUtil.listToString(instance.getValues()));
        logger.debug(ListArrayUtil.listToString(instance.getCounts()));
        logger.debug(ListArrayUtil.listToString(instance.getProbabilities()));
        ProbDist pd = instance.getProbDist();
        List<Double> probs = pd.getProbabilities();
        List<String> values = pd.getValues();
        int aIndex = values.indexOf("a");
        assertEquals(probs.get(aIndex), .4, .0001);
        int bIndex = values.indexOf("b");
        assertEquals(probs.get(bIndex), .6, .0001);
        logger.debug(pd.toString());
        
         try {
            Histogram hist = new Histogram(new String[] { "A", "B", "C", "D"}, new int[] { 33, 33, 33, 0 });
            logger.debug(hist.toString());
            logger.debug(hist.getProbDist());
            logger.debug(hist.getProbDist().getProbabilities().contains(0.0));
            logger.debug(hist.getEntropy());
            assertEquals(1.584963, hist.getEntropy(), .000001);
        } catch(ProbabilityException e) {
            logger.error(e.getClass() + " in testGetEntropy():  " + e.getMessage());
        }
    }
    
    @Test
    public void testSize() {
        logger.info("\ntesting size()");
    }
    
    @Test
    public void testToString() {
        logger.info("\ntesting toString()");
        Histogram instance = new Histogram();
        DataList<String> list = new DataList<String>();
        list.add("a").add("b").add("b").add("a").add("b");
        instance.setDataList(list);
        logger.debug(instance.toString());
        logger.debug(instance.toString("<br>"));
    }
}