/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package toolbox.stats;

import java.util.TreeMap;
import java.util.PriorityQueue;
import java.util.Comparator;
import java.util.List;


/**
 *
 * @author pabernathy
 */
public class TreeHistogram<T> {
    
    private TreeMap<T, Integer> data;
    //private TreeMap<Integer, T> counts; 
    /*The idea was to have two parallel trees - one for the data (the main one),
    and a second one for counts, to make retreival/sorting by count faster.  Then I realized this won't
    work because the TreeMap expects one value for each given count, but in the histogram there could be
    many items with the same count.*/
    //TODO:  implement or (preferably) find a balanced tree that will allow adding an arbitrary number of
    //entries for a given count, but still has ~O(logn) time complexity.
    PriorityQueue<HistogramEntry> counts;
    
    public enum Sort {
        ITEM, COUNT, UNSORTED;
    }
    
    public TreeHistogram() {
        this.data = new TreeMap<T, Integer>();
        /*this.counts = new PriorityQueue<HistogramEntry>(new Comparator<HistogramEntry>() {
            public int compare(HistogramEntry left, HistogramEntry right) {
                return left.count.compareTo(right.count);
            }
        });*/
        this.counts = new PriorityQueue<>((HistogramEntry left, HistogramEntry right) -> left.count.compareTo(right.count));
    }
    
    protected TreeMap<T, Integer> addToData(T item, int count) {
        Integer currentCount = data.get(item);
        if(currentCount != null) {
            int newCount = currentCount + count;
            data.replace(item, currentCount, newCount);
        } else {
            data.put(item, count);
        }
        return data;
    }
    
    protected PriorityQueue<HistogramEntry> addToCounts(T item, int count) {
        
        return counts;
    }
    
    public TreeHistogram<T> add(T item, int count) {
        this.addToData(item, count);
        return this;
    }
    
    public int getCount(T item) {
        return 0;
    }
    
    public List<HistogramEntry> getAsList() {
        return this.getAsList(Sort.ITEM);
    }
    
    public List<HistogramEntry> getAsList(Sort sortedBy) {
        return null;
    }
    
    public int size() {
        return data.size();
    }
    
    public List<T> getDataAsList() {
        return null;
    }
}
