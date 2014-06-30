/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package toolbox.io;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

import org.apache.log4j.*;
import java.io.*;
import java.util.List;
import java.util.ArrayList;

/**
 *
 * @author paul
 */
public class CSVWriterTest {
    
    private static Logger logger;
    
    public CSVWriterTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
        logger = toolbox.util.ListArrayUtil.getLogger(CSVWriterTest.class, Level.DEBUG);
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
    public void testWriteArray() throws Exception {
        logger.info("fill in unit test for writeArray()");
        //TODO:  fill in
    }

    //@Test
    public void testWriteRowsToFile() throws Exception {
        logger.info("\ntesting writeRowsToFile()");
        assertEquals(false, doesFileExist(null));
        assertEquals(false, doesFileExist(""));
        assertEquals(false, doesFileExist("doesnotexist"));
        assertEquals(true, doesFileExist("metro.csv"));
        
        String filename = null;
        try {
            logger.debug(deleteFileIfExists(filename));
            CSVWriter.writeRowsToFile(null, filename, null, null);
            assertEquals(false, doesFileExist(filename));
            
            filename = "output.csv";
            logger.debug(deleteFileIfExists(filename));
            CSVWriter.writeRowsToFile(null, filename, null, null);
            assertEquals(false, doesFileExist(filename));
            
            CSVWriter.writeRowsToFile(null, filename, null, ",");
            assertEquals(false, doesFileExist(filename));
            
            List<List> data = new ArrayList<List>();
            List<String> region = new ArrayList<String>();
            region.add("a");
            region.add("b");
            List<String> state = new ArrayList<String>();
            state.add("WA");
            state.add("CA");
            List<String> metro = new ArrayList<String>();
            metro.add("LA");
            metro.add("NY");
            
            CSVWriter.writeRowsToFile(data, filename, null, null);
            assertEquals(false, doesFileExist(filename));
            
            CSVWriter.writeRowsToFile(data, filename, null, ",");
            assertEquals(true, doesFileExist(filename));
            
            //TODO:  more testing; empty data set, mismatched List lengths, missing values
            
        } catch(IOException e) {
            
        }
    }
    
    @Test
    public void testWriteColumnsToFile() throws Exception {
        logger.info("\ntesting writeColumnsToFile()");
        assertEquals(false, doesFileExist(null));
        assertEquals(false, doesFileExist(""));
        assertEquals(false, doesFileExist("doesnotexist"));
        assertEquals(true, doesFileExist("metro.csv"));
        
        String filename = null;
        try {
            logger.debug(deleteFileIfExists(filename));
            CSVWriter.writeColumnsToFile(null, filename, null, null);
            assertEquals(false, doesFileExist(filename));
            
            filename = "output.csv";
            logger.debug(deleteFileIfExists(filename));
            CSVWriter.writeColumnsToFile(null, filename, null, null);
            assertEquals(false, doesFileExist(filename));
            
            CSVWriter.writeColumnsToFile(null, filename, null, ",");
            assertEquals(false, doesFileExist(filename));
            
            List<List> data = new ArrayList<List>();
            List<String> region = new ArrayList<String>();
            region.add("a");
            region.add("b");
            List<String> state = new ArrayList<String>();
            state.add("WA");
            state.add("CA");
            List<String> metro = new ArrayList<String>();
            metro.add("LA");
            metro.add("NY");
            
            data.add(region);
            data.add(state);
            data.add(metro);
            
            CSVWriter.writeColumnsToFile(data, filename, null, null);
            assertEquals(false, doesFileExist(filename));
            
            CSVWriter.writeColumnsToFile(data, filename, "region, state, metro", ",");
            assertEquals(true, doesFileExist(filename));
            
            //TODO:  more testing; empty data set, mismatched List lengths, missing values
            
        } catch(IOException e) {
            
        }
    }
    
    private boolean doesFileExist(String filename) {
        if(filename == null) {
            return false;
        }
        File file = new File(filename);
        return file.exists();
    }
    
    private boolean deleteFileIfExists(String filename) {
        if(doesFileExist(filename)) {
            logger.debug("file " + filename + " exists, so delete");
            return new File(filename).delete();
        }
        logger.debug("file " + filename + " does not exist");
        return false;
    }
}