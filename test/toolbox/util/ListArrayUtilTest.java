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
import org.apache.logging.log4j.*;
import java.util.Arrays;

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
    public void testListToString() {
        logger.info("\ntesting listToString()");
        String empty = ListArrayUtil.DEFAULT_LIST_TO_STRING_OPEN + " " + ListArrayUtil.DEFAULT_LIST_TO_STRING_CLOSE;
        assertEquals(empty, ListArrayUtil.listToString(null, null));
        assertEquals(empty, ListArrayUtil.listToString(null, ""));
        assertEquals(empty, ListArrayUtil.listToString(null, ","));
        assertEquals(empty, ListArrayUtil.listToString(null, " "));
        assertEquals(empty, ListArrayUtil.listToString(null));
        
        List data = new ArrayList<String>();
        assertEquals(empty, ListArrayUtil.listToString(data, null));
        assertEquals(empty, ListArrayUtil.listToString(data, ""));
        assertEquals(empty, ListArrayUtil.listToString(data, ","));
        assertEquals(empty, ListArrayUtil.listToString(data, " "));
        assertEquals(empty, ListArrayUtil.listToString(data));
        
        data.add("first");
        assertEquals("< first >", ListArrayUtil.listToString(data, null));
        assertEquals("< first >", ListArrayUtil.listToString(data, ""));
        assertEquals("< first >", ListArrayUtil.listToString(data, ","));
        assertEquals("< first >", ListArrayUtil.listToString(data, " "));
        assertEquals("< first >", ListArrayUtil.listToString(data));
        
        data.add("second");
        assertEquals("< first, second >", ListArrayUtil.listToString(data, null));
        assertEquals("< firstsecond >", ListArrayUtil.listToString(data, ""));
        assertEquals("< first,second >", ListArrayUtil.listToString(data, ","));
        assertEquals("< first second >", ListArrayUtil.listToString(data, " "));
        assertEquals("< first, second >", ListArrayUtil.listToString(data));
    }
    
    @Test
    public void testListToStringOpen_input_sep_open_close() {
        logger.info("listToString(List input, String sep, String open, String close)");
        assertEquals("{  >", ListArrayUtil.listToString(null, null, "{", " >"));
        assertEquals("{  >", ListArrayUtil.listToString(null, "", "{", " >"));
        assertEquals("{  >", ListArrayUtil.listToString(null, " ", "{", " >"));
        assertEquals("{  >", ListArrayUtil.listToString(null, ",", "{", " >"));
        
        assertEquals("{   >", ListArrayUtil.listToString(null, null, "{ ", " >"));
        assertEquals("{   >", ListArrayUtil.listToString(null, "", "{ ", " >"));
        assertEquals("{   >", ListArrayUtil.listToString(null, " ", "{ ", " >"));
        assertEquals("{   >", ListArrayUtil.listToString(null, ",", "{ ", " >"));
        
        List data = new ArrayList<String>();
        String open = "{";
        String close = " >";
        String empty = open + " " + close;
        assertEquals(empty, ListArrayUtil.listToString(data, null, open, close));
        assertEquals(empty, ListArrayUtil.listToString(data, "", open, close));
        assertEquals(empty, ListArrayUtil.listToString(data, ",", open, close));
        assertEquals(empty, ListArrayUtil.listToString(data, " ", open, close));
        
        data.add("first");
        assertEquals(open + "first" + close, ListArrayUtil.listToString(data, null, open, close));
        assertEquals(open + "first" + close, ListArrayUtil.listToString(data, "", open, close));
        assertEquals(open + "first" + close, ListArrayUtil.listToString(data, ",", open, close));
        assertEquals(open + "first" + close, ListArrayUtil.listToString(data, " ", open, close));
        
        data.add("second");
        assertEquals(open + "first, second" + close, ListArrayUtil.listToString(data, null, open, close));
        assertEquals(open + "firstsecond" + close, ListArrayUtil.listToString(data, "", open, close));
        assertEquals(open + "first,second" + close, ListArrayUtil.listToString(data, ",", open, close));
        assertEquals(open + "first second" + close, ListArrayUtil.listToString(data, " ", open, close));
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
    public void testContains_int() {
        logger.info("\ntesting contains(int[] array, int value)");
        assertEquals(false, ListArrayUtil.contains(null, 0));
        assertEquals(false, ListArrayUtil.contains(new int[] { }, 0));
        assertEquals(false, ListArrayUtil.contains(new int[0], 0));
        assertEquals(true, ListArrayUtil.contains(new int[1], 0));  //only because arrays are initialized to 0
        assertEquals(false, ListArrayUtil.contains(new int[0], 1));
        
        assertEquals(false, ListArrayUtil.contains(new int[] { 1, 2, 3 }, 0));
        assertEquals(true, ListArrayUtil.contains(new int[] { 1, 2, 3 }, 2));
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
    
    @Test
    public void testDim_List_List() {
        logger.info("\ntesting dim(List<List> data)");
        
        int[] result = null;
        result = ListArrayUtil.dim(null);
        assertEquals(2, result.length);
        assertEquals(0, result[0]);
        assertEquals(0, result[1]);
        
        result = ListArrayUtil.dim(new ArrayList<List>());
        assertEquals(2, result.length);
        assertEquals(0, result[0]);
        assertEquals(0, result[1]);
        
        ArrayList<List> input = new ArrayList<List>();
        result = ListArrayUtil.dim(input);
        assertEquals(2, result.length);
        assertEquals(0, result[0]);
        assertEquals(0, result[1]);
        
        List<String> a = new ArrayList<String>();
        a.add("a");
        a.add("aa");
        
        List<String> b = new ArrayList<String>();
        b.add("b");
        b.add("bb");
        b.add("bbb");
        
        input.add(a);
        input.add(b);
        
        result = ListArrayUtil.dim(input);
        logger.debug(ListArrayUtil.arrayToString(result));
        assertEquals(2, result.length);
        assertEquals(2, result[0]);
        assertEquals(3, result[1]);
        
        
        result = ListArrayUtil.dim(input, false);
        logger.debug(ListArrayUtil.arrayToString(result));
        assertEquals(2, result.length);
        assertEquals(3, result[0]);
        assertEquals(2, result[1]);
    }
    
    //TODO:  test dim by cols
    
    @Test
    public void testHaveSameElements_doubleArray() {
        logger.info("\ntesting haveSameElements(double[] left, double[] right)");
        double[] a = null;
        double[] b = null;
        assertEquals(true, ListArrayUtil.haveSameElements(a, b));
        assertEquals(true, ListArrayUtil.haveSameElements(b, a));
        a = new double[0];
        assertEquals(false, ListArrayUtil.haveSameElements(a, b));
        assertEquals(false, ListArrayUtil.haveSameElements(b, a));
        b = new double[0];
        assertEquals(true, ListArrayUtil.haveSameElements(a, b));
        assertEquals(true, ListArrayUtil.haveSameElements(b, a));   //reflexive property should hold
        assertEquals(false, ListArrayUtil.haveSameElements(null, b));
        assertEquals(false, ListArrayUtil.haveSameElements(b, null));
        
        a = new double[] { -1.0, 2.0, 5.0 };
        assertEquals(false, ListArrayUtil.haveSameElements(a, b));
        assertEquals(false, ListArrayUtil.haveSameElements(b, a));
        
        b = new double[] { -1.0, 2.0, 5.0 };
        assertEquals(true, ListArrayUtil.haveSameElements(a, b));
        assertEquals(true, ListArrayUtil.haveSameElements(b, a));
        assertEquals(false, ListArrayUtil.haveSameElements(null, b));
        assertEquals(false, ListArrayUtil.haveSameElements(b, null));
        assertEquals(false, ListArrayUtil.haveSameElements(null, a));
        assertEquals(false, ListArrayUtil.haveSameElements(a, null));
        
        a[1] = 4.0;
        assertEquals(false, ListArrayUtil.haveSameElements(a, b));
        assertEquals(false, ListArrayUtil.haveSameElements(b, a));
        
         a = new double[] { -1.0, 2.0, 5.0, 1.0 };
         assertEquals(false, ListArrayUtil.haveSameElements(a, b));
         assertEquals(false, ListArrayUtil.haveSameElements(b, a));
    }
    
    @Test
    public void haveSameElements_genericArray() {
        logger.info("\nhaveSameElements(T[] left, T[] right)");
        Integer[] a = null;
        Integer[] b = null;
        assertEquals(true, ListArrayUtil.haveSameElements(a, b));
        assertEquals(true, ListArrayUtil.haveSameElements(b, a));
        a = new Integer[0];
        assertEquals(false, ListArrayUtil.haveSameElements(a, b));
        assertEquals(false, ListArrayUtil.haveSameElements(b, a));
        b = new Integer[0];
        assertEquals(true, ListArrayUtil.haveSameElements(a, b));
        assertEquals(true, ListArrayUtil.haveSameElements(b, a));   //reflexive property should hold
        assertEquals(false, ListArrayUtil.haveSameElements(null, b));
        assertEquals(false, ListArrayUtil.haveSameElements(b, null));
        
         a = new Integer[] { -1, 2, 5 };
        assertEquals(false, ListArrayUtil.haveSameElements(a, b));
        assertEquals(false, ListArrayUtil.haveSameElements(b, a));
        
        b = new Integer[] { -1, 2, 5 };
        assertEquals(true, ListArrayUtil.haveSameElements(a, b));
        assertEquals(true, ListArrayUtil.haveSameElements(b, a));
        assertEquals(false, ListArrayUtil.haveSameElements(null, b));
        assertEquals(false, ListArrayUtil.haveSameElements(b, null));
        assertEquals(false, ListArrayUtil.haveSameElements(null, a));
        assertEquals(false, ListArrayUtil.haveSameElements(a, null));
        
        a[1] = 4;
        assertEquals(false, ListArrayUtil.haveSameElements(a, b));
        assertEquals(false, ListArrayUtil.haveSameElements(b, a));
        
         a = new Integer[] { -1, 2, 5, 1 };
         assertEquals(false, ListArrayUtil.haveSameElements(a, b));
         assertEquals(false, ListArrayUtil.haveSameElements(b, a));
         
         
         //TODO:  check for things besides Integers
    }
    @Test
    public void testHaveSameElements_List() {
        logger.info("\ntesting haveSameElements(double[] left, double[] right)");
        List a = null;
        List b = null;
        assertEquals(true, ListArrayUtil.haveSameElements(a, b));
        assertEquals(true, ListArrayUtil.haveSameElements(b, a));
        a = new ArrayList();
        assertEquals(false, ListArrayUtil.haveSameElements(a, b));
        assertEquals(false, ListArrayUtil.haveSameElements(b, a));
        b = new ArrayList();
        assertEquals(true, ListArrayUtil.haveSameElements(a, b));
        assertEquals(true, ListArrayUtil.haveSameElements(b, a));   //reflexive property should hold
        assertEquals(false, ListArrayUtil.haveSameElements(null, b));
        assertEquals(false, ListArrayUtil.haveSameElements(b, null));
        
        a.add(-1.0);
        a.add(2.0);
        a.add(5.0);
        assertEquals(false, ListArrayUtil.haveSameElements(a, b));
        assertEquals(false, ListArrayUtil.haveSameElements(b, a));
        
        b.add(-1.0);
        b.add(2.0);
        b.add(5.0);
        assertEquals(true, ListArrayUtil.haveSameElements(a, b));
        assertEquals(true, ListArrayUtil.haveSameElements(b, a));
        assertEquals(false, ListArrayUtil.haveSameElements(null, b));
        assertEquals(false, ListArrayUtil.haveSameElements(b, null));
        assertEquals(false, ListArrayUtil.haveSameElements(null, a));
        assertEquals(false, ListArrayUtil.haveSameElements(a, null));
        
        a.set(1, 4.0);
        assertEquals(false, ListArrayUtil.haveSameElements(a, b));
        assertEquals(false, ListArrayUtil.haveSameElements(b, a));
        
        a = new ArrayList();
        a.add(-1.0);
        a.add(2.0);
        a.add(5.0);
        a.add(1.0);
        assertEquals(false, ListArrayUtil.haveSameElements(a, b));
        assertEquals(false, ListArrayUtil.haveSameElements(b, a));
        
        //now with Strings
        a = new ArrayList();
        b = new ArrayList();
        a.add("-1.0");
        a.add("2.0");
        a.add("5.0");
        assertEquals(false, ListArrayUtil.haveSameElements(a, b));
        assertEquals(false, ListArrayUtil.haveSameElements(b, a));
        
        b.add("-1.0");
        b.add("2.0");
        b.add("5.0");
        assertEquals(true, ListArrayUtil.haveSameElements(a, b));
        assertEquals(true, ListArrayUtil.haveSameElements(b, a));
        assertEquals(false, ListArrayUtil.haveSameElements(null, b));
        assertEquals(false, ListArrayUtil.haveSameElements(b, null));
        assertEquals(false, ListArrayUtil.haveSameElements(null, a));
        assertEquals(false, ListArrayUtil.haveSameElements(a, null));
        
        a.set(1, "4.0");
        assertEquals(false, ListArrayUtil.haveSameElements(a, b));
        assertEquals(false, ListArrayUtil.haveSameElements(b, a));
        
        a = new ArrayList();
        a.add("-1.0");
        a.add("2.0");
        a.add("5.0");
        a.add("1.0");
        assertEquals(false, ListArrayUtil.haveSameElements(a, b));
        assertEquals(false, ListArrayUtil.haveSameElements(b, a));
        
        //now a mix
        a = new ArrayList();
        b = new ArrayList();
        a.add(-1.0);
        a.add("2.0");
        a.add(5.0);
        assertEquals(false, ListArrayUtil.haveSameElements(a, b));
        assertEquals(false, ListArrayUtil.haveSameElements(b, a));
        
        b.add(-1.0);
        b.add("2.0");
        b.add(5.0);
        assertEquals(true, ListArrayUtil.haveSameElements(a, b));
        assertEquals(true, ListArrayUtil.haveSameElements(b, a));
        assertEquals(false, ListArrayUtil.haveSameElements(null, b));
        assertEquals(false, ListArrayUtil.haveSameElements(b, null));
        assertEquals(false, ListArrayUtil.haveSameElements(null, a));
        assertEquals(false, ListArrayUtil.haveSameElements(a, null));
        
        a.set(1, "4.0");
        assertEquals(false, ListArrayUtil.haveSameElements(a, b));
        assertEquals(false, ListArrayUtil.haveSameElements(b, a));
        
        a = new ArrayList();
        a.add(-1.0);
        a.add("2.0");
        a.add(5.0);
        a.add("1.0");
        assertEquals(false, ListArrayUtil.haveSameElements(a, b));
        assertEquals(false, ListArrayUtil.haveSameElements(b, a));
        
        a = new ArrayList();
        b = new ArrayList();
        a.add(-1.0);
        a.add("2.0");
        a.add(5.0);
        b.add(-1.0);
        b.add(2.0);
        b.add(5.0);
        assertEquals(false, ListArrayUtil.haveSameElements(a, b));
        assertEquals(false, ListArrayUtil.haveSameElements(b, a));
    }
    
    @Test
    public void testFindNumDiffs_List_List() {
        logger.info("\ntesting findNumDiffs(List<Double> a, List<Double> b)");
        List a = null;
        List b = null;
        assertEquals(0, ListArrayUtil.findNumDiffs(a, b));
        assertEquals(0, ListArrayUtil.findNumDiffs(b, a));
        a = new ArrayList();
        assertEquals(0, ListArrayUtil.findNumDiffs(a, b));
        assertEquals(0, ListArrayUtil.findNumDiffs(b, a));
        b = new ArrayList();
        assertEquals(0, ListArrayUtil.findNumDiffs(a, b));
        assertEquals(0, ListArrayUtil.findNumDiffs(b, a));
        a = null;
        assertEquals(0, ListArrayUtil.findNumDiffs(a, b));
        assertEquals(0, ListArrayUtil.findNumDiffs(b, a));
        
        b.add("hi");
        b.add("there");
        assertEquals(2, ListArrayUtil.findNumDiffs(a, b));
        assertEquals(2, ListArrayUtil.findNumDiffs(b, a));
        
        a = new ArrayList();
        a.add("hi");
        a.add("thar");
        assertEquals(1, ListArrayUtil.findNumDiffs(a, b));
        assertEquals(1, ListArrayUtil.findNumDiffs(b, a));
        
        a.set(1, "there");
        assertEquals(0, ListArrayUtil.findNumDiffs(a, b));
        assertEquals(0, ListArrayUtil.findNumDiffs(b, a));
        
        a.add("buddy");
        assertEquals(1, ListArrayUtil.findNumDiffs(a, b));
        assertEquals(1, ListArrayUtil.findNumDiffs(b, a));
        
        a.set(2, 2);
        b.add(2);
        assertEquals(0, ListArrayUtil.findNumDiffs(a, b));
        assertEquals(0, ListArrayUtil.findNumDiffs(b, a));
        
        b.add(3);
        /**a.add(3);
        assertEquals(0, ListArrayUtil.findNumDiffs(a, b));
        assertEquals(0, ListArrayUtil.findNumDiffs(b, a));
        b.add(27.089);
        a.add(27.089);
        assertEquals(0, ListArrayUtil.findNumDiffs(a, b));
        assertEquals(0, ListArrayUtil.findNumDiffs(b, a));
        b.add(45l);
        a.add(45l);
        assertEquals(0, ListArrayUtil.findNumDiffs(a, b));
        assertEquals(0, ListArrayUtil.findNumDiffs(b, a));*/
        b.add(27.089);
        b.add(45l);
        assertEquals(3, ListArrayUtil.findNumDiffs(a, b));
        assertEquals(3, ListArrayUtil.findNumDiffs(b, a));
        
        a.add(3);
        a.add(27.089);
        a.add(45l);
        assertEquals(0, ListArrayUtil.findNumDiffs(a, b));
        assertEquals(0, ListArrayUtil.findNumDiffs(b, a));
        
        a.add("a");
        assertEquals(1, ListArrayUtil.findNumDiffs(a, b));
        assertEquals(1, ListArrayUtil.findNumDiffs(b, a));
        b.add("b");
        
        //a.add(new SomeClass("one", "two"));
        //b.add(new SomeClass("one", "two"));
        assertEquals(1, ListArrayUtil.findNumDiffs(a, b));
        assertEquals(1, ListArrayUtil.findNumDiffs(b, a));
        
        a.add(new SomeClass("one", "two"));
        b.add(new SomeClass("one", "two"));
        logger.debug(a.equals(b));
        assertEquals(1, ListArrayUtil.findNumDiffs(a, b));  //is 2 when there the equals method is not in SomeClass
        assertEquals(1, ListArrayUtil.findNumDiffs(b, a));
        /**/
    }
    
    @Test
    public void testFindNumDiffs_int_arrays() {
        logger.info("\ntesting findNumDiffs(int[] a, int[] b)");
        int[] a = null;
        int[] b = null;
        assertEquals(0, ListArrayUtil.findNumDiffs(a, b));
        assertEquals(0, ListArrayUtil.findNumDiffs(b, a));
        
        a = new int[0];
        assertEquals(0, ListArrayUtil.findNumDiffs(a, b));
        assertEquals(0, ListArrayUtil.findNumDiffs(b, a));
        
        b = new int[0];
        assertEquals(0, ListArrayUtil.findNumDiffs(a, b));
        assertEquals(0, ListArrayUtil.findNumDiffs(b, a));
        
        a = null;   //redundant I suppose but won't hurt
        assertEquals(0, ListArrayUtil.findNumDiffs(a, b));
        assertEquals(0, ListArrayUtil.findNumDiffs(b, a));
        
        a = new int[] { 0 };
        assertEquals(1, ListArrayUtil.findNumDiffs(a, b));
        assertEquals(1, ListArrayUtil.findNumDiffs(b, a));
        
        b = new int[] { 0 };
        assertEquals(0, ListArrayUtil.findNumDiffs(a, b));
        assertEquals(0, ListArrayUtil.findNumDiffs(b, a));
        
        b[0] = 1;
        assertEquals(1, ListArrayUtil.findNumDiffs(a, b));
        assertEquals(1, ListArrayUtil.findNumDiffs(b, a));
        
        b = new int[] { 0, 1, 2, 3 };
        assertEquals(3, ListArrayUtil.findNumDiffs(a, b));
        assertEquals(3, ListArrayUtil.findNumDiffs(b, a));
        
        a = new int[] { -5, 2, 1, 8, 15, -27 };
        b = new int[] { 4, 2, 1, -27, 15 };
        assertEquals(3, ListArrayUtil.findNumDiffs(a, b));
        assertEquals(3, ListArrayUtil.findNumDiffs(b, a));
    }
    
    @Test
    public void testFindNumDiffs_double_arrays() {
	logger.info("\ntesting findNumDiffs(double[] a, double[] b)");
        double[] a = null;
        double[] b = null;
        assertEquals(0, ListArrayUtil.findNumDiffs(a, b));
        assertEquals(0, ListArrayUtil.findNumDiffs(b, a));
        
        a = new double[0];
        assertEquals(0, ListArrayUtil.findNumDiffs(a, b));
        assertEquals(0, ListArrayUtil.findNumDiffs(b, a));
        
        b = new double[0];
        assertEquals(0, ListArrayUtil.findNumDiffs(a, b));
        assertEquals(0, ListArrayUtil.findNumDiffs(b, a));
        
        a = null;   //redundant I suppose but won't hurt
        assertEquals(0, ListArrayUtil.findNumDiffs(a, b));
        assertEquals(0, ListArrayUtil.findNumDiffs(b, a));
        
        a = new double[] { 0.0 };
        assertEquals(1, ListArrayUtil.findNumDiffs(a, b));
        assertEquals(1, ListArrayUtil.findNumDiffs(b, a));
        
        b = new double[] { 0.0 };
        assertEquals(0, ListArrayUtil.findNumDiffs(a, b));
        assertEquals(0, ListArrayUtil.findNumDiffs(b, a));
        
        b[0] = 1.0;
        assertEquals(1, ListArrayUtil.findNumDiffs(a, b));
        assertEquals(1, ListArrayUtil.findNumDiffs(b, a));
        
        b = new double[] { 0.0, 1.0, 2.0, 3.0 };
        assertEquals(3, ListArrayUtil.findNumDiffs(a, b));
        assertEquals(3, ListArrayUtil.findNumDiffs(b, a));
        
        a = new double[] { -5.0, 2.0, 1.0, 8.0, 15.0, -27.0 };
        b = new double[] { 4.0, 2.0, 1.0, -27.0, 15.0 };
        assertEquals(3, ListArrayUtil.findNumDiffs(a, b));
        assertEquals(3, ListArrayUtil.findNumDiffs(b, a));
    }
    
    @Test
    public void testMerge() {
        logger.info("\nteting merge()");
        List<String> left = new ArrayList<String>();
        List<String> right = new ArrayList<String>();
        List<String> result = null;
        
        result = ListArrayUtil.merge(null, null);
        assertEquals(0, ListArrayUtil.merge(null, null).size());
        assertEquals(0, ListArrayUtil.merge(left, null).size());
        assertEquals(0, ListArrayUtil.merge(null, left).size());
        assertEquals(0, ListArrayUtil.merge(right, null).size());
        assertEquals(0, ListArrayUtil.merge(null, right).size());
        assertEquals(0, ListArrayUtil.merge(left, right).size());
        assertEquals(0, ListArrayUtil.merge(right, left).size());
        
        left.addAll(Arrays.asList("hat", "ball", "gloves", "bat", "field"));
        right.addAll(Arrays.asList("ball", "field", "cleats", "shin guards", "hat"));
        result = ListArrayUtil.merge(left, right);
        assertEquals(10, result.size());
    }
    
    private class SomeClass {
        String paramOne;
        String paramTwo;
        
        public SomeClass(String paramOne, String paramTwo) {
            this.paramOne = paramOne;
            this.paramTwo = paramTwo;
        }
        
        public boolean equals(Object other) {
            if(other instanceof SomeClass && ((SomeClass)(other)).paramOne.equals(this.paramOne) && ((SomeClass)(other)).paramTwo.equals(this.paramTwo)) {
                return true;
            }
            return false;
        }
    }
    
    @Test
    public void testGetLogger() {
        logger.info("\ntesting getLogger");
        Logger logger = ListArrayUtil.getLogger(MathUtil.class, Level.DEBUG);
        logger.debug("first");
        logger = ListArrayUtil.getLogger(MathUtil.class, Level.DEBUG);
        logger.debug("second");     //Should only print once, not twice!
    }
}