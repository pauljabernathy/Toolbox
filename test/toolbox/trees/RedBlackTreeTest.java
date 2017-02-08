/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package toolbox.trees;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

import org.apache.logging.log4j.*;
import toolbox.util.ListArrayUtil;
import toolbox.trees.RedBlackTree.Color;

/**
 *
 * @author pabernathy
 */
public class RedBlackTreeTest {
    
    private static Logger logger;
    
    public RedBlackTreeTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
        logger = ListArrayUtil.getLogger(RedBlackTreeTest.class, Level.INFO);
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
     * Test of setColor method, of class RedBlackTree.
     */
    @Test
    public void testSetColor() {
        System.out.println("setColor");
        RedBlackTree.Color color = Color.RED;
        RedBlackTree instance = new RedBlackTree(null);
        RedBlackTree expResult = null;
        RedBlackTree result = instance.setColor(color);
        assertEquals(instance, result);
        assertEquals(Color.RED, instance.color);
        instance.setColor(Color.BLACK);
        assertEquals(Color.BLACK, instance.color);
    }

    /**
     * Test of equalsValue method, of class RedBlackTree.
     */
    @Test
    public void testValueEquals_RedBlackTree() {
        System.out.println("equalsValue");
        RedBlackTree<String> instance1 = new RedBlackTree<>(null);
        RedBlackTree<String> instance2 = null;
        assertEquals(false, instance1.valueEquals(instance2));
        
        instance2 = new RedBlackTree<>(null);
        assertEquals(true, instance1.valueEquals(instance2));
        
        instance2 = new RedBlackTree<>("abc123");
        assertEquals(false, instance1.valueEquals(instance2));
        
        
        instance1 = new RedBlackTree<>("abc123");
        instance2 = null;
        assertEquals(false, instance1.valueEquals(instance2));
        
        instance2 = new RedBlackTree<>(null);
        assertEquals(false, instance1.valueEquals(instance2));
        
        instance2 = new RedBlackTree<>("abc123");
        assertEquals(true, instance1.valueEquals(instance2));
        
        
        RedBlackTree<Integer> instance3 = new RedBlackTree<>(1);
        RedBlackTree<Integer> instance4 = new RedBlackTree<>(1);
        assertEquals(false, instance3.equals(instance4));
        assertEquals(true, instance3.valueEquals(instance4));
        
        assertEquals(false, instance1.equals(instance3));
        //assertEquals(true, instance1.equalsValue(instance3));
        
        instance1 = new RedBlackTree<>(null);
        assertEquals(false, instance1.equals(instance2));
        assertEquals(false, instance1.valueEquals(instance2));
        instance1 = new RedBlackTree<>("abc123");
        instance1 = new RedBlackTree<>(null);
        assertEquals(false, instance1.equals(instance2));
        assertEquals(false, instance1.valueEquals(instance2));
    }
    
    @Test
    public void testValueEquals_Generic() {
        logger.info("\ntesting valueEquals(T value)");
        RedBlackTree<String> instance = new RedBlackTree<>(null);
        String s = null;
        assertEquals(true, instance.valueEquals(s));
        assertEquals(false, instance.valueEquals("abc123"));
        
        instance = new RedBlackTree<>("abc123");
        assertEquals(false, instance.valueEquals(s));
        assertEquals(true, instance.valueEquals("abc123"));
    }

    /**
     * Test of contains method, of class RedBlackTree.
     */
    @Test
    public void testContains() {
        System.out.println("contains");
        Object value = null;
        RedBlackTree instance = null;
        boolean expResult = false;
        boolean result = instance.contains(null);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

        @Test
    public void testGet() {
        logger.info("\ntesting get()");
        RedBlackTree<String> instance = new RedBlackTree<String>("ijkl");
        assertEquals(instance, instance.get("ijkl"));
        RedBlackTree<String> abcd = instance.insert("abcd");
        assertEquals(abcd, abcd.get("abcd"));
        assertEquals(abcd, instance.get("abcd"));
        RedBlackTree<String> efgh = instance.insert("efgh");
        RedBlackTree<String> mnop = instance.insert("mnop");
        assertEquals("efgh", instance.get("efgh").value);
        assertEquals(efgh, instance.get("efgh"));
        assertEquals(mnop, instance.get("mnop"));
        assertEquals(null, instance.get("mnoo"));
    }
    
    /**
     * Test of insert method, of class RedBlackTree.
     */
    @Test
    public void testInsert() {
        logger.info("\ninsert");
        Object value = null;
        RedBlackTree<String> instance = new RedBlackTree<String> ("lmno");
        RedBlackTree<String> hijk = instance.insert("hijk");
        assertEquals("hijk", hijk.value);
        assertEquals(null, hijk.left);
        assertEquals(null, hijk.right);
        assertEquals(instance, hijk.parent);
        
        RedBlackTree<String> mmmm = instance.insert("mmmm");
        assertEquals("mmmm", mmmm.value);
        assertEquals(null, mmmm.left);
        assertEquals(null, mmmm.right);
        assertEquals("hijk", instance.left.value);
        assertEquals("mmmm", instance.right.value);
        
        RedBlackTree<String> mmnn = instance.insert("mmnn");
        assertEquals(true, instance.right.right.valueEquals("mmnn"));
        
        RedBlackTree<Integer> nums = new RedBlackTree<Integer>(11);
        nums.insert(2);
        nums.insert(1);
        nums.insert(7);
        nums.insert(5);
        nums.insert(4);
        nums.insert(14);
        nums.insert(8);
        nums.insert(15);
        //before rotation, should look like the Tree on Algorithms page 269
        assertEquals(2, nums.left.value.intValue());
        assertEquals(1, nums.left.left.value.intValue());
        assertEquals(7, nums.left.right.value.intValue());
        assertEquals(5, nums.left.right.left.value.intValue());
        assertEquals(4, nums.left.right.left.left.value.intValue());
        assertEquals(8, nums.left.right.right.value.intValue());
        assertEquals(14, nums.right.value.intValue());
        assertEquals(15, nums.right.right.value.intValue());
        assertEquals(11, nums.value.intValue());
        
        nums.setColor(Color.BLACK);
        nums.left.setColor(Color.RED);
        nums.left.left.setColor(Color.BLACK);
        nums.left.right.setColor(Color.BLACK);
        nums.left.right.left.setColor(Color.RED);
        nums.left.right.left.left.setColor(Color.RED);
        nums.left.right.right.setColor(Color.RED);
        nums.right.setColor(Color.BLACK);
        nums.right.right.setColor(Color.BLACK);
        
        nums.left.right.left.left.rebalance();
        assertEquals(7, nums.value.intValue());
        assertEquals(2, nums.left.value.intValue());
        assertEquals(1, nums.left.left.value.intValue());
        assertEquals(5, nums.left.right.value.intValue());
        assertEquals(4, nums.left.right.left.value.intValue());
        assertEquals(11, nums.right.value.intValue());
        assertEquals(8, nums.right.left.value.intValue());
        assertEquals(14, nums.right.right.value.intValue());
        assertEquals(15, nums.right.right.right.value.intValue());
    }

    /**
     * Test of insertOrUpdate method, of class RedBlackTree.
     */
    @Test
    public void testInsertOrUpdate() {
        System.out.println("insertOrUpdate");
        Object value = null;
        RedBlackTree instance = null;
        RedBlackTree expResult = null;
        RedBlackTree result = instance.insertOrUpdate(null);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of rebalance method, of class RedBlackTree.
     */
    @Test
    public void testRebalance() {
        System.out.println("rebalance");
        
        RedBlackTree<Integer> nums = new RedBlackTree<>(11);
        nums.left = new RedBlackTree<>(2);
        nums.left.left = new RedBlackTree<Integer>(1);
        nums.left.right = new RedBlackTree<Integer>(7);
        nums.left.right.left = new RedBlackTree<Integer>(5);
        nums.left.right.left.left = new RedBlackTree<Integer>(4);
        nums.left.right.right = new RedBlackTree<Integer>(8);
        nums.right = new RedBlackTree<Integer>(14);
        nums.right.right = new RedBlackTree<Integer>(15);

        //before rotation, should look like the Tree on Algorithms page 269
        assertEquals(2, nums.left.value.intValue());
        assertEquals(1, nums.left.left.value.intValue());
        assertEquals(7, nums.left.right.value.intValue());
        assertEquals(5, nums.left.right.left.value.intValue());
        assertEquals(4, nums.left.right.left.left.value.intValue());
        assertEquals(8, nums.left.right.right.value.intValue());
        assertEquals(14, nums.right.value.intValue());
        assertEquals(15, nums.right.right.value.intValue());
        assertEquals(11, nums.value.intValue());
        
        nums.setColor(Color.BLACK);
        nums.left.setColor(Color.RED);
        nums.left.left.setColor(Color.BLACK);
        nums.left.right.setColor(Color.BLACK);
        nums.left.right.left.setColor(Color.RED);
        nums.left.right.left.left.setColor(Color.RED);
        nums.left.right.right.setColor(Color.RED);
        nums.right.setColor(Color.BLACK);
        nums.right.right.setColor(Color.BLACK);
        
        nums.left.right.left.left.rebalance();
        assertEquals(7, nums.value.intValue());
        assertEquals(2, nums.left.value.intValue());
        assertEquals(1, nums.left.left.value.intValue());
        assertEquals(5, nums.left.right.value.intValue());
        assertEquals(4, nums.left.right.left.value.intValue());
        assertEquals(11, nums.right.value.intValue());
        assertEquals(8, nums.right.left.value.intValue());
        assertEquals(14, nums.right.right.value.intValue());
        assertEquals(15, nums.right.right.right.value.intValue());
    }
    
}
