/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package toolbox.stats;

import toolbox.stats.ProbDist;
import java.util.ArrayList;
import java.util.List;
import java.util.HashMap;
import toolbox.stats.ProbDist;
import toolbox.Utilities;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

import org.apache.log4j.*;

/**
 *
 * @author paul
 */
public class ProbDistTest {
    
    private static Logger logger;
    
    public ProbDistTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
        logger = Logger.getLogger(ProbDistTest.class);
        logger.addAppender(new ConsoleAppender(new PatternLayout(toolbox.Constants.DEFAULT_LOG_FORMAT)));
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

    /**
     * Test of reset method, of class CPD.
     */
    @Test
    public void testReset() {
        logger.info("\nreset()");
        ProbDist instance = new ProbDist();
        instance.reset();
    }

    /**
     * Test of getValues method, of class CPD.
     */
    @Test
    public void testGetValues() {
        logger.info("\ntesting getValues()");
        ProbDist instance = new ProbDist();
        List result = instance.getValues();
        if(result == null) {
            fail("result was null");
        }
        instance.add("A", .3);
        instance.add("B", .4);
        instance.add("C", .1);
        result = instance.getValues();
        assertEquals(4, result.size());
        assertEquals(null, result.get(0));
        assertEquals("A", result.get(1));
        assertEquals("B", result.get(2));
        assertEquals("C", result.get(3));
    }

    /**
     * Test of getValue method, of class CPD.
     */
    //@Test
    public void testGetValue() {
        logger.info("\ntesting getValue()");
        int index = 0;
        ProbDist<String> instance = new ProbDist<String>();
        assertEquals(null, instance.getValue(0));
        //instance.add("Paul", 0.5);
        Object expResult = null;
        Object result = instance.getValue(index);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of setValues method, of class CPD.
     */
    //@Test
    public void testSetValues() {
        logger.info("\nsetValues()");
        ProbDist instance = new ProbDist();
        instance.setValues(null);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    @Test
    public void testAdd() {
        logger.info("\ntesting add()");
        ProbDist<String> instance = new ProbDist<String>();
        boolean result = false;
        logger.debug(instance.contains("Duke"));
        result = instance.add("Duke", 0.5);
        assertEquals(true, result);
        instance.display();
        result = instance.add("UNC", 0.501);
        assertEquals(false, result);
        instance.display();
        
        result = instance.add("UNC", 0.50);
        assertEquals(true, result);
        instance.display();
        
        instance.reset();
        logger.debug("\n\nresetting");
        instance.add("Duke", 0.2);
        instance.add("UNC", 0.3);
        instance.add("Wake Forest", 0.2);
        instance.add("NC State", 0.15);
        result = instance.add("GA Tech", 0.15);
        logger.debug("added GA Tech? " + result);
        instance.display();
        logger.debug("probs;");
        showArrayList(instance.getProbabilities());
        logger.debug("values");
        showArrayList(instance.getValues());
    }
    
    @Test
    public void testValidateNormalized() {
        logger.info("\ntesting validateNormalized()");
        ArrayList<Double> probs = null;
        assertEquals(false, ProbDist.validateNormalized(probs));
        
        probs = new ArrayList<Double>();
        assertEquals(false, ProbDist.validateNormalized(probs));
        
        probs.add(.99);
        assertEquals(false, ProbDist.validateNormalized(probs));
        
        probs.add(.01);
        assertEquals(true, ProbDist.validateNormalized(probs));
        
        probs.add(.01);
        assertEquals(false, ProbDist.validateNormalized(probs));
        
        probs.remove(.01);
        assertEquals(true, ProbDist.validateNormalized(probs));
        
        probs.add(5.0);
        assertEquals(false, ProbDist.validateNormalized(probs));
    }
    
    @Test
    public void testGetRandomValue() {
        logger.info("\ntesting getRandomValue()");
        ProbDist<String> instance = new ProbDist<String>();
        boolean added = false;
        instance.add("Duke", 0.2);
        instance.add("UNC", 0.3);
        instance.add("Wake Forest", 0.2);
        instance.add("NC State", 0.15);
        added = instance.add("GA Tech", 0.15);
        logger.debug("added GA Tech? " + added);
        assertEquals(true, ProbDist.validateNormalized(instance.getProbabilities()));
        /*logger.debug("values");
        showArrayList(instance.getValues());
        logger.debug("probabilities");
        showArrayList(instance.getProbabilities());*/
        instance.display();
        int[] counts = new int[5];
        int numRands = 100000;
        String rand = "";
        for(int i = 0; i < numRands; i++) {
            rand = instance.getRandomValue();
            //logger.debug(i + " " + rand);
            if(rand == null) {
                fail("was null");
            }
            if(rand.equals("Duke")) {
                counts[0]++;
            } else if(rand.equals("UNC")) {
                counts[1]++;
            } else if(rand.equals("Wake Forest")) {
                counts[2]++;
            } else if(rand.equals("NC State")) {
                counts[3]++;
            } else if(rand.equals(("GA Tech"))) {
                counts[4]++;
            }
        }
        showArray(counts);
        try {
            Histogram hist = new Histogram(new String[] { "Duke", "UNC", "Wake Forest", "NC State", "GA Tech" }, counts);
            logger.debug(hist.toString());
        } catch(ProbabilityException e) {
            logger.error("ProbabilityException trying to make a histogram in testGetRandomValue():  " + e.getMessage());
        }
    }

    /**
     * Test of getProbabilities method, of class CPD.
     */
    @Test
    public void testGetProbabilities() {
        logger.info("\ntesting getProbabilities()");
        ProbDist instance = new ProbDist();
        List result = instance.getProbabilities();
        assertEquals(1.0, result.get(0));
        
        instance.add("A", .3);
        instance.add("B", .4);
        instance.add("C", .1);
        result = instance.getProbabilities();
        assertEquals(4, result.size());
    }

    /**
     * Test of setProbabilities method, of class CPD.
     */
    @Test
    public void testSetProbabilities() {
        logger.info("\ntesting setProbabilities()");
        ArrayList<Double> probabilities = null;
        ProbDist instance = new ProbDist();
        try {
            instance.setProbabilities(null);
            if(instance.getProbabilities() == null) {
                fail("probabilities was null");
            }
        } catch(ProbabilityException e) {
            
        }
    }

    /**
     * Test of contains method, of class CPD.
     */
    @Test
    public void testContains() {
        logger.info("\ntesting contains()");
        Object value = null;
        ProbDist instance = new ProbDist();
        boolean result = instance.contains(value);
        assertEquals(true, result);
        //List values = instance.getValues();
        instance.add("a", .5);
        assertEquals(true, instance.contains("a"));
        assertEquals(false, instance.contains("b"));
        //values = instance.getValues();
        instance.add("b", .5);
        //values = instance.getValues();
        logger.debug(Utilities.listToString(instance.getValues()));
    }
    
    private void showArray(int[] input) {
        if(input == null) {
            return;
        }
        for(int i = 0; i < input.length; i++) {
            logger.debug(input[i]);
        }
    }
    
    public <T> void showArrayList(List<T> input) {
        if(input == null) {
            return;
        }
        logger.debug("\n");
        for(int i = 0; i < input.size(); i++) {
            logger.debug(i + " " + input.get(i));
        }
    }
    
    @Test
    public void testProbabilityOf() {
        logger.info("\ntesting probabilityOf()");
        ProbDist<String> instance = new ProbDist<String>();
        instance.add("Duke", 0.2);
        instance.add("UNC", 0.3);
        instance.add("Wake Forest", 0.2);
        instance.add("NC State", 0.15);
        instance.add("GA Tech", 0.15);
        //instance.display();
        assertEquals(.2, instance.probatilityOf("Duke"), 0.0);
        assertEquals(.3, instance.probatilityOf("UNC"), 0.0);
        assertEquals(.2, instance.probatilityOf("Wake Forest"), 0.0);
        assertEquals(.15, instance.probatilityOf("NC State"), 0.0);
        assertEquals(.15, instance.probatilityOf("GA Tech"), 0.0);
        
        assertEquals(0.0, instance.probatilityOf("Duk"), 0.0);
        assertEquals(0.0, instance.probatilityOf("false"), 0.0);
        assertEquals(0.0, instance.probatilityOf(null), 0.0);
    }
    
    @Test
    public void testCreateInstanceFromCounts() {
        logger.info("\ntesting createInstanceFromCounts()");
        ProbDist<String> result = null;
        ProbDist.createInstanceFromCounts(null, null);
        
        ArrayList<String> strValues = new ArrayList<String>();
        strValues.add("A");
        strValues.add("B");
        strValues.add("C");
        
        ArrayList<Integer> counts = new ArrayList<Integer>();
        counts.add(10);
        counts.add(20);
        counts.add(10);
        
        //TODO:  use assertEquals instead of display()
        result = ProbDist.createInstanceFromCounts(strValues, counts);
        result.display();
        
        counts.add(60);
        /*result = ProbDist.createInstanceFromCounts(strValues, counts);  //blows up as inteded because of the assert line
        result.display();*/
        strValues.add("C");
        result = ProbDist.createInstanceFromCounts(strValues, counts);  //blows up as inteded because of the assert line
        result.display();
        
        ArrayList<Boolean> boolValues = new ArrayList<Boolean>();
        boolValues.add(true);
        boolValues.add(false);
        ArrayList<Integer> boolCounts = new ArrayList<Integer>();
        boolCounts.add(100);
        boolCounts.add(150);
        
        ProbDist<Boolean> resultBool = ProbDist.createInstanceFromCounts(boolValues, boolCounts);  //blows up as inteded because of the assert line
        resultBool.display();
    }
    
    @Test
    public void testGetJointDistribution() {
        logger.info("\ntesting getJointDistribution()");
        ProbDist<String> fruit = new ProbDist<String>();
        fruit.add("blueberries", .7);
        fruit.add("grapes", .3);
        
        ProbDist<String> mains = new ProbDist<String>();
        mains.add("steak", .5);
        mains.add("mutton", .1);
        mains.add("Maine Lobster", .4);
        
        ProbDist<List> joint = ProbDist.getJointDistribution(fruit, mains);
        assertEquals(6, joint.getValues().size());
        for(List<String> values : joint.getValues()) {
            assertEquals(2, values.size());
        }
        joint.display();
        assertEquals("blueberries", joint.getValues().get(0).get(0));
        assertEquals("steak", joint.getValues().get(0).get(1));
        assertEquals("blueberries", joint.getValues().get(1).get(0));
        assertEquals("mutton", joint.getValues().get(1).get(1));
        assertEquals("blueberries", joint.getValues().get(2).get(0));
        assertEquals("Maine Lobster", joint.getValues().get(2).get(1));
        
        assertEquals("grapes", joint.getValues().get(3).get(0));
        assertEquals("steak", joint.getValues().get(3).get(1));
        assertEquals("grapes", joint.getValues().get(4).get(0));
        assertEquals("mutton", joint.getValues().get(4).get(1));
        assertEquals("grapes", joint.getValues().get(5).get(0));
        assertEquals("Maine Lobster", joint.getValues().get(5).get(1));
        
        assertEquals(.35, joint.getProbabilities().get(0), .00001);
        assertEquals(.07, joint.getProbabilities().get(1), .00001);
        assertEquals(.28, joint.getProbabilities().get(2), .00001);
        assertEquals(.15, joint.getProbabilities().get(3), .00001);
        assertEquals(.03, joint.getProbabilities().get(4), .00001);
        assertEquals(.12, joint.getProbabilities().get(5), .00001);
        logger.debug(joint.getEntropy());
    }
    
    @Test
    public void testGetEntropy() {
        logger.info("\ntesting getEntropy()");
        ProbDist<String> instance = new ProbDist<String>();
        assertEquals(0.0, instance.getEntropy(), .0000001);
        
        instance.add("first", 0.2424242);
        logger.debug(instance.getEntropy());
        logger.debug(ProbDist.validateNormalized(instance.getProbabilities()));
        toolbox.Utilities.showList(instance.getProbabilities());

        instance.add("second", 0.2065095);
        instance.add("third", 0.5510662);

        assertEquals(1.439321, instance.getEntropy(), .001);
            
        instance = new ProbDist<String>();
        assertEquals(0.0, instance.getEntropy(), .0000001);
        instance.add("A", .3333333333333333);
        instance.add("B", .3333333333333333);
        instance.add("C", .3333333333333333);
        assertEquals(1.584963, instance.getEntropy(), .000001);
        instance.add("D", 0.0);
        logger.debug(instance.getEntropy());
        assertEquals(1.584963, instance.getEntropy(), .000001);
        logger.debug(instance);
        
        try {
            Histogram hist = new Histogram(new String[] { "A", "B", "C", "D"}, new int[] { 33, 33, 33, 0 });
            logger.debug(hist.toString());
            logger.debug(hist.getProbDist());
            logger.debug(hist.getEntropy());
            assertEquals(1.584963, hist.getEntropy(), .000001);
        } catch(ProbabilityException e) {
            logger.error(e.getClass() + " in testGetEntropy():  " + e.getMessage());
        }
        
    }
    
    @Test
    public void testGetMutualInformation() {
        logger.info("\ntesting getMutualInformation");
        ProbDist<String> fruit = new ProbDist<String>();
        fruit.add("blueberries", .7);
        fruit.add("grapes", .3);
        
        ProbDist<String> mains = new ProbDist<String>();
        mains.add("steak", .5);
        mains.add("mutton", .1);
        mains.add("Maine Lobster", .4);
        
        logger.debug(ProbDist.getMutualInformation(fruit, mains));
    }
}