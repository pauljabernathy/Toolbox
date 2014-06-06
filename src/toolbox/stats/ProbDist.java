/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package toolbox.stats;
import java.util.*;
import java.math.BigDecimal;
import java.math.MathContext;

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
    public void setValuesAndProbabilities(List<T> values, List<Double> probs) throws ProbabilityException {
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
    }
    
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
        //System.out.println("unknownProb = " + unknownProb);
        //see if there is still space
        if(probability > unknownProb + MIN_UNKNOWN) {
            System.out.println(probability + " > " + unknownProb + " + " + MIN_UNKNOWN + "; returning false");
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
        if(this.cumProbs.size() > 0) {
            this.cumProbs.add(probability + this.cumProbs.get(this.cumProbs.size() - 1));
        } else {
            this.cumProbs.add(probability);
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
    //TODO:  faster method of selection; test showed this one is very slow
    public T getRandomValue() {
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
    
    public T getRandomValue2() {
        if(this.getProbabilities() == null || this.getProbabilities().size() == 0 || this.getValues() == null || this.getValues().size() == 0) {
            return null;
        }
        double rand = Math.random();
        double sum = 0.0;
        int index = 0;
        while(index < this.cumProbs.size() && rand < this.cumProbs.get(index)) {
            index++;
        }
        return this.getValues().get(index);
    }
    //TODO:  try a binary search tree
    
    
    public List<Double> getProbabilities() {
        return probabilities;
    }

    public void setProbabilities(List<Double> probabilities) throws ProbabilityException {
        if(probabilities == null || probabilities.size() == 0 || probabilities.size() != this.getValues().size()) {
            throw new ProbabilityException("The number of probabilities must match the number of values.");
        }
        this.probabilities = (ArrayList)probabilities;
    }
    
    public List<Double> getCumProbs() {
        return this.cumProbs;
    }
    
    public static List<Double> getCumProbsFromProbs(List<Double> probabilities) throws ProbabilityException {
        if(probabilities == null || probabilities.size() == 0) {
            throw new ProbabilityException("The number of probabilities must match the number of values.");
        }
        return Utilities.cumsumList(probabilities);
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
    
    private double calculateEntropy() {//throws ProbabilityException {
        if(this.probabilities == null) {
            return 0.0;
        }
        if(!validateNormalized(this.probabilities)) {
            //throw new ProbabilityException("Cannot calculate the entropy of a non normalized probability distribution.");
        }
        double H = 0.0;
        for(Double p : this.probabilities) {
            H -= p * toolbox.Utilities.logBase2(p);
        }
        return H;
    }
    
    /**
     * This function as it currently is is worthless because it calculate the joint distribution under the assumption that the two
     * variables are independent, meaning mutual information will always be 0.  We need to get a joint distribution empirically
     * (or some means other than the getJointDistribution() function) before the mutual information will mean anything.
     * @param x
     * @param y
     * @return 
     */
    public static double getMutualInformation(ProbDist x, ProbDist y) {
        return x.getEntropy() + y.getEntropy() - ProbDist.getJointDistribution(x, y).getEntropy();
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
