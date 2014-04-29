/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package toolbox.stats;

import toolbox.Constants;
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
public class DataListTest {
    
    private static Logger logger;
    
    public DataListTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
        logger = Logger.getLogger(DataListTest.class);
        logger.addAppender(new ConsoleAppender(new PatternLayout(Constants.DEFAULT_LOG_FORMAT)));
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
    public void testAdd() {
        logger.info("\ntesting add()");
        DataList instance = new DataList();
        instance.add("a");
        //instance.print();
        assertEquals(1, instance.size());
        assertEquals("a", instance.get(0));
        instance.add("a");
        instance.add("b");
        //instance.print();
        assertEquals(3, instance.size());
        assertEquals("a", instance.get(1));
        assertEquals("b", instance.get(2));
    }

    @Test
    public void testGetHistogram() {
        logger.info("\ntesting getHistogram()");
        DataList instance = new DataList();
        Histogram result = instance.getHistogram();
        assertEquals(0, result.size());
        instance.add("a").add("a").add("b").add("a");
        result = instance.getHistogram();
        assertEquals(2, result.size());
        
    }

    @Test
    public void testSize() {
        logger.info("\ntesting size()");
        DataList instance = new DataList();
        int result = instance.size();
        assertEquals(0, instance.size());
        instance.add("a");
        assertEquals(1, instance.size());
        instance.add("a").add("b").add("c");
        assertEquals(4, instance.size());
    }

    @Test
    public void testGet() {
        logger.info("\ntesting get()");
        int index = 0;
        DataList instance = new DataList();
        instance.add("0");
        instance.add("1");
        assertEquals("1", instance.get(1));
        assertEquals("0", instance.get(0));
    }
}