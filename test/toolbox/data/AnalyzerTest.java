/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package toolbox.data;

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
public class AnalyzerTest {
    
    private static Logger logger;
    
    public AnalyzerTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
        logger = ListArrayUtil.getLogger(AnalyzerTest.class, Level.DEBUG);
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
    public void testMain() {
    }

    @Test
    public void testAddWriterAppender() {
    }

    @Test
    public void testAnalyzeFile_String_String() {
    }

    @Test
    public void testAnalyzeFile_3args() {
    }

    @Test
    public void testGetEndLine() {
    }

    @Test
    public void testSetEndLine() {
    }
    
    @Test
    public void testHasChanged() {
        logger.error("\ntesting hasChanged()");
        Analyzer instance = new Analyzer();
        logger.debug(instance.hasChanged());
        instance.notifyObservers();
        logger.debug(instance.hasChanged());
    }
}