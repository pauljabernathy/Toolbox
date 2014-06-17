/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package toolbox.random;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

import org.apache.log4j.*;

import toolbox.util.ListArrayUtil;
import java.io.*;

/**
 *
 * @author paul
 */
public class RandomServletTest {
    
    private static Logger logger;
    
    public RandomServletTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
        logger = ListArrayUtil.getLogger(RandomServletTest.class, Level.DEBUG);
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
    public void testToXML() {
        logger.info("\ntesting toXML()");
        double[] input = null;
        String result = null;
        RandomServlet instance = new RandomServlet();
        assertEquals(59, instance.toXML(input).length());
        logger.debug(instance.toXML(input));
        input = new double[] { 1, 27, 81.923, 3.14196 };
        result = instance.toXML(input);
        assertEquals(159, result.length());
        logger.debug(instance.toXML(input));
        try {
            PrintWriter writer = new PrintWriter(new FileWriter("result.xml"));
            writer.printf(result);
            writer.flush();
            writer.close();
        } catch(IOException e) {
            logger.error(e.getClass() + " in testToXML():  " + e.getMessage());
        }
    }
}