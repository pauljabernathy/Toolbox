/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package toolbox.information;

import java.io.IOException;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

import org.apache.logging.log4j.*;
import java.util.ArrayList;
import java.util.List;
import toolbox.io.CSVReader;
import toolbox.util.ListArrayUtil;
import toolbox.stats.*;

/**
 *
 * @author paul
 */
public class ShannonTest {
    
    private static Logger logger;
    private static Logger sameLineLogger;
    
    public ShannonTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
        logger = toolbox.util.ListArrayUtil.getLogger(ShannonTest.class, Level.DEBUG);
        sameLineLogger = ListArrayUtil.getSameLineLogger(ShannonTest.class, Level.INFO);
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
    public void testGetEntropy_Array() {
        logger.info("\ntesting getEntropy(T[] input)");
        
        Integer[] inputInt = new Integer[] { 1, 2, 3, 1 }; 
        assertEquals(1.5, Shannon.getEntropy(inputInt), 0.0000001);
        
        String[] inputStr = new String[] { "strawberry", "strawberry", "raspberry", "blueberry" };
        assertEquals(1.5, Shannon.getEntropy(inputStr), 0.000001);
    }

    @Test
    public void testGetEntropy_List() {
        logger.info("\ntesting getEntropy(List<T> input)");
        List input = new ArrayList<Integer>();
        input.add(1);
        input.add(2);
        input.add(3);
        input.add(1);
        
        assertEquals(1.5, Shannon.getEntropy(input), 0.000001);
        
        input = new ArrayList<String>();
        input.add("strawberry");
        input.add("strawberry");
        input.add("raspberry");
        input.add("blueberry");
        
        assertEquals(1.5, Shannon.getEntropy(input), 0.0000001);
    }

    @Test
    public void testGetMutualInformation() {
        logger.info("\ntesting getMutualInformation()");
        String separator = ";   ";
        ArrayList left = new ArrayList<Double>();
        left.add(1.0);
        left.add(2.0);
        left.add(3.0);
        left.add(4.0);
        
        ArrayList right = new ArrayList<Double>();
        right.add(5.0);
        right.add(6.0);
        right.add(7.0);
        right.add(8.0);
        
        ProbDist<Double> A = new Histogram(left).getProbDist();
        ProbDist<Double> B = new Histogram(right).getProbDist();
        /*logger.debug(A.getEntropy());
        logger.debug(B.getEntropy());
        logger.debug(Shannon.getCombinedEntropy(left, right));
        logger.debug(Shannon.getMutualInformation(left, right) + separator + Shannon.getEntropyRatio(left, right));*/
        this.showEntropies(left, right);
        assertEquals(2.0, Shannon.getMutualInformation(left, right), 0.00000001);
        
        logger.debug("\n");
        right = new ArrayList<Double>();
        right.add(1.0);
        right.add(2.0);
        right.add(3.0);
        right.add(4.0);
        A = new Histogram(left).getProbDist();
        B = new Histogram(right).getProbDist();
        /*logger.debug(A.getEntropy());
        logger.debug(B.getEntropy());
        logger.debug(Shannon.getCombinedEntropy(left, right));
        logger.debug(Shannon.getMutualInformation(left, right) + separator + Shannon.getEntropyRatio(left, right));*/
        this.showEntropies(left, right);
        assertEquals(2.0, Shannon.getMutualInformation(left, right), 0.00000001);
        
        logger.debug("\n");
        right = new ArrayList<Double>();
        right.add(2.0);
        right.add(2.0);
        right.add(2.0);
        right.add(2.0);
        A = new Histogram(left).getProbDist();
        B = new Histogram(right).getProbDist();
        /*logger.debug(A.getEntropy());
        logger.debug(B.getEntropy());
        logger.debug(Shannon.getCombinedEntropy(left, right));
        logger.debug(Shannon.getMutualInformation(left, right) + separator + Shannon.getEntropyRatio(left, right));*/
        this.showEntropies(left, right);
        assertEquals(0.0, Shannon.getMutualInformation(left, right), 0.00000001);
        
        logger.debug("\n");
        left = new ArrayList<Double>();
        left.add(2.0);
        left.add(3.0);
        left.add(5.0);
        left.add(2.0);
        
        right = new ArrayList<Double>();
        right.add(4.0);
        right.add(3.0);
        right.add(8.0);
        right.add(4.0);
        
        this.showEntropies(left, right);
        
        logger.debug("\n");
        right = new ArrayList<Double>();
        right.add(4.0);
        right.add(3.0);
        right.add(8.0);
        right.add(5.0);
        
        this.showEntropies(left, right);
        
        logger.debug("\n");
        left = new ArrayList<Double>();
        left.add(2.0);
        left.add(3.0);
        left.add(5.0);
        left.add(2.0);
        
        right = new ArrayList<Double>();
        right.add(3.0);
        right.add(4.0);
        right.add(8.0);
        right.add(4.0);
        
        /*logger.debug(A.getEntropy());
        logger.debug(B.getEntropy());
        logger.debug(Shannon.getCombinedEntropy(left, right));
        logger.debug(Shannon.getMutualInformation(left, right) + separator + Shannon.getEntropyRatio(left, right));*/
        this.showEntropies(left, right);
        
        logger.debug("\n");
        left = new ArrayList<Double>();
        left.add(1.0);
        left.add(2.0);
        left.add(3.0);
        left.add(4.0);
        
        right = new ArrayList<Double>();
        right.add(5.0);
        right.add(5.0);
        right.add(6.0);
        right.add(6.0);
        /*logger.debug(A.getEntropy());
        logger.debug(B.getEntropy());
        logger.debug(Shannon.getCombinedEntropy(left, right));
        logger.debug(Shannon.getMutualInformation(left, right) + separator + Shannon.getEntropyRatio(left, right));*/
        this.showEntropies(left, right);
        assertEquals(1.0, Shannon.getMutualInformation(left, right), .0000001);
        
        logger.debug("\n");
        left = new ArrayList<String>();
        right = new ArrayList<String>();
        left.add("1.0");
        left.add("2.0");
        left.add("3.0");
        left.add("4.0");
        
        right.add(5.0);
        right.add(5.0);
        right.add(6.0);
        right.add(6.0);
        //TODO:  Figure out what class is being used above.  String or Double?
        /*logger.debug(A.getEntropy());
        logger.debug(B.getEntropy());
        logger.debug(Shannon.getCombinedEntropy(left, right));
        logger.debug(Shannon.getMutualInformation(left, right) + separator + Shannon.getEntropyRatio(left, right));*/
        this.showEntropies(left, right);
        assertEquals(1.0, Shannon.getMutualInformation(left, right), .0000001);
        
        logger.debug("\n");
        left = new ArrayList<Double>();
        left.add(1.0);
        left.add(2.0);
        left.add(3.0);
        left.add(4.0);
        left.add(5.0);
        left.add(6.0);
        left.add(7.0);
        left.add(8.0);
        
        right = new ArrayList<Double>();
        right.add(9.0);
        right.add(10.0);
        right.add(11.0);
        right.add(12.0);
        right.add(13.0);
        right.add(14.0);
        right.add(15.0);
        right.add(16.0);
        
        this.showEntropies(left, right);
        
        logger.debug("");
        right = new ArrayList<Double>();
        right.add(9.0);
        right.add(10.0);
        right.add(11);
        right.add(12);
        right.add(13);
        right.add(14);
        right.add(15);
        right.add(15);
        this.showEntropies(left, right);
        
        logger.debug("\n");
        left = new ArrayList<Double>();
        left.add(1.0);
        left.add(2.0);
        left.add(3.0);
        left.add(4.0);
        left.add(1.0);
        left.add(2.0);
        left.add(3.0);
        left.add(4.0);
        
        right = new ArrayList<Double>();
        right.add(9.0);
        right.add(10.0);
        right.add(11.0);
        right.add(12.0);
        right.add(9.0);
        right.add(10.0);
        right.add(11.0);
        right.add(12.0);
        this.showEntropies(left, right);
        
        logger.debug("");
        right = new ArrayList<Double>();
        right.add(9.0);
        right.add(10.0);
        right.add(11.0);
        right.add(12.0);
        right.add(9.0);
        right.add(10.0);
        right.add(11.0);
        right.add(13.0);
        this.showEntropies(left, right);
        
        logger.debug("");
        right = new ArrayList<Double>();
        right.add(9.0);
        right.add(10.0);
        right.add(11.0);
        right.add(12.0);
        right.add(13.0);
        right.add(14.0);
        right.add(15.0);
        right.add(16.0);
        this.showEntropies(left, right);
        
        logger.debug("");
        right = new ArrayList<Double>();
        right.add(9.0);
        right.add(10.0);
        right.add(11.0);
        right.add(12.0);
        right.add(13.0);
        right.add(14.0);
        right.add(15.0);
        right.add(15.0);
        this.showEntropies(left, right);
        
        logger.debug("");
        right = new ArrayList<Double>();
        right.add(9.0);
        right.add(10.0);
        right.add(11.0);
        right.add(12.0);
        right.add(12.0);
        right.add(11.0);
        right.add(10.0);
        right.add(9.0);
        this.showEntropies(left, right);
        
        /**try {
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
        }/**/
    }
    
    private <T> void showEntropies2(List<T> left, List<T> right, boolean showLists) {
        String separator = "   ";
        if(showLists) {
            logger.debug("X = " + ListArrayUtil.listToString(left));
            logger.debug("Y = " + ListArrayUtil.listToString(right));
        }
        logger.debug("H(X) = " + new Histogram(left).getEntropy());
        logger.debug("H(Y) = " + new Histogram(right).getEntropy());
        logger.debug("H(X,Y) = " + Shannon.getCombinedEntropy(left, right));
        //logger.debug(Shannon.getMutualInformation(left, right) + separator + Shannon.getEntropyRatio(left, right));
        logger.debug("I(X,Y) = " + Shannon.getMutualInformation(left, right));
        logger.debug("H(X,Y) / (H(X) + H(Y)) = " + Shannon.getEntropyRatio(left, right));
        logger.debug("H(X,Y) / I(X,Y) = " + Shannon.getCombinedEntropy(left, right) / Shannon.getMutualInformation(left, right));
        logger.debug("combined histogram size = " + new Histogram(Shannon.getCombinedList(left, right)).size());
    }
    
    private <T> void showEntropies(List<T> left, List<T> right, boolean showLists) {
        String separator = "   ";
        if(showLists) {
            logger.debug("left = " + ListArrayUtil.listToString(left));
            logger.debug("right = " + ListArrayUtil.listToString(right));
        }
        logger.debug("HL = " + new Histogram(left).getEntropy());
        logger.debug("HR = " + new Histogram(right).getEntropy());
        logger.debug("HLR = " + Shannon.getCombinedEntropy(left, right));
        //logger.debug(Shannon.getMutualInformation(left, right) + separator + Shannon.getEntropyRatio(left, right));
        logger.debug("I = " + Shannon.getMutualInformation(left, right));
        logger.debug("HLR / (HL + HR) = " + Shannon.getEntropyRatio(left, right));
        logger.debug("HLR / I = " + Shannon.getCombinedEntropy(left, right) / Shannon.getMutualInformation(left, right));
        logger.debug("combined histogram size = " + new Histogram(Shannon.getCombinedList(left, right)).size());
    }
    
    private <T> void showEntropies(List<T> left, List<T> right) {
        showEntropies2(left, right, true);
    }
    @Test
    public void testGetEntropyRatio() {
        logger.info("\ntesting getEntropyRatio()");
        try {
            DataList<String> pclass = CSVReader.getSingleColumn("titanic.csv", 2, ",");
            DataList<String> sex = CSVReader.getSingleColumn("titanic.csv", 5, ",");
            DataList<String> sibsp = CSVReader.getSingleColumn("titanic.csv", 7, ",");
            DataList<String> parch = CSVReader.getSingleColumn("titanic.csv", 8, ",");
            DataList<String> port = CSVReader.getSingleColumn("titanic.csv", 12, ",");
            DataList<String> child = CSVReader.getSingleColumn("titanic.csv", 13, ",");
            
            logger.debug("class and sex:  " + Shannon.getEntropyRatio(pclass.getData(), sex.getData()));
            logger.debug("class and sibsp:  " + Shannon.getEntropyRatio(pclass.getData(), sibsp.getData()));
            logger.debug("class and parch:  " + Shannon.getEntropyRatio(pclass.getData(), parch.getData()));
            logger.debug("class and port:  " + Shannon.getEntropyRatio(pclass.getData(), port.getData()));
            logger.debug("class and child:  " + Shannon.getEntropyRatio(pclass.getData(), child.getData()));
            
            logger.debug("sex and sibsp:  " + Shannon.getEntropyRatio(sex.getData(), sibsp.getData()));
            logger.debug("sex and parch:  " + Shannon.getEntropyRatio(sex.getData(), parch.getData()));
            logger.debug("sex and port:  " + Shannon.getEntropyRatio(sex.getData(), port.getData()));
            logger.debug("sex and child:  " + Shannon.getEntropyRatio(sex.getData(), child.getData()));
            
            logger.debug("sibsp and parch:  " + Shannon.getEntropyRatio(sibsp.getData(), parch.getData()));
            logger.debug("sibsp and port:  " + Shannon.getEntropyRatio(sibsp.getData(), port.getData()));
            logger.debug("sibsp and child:  " + Shannon.getEntropyRatio(sibsp.getData(), child.getData()));
            
            logger.debug("parch and port:  " + Shannon.getEntropyRatio(parch.getData(), port.getData()));
            logger.debug("parch and child:  " + Shannon.getEntropyRatio(parch.getData(), child.getData()));
            
            logger.debug("port and child:  " + Shannon.getEntropyRatio(port.getData(), child.getData()));
        } catch(IOException e) {
            fail(e.getClass() + " in getGetMutualInformation(): " + e.getMessage());
        }
    }
    
    @Test
    public void compareMutualInformationAndEntropyRatio() {
        logger.info("\ncompareMutualInformationAndEntropyRatio()");
        try {
            DataList<String> pclass = CSVReader.getSingleColumn("titanic.csv", 2, ",");
            DataList<String> sex = CSVReader.getSingleColumn("titanic.csv", 5, ",");
            DataList<String> sibsp = CSVReader.getSingleColumn("titanic.csv", 7, ",");
            DataList<String> parch = CSVReader.getSingleColumn("titanic.csv", 8, ",");
            DataList<String> port = CSVReader.getSingleColumn("titanic.csv", 12, ",");
            DataList<String> child = CSVReader.getSingleColumn("titanic.csv", 13, ",");
            
            String separator = ",";
            /*logger.debug("class and sex" + separator + Shannon.getMutualInformation(pclass.getData(), sex.getData()) + separator + Shannon.getEntropyRatio(pclass.getData(), sex.getData()) + separator + Shannon.getKullbackLeiblerDivergence(pclass.getData(), sex.getData()));
            logger.debug("class and sibsp" + separator + Shannon.getMutualInformation(pclass.getData(), sibsp.getData()) + separator  + Shannon.getEntropyRatio(pclass.getData(), sibsp.getData()));
            logger.debug("class and parch" + separator + Shannon.getMutualInformation(pclass.getData(), parch.getData()) + separator  + Shannon.getEntropyRatio(pclass.getData(), parch.getData()));
            logger.debug("class and port" + separator + Shannon.getMutualInformation(pclass.getData(), port.getData()) + separator  + Shannon.getEntropyRatio(pclass.getData(), port.getData()));
            logger.debug("class and child" + separator + Shannon.getMutualInformation(pclass.getData(), child.getData()) + separator  + Shannon.getEntropyRatio(pclass.getData(), child.getData()));
            
            logger.debug("sex and sibsp" + separator + Shannon.getMutualInformation(sex.getData(), sibsp.getData()) + separator  + Shannon.getEntropyRatio(sex.getData(), sibsp.getData()));
            logger.debug("sex and parch" + separator + Shannon.getMutualInformation(sex.getData(), parch.getData()) + separator  + Shannon.getEntropyRatio(sex.getData(), parch.getData()));
            logger.debug("sex and port" + separator + Shannon.getMutualInformation(sex.getData(), port.getData()) + separator  + Shannon.getEntropyRatio(sex.getData(), port.getData()));
            logger.debug("sex and child" + separator + Shannon.getMutualInformation(sex.getData(), child.getData()) + separator  + Shannon.getEntropyRatio(sex.getData(), child.getData()));
            
            logger.debug("sibsp and parch" + separator + Shannon.getMutualInformation(sibsp.getData(), parch.getData()) + separator  + Shannon.getEntropyRatio(sibsp.getData(), parch.getData()));
            logger.debug("sibsp and port" + separator + Shannon.getMutualInformation(sibsp.getData(), port.getData()) + separator  + Shannon.getEntropyRatio(sibsp.getData(), port.getData()));
            logger.debug("sibsp and child" + separator + Shannon.getMutualInformation(sibsp.getData(), child.getData()) + separator  + Shannon.getEntropyRatio(sibsp.getData(), child.getData()));
            
            logger.debug("parch and port" + separator + Shannon.getMutualInformation(parch.getData(), port.getData()) + separator  + Shannon.getEntropyRatio(parch.getData(), port.getData()));
            logger.debug("parch and child" + separator + Shannon.getMutualInformation(parch.getData(), child.getData()) + separator  + Shannon.getEntropyRatio(parch.getData(), child.getData()));
            
            logger.debug("port and child" + separator + Shannon.getMutualInformation(port.getData(), child.getData()) + separator  + Shannon.getEntropyRatio(port.getData(), child.getData()));*/
           doOneComparison(pclass.getData(), sex.getData(), "class", "sex", separator);
           doOneComparison(pclass.getData(), sibsp.getData(), "class", "sibsp", separator);
           doOneComparison(pclass.getData(), parch.getData(), "class", "parch", separator);
           doOneComparison(pclass.getData(), port.getData(), "class", "port", separator);
           doOneComparison(pclass.getData(), child.getData(), "class", "child", separator);
           
           doOneComparison(sex.getData(), sibsp.getData(), "sex", "sibsp", separator);
           doOneComparison(sex.getData(), parch.getData(), "sex", "parch", separator);
           doOneComparison(sex.getData(), port.getData(), "sex", "port", separator);
           doOneComparison(sex.getData(), child.getData(), "sex", "child", separator);
           
           doOneComparison(sibsp.getData(), parch.getData(), "sibsp", "parch", separator);
           doOneComparison(sibsp.getData(), port.getData(), "sibsp", "port", separator);
           doOneComparison(sibsp.getData(), child.getData(), "sibsp", "child", separator);
           
           doOneComparison(parch.getData(), port.getData(), "parch", "port", separator);
           doOneComparison(parch.getData(), child.getData(), "parch", "child", separator);
           
           doOneComparison(port.getData(), child.getData(), "port", "child", separator);
           
        } catch(IOException e) {
            fail(e.getClass() + " in getGetMutualInformation(): " + e.getMessage());
        } /*catch(ProbabilityException e) {
            fail(e.getClass() + " in getGetMutualInformation(): " + e.getMessage());
        }*/
    }
    
    private void doOneComparison(List a, List b, String aName, String bName, String separator) {
        try {
            logger.debug(aName + " and " + bName + separator + Shannon.getMutualInformation(a, b) + separator + Shannon.getEntropyRatio(a, b) + separator + Shannon.getKullbackLeiblerDivergence(a, b) + separator + Shannon.getKullbackLeiblerDivergence(b, a));
        } catch(ProbabilityException e) {
            logger.error("ProbabilityException comparing " + aName + " and " + bName + ":  " + e.getMessage());
        }
    }
    
    @Test
    public void testGetCombinedList() {
        logger.info("\ntesting getCombinedList()");
        
        List<List<Double>> result = null;
        assertEquals(0, Shannon.getCombinedList(null, null).size());
        
        ArrayList<Double> left = new ArrayList<Double>();
        left.add(2.0);
        left.add(3.0);
        left.add(5.0);
        
        ArrayList<Double> right = new ArrayList<Double>();
        right.add(4.0);
        right.add(3.0);
        right.add(8.0);
        right.add(4.0);
        
        assertEquals(0, Shannon.getCombinedList(left, right).size());
        
        left.add(2.0);
        result = Shannon.getCombinedList(left, right);
        assertEquals(4, result.size());
        assertEquals(2, result.get(0).get(0), 0.0);
        assertEquals(4, result.get(0).get(1), 0.0);
        assertEquals(3, result.get(1).get(0), 0.0);
        assertEquals(3, result.get(1).get(1), 0.0);
        assertEquals(5, result.get(2).get(0), 0.0);
        assertEquals(8, result.get(2).get(1), 0.0);
        assertEquals(2, result.get(3).get(0), 0.0);
        assertEquals(4, result.get(3).get(1), 0.0);
        for(List<Double> list : result) {
            for(Double d : list) {
                sameLineLogger.debug(d + " ");
            }
            sameLineLogger.debug("\n");
        }
    }
    
    @Test
    public void testGetKullbackLeiblerDivergence() {
        logger.info("\ntesting getKullbackLeiblerDivergence()");
        ProbDist p = null;
        ProbDist q = null;
        try {
            Shannon.getKullbackLeiblerDivergence(p, q);
            fail("did not throw a ProbabilityException");
        } catch(ProbabilityException e) {
            logger.debug("correctly threw the exception with message " + e.getMessage());
        }
        p = new ProbDist();
        q = new ProbDist();
        try {
            Shannon.getKullbackLeiblerDivergence(p, q);
            fail("did not throw a ProbabilityException");
        } catch(ProbabilityException e) {
            logger.debug("correctly threw the exception with message" + e.getMessage());
        }
        
        p.add("a", .25);
        p.add("b", .5);
        p.add("c", .125);
        p.add("d", .125);
        
        q.add("a", .25);
        q.add("b", .5);
        q.add("c", .25);
        try {
            Shannon.getKullbackLeiblerDivergence(p, q);
            fail("did not throw a ProbabilityException");
        } catch(ProbabilityException e) {
            logger.debug("correctly threw the exception with message" + e.getMessage());
        }
        
        q = new ProbDist();
        q.add("a", .5);
        q.add("b", .25);
        q.add("c", .25);
        q.add("d", 0);
        try {
            logger.debug(Shannon.getKullbackLeiblerDivergence(p, q));
            fail("did not throw a ProbabilityException");
        } catch(ProbabilityException e) {
            logger.debug("correctly threw the exception with message" + e.getMessage());
        }
        
        q = new ProbDist();
        q.add("a", .125);
        q.add("b", .25);
        q.add("c", .125);
        q.add("d", .5);
        try {
            logger.debug(Shannon.getKullbackLeiblerDivergence(p, q));
            logger.debug(Shannon.getKullbackLeiblerDivergence(q, p));
            assertEquals(.5, Shannon.getKullbackLeiblerDivergence(p, q), .00001);
            assertEquals(.625, Shannon.getKullbackLeiblerDivergence(q, p), .00001);
            logger.debug(toolbox.util.MathUtil.logBase2(0));
            logger.debug(toolbox.util.MathUtil.logBase2(0) * 0.0);
            logger.debug(0.0 * toolbox.util.MathUtil.logBase2(0));
        } catch(ProbabilityException e) {
            logger.debug(e.getClass() + " " + e.getMessage());
        }
    }
}