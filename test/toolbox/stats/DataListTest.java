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
import java.util.ArrayList;

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
    public void testConstructor_List() {
        logger.info("\ntesting DataList(List<T> list)");
        DataList<String> instanceStr = null;
        ArrayList<String> inputStr = null;
        instanceStr = new DataList<String>(inputStr);
        assertEquals(0, instanceStr.getData().size());
        
        inputStr = new ArrayList<String>();
        inputStr.add("one");
        inputStr.add("two");
        inputStr.add("three");
        inputStr.add("one");
        instanceStr = new DataList<String>(inputStr);
        assertEquals(4, instanceStr.getData().size());
        assertEquals(3, instanceStr.getHistogram().size());
        
        ArrayList<Integer> inputInt = null;
        DataList<Integer> instanceInt = new DataList<Integer>(inputInt);
        assertEquals(0, instanceInt.getData().size());
        
        inputInt = new ArrayList<Integer>();
        inputInt.add(1);
        inputInt.add(2);
        inputInt.add(3);
        inputInt.add(1);
        instanceInt = new DataList<Integer>(inputInt);
        assertEquals(4, instanceInt.getData().size());
        assertEquals(3, instanceInt.getHistogram().size());
    }
    
    @Test
    public void testConstructor_Array() {
        logger.info("\ntesting DataList(T[] array)");
        DataList<String> instanceStr = null;
        String[] inputStr = null;
        instanceStr = new DataList(inputStr);
        assertEquals(0, instanceStr.getData().size());
        
        inputStr = new String[] { "one", "three", "two", "one" };
        instanceStr = new DataList<String>(inputStr);
        assertEquals(4, instanceStr.getData().size());
        assertEquals(3, instanceStr.getHistogram().size());
        
        Integer[] inputInt = null;
        DataList<Integer> instanceInt = null;
        instanceInt = new DataList(inputInt);
        assertEquals(0, instanceInt.getData().size());
        
        inputInt = new Integer[] { 1, 2, 3, 1 };
        instanceInt = new DataList(inputInt);
        assertEquals(4, instanceInt.getData().size());
        assertEquals(3, instanceInt.getHistogram().size());
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