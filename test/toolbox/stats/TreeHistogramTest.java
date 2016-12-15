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
import toolbox.trees.WeightedBinaryTree;
import java.util.List;

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
    public void testAddToDataAndGetCorrectCounts() {
        logger.info("\ntesting addToData()");
        TreeHistogram<String> instance = new TreeHistogram<>();
        TreeMap expResult = null;
        WeightedBinaryTree<String> result = instance.addToData("a", 1);
        assertEquals("a", result.value);
        assertEquals(1, result.weight, 0.0);
        assertEquals(1, instance.getNumEntries());
        assertEquals(1, instance.getTotalCount());
        assertEquals(null, result.left);
        assertEquals(null, result.right);
        result = instance.addToData("b", 1);
        assertEquals("a", result.value);
        assertEquals("b", result.right.value);
        assertEquals(1, result.right.weight, 0.0);
        assertEquals(2, instance.getNumEntries());
        assertEquals(2, instance.getTotalCount());
        result = instance.addToData("a", 27);
        assertEquals("a", result.value);
        assertEquals(28, result.weight, 0.0);
        assertEquals(2, instance.getNumEntries());
        assertEquals(29, instance.getTotalCount());
        assertEquals(null, result.left);
        assertEquals("b", result.right.value);
        logger.info(result);
    }

    @Test
    public void testGetAsListByNaturalOrder() {
        logger.info("\ntesting getAsList() by natural order");
        TreeHistogram<String> hist = this.createSimpleWordHistogram();
        List<HistogramEntry> list = hist.getAsList(TreeHistogram.Sort.ITEM);
        logger.info(list);
    }
    
    @Test
    public void testGetAsListByCount() {
        TreeHistogram<String> hist = this.createSimpleWordHistogram();
        List<HistogramEntry> list = hist.getAsList(TreeHistogram.Sort.COUNT);
        logger.info(list);
    }
    
    private TreeHistogram<String> createSimpleWordHistogram() {
        TreeHistogram<String> hist = new TreeHistogram<>();
        hist.addToData("the", 1);
        hist.addToData("holiday", 5);
        hist.addToData("and", 8);
        hist.addToData("the", 10);
        hist.addToData("relevance", 1);
        hist.addToData("injure", 2);
        return hist;
    }
}
