/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package toolbox.information;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

import org.apache.logging.log4j.*;
import toolbox.util.ListArrayUtil;
import toolbox.stats.Histogram;
import java.util.List;

/**
 *
 * @author paul
 */
public class CompressionTest {
    
    private static Logger logger;
    
    public CompressionTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
        logger = ListArrayUtil.getLogger(CompressionTest.class, Level.INFO);
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
    public void testCompress() {
        logger.info("\ntesting compress");
    }
    
    @Test
    public void testCreateHistogramOfChars() {
        logger.info("\ntesting creating Histogram of Characters");
        Histogram histogram = new Histogram(Compression.convertToChar("aaaabbbccd"));
        List<Character> values = histogram.getValues();
        List<Double> probs = histogram.getProbabilities();
        double epsilon = 0.0001;
        assertEquals(4, values.size());
        assertEquals(true, values.contains('a'));
        assertEquals(true, values.contains('b'));
        assertEquals(true, values.contains('c'));
        assertEquals(true, values.contains('d'));
        assertEquals(false, values.contains('e'));
        assertEquals(.4, histogram.getProbDist().probatilityOf('a'), epsilon);
        assertEquals(.3, histogram.getProbDist().probatilityOf('b'), epsilon);
        assertEquals(.2, histogram.getProbDist().probatilityOf('c'), epsilon);
        assertEquals(.1, histogram.getProbDist().probatilityOf('d'), epsilon);
        
        histogram = new Histogram(Compression.convertToChar(null));
        assertEquals(0, histogram.size());
        assertEquals(0, histogram.getValues().size());
        assertEquals(0, histogram.getProbabilities().size());
    }
    
    @Test
    public void testConvertToChar() {
        logger.info("\ntesting convertToChar()");
        assertEquals(0, Compression.convertToChar(null).length);
        assertEquals(0, Compression.convertToChar("").length);
        assertEquals(1, Compression.convertToChar("a").length);
        assertEquals(Character.valueOf('a'), Compression.convertToChar("a")[0]);
        
        Character[] result = null;
        result = Compression.convertToChar("some text");
        assertEquals(9, result.length);
        assertEquals(Character.valueOf('s'), result[0]);
        assertEquals(Character.valueOf('o'), result[1]);
        assertEquals(Character.valueOf('m'), result[2]);
        assertEquals(Character.valueOf('e'), result[3]);
        assertEquals(Character.valueOf(' '), result[4]);
        assertEquals(Character.valueOf('t'), result[5]);
        assertEquals(Character.valueOf('e'), result[6]);
        assertEquals(Character.valueOf('x'), result[7]);
        assertEquals(Character.valueOf('t'), result[8]);
    }
    
    @Test
    public void testGetASCIIBinary() {
        logger.info("\ntesting getASCIIBinary");
        assertEquals(0, Compression.getASCIIBinary(null).length);
        assertEquals(0, Compression.getASCIIBinary("").length);
        //{ 0, 1, 1, 0, 0, 0, 0, 1 }
        int[] result = Compression.getASCIIBinary("a");
        logger.info(ListArrayUtil.arrayToString(result));
        logger.info(ListArrayUtil.arrayToString(ListArrayUtil.toBinaryArray(97, 8)));
        assertEquals(0, result[0]);
        assertEquals(1, result[1]);
        assertEquals(1, result[2]);
        assertEquals(0, result[3]);
        assertEquals(0, result[4]);
        assertEquals(0, result[5]);
        assertEquals(0, result[6]);
        assertEquals(1, result[7]);
    }
}
