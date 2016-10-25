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

/**
 *
 * @author pabernathy
 */
public class BinaryTreeNodeTest {
    
    private static Logger logger;
    
    public BinaryTreeNodeTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
        logger = ListArrayUtil.getLogger(BinaryTreeNodeTest.class, Level.INFO);
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
     * Test of equalsValue method, of class BinaryTreeNode.
     */
    @Test
    public void testValueEquals_BinaryTreeNode() {
        System.out.println("equalsValue");
        BinaryTreeNode<String> instance1 = new BinaryTreeNode<>(null);
        BinaryTreeNode<String> instance2 = null;
        assertEquals(false, instance1.valueEquals(instance2));
        
        instance2 = new BinaryTreeNode<>(null);
        assertEquals(true, instance1.valueEquals(instance2));
        
        instance2 = new BinaryTreeNode<>("abc123");
        assertEquals(false, instance1.valueEquals(instance2));
        
        
        instance1 = new BinaryTreeNode<>("abc123");
        instance2 = null;
        assertEquals(false, instance1.valueEquals(instance2));
        
        instance2 = new BinaryTreeNode<>(null);
        assertEquals(false, instance1.valueEquals(instance2));
        
        instance2 = new BinaryTreeNode<>("abc123");
        assertEquals(true, instance1.valueEquals(instance2));
        
        
        BinaryTreeNode<Integer> instance3 = new BinaryTreeNode<>(1);
        BinaryTreeNode<Integer> instance4 = new BinaryTreeNode<>(1);
        assertEquals(false, instance3.equals(instance4));
        assertEquals(true, instance3.valueEquals(instance4));
        
        assertEquals(false, instance1.equals(instance3));
        //assertEquals(true, instance1.equalsValue(instance3));
        
        instance1 = new BinaryTreeNode<>(null);
        assertEquals(false, instance1.equals(instance2));
        assertEquals(false, instance1.valueEquals(instance2));
        instance1 = new BinaryTreeNode<>("abc123");
        instance1 = new BinaryTreeNode<>(null);
        assertEquals(false, instance1.equals(instance2));
        assertEquals(false, instance1.valueEquals(instance2));
    }
    
    @Test
    public void testValueEquals_Generic() {
        logger.info("\ntesting valueEquals(T value)");
        BinaryTreeNode<String> instance = new BinaryTreeNode<>(null);
        String s = null;
        assertEquals(true, instance.valueEquals(s));
        assertEquals(false, instance.valueEquals("abc123"));
        
        instance = new BinaryTreeNode<>("abc123");
        assertEquals(false, instance.valueEquals(s));
        assertEquals(true, instance.valueEquals("abc123"));
    }

    @Test
    public void testContains() {
        logger.info("\ntesting contains");
    }
    
    @Test
    public void testGet() {
        logger.info("\ntesting get()");
        BinaryTreeNode<String> instance = new BinaryTreeNode<String>("ijkl");
        assertEquals(instance, instance.get("ijkl"));
        BinaryTreeNode<String> abcd = instance.insert("abcd");
        assertEquals(abcd, abcd.get("abcd"));
        assertEquals(abcd, instance.get("abcd"));
        BinaryTreeNode<String> efgh = instance.insert("efgh");
        BinaryTreeNode<String> mnop = instance.insert("mnop");
        assertEquals("efgh", instance.get("efgh").value);
        assertEquals(efgh, instance.get("efgh"));
        assertEquals(mnop, instance.get("mnop"));
        assertEquals(null, instance.get("mnoo"));
    }
    
    /**
     * Test of insert method, of class BinaryTreeNode.
     */
    @Test
    public void testInsert() {
        logger.info("\ninsert");
        Object value = null;
        BinaryTreeNode<String> instance = new BinaryTreeNode<String> ("lmno");
        BinaryTreeNode<String> hijk = instance.insert("hijk");
        assertEquals("hijk", hijk.value);
        assertEquals(null, hijk.left);
        assertEquals(null, hijk.right);
        assertEquals(instance, hijk.parent);
        
        BinaryTreeNode<String> mmmm = instance.insert("mmmm");
        assertEquals("mmmm", mmmm.value);
        assertEquals(null, mmmm.left);
        assertEquals(null, mmmm.right);
        assertEquals("hijk", instance.left.value);
        assertEquals("mmmm", instance.right.value);
        
        BinaryTreeNode<String> mmnn = instance.insert("mmnn");
        assertEquals(true, instance.right.right.valueEquals("mmnn"));
    }
    
}
