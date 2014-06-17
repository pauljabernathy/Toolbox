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

import org.apache.log4j.*;
import toolbox.util.ListArrayUtil;
import java.util.regex.*;

/**
 *
 * @author paul
 */
public class AnalyzerServletTest {
    
    private static Logger logger;
    
    public AnalyzerServletTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
        logger = ListArrayUtil.getLogger(AnalyzerServletTest.class, Level.DEBUG);
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
    public void testDoGet() throws Exception {
    }

    @Test
    public void testGetColumns() {
        logger.info("\ntesting getColumns()");
        AnalyzerServlet instance = new AnalyzerServlet();
        int[] result = null;
        try {
            instance.getColumns(null);
            fail("Should have thrown the exception but did not");
        } catch(Exception e) {
            assertEquals(AnalyzerServlet.ENTER_COLUMNS_MESSAGE, e.getMessage());
        }
        
        try {
            instance.getColumns("");
            fail("Should have thrown the exception but did not");
        } catch(Exception e) {
            assertEquals(AnalyzerServlet.ENTER_COLUMNS_MESSAGE, e.getMessage());
        }
        
        try {
            instance.getColumns(",");
            fail("Should have thrown the exception but did not");
        } catch(Exception e) {
            assertEquals(AnalyzerServlet.INVALID_COLUMNS_FORMAT_MESSAGE, e.getMessage());
        }
        Pattern p = Pattern.compile("[^\\d^,]");
        Matcher m = p.matcher("12fd 8,");
        m = p.matcher("1,2");
        while(m.find()) {
            logger.debug(m.start());
        }
        logger.debug("---");
        try {
            instance.getColumns("1 2");
            fail("Should have thrown the exception but did not");
        } catch(Exception e) {
            assertEquals(AnalyzerServlet.INVALID_COLUMNS_FORMAT_MESSAGE, e.getMessage());
        }
        
        String s = "2";
        String[] ss = s.split(",");
        //logger.debug("ss.length = " + ss.length);
        for(int i = 0; i < ss.length; i++) {
            //logger.debug(ss[i]);
        }
        try {
            result = instance.getColumns(",2");
            //A comma at the beginning should work.
            //fail("Should have thrown an exception for \",2\" but did not");
            assertEquals(1, result.length);
            logger.debug(",2 -> " + ListArrayUtil.arrayToString(result));
        } catch(Exception e) {
            logger.error(e.getClass() + " " + e.getMessage());
            assertEquals(AnalyzerServlet.INVALID_COLUMNS_FORMAT_MESSAGE, e.getMessage());
        }
        
        try {
            result = instance.getColumns("1,");
            //It is OK if there is a trailing comma.
            assertEquals(1, result.length);
            logger.debug("1, -> " + ListArrayUtil.arrayToString(result));
        } catch(Exception e) {
            logger.error(e.getClass() + " " + e.getMessage());
        }
        
        try {
            result = instance.getColumns("1");
            //It is OK if there is a trailing comma.
            assertEquals(1, result.length);
            logger.debug("1 -> " + ListArrayUtil.arrayToString(result));
        } catch(Exception e) {
            logger.error(e.getClass() + " " + e.getMessage());
        }
        
        try {
            result = instance.getColumns("1,2");
            assertEquals(2, result.length);
            logger.debug("1,2 -> " + ListArrayUtil.arrayToString(result));
        } catch(Exception e) {
            fail("input was valid (1,2) but threw exception with message: " + e.getMessage());
        }
    }

    @Test
    public void testDoPost() throws Exception {
    }

    @Test
    public void testUpdate() {
    }
}