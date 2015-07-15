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

import java.io.IOException;
import java.util.List;
import java.util.ArrayList;
import toolbox.Constants;
import toolbox.util.ListArrayUtil;

import org.apache.logging.log4j.*;

//import toolbox.stats.DataList;
import toolbox.stats.*;
import toolbox.information.Shannon;

/**
 *
 * @author paul
 */
public class CSVReaderTest {
    
    private static Logger logger;
    private static final int SURVIVED_COLUMN = 1;
    public static final String LINE_ONE = "1,0,3,\"Braund, Mr. Owen Harris\",male,22,1,0,A/5 21171,7.25,,S,FALSE";
    
    public CSVReaderTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
        logger = ListArrayUtil.getLogger(CSVReaderTest.class, Level.DEBUG);
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
    public void testGetRowsAsLists_noColumns() {
        logger.info("\ntesting getRowsAsLists()");
        String filename = "titanic.csv";
        //filename = "titanic.csv";
        List<List> data = (List<List>)CSVReader.getRowsAsLists(filename, 10000);
        logger.debug(data.size());
        int count = 0;
        StringBuilder sb = new StringBuilder();
        for(List features : data) {
            sb = new StringBuilder();
            sb.append(count++).append(" ");
            int i = 0;
            for(Object feature : features) {
                sb.append(i++).append(" ").append(feature).append(" ");
            }
            //logger.debug(sb);
        }
    }
    
    @Test
    public void testGetRowsAsLists_withColumns() {
        logger.info("\ntesting getRowsAsLists() with columns");
        String filename = "titanic.csv";
        //filename = "titanic.csv";
        List<List<String>> result = null;
        
        try {
            result = (List<List<String>>)CSVReader.getRowsAsLists(null, new int[] { 0, 1, 2, 5, 7, 8, 12, 13 }, 10000);
            assertEquals(0, result.size());
        } catch(IOException e) {
            fail("should not have thrown IOException for null filename");
        }
        
        try {
            result = (List<List<String>>)CSVReader.getRowsAsLists("", new int[] { 0, 1, 2, 5, 7, 8, 12, 13 }, 10000);
            assertEquals(0, result.size());
        } catch(IOException e) {
            fail("should not have thrown IOException for empty filename");
        }
        
        //TODO:  should this throw an IOException?
        try {
            result = (List<List<String>>)CSVReader.getRowsAsLists("filethatdoesnotexist", new int[] { 0, 1, 2, 5, 7, 8, 12, 13 }, 10000);
            fail("failed to throw an IOException for non existent file");
        } catch(IOException e) {
            logger.debug("correctly threw IOException for non existent file");
        }
        
        int numLinesToRead = -1;
        try {
            result = (List<List<String>>)CSVReader.getRowsAsLists(filename, new int[] { 0, 1, 2, 5, 7, 8, 12, 13 }, numLinesToRead);
            assertEquals(0, result.size());
        } catch(IOException e) {
            logger.error(e.getClass() + " for  getRowsAsLists(filename, new int[] { 0, 1, 2, 5, 7, 8, 12, 13 }, " + numLinesToRead + "):  " + e.getMessage());
        }
        
        numLinesToRead = 0;
        try {
            result = (List<List<String>>)CSVReader.getRowsAsLists(filename, new int[] { 0, 1, 2, 5, 7, 8, 12, 13 }, numLinesToRead);
            assertEquals(numLinesToRead, result.size());
        } catch(IOException e) {
            logger.error(e.getClass() + " for  getRowsAsLists(filename, new int[] { 0, 1, 2, 5, 7, 8, 12, 13 }, " + numLinesToRead + "):  " + e.getMessage());
        }
        
        numLinesToRead = 25;
        try {
            result = (List<List<String>>)CSVReader.getRowsAsLists(filename, new int[] { 1, 2, 5, 6, 7, 8, 10, 12, 13 }, 25);
            assertEquals(numLinesToRead, result.size());
            assertEquals(9, result.get(0).size());
            StringBuilder sb = new StringBuilder();
            int count = 0;
            for(List features : result) {
                sb = new StringBuilder();
                sb.append(count++).append(" ");
                int i = 0;
                for(Object feature : features) {
                    sb./*append(i++).*/append(" ").append(feature).append(" ");
                }
                logger.debug(sb);
            }
        } catch(IOException e) {
            logger.error(e.getClass() + " for  getRowsAsLists(filename, new int[] { 0, 1, 2, 5, 7, 8, 12, 13 }, " + numLinesToRead + "):  " + e.getMessage());
        }
        
        numLinesToRead = 10000;
        try {
            result = (List<List<String>>)CSVReader.getRowsAsLists(filename, new int[] { 0, 1, 2, 5, 7, 8, 12, 13 }, numLinesToRead);
            assertEquals(891, result.size());
            assertEquals(8, result.get(0).size());
            int count = 0;
            StringBuilder sb = new StringBuilder();
            for(List features : result) {
                sb = new StringBuilder();
                sb.append(count++).append(" ");
                int i = 0;
                for(Object feature : features) {
                    sb.append(i++).append(" ").append(feature).append(" ");
                }
                //logger.debug(sb);
            }
        } catch(IOException e) {
            logger.error(e.getClass() + " for  getRowsAsLists(filename, new int[] { 0, 1, 2, 5, 7, 8, 12, 13 }, " + numLinesToRead + "):  " + e.getMessage());
        }
        
        numLinesToRead = 10000;
        try {
            result = (List<List<String>>)CSVReader.getRowsAsLists(filename, new int[] { 0, 1, 2, 5, 7, 8, 12, 13, 45 }, numLinesToRead);
            assertEquals(891, result.size());
            assertEquals(8, result.get(0).size());
        } catch(IOException e) {
            logger.error(e.getClass() + " for  getRowsAsLists(filename, new int[] { 0, 1, 2, 5, 7, 8, 12, 13 }, " + numLinesToRead + "):  " + e.getMessage());
        }
    }

    /*
    @Test
    public void testGetDistributions() {
        logger.info("\ntesting getDistributions()");
        int[] featureColumns = { 2, 5, 13 };//{ 2, 5, 13 };
        try {
            ProbDist<Classification> result = CSVReader.getDistributions("titanic.csv", SURVIVED_COLUMN, featureColumns, Constants.DEFAULT_SEPARATOR);
            //result.display();
            List<Classification> classifications = result.getValues();
            List<Double> probs = result.getProbabilities();
            for(Classification c : classifications) {
                logger.debug(c.getName());
                List<ProbDist> featureDists = c.getFeatureCPDs();
                for(ProbDist dist : featureDists) {
                    //dist.display();
                }
                logger.debug("");
            }
        } catch(Exception e) {
            logger.error(e.getClass() + " in testGetDistributions():  " + e.getMessage());
        }
    }
    */
    
    @Test
    public void testGetFeatureDists() {
        logger.info("\ntesting getFeatureDists()");
        int[] featureColumns = { 2, 5, 13 };
        try {
            List<ProbDist> dists = CSVReader.getFeatureDists("titanic.csv", SURVIVED_COLUMN, "0", featureColumns, Constants.DEFAULT_SEPARATOR);
            //Utilities.showList(dists);
            assertEquals(.1457, (Double)dists.get(0).getProbabilities().get(1), .0001);
            assertEquals(.1457, dists.get(0).probatilityOf("1"), .0001);
            assertEquals(.8525, dists.get(1).probatilityOf("male"), .001);
            
            dists = CSVReader.getFeatureDists("titanic.csv", SURVIVED_COLUMN, "1", featureColumns, Constants.DEFAULT_SEPARATOR);
            //Utilities.showList(dists);
        } catch(IOException e) {
            fail(e.getClass() + " " + e.getMessage());
        }
    }
    
    @Test
    public void testParseFile() {
        logger.info("\ntesting parseFile()");
        int[] featureColumns = { 3, 6, 13 };
        //TODO:  fill this function out
        try {
            List<List<String>> data = (List<List<String>>)CSVReader.parseFile("titanic.csv", SURVIVED_COLUMN, "0", featureColumns, Constants.DEFAULT_SEPARATOR);
            for(List<String> curList : data) {
                //logger.debug(curList.size());
                //Utilities.showList(curList);
            }
        } catch(Exception e) {
            fail(e.getClass() + " " + e.getMessage());
        }
    }
    
    @Test
    public void testParseLineInt() {
        logger.info("\ntesting parseLine(String line, int column, String columnSeparator)");
        //String result = "result";
        assertEquals(Constants.UNKNOWN, CSVReader.parseLine(null, -1, null));
        assertEquals(Constants.UNKNOWN, CSVReader.parseLine(LINE_ONE, -1, null));
        assertEquals(Constants.UNKNOWN, CSVReader.parseLine(LINE_ONE, 0, null));
        assertEquals(Constants.UNKNOWN, CSVReader.parseLine(LINE_ONE, -1, Constants.DEFAULT_SEPARATOR));
        assertEquals(Constants.UNKNOWN, CSVReader.parseLine(LINE_ONE, 1, null));
        
        assertEquals("0", CSVReader.parseLine(LINE_ONE, 1, Constants.DEFAULT_SEPARATOR));
        assertEquals(Constants.UNKNOWN, CSVReader.parseLine("", 2, Constants.DEFAULT_SEPARATOR));
    }
        
    @Test
    public void testParseLine() {
        logger.info("\ntesting parseLine(String line, int[] columns, String columnSeparator)");
        assertEquals(0, CSVReader.parseLine(null, null, null).size());
        assertEquals(0, CSVReader.parseLine("", null, null).size());
        assertEquals(0, CSVReader.parseLine(LINE_ONE, null, null).size());
        assertEquals(0, CSVReader.parseLine(LINE_ONE, new int[0], null).size());
        
        int[] columns = new int[] { 2, 5, 13 };
        List<String> result = null;
        result = CSVReader.parseLine(LINE_ONE, columns, null);
        result = CSVReader.parseLine(LINE_ONE, columns, "");
        assertEquals(0, result.size());
        
        //indexes out of range
        columns = new int[] { 2, 5, 15 };
        result = CSVReader.parseLine(LINE_ONE, columns, ",");
        assertEquals(0, result.size());
        logger.debug(ListArrayUtil.arrayToString(columns) + ":  " + result);
        
        columns = new int[] { 15, 5, 2 };
        result = CSVReader.parseLine(LINE_ONE, columns, ",");
        assertEquals(0, result.size());
        logger.debug(ListArrayUtil.arrayToString(columns) + ":  " + result);
        
        columns = new int[] { 2, 5, 14 };
        result = CSVReader.parseLine(LINE_ONE, columns, ",");
        assertEquals(0, result.size());
        logger.debug(ListArrayUtil.arrayToString(columns) + ":  " + result);
        
        columns = new int[] { 14, 5, 2 };
        result = CSVReader.parseLine(LINE_ONE, columns, ",");
        assertEquals(0, result.size());
        logger.debug(ListArrayUtil.arrayToString(columns) + ":  " + result);
        
        
        //now the legit inputs
        columns = new int[] { 2, 5, 13 };
        result = CSVReader.parseLine(LINE_ONE, columns, ",");
        assertEquals(3, result.size());
        logger.debug(ListArrayUtil.listToString(result));
        assertEquals("3", result.get(0));
        assertEquals("male", result.get(1));
        assertEquals("FALSE", result.get(2));
        
        columns = new int[] { 2, 13, 5 };
        result = CSVReader.parseLine(LINE_ONE, columns, ",");
        assertEquals(3, result.size());
        logger.debug(ListArrayUtil.listToString(result));
        assertEquals("3", result.get(0));
        assertEquals("FALSE", result.get(1));
        assertEquals("male", result.get(2));
        
        columns = new int[] { 13, 5, 2 };
        result = CSVReader.parseLine(LINE_ONE, columns, ",");
        assertEquals(3, result.size());
        logger.debug(ListArrayUtil.listToString(result));
        assertEquals("FALSE", result.get(0));
        assertEquals("male", result.get(1));
        assertEquals("3", result.get(2));
        
    }

    @Test
    public void testVerifyParameters() throws Exception {
    }
    
    @Test
    public void testGetSingleColumn() {
        logger.info("\ntesting getSingleColumn()");
        DataList<String> result = null;
        
        try {
            result = CSVReader.getSingleColumn("titanic.csv", 1, ",");
            assertEquals(891, result.size());
            for(int i = 0; i < 5; i++) {
                logger.debug(result.get(i));
            }
            assertEquals("0", result.get(0));
            assertEquals("1", result.get(1));
            assertEquals("1", result.get(2));
            assertEquals("1", result.get(3));
            assertEquals("0", result.get(4));
            assertEquals("0", result.get(5));
            
            assertEquals("0", result.get(886));
            assertEquals("1", result.get(887));
        } catch(IOException e) {
            fail(e.getClass() + " in testGetSingleColumn():  " + e.getMessage());
        }
        
        try {
            result = CSVReader.getSingleColumn("titanic.csv", 2, ",");
            assertEquals(891, result.size());
            for(int i = 0; i < 5; i++) {
                logger.debug(result.get(i));
            }
            assertEquals("3", result.get(0));
            assertEquals("1", result.get(1));
            assertEquals("3", result.get(2));
            assertEquals("1", result.get(3));
            assertEquals("3", result.get(4));
            assertEquals("3", result.get(5));
            
            assertEquals("2", result.get(886));
            assertEquals("1", result.get(887));
        } catch(IOException e) {
            fail(e.getClass() + " in testGetSingleColumn():  " + e.getMessage());
        }
        
        try {
            result = CSVReader.getSingleColumn("titanic.csv", 5, ",");
            assertEquals(891, result.size());
            for(int i = 0; i < 5; i++) {
                logger.debug(result.get(i));
            }
            assertEquals("male", result.get(0));
            assertEquals("female", result.get(1));
            assertEquals("female", result.get(2));
            assertEquals("female", result.get(3));
            assertEquals("male", result.get(4));
            assertEquals("male", result.get(5));
            
            assertEquals("male", result.get(886));
            assertEquals("female", result.get(887));
        } catch(IOException e) {
            fail(e.getClass() + " in testGetSingleColumn():  " + e.getMessage());
        }
    }
    
    @Test
    public void testGetColumns() {
        logger.info("\ntesting getColumns()");
        List<List<String>> result = null;
        int[] columns = null;
        columns = new int[] { 2, 5 };
        try {
            assertEquals(0, CSVReader.getColumns(null, null, null).size());
            assertEquals(0, CSVReader.getColumns("titanic.csv", columns, null).size());
            assertEquals(0, CSVReader.getColumns("titanic.csv", columns, "").size());
            
            assertEquals(0, CSVReader.getColumns("titanic.csv", null, ",").size());
            assertEquals(0, CSVReader.getColumns("titanic.csv", new int[] { }, ",").size());
            
            assertEquals(0, CSVReader.getColumns(null, columns, ",").size());
            assertEquals(0, CSVReader.getColumns("", columns, ",").size());
        } catch(IOException e) {
            fail(e.getClass() + " in testGetColumns():  " + e.getMessage());
        }
        
        try {
            assertEquals(0, CSVReader.getColumns("titanic.csv", new int[] { -1, 2 }, ",").size());
        } catch(IOException e) {
            fail(e.getClass() + " in testGetColumns():  " + e.getMessage());
        }
        
        try {
            result = CSVReader.getColumns("titanic.csv", new int[] { 2, 5 }, ",");
            assertEquals(2, result.size());
            List<String> pclass = result.get(0);
            List<String> sex = result.get(1);
            assertEquals(891, pclass.size());
            assertEquals(891, sex.size());
            assertEquals("3", pclass.get(0));
            assertEquals("1", pclass.get(1));
            
            assertEquals("male", sex.get(0));
            assertEquals("female", sex.get(1));
        } catch(IOException e) {
            fail(e.getClass() + " in testGetColumns():  " + e.getMessage());
        }
        
        try {
            result = CSVReader.getColumns("titanic.csv", new int[] { 0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13 }, ",");
            assertEquals(14, result.size());
            List<String> id = result.get(0);
            for(int i = 1; i < 892; i++) {
                assertEquals(Integer.toString(i), id.get(i - 1));
            }
            
            List<String> survived = result.get(1);
            assertEquals("0", survived.get(0));
            assertEquals("1", survived.get(1));
            assertEquals("1", survived.get(889));
            assertEquals("0", survived.get(890));
            
            List<String> pclass = result.get(2);
            assertEquals(891, pclass.size());
            assertEquals("3", pclass.get(0));
            assertEquals("1", pclass.get(1));
            
            List<String> lastName = result.get(3);
            assertEquals("Braund", lastName.get(0));
            
            List<String> firstName = result.get(4);
            assertEquals(" Mr. Owen Harris", firstName.get(0));
            
            List<String> sex = result.get(5);
            assertEquals(891, sex.size());
            assertEquals("male", sex.get(0));
            assertEquals("female", sex.get(1));
            
            List<String> age = result.get(6);
            assertEquals("22", age.get(0));
            
            List<String> sibsp = result.get(7);
            assertEquals("1", sibsp.get(0));
            
            List<String> parch = result.get(8);
            assertEquals("0", parch.get(0));
            
            List<String> ticket = result.get(9);
            assertEquals(891, ticket.size());
            assertEquals("A/5 21171", ticket.get(0));
            
            List<String> fare = result.get(10);
            assertEquals(891, fare.size());
            assertEquals("7.25", fare.get(0));
            
            List<String> cabin = result.get(11);
            assertEquals(891, cabin.size());
            assertEquals("", cabin.get(0));
            assertEquals("C85", cabin.get(1));
            
            List<String> embarked = result.get(12);
            assertEquals("S", embarked.get(0));
            
            List<String> child = result.get(13);
            assertEquals("FALSE", child.get(0));
            assertEquals("NA", child.get(5));
        } catch(IOException e) {
            fail(e.getClass() + " in testGetColumns():  " + e.getMessage());
        }
    }
    
    @Test
    public void testGetAllColumns() {
        logger.debug("\ntesting getAllColumns()");
        List<List<String>> result = null;
        try {
            assertEquals(0, CSVReader.getAllColumns(null, null).size());
            assertEquals(0, CSVReader.getAllColumns(null, "").size());
            assertEquals(0, CSVReader.getAllColumns(null, ",").size());
            assertEquals(0, CSVReader.getAllColumns("", null).size());
            assertEquals(0, CSVReader.getAllColumns("", "").size());
            assertEquals(0, CSVReader.getAllColumns("", ",").size());
            assertEquals(0, CSVReader.getAllColumns("titanic.csv", null).size());
            assertEquals(0, CSVReader.getAllColumns("titanic.csv", "").size());
        } catch(IOException e) {
            fail(e.getClass() + " in testGetAllColumns():  " + e.getMessage());
        }
        
        try {
            assertEquals(14, CSVReader.getAllColumns("titanic.csv", ",").size());   //14, not 13, because of the comma in the name
        } catch(IOException e) {
            fail(e.getClass() + " in testGetAllColumns():  " + e.getMessage());
        }
    }
    
    @Test
    public void testGetSingleHistogram() {
        logger.info("\ntesting getSingleHistogram()");
        Histogram result = null;
        try {
            result = CSVReader.getSingleHistogram(null, 1, ",");
            assertEquals(0, result.getCounts().size());
            result = CSVReader.getSingleHistogram("titanic.csv", -1, ",");
            logger.debug(result.toString());
            assertEquals(0, result.getCounts().size());
            result = CSVReader.getSingleHistogram("titanic.csv", 2, null);
            logger.debug(result.toString());
            assertEquals(0, result.getCounts().size());
            
            result = CSVReader.getSingleHistogram("titanic.csv", 2, ",");
            logger.debug(result.toString());
            assertEquals(3, result.getCounts().size());
        } catch(IOException e) {
            fail(e.getClass() + " " + e.getMessage());
        }
    }

    @Test
    public void testGetSingleProbDist() {
        logger.info("\ntesting getSingleProbDist()");
        try {
            ProbDist result = null;
            result = CSVReader.getSingleDistribution("titanic.csv", 2, ",");
            logger.debug(result.toString());
        } catch(IOException e) {
            fail(e.getClass() + " " + e.getMessage());
        }
    }
    
    @Test
    public void testContains() {
        logger.info("\ncontains");
        ArrayList<String> list1 = new ArrayList<String>();
        list1.add("one");
        list1.add("two");
        assertEquals(true, list1.contains("one"));
        assertEquals(false, list1.contains("three"));
        
        ArrayList<ArrayList<String>> list2 = new ArrayList<ArrayList<String>>();
        ArrayList<String> l1 = new ArrayList<String>();
        l1.add("a");
        l1.add("b");
        ArrayList<String> l2 = new ArrayList<String>();
        l2.add("a");
        l2.add("c");
        ArrayList<String> l3 = new ArrayList<String>();
        l3.add("a");
        l3.add("b");
        ArrayList<String> l4 = new ArrayList<String>();
        l4.add("b");
        l4.add("a");
        
        list2.add(l1);
        //list2.add(l2);
        //list2.add(l3);
        logger.debug(list2.contains(l1));
        logger.debug(list2.contains(l2));
        logger.debug(list2.contains(l3));
        logger.debug(list2.contains(l4));
    }
    
    @Test
    public void testGetJointHistogram() {
        logger.info("\ntesting getJointHistogram()");
        try {
            Histogram result = null;
            result = CSVReader.getJointHistogram("titanic.csv", new int[] { 2, 5 }, ",");
            logger.debug(result.toString());
            logger.debug(result.getEntropy());
        } catch(IOException e) {
            fail(e.getClass() + " " + e.getMessage());
        }
    }
    
    @Test
    public void testGetJointDistribution() {
        logger.info("\ntesting getJointDistribution()");
        try {
            ProbDist result = null;
            result = CSVReader.getJointDistribution("titanic.csv", new int[] { 2, 5 }, ",");
            logger.debug(result.toString());
        } catch(IOException e) {
            fail(e.getClass() + " " + e.getMessage());
        }
    }
    
    @Test
    public void testGetMutualInformation() {
        logger.info("\ntesting getMutualInformation()");
        try {
            double result = 0.0;
            
            logger.debug("first with CSVReader.getMutualInforation():");
            //CLASS(2), SEX(5), SIBSP(7), PARCH(8), EMBARKED(12), ISCHILD(13);
            logger.debug("\nclass and sex:  " + CSVReader.getMutualInformation("titanic.csv", 2, 5, ","));
            logger.debug("class and sibsp:  " + CSVReader.getMutualInformation("titanic.csv", 2, 7, ","));
            logger.debug("class and parch:  " + CSVReader.getMutualInformation("titanic.csv", 2, 8, ","));
            logger.debug("class and port:  " + CSVReader.getMutualInformation("titanic.csv", 2, 12, ","));
            logger.debug("class and child:  " + CSVReader.getMutualInformation("titanic.csv", 2, 13, ","));
            
            logger.debug("sex and sibsp:  " + CSVReader.getMutualInformation("titanic.csv", 5, 7, ","));
            logger.debug("sex and parch:  " + CSVReader.getMutualInformation("titanic.csv", 5, 8, ","));
            logger.debug("sex and port:  " + CSVReader.getMutualInformation("titanic.csv", 5, 12, ","));
            logger.debug("sex and child:  " + CSVReader.getMutualInformation("titanic.csv", 5, 13, ","));
            
            logger.debug("sibsp and parch:  " + CSVReader.getMutualInformation("titanic.csv", 7, 8, ","));
            logger.debug("sibsp and port:  " + CSVReader.getMutualInformation("titanic.csv", 7, 12, ","));
            logger.debug("sibsp and child:  " + CSVReader.getMutualInformation("titanic.csv", 7, 13, ","));
            
            logger.debug("parch and port:  " + CSVReader.getMutualInformation("titanic.csv", 8, 12, ","));
            logger.debug("parch and child:  " + CSVReader.getMutualInformation("titanic.csv", 8, 13, ","));
            
            logger.debug("port and child:  " + CSVReader.getMutualInformation("titanic.csv", 12, 13, ","));

            logger.debug("\n--\nnow with Shannon.getMutualInformation():");
            DataList<String> pclass = CSVReader.getSingleColumn("titanic.csv", 2, ",");
            DataList<String> sex = CSVReader.getSingleColumn("titanic.csv", 5, ",");
            DataList<String> sibsp = CSVReader.getSingleColumn("titanic.csv", 7, ",");
            DataList<String> parch = CSVReader.getSingleColumn("titanic.csv", 8, ",");
            DataList<String> port = CSVReader.getSingleColumn("titanic.csv", 12, ",");
            DataList<String> child = CSVReader.getSingleColumn("titanic.csv", 13, ",");
            
            logger.debug("class and sex:  " + Shannon.getMutualInformation(pclass.getData(), sex.getData()));
            logger.debug("class and sibsp:  " + Shannon.getMutualInformation(pclass.getData(), sibsp.getData()));
            logger.debug("class and parch:  " + Shannon.getMutualInformation(pclass.getData(), parch.getData()));
            logger.debug("class and port:  " + Shannon.getMutualInformation(pclass.getData(), port.getData()));
            logger.debug("class and child:  " + Shannon.getMutualInformation(pclass.getData(), child.getData()));
            
            logger.debug("sex and sibsp:  " + Shannon.getMutualInformation(sex.getData(), sibsp.getData()));
            logger.debug("sex and parch:  " + Shannon.getMutualInformation(sex.getData(), parch.getData()));
            logger.debug("sex and port:  " + Shannon.getMutualInformation(sex.getData(), port.getData()));
            logger.debug("sex and child:  " + Shannon.getMutualInformation(sex.getData(), child.getData()));
            
            logger.debug("sibsp and parch:  " + Shannon.getMutualInformation(sibsp.getData(), parch.getData()));
            logger.debug("sibsp and port:  " + Shannon.getMutualInformation(sibsp.getData(), port.getData()));
            logger.debug("sibsp and child:  " + Shannon.getMutualInformation(sibsp.getData(), child.getData()));
            
            logger.debug("parch and port:  " + Shannon.getMutualInformation(parch.getData(), port.getData()));
            logger.debug("parch and child:  " + Shannon.getMutualInformation(parch.getData(), child.getData()));
            
            logger.debug("port and child:  " + Shannon.getMutualInformation(port.getData(), child.getData()));
            
        } catch(IOException e) {
            fail(e.getClass() + " in getGetMutualInformation(): " + e.getMessage());
        }
        logger.debug("----");
    }
    @Test
    public void testScrub() {
    }
}