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

import org.apache.log4j.*;

/**
 *
 * @author paul
 */
public class NodeTest {
    
    private static Logger logger;
    
    public NodeTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
        logger = Logger.getLogger(NodeTest.class);
        logger.addAppender(new ConsoleAppender(new PatternLayout("%m%n")));
        logger.setLevel(Level.INFO);
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
    public void testGetId() {
    }

    @Test
    public void testSetId() {
    }

    @Test
    public void testGetNeighbors() {
    }

    /*@Test
    public void testSetNeighbors() {
    }*/

    @Test
    public void testAddNeighbor() {
        logger.info("\ntesting addNeighbor()");
        Node a = new Node("a");
        Node b = new Node("b");
        Node c = new Node("c");
        
        assertEquals(0, a.getNeighbors().size());
        assertEquals(0, b.getNeighbors().size());
        assertEquals(0, c.getNeighbors().size());
        
        a.addNeighbor(null, true);
        assertEquals(0, a.getNeighbors().size());
        
        a.addNeighbor(b, true);
        assertEquals(1, a.getNeighbors().size());
        //make sure it does not add it a second time
        a.addNeighbor(b, true);
        assertEquals(1, a.getNeighbors().size());
        
        assertEquals(1, b.getNeighbors().size());
        assertEquals("a", b.getNeighbors().get(0).getId());
        
        //c should still be lonely
        assertEquals(0, c.getNeighbors().size());
        b.addNeighbor(c, true);
        //now c should have a neightbor
        assertEquals(1, c.getNeighbors().size());
        //and b should still only have one
        assertEquals(2, b.getNeighbors().size());
        b.addNeighbor(c, true);
        assertEquals(1, c.getNeighbors().size());
        assertEquals(2, b.getNeighbors().size());
        
        c.addNeighbor(b, true);
        assertEquals(1, c.getNeighbors().size());
        assertEquals(2, b.getNeighbors().size());
    }
    
    @Test
    public void testGetEdges() {
        logger.info("\ntesting getEdges()");
        Node a = new Node("a");
        Node b = new Node("b");
        Node c = new Node("c");
        
        assertEquals(0, a.getEdges().size());
        assertEquals(0, b.getEdges().size());
        assertEquals(0, c.getEdges().size());
        
        a.addNeighbor(null, true);
        assertEquals(0, a.getEdges().size());
        
        a.addNeighbor(b, true);
        assertEquals(1, a.getEdges().size());
        assertEquals("b", a.getEdges().get(0).destination.getId());
        assertEquals("b", a.getEdges().get(0).getDestination().getId());
    }
}