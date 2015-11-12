/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package toolbox.stats;
import toolbox.util.MathUtil;
import java.util.*;
import java.math.BigDecimal;
import java.math.MathContext;

import java.util.stream.Collectors;

/**
 *
 * @author paul
 */
public class ProbDist<T> {
    private ArrayList<T> values;
    private ArrayList<Double> probabilities;
    private ArrayList<Double> cumProbs;     //cumulative probabilities
    private final T UNKNOWN = null;
    private static final double MIN_UNKNOWN = 0.0001;
    private static final double DEFAULT_PRECISION = 0.0001;
    private double precision = DEFAULT_PRECISION;
    private String label;
    
    public ProbDist() {
        this.reset();
        this.label = "";
    }

    public void reset() {
        try {
            ArrayList<T> values = new ArrayList<T>();
            values.add(UNKNOWN);
            ArrayList<Double> probs = new ArrayList<Double>();
            probs.add(1.0);
            cumProbs = new ArrayList<Double>();
            this.setValuesAndProbabilities(values, probs);
        } catch(ProbabilityException e) {
            System.err.println(e.getClass() + " in reset():  " + e.getMessage());
        }
    }

    public List<T> getValues() {
        return values;
    }

    public T getValue(int index) {
        if(this.getValues() == null || index < 0 || this.getValues().size() <= index) {
            return null;
        } else if(this.contains(UNKNOWN) && this.getValues().size() == 1) {
            return null;
        } else if(this.contains(UNKNOWN) && this.getValues().size() > 1) {
            int newIndex = index + 1;
            if(newIndex >= this.getValues().size()) {
                return null;
            } else {
                return this.getValues().get(newIndex);
            }
        } else {
            return this.getValues().get(index);
        }
    }
    
    public void setValues(List<T> values) {
        this.values = (ArrayList<T>)values;
    }

    //TODO:  check for probabilites of 0 since add() will not allow a 0 probability, or allow zero probabilites (but make sure to change getEntropy to ignore them)
    public ProbDist<T> setValuesAndProbabilities(List<T> values, List<Double> probs) throws ProbabilityException {
        if(values == null) {
            throw new ProbabilityException("values must be non null");
        } else if(values.isEmpty()) {
            throw new ProbabilityException("values must be non empty");
        } else if(probs == null) {
            throw new ProbabilityException("probabilities must be non null");
        } else if(probs.isEmpty()) {
            throw new ProbabilityException("probabilities must be non empty");
        }
        /*if(values == null || values.size() < 1 || probs == null || probs.size() < 1 || values.size() != probs.size()) {
            throw new ProbabilityException("values and probabilities must be non null, non empty, and of the same length");
        } */else {
            this.setValues(values);
            this.setProbabilities(probs);
        }
        return this;
    }
    
    //TODO:  should probably throw a ProbabilityException for bad inputs
    public boolean add(T value, double probability) {
        //TODO:  value.equals("") does not work with generics
        if(value == null || value.equals("") || probability <= 0.0 || probability >= 1.0) {
            return false;
        }
        //TODO:  if 0 probabilities are ever allowed, change Histogram.getProbDist() to not remove them
        if(this.contains(value)) {
            return false;
        }
        
        //see if there is any unknown part left
        int unknownIndex = this.getValues().indexOf(UNKNOWN);
        double unknownProb = 0.0;
        if(unknownIndex > -1 && unknownIndex < this.getProbabilities().size()) {
            unknownProb = this.getProbabilities().get(unknownIndex);
        }
        
        //see if there is still space
        if(probability > unknownProb + MIN_UNKNOWN) {
            return false;
        }
        
        //if any unkown, subtract from unknown's prob
        if(-1 < unknownIndex && unknownIndex < this.getProbabilities().size()) {
            unknownProb -= probability;
            if(unknownProb < MIN_UNKNOWN) {
                this.getProbabilities().remove(unknownIndex);
                this.getValues().remove(unknownIndex);
            } else {
                this.getProbabilities().set(unknownIndex, unknownProb);
            }
        }
        
        //now it is safe to add it
        //TODO:  some kind of synchronization?
        this.getValues().add(value);
        this.getProbabilities().add(probability);
        //find out if we just added the first non UNKNOWN element
        if(this.probabilities.size() == 2 && this.getValues().indexOf(UNKNOWN) > -1) {
            this.cumProbs = new ArrayList<Double>();
            this.cumProbs.add(probability);
        } else {
            this.cumProbs.add(probability + this.cumProbs.get(this.cumProbs.size() - 1));
        }
        return true;
    }
    
    public static boolean validateNormalized(List<Double> probs) {
        if(probs == null) {
            return false;
        }
        double total = 0.0;
        for(int i = 0; i < probs.size(); i++) {
            BigDecimal current = new BigDecimal(probs.get(i), new MathContext(2));
            total += current.doubleValue();
        }
        if(total == 1.0) {
            return true;
        } else {
            return false;
        }
    }
    
    //TODO:  check for normalized before getting a random value?  what to do if not normalized?
    public T getRandomValue() {
        if(this.getProbabilities() == null || this.getProbabilities().size() == 0 || this.getValues() == null || this.getValues().size() == 0) {
            return null;
        }
        double rand = Math.random();
        double sum = 0.0;
        int index = 0;
        while(index < this.cumProbs.size() && this.cumProbs.get(index) <= rand) {
            index++;
        }
        return this.getValues().get(index);
    }
    //TODO:  try a binary search tree
    
    /** This is this original version but is much slower that the new one in the majority of tests I have run.
     * @deprecated 
     * @return a random value
     */
    public T getRandomValue2() {
        double rand = Math.random();
        double sum = 0.0;
        int index = 0;
        if(this.getProbabilities() == null || this.getProbabilities().size() == 0 || this.getValues() == null || this.getValues().size() == 0) {
            return null;
        }
        while(sum < rand) {
            sum += this.getProbabilities().get(index);
            if(sum < rand) {
                index++;
            }
        }
        return this.getValues().get(index);
    }
    
    
    public List<Double> getProbabilities() {
        return probabilities;
    }

    //TODO:  check for normalized
    public void setProbabilities(List<Double> probabilities) throws ProbabilityException {
        if(probabilities == null || probabilities.size() == 0 || probabilities.size() != this.getValues().size()) {
            throw new ProbabilityException("The number of probabilities must match the number of values.");
        }
        this.probabilities = (ArrayList<Double>)probabilities;
        this.cumProbs = (ArrayList<Double>)getCumProbsFromProbs(this.probabilities);
    }
    
    public List<Double> getCumProbs() {
        return this.cumProbs;
    }
    
    public static List<Double> getCumProbsFromProbs(List<Double> probabilities) throws ProbabilityException {
        if(probabilities == null || probabilities.size() == 0) {
            throw new ProbabilityException("The number of probabilities must match the number of values.");
        }
        return MathUtil.cumsumList(probabilities);
    }
    
    public boolean contains(T value) {
        return this.getValues().contains(value);
    }
    //TODO:  class cast exception?
    public double probatilityOf(Object obj) {
        if(!contains((T)obj)) {
            return 0.0;
        } else {
            return this.getProbabilities().get(this.getValues().indexOf(obj));
        }
    }
    
    //TODO:  Check that the values in "values" are unique.  Also I'm not sure about the assert line...
    //might should just through an exception for bad input.
    public static <E> ProbDist<E> createInstanceFromCounts(List<E> values, List<Integer> counts) {
        if(values == null || values.isEmpty() || counts == null || counts.isEmpty() || values.size() != counts.size()) {
            return new ProbDist<E>();
        }
        assert(values.size() == counts.size()):"sizes don't match up";
        int sum = 0;
        for(int i = 0; i < counts.size(); i++) {
            sum += counts.get(i);
        }
        ArrayList<Double> percents = new ArrayList<Double>();
        for(int i = 0; i < counts.size(); i++) {
            percents.add(counts.get(i) / (double)sum);
        }
        
        ProbDist result = new ProbDist();
        //result.setValues(values);
        //result.setProbabilities(percents);
        try {
            result.setValuesAndProbabilities(values, percents);
        } catch(ProbabilityException e) {
            //should not get here since values and counts are already checked
            System.err.println(e.getClass() + " in createInstanceFromCounts():  " + e.getMessage());
        }
        return result;
    }
    
    /**
     * 
     * @param probThreshold the threshold probability; for example, if you want the top 25%, this would be .25
     * @param roundDown whether or not you should round down since it is likely that there will not be an exact match for the probability you specify; 
     * for example, if you want the top 50% and you can break the list into the top 48.5% or the top 53%, roundDown = true would give you the top 48.5%
     * @return a List of ProbDists
     */
    public List<ProbDist<T>> split(double probThreshold, boolean roundDown) throws ProbabilityException {
        List<T> sortedValues = this.values.stream().sorted((T a, T b) -> 
                this.probabilities.get(this.values.indexOf(b)).compareTo(this.probabilities.get(this.values.indexOf(a))))
                .collect(Collectors.toList());
        List<Double> sortedProbs = this.probabilities.stream().sorted((Double a, Double b) -> b.compareTo(a)).collect(Collectors.toList());
        List<Double> cumProbs = MathUtil.cumsumList(sortedProbs);
        
        List<ProbDist<T>> result = new ArrayList<>();
        //find the place where the top x places ends
        int index = 0;
        int i = -1;
        boolean foundExactMatch = false;
        while(i + 1 < cumProbs.size() && cumProbs.get(i + 1) < probThreshold - precision) {
            //if(cumProbs.get(i) == probThreshold) {
            //    foundExactMatch = true;
            //}
            i++;
        }
        System.out.println(cumProbs);
        //If we were looking for an exact match, the next one will be that match.  Just check that we aren't at the end of the list.
        System.out.println(cumProbs.get(i + 1) + precision);
        System.out.println(cumProbs.get(i + 1) - precision);
        if(i < cumProbs.size() -1 && (cumProbs.get(i + 1) + precision >= probThreshold && cumProbs.get(i + 1) - precision <= probThreshold)) {
            index = i + 1;
        }
        else if(roundDown) {
            //no exact match, and using the lower number
            index = i;
            //looking for a percentage so small that it does not exist in this distribution
            if(index < 0) {
                result.add(new ProbDist<T>());
                result.add(new ProbDist<T>().setValuesAndProbabilities(sortedValues, sortedProbs));
                return result;
            }
        } else {
            //no exact match, using the higher number
            index = i + 1;
        }
        
        List<Double> topProbs = new ArrayList<>();
        List<T> topValues = new ArrayList<>();
        List<Double> bottomProbs = new ArrayList<>();
        List<T> bottomValues = new ArrayList<>();
        for(int j = 0; j <= index; j++) {
            topProbs.add(sortedProbs.get(j));
            topValues.add(sortedValues.get(j));
        }
        for(int k = index + 1; k < this.values.size(); k++) {
            bottomProbs.add(sortedProbs.get(k));
            bottomValues.add(sortedValues.get(k));
        }
        result.add(new ProbDist<T>().setValuesAndProbabilities(topValues, topProbs));
        result.add(new ProbDist<T>().setValuesAndProbabilities(bottomValues, bottomProbs));
        return result;
    }
    
    public static ProbDist<List> getJointDistribution(ProbDist left, ProbDist right) {
        
        //find all combinations
        //get probs for each combination
        //put combo and prob into result
        ProbDist<List> result = new ProbDist<List>();
        for(int i = 0; i < left.getValues().size(); i++) {
            for(int j = 0; j < right.getValues().size(); j++) {
                List values = new ArrayList();
                values.add(left.getValue(i));
                values.add(right.getValue(j));
                result.add(values, (double)left.getProbabilities().get(i) * (double)right.getProbabilities().get(j));
            }
        }
        return result;
    }
    
    public double getEntropy() {//throws ProbabilityException {
        return this.calculateEntropy();
    }
    
    //TODO:  return NAN for null probabilities
    //TODO:  move this calculation to Shannon class?
    private double calculateEntropy() {//throws ProbabilityException {
        if(this.probabilities == null) {
            return 0.0;
        }
        if(!validateNormalized(this.probabilities)) {
            //throw new ProbabilityException("Cannot calculate the entropy of a non normalized probability distribution.");
        }
        double H = 0.0;
        for(Double p : this.probabilities) {
            H -= p * MathUtil.logBase2(p);
        }
        return H;
    }
    
    /**
     * This function as it currently is is worthless because it calculate the joint distribution under the assumption that the two
     * variables are independent, meaning mutual information will always be 0.  We need to get a joint distribution empirically
     * (or some means other than the getJointDistribution() function) before the mutual information will mean anything.
     * @param x one probability distribution
     * @param y the other probability distribution
     * @return  the mutual information
     */
    public static double getMutualInformation(ProbDist x, ProbDist y) {
        return x.getEntropy() + y.getEntropy() - ProbDist.getJointDistribution(x, y).getEntropy();
    }
    
    public boolean isEmpty() {
        if(this.values == null || this.values.isEmpty() || (this.values.size() == 1 && this.values.get(0) == null)) {
            return true;
        } else {
            return false;
        }
    }
    
    public String getLabel() {
        return this.label;
    }
    
    public void setLabel(String label) {
        this.label = label;
    }
    
    public String toString() {
        StringBuilder sb = new StringBuilder();
        double total = 0.0;
        for(int i = 0; i < this.getProbabilities().size(); i++) {
            sb.append(this.getValues().get(i)).append(" ").append(this.getProbabilities().get(i)).append(";  ");
            sb.append("\n");
            total += this.getProbabilities().get(i);
        }
        //sb.append("total = " + total);
        return sb.toString();
    }
    
    public String asXHTMLTable() {
        StringBuilder sb = new StringBuilder();
        sb.append("<table border=1>");
        if(this.label != null && !this.label.equals("")) {
            sb.append("<tr><td colspan = 3 align=center><b>").append(this.label).append("</b></td></tr>");
        }
        for(int i = 0; i < this.getValues().size(); i++) {
            if(this.values.get(i) == null && this.probabilities.get(i) < MIN_UNKNOWN) {
                //don't show the "rounding error"
                //continue;
            }
            sb.append("<tr><td>").append(this.values.get(i)).append("</td><td>").append(this.probabilities.get(i)).append("</td></tr>");
        }
        sb.append("<tr><td>").append("<a href=\"http://en.wikipedia.org/wiki/Entropy#Information_theory\"  target=\"_\">Entropy</a>").append("</td><td>").append(this.getEntropy()).append("</td></tr>");
        sb.append("</table>");
        return sb.toString();
    }
    
    /**
     * @deprecated
     */
    public void display() {
        System.out.println(this.toString());
    }
}
