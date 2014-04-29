/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package toolbox.trees;

import java.util.List;
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
public class TreeTest {
    
    private static Logger logger;
    
    public TreeTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
        logger = Logger.getLogger(TreeTest.class);
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
    public void testGetId() {
        logger.info("\ntesting getId()");
        String name = "larry";
        Tree instance = new Tree(name);
        assertEquals(name, instance.getId());
    }

    @Test
    public void testSetId() {
        logger.info("\ntesting setId()");
        String name = "larry";
        Tree instance = new Tree(name);
        assertEquals(name, instance.getId());
        String newName = "bob";
        instance.setId(newName);
        assertEquals(newName, instance.getId());
    }

    @Test
    public void testAddChildAndGetChildren() {
        logger.info("\ntesting addChild() and getChildren()");
        Tree instance = new Tree("instance");
        if(instance.getChildren() == null) {
            fail("children was null");
        }
        assertEquals(0, instance.getChildren().size());
        Tree child1 = new Tree("child1");
        Tree child2 = new Tree("child2");
        instance.addChild(child1);
        if(instance.getChildren() == null) {
            fail("children was null");
        }
        assertEquals(1, instance.getChildren().size());
        instance.addChild(child2);
        assertEquals(2, instance.getChildren().size());
        assertEquals("child2", instance.getChildren().get(1).getId());
    }
    
    @Test
    public void testGetPathFromRoot() {
        logger.info("\ntesting getPathFromRoot()");
        Tree root = Utility.generateTree();
        List<Tree> result = null;
        Tree c1c1c3 = root.getChildren().get(0).getChildren().get(0).getChildren().get(2);
        result = c1c1c3.getPathFromRoot();
        for(int i = 0; i < result.size(); i++) {
            logger.debug(result.get(i));
        }
        assertEquals(4, result.size());
    }
    
    @Test
    public void testEquals() {
        logger.info("\ntesting equals()");
        Tree root = new Tree("root");
        Tree c1 = new Tree("c1");
        Tree c2 = new Tree("c2");
        Tree c3 = new Tree("c3");
        assertEquals(false, c1.equals(c3));
        assertEquals(true, c1.equals(c1));
        assertEquals(true, c1.equals(new Tree("c1")));  //This is where things are pretty, uh, questionable.  Really, we probably need a mechanism for making sure there are no duplicate node names in the tree.
    }
}