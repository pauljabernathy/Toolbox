/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package toolbox.stats;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
import java.util.PriorityQueue;
import java.util.TreeMap;
import java.util.function.Predicate;
import static java.util.stream.Collectors.*;
import toolbox.trees.DuplicateEntryOption;
import toolbox.trees.InsertionResult;
import toolbox.trees.WeightedBinaryTree;

/**
 *
 * @author pabernathy
 */
public class TreeHistogram<T extends Comparable> {
    
    //private TreeMap<T, Integer> data;
    //private TreeMap<Integer, T> counts; 
    /*The idea was to have two parallel trees - one for the data (the main one),
    and a second one for counts, to make retreival/sorting by count faster.  Then I realized this won't
    work because the TreeMap expects one value for each given count, but in the histogram there could be
    many items with the same count.*/
    //TODO:  implement or (preferably) find a balanced tree that will allow adding an arbitrary number of
    //entries for a given count, but still has ~O(logn) time complexity.
    PriorityQueue<HistogramEntry> counts;
    
    //try a BalancedBinaryTree, which was originally made for this purpose
    private WeightedBinaryTree<T> data;
    
    //keep track of size and total count till such time as the WeightedBinaryTree keeps track of it
    private int uniqueEntries;
    private int totalCount;
    
    public enum Sort {
        ITEM, COUNT, UNSORTED;
    }
    
    public TreeHistogram() {
        //this.data = new TreeMap<T, Integer>();
        /*this.counts = new PriorityQueue<HistogramEntry>(new Comparator<HistogramEntry>() {
            public int compare(HistogramEntry left, HistogramEntry right) {
                return left.count.compareTo(right.count);
            }
        });*/
        this.counts = new PriorityQueue<>((HistogramEntry left, HistogramEntry right) -> left.count.compareTo(right.count));
        //this.data = new WeightedBinaryTree<T>(null);
        this.uniqueEntries = 0;
        this.totalCount = 0;
    }
    
    public WeightedBinaryTree<T> insert(T item, int count) {
        this.addToData(item, count);
        return this.data;
    }
    
    protected WeightedBinaryTree<T> addToData(T item, int count) {
        //TODO:  If WeightedBinaryTree is upgraded to be able to handle creation without initializing the root node, put the creation in the constuctor!
        if(data == null) {
            data = new WeightedBinaryTree<T>(item, count);
            this.uniqueEntries = 1;
            this.totalCount = count;
        } else {
            InsertionResult result = data.insert(item, count, DuplicateEntryOption.UPDATE);
            //System.out.println(result.status);
            if(result.status == InsertionResult.Status.CREATED) {
                this.uniqueEntries++;
            }
            //TODO:  something better for UNKNOWN; maybe a variable in this class for if there are any unknowns; unlikely to happen though
            if(result.status != InsertionResult.Status.IGNORED && result.status != InsertionResult.Status.FAILED && result.status != InsertionResult.Status.UNKNOWN) {
                this.totalCount += count;
            }
            data = result.getRoot();    //in case the root node changed
        }
        return data;
    }
    
    protected PriorityQueue<HistogramEntry> addToCounts(T item, int count) {
        
        return counts;
    }
    
    public List<HistogramEntry<T>> getAsList() {
        return this.getAsList(Sort.ITEM);
    }
    
    public List<HistogramEntry<T>> getAsList(Sort sortedBy) {
        LinkedList<WeightedBinaryTree<T>> list = null;
        if(sortedBy == Sort.COUNT) {
            list = data.getAsList(WeightedBinaryTree.SortType.WEIGHT);
        } else {
            list = data.getAsList(WeightedBinaryTree.SortType.NATURAL_ORDER);
        }
        List<HistogramEntry<T>> result = new ArrayList<>();
        list.forEach(tree -> result.add(new HistogramEntry(tree.getKey(), (int)tree.weight)));
        return result;
    }
    
    public int getNumEntries() {
        return this.uniqueEntries;
    }
    
    public int getTotalCount() {
        return this.totalCount;
    }
    
    public List<T> getDataAsList() {
        return null;
    }
    
    protected WeightedBinaryTree<T> getData() {
        return this.data;
    }
    
    protected List<T> queryFromFirst(Predicate<T> p) {
        Predicate<WeightedBinaryTree<T>> p2 = (WeightedBinaryTree<T> t) -> p.test(t.getKey());
        List<WeightedBinaryTree<T>> nodes = this.data.queryFromFirst(p2);
        List<T> result = new ArrayList<>();
        nodes.stream().forEach(n -> result.add(n.getKey()));
        return result;
    }
    
    protected List<HistogramEntry<T>> queryAll(Predicate<T> p) {
        List<T> result = new ArrayList<>();
        List<HistogramEntry<T>> asList = this.getAsList(Sort.ITEM);
        return asList.stream().filter(entry -> p.test(entry.item)).collect(toList());
    }
}
