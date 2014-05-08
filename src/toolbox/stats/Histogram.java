/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package toolbox.stats;

import java.util.List;
import java.util.ArrayList;
//TODO:  Should this use a generic or can we get away without using it?
/**
 *
 * @author paul
 */
public class Histogram {
    private ArrayList values;
    private ArrayList<Integer> counts;
    
    private String label;
    
    //percents and sum are technically redundant but here to make returning them faster than calculating them from counts each time
    private ArrayList<Double> probabilities;
    private int sum;
    
    public Histogram() {
        this.values = new ArrayList();
        this.counts = new ArrayList<Integer>();
        label = "";
    }
    
    public <T> Histogram(T[] data) {
        this();
        this.setDataList(data);
    }
    
    public Histogram(DataList data) {
        this.setDataList(data);
    }
    
    public Histogram(List data) {
        this.setDataList(data);
    }
    
    public <T> Histogram(T[] values, int[] counts) throws ProbabilityException {
        if(values == null || values.length == 0 || counts == null || counts.length == 0) {
            throw new ProbabilityException("Histogram requires non empty values and counts");
        } else if(counts.length != values.length) {
            throw new ProbabilityException("length of values and counts must match");
        }
        
        this.values = new ArrayList();
        this.counts = new ArrayList<Integer>();
        for(T value : values) {
            this.values.add(value);
        }
        for(int count : counts) {
            this.counts.add(count);
        }
        this.updatePercents();
    }
    
    public <T> void setDataList(T[] data) {
        if(data == null) {
            return;//?
        }
        this.values = new ArrayList<T>();
        this.counts = new ArrayList<Integer>();
        for(int i = 0; i < data.length; i++) {
            if(values.contains(data[i])) {
                int index = values.indexOf(data[i]);
                counts.set(index, counts.get(index) + 1);
            } else {
                values.add(data[i]);
                counts.add(1);
            }
        }
        this.updatePercents();
    }
    
    public <T> void setDataList(DataList<T> data) {
        if(data == null) {
            return;//?
        }
        this.values = new ArrayList<T>();
        this.counts = new ArrayList<Integer>();
        for(int i = 0; i < data.size(); i++) {
            if(values.contains(data.get(i))) {
                int index = values.indexOf(data.get(i));
                counts.set(index, counts.get(index) + 1);
            } else {
                values.add(data.get(i));
                counts.add(1);
            }
        }
        this.updatePercents();
    }
    
    public <T> void setDataList(List<T> data) {
        if(data == null) {
            return;//?
        }
        this.values = new ArrayList<T>();
        this.counts = new ArrayList<Integer>();
        for(int i = 0; i < data.size(); i++) {
            if(values.contains(data.get(i))) {
                int index = values.indexOf(data.get(i));
                counts.set(index, counts.get(index) + 1);
            } else {
                values.add(data.get(i));
                counts.add(1);
            }
        }
        this.updatePercents();
    }
    
    public String getLabel() {
        return this.label;
    }
    
    public void setLabel(String label) {
        this.label = label;
    }
    
    private void updateSum() {
        for(int i = 0; i < this.counts.size(); i++) {
            sum += this.counts.get(i);
        }
        this.sum = sum;
    }
    
    private void updatePercents() {
        this.probabilities = new ArrayList<Double>();
        int sum = 0;
        this.updateSum();
        for(int i = 0; i < this.counts.size(); i++) {
            this.probabilities.add((double)this.counts.get(i) / (double) this.sum);
        }
    }
    
    /**
     * gets the probability distribution; any elements with a count of 0 are omitted 
     * because this causes getEntropy() to return NaN (ProbDist is not designed to have probabilities of 0)
     */
    public ProbDist getProbDist() {
        ProbDist result = new ProbDist();
        if(!this.probabilities.contains(0.0)) {
            try {
                result.setValuesAndProbabilities(this.values, this.probabilities);
            } catch(ProbabilityException e) {
                System.err.println(e.getClass() + " in Histogram.getProbDist():  " + e.getMessage());
            }
            return result;
        } else {
            List v = new ArrayList();
            List<Integer> c = new ArrayList<Integer>();
            List<Double> p = new ArrayList<Double>();
            for(int i = 0; i < this.counts.size(); i++) {
                Integer count = this.counts.get(i);
                if(count != 0) {
                    c.add(count);
                    v.add(this.values.get(i));
                    p.add((double)count / this.sum);
                }
            }
            try {
                result.setValuesAndProbabilities(v, p);
            } catch(ProbabilityException e) {
                System.err.println(e.getClass() + " in Histogram.getProbDist():  " + e.getMessage());
            }
            return result;
        }
    }
    
    public List getValues() {
        return this.values;
    }
    
    public List<Integer> getCounts() {
        return this.counts;
    }
    
    public List<Double> getProbabilities() {
        return this.probabilities;
    }
    
    public int size() {
        return this.values.size();
    }
    
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for(int i = 0; i < this.getCounts().size(); i++) {
            sb.append(this.values.get(i)).append("  ").append(this.counts.get(i)).append("  ").append(this.probabilities.get(i));
            sb.append("\n");
        }
        //return sb.toString();*/
        return this.toString("\n");
    }
    
    public String toString(String endLine) {
        StringBuilder sb = new StringBuilder();
        if(this.label != null && !this.label.equals("")) {
            sb.append(this.label).append(endLine);
        }
        for(int i = 0; i < this.getCounts().size(); i++) {
            sb.append(this.values.get(i)).append("  ").append(this.counts.get(i)).append("  ").append(this.probabilities.get(i));
            sb.append(endLine);
        }
        sb.append("Total").append("  ").append(this.sum).append("  ");
        double d = 0.0;
        for(int i = 0; i < this.probabilities.size(); i++) {
            d += this.probabilities.get(i);
        }
        sb.append(d);
        sb.append(endLine).append("Entropy = ").append(this.getEntropy());
        return sb.toString();
    }
    
    public void display() {
        System.out.println(this.toString());
    }
    
    public double getEntropy() {
        return this.getProbDist().getEntropy();
    }
}
