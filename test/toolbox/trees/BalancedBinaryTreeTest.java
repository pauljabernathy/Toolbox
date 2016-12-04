/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package toolbox.trees;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.Logger;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import toolbox.util.ListArrayUtil;
import java.util.LinkedList;

/**
 *
 * @author pabernathy
 */
public class BalancedBinaryTreeTest {
    
    private static Logger logger;
    
    public BalancedBinaryTreeTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
        logger = ListArrayUtil.getLogger(BalancedBinaryTreeTest.class, Level.INFO);
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
     * Test of equalsValue method, of class BalancedBinaryTree.
     */
    @Test
    public void testValueEquals_BalancedBinaryTree() {
        System.out.println("equalsValue");
        BalancedBinaryTree<String> instance1 = new BalancedBinaryTree<>(null);
        BalancedBinaryTree<String> instance2 = null;
        assertEquals(false, instance1.valueEquals(instance2));
        
        instance2 = new BalancedBinaryTree<>(null);
        assertEquals(true, instance1.valueEquals(instance2));
        
        instance2 = new BalancedBinaryTree<>("abc123");
        assertEquals(false, instance1.valueEquals(instance2));
        
        
        instance1 = new BalancedBinaryTree<>("abc123");
        instance2 = null;
        assertEquals(false, instance1.valueEquals(instance2));
        
        instance2 = new BalancedBinaryTree<>(null);
        assertEquals(false, instance1.valueEquals(instance2));
        
        instance2 = new BalancedBinaryTree<>("abc123");
        assertEquals(true, instance1.valueEquals(instance2));
        
        
        BalancedBinaryTree<Integer> instance3 = new BalancedBinaryTree<>(1);
        BalancedBinaryTree<Integer> instance4 = new BalancedBinaryTree<>(1);
        assertEquals(false, instance3.equals(instance4));
        assertEquals(true, instance3.valueEquals(instance4));
        
        assertEquals(false, instance1.equals(instance3));
        //assertEquals(true, instance1.equalsValue(instance3));
        
        instance1 = new BalancedBinaryTree<>(null);
        assertEquals(false, instance1.equals(instance2));
        assertEquals(false, instance1.valueEquals(instance2));
        instance1 = new BalancedBinaryTree<>("abc123");
        instance1 = new BalancedBinaryTree<>(null);
        assertEquals(false, instance1.equals(instance2));
        assertEquals(false, instance1.valueEquals(instance2));
    }
    
    @Test
    public void testValueEquals_Generic() {
        logger.info("\ntesting valueEquals(T value)");
        BalancedBinaryTree<String> instance = new BalancedBinaryTree<>(null);
        String s = null;
        assertEquals(true, instance.valueEquals(s));
        assertEquals(false, instance.valueEquals("abc123"));
        
        instance = new BalancedBinaryTree<>("abc123");
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
        BalancedBinaryTree<String> instance = new BalancedBinaryTree<String>("ijkl");
        assertEquals(instance, instance.get("ijkl"));
        BalancedBinaryTree<String> abcd = instance.insert("abcd").get(0);
        assertEquals(abcd, abcd.get("abcd"));
        assertEquals(abcd, instance.get("abcd"));
        BalancedBinaryTree<String> efgh = instance.insert("efgh").get(0);
        BalancedBinaryTree<String> mnop = instance.insert("mnop").get(0);
        assertEquals("efgh", instance.get("efgh").value);
        assertEquals(efgh, instance.get("efgh"));
        assertEquals(mnop, instance.get("mnop"));
        assertEquals(null, instance.get("mnoo"));
    }
    
    /**
     * Test of insert method, of class BalancedBinaryTree.
     */
    @Test
    public void testInsert() {
        logger.info("\ntesting insert()");
        Object value = null;
        /*BalancedBinaryTree<String> instance = new BalancedBinaryTree<String> ("lmno");
        BalancedBinaryTree<String> hijk = instance.insert("hijk").get(0);
        assertEquals("hijk", hijk.value);
        assertEquals(null, hijk.left);
        assertEquals(null, hijk.right);
        assertEquals(instance, hijk.parent);
        
        BalancedBinaryTree<String> mmmm = instance.insert("mmmm").get(0);
        assertEquals("mmmm", mmmm.value);
        assertEquals(null, mmmm.left);
        assertEquals(null, mmmm.right);
        assertEquals("hijk", instance.left.value);
        assertEquals("mmmm", instance.right.value);
        
        BalancedBinaryTree<String> mmnn = instance.insert("mmnn").get(0);
        assertEquals(true, instance.right.right.valueEquals("mmnn"));*/
        
        BalancedBinaryTree<String> m = new BalancedBinaryTree<>("m", 5);
        LinkedList<BalancedBinaryTree> list = m.insertOrAddWeight("n", 4);
        logger.info(list);
        assertEquals(2, list.size());
        assertEquals(m, list.get(0));
        assert(m == list.get(0));
    }
    
    /**
     * Test of insert method, of class BalancedBinaryTree.
     */
    @Test
    public void testInsertWeighted() {
        logger.info("\ntesting insertWeighted()");
        
        BalancedBinaryTree<String> m = new BalancedBinaryTree<>("m", 5);
        LinkedList<BalancedBinaryTree> list = m.insertWeighted("n", 4);
        logger.info(list);
        assertEquals(2, list.size());
        assertEquals(m, list.get(0));
        assert(m == list.get(0));
        assertEquals("m", list.get(0).value);
        assertEquals("n", list.get(1).value);
        
        list = m.insertWeighted("k", 3);
        logger.info(list);
        assertEquals(2, list.size());
        assertEquals("m", list.get(0).value);
        assertEquals("k", list.get(1).value);
        
        list = m.insertWeighted("l", 4);
        logger.info(list);
        assertEquals(2, list.size());
        assertEquals("m", list.get(0).value);
        assertEquals("l", list.get(1).value);
        
        m = new BalancedBinaryTree<>("m", 5);
        list = m.insertWeighted("n", 4);
        logger.info(list);
        assertEquals(2, list.size());
        assertEquals(m, list.get(0));
        assertEquals("m", list.get(0).value);
        assertEquals("n", list.get(1).value);
        
        list = m.insertWeighted("l", 3);
        logger.info(list);
        assertEquals(2, list.size());
        assertEquals("m", list.get(0).value);
        assertEquals("l", list.get(1).value);
        
        list = m.insertWeighted("k", 4);
        logger.info(list);
        assertEquals(2, list.size());
        assertEquals("m", list.get(0).value);
        assertEquals("k", list.get(1).value);
        //assertEquals("l", list.get(2).value);
        assertEquals("l", list.get(1).right.value);
        logger.info(list.get(1).right.getPathFromRoot());
        
        //attempting to insert the same thing again should have no affect
        list = m.insertWeighted("k", 4);
        logger.info(list);
        assertEquals(2, list.size());
        assertEquals("m", list.get(0).value);
        assertEquals("k", list.get(1).value);
        //assertEquals("l", list.get(1).right.value);
        logger.info(list.get(1).right.getPathFromRoot());
    }
    
    @Test
    public void testInsertWeightedOnRightSide() {
        logger.info("\ntesting insert on the right side of the tree");
        BalancedBinaryTree<String> m = new BalancedBinaryTree<>("m", 5);
        
        //First, M5 and O3, then insert N4
        LinkedList<BalancedBinaryTree> list = m.insertWeighted("o", 3);
        //logger.info(list);
        assertEquals(2, list.size());
        assertEquals("m", list.get(0).value);
        assertEquals("o", list.get(1).value);
        assertEquals(list.get(1), m.right);
        
        list = m.insertWeighted("n", 4);
        //logger.info(list);
        assertEquals("n", list.get(1).value);
        //logger.info(m.get("o").getPathFromRoot());
        assertEquals("o", list.get(1).right.value);
        assertEquals(null, list.get(1).left);
        
        //M5 and O3, then insert N2
        m = new BalancedBinaryTree<>("m", 5);
        m.insertWeighted("o", 3);
        list = m.insertWeighted("n", 2);
        //logger.info(list);
        assertEquals("n", list.get(2).value);
        assertEquals("o", list.get(1).value);
        assertEquals("o", list.get(0).right.value);
        assertEquals(null, list.get(0).left);
        assertEquals("n", list.get(1).left.value);
        assertEquals(null, list.get(1).right);
        
        //now, M5 and N4, insert O3
        m = new BalancedBinaryTree<>("m", 5);
        m.insertWeighted("n", 4);
        list = m.insertWeighted("o", 3);
        //logger.info(list);
        assertEquals("o", list.get(2).value);
        assertEquals("n", list.get(1).value);
        assertEquals("m", list.get(0).value);
        assertEquals("n", list.get(0).right.value);
        assertEquals(null, list.get(0).left);
        assertEquals("o", list.get(1).right.value);
        assertEquals(null, list.get(1).left);
        
        m = new BalancedBinaryTree<>("m", 5);
        m.insertWeighted("n", 2);
        list = m.insertWeighted("o", 3);
        //logger.info(list);
        assertEquals("o", list.get(1).value);
        assertEquals("m", list.get(0).value);
        //logger.info(m.get("n").getPathFromRoot());
        assertEquals("o", list.get(0).right.value);
        assertEquals(null, list.get(0).left);
        assertEquals("n", list.get(1).left.value);
        assertEquals(null, list.get(1).right);
    }
    
    @Test
    public void testInsertOrAddWeight() {
        logger.info("\ntesting insertOrAddWeight()");
        BalancedBinaryTree<String> m = new BalancedBinaryTree<>("m", 5);
        LinkedList<BalancedBinaryTree> list = m.insertOrAddWeight("n", 4);
        logger.info(list);
        assertEquals(2, list.size());
        assertEquals(m, list.get(0));
        assert(m == list.get(0));
        assertEquals("m", list.get(0).value);
        assertEquals("n", list.get(1).value);
        
        list = m.insertOrAddWeight("k", 3);
        logger.info(list);
        assertEquals(2, list.size());
        assertEquals("m", list.get(0).value);
        assertEquals("k", list.get(1).value);
        
        list = m.insertOrAddWeight("l", 4);
        logger.info(list);
        assertEquals(2, list.size());
        assertEquals("m", list.get(0).value);
        assertEquals("l", list.get(1).value);
        
        
        //test just insertion
        m = new BalancedBinaryTree<>("m", 5);
        
        //First, M5 and O3, then insert N4
        list = m.insertOrAddWeight("o", 3);
        //logger.info(list);
        assertEquals(2, list.size());
        assertEquals("m", list.get(0).value);
        assertEquals("o", list.get(1).value);
        assertEquals(list.get(1), m.right);
        
        list = m.insertOrAddWeight("n", 4);
        //logger.info(list);
        assertEquals("n", list.get(1).value);
        //logger.info(m.get("o").getPathFromRoot());
        assertEquals("o", list.get(1).right.value);
        assertEquals(null, list.get(1).left);
        
        //M5 and O3, then insert N2
        m = new BalancedBinaryTree<>("m", 5);
        m.insertOrAddWeight("o", 3);
        list = m.insertOrAddWeight("n", 2);
        //logger.info(list);
        assertEquals("n", list.get(2).value);
        assertEquals("o", list.get(1).value);
        assertEquals("o", list.get(0).right.value);
        assertEquals(null, list.get(0).left);
        assertEquals("n", list.get(1).left.value);
        assertEquals(null, list.get(1).right);
        
        //now, M5 and N4, insert O3
        m = new BalancedBinaryTree<>("m", 5);
        m.insertOrAddWeight("n", 4);
        list = m.insertOrAddWeight("o", 3);
        //logger.info(list);
        assertEquals("o", list.get(2).value);
        assertEquals("n", list.get(1).value);
        assertEquals("m", list.get(0).value);
        assertEquals("n", list.get(0).right.value);
        assertEquals(null, list.get(0).left);
        assertEquals("o", list.get(1).right.value);
        assertEquals(null, list.get(1).left);
        
        m = new BalancedBinaryTree<>("m", 5);
        m.insertOrAddWeight("n", 2);
        list = m.insertOrAddWeight("o", 3);
        //logger.info(list);
        assertEquals("o", list.get(1).value);
        assertEquals("m", list.get(0).value);
        //logger.info(m.get("n").getPathFromRoot());
        assertEquals("o", list.get(0).right.value);
        assertEquals(null, list.get(0).left);
        assertEquals("n", list.get(1).left.value);
        assertEquals(null, list.get(1).right);
    }
    
    @Test
    public void testGetPathToRoot() {
        logger.info("\ntesting getPathToRoot()");
        BalancedBinaryTree<String> f = new BalancedBinaryTree<String>("F");
        LinkedList<BalancedBinaryTree> result = null;
        result = f.getPathToRoot();
        if(result == null) {
            fail("result was null");
        }
        assertEquals(0, result.size());
        BalancedBinaryTree<String> c = f.insert("C").get(0);
        result = c.getPathToRoot();
        assertEquals(1, result.size());
        for(BalancedBinaryTree tree : result) {
            logger.info(tree);
        }
        
        BalancedBinaryTree b = f.insert("B").get(0);
        result = b.getPathToRoot();
        assertEquals(2, result.size());
        for(BalancedBinaryTree tree : result) {
            logger.info(tree);
        }
        assertEquals(c, result.get(0));
        assertEquals(f, result.get(1));
    }
    
    @Test
    public void testGetPathFromRoot() {
        logger.info("\ntesting getPathFromRoot()");
        BalancedBinaryTree<String> f = new BalancedBinaryTree<String>("F");
        LinkedList<BalancedBinaryTree> result = null;
        result = f.getPathFromRoot();
        if(result == null) {
            fail("result was null");
        }
        assertEquals(1, result.size());
        //BalancedBinaryTree<String> c = f.insert("C").get(0);
        //c.getPathFromRoot();
        result = f.insert("C");
        assertEquals(2, result.size());
        for(BalancedBinaryTree tree : result) {
            logger.info(tree);
        }
        
        //BalancedBinaryTree b = f.insert("B").get(0);
        //result = b.getPathFromRoot();
        result = f.insert("B");
        assertEquals(3, result.size());
        for(BalancedBinaryTree tree : result) {
            logger.info(tree);
        }
        assertEquals("B", result.get(2).value);
        assertEquals("C", result.get(1).value);
        assertEquals("F", result.get(0).value);
    }
}
