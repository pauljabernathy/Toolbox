/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package toolbox.trees;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

import org.apache.log4j.*;
import java.util.List;


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

    @Test
    public void testFind() {
        logger.info("\ntesting find()");
        Tree root = generateTree();
        //logger.debug(root.getChildren().size());
        List<Tree> result = null;
        assertEquals(0, BreadthFirstSearch.find(null, null).size());
        assertEquals(0, BreadthFirstSearch.find(null, root).size());
        result = BreadthFirstSearch.find(null, root);
        for(int i = 0; i < result.size(); i++) {
            logger.debug(result.get(i));
        }
        result = BreadthFirstSearch.find(new Tree("c1c2c2"), root);
        assertEquals(1, result.size());
        for(int i = 0; i < result.size(); i++) {
            logger.debug(result.get(i));
        }
        
        result = BreadthFirstSearch.find(root.getChildren().get(0).getChildren().get(0).getChildren().get(2), root);
        assertEquals(4, result.size());
        for(int i = 0; i < result.size(); i++) {
            logger.debug(result.get(i));
        }
    }
    
    private Tree generateTree() {
        Tree root = new Tree("root");
        Tree c1 = new Tree("c1");
        Tree c2 = new Tree("c2");
        Tree c3 = new Tree("c3");
        root.addChild(c1).addChild(c2).addChild(c3);
        
        Tree c1c1 = new Tree("c1c1");
        Tree c1c2 = new Tree("c1c2");
        c1.addChild(c1c1).addChild(c1c2);
        
        Tree c2c1 = new Tree("c2c1");
        Tree c2c2 = new Tree("c2c2");
        Tree c2c3 = new Tree("c2c3");
        Tree c2c4 = new Tree("c2c4");
        c2.addChild(c2c1).addChild(c2c2).addChild(c2c3).addChild(c2c4);
        
        Tree c3c1 = new Tree("c3c1");
        Tree c3c2 = new Tree("c3c2");
        Tree c3c3 = new Tree("c3c3");
        c3.addChild(c3c1).addChild(c3c2).addChild(c3c3);
        
        Tree c1c1c1 = new Tree("c1c1c1");
        Tree c1c1c2 = new Tree("c1c1c2");
        Tree c1c1c3 = new Tree("c1c1c3");
        c1c1.addChild(c1c1c1).addChild(c1c1c2).addChild(c1c1c3);
        Tree c1c2c1 = new Tree("c1c2c1");
        Tree c1c2c2 = new Tree("c1c2c2");
        Tree c1c2c3 = new Tree("c1c2c3");
        c1c2.addChild(c1c2c1).addChild(c1c2c2).addChild(c1c2c3);
        
        
        Tree c1c3c1 = new Tree("c1c3c1");
        Tree c1c3c2 = new Tree("c1c3c2");
        Tree c1c3c3 = new Tree("c1c3c3");
        
        return root;
    }
}