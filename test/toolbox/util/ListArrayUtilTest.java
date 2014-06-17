/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package toolbox.util;

import toolbox.util.ListArrayUtil;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

import java.util.List;
import java.util.ArrayList;
import org.apache.log4j.*;

/**
 *
 * @author paul
 */
public class ListArrayUtilTest {

    private static Logger logger;
    
    public ListArrayUtilTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
        logger = ListArrayUtil.getLogger(ListArrayUtilTest.class, Level.DEBUG);
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
    public void testShowList() {
    }

    @Test
    public void testShowArray() {
    }
    
    @Test
    public void testContains_String() {
        logger.info("\ntesting contains(String[] array, String value)");
        String[] array = null;
        String value = null;
        assertEquals(false, ListArrayUtil.contains(array, value));
        assertEquals(false, ListArrayUtil.contains(array, ""));
        assertEquals(false, ListArrayUtil.contains(array, "value"));
        
        array = new String[] { };
        assertEquals(false, ListArrayUtil.contains(array, value));
        assertEquals(false, ListArrayUtil.contains(array, ""));
        assertEquals(false, ListArrayUtil.contains(array, "value"));
        
        array = new String[] { "val", "values", "something else" };
        assertEquals(false, ListArrayUtil.contains(array, value));
        assertEquals(false, ListArrayUtil.contains(array, ""));
        assertEquals(false, ListArrayUtil.contains(array, "value"));
        
        array = new String[] { "val", "values", "value", "something else" };
        assertEquals(false, ListArrayUtil.contains(array, value));
        assertEquals(false, ListArrayUtil.contains(array, ""));
        assertEquals(true, ListArrayUtil.contains(array, "value"));
        
        array = new String[] { "val", "values", "value", null, "something else" };
        assertEquals(true, ListArrayUtil.contains(array, value));
        assertEquals(false, ListArrayUtil.contains(array, ""));
        assertEquals(true, ListArrayUtil.contains(array, "value"));
        
        array = new String[] { "val", "values", null, "value", "something else" };
        assertEquals(true, ListArrayUtil.contains(array, value));
        assertEquals(false, ListArrayUtil.contains(array, ""));
        assertEquals(true, ListArrayUtil.contains(array, "value"));
    }
    
    @Test
    public void testGetCondensedPermutations() {
        logger.info("\ntesting getCondensedPermutations()");
        int[] input = null;
        input = new int[] { 5, 4, 3, 2, 2 };
        input = new int[]{ 2, 5, 7, 8, 12, 13 };//{  3, 6  };
        List<int[]> result = ListArrayUtil.getCondensedPermutations(input);
        if(result == null || result.isEmpty()) {
            fail("returned an empty list");
        }
        for(int[] current : result) {
            //Utilities.showArray(current);
        }
        input = new int[] { 0, 1, 2 };
        result = ListArrayUtil.getCondensedPermutations(input);
        assertEquals(7, result.size());
        //logger.debug(ListArrayUtil.listToString(result));
        for(int[] current : result) {
            logger.debug(ListArrayUtil.arrayToString(current));
        }
        logger.debug("---");
        input = new int[] { 3, 1, 2 };
        result = ListArrayUtil.getCondensedPermutations(input);
        assertEquals(7, result.size());
        //logger.debug(ListArrayUtil.listToString(result));
        for(int[] current : result) {
            logger.debug(ListArrayUtil.arrayToString(current));
        }
    }
    
    @Test
    public void testGetPermutations_array() {
        System.out.println("\ntesting getPermutations");
        int[] input = null;
        input = new int[] { 5, 4, 3, 2, 2 };
        ListArrayUtil.showArray(input);
        ListArrayUtil.getPermutations(input);
    }
        
    @Test
    public void testToBinaryArray() {
        System.out.println("\ntesting toBinaryArray()");
        //Utilities.showArray(ListArrayUtil.toBinaryArray(-1));
        ListArrayUtil.showArray(ListArrayUtil.toBinaryArray(0));
        //Utilities.showArray(ListArrayUtil.toBinaryArray(1));
        //Utilities.showArray(ListArrayUtil.toBinaryArray(2));
        //Utilities.showArray(ListArrayUtil.toBinaryArray(3));
        ListArrayUtil.showArray(ListArrayUtil.toBinaryArray(4));
        //Utilities.showArray(ListArrayUtil.toBinaryArray(5));
        ListArrayUtil.showArray(ListArrayUtil.toBinaryArray(6));
        ListArrayUtil.showArray(ListArrayUtil.toBinaryArray(7));
        ListArrayUtil.showArray(ListArrayUtil.toBinaryArray(8));
        ListArrayUtil.showArray(ListArrayUtil.toBinaryArray(9));
        ListArrayUtil.showArray(ListArrayUtil.toBinaryArray(10));
        ListArrayUtil.showArray(ListArrayUtil.toBinaryArray(11));
        ListArrayUtil.showArray(ListArrayUtil.toBinaryArray(12));
        ListArrayUtil.showArray(ListArrayUtil.toBinaryArray(13));
        assertArrayEquals(new int[] { 0 }, ListArrayUtil.toBinaryArray(0));
        assertArrayEquals(new int[] { 1 }, ListArrayUtil.toBinaryArray(1));
        assertArrayEquals(new int[] { 1, 0 }, ListArrayUtil.toBinaryArray(2));
        assertArrayEquals(new int[] { 1, 1 }, ListArrayUtil.toBinaryArray(3));
        assertArrayEquals(new int[] { 1, 0, 0 }, ListArrayUtil.toBinaryArray(4));
        assertArrayEquals(new int[] { 1, 0, 1 }, ListArrayUtil.toBinaryArray(5));
        assertArrayEquals(new int[] { 1, 1, 0 }, ListArrayUtil.toBinaryArray(6));
        assertArrayEquals(new int[] { 1, 1, 1 }, ListArrayUtil.toBinaryArray(7));
        assertArrayEquals(new int[] { 1, 0, 0, 0 }, ListArrayUtil.toBinaryArray(8));
        assertArrayEquals(new int[] { 1, 0, 0, 1 }, ListArrayUtil.toBinaryArray(9));
        assertArrayEquals(new int[] { 1, 0, 1, 0 }, ListArrayUtil.toBinaryArray(10));
        assertArrayEquals(new int[] { 1, 0, 1, 1 }, ListArrayUtil.toBinaryArray(11));
        assertArrayEquals(new int[] { 1, 1, 0, 0 }, ListArrayUtil.toBinaryArray(12));
        assertArrayEquals(new int[] { 1, 1, 0, 1 }, ListArrayUtil.toBinaryArray(13));
    }
    
    @Test
    public void testAndAndCondense() {
        System.out.println("\ntesting andAndCondense()");
        int[] source = new int[] { 1, 2, 3, 4, 5};
        int[] bits = new int[] { 0, 1, 0, 1, 1 };
        ListArrayUtil.showArray(ListArrayUtil.andAndCondense(source, bits));
        bits = new int[] { 0, 0, 0, 0, 0 };
        ListArrayUtil.showArray(ListArrayUtil.andAndCondense(source, bits));
        bits = new int[] { 1, 1, 1, 1, 1 };
        ListArrayUtil.showArray(ListArrayUtil.andAndCondense(source, bits));
    }
    
    @Test
    public void testAnd() {
        System.out.println("\ntesting and()");
        int[] source = new int[] { 1, 2, 3, 4, 5};
        int[] bits = new int[] { 0, 1, 0, 1, 1 };
        int[] result = null;
        assertEquals(0, ListArrayUtil.and(new int[] { 1, 2, 3, 4, 5}, new int[] { 0, 2, 0, 2 }).length);
        assertEquals(0, ListArrayUtil.and(new int[] { 1, 2, 3, 4}, new int[] { 0, 2, 0, 2, 5 }).length);
        
        assertEquals(0, ListArrayUtil.and(null, new int[] { 0, 2, 0, 2 }).length);
        assertEquals(0, ListArrayUtil.and(new int[] { }, new int[] { 0, 2, 0, 2 }).length);
        
        assertEquals(0, ListArrayUtil.and(new int[] { 1, 2, 3, 4, 5}, null).length);
        assertEquals(0, ListArrayUtil.and(new int[] { 1, 2, 3, 4, 5}, new int[] { }).length);
        
        //TODO:  automated test - test by value, not by visual
        result = ListArrayUtil.and(source, bits);
        assertEquals(0, result[0]);
        assertEquals(2, result[1]);
        assertEquals(0, result[2]);
        assertArrayEquals(new int[] { 0, 2, 0, 4, 5}, result);
        assertArrayEquals(new int[] { 0, 2, 0, 4, 5 }, ListArrayUtil.and(source, new int[] { 0, 2, 0, 2, 1 }));
        assertArrayEquals(new int[] { 0, 0, 0, 4, 5}, ListArrayUtil.and(new int[] { 1, 0, 3, 4, 5}, new int[] { 0, 2, 0, 2, 1 }));
        assertArrayEquals(new int[] { 0, 2, 0, 2, 1}, ListArrayUtil.and(new int[] { 0, 2, 0, 2, 1 }, new int[] { 1, 2, 3, 4, 5}));
    }
    
    @Test
    public void testOr() {
        System.out.println("\ntesting or()");
        System.out.println(ListArrayUtil.arrayToString(ListArrayUtil.or(new int[] { 1, 0, 0 }, new int[] { 0, 0, 0, 1})));
        System.out.println(ListArrayUtil.arrayToString(ListArrayUtil.or(new int[] { 1, 0, 0, 1, 0, 0 }, new int[] { 0, 0, 0, 1})));
    }
    
}