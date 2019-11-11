/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package toolbox.trees;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.Logger;
import org.junit.After;
import org.junit.AfterClass;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import toolbox.io.TextReader;
import toolbox.stats.Histogram;
import toolbox.stats.TreeHistogram;
import toolbox.stats.TreeHistogram.Sort;
import toolbox.util.ListArrayUtil;
import toolbox.util.MathUtil;

/**
 *
 * @author pabernathy
 */
public class WeightedBinaryTreeTest {
    
    private static Logger logger;
    private static final double EPSILON = .001;
    
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
    public void testKeyEquals_BalancedBinaryTree() {
        logger.info("\ntesting keyEquals(WeightedBinaryTree<T>)");
        WeightedBinaryTree<String> instance1 = new WeightedBinaryTree<>(null);
        WeightedBinaryTree<String> instance2 = null;
        assertEquals(false, instance1.keyEquals(instance2));
        
        instance2 = new WeightedBinaryTree<>(null);
        assertEquals(true, instance1.keyEquals(instance2));
        
        instance2 = new WeightedBinaryTree<>("abc123");
        assertEquals(false, instance1.keyEquals(instance2));
        
        
        instance1 = new WeightedBinaryTree<>("abc123");
        instance2 = null;
        assertEquals(false, instance1.keyEquals(instance2));
        
        instance2 = new WeightedBinaryTree<>(null);
        assertEquals(false, instance1.keyEquals(instance2));
        
        instance2 = new WeightedBinaryTree<>("abc123");
        assertEquals(true, instance1.keyEquals(instance2));
        
        
        WeightedBinaryTree<Integer> instance3 = new WeightedBinaryTree<>(1);
        WeightedBinaryTree<Integer> instance4 = new WeightedBinaryTree<>(1);
        assertEquals(false, instance3.equals(instance4));
        assertEquals(true, instance3.keyEquals(instance4));
        
        assertEquals(false, instance1.equals(instance3));
        
        instance1 = new WeightedBinaryTree<>(null);
        assertEquals(false, instance1.equals(instance2));
        assertEquals(false, instance1.keyEquals(instance2));
        instance1 = new WeightedBinaryTree<>("abc123");
        assertEquals(true, instance1.keyEquals(instance2));
        assertEquals(true, instance2.keyEquals(instance1));
        instance1 = new WeightedBinaryTree<>(null);
        assertEquals(false, instance1.equals(instance2));
        assertEquals(false, instance1.keyEquals(instance2));
    }
    
    @Test
    public void testValueEquals_Generic() {
        logger.info("\ntesting keyEquals(T value)");
        WeightedBinaryTree<String> instance = new WeightedBinaryTree<>(null);
        String s = null;
        assertEquals(true, instance.keyEquals(s));
        assertEquals(false, instance.keyEquals("abc123"));
        
        instance = new WeightedBinaryTree<>("abc123");
        assertEquals(false, instance.keyEquals(s));
        assertEquals(true, instance.keyEquals("abc123"));
        assertEquals(false, instance.keyEquals("abc1234"));
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
        //WeightedBinaryTree<String> abcd = instance.insert("abcd").getInsertedNode();//instance.simpleBinaryInsert("abcd", 1.0, DuplicateEntryOption.REPLACE).getInsertedNode();
        InsertionResult result = instance.simpleBinaryInsert("abcd");
        logger.info("result.insertedNode = " + result.insertedNode);
        WeightedBinaryTree<String> abcd = result.getInsertedNode();
        System.out.println("result.getInsertedNode() = " + result.getInsertedNode());
        instance.display();
        System.out.println("result.insertedNode = " + result.insertedNode);
        assertEquals(abcd, abcd.get("abcd"));
        assertEquals(abcd, instance.get("abcd"));
        WeightedBinaryTree<String> efgh = instance.simpleBinaryInsert("efgh").getInsertedNode();
        WeightedBinaryTree<String> mnop = instance.simpleBinaryInsert("mnop").getInsertedNode();
        assertEquals("efgh", instance.get("efgh").getKey());
        assertEquals(efgh, instance.get("efgh"));
        assertEquals(mnop, instance.get("mnop"));
        assertEquals(null, instance.get("mnoo"));
    }
    
    @Test
    public void testSetParent() {
        logger.info("\ntesting setParent()");
        WeightedBinaryTree<Integer> seven = new WeightedBinaryTree<>(7);
        assertEquals(null, seven.parent);
        assertEquals(null, seven.left);
        assertEquals(null, seven.right);
        
        WeightedBinaryTree<Integer> five = new WeightedBinaryTree<>(5);
        seven.setParent(five);
        assertEquals(five, seven.parent);
    }
    
    @Test
    public void testSetLeftChild() {
        logger.info("\ntesting setLeftChild()");
        WeightedBinaryTree<Integer> seven = new WeightedBinaryTree<>(7);
        assertEquals(null, seven.parent);
        assertEquals(null, seven.left);
        assertEquals(null, seven.right);
        assertEquals(1, seven.getDepth());
        
        WeightedBinaryTree<Integer> five = new WeightedBinaryTree<>(5);
        logger.info(five.getDepth());
        seven.setLeftChild(five);
        assertEquals(five, seven.left);
        assertEquals(null, five.left);
        logger.info(five.getPathToRoot());
        logger.info(five.getPathFromRoot());
        assertEquals(1, seven.weight, 0.0);
        
        five.setLeftChild(seven, DuplicateEntryOption.UPDATE);
        assertEquals(null, five.left);
        
        seven.setLeftChild(seven); 
        logger.info(seven.getLeftChild());
        assertEquals(five, seven.left);
        
        WeightedBinaryTree<String> m = new WeightedBinaryTree<>("m", 1);
        assertEquals(1.0, m.getTreeWeight(), 0.0);
        InsertionResult<String> result = null;
        WeightedBinaryTree<String> l = m.setLeftChild(new WeightedBinaryTree<>("l")).getLeftChild();
        logger.info(m.getTreeWeight());
        assertEquals(1.0, l.getTreeWeight(), 0.0);
        assertEquals(2.0, m.getTreeWeight(), 0.0);
        WeightedBinaryTree<String> o = m.setRightChild(new WeightedBinaryTree<>("o", 1)).getRightChild();
        logger.info(m.getTreeWeight());
        WeightedBinaryTree<String> n = o.setLeftChild(new WeightedBinaryTree<>("n")).getLeftChild();
        logger.info(m.getTreeWeight());
        WeightedBinaryTree<String> p = o.setRightChild(new WeightedBinaryTree<>("p")).getRightChild();
        logger.info(m.getTreeWeight());
        assertEquals(l, m.getLeftChild());
        assertEquals(o, m.getRightChild());
        assertEquals(p, m.getRightChild().getRightChild());
        assertEquals(n, m.getRightChild().getLeftChild());
        m.display();
        o.display();
        assertEquals(3, o.getTreeWeight(), 0.0);
        m.display();
        
        o.setLeftChild(m);
        m.setRightChild(n);
        logger.debug("\n");
        o.display();
        assertEquals(m, o.getLeftChild());
        assertEquals(l, m.getLeftChild());
        assertEquals(n, m.getRightChild());
        assertEquals(p, o.getRightChild());
    }
    
    @Test
    public void testThatSetLeftChildSetsParentOfOldLeft() {
        logger.info("\ntestThatSetLeftChildSetsParentOfOldLeft()");
        WeightedBinaryTree<String> m = new WeightedBinaryTree<>("m", 1);
        assertEquals(1.0, m.getTreeWeight(), 0.0);
        InsertionResult<String> result = null;
        WeightedBinaryTree<String> l = m.setLeftChild(new WeightedBinaryTree<>("l")).getLeftChild();
        m.setLeftChild(l);
        assertEquals(l, m.getLeftChild());
        assertEquals(null, m.getRightChild());
        assertEquals(m, l.getParent());
        assertEquals(null, l.getRightChild());
        assertEquals(null, l.getLeftChild());
        
        m.setLeftChild(null);
        assertEquals(null, m.getLeftChild());
        assertEquals(null, l.getParent());
        
        m.setLeftChild(l);
        WeightedBinaryTree<String> n = new WeightedBinaryTree<>("n");
        n.setLeftChild(l);
        assertEquals(l, n.getLeftChild());
        assertEquals(null, m.getLeftChild());
    }
    
    @Test
    public void testThatSetRightChildSetsParentOfOldLeft() {
        logger.info("\ntestThatSetRightChildSetsParentOfOldLeft");
        
        WeightedBinaryTree<String> m = new WeightedBinaryTree<>("m", 1);
        assertEquals(0.0, m.getSubTreeWeight(), 0.0);
        assertEquals(1.0, m.getTreeWeight(), 0.0);
        
        //Now give m a child and make sure that it's getRightChild() and weights etc. are correct
        WeightedBinaryTree<String> l = m.setRightChild(new WeightedBinaryTree<>("l")).getRightChild();
        assertEquals(l, m.getRightChild());
        assertEquals(null, m.getLeftChild());
        assertEquals(1.0, m.getSubTreeWeight(), 0.0);
        assertEquals(2.0, m.getTreeWeight(), 0.0);
        assertEquals(m, l.getParent());
        assertEquals(null, l.getLeftChild());
        assertEquals(null, l.getRightChild());
        
        //Remove the child and make sure getRightChild and weights are updated.
        m.setRightChild(null);
        assertEquals(null, m.getRightChild());
        assertEquals(null, l.getParent());
        assertEquals(0.0, m.getSubTreeWeight(), 0.0);
        assertEquals(1.0, m.getTreeWeight(), 0.0);
        
        //Put the child back and make sure everything is updated again.
        m.setRightChild(l);
        assertEquals(1.0, m.getSubTreeWeight(), 0.0);
        assertEquals(2.0, m.getTreeWeight(), 0.0);
        
        WeightedBinaryTree<String> n = new WeightedBinaryTree<>("n");
        assertEquals(0.0, n.getSubTreeWeight(), 0.0);
        assertEquals(1.0, n.getTreeWeight(), 1.0);
        //Now if you set l to be n's right child, m should know l is not it's right child anymore.
        n.setRightChild(l);
        assertEquals(1.0, n.getSubTreeWeight(), 0.0);
        assertEquals(l, n.getRightChild());
        assertEquals(null, m.getRightChild());
        assertEquals(0.0, m.getSubTreeWeight(), 0.0);
        assertEquals(1.0, m.getTreeWeight(), 0.0);
    }
    
    @Test
    public void testSetRightChild() {
        logger.info("\ntesting setRightChild()");
        WeightedBinaryTree<String> instance = new WeightedBinaryTree<>("m");
        instance.setRightChild(new WeightedBinaryTree<>("o", 4));
        assertEquals(null, instance.left);
        assertEquals("o", instance.right.getKey());
        //assertEquals(8, instance.right.getIndividualTorque(), 0.0);
        //assertEquals(9, instance.getTreeTorque(), 0.0);
        
        WeightedBinaryTree<String> m = new WeightedBinaryTree<>("m", 1);
        WeightedBinaryTree<String> n = m.setRightChild(new WeightedBinaryTree("n", 4)).getRightChild();//.getRoot();
        //instance.display();
        assertEquals(4.0, n.getWeight(), 0.0);
        assertEquals(4.0, n.getWeight(), 0.0);
        assertEquals(4.0, m.getSubTreeWeight(), 0.0);
        assertEquals(5.0, m.getTreeWeight(), 0.0);
        WeightedBinaryTree<String> j = m.setLeftChild(new WeightedBinaryTree("j", 1)).getLeftChild();
        assertEquals(6.0, m.getTreeWeight(), 0.0);
        WeightedBinaryTree<String> l = j.setRightChild(new WeightedBinaryTree<>("l", 1)).getRightChild();
        assertEquals(7, m.getTreeWeight(), 0.0);
        m.display();
        assertEquals(j, m.getLeftChild());
        assertEquals(n, m.getRightChild());
        assertEquals(l, j.getRightChild());
        
        //m.setLeftChild(l);
        m.left = l;
        j.setRightChild(m);//, DuplicateEntryOption.REPLACE);
        //j.right = m;
        logger.info("\n");
        j.display();
        assertEquals(m, j.getRightChild());
        assertEquals(l, m.getLeftChild());
        
        logger.info("\nm");
        m.display();
    }
    
    /*@Test
    public void testSetSubTreeWeight() {
        logger.info("\ntesting setSubTreeWeight()");
        WeightedBinaryTree<Double> instance = new WeightedBinaryTree<>(5.0, 1);
        
    }*/
    
    /*@Test
    public void testSetSubTreeTorque() {
        logger.info("\ntesting setSubTreeTorque()");
        WeightedBinaryTree<Double> instance = new WeightedBinaryTree<>(5.0, 1);
        assertEquals(1.0, instance.getIndividualTorque(), 0.0);
        assertEquals(1.0, instance.getTreeTorque(), 0.0);
        instance.setSubTreeTorque(55);
        assertEquals(1.0, instance.getIndividualTorque(), 0.0);
        assertEquals(1.0, instance.getTreeTorque(), 0.0);
        instance.setLeftChild(new WeightedBinaryTree<>(4.0, 2));
        assertEquals(5.0, instance.getTreeTorque(), 0.0);
        instance.setSubTreeTorque(55);
        assertEquals(56.0, instance.getTreeTorque(), 0.0);
    }*/
    
    //TODO:  This test has mahy problems. Something has changed in getBasicTree().
    //@Test
    public void testGetSiblingAndIsLeftOrRight() {
        logger.info("\ntesting getSibblingAndIsLeftOrRight");
        WeightedBinaryTree<String> instance = this.getBasicTree();
        assertEquals(null, instance.getSibling());
        instance.display();
        WeightedBinaryTree<String> relevance = instance.get("relevance");
        WeightedBinaryTree<String> aa = instance.get("aa");
        assertEquals(aa, relevance.getSibling());
        assertEquals(relevance, aa.getSibling());
        
        WeightedBinaryTree<String> holiday = instance.get("holiday");
        WeightedBinaryTree<String> injure = instance.get("injure");
        assertEquals(null, holiday.getSibling());
        assertEquals(null, injure.getSibling());
        
        assertEquals(false, instance.isLeftChild());
        assertEquals(false, instance.isRightChild());
        assertEquals(true, aa.isLeftChild());
        assertEquals(false, aa.isRightChild());
        assertEquals(false, relevance.isLeftChild());
        assertEquals(true, relevance.isRightChild());
        assertEquals(true, holiday.isLeftChild());
        assertEquals(false, holiday.isRightChild());
        assertEquals(false, injure.isLeftChild());
        assertEquals(true, injure.isRightChild());
    }
    
    @Test
    public void testChildAndAncestorTesters() {
        logger.info("\ntesting isChildOf()");
        WeightedBinaryTree<Double> one = new WeightedBinaryTree<>(1.0);
        WeightedBinaryTree<Double> pointFive = new WeightedBinaryTree<>(0.5);
        WeightedBinaryTree<Double> pointSevenFive = new WeightedBinaryTree<>(0.75);
        WeightedBinaryTree<Double> two = new WeightedBinaryTree<>(2.0);
        two.setLeftChild(pointFive).getLeftChild().setRightChild(one).getRightChild().setLeftChild(pointSevenFive);
        WeightedBinaryTree<Double> three = new WeightedBinaryTree<>(3.0);
        two.setRightChild(three);
        
        assertEquals(false, pointSevenFive.isChildOf(two));
        assertEquals(false, pointSevenFive.isChildOf(pointFive));
        assertEquals(true, pointSevenFive.isChildOf(one));
        assertEquals(false, pointSevenFive.isChildOf(pointSevenFive));
        assertEquals(false, pointSevenFive.isChildOf(three));
        
        assertEquals(true, pointFive.isChildOf(two));
        assertEquals(false, pointFive.isChildOf(pointFive));
        assertEquals(false, pointFive.isChildOf(one));
        assertEquals(false, pointFive.isChildOf(pointSevenFive));
        assertEquals(false, pointFive.isChildOf(three));
        
        assertEquals(false, two.isAncestorOf(two));
        assertEquals(true, two.isAncestorOf(one));
        assertEquals(true, two.isAncestorOf(pointSevenFive));
        assertEquals(true, two.isAncestorOf(pointFive));
        assertEquals(true, two.isAncestorOf(three));
        
        assertEquals(false, pointFive.isAncestorOf(two));
        assertEquals(false, pointFive.isAncestorOf(pointFive));
        assertEquals(true, pointFive.isAncestorOf(one));
        assertEquals(true, pointFive.isAncestorOf(pointSevenFive));
        assertEquals(false, pointFive.isAncestorOf(three));
        
        assertEquals(false, one.isAncestorOf(two));
        assertEquals(false, one.isAncestorOf(pointFive));
        assertEquals(false, one.isAncestorOf(one));
        assertEquals(true, one.isAncestorOf(pointSevenFive));
        assertEquals(false, one.isAncestorOf(three));
        
        assertEquals(false, pointSevenFive.isAncestorOf(two));
        assertEquals(false, pointSevenFive.isAncestorOf(one));
        assertEquals(false, pointSevenFive.isAncestorOf(pointFive));
        assertEquals(false, pointSevenFive.isAncestorOf(pointSevenFive));
        assertEquals(false, pointSevenFive.isAncestorOf(three));
        
        assertEquals(false, three.isAncestorOf(two));
        assertEquals(false, three.isAncestorOf(pointFive));
        assertEquals(false, three.isAncestorOf(one));
        assertEquals(false, three.isAncestorOf(pointSevenFive));
        assertEquals(false, three.isAncestorOf(three));
        
        
        assertEquals(false, two.isDescendentOf(two));
        assertEquals(false, two.isDescendentOf(pointFive));
        assertEquals(false, two.isDescendentOf(one));
        assertEquals(false, two.isDescendentOf(pointSevenFive));
        assertEquals(false, two.isDescendentOf(three));
        
        assertEquals(true, pointFive.isDescendentOf(two));
        assertEquals(false, pointFive.isDescendentOf(pointFive));
        assertEquals(false, pointFive.isDescendentOf(one));
        assertEquals(false, pointFive.isDescendentOf(pointSevenFive));
        assertEquals(false, pointFive.isDescendentOf(three));
        
        assertEquals(true, pointSevenFive.isDescendentOf(two));
        assertEquals(true, pointSevenFive.isDescendentOf(pointFive));
        assertEquals(true, pointSevenFive.isDescendentOf(one));
        assertEquals(false, pointSevenFive.isDescendentOf(pointSevenFive));
        assertEquals(false, pointSevenFive.isDescendentOf(three));
        
        assertEquals(true, three.isDescendentOf(two));
        assertEquals(false, three.isDescendentOf(pointFive));
        assertEquals(false, three.isDescendentOf(one));
        assertEquals(false, three.isDescendentOf(pointSevenFive));
        assertEquals(false, three.isDescendentOf(three));
        
        
        assertEquals(false, two.isDistantAncestorOf(two));
        assertEquals(false, two.isDistantAncestorOf(pointFive));
        assertEquals(true, two.isDistantAncestorOf(one));
        assertEquals(true, two.isDistantAncestorOf(pointSevenFive));
        assertEquals(false, two.isDistantAncestorOf(three));
        
        assertEquals(false, pointFive.isDistantAncestorOf(two));
        assertEquals(false, pointFive.isDistantAncestorOf(pointFive));
        assertEquals(false, pointFive.isDistantAncestorOf(one));
        assertEquals(true, pointFive.isDistantAncestorOf(pointSevenFive));
        assertEquals(false, pointFive.isDistantAncestorOf(three));
        
        assertEquals(false, one.isDistantAncestorOf(two));
        assertEquals(false, one.isDistantAncestorOf(pointFive));
        assertEquals(false, one.isDistantAncestorOf(one));
        assertEquals(false, one.isDistantAncestorOf(pointSevenFive));
        assertEquals(false, one.isDistantAncestorOf(three));
        
        assertEquals(false, pointSevenFive.isDistantAncestorOf(two));
        assertEquals(false, pointSevenFive.isDistantAncestorOf(pointFive));
        assertEquals(false, pointSevenFive.isDistantAncestorOf(one));
        assertEquals(false, pointSevenFive.isDistantAncestorOf(pointSevenFive));
        assertEquals(false, pointSevenFive.isDistantAncestorOf(three));
        
        assertEquals(false, three.isDistantAncestorOf(two));
        assertEquals(false, three.isDistantAncestorOf(pointFive));
        assertEquals(false, three.isDistantAncestorOf(one));
        assertEquals(false, three.isDistantAncestorOf(pointSevenFive));
        assertEquals(false, three.isDistantAncestorOf(three));
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
        assertEquals("hijk", hijk.getKey());
        assertEquals(null, hijk.left);
        assertEquals(null, hijk.right);
        assertEquals(instance, hijk.parent);
        
        BalancedBinaryTree<String> mmmm = instance.insert("mmmm").get(0);
        assertEquals("mmmm", mmmm.getKey());
        assertEquals(null, mmmm.left);
        assertEquals(null, mmmm.right);
        assertEquals("hijk", instance.left.getKey());
        assertEquals("mmmm", instance.right.getkey());
        
        BalancedBinaryTree<String> mmnn = instance.insert("mmnn").get(0);
        assertEquals(true, instance.right.right.getKey()Equals("mmnn"));*/
        
        WeightedBinaryTree<String> m = new WeightedBinaryTree<>("m", 5);
        /*InsertionResult result = m.insert("n", 4);
        LinkedList<WeightedBinaryTree<String>> list = result.getPathFromRoot();
        logger.info(list);
        assertEquals(2, list.size());
        assertEquals(m, list.get(0));
        assertEquals(InsertionResult.Status.CREATED, result.status);
        assert(m == list.get(0));
        
        //Adding a new node with greater weight, but no child node to switch with
        list = m.insert("k", 6).getPathFromRoot();
        logger.info(list);
        logger.info(m.getAsList(WeightedBinaryTree.SortType.WEIGHT));
        assertEquals(2, list.size());
        assertEquals("k", list.get(1).getKey());
        assertEquals("m", list.get(0).getKey());
        m.display();
        
        m = new WeightedBinaryTree<>("m", 5);
        list = m.insert("n", 4).getPathFromRoot();
        list = m.insert("o", 7).getPathFromRoot();
        logger.info(list);
        m.display();
        assertEquals(2, list.size());
        assertEquals("m", list.get(0).getKey());
        assertEquals("o", list.get(0).getRightChild().getKey());*/
        
        logger.info("");
        m = new WeightedBinaryTree<>("and", 5);
        InsertionResult result = m.insert("aardvark", 6);
        //logger.info(m.getPathFromRoot());
        m.insert("make", 12);
        result.insertedNode.display();
        //logger.info(m.getPathFromRoot());
        //logger.info(m.getPathFromRoot());
        //logger.info(m.parent);
        //logger.info(m.getAsList(WeightedBinaryTree.SortType.WEIGHT));
        //logger.info(m.getAsList(WeightedBinaryTree.SortType.NATURAL_ORDER));
        //logger.info(m.parent.getAsList(WeightedBinaryTree.SortType.WEIGHT));
        //logger.info(m.parent.getAsList(WeightedBinaryTree.SortType.NATURAL_ORDER));
        logger.info("m is " + m + ";  root is " + m.getRoot());
        result.getInsertedNode().display();
        m.getParent().display();
        logger.info("\n\n---");
        m.getRoot().display();
        
        m.getRoot().insert("and");
        m.getRoot().display();
    }
    
    @Test
    public void testKeepsTrackOfWeightCorrectlyOnWeightOneInserts() {
	logger.info("\ntestKeepsTrackOfWeightCorrectlyOnWeightOneInserts()");
	String sentence = "one two three one two one";
	WeightedBinaryTree<String> tree = new WeightedBinaryTree(".");
	//List<String> words = Arrays.asList(sentence.split(" "));
	String[] words = sentence.split(" ");
	for(int i = 0; i < words.length; i++) {
	    tree = tree.insert(words[i]).getRoot();
	    assertEquals(i + 2, tree.getTreeWeight(), 0.000000001);
	}
	
    }
    
    @Test
    public void testSimpleBinaryInsert() {
        logger.info("\ntesting simpleBinaryInsert()");
        WeightedBinaryTree<String> instance = new WeightedBinaryTree<>("m", 1);
        InsertionResult<String> result = null;
        //instance = instance.simpleBinaryInsert("o", 1, DuplicateEntryOption.UPDATE).getRoot();
        logger.info(instance.simpleBinaryInsert("o", 1, DuplicateEntryOption.UPDATE));
        //WeightedBinaryTree<String> o = new WeightedBinaryTree<>("o", 1);
        //instance.setRightChild(o);
        //logger.info(instance.setRightChild(new WeightedBinaryTree<>("o", 1)));
        instance.display();
        logger.info(instance.simpleBinaryInsert("j", 1, DuplicateEntryOption.UPDATE));
        instance.display();
        logger.info("result is " + instance.simpleBinaryInsert("j", 3, DuplicateEntryOption.UPDATE));
        instance.display();
        assertEquals(4.0, instance.get("j").weight, 0.0);
        assertEquals(6, instance.getTreeWeight(), 0.0);
        assertEquals(0.0, instance.get("j").getSubTreeWeight(), 0.0);
        assertEquals(4.0, instance.get("j").getTreeWeight(), 0.0);
        instance.simpleBinaryInsert("j", 1, DuplicateEntryOption.UPDATE).insertedNode.display();
        instance = instance.simpleBinaryInsert("j", 1, DuplicateEntryOption.UPDATE).getRoot();
        instance = instance.simpleBinaryInsert("n", 1, DuplicateEntryOption.UPDATE).getRoot();
        instance = instance.simpleBinaryInsert("s", 0, DuplicateEntryOption.UPDATE).getRoot();
        instance.simpleBinaryInsert("t", 1, DuplicateEntryOption.UPDATE);
        instance.simpleBinaryInsert("r", 1, DuplicateEntryOption.UPDATE);
        instance.simpleBinaryInsert("ss", 1, DuplicateEntryOption.UPDATE);
        result = instance.simpleBinaryInsert("ts", 1, DuplicateEntryOption.UPDATE);
        assertEquals("m", result.getRoot().getKey());
        assertEquals(5, result.getPathFromRoot().size());
        assertEquals(1, result.getInsertedNode().weight, 0.0);
        
        result = instance.simpleBinaryInsert("n", 1, DuplicateEntryOption.UPDATE);
        assertEquals("m", result.getRoot().getKey());
        assertEquals(3, result.getPathFromRoot().size());
        assertEquals(2, result.getInsertedNode().weight, 0.0);
        instance.display();
        /**/
    }
    
    @Test
    public void testUpdateSubTreeWeight() {
        logger.info("\ntesting updateSubTreeWeight(), no params");
        WeightedBinaryTree<String> m = new WeightedBinaryTree<>("m");
        WeightedBinaryTree<String> l = new WeightedBinaryTree<>("l");
        WeightedBinaryTree<String> n = new WeightedBinaryTree<>("n");
        assertEquals(0.0, m.getSubTreeWeight(), 0.0);
        m.left = l;
        assertEquals(0.0, m.getSubTreeWeight(), 0.0);
        m.updateSubTreeWeight();
        assertEquals(1.0, m.getSubTreeWeight(), 0.0);
        m.right = n;
        assertEquals(1.0, m.getSubTreeWeight(), 0.0);
        m.updateSubTreeWeight();
        assertEquals(2.0, m.getSubTreeWeight(), 0.0);
        m.right = null;
        m.updateSubTreeWeight();
        assertEquals(1.0, m.getSubTreeWeight(), 0.0);
        
        m.right = null;
        m.left = null;
        m.updateSubTreeWeight();
        assertEquals(0.0, m.getSubTreeWeight(), 0.0);
        m.right = n;
        m.updateSubTreeWeight();
        assertEquals(1.0, m.getSubTreeWeight(), 0.0);
    }
    
    @Test
    public void testRebalanceWhenThisIsRoot() {
	logger.info("\testing rebalance()");
	WeightedBinaryTree<String> m = null;
        
        m = new WeightedBinaryTree<>("m", 1);
        WeightedBinaryTree<String> j = m.simpleBinaryInsert("j", 10).getInsertedNode();
        WeightedBinaryTree<String> l = m.simpleBinaryInsert("l", 1).getInsertedNode();
        WeightedBinaryTree<String> i = m.simpleBinaryInsert("i", 1).getInsertedNode();
        WeightedBinaryTree<String> n = m.simpleBinaryInsert("n", 1).getInsertedNode();
        assertEquals(n, j.getSibling());
        m.display();
	m.rebalanceOneLevel();
    }
    
    @Test
    public void testRebalanceWhenParentIsRootAndThisIsLeft() {
        logger.info("\ntesting rebalance()");
        //parent is root and this is left child
        WeightedBinaryTree<String> m = null;
        
        m = new WeightedBinaryTree<>("m", 1);
        WeightedBinaryTree<String> j = m.simpleBinaryInsert("j", 10).getInsertedNode();
        WeightedBinaryTree<String> l = m.simpleBinaryInsert("l", 1).getInsertedNode();
        WeightedBinaryTree<String> i = m.simpleBinaryInsert("i", 1).getInsertedNode();
        //m.insert("l");
        WeightedBinaryTree<String> n = m.simpleBinaryInsert("n", 1).getInsertedNode();
        assertEquals(n, j.getSibling());
        m.display();
        
        j.rebalanceOneLevel();
        //logger.info("\nj:");
        j.display();
        
        //logger.info("\nm");
        //m.display();
        assertEquals(null, j.getParent());
        assertEquals(i, j.getLeftChild());
        assertEquals(m, j.getRightChild());
        assertEquals(l, m.getLeftChild());
        assertEquals(n, m.getRightChild());
    }
    
    @Test
    public void testRebalanceWhenParentIsRootAndThisIsRightChild() {    
        //parent is root and this is right child
        logger.info("\nparent is root and this is right child");
        WeightedBinaryTree<String> m = new WeightedBinaryTree<>("m");
        WeightedBinaryTree<String> n = new WeightedBinaryTree<>("n");
        WeightedBinaryTree<String> o = new WeightedBinaryTree<>("o", 10);
        WeightedBinaryTree<String> p = new WeightedBinaryTree<>("p", 1);
        WeightedBinaryTree<String> l = new WeightedBinaryTree<>("l");
        m.setWeight(1);
        m.setRightChild(o);
        m.setLeftChild(l);
        o.setLeftChild(n);
        o.setRightChild(p);
        m.display();
        o.rebalanceOneLevel();
        logger.info("");
        o.display();
        
        assertEquals(null, o.getParent());
        assertEquals(m, o.getLeftChild());
        assertEquals(l, m.getLeftChild());
        assertEquals(n, m.getRightChild());
        assertEquals(p, o.getRightChild());
        
        WeightedBinaryTree<String> treebeard = new WeightedBinaryTree("and", 1);
        treebeard = treebeard.simpleBinaryInsert("relevance", 8, DuplicateEntryOption.REPLACE).getRoot();
        treebeard.display();
        logger.info(treebeard.getRightChild());
        //logger.info(treebeard.getRightChild().rebalanceOneLevel().afterPathFromRoot.get(0));
        //treebeard = treebeard.getRightChild().rebalanceOneLevel().afterPathFromRoot.get(0);
        treebeard.getRightChild().rebalanceOneLevel();
        treebeard.display();
        //treebeard.getRightChild().display();
        logger.info(treebeard.getParent());
        logger.info(treebeard.getRightChild());
        logger.info(treebeard.getLeftChild());
    }
    
    @Test
    public void testRebalanceWhenParentIsLeftAndThisIsLeft() {   
        //parent is left child and this is left child
        logger.info("\nparent is left child and this is left child");
        WeightedBinaryTree<String> m = new WeightedBinaryTree<>("m", 1);
        WeightedBinaryTree<String> n = new WeightedBinaryTree<>("n"); //m.insert("n").getInsertedNode();
        m.setRightChild(n);
        WeightedBinaryTree<String> k = new WeightedBinaryTree<>("k");
        WeightedBinaryTree<String> i = new WeightedBinaryTree<>("i", 10);
        WeightedBinaryTree<String> h = new WeightedBinaryTree<>("h");
        WeightedBinaryTree<String> j = new WeightedBinaryTree<>("j");
        WeightedBinaryTree<String> l = new WeightedBinaryTree<>("l");
        m.setLeftChild(k);
        k.setLeftChild(i);
        k.setRightChild(l);
        //l.setLeftChild(null);
        //l.setRightChild(null);
        i.setLeftChild(h);
        i.setRightChild(j);
        //j.setLeftChild(null);
        //j.setRightChild(null);
        m.display();
        assertEquals(null, m.getParent());
        assertEquals(k, m.getLeftChild());
        assertEquals(n, m.getRightChild());
        
        i.rebalanceOneLevel();
        m.display();
        
        assertEquals(null, m.getParent());
        assertEquals(i, m.getLeftChild());
        assertEquals(n, m.getRightChild());
        assertEquals(h, i.getLeftChild());
        assertEquals(k, i.getRightChild());
        assertEquals(j, k.getLeftChild());
        assertEquals(l, k.getRightChild());
    }
    
    @Test
    public void testRebalanceWhenParentIsLeftAndThisIsRight() {     
        //parent is left child and this is right child
        logger.info("\nparent is left child and this is right child");
        WeightedBinaryTree<String> m = new WeightedBinaryTree<>("m", 1);
	WeightedBinaryTree<String> i = new WeightedBinaryTree<>("i", 1);
	WeightedBinaryTree<String> h = new WeightedBinaryTree<>("h", 1);
        WeightedBinaryTree<String> k = new WeightedBinaryTree<>("k", 10);
        WeightedBinaryTree<String> j = new WeightedBinaryTree<>("j", 1);
        WeightedBinaryTree<String> l = new WeightedBinaryTree<>("l", 1);
        WeightedBinaryTree<String> n = new WeightedBinaryTree<>("n", 1);
        m.setLeftChild(i);
	i.setLeftChild(h);
	i.setRightChild(k);
        k.setLeftChild(j);
	k.setRightChild(l);
        m.setRightChild(n);
        
        //m.display();
        assertEquals(null, m.getParent());
        assertEquals(i, m.getLeftChild());
        assertEquals(h, i.getLeftChild());
        assertEquals(k, i.getRightChild());
        assertEquals(j, k.getLeftChild());
	assertEquals(l, k.getRightChild());
        assertEquals(n, m.getRightChild());
        
        k.rebalanceOneLevel();
        //m.display();
        assertEquals(null, m.getParent());
        assertEquals(k, m.getLeftChild());
        assertEquals(i, k.getLeftChild());
        assertEquals(h, i.getLeftChild());
        assertEquals(j, i.getRightChild());
        assertEquals(l, k.getRightChild());
        assertEquals(n, m.getRightChild());
        
    }
    
    @Test
    public void testRebalanceWhenParentIsRightAndThisIsLeft() {
        logger.info("parent is right child and this is left child");
        WeightedBinaryTree<String> m = new WeightedBinaryTree<>("m");
        WeightedBinaryTree<String> l = new WeightedBinaryTree<>("l");
        WeightedBinaryTree<String> r = new WeightedBinaryTree<>("r");
        WeightedBinaryTree<String> p = new WeightedBinaryTree<>("p", 10);
        WeightedBinaryTree<String> n = new WeightedBinaryTree<>("n");
        WeightedBinaryTree<String> q = new WeightedBinaryTree<>("q");
        WeightedBinaryTree<String> s = new WeightedBinaryTree<>("s");
        
        m.setLeftChild(l);
        m.setRightChild(r);
        r.setLeftChild(p);
        p.setLeftChild(n);
        p.setRightChild(q);
        r.setRightChild(s);
        
        assertEquals(null, m.getParent());
        assertEquals(l, m.getLeftChild());
        assertEquals(r, m.getRightChild());
        assertEquals(p, r.getLeftChild());
        assertEquals(n, p.getLeftChild());
        assertEquals(q, p.getRightChild());
        assertEquals(s, r.getRightChild());
        
        p.rebalanceOneLevel();
        assertEquals(null, m.getParent());
        assertEquals(l, m.getLeftChild());
        assertEquals(p, m.getRightChild());
        assertEquals(n, p.getLeftChild());
        assertEquals(r, p.getRightChild());
        assertEquals(q, r.getLeftChild());
        assertEquals(s, r.getRightChild());
        
    }
    
    @Test
    public void testRebalanceWhenParentIsRightAndThisIsRight() {
        logger.info("parent is right child and this is right child");
        WeightedBinaryTree<String> m = new WeightedBinaryTree<>("m");
        WeightedBinaryTree<String> l = new WeightedBinaryTree<>("l");
        WeightedBinaryTree<String> n = new WeightedBinaryTree<>("n");
        WeightedBinaryTree<String> o = new WeightedBinaryTree<>("o");
        WeightedBinaryTree<String> q = new WeightedBinaryTree<>("q", 10);
        WeightedBinaryTree<String> p = new WeightedBinaryTree<>("p");
        WeightedBinaryTree<String> r = new WeightedBinaryTree<>("r");
        
        m.setLeftChild(l);
        m.setRightChild(o);
        o.setLeftChild(n);
        o.setRightChild(q);
        q.setLeftChild(p);
        q.setRightChild(r);
        assertEquals(null, m.getParent());
        assertEquals(l, m.getLeftChild());
        assertEquals(o, m.getRightChild());
        assertEquals(n, o.getLeftChild());
        assertEquals(q, o.getRightChild());
        assertEquals(p, q.getLeftChild());
        assertEquals(r, q.getRightChild());
	m.display();
        
        q.rebalanceOneLevel();
	m.display();
        assertEquals(null, m.getParent());
        assertEquals(l, m.getLeftChild());
        assertEquals(q, m.getRightChild());
        assertEquals(o, q.getLeftChild());
        assertEquals(n, o.getLeftChild());
        assertEquals(p, o.getRightChild());
        assertEquals(r, q.getRightChild());
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
        WeightedBinaryTree<String> c = f.simpleBinaryInsert("C").getPathFromRoot().get(1);
        result = c.getPathToRoot();
        assertEquals(2, result.size());
        result.forEach(tree -> logger.info(tree));
        
        WeightedBinaryTree b = f.simpleBinaryInsert("B").getPathFromRoot().get(2);
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
        result = f.simpleBinaryInsert("C").getPathFromRoot();
        assertEquals(2, result.size());
        for(WeightedBinaryTree tree : result) {
            logger.info(tree);
        }
        
        //BalancedBinaryTree b = f.insert("B").get(0);
        //result = b.getPathFromRoot();
        result = f.simpleBinaryInsert("B").getPathFromRoot();
        assertEquals(3, result.size());
        for(WeightedBinaryTree tree : result) {
            logger.info(tree);
        }
        assertEquals("B", result.get(2).getKey());
        assertEquals("C", result.get(1).getKey());
        assertEquals("F", result.get(0).getKey());
    }
    @Test
    public void testGetRoot() {
        logger.info("\ntesting getRoot()");
        WeightedBinaryTree<String> m = new WeightedBinaryTree<>("m", 5);
        LinkedList<WeightedBinaryTree<String>> list = m.simpleBinaryInsert("n", 4).getPathFromRoot();
        assertEquals("m", list.get(1).getRoot().getKey());
    }
    
    @Test
    public void testGetAsList() {
        logger.info("\ntesting getAsList()");
        WeightedBinaryTree<String> tree = this.getBasicTree();
        //System.out.println("\ntree is " + tree);
        
        LinkedList<WeightedBinaryTree<String>> list = tree.getAsList(WeightedBinaryTree.SortType.NATURAL_ORDER);
        logger.info("depth first list = " + list);
        
        /*ArrayList<String> words = new ArrayList<String>();
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
        logger.info("sorted word = " + words);*/
        
        tree.display();
        list = tree.getAsList(WeightedBinaryTree.SortType.WEIGHT);
        logger.info("breadth first list = " + list);
    }
    
    //TODO:  Did something change in thie "recently"?
    //Why is it giving a tree where the root has no left?  I thought it did,
    //and apparently a unit test or two thought so too.
    private WeightedBinaryTree<String> getBasicTree() {
        WeightedBinaryTree<String> treebeard = null;
        
        treebeard = new WeightedBinaryTree("and", 1);
        treebeard = treebeard.insert("relevance", 8).getRoot();
        treebeard = treebeard.insert("aa", 10).getRoot();
        treebeard = treebeard.insert("holiday", 5).getRoot();
        treebeard = treebeard.insert("injure", 2).getRoot();
        treebeard = treebeard.insert("instantiate", 1).getRoot();
        treebeard = treebeard.insert("irritating", 2).getRoot();
        //TODO:  Why does using insert instead of simpleBinaryInsert break several tests?!!!!
        
        return treebeard.getRoot();
    }
    
    @Test
    public void makeWordHistogram() {
        logger.info("making a word histogram");
        String text = "and I want to make a word histogram and and I and histogram I";
        WeightedBinaryTree<String> hist = new WeightedBinaryTree<>("a");
        String[] words = text.split(" ");
        /*for(String word : words) {
            hist.insert(word, 1.0, DuplicateEntryOption.UPDATE);
        }
        for(String word : words) {
            logger.info(hist.get(word));
        }
        logger.info(hist.getAsList(WeightedBinaryTree.SortType.NATURAL_ORDER));
        logger.info(hist.getAsList(WeightedBinaryTree.SortType.WEIGHT));*/
        
        text = "The sun was shining on the sea,\n"
/**/ + "Shining with all his might:\n"
/**/+ "He did his very best to make\n"
/**/+ "The billows smooth and bright--\n"
/**/+ "And this was odd, because it was\n"
+ "The middle of the night."/**/;
        text = text.toLowerCase().replaceAll("\\.", "").replaceAll(":", "").replaceAll("\\-", "").replaceAll("\n", " ").replaceAll(",", "");
        
        //text = "a b c d e f g a b c d e f a b c d e a b c d a b c a b";
        hist = new WeightedBinaryTree<>("a");
        words = text.split(" ");
        for(String word : words) {
            hist.getRoot().insert(word, 1.0, DuplicateEntryOption.UPDATE);
        }
        for(String word : words) {
            //logger.info(hist.get(word));
        }
        hist = hist.getRoot();
        hist.display();
        logger.info(hist.getAsList(WeightedBinaryTree.SortType.NATURAL_ORDER));
        logger.info(hist.getAsList(WeightedBinaryTree.SortType.WEIGHT));
        LinkedList<WeightedBinaryTree<String>> byWeight = hist.getAsList(WeightedBinaryTree.SortType.WEIGHT);
        Histogram h = new Histogram(words);
        logger.info(h.toString("\n"));
        
        String currentValue = null;
        WeightedBinaryTree<String> currentNode = null;
        for(int i = 0; i < h.getCounts().size(); i++) {
            currentValue = (String)h.getValues().get(i);
            System.out.println(currentValue + " " + h.getCounts().get(i));
            currentNode = hist.get(currentValue);
            if(currentNode.getWeight() != h.getCounts().get(i)) {
                fail("counts for " + currentValue + " did not match:  " + h.getCounts().get(i) + " vs " + currentNode.getWeight());
            }
        }
        //byWeight.forEach(logger::info);
        
        //Histogram histogram = new Histogram(words);
        //logger.info(histogram.toString());
       
        //hist.insert("holiday", 5);
        /*hist = new WeightedBinaryTree("holiday", 5);
        hist.display();
        hist = hist.insert("and", 8, DuplicateEntryOption.REPLACE).getRoot();
        hist.display();
        hist = hist.insert("the", 10).getRoot();
        hist.display();
        hist = hist.insert("relevance", 1).getRoot();
        hist.display();
        hist = hist.insert("injure", 2).getRoot();
        hist.display();*/
        /**hist = new WeightedBinaryTree<>("the", 1);
        hist = hist.insert("holiday", 5).getRoot();
        //hist.display();
        hist = hist.insert("and", 8).getRoot();
        //hist.display();
        hist = hist.insert("the", 10).getRoot();
        //hist.display();
        hist = hist.insert("relevance", 1).getRoot();
        hist = hist.insert("injure", 2).getRoot();
        logger.info("\nfinal:");
        hist.display();
        logger.info("\n" + hist.getAsList(WeightedBinaryTree.SortType.NATURAL_ORDER));
        logger.info("\n" + hist.getAsList(WeightedBinaryTree.SortType.WEIGHT));
        /**/
    }
    
    @Test
    public void readWordHistogramFromFile() {
        logger.info("\nreadWordHistogramFromFile()");
        String filename = "jabberwocky.txt";
        filename = "beowulf.txt";
        filename = "through_the_looking_glass.txt";
        filename = "les_miserables.txt";
        filename = "beowulf i to xxii.txt";
        try {
            List<String> words = TextReader.getWordsAsList(filename);
            words = words.stream().map(word -> word.toLowerCase().replaceAll("\\.", "").replaceAll(":", "").replaceAll("\\-", "").replaceAll("\n", " ").replaceAll(",", "").replaceAll("\"", "")).collect(java.util.stream.Collectors.toList());
        
            Histogram h = new Histogram(words);
            WeightedBinaryTree<String> hist = new WeightedBinaryTree<String>("mmmm");
            for(String word : words) {
                hist.getRoot().insert(word);
            }
            hist = hist.getRoot();
            hist.display();
            logger.info("\n---weight");
            logger.info(hist.getAsList(WeightedBinaryTree.SortType.WEIGHT));
            logger.info("\n---natural order");
            logger.info(hist.getAsList(WeightedBinaryTree.SortType.NATURAL_ORDER));
            String currentValue = null;
            WeightedBinaryTree<String> currentNode = null;
            for(int i = 0; i < h.getCounts().size(); i++) {
                currentValue = (String)h.getValues().get(i);
                //System.out.println(currentValue + " " + h.getCounts().get(i));
                currentNode = hist.get(currentValue);
                if(currentNode == null) {
                    fail("did not find " + currentValue + " in tree histogram");
                }
                if(currentNode.getWeight() != h.getCounts().get(i)) {
                    fail("counts for " + currentValue + " did not match:  " + h.getCounts().get(i) + " vs " + currentNode.getWeight());
                }
            }
            //assertEquals(true, verifyNaturalOrder(removeGeneric(hist.getAsList(WeightedBinaryTree.SortType.NATURAL_ORDER))));
            //assertEquals(true, verifySortOrder(removeGeneric(hist.getAsList(WeightedBinaryTree.SortType.WEIGHT))));
            
        } catch(IOException e) {
            fail("could not read file " + filename + " in readWordHistogramFromFile():  " + e.getMessage());
        }
        
    }
    
    @Test
    public void testVerifyOrderFunctions() {
        logger.info("\ntestVerifyFunctions()");
        List<WeightedBinaryTree> ints = new ArrayList<>();
        ints.add(new WeightedBinaryTree<Integer>(5));
        ints.add(new WeightedBinaryTree<Integer>(4));
        ints.add(new WeightedBinaryTree<Integer>(3));
        ints.add(new WeightedBinaryTree<Integer>(2));
        assertEquals(true, verifyNaturalOrder(ints));
        ints.add(new WeightedBinaryTree<Integer>(6));
        assertEquals(false, verifyNaturalOrder(ints));
        
        List<WeightedBinaryTree> words = new ArrayList<>();
        words.add(new WeightedBinaryTree<String>("the", 14));
        words.add(new WeightedBinaryTree<String>("and", 10));
        words.add(new WeightedBinaryTree<String>("a", 8));
        words.add(new WeightedBinaryTree<String>("other", 3));
        assertEquals(true, verifySortOrder(words));
        words.add(new WeightedBinaryTree<String>("herculean", 147));
        assertEquals(false, verifySortOrder(words));
    }
    
    private boolean verifyNaturalOrder(List<WeightedBinaryTree> list) {
        //TODO: some Java 8 way?
        for(int i = 1; i < list.size(); i++) {
            if(list.get(i - 1).compareTo(list.get(i)) < 1) {
                return false;
            }
        }
        return true;
    }
    
    private boolean verifySortOrder(List<WeightedBinaryTree> list) {
        for(int i = 1; i < list.size(); i++) {
            if(list.get(i - 1).getWeight() < list.get(i).getWeight()) {
                return false;
            }
        }
        return true;
    }
    
    private List<WeightedBinaryTree> removeGeneric(List<WeightedBinaryTree<String>> list) {
        List<WeightedBinaryTree> result = new ArrayList<WeightedBinaryTree>();
        result.stream().forEach(item -> result.add(item));
        return result;
    }
    
    @Test
    public void testFindFirst() {
        logger.info("\ntesting findFirst()");
        WeightedBinaryTree<String> tree = this.getBasicTree();
        tree.display();
        Optional<WeightedBinaryTree<String>> first = tree.findFirst(null);
        assertEquals(false, first.isPresent());
        Predicate<WeightedBinaryTree<String>> p = (WeightedBinaryTree<String> t) -> t.getKey().startsWith("r");
        first = tree.findFirst(p);
        logger.debug(p.test(tree));
        //logger.debug(p.test(tree.getLeftChild()));
        logger.debug(p.test(tree.getRightChild()));
        assertEquals(tree.getRightChild(), first.get());
        
        //assertEquals(tree.getRightChild(), tree.findFirst((WeightedBinaryTree<String> t) -> t.getKey().startsWith("r")));
	p = (WeightedBinaryTree<String> t) -> t.getKey().equals("and");
	first = tree.findFirst(p);//(WeightedBinaryTree<String> t) -> t.getKey().equals("and"));
	assertTrue(first.isPresent());
	assertEquals("and", first.get().getKey());
	
	WeightedBinaryTree<String> hist = new WeightedBinaryTree<>("the");
        //hist.insert("the", 1);
        hist.insert("holiday", 5);
        hist.insert("and", 8);
        hist.insert("the", 10);
        hist.insert("relevance", 1);
        hist.insert("injure", 2);
        hist.insert("Philippino", 1);
        hist.insert("Amaranthus", 1);
        hist.insert("hardwood", 1);
        hist.insert("philharmonic", 1);
        hist.insert("the", 1);
	
	first = hist.findFirst(w -> w.equals("Amaranthus"));
	//assertTrue(first.isPresent());
	//assertEquals("Amaranthus", first.get().getKey());
	hist.display();
	p = (WeightedBinaryTree<String> t) -> t.getKey().equals("Amaranthus");
	first = hist.findFirst(p);//w -> w.getKey().startsWith("Amaran"));
	assertTrue(first.isPresent());
	p = (WeightedBinaryTree<String> t) -> t.getKey().equals("injure");
	first = hist.findFirst(p);//w -> w.equals("injure"));
	assertTrue(first.isPresent());
    }
    
    @Test
    public void testQueryFromFirst() {
        logger.info("\ntesting queryFromFirst()");
        WeightedBinaryTree<String> tree = this.getBasicTree();
        tree.display();
        Predicate<WeightedBinaryTree<String>> p = (WeightedBinaryTree<String> t) -> t.getKey().startsWith("in");
        List<WeightedBinaryTree<String>> result = tree.queryFromFirst(p);
        assertEquals(2, result.size());
        logger.debug(result);
        assertEquals("injure", result.get(0).getKey());
        assertEquals("instantiate", result.get(1).getKey());
        
        p = (WeightedBinaryTree<String> t) -> t.getKey().startsWith("xyz");
        result = tree.queryFromFirst(p);
        assertEquals(0, result.size());
    }
    
    @Test
    public void testGetRandomValue() {
	logger.info("\ntesting getRandomValue()");
	WeightedBinaryTree<String> tree = new WeightedBinaryTree("byzantium");
	tree.setWeight(10.0);
	tree.display();
	String value = tree.getRandomValue();
	logger.debug(value);
    }
    
    @Test
    public void testGetRandomValueWithZeroWeight() {
	logger.info("\ntestGetRandomValueWithZeroWeight()");
	WeightedBinaryTree<Integer> tree = new WeightedBinaryTree(42);
	tree.setWeight(0.0);
	Integer value = tree.getRandomValue();
	assertEquals(new Integer(42), value);
    }
    
    @Test
    public void testGetRandomValueManyValues() {
	logger.info("\ntesting getRandomValue() with many values");
	WeightedBinaryTree<String> tree = this.getBasicTree();
	tree.display();
	TreeHistogram<String> h = new TreeHistogram<>();
	
	int numValues = 10000000;
	for(int i = 0; i < numValues; i++) {
	    h.insert(tree.getRandomValue(), 1);
	}
	h.getAsList(Sort.COUNT).forEach(System.out::println);
	assertEquals(tree.get("aa").getWeight() / tree.getTreeWeight(), MathUtil.ratio(h.get("aa").get().count, numValues), EPSILON);
	h.getAsList(Sort.COUNT).forEach(i -> { 
	    assertEquals(tree.get(i.item).getWeight() / tree.getTreeWeight(), MathUtil.ratio(h.get(i.item).get().count, numValues), EPSILON);
	});
    }
}
