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
import org.apache.logging.log4j.*;
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
        logger = ListArrayUtil.getLogger(HistogramTest.class, Level.DEBUG);
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
        logger.info("\ntesting Constructor_List()");
        String log4jProperty = "log4j.configurationFile";
        System.out.println(System.getProperty(log4jProperty));
        System.setProperty(log4jProperty, "log4j2-test.xml");
        System.out.println(System.getProperty("java.class.path"));
        System.out.println(System.getProperty(log4jProperty));
        
        List<String> list = null;
        Histogram instance = new Histogram(list);
        assertEquals(0, instance.size());
        
        list = new ArrayList<String>();
        instance = new Histogram(list);
        assertEquals(0, instance.size());
        
        list.add("one");
        list.add("one");
        list.add("two");
        instance = new Histogram(list);
        assertEquals(2, instance.size());
        
        /**try {
            retirement.InvestmentCalculator calc = new retirement.InvestmentCalculator();
            List<Double> ratios = calc.getRatios("sp500.csv");
            instance = new Histogram(ratios);
            logger.debug(instance.getEntropy());
            if(instance.getEntropy() == 1.0) { 
                fail("entropy was 1");
            }
            List<Double> values = instance.getValues();
            for(int i = 0; i < 10; i++) {
                logger.debug(values.get(i));
            }
            
        } catch(java.io.IOException e) {
            logger.error(e.getClass() + " in testConstructor_List():  " + e.getMessage());
        }/**/
    }
    
    @Test
    public void testConstructor_int_array() {
        logger.info("\ntesting Histogram(int[] data)");
        int[] data = null;
        Histogram instance = null;
        instance = new Histogram(data);
        assertEquals(0, instance.size());
        
        data = new int[0];
        instance = new Histogram(data);
        assertEquals(0, instance.size());
        
        data = new int[] { 1, 2, 3, 3, 4, 4, 2, 4, 3, 4 };
        instance = new Histogram(data);
        assertEquals(4, instance.size());
        assertEquals(1, instance.getCountOf(1));
        assertEquals(2, instance.getCountOf(2));
        assertEquals(3, instance.getCountOf(3));
        assertEquals(4, instance.getCountOf(4));
    }
    @Test
    public void testConstructor_generic_array_array() {
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
    public void testClear() {
        logger.info("\ntesting clear()");
        List<String> data = null;
        Histogram instance = new Histogram(data);
        assertEquals(0, instance.size());
        instance.clear();
        assertEquals(0, instance.size());
        
        data = new ArrayList<String>();
        instance.setDataList(data);
        assertEquals(0, instance.size());
        instance.clear();
        assertEquals(0, instance.size());
        
        data.add("a");
        data.add("b");
        data.add("c");
        data.add("b");
        instance.setDataList(data);
        assertEquals(3, instance.size());
        instance.clear();
        assertEquals(0, instance.size());
        
        String[] dataStr = new String[] { "a", "b", "c", "b" };
        instance.setDataList(dataStr);
        assertEquals(3, instance.size());
        instance.clear();
        assertEquals(0, instance.size());
    }
    
    @Test
    public void testSetDataList() {
        logger.info("\ntesting setDataList()");
        Histogram instance = new Histogram();
        assertEquals(0, instance.getValues().size());
        DataList<Double> d = null;
        instance.setDataList(d);
        assertEquals(0, instance.getValues().size());
        
        d = new DataList<Double>();
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
        
        ArrayList<String> empty = null;
        instance.setDataList(empty);
        assertEquals(0, instance.size());    
        
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
         
        /**try {
            retirement.InvestmentCalculator calc = new retirement.InvestmentCalculator();
            List<Double> ratios = calc.getRatios("sp500.csv");
            instance = new Histogram(ratios);
            logger.debug(instance.getEntropy());
            if(instance.getEntropy() == 1.0) { 
                fail("entropy was 1");
            }
            values = instance.getValues();
            
            for(int i = 0; i < 10; i++) {
                logger.debug(values.get(i));
            }
            logger.debug("\n");
            ProbDist<Double> p = instance.getProbDist();
            List<Double> v = p.getValues();
            for(int i = 0; i < 10; i++) {
                logger.debug(v.get(i));
            }
        } catch(java.io.IOException e) {
            logger.error(e.getClass() + " in testConstructor_List():  " + e.getMessage());
        }/**/
    }
    
    @Test
    public void testGetCountOf() {
        logger.info("\ntesting getCountOf()");
        Histogram instance = new Histogram();
        assertEquals(0, instance.getCountOf("some string that isn't there"));
        assertEquals(0, instance.getCountOf(1));
        
        String[] strings = new String[] { "a", "a", "b", "a", "b", "c" };
        instance = new Histogram(strings);
        assertEquals(3, instance.getCountOf("a"));
        assertEquals(2, instance.getCountOf("b"));
        assertEquals(1, instance.getCountOf("c"));
        assertEquals(0, instance.getCountOf("some string that isn't there"));
        assertEquals(0, instance.getCountOf(1));
        
        List data = new ArrayList();
        data.add("a");
        data.add(2);
        data.add("a");
        instance = new Histogram(data);
        assertEquals(2, instance.getCountOf("a"));
        assertEquals(0, instance.getCountOf("b"));
        assertEquals(0, instance.getCountOf("c"));
        assertEquals(0, instance.getCountOf("some string that isn't there"));
        assertEquals(0, instance.getCountOf(1));
        assertEquals(1, instance.getCountOf(2));
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