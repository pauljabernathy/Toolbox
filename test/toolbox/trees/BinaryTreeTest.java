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

import toolbox.util.ListArrayUtil;
import org.apache.logging.log4j.*;

/**
 *
 * @author pabernathy
 */
public class BinaryTreeTest {
    
    private static Logger logger;
    
    public BinaryTreeTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
        logger = ListArrayUtil.getLogger(BinaryTreeTest.class, Level.INFO);
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
     * Test of insert method, of class BinaryTree.
     */
    @Test
    public void testInsert_BinaryTreeNode() {
        System.out.println("insert");
        BinaryTreeNode node = null;
        BinaryTree<String> instance = new BinaryTree();
        instance.insert(new BinaryTreeNode<String>("aaaa"));
        assertEquals("aaaa", instance.getRoot().value);
        assertEquals(null, instance.getRoot().left);
        assertEquals(null, instance.getRoot().right);
        
        assertEquals(true, instance.insert(new BinaryTreeNode<String>("abcd")));
        
        assertEquals(false, instance.insert(new BinaryTreeNode<Integer>(1)));
    }

    /**
     * Test of insert method, of class BinaryTree.
     */
    @Test
    public void testInsert_GenericType() {
        System.out.println("insert");
        BinaryTree instance = new BinaryTree();
        BinaryTree expResult = null;
        BinaryTree result = instance.insert("hijk");
        assertEquals(expResult, result);
    }

    /**
     * Test of contains method, of class BinaryTree.
     */
    @Test
    public void testContains_BinaryTreeNode() {
        System.out.println("contains");
        BinaryTreeNode node = null;
        BinaryTree instance = new BinaryTree();
        boolean expResult = false;
        boolean result = instance.contains(node);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of contains method, of class BinaryTree.
     */
    @Test
    public void testContains_GenericType() {
        System.out.println("contains");
        BinaryTree instance = new BinaryTree();
        boolean expResult = false;
        boolean result = instance.contains("hijk");
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }
    
}
