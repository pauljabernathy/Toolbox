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
import toolbox.Utilities;

/**
 *
 * @author paul
 */
public class Shannon {
    
    private static Logger logger;
    
    static {
        logger = Utilities.getLogger(Shannon.class, Level.INFO);
    }
    
    public static double getEntropy(double[] input) {
        DataList<Double> list = new DataList<Double>();
        for(double num : input) {
            list.add(num);
        }
        return list.getEntropy();
    }
    
    public static double getEntropy(List<Double> input) {
        return new Histogram(input).getEntropy();
    }
    
    public static double getMutualInformation(List<Double> left, List<Double> right) {
        if(left == null || right == null || left.size() != right.size()) {
            return -1.0;
        }
        
        List<List<Double>> combined = getCombinedList(left, right);
        double HL = new Histogram(left).getEntropy();
        logger.debug("HL = " + HL);
        double HR = new Histogram(right).getEntropy();
        logger.debug("HR = " + HR);
        double HLR = new Histogram(combined).getEntropy();
        //new Histogram(combined).display();
        logger.debug("HLR = " + HLR);
        return HL + HR - HLR;
    }
    
    protected static List<List<Double>> getCombinedList(List<Double> left, List<Double> right) {
        if(left == null || right == null || left.size() != right.size()) {
            return new ArrayList<List<Double>>();
        }
        ArrayList<List<Double>> combined = new ArrayList<List<Double>>();
        ArrayList<Double> current = new ArrayList<Double>();
        for(int i = 0; i < left.size(); i++) {
            current = new ArrayList<Double>();
            current.add(left.get(i));
            current.add(right.get(i));
            combined.add(current);
        }
        
        return combined;
    }
}
