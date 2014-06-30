/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package toolbox;

import toolbox.util.MathUtil;
import toolbox.util.ListArrayUtil;
import toolbox.io.CSVReader;
import toolbox.stats.*;
import java.io.*;
import java.util.List;
import java.util.ArrayList;
import org.apache.log4j.*;
import toolbox.random.Random;

import java.util.Date;

/**
 *
 * @author paul
 */
public class InvestmentCalculator {
    
    private Logger logger;
    
    public static void main(String[] args) {
        InvestmentCalculator snp = new InvestmentCalculator();
        //snp.doFinalValueSimulation("sp500.csv", "sp500results.csv", 1000);
        //snp.testSamples("sp500.csv");
        try {
            int numDays = 16173;
            numDays = 365 * 25;
            //snp.doFinalValueSimulation(numDays, 70000, "sp500.csv", "sp500results.csv", 10000, 100.0, 15);
             snp.doFinalValueSimulation(numDays, 100000, "sp500.csv", "sp500results.csv", 10000, -1000.0, 15);  //taking money out
            //snp.doTargetContributionSim("sp500.csv", 16.66, numDays, 1815.69, 14, 10000);  //with numdays at 16173 this should get a median of pretty close to 0
            //snp.doTargetContributionSim("sp500.csv", 70000.0, numDays, 4000000, 15, 10000);
        } catch(IOException e) {
            System.out.println(e.getClass() + " in main():  " + e.getMessage());
        }
    }
    
    public InvestmentCalculator() {
        this.logger = ListArrayUtil.getLogger(this.getClass(), Level.INFO);
    }
    
    public void doFinalValueSimulation(int numDays, double initialInvestment, String inputFilename, String outputFilename, int numRuns, double regularContribution, int interval) throws IOException {
        List<Double> ratios = getRatios(inputFilename);
        double amount = initialInvestment;
        for(int i = 0; i < ratios.size(); i++) {
            amount *= ratios.get(i);
        }
        logger.info("how it actually turned out = " + amount);
        long start = new Date().getTime();
        List<Double> results = new ArrayList<Double>();
        double current = 0.0;
        try {
            ProbDist<Double> probs = new Histogram(ratios).getProbDist();
            for(int i = 0; i < numRuns; i++) {
                //current = doOneRun2(initialInvestment, probs, ratios.size(), regularContribution, interval);//doOneRun2() seems slightly faster
                //current = doOneRun(initialInvestment, ratios, ratios.size(), regularContribution, interval);
                current = doOneRun(initialInvestment, ratios, numDays, regularContribution, interval);
                results.add(current);
                logger.debug(current);
            }
            Histogram hist = new Histogram(results);
            //logger.info(hist.toString());
            logger.info(toolbox.util.MathUtil.summary(results));
        } catch(Exception e) {
            logger.error("Exception in doSimulation:  " + e.getMessage());
        }
        long end = new Date().getTime();
        logger.info("time taken for simulation:  " + (end - start) + " millis");
        PrintWriter writer = new PrintWriter(new FileWriter(outputFilename));
            writer.println("result");
            for(Double d : results) {
                writer.println(d);
            }
            writer.flush();
            writer.close();
    }
    
    /**
     * @deprecated
     * @param inputFilename
     * @param outputFilename
     * @param numRuns 
     */
    public void doFinalValueSimulation(String inputFilename, String outputFilename, int numRuns) {
        try {
            List<Double> ratios = getRatios(inputFilename);
            double amount = 16.66;
            for(int i = 0; i < ratios.size(); i++) {
                amount *= ratios.get(i);
            }
            logger.info("how it actually turned out = " + amount);
            logger.info("how it could have turned out:");
            long start = new Date().getTime();
            List<Double> results = new ArrayList<Double>();
            double current = 0.0;
            try {
                for(int i = 0; i < numRuns; i++) {
                    current = doOneRun(16.66, ratios, ratios.size());
                    results.add(current);
                    logger.debug(current);
                }
                Histogram hist = new Histogram(results);
                //logger.info(hist.toString());
                logger.info(toolbox.util.MathUtil.summary(results));
            } catch(Exception e) {
                logger.error("Exception in doSimulation:  " + e.getMessage());
            }
            long end = new Date().getTime();
            logger.info("time taken for simulation:  " + (end - start) + " millis");
            
            PrintWriter writer = new PrintWriter(new FileWriter(outputFilename));
            writer.println("result");
            for(Double d : results) {
                writer.println(d);
            }
            writer.flush();
            writer.close();
        } catch(IOException e) {
            logger.error(e.getClass() + " trying to load file \"" + inputFilename + "\" in getDistribution():  " + e.getMessage());
        }
    }
    
    protected double doOneRun(double initial, List<Double> ratios, int numDays) throws Exception {
        double amount = initial;
        ratios = Random.sample(ratios, ratios.size(), true);
        for(int i = 0; i < ratios.size(); i++) {
            amount *= ratios.get(i);
        }
        return amount;
    }
    
    protected double doOneRun(double initial, List<Double> ratios, int numDays, double regularContribution, int interval) throws Exception {
        double amount = initial;
        ratios = Random.sample(ratios, numDays, true);
        /**/for(int i = 0; i < ratios.size(); i++) {
            amount *= ratios.get(i);
            if(i % interval == 0) {
                amount += regularContribution;
            }
        }
        return amount;/**/
        //return getTotal(initial, ratios, regularContribution, interval);
    }
    
    protected double doOneRun2(double initial, ProbDist<Double> ratios, int numDays, double regularContribution, int interval) throws Exception {
        double amount = initial;
        //ratios = Random.sample(ratios, ratios.size(), true);
        //int size = ratios.getProbabilities().size();
        for(int i = 0; i < numDays; i++) {
            amount *= ratios.getRandomValue();
            if(i % interval == 0) {
                amount += regularContribution;
            }
        }
        return amount;
    }
    
    /**
     * Let's say you have a list and want to multiply it all together, but add a certain amount at regular intervals, like investing a certain amount every two weeks.
     * The day to day percent changes are a, b, c, d, e, f, g, h, i, j, k, and l.  Without the regular contribution, the total would be abcdefghijkl.
     * If you add x dollars every three days, it becomes abcdefghiklk + xdefghijkl + xghijkl + xjkl.
     */
    protected double getTotal(double initial, List<Double> ratios, double regularContribution, int interval) {
        if(ratios == null || ratios.isEmpty()) {
            return 0.0;
        }
        
        double[] bins = getBins(ratios, interval);
        //bins will hold the products abc, def, ghi, jkl from the example above
        double[] cumBins = MathUtil.cumProd(bins, true);//new double[bins.length];
        //cum bins will hold the products abcdefghiklk, defghijkl, ghijkl and jkl from the example above.  Really it's more like reverse cumulative.        
        //return initial * (cumBins[0] + regularContribution * MathUtil.sum(cumBins, 1, cumBins.length - 1));
        return initial * cumBins[0] + regularContribution * MathUtil.sum(cumBins, 1, cumBins.length - 1);
    }
    
    protected double[] getBins(List<Double> list, int interval) {
        if(list == null) {
            return new double[0];
        }
        int numBins = list.size() / interval;
        if(list.size() % interval != 0) {
            numBins++;
        }
        double[] bins = new double[numBins];
        
        //now fill in the bins, each being the product of the ratios between contributions (for "interval" number of days)
        //so if the interval is 14 days, the first bin will be the product of the first 14 days, the second will be the product of the second 14 days, etc.
        for(int i = 0; i < numBins; i++) {
            bins[i] = toolbox.util.MathUtil.prod(list, i * interval, i * interval + interval - 1);
        }
        return bins;
    }
    
    protected List<Double> doTargetContributionSim(String inputFilename, double initial, int numDays, double targetEnd, int interval, int numRuns) throws IOException {
        List<Double> ratios = getRatios(inputFilename);
        long start = new Date().getTime();
        List<Double> results = new ArrayList<Double>();
        double current = 0.0;
        for(int i = 0; i < numRuns; i++) {
            try {
            current = this.getOneTargetContribution(initial, Random.sample(ratios, numDays, true), targetEnd, interval);
            results.add(current);
            } catch(Exception e) {
                logger.error(e.getClass() + " trying to load file \"" + inputFilename + "\" in getDistribution():  " + e.getMessage());
            }
        }
        //Histogram hist = new Histogram(results);
        logger.info(toolbox.util.MathUtil.summary(results));
        long end = new Date().getTime();
        logger.info("time taken for simulation:  " + (end - start) + " millis");
        return results;
    }
    
    /**
     * Tells you have much you should contribute in your regular contribution, given an initial investment, daily percent changes, a target amuont to have at the end, and an interval of how often to do regular investments (e.g. every 15 days).
     * If the ratios is null, a NULLPointerException will result.
     * Otherwise, if ratios is empty or less than interval, it will assume only only contribution.
     * @param initial
     * @param ratios
     * @param targetEnd
     * @param interval
     * @return 
     */
    protected double getOneTargetContribution(double initial, List<Double> ratios, double targetEnd, int interval) {
        
        if(ratios.isEmpty()) {
            return targetEnd - initial;
        }
        
        double[] bins = getBins(ratios, interval);
        //bins will hold the products abc, def, ghi, jkl from the example above
        double[] cumBins = MathUtil.cumProd(bins, true);
        //double regularContribution = ((targetEnd / initial) - cumBins[0]) / MathUtil.sum(cumBins, 1, cumBins.length - 1);
        //targetEnd = initial * cumBins[0] + regularContribution * MathUtil.sum(cumBins, 1, cumBins.length - 1);
        if(interval > ratios.size()) {
            double regularContribution = (targetEnd - initial * cumBins[0]); 
            return regularContribution;
        }
        double regularContribution = (targetEnd - initial * cumBins[0]) / MathUtil.sum(cumBins, 1, cumBins.length - 1); //TODO:  determine when this could be 0
        return regularContribution;
    }
    
    public void testSamples(String filename) {
        ProbDist<String> dist = getDistribution(filename, 4);
        int numSamples = 10000;
        int numRuns = 1000;
        double[] means1 = new double[numRuns];
        
        try {
            DataList<String> list = CSVReader.getSingleColumn(filename, 4, ",");
            logger.debug(list.size());
            for(int i = 0; i < 10; i++) {
                //logger.debug(list.get(i));
            }
            DataList<Double> numList = new DataList<Double>();
            for(int i = 0; i < list.size(); i++) {
                numList.add(Double.parseDouble(list.get(i)));
            }
            for(int i = 0; i < 10; i++) {
                //logger.debug(numList.get(i));
            }
            long t1 = new Date().getTime();
            for(int i = 0; i < numRuns; i++) {
                try {
                    List<Double> current = Random.sample(numList.getData(), numSamples, true);
                    means1[i] = toolbox.util.MathUtil.mean(current);
                    logger.debug(i + " " + means1[i]);
                } catch(Exception e) {
                    logger.error(e.getClass() + " " + e.getMessage());
                }
            }
            long t2 = new Date().getTime();
            logger.info(toolbox.util.MathUtil.mean(means1));
            
            logger.debug("\n\n---\n\n");
            double[] means2 = new double[numRuns];
            ProbDist<Double> d = numList.getHistogram().getProbDist();
            long t3 = new Date().getTime();
            for(int i = 0; i < numRuns; i++) {
                double sum = 0.0;
                for(int j = 0; j < numSamples; j++) {
                    sum += d.getRandomValue();
                }
                means2[i] = sum / numSamples;
                logger.debug(i + " " + means2[i]);
            }
            long t4 = new Date().getTime();
            logger.info(toolbox.util.MathUtil.mean(means2));
            /*double[] means3 = new double[numRuns];
            long t5 = new Date().getTime();
            for(int i = 0; i < numRuns; i++) {
                double sum = 0.0;
                for(int j = 0; j < numSamples; j++) {
                    sum += d.getRandomValue2();
                }
                means3[i] = sum / numSamples;
                logger.debug(i + " " + means3[i]);
            }
            long t6 = new java.util.Date().getTime();
            logger.info(toolbox.stats.ListArrayUtil.mean(means3));*/
            
            long sampleTime = t2 - t1;
            long probDistTime = t4 - t3;
            //long probDistTime2 = t6 - t5;
            logger.debug("Random.sample() time = " + sampleTime + " milliseconds");
            logger.debug("ProbDist.getRandomValue() time = " + probDistTime + " milliseconds");
            //logger.debug("ProbDist.getRandomValue2() time = " + probDistTime2 + " milliseconds");
            if(probDistTime > sampleTime) {
                logger.debug("prob dist time was " + (double)probDistTime / (double)sampleTime + " times sample time");
            } else if(sampleTime > probDistTime) {
                logger.debug("sample time was " + (double)sampleTime / (double)probDistTime + " times prob dist time");
            } else {
                logger.debug("both times were the same");
            }
        } catch(IOException e) {
            logger.error(e.getClass() + " trying to load file \"" + filename + "\" in getDistribution():  " + e.getMessage());
        }
        
        /*for(int i = 0; i < numRuns; i++) {
            //logger.debug(i);
            double sum = 0.0;
            for(int j = 0; j < numSamples; j++) {
                sum += Double.parseDouble(dist.getRandomValue());
            }
            means[i] = sum / numSamples;
        }*/
        //logger.info(ListArrayUtil.mean(means));
    }
    
    public ProbDist<String> getDistribution(String filename, int column) {
        try {
            DataList list = CSVReader.getSingleColumn(filename, column, ",");
            logger.debug(list.size());
            for(int i = 0; i < 10; i++) {
                //logger.debug(list.get(i));
            }
            Histogram hist = new Histogram(list);
            logger.debug(hist.size());
            return hist.getProbDist();
        } catch(IOException e) {
            logger.error(e.getClass() + " trying to load file \"" + filename + "\" in getDistribution():  " + e.getMessage());
        }
        return null;
    }
    
    protected List<Double> getRatios(String filename) throws IOException {
        if(filename == null || filename.equals("")) {
            return new ArrayList<Double>();
        }
        DataList<String> list = CSVReader.getSingleColumn(filename, 4, ",");
        logger.debug(list.size());
        DataList<Double> numList = new DataList<Double>();
        for(int i = 0; i < list.size(); i++) {
            numList.add(Double.parseDouble(list.get(i)));
        }

        List<Double> ratios = MathUtil.diffRatiosList(numList.getData());
        return ratios;
    }
}
