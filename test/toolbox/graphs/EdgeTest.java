/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package toolbox.graphs;

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
 * @author paul
 */
public class EdgeTest {
    
    private static Logger logger;
    
    public EdgeTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
        logger = ListArrayUtil.getLogger(EdgeTest.class, Level.DEBUG);
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
    public void testGetSource() {
        logger.info("\ntesting getSource()");
        Node source = new Node("source");
        Node destination = new Node("destination");
        Edge instance = new Edge(source, destination, 1);
        assertEquals(instance.source, instance.getSource());
        assertEquals(source, instance.source);
        assertEquals(source, instance.getSource());
    }

    @Test
    public void testSetSource() {
        logger.info("\ntesting setSource()");
        Node source = new Node("source");
        Node destination = new Node("destination");
        Edge instance = new Edge(source, destination, 1);
        assertEquals(instance.source, instance.getSource());
        assertEquals(source, instance.source);
        assertEquals(source, instance.getSource());
        
        Node newSource = new Node("another source");
        instance.setSource(newSource);
        assertEquals(instance.source, instance.getSource());
        assertEquals(newSource, instance.source);
        assertEquals(newSource, instance.getSource());
    }

    @Test
    public void testGetDestination() {
        logger.info("\ntesting getDestination()");
        Node source = new Node("source");
        Node destination = new Node("destination");
        Edge instance = new Edge(source, destination, 1);
        assertEquals(instance.destination, instance.getDestination());
        assertEquals(destination, instance.destination);
        assertEquals(destination, instance.getDestination());
    }

    @Test
    public void testSetDestination() {
        logger.info("\ntesting setDestination()");
        Node source = new Node("source");
        Node destination = new Node("destination");
        Edge instance = new Edge(source, destination, 1);
        assertEquals(instance.destination, instance.getDestination());
        assertEquals(destination, instance.destination);
        assertEquals(destination, instance.getDestination());
        
        Node newDest = new Node("new destination");
        instance.setDestination(newDest);
        assertEquals(instance.destination, instance.getDestination());
        assertEquals(newDest, instance.destination);
        assertEquals(newDest, instance.getDestination());
    }

    @Test
    public void testGetWeight() {
        logger.info("\ntesting getWeight()");
        Node source = new Node("source");
        Node destination = new Node("destination");
        Edge instance = new Edge(source, destination, 1);
        assertEquals(instance.weight, instance.getWeight());
        assertEquals(1, instance.weight);
        assertEquals(1, instance.getWeight());
    }

    @Test
    public void testSetWeight() {
        logger.info("\ntesting setWeight()");
        Node source = new Node("source");
        Node destination = new Node("destination");
        Edge instance = new Edge(source, destination, 1);
        assertEquals(instance.weight, instance.getWeight());
        assertEquals(1, instance.weight);
        assertEquals(1, instance.getWeight());
        
        instance.setWeight(45);
        assertEquals(instance.weight, instance.getWeight());
        assertEquals(45, instance.weight);
        assertEquals(45, instance.getWeight());
    }

    @Test
    public void testCompareTo() {
        logger.info("\ntesting compareTo()");
        Node a = new Node("a");
        Node b = new Node("b");
        Node c = new Node("c");
        Node d = new Node("d");
        
        Edge e1 = new Edge(a, b, 27);
        Edge e2 = new Edge(c, d, 1001);
        Edge e3 = new Edge(d, a, 1001);
        assertEquals(e1.compareTo(e2), -1);
        assertEquals(e2.compareTo(e1), 1);
        assertEquals(e2.compareTo(e3), 0);
        assertEquals(e3.compareTo(e2), 0);
    }

    @Test
    public void testToString() {
        logger.info("\ntesting toString()");
        Node result = null;
        Node source = new Node("source");
        Node destination = new Node("destination");
        Edge instance = new Edge(source, destination, 1);
        assertEquals("source -- 1 --> destination", instance.toString());
    }
}