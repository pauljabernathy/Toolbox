/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package toolbox.stats;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import toolbox.util.ListArrayUtil;
import org.apache.logging.log4j.*;
import java.util.List;
import toolbox.trees.WeightedBinaryTree;

/**
 *
 * @author paul
 */
public class StringHistogramTest {
    
    private static Logger logger;
    
    public StringHistogramTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
        logger = ListArrayUtil.getLogger(StringHistogramTest.class, Level.INFO);
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
    public void testAdd() {
        logger.info("\ntesting add");
        StringHistogram hist = new StringHistogram();
        hist.insert("histogram", 6);
        hist.insert("add");
        hist.insert("instant", 3);
        hist.insert("hogwash");
        hist.insert("hog wild");
        hist.insert("hogggin");
        hist.display();
        
        assertEquals(6, hist.getAsList().size());
    }

    @Test
    public void testQuery() {
        logger.info("\ntesting add");
        StringHistogram hist = new StringHistogram();
        hist.insert("histogram", 6);
        hist.insert("add");
        hist.insert("instant", 3);
        hist.insert("hogwash");
        hist.insert("hog wild");
        hist.insert("hogggin");
        hist.display();
        
        List<HistogramEntry<String>> hogs = hist.query("hog", StringHistogram.MatchType.STARTS_WITH);
        logger.info(hogs);
        assertEquals(3, hogs.size());
        logger.info(hogs);
        
        String filename = "../Capstone/histTest2.txt";
        try {
            StringHistogram histogram = readNGramsFromFile(filename);
            histogram.display();
            logger.info("");
            List<HistogramEntry<String>> results = histogram.query("lumber town ", StringHistogram.MatchType.STARTS_WITH);
            logger.info(results);
        } catch(IOException e) {
            fail("could not read file " + filename + ":  " + e.getClass() + " - " + e.getMessage());
        }
    }
    
    @Test
    public void testQueryFromLargeFile() {
        String filename = "../Capstone/blogsSample2_4grams.txt";
        try {
            StringHistogram histogram = readNGramsFromFile(filename);
            List<HistogramEntry<String>> results = histogram.query("a case of ", StringHistogram.MatchType.STARTS_WITH);
            logger.info(results);
        } catch(IOException e) {
            fail("could not read file " + filename + ":  " + e.getClass() + " - " + e.getMessage());
        }
    }
    
    public static StringHistogram readNGramsFromFile(String filename) throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader(filename));
        StringHistogram histogram = new StringHistogram();
        String line = null;
        while(reader.ready()) {
            line = reader.readLine();
            histogram.insert(line, 1);
        }
        return histogram;
    }

    @Test
    public void testFindFirst() {
        logger.info("\ntesting query()");
        StringHistogram hist = new StringHistogram();
        hist.insert("histogram", 6);
        hist.insert("add");
        hist.insert("instant", 3);
        hist.insert("hogwash");
        hist.insert("hog wild");
        hist.insert("hogggin");
        
        hist.findFirst("hog").display();
    }
    
}
