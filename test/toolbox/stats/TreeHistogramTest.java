/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package toolbox.stats;

import java.util.TreeMap;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

import org.apache.logging.log4j.*;
import toolbox.util.ListArrayUtil;
import java.util.PriorityQueue;

/**
 *
 * @author pabernathy
 */
public class TreeHistogramTest {
    
    private static Logger logger;
    
    public TreeHistogramTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
        logger = ListArrayUtil.getLogger(TreeHistogramTest.class, Level.INFO);
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

    /**
     * Test of addToData method, of class TreeHistogram.
     */
    @Test
    public void testAddToData() {
        logger.info("\ntesting addToData()");
        Object item = null;
        int count = 0;
        TreeHistogram<String> instance = new TreeHistogram<>();
        TreeMap expResult = null;
        TreeMap result = instance.addToData("a", 1);
        assertEquals(1, instance.size());
        instance.addToData("b", 1);
        assertEquals(2, instance.size());
        instance.addToData("c", 27);
        assertEquals(3, instance.size());
        result = instance.addToData("a", 2);
        System.out.println(result);
        assertEquals(3, instance.size());
        assertEquals(3, result.get("a"));
        assertEquals(1, result.get("b"));
    }

    /**
     * Test of addToCounts method, of class TreeHistogram.
     */
    @Test
    public void testAddToCounts() {
        logger.info("\ntesting addToCounts()");
        
        TreeMap<Integer, String> test = new TreeMap<>();
        test.put(1, "1");
        logger.info(test);
        test.put(1, "one");
        logger.info(test);
        Object item = null;
        int count = 0;
        TreeHistogram<String> instance = new TreeHistogram();
        TreeMap expResult = null;
        //TreeMap result = instance.addToCounts("a", count);
        //assertEquals(1, result.size());
        
        PriorityQueue<Integer> q = new PriorityQueue();
        q.add(1);
        q.add(2);
        q.add(3);
        q.add(2);
        System.out.println(q);
        for(int i = 0; i < 4; i++) {
            System.out.println(q.poll());
        }
        
        java.util.Comparator<Integer> c = new java.util.Comparator<Integer>() {
            public int compare(Integer a, Integer b) {
                return b.compareTo(a);
            }
        };
        
        q = new PriorityQueue<Integer>(c);
        q.add(1);
        q.add(2);
        q.add(3);
        q.add(2);
        System.out.println(q);
        for(int i = 0; i < 4; i++) {
            System.out.println(q.poll());
        }
        
        PriorityQueue<HistogramEntry> hq = new PriorityQueue<>();
        java.util.Comparator<HistogramEntry> hc = new java.util.Comparator<HistogramEntry>() {
            public int compare(HistogramEntry left, HistogramEntry right) {
                return left.count.compareTo(right.count);
            }
        };
        hq = new PriorityQueue<>(hc);
        hq = new PriorityQueue<>((HistogramEntry left, HistogramEntry right) -> right.count.compareTo(left.count));
        
        hq.add(new HistogramEntry("a", 1));
        hq.add(new HistogramEntry("a", 2));
        hq.add(new HistogramEntry("b", 1));
        hq.add(new HistogramEntry("c", 4));
        System.out.println(hq);
        for(int i = 0; i < 4; i++) {
            logger.info(hq.poll());
        }
    }

    /**
     * Test of add method, of class TreeHistogram.
     */
    @Test
    public void testAdd() {
        logger.info("\n testing add()");
        Object item = null;
        int count = 0;
        TreeHistogram instance = new TreeHistogram();
        TreeHistogram expResult = null;
        TreeHistogram result = instance.add(item, count);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getCount method, of class TreeHistogram.
     */
    @Test
    public void testGetCount() {
        logger.info("\ntestinggetCount()");
        Object item = null;
        TreeHistogram instance = new TreeHistogram();
        int expResult = 0;
        int result = instance.getCount(item);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }
    
    @Test
    public void testSize() {
        logger.info("\ntesting size()");
        TreeHistogram<String> instance = new TreeHistogram<>();
        assertEquals(0, instance.size());
    }
    
}
