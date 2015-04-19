/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package toolbox.information;

import toolbox.stats.*;
import java.util.List;
import java.util.ArrayList;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.Level;
import toolbox.util.ListArrayUtil;
import toolbox.util.MathUtil;

/**
 *
 * @author paul
 */
public class Shannon {
    
    private static Logger logger;
    
    static {
        logger = ListArrayUtil.getLogger(Shannon.class, Level.INFO);
    }
    
    //TODO:  Should these functions even be here since they just pass the calculation off to something else?
    //Or should Histogram pass the calculation off to here?
    public static <T> double getEntropy(T[] input) {
        return new DataList<T>(input).getEntropy();
    }
    
    public static <T> double getEntropy(List<T> input) {
        return new Histogram(input).getEntropy();
    }
    
    /**
     * finds the mutual information of the two lists, using the formula HL + HR - HLR where HL is the entropy of the left list, HR is the entropy of the right, and HLR is the entropy of the combined list
     * @param <T> the list type
     * @param left one list
     * @param right the other list
     * @return the mutual information
     */
    public static <T> double getMutualInformation(List<T> left, List<T> right) {
        if(left == null || right == null || left.size() != right.size()) {
            return -1.0;
        }
        
        List<List<T>> combined = getCombinedList(left, right);
        double HL = new Histogram(left).getEntropy();
        logger.debug("HL = " + HL);
        double HR = new Histogram(right).getEntropy();
        logger.debug("HR = " + HR);
        double HLR = new Histogram(combined).getEntropy();
        logger.debug("HLR = " + HLR);
        return HL + HR - HLR;
    }
    
    public static <T> double getEntropyRatio(List<T> left, List<T> right) {
        if(left == null || right == null || left.size() != right.size()) {
            return -1.0;
        }
        
        List<List<T>> combined = getCombinedList(left, right);
        double HL = new Histogram(left).getEntropy();
        logger.debug("HL = " + HL);
        double HR = new Histogram(right).getEntropy();
        logger.debug("HR = " + HR);
        double HLR = new Histogram(combined).getEntropy();
        logger.debug("HLR = " + HLR);
        return HLR / (HL + HR);     //TODO:  deal with case where HL + HR = 0  HL + HR is only 0 if both are 0, right?  Then, wouldn't HLR also be 0?
        //return (HL + HR) / HLR;     //TODO:  if HLR is 0, that means HL and HR are also 0, right?  So what would this be?  1?
    }
    
    public static <T> List<List<T>> getCombinedList(List<T> left, List<T> right) {
        if(left == null || right == null || left.size() != right.size()) {
            return new ArrayList<List<T>>();
        }
        ArrayList<List<T>> combined = new ArrayList<List<T>>();
        ArrayList<T> current = new ArrayList<T>();
        for(int i = 0; i < left.size(); i++) {
            current = new ArrayList<T>();
            current.add(left.get(i));
            current.add(right.get(i));
            combined.add(current);
        }
        
        return combined;
    }
    
    public static <T> double getCombinedEntropy(List<T> left, List<T> right) {
        List<List<T>> combined = getCombinedList(left, right);
        return new Histogram(combined).getEntropy();
    }
    
    /**
     * gives the Kullback-Leibler divergence using 2 as the logarithm base
     * @param p one probability distribution
     * @param q the other probability distribution
     * @return the Kullback / Leibler Divergence
     * @throws ProbabilityException if one or more of the given probability distributions is invalid
     */
    public static double getKullbackLeiblerDivergence(ProbDist p, ProbDist q) throws ProbabilityException {
        if(p == null || q == null || p.isEmpty() || q.isEmpty()) {
            throw new ProbabilityException("cannot compute Kullback-Leibler divergence of a null probability distribution");
        }
        if(p.getProbabilities().size() != q.getProbabilities().size()) {
            throw new ProbabilityException("probability distributions must be of the same size");
        }
        double result = 0.0;
        List<Double> pProbs = p.getProbabilities();
        List<Double> qProbs = q.getProbabilities();
        for(int i = 0; i < pProbs.size(); i++) {
            result += MathUtil.logBase2(pProbs.get(i) / qProbs.get(i)) * pProbs.get(i);
        }
        return result;
    }
    
    public static double getKullbackLeiblerDivergence(List p, List q) throws ProbabilityException {
        return getKullbackLeiblerDivergence(new Histogram(p).getProbDist(), new Histogram(q).getProbDist());
    }
}
