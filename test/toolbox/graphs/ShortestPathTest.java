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
import java.util.List;
import java.util.HashMap;
import java.util.LinkedList;

/**
 *
 * @author paul
 */
public class ShortestPathTest {
    
    private static Logger logger;
    
    public ShortestPathTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
        logger = ListArrayUtil.getLogger(ShortestPathTest.class, Level.DEBUG);
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
    public void testGetOneToAllPaths() {
        logger.info("\ntesting getOneToAllPaths()");
        List<Node> graph = Utility.getWeightedDirectedGraph1();
        assertEquals("s", graph.get(0).getId());
        assertEquals("v", graph.get(2).getId());
        
        Node s = graph.get(0);
        List<List<Node>> paths = ShortestPath.getOneToAllPaths(s);
        assertEquals(5, paths.size());
    }
    
    //@Test
    public void testGetOneToAllPathMap() {
        logger.info("getOneToAllPathsMap");
        
        List<Node> graph = Utility.getWeightedDirectedGraph1();
        assertEquals("s", graph.get(0).getId());
        assertEquals("v", graph.get(2).getId());
        
        Node s = graph.get(0);
        HashMap<Node, Node> parents = null;
        parents = ShortestPath.getOneToAllPathsMap(s);
        assertEquals(5, parents.size());
    }
    
    @Test
    public void testgetDijkstraPaths() {
        logger.info("testing getDijstraPaths()");
        List<Node> graph = Utility.getWeightedDirectedGraph1();
        assertEquals("s", graph.get(0).getId());
        assertEquals("v", graph.get(2).getId());
        
        Node s = graph.get(0);
        assertEquals("s", s.getId());
        //assertEquals(2, s.getEdges().size());
        List<Edge> edges = s.getEdges();
        for(Edge edge : edges) {
            logger.debug(edge);
        }
        HashMap<Node, Node> parentsMap = null;
        List<LinkedList<Edge>> paths = ShortestPath.getDijkstraPaths(s);
        for(LinkedList<Edge> currentPath : paths) {
            System.out.println("-");
            for(Edge edge : currentPath) {
                System.out.print(edge + ",   ");
            }
            System.out.println();
        }
    }
    
    @Test
    public void testgetDijkstraMap() {
        logger.info("testing getDijstraMap()");
        List<Node> graph = Utility.getWeightedDirectedGraph1();
        assertEquals("s", graph.get(0).getId());
        assertEquals("v", graph.get(2).getId());
        
        Node s = graph.get(0);
        assertEquals("s", s.getId());
        //assertEquals(2, s.getEdges().size());
        List<Edge> edges = s.getEdges();
        for(Edge edge : edges) {
            logger.debug(edge);
        }
        HashMap<Node, Node> parentsMap = null;
        parentsMap = ShortestPath.getDijkstraMap(s);
        java.util.Set<Node> destinations = parentsMap.keySet();
        for(Node destination : destinations) {
            logger.debug(parentsMap.get(destination) + " --> " + destination);
        }
    }
}