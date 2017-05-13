/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package toolbox.stats;

import java.util.TreeMap;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

import org.apache.logging.log4j.*;
import toolbox.util.ListArrayUtil;
import java.util.PriorityQueue;
import toolbox.trees.WeightedBinaryTree;
import java.util.List;
import java.util.ArrayList;
import java.io.*;
import toolbox.util.MathUtil;
import java.util.Date;
import java.util.Calendar;
import java.util.function.Predicate;

/**
 *
 * @author pabernathy
 */
public class TreeHistogramTest {
    
    private static Logger logger;
    
    public TreeHistogramTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
        logger = ListArrayUtil.getLogger(TreeHistogramTest.class, Level.INFO);
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

    /**
     * Test of addToData method, of class TreeHistogram.
     */
    @Test
    public void testAddToDataAndGetCorrectCounts() {
        logger.info("\ntesting addToData()");
        TreeHistogram<String> instance = new TreeHistogram<>();
        TreeMap expResult = null;
        WeightedBinaryTree<String> result = instance.addToData("a", 1);
        assertEquals("a", result.getKey());
        assertEquals(1, result.weight, 0.0);
        assertEquals(1, instance.getNumEntries());
        assertEquals(1, instance.getTotalCount());
        assertEquals(null, result.left);
        assertEquals(null, result.right);
        result = instance.addToData("b", 1);
        assertEquals("a", result.getKey());
        assertEquals("b", result.right.getKey());
        assertEquals(1, result.right.weight, 0.0);
        assertEquals(2, instance.getNumEntries());
        assertEquals(2, instance.getTotalCount());
        result = instance.addToData("a", 27);
        assertEquals("a", result.getKey());
        assertEquals(28, result.weight, 0.0);
        assertEquals(2, instance.getNumEntries());
        assertEquals(29, instance.getTotalCount());
        assertEquals(null, result.left);
        assertEquals("b", result.right.getKey());
        logger.info(result);
    }

    @Test
    public void testGetAsListByNaturalOrder() {
        logger.info("\ntesting getAsList() by natural order");
        TreeHistogram<String> hist = this.createSimpleWordHistogram();
        List<HistogramEntry<String>> list = hist.getAsList(TreeHistogram.Sort.ITEM);
        logger.info(list);
    }
    
    @Test
    public void testGetAsListByCount() {
        logger.info("\ntesting getAsList() by count");
        TreeHistogram<String> hist = this.createSimpleWordHistogram();
        List<HistogramEntry<String>> list = hist.getAsList(TreeHistogram.Sort.COUNT);
        logger.info(list);
    }
    
    private TreeHistogram<String> createSimpleWordHistogram() {
        TreeHistogram<String> hist = new TreeHistogram<>();
        hist.addToData("the", 1);
        hist.addToData("holiday", 5);
        hist.addToData("and", 8);
        hist.addToData("the", 10);
        hist.addToData("relevance", 1);
        hist.addToData("injure", 2);
        hist.addToData("Philippino", 1);
        hist.addToData("Amaranthus", 1);
        hist.addToData("hardwood", 1);
        hist.addToData("philharmonic", 1);
        hist.addToData("the", 1);
        hist.getData().display();
        return hist;
    }
    
    @Test
    public void testCountWordsOfModerateSizedFile() {
        logger.info("\ntesting counting words of moderage sized file");
        TreeHistogram<String> th = new TreeHistogram();
        Histogram h = new Histogram();
        
        String filename = "beowulf i to xxii.txt";
        try(BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            String line = null;
            String[] lineWords = null;
            List<String> wordList = new ArrayList<>();
            while(reader.ready()) {
                line = reader.readLine();
                lineWords = line.split(" ");
                for(String word : lineWords) {
                    word = word.toLowerCase().trim();
                    word = word.toLowerCase().replaceAll("\\.", "").replaceAll(":", "").replaceAll("\\-", "").replaceAll("\n", " ").replaceAll(",", "").replaceAll("\"", "");
                    if("".equals(word)) {
                        continue;
                    }
                    th.addToData(word, 1);
                    wordList.add(word);
                }
            }
            h.setDataList(wordList);
            logger.info(wordList.size());
            logger.info(h.size());
            int sum = 0;
            for(Integer i : h.getCounts()) {
                sum += i;
            }
            logger.info(sum);
            List<HistogramEntry<String>> thList = th.getAsList(TreeHistogram.Sort.ITEM);
            logger.info(thList.size());
            logger.info(thList);
            thList = th.getAsList(TreeHistogram.Sort.COUNT);
            logger.info(thList.size());
            logger.info(thList);
            logger.info(th.getAsList(TreeHistogram.Sort.UNSORTED));
            logger.info(h.toString());
        } catch(Exception e) {
            logger.error(e.getClass() + " trying to read the file:  " + e.getMessage());
        }
    }
    
    @Test
    public void testCountWordsOfLargeFile() {
        logger.info("\ntestCountWordsOfLargeFile()");
        TreeHistogram<String> th = new TreeHistogram();
        Histogram h = new Histogram();
        
        String filename = "beowulf i to xxii.txt";
        filename = "les_miserables.txt";
        compareHistograms(filename);
    }
    
    @Test
    public void testVariousSizeFiles() {
        logger.info("\ntestVariousSizeFiles");
        //compareHistograms("jabberwocky.txt");
        //compareHistograms("beowulf i to xxii.txt");
        //compareHistograms("through_the_looking_glass.txt");
        //compareHistograms("beowulf.txt");
        compareHistograms("les_miserables.txt");
    }
    
    private void compareHistograms(String filename) {
        logger.info("\ncomparing the Histograms for " + filename);
        TreeHistogram<String> th = new TreeHistogram();
        Histogram h = new Histogram();
        try(BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            //First, gather all words into a list.
            String line = null;
            String[] lineWords = null;
            List<String> wordList = new ArrayList<>();
            while(reader.ready()) {
                line = reader.readLine();
                lineWords = line.split(" ");
                for(String word : lineWords) {
                    word = word.toLowerCase().trim();
                    word = word.toLowerCase().replaceAll("\\.", "").replaceAll(":", "").replaceAll("\\-", "").replaceAll("\n", " ").replaceAll(",", "").replaceAll("\"", "");
                    if("".equals(word)) {
                        continue;
                    }
                    wordList.add(word);
                }
            }
            
            //TreeHistogram
            Calendar calendar =  Calendar.getInstance();
            long treeStart = Calendar.getInstance().getTimeInMillis();
            for(String word : wordList) {
                th.insert(word, 1);
            }
            List<HistogramEntry<String>> thList = th.getAsList(TreeHistogram.Sort.COUNT);
            long treeStop = Calendar.getInstance().getTimeInMillis();
            
            //regular Histogram
            long regularStart = Calendar.getInstance().getTimeInMillis();
            h.setDataList(wordList);
            long regularStop = Calendar.getInstance().getTimeInMillis();
            
            //logger.info("wordList length = " + wordList.size());
            //logger.info("regular histogram size = " + h.size());
            int sum = 0;
            for(Integer i : h.getCounts()) {
                sum += i;
            }
            //logger.info("regular histogram sum = " + sum);
            
            //logger.info("tree histogram weight = " + th.getData().getTreeWeight());
            //logger.info("thList length = " + thList.size());
            //logger.info("treeStart = " + treeStart + "  treeStop = " + treeStop + "   difference = " + (treeStop - treeStart));
            //logger.info("regularStart = " + regularStart + "  regularStop = " + regularStop + "   difference = " + (regularStop - regularStart));
            long treeTime = treeStop - treeStart;
            long regularTime = regularStop - regularStart;
            logger.info("total words = " + wordList.size() + ";  unique words = " + thList.size());
            logger.info("regular time = " + regularTime + " millis   " + (double)wordList.size() / ((double)regularTime / 1000.0) + "words per second");
            logger.info("tree time = " + treeTime + " millis    " + ((double)wordList.size()) / ((double)treeTime / 1000.0) + " words per second");
            logger.info("difference = " + (regularTime - treeTime) + " millis");
            assertEquals(wordList.size(), th.getTotalCount());
            assertEquals(wordList.size(), th.getData().getTreeWeight(), 0.0);
            assertEquals(wordList.size(), sum);
            assertEquals(h.size(), thList.size());
            int size = h.getValues().size();
            String currentWord = null;
            WeightedBinaryTree<String> currentNode = null;
            for(int i = 0; i < size; i++) {
                currentWord = (String)h.getValues().get(i);
                currentNode = th.getData().get(currentWord);
                if(currentNode == null) {
                    fail(currentWord + " was in regular histogram but not in the tree histogram.");
                }
                if(h.getCounts().get(i) != currentNode.getWeight()) {
                    fail("count for " + currentWord + " is " + h.getCounts().get(i) + " in the regular histogram but " + currentNode.getWeight() + " in the tree histogram");
                }
            }
        } catch(Exception e) {
            fail(e.getClass() + " trying to read the file:  " + e.getMessage());
        }
    }
    
    @Test
    public void testQueryFromFirst() {
        logger.info("\ntesting queryFromFirst()");
        TreeHistogram<String> h = this.createSimpleWordHistogram();
        h.getData().display();
        Predicate<String> p = (s) -> s.startsWith("h");
        List<String> result = h.queryFromFirst(p);
        logger.debug(result);
        assertEquals(2, result.size());
        
        p = (s) -> s.startsWith("h") && !s.contains("wood");
        result = h.queryFromFirst(p);
        logger.debug(result);
        assertEquals(1, result.size());
    }
    
    @Test
    public void testQueryAll() {
        logger.info("\ntesting queryAll()");
        TreeHistogram<String> h = this.createSimpleWordHistogram();
        Predicate<String> p = s -> s.contains("wood");
        List<HistogramEntry<String>> result = h.queryAll(p);
        assertEquals(1, result.size());
        
        p = s -> s.contains("il");
        result = h.queryAll(p);
        assertEquals(2, result.size());
        result.stream().forEach( entry -> {
            if(!"Philippino".equals(entry.item) && !"philharmonic".equals(entry.item)) { 
                fail(entry.item + " should not be here");
            }
        });
        logger.debug(result);
    }
}
