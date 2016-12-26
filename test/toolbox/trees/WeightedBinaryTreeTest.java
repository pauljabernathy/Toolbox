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
import toolbox.stats.Histogram;

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
        assertEquals(1, seven.getIndividualTorque(), 0.0);
        assertEquals(2, five.getIndividualTorque(), 0.0);
        assertEquals(3.0, seven.getTreeTorque(), 0.0);
        
        five.setLeftChild(seven, DuplicateEntryOption.UPDATE);
        assertEquals(null, five.left);
        
        seven.setLeftChild(seven);
        assertEquals(five, seven.left);
        
        WeightedBinaryTree<String> m = new WeightedBinaryTree<>("m", 1);
        InsertionResult<String> result = null;
        WeightedBinaryTree<String> l = m.setLeftChild(new WeightedBinaryTree<>("l")).getLeftChild();
        WeightedBinaryTree<String> o = m.setRightChild(new WeightedBinaryTree<>("o", 1)).getRightChild();
        WeightedBinaryTree<String> n = o.setLeftChild(new WeightedBinaryTree<>("n")).getLeftChild();
        WeightedBinaryTree<String> p = o.setRightChild(new WeightedBinaryTree<>("p")).getRightChild();
        assertEquals(l, m.getLeftChild());
        assertEquals(o, m.getRightChild());
        assertEquals(p, m.getRightChild().getRightChild());
        assertEquals(n, m.getRightChild().getLeftChild());
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
    public void testSetRightChild() {
        logger.info("\ntesting setRightChild()");
        WeightedBinaryTree<String> instance = new WeightedBinaryTree<>("m");
        instance.setRightChild(new WeightedBinaryTree<>("o", 4));
        assertEquals(null, instance.left);
        assertEquals("o", instance.right.value);
        assertEquals(8, instance.right.getIndividualTorque(), 0.0);
        assertEquals(9, instance.getTreeTorque(), 0.0);
        
        WeightedBinaryTree<String> m = new WeightedBinaryTree<>("m", 1);
        WeightedBinaryTree<String> n = m.setRightChild(new WeightedBinaryTree("n", 1)).getRightChild();//.getRoot();
        //instance.display();
        WeightedBinaryTree<String> j = m.setLeftChild(new WeightedBinaryTree("j", 1)).getLeftChild();
        WeightedBinaryTree<String> l = j.setRightChild(new WeightedBinaryTree<>("l", 1)).getRightChild();
        m.display();
        assertEquals(j, m.getLeftChild());
        assertEquals(n, m.getRightChild());
        assertEquals(l, j.getRightChild());
        
        //m.setLeftChild(l);
        m.left = l;
        j.setRightChild(m, DuplicateEntryOption.REPLACE);
        //j.right = m;
        logger.info("\n");
        j.display();
        assertEquals(m, j.getRightChild());
        assertEquals(l, m.getLeftChild());
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
    
    @Test
    public void testGetSiblingAndIsLeftOrRight() {
        logger.info("\ntesting getSibbling");
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
        InsertionResult result = m.insert("n", 4);
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
    public void testSimpleBinaryInsert() {
        logger.info("\ntesting simpleBinaryInsert()");
        WeightedBinaryTree<String> instance = new WeightedBinaryTree<>("m", 1);
        InsertionResult<String> result = null;
        //instance = instance.simpleBinaryInsert("o", 1, DuplicateEntryOption.UPDATE).getRoot();
        logger.info(instance.simpleBinaryInsert("o", 1, DuplicateEntryOption.UPDATE));
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
        assertEquals("m", result.getRoot().value);
        assertEquals(5, result.getPathFromRoot().size());
        assertEquals(1, result.getInsertedNode().weight, 0.0);
        
        result = instance.simpleBinaryInsert("n", 1, DuplicateEntryOption.UPDATE);
        assertEquals("m", result.getRoot().value);
        assertEquals(3, result.getPathFromRoot().size());
        assertEquals(2, result.getInsertedNode().weight, 0.0);
        instance.display();
        /**/
    }
    
    @Test
    public void testRebalance() {
        logger.info("\ntesting rebalance()");
        WeightedBinaryTree<String> instance = null;
        
        instance = new WeightedBinaryTree<>("m", 1);
        WeightedBinaryTree<String> j = instance.insert("j", 10).getInsertedNode();      
        WeightedBinaryTree<String> i = instance.insert("i", 1).getInsertedNode();
        instance.insert("l");
        WeightedBinaryTree<String> n = instance.insert("n").getInsertedNode();
        assertEquals(n, j.getSibling());
        System.out.println("\ninstance:");System.out.flush();
        instance.display();
        
        instance.get("j").rebalance();
        System.out.println("\ninstance:");System.out.flush();
        j.display();
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
    
    private WeightedBinaryTree<String> getBasicTree() {
        WeightedBinaryTree<String> treebeard = null;
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
        
        /**WeightedBinaryTree<String> treebeard = new WeightedBinaryTree<>("make", 2.0);
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
        
        /*treebeard = new WeightedBinaryTree("holiday", 5);
        treebeard = treebeard.insert("and", 8).getRoot();
        treebeard = treebeard.insert("the", 10).getRoot();
        treebeard = treebeard.insert("relevance", 1).getRoot();
        treebeard = treebeard.insert("injure", 2).getRoot();*/
        treebeard = new WeightedBinaryTree("and", 1);
        treebeard = treebeard.insert("relevance", 8).getRoot();
        treebeard.display();
        treebeard = treebeard.insert("aa", 10).getRoot();
        treebeard.display();
        treebeard = treebeard.insert("holiday", 5).getRoot();
        treebeard = treebeard.insert("injure", 2).getRoot();
        
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
        
        text = "The sun was shining on the sea,\n" +
"Shining with all his might:\n" +
"He did his very best to make\n" +
"The billows smooth and bright--\n" +
"And this was odd, because it was\n" +
"The middle of the night.";
        text = text.toLowerCase().replaceAll("\\.", "").replaceAll(":", "").replaceAll("\\-", "");
        
        text = "a b c d e f g a b c d e f a b c d e a b c d a b c a b";
        hist = new WeightedBinaryTree<>("a");
        words = text.split(" ");
        for(String word : words) {
            hist.insert_old(word, 1.0, DuplicateEntryOption.UPDATE);
        }
        for(String word : words) {
            //logger.info(hist.get(word));
        }
        //logger.info(hist.getAsList(WeightedBinaryTree.SortType.NATURAL_ORDER));
        //logger.info(hist.getAsList(WeightedBinaryTree.SortType.WEIGHT));
        LinkedList<WeightedBinaryTree<String>> byWeight = hist.getAsList(WeightedBinaryTree.SortType.WEIGHT);
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
        hist = new WeightedBinaryTree<>("the", 1);
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
    }
}
