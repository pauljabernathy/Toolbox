/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package toolbox.stats;

import java.util.List;
import java.util.ArrayList;

//TODO:  remove(), elementAt()?, other?

/**
 *
 * @author paul
 */
public class DataList <T> {
    
    private ArrayList<T> values;
    //private ArrayList<Integer> counts;
    
    public DataList() {
        this.values = new ArrayList<T>();
    }
    
    public DataList<T> add(T value) {
        values.add(value);
        return this;
    }
    
    public Histogram getHistogram() {
        return new Histogram(this);
    }
    
    public int size() {
        return values.size();
    }
    
    public T get(int index) {
        return values.get(index);
    }
    
    public List<T> getData() {
        return this.values;
    }
    
    public void print() {
        if(this.values == null) {
            return;
        }
        for(int i = 0; i < this.values.size() - 1; i++) {
            System.out.print(this.values.get(i) + " ");
        }
        System.out.println(this.values.get(this.values.size() - 1));
    }
    
    public double getEntropy() {
        return this.getHistogram().getEntropy();
    }
}
