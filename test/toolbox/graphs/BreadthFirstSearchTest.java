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
import java.util.List;
import java.util.LinkedList;
import java.util.HashMap;

/**
 *
 * @author paul
 */
public class BreadthFirstSearchTest {
    
    private static Logger logger;
    
    public BreadthFirstSearchTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
        logger = Logger.getLogger(BreadthFirstSearchTest.class);
        logger.addAppender(new ConsoleAppender(new PatternLayout("%m%n")));
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

    //@Test
    public void testFind() {
        logger.info("\ntesting find()");
        List<Node> result = null;
        result = BreadthFirstSearch.find(null, null);
        assertEquals(0, result.size());
        
        List<Node> graph = Utility.generateGraph();
        assertEquals("s", graph.get(1).getId());
        assertEquals("u", graph.get(3).getId());
        result = BreadthFirstSearch.find(graph.get(3), graph.get(1));
        assertEquals(1, result.size());
        logger.debug(result.get(0));
        
        assertEquals("z", graph.get(8).getId());
        result = BreadthFirstSearch.find(graph.get(8), graph.get(1));
        assertEquals(1, result.size());
        logger.debug(result.get(0));
        
        assertEquals("a", graph.get(9).getId());
        result = BreadthFirstSearch.find(graph.get(9), graph.get(1));
        assertEquals(1, result.size());
        logger.debug(result.get(0));
    }
    
    @Test
    public void testFindWithPath() {
        logger.info("\ntesting findWithPath()");
    }
    
    @Test
    public void testGetPath() {
        logger.info("\ntesting getPath()");
        
        assertEquals(0, BreadthFirstSearch.getPath(null, null, null).size());
        
        List<Node> graph = Utility.generateGraph();
        assertEquals("s", graph.get(1).getId());
        assertEquals("u", graph.get(3).getId());
        
        Node r = graph.get(0);
        Node s = graph.get(1);
        Node t = graph.get(2);
        Node u = graph.get(3);
        Node v = graph.get(4);
        Node w = graph.get(5);
        Node x = graph.get(6);
        Node y = graph.get(7);
        Node z = graph.get(8);
        Node a = graph.get(9);
        
        LinkedList<Node> result = null;
        result = BreadthFirstSearch.getPath(u, s, null);
        assertEquals(0, result.size());
        
        result = BreadthFirstSearch.getPath(u, s, new HashMap<Node, Node>());
        assertEquals(0, result.size());
        
        HashMap<Node, Node> path = new HashMap<Node, Node>();
        path.put(graph.get(9), graph.get(8));
        
        result = BreadthFirstSearch.getPath(u, s, path);
        assertEquals(0, result.size());
        
        HashMap<Node, Node> history = Utility.getSampleTraversal();
        logger.debug(history);
        assertEquals(9, history.size());
        assertEquals(9, history.keySet().size());
        Object[] keys = history.keySet().toArray();
        for(int i = 0; i < keys.length; i++) {
            logger.debug(keys[i] + " " + history.get(keys[i]));
        }
        assertEquals(true, history.containsKey(u));
        assertEquals(true, history.containsKey(w));
        //assertEquals(1, BreadthFirstSearch.getPath(r, r, history).size());
        //assertEquals(2, BreadthFirstSearch.getPath(t, w, history).size());
        
        result = BreadthFirstSearch.getPath(w, s, history);
        assertEquals(2, result.size());
        logger.debug(result);
        
        result = BreadthFirstSearch.getPath(u, s, history);
        assertEquals(4, result.size());
        logger.debug(result);
        
        logger.debug(history.get(w));
        logger.debug(history);
        history.remove(w);
        logger.debug(history.get(w));
        for(int i = 0; i < keys.length; i++) {
            logger.debug(keys[i] + " " + history.get(keys[i]));
        }
        logger.debug(history);
        
        /*HashMap<String, String> names = new HashMap<String, String>();
        names.put("p", "Paul");
        names.put("l", "Lora");
        names.put("o", "Olivia");
        names.put("s1", "Sean");
        names.put("s2", "Shiloh");
        logger.debug(names);
        names.remove("p");
        logger.debug(names);*/
    }
}