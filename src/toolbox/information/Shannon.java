/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package toolbox.information;

import toolbox.stats.*;
import java.util.List;
import java.util.ArrayList;

import org.apache.log4j.Logger;
import org.apache.log4j.Level;
import toolbox.util.ListArrayUtil;

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
     * @param <T>
     * @param left
     * @param right
     * @return 
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
        return HLR / (HL + HR);     //TODO:  deal with case where HL + HR = 0
    }
    
    protected static <T> List<List<T>> getCombinedList(List<T> left, List<T> right) {
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
}
