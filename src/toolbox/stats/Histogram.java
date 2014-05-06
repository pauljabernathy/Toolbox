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
    
    //percents and sum are technically redundant but here to make returning them faster than calculating them from counts each time
    private ArrayList<Double> probabilities;
    private int sum;
    
    public Histogram() {
        this.values = new ArrayList();
        this.counts = new ArrayList<Integer>();
    }
    
    public Histogram(DataList data) {
        this.setDataList(data);
    }
    
    public Histogram(List data) {
        this.setDataList(data);
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
    
    public ProbDist getProbDist() {
        ProbDist result = new ProbDist();
        //result.setValues(this.values);
        //result.setProbabilities(this.probabilities);
        try {
            result.setValuesAndProbabilities(this.values, this.probabilities);
        } catch(ProbabilityException e) {
            System.err.println(e.getClass() + " in Histogram.getProbDist():  " + e.getMessage());
        }
        return result;
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
        for(int i = 0; i < this.getCounts().size(); i++) {
            sb.append(this.values.get(i)).append("  ").append(this.counts.get(i)).append("  ").append(this.probabilities.get(i));
            sb.append(endLine);
        }
        return sb.toString();
    }
    
    public void display() {
        System.out.println(this.toString());
    }
    
    public double getEntropy() {
        return this.getProbDist().getEntropy();
    }
}
