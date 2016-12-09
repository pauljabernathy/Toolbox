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
import java.util.ArrayList;

/**
 *
 * @author pabernathy
 */
public class WeightedBinaryTreeTest {
    
    private static Logger logger;
    
    public WeightedBinaryTreeTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
        logger = ListArrayUtil.getLogger(WeightedBinaryTreeTest.class, Level.INFO);
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
        WeightedBinaryTree<String> instance1 = new WeightedBinaryTree<>(null);
        WeightedBinaryTree<String> instance2 = null;
        assertEquals(false, instance1.valueEquals(instance2));
        
        instance2 = new WeightedBinaryTree<>(null);
        assertEquals(true, instance1.valueEquals(instance2));
        
        instance2 = new WeightedBinaryTree<>("abc123");
        assertEquals(false, instance1.valueEquals(instance2));
        
        
        instance1 = new WeightedBinaryTree<>("abc123");
        instance2 = null;
        assertEquals(false, instance1.valueEquals(instance2));
        
        instance2 = new WeightedBinaryTree<>(null);
        assertEquals(false, instance1.valueEquals(instance2));
        
        instance2 = new WeightedBinaryTree<>("abc123");
        assertEquals(true, instance1.valueEquals(instance2));
        
        
        WeightedBinaryTree<Integer> instance3 = new WeightedBinaryTree<>(1);
        WeightedBinaryTree<Integer> instance4 = new WeightedBinaryTree<>(1);
        assertEquals(false, instance3.equals(instance4));
        assertEquals(true, instance3.valueEquals(instance4));
        
        assertEquals(false, instance1.equals(instance3));
        //assertEquals(true, instance1.equalsValue(instance3));
        
        instance1 = new WeightedBinaryTree<>(null);
        assertEquals(false, instance1.equals(instance2));
        assertEquals(false, instance1.valueEquals(instance2));
        instance1 = new WeightedBinaryTree<>("abc123");
        instance1 = new WeightedBinaryTree<>(null);
        assertEquals(false, instance1.equals(instance2));
        assertEquals(false, instance1.valueEquals(instance2));
    }
    
    @Test
    public void testValueEquals_Generic() {
        logger.info("\ntesting valueEquals(T value)");
        WeightedBinaryTree<String> instance = new WeightedBinaryTree<>(null);
        String s = null;
        assertEquals(true, instance.valueEquals(s));
        assertEquals(false, instance.valueEquals("abc123"));
        
        instance = new WeightedBinaryTree<>("abc123");
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
        WeightedBinaryTree<String> instance = new WeightedBinaryTree<>("ijkl");
        assertEquals(instance, instance.get("ijkl"));
        WeightedBinaryTree<String> abcd = instance.insert("abcd").getInsertedNode();
        assertEquals(abcd, abcd.get("abcd"));
        assertEquals(abcd, instance.get("abcd"));
        WeightedBinaryTree<String> efgh = instance.insert("efgh").getInsertedNode();
        WeightedBinaryTree<String> mnop = instance.insert("mnop").getInsertedNode();
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
        
        WeightedBinaryTree<String> m = new WeightedBinaryTree<>("m", 5);
        LinkedList<WeightedBinaryTree<String>> list = m.insert("n", 4).getPathFromRoot();
        logger.info(list);
        assertEquals(2, list.size());
        assertEquals(m, list.get(0));
        assert(m == list.get(0));
        
        //Adding a new node with greater weight, but no child node to switch with
        list = m.insert("k", 6).getPathFromRoot();
        logger.info(list);
        logger.info(m.getAsList(WeightedBinaryTree.SortType.WEIGHT));
        assertEquals(1, list.size());
        assertEquals("k", list.get(0).value);
        assertEquals("m", list.get(0).right.value);
        
        m = new WeightedBinaryTree<>("m", 5);
        list = m.insert("n", 4).getPathFromRoot();
        list = m.insert("o", 7).getPathFromRoot();
        logger.info(list);
        assertEquals(1, list.size());
        assertEquals("o", list.get(0).value);
        assertEquals("m", list.get(0).left.value);
        
        m = new WeightedBinaryTree<>("and", 5);
        m.insert("aardvark", 6);
        m.insert("make", 2);
        logger.info(m.getPathFromRoot());
        logger.info(m.parent);
        logger.info(m.getAsList(WeightedBinaryTree.SortType.WEIGHT));
        logger.info(m.parent.getAsList(WeightedBinaryTree.SortType.WEIGHT));
    }
    
    /**
     * Test of insert method, of class BalancedBinaryTree.
     */
    @Test
    public void testInsertWeighted() {
        logger.info("\ntesting insertWeighted()");
        
        WeightedBinaryTree<String> m = new WeightedBinaryTree<>("m", 5);
        LinkedList<WeightedBinaryTree<String>> list = m.insertWeighted("n", 4).getPathFromRoot();
        logger.info(list);
        assertEquals(2, list.size());
        assertEquals(m, list.get(0));
        assert(m == list.get(0));
        assertEquals("m", list.get(0).value);
        assertEquals("n", list.get(1).value);
        
        list = m.insertWeighted("k", 3).getPathFromRoot();
        logger.info(list);
        assertEquals(2, list.size());
        assertEquals("m", list.get(0).value);
        assertEquals("k", list.get(1).value);
        
        list = m.insertWeighted("l", 4).getPathFromRoot();
        logger.info(list);
        assertEquals(2, list.size());
        assertEquals("m", list.get(0).value);
        assertEquals("l", list.get(1).value);
        
        m = new WeightedBinaryTree<>("m", 5);
        list = m.insertWeighted("n", 4).getPathFromRoot();
        logger.info(list);
        assertEquals(2, list.size());
        assertEquals(m, list.get(0));
        assertEquals("m", list.get(0).value);
        assertEquals("n", list.get(1).value);
        
        list = m.insertWeighted("l", 3).getPathFromRoot();
        logger.info(list);
        assertEquals(2, list.size());
        assertEquals("m", list.get(0).value);
        assertEquals("l", list.get(1).value);
        
        list = m.insertWeighted("k", 4).getPathFromRoot();
        logger.info(list);
        assertEquals(2, list.size());
        assertEquals("m", list.get(0).value);
        assertEquals("k", list.get(1).value);
        //assertEquals("l", list.get(2).value);
        assertEquals("l", list.get(1).right.value);
        logger.info(list.get(1).right.getPathFromRoot());
        
        //attempting to insert the same thing again should have no affect
        list = m.insertWeighted("k", 4).getPathFromRoot();
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
        WeightedBinaryTree<String> m = new WeightedBinaryTree<>("m", 5);
        
        //First, M5 and O3, then insert N4
        LinkedList<WeightedBinaryTree<String>> list = m.insertWeighted("o", 3).getPathFromRoot();
        //logger.info(list);
        assertEquals(2, list.size());
        assertEquals("m", list.get(0).value);
        assertEquals("o", list.get(1).value);
        assertEquals(list.get(1), m.right);
        
        list = m.insertWeighted("n", 4).getPathFromRoot();
        //logger.info(list);
        assertEquals("n", list.get(1).value);
        //logger.info(m.get("o").getPathFromRoot());
        assertEquals("o", list.get(1).right.value);
        assertEquals(null, list.get(1).left);
        
        //M5 and O3, then insert N2
        m = new WeightedBinaryTree<>("m", 5);
        m.insertWeighted("o", 3);
        list = m.insertWeighted("n", 2).getPathFromRoot();
        //logger.info(list);
        assertEquals("n", list.get(2).value);
        assertEquals("o", list.get(1).value);
        assertEquals("o", list.get(0).right.value);
        assertEquals(null, list.get(0).left);
        assertEquals("n", list.get(1).left.value);
        assertEquals(null, list.get(1).right);
        
        //now, M5 and N4, insert O3
        m = new WeightedBinaryTree<>("m", 5);
        m.insertWeighted("n", 4);
        list = m.insertWeighted("o", 3).getPathFromRoot();
        //logger.info(list);
        assertEquals("o", list.get(2).value);
        assertEquals("n", list.get(1).value);
        assertEquals("m", list.get(0).value);
        assertEquals("n", list.get(0).right.value);
        assertEquals(null, list.get(0).left);
        assertEquals("o", list.get(1).right.value);
        assertEquals(null, list.get(1).left);
        
        m = new WeightedBinaryTree<>("m", 5);
        m.insertWeighted("n", 2);
        list = m.insertWeighted("o", 3).getPathFromRoot();
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
        WeightedBinaryTree<String> m = new WeightedBinaryTree<>("m", 5);
        LinkedList<WeightedBinaryTree<String>> list = m.insertOrAddWeight("n", 4).getPathFromRoot();
        logger.info(list);
        assertEquals(2, list.size());
        assertEquals(m, list.get(0));
        assert(m == list.get(0));
        assertEquals("m", list.get(0).value);
        assertEquals("n", list.get(1).value);
        
        list = m.insertOrAddWeight("k", 3).getPathFromRoot();
        logger.info(list);
        assertEquals(2, list.size());
        assertEquals("m", list.get(0).value);
        assertEquals("k", list.get(1).value);
        
        list = m.insertOrAddWeight("l", 4).getPathFromRoot();
        logger.info(list);
        assertEquals(2, list.size());
        assertEquals("m", list.get(0).value);
        assertEquals("l", list.get(1).value);
        
        
        //test just insertion
        m = new WeightedBinaryTree<>("m", 5);
        
        //First, M5 and O3, then insert N4
        list = m.insertOrAddWeight("o", 3).getPathFromRoot();
        //logger.info(list);
        assertEquals(2, list.size());
        assertEquals("m", list.get(0).value);
        assertEquals("o", list.get(1).value);
        assertEquals(list.get(1), m.right);
        
        list = m.insertOrAddWeight("n", 4).getPathFromRoot();
        //logger.info(list);
        assertEquals("n", list.get(1).value);
        //logger.info(m.get("o").getPathFromRoot());
        assertEquals("o", list.get(1).right.value);
        assertEquals(null, list.get(1).left);
        
        //M5 and O3, then insert N2
        m = new WeightedBinaryTree<>("m", 5);
        m.insertOrAddWeight("o", 3);
        list = m.insertOrAddWeight("n", 2).getPathFromRoot();
        //logger.info(list);
        assertEquals("n", list.get(2).value);
        assertEquals("o", list.get(1).value);
        assertEquals("o", list.get(0).right.value);
        assertEquals(null, list.get(0).left);
        assertEquals("n", list.get(1).left.value);
        assertEquals(null, list.get(1).right);
        
        //now, M5 and N4, insert O3
        m = new WeightedBinaryTree<>("m", 5);
        m.insertOrAddWeight("n", 4);
        list = m.insertOrAddWeight("o", 3).getPathFromRoot();
        //logger.info(list);
        assertEquals("o", list.get(2).value);
        assertEquals("n", list.get(1).value);
        assertEquals("m", list.get(0).value);
        assertEquals("n", list.get(0).right.value);
        assertEquals(null, list.get(0).left);
        assertEquals("o", list.get(1).right.value);
        assertEquals(null, list.get(1).left);
        
        m = new WeightedBinaryTree<>("m", 5);
        m.insertOrAddWeight("n", 2);
        list = m.insertOrAddWeight("o", 3).getPathFromRoot();
        //logger.info(list);
        assertEquals("o", list.get(1).value);
        assertEquals("m", list.get(0).value);
        //logger.info(m.get("n").getPathFromRoot());
        assertEquals("o", list.get(0).right.value);
        assertEquals(null, list.get(0).left);
        assertEquals("n", list.get(1).left.value);
        assertEquals(null, list.get(1).right);
        
        //now, some updates
        list = m.insertOrAddWeight("o", 1).getPathFromRoot();
        logger.info(list);
        assertEquals("o", list.get(1).value);
        assertEquals(4.0, list.get(1).weight, 0.0);
    }
    
    @Test
    public void testGetPathToRoot() {
        logger.info("\ntesting getPathToRoot()");
        WeightedBinaryTree<String> f = new WeightedBinaryTree<String>("F");
        LinkedList<WeightedBinaryTree<String>> result = null;
        result = f.getPathToRoot();
        if(result == null) {
            fail("result was null");
        }
        assertEquals(1, result.size());
        WeightedBinaryTree<String> c = f.insert("C").getPathFromRoot().get(1);
        result = c.getPathToRoot();
        assertEquals(2, result.size());
        result.forEach(tree -> logger.info(tree));
        
        WeightedBinaryTree b = f.insert("B").getPathFromRoot().get(2);
        result = b.getPathToRoot();
        assertEquals(3, result.size());
        result.forEach(tree -> logger.info(tree));
        assertEquals(c, result.get(1));
        assertEquals(f, result.get(2));
    }
    
    @Test
    public void testGetPathFromRoot() {
        logger.info("\ntesting getPathFromRoot()");
        WeightedBinaryTree<String> f = new WeightedBinaryTree<String>("F");
        LinkedList<WeightedBinaryTree<String>> result = null;
        result = f.getPathFromRoot();
        if(result == null) {
            fail("result was null");
        }
        assertEquals(1, result.size());
        //BalancedBinaryTree<String> c = f.insert("C").get(0);
        //c.getPathFromRoot();
        result = f.insert("C").getPathFromRoot();
        assertEquals(2, result.size());
        for(WeightedBinaryTree tree : result) {
            logger.info(tree);
        }
        
        //BalancedBinaryTree b = f.insert("B").get(0);
        //result = b.getPathFromRoot();
        result = f.insert("B").getPathFromRoot();
        assertEquals(3, result.size());
        for(WeightedBinaryTree tree : result) {
            logger.info(tree);
        }
        assertEquals("B", result.get(2).value);
        assertEquals("C", result.get(1).value);
        assertEquals("F", result.get(0).value);
    }
    @Test
    public void testGetRoot() {
        logger.info("\ntesting getRoot()");
        WeightedBinaryTree<String> m = new WeightedBinaryTree<>("m", 5);
        LinkedList<WeightedBinaryTree<String>> list = m.insert("n", 4).getPathFromRoot();
        assertEquals("m", list.get(1).getRoot().value);
    }
    
    @Test
    public void testGetAsList() {
        logger.info("\ntesting getAsList()");
        WeightedBinaryTree<String> tree = this.getBasicTree();
        System.out.println("\ntree is " + tree);
        LinkedList<WeightedBinaryTree<String>> list = tree.getAsList(WeightedBinaryTree.SortType.NATURAL_ORDER);
        logger.info("depth first list = " + list);
        ArrayList<String> words = new ArrayList<String>();
        words.add("word");
        words.add("want");
        words.add("make");
        words.add("histogram");
        words.add("and");
        words.add("I");
        words.add("to");
        words.add("aardvark");
        logger.info("words = " + words);
        java.util.Collections.sort(words);
        logger.info("sorted word = " + words);
        
        tree.display();
        list = tree.getAsList(WeightedBinaryTree.SortType.WEIGHT);
        logger.info("breadth first list = " + list);
    }
    
    private WeightedBinaryTree<String> getBasicTree() {
        /*WeightedBinaryTree<String> treebeard = new WeightedBinaryTree<>("n", 2);
        treebeard = treebeard.insert("m", 5).getRoot();
        logger.info(treebeard);
        treebeard.insert("o", 3);
        treebeard = treebeard.insert("l", 6).getRoot();
        treebeard.insert("k", 3);*/
        
        /**treebeard = new WeightedBinaryTree<>("a", 1.0);
        treebeard.insert("and", 1.0, DuplicateEntryOption.UPDATE);
        treebeard.insert("I", 1.0, DuplicateEntryOption.UPDATE);
        treebeard.insert("want", 1.0, DuplicateEntryOption.UPDATE);
        treebeard.insert("to", 1.0, DuplicateEntryOption.UPDATE);
        treebeard.insert("make", 1.0, DuplicateEntryOption.UPDATE);
        treebeard.insert("a", 1.0, DuplicateEntryOption.UPDATE);
        treebeard.insert("word", 1.0, DuplicateEntryOption.UPDATE);
        treebeard.insert("histogram", 1.0, DuplicateEntryOption.UPDATE);
        treebeard.insert("and", 1.0, DuplicateEntryOption.UPDATE);
        treebeard.insert("and", 1.0, DuplicateEntryOption.UPDATE);
        treebeard.insert("I", 1.0, DuplicateEntryOption.UPDATE);
        treebeard.insert("and", 1.0, DuplicateEntryOption.UPDATE);
        treebeard.insert("histogram", 1.0, DuplicateEntryOption.UPDATE);
        treebeard.insert("I", 1.0, DuplicateEntryOption.UPDATE);/**/
        
        WeightedBinaryTree<String> treebeard = new WeightedBinaryTree<>("make", 2.0);
        treebeard.getRoot().insert("aardvark", 5.0, DuplicateEntryOption.UPDATE);
        treebeard.getRoot().display();
        treebeard.getRoot().insert("and", 4.0, DuplicateEntryOption.UPDATE);
        treebeard.getRoot().display();
        treebeard.getRoot().insert("I", 3.0, DuplicateEntryOption.UPDATE);
        treebeard.getRoot().display();
        treebeard.getRoot().insert("want", 1.0, DuplicateEntryOption.UPDATE);
        treebeard.getRoot().display();
        treebeard.getRoot().insert("to", 1.0, DuplicateEntryOption.UPDATE);
        treebeard.getRoot().display();
        treebeard.getRoot().insert("word", 1.0, DuplicateEntryOption.UPDATE);
        treebeard.getRoot().display();
        treebeard.getRoot().insert("make", 1.0, DuplicateEntryOption.UPDATE);
        treebeard.getRoot().display();
        treebeard.getRoot().insert("histogram", 2.0, DuplicateEntryOption.UPDATE);
        treebeard.getRoot().display();
        //treebeard.getRoot().insert("aardvark", 5.0, DuplicateEntryOption.UPDATE);
        treebeard.getRoot().insert("bbb", 4.9, DuplicateEntryOption.UPDATE);
        treebeard.getRoot().display();/**/
        
        return treebeard.getRoot();
    }
    
    @Test
    public void makeWordHistogram() {
        logger.info("making a word histogram");
        String text = "and I want to make a word histogram and and I and histogram I";
        WeightedBinaryTree<String> hist = new WeightedBinaryTree<>("a");
        String[] words = text.split(" ");
        for(String word : words) {
            hist.insert(word, 1.0, DuplicateEntryOption.UPDATE);
        }
        for(String word : words) {
            logger.info(hist.get(word));
        }
        logger.info(hist.getAsList(WeightedBinaryTree.SortType.NATURAL_ORDER));
        logger.info(hist.getAsList(WeightedBinaryTree.SortType.WEIGHT));
    }
}
