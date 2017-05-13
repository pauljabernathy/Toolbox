/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package toolbox.stats;
import toolbox.stats.TreeHistogram;
import toolbox.stats.HistogramEntry;
import toolbox.trees.WeightedBinaryTree;
import toolbox.trees.WeightComparator;
import toolbox.stats.Sort;
import java.util.List;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.PriorityQueue;

/**
 *
 * @author paul
 */
public class StringHistogram {
    
    public enum MatchType { STARTS_WITH, /*CONTAINS,*/ EQUALS };
            
    private TreeHistogram<String> histogram;
    
    public StringHistogram() {
        this.histogram = new TreeHistogram<>();
    }
    
    public void insert(String word) {
        this.insert(word, 1);
    }
    
    public void insert(String word, int count) {
        this.histogram.insert(word, count);
    }
    
    public List<HistogramEntry<String>> query(String match, MatchType matchType) {
        List<HistogramEntry<String>> results = new ArrayList<>();
        WeightedBinaryTree<String> tree = this.findFirst(match);
        if(tree == null) {
            return results;
        }
        
        //List<WeightedBinaryTree<String>> nodes = tree.getAsList(WeightedBinaryTree.SortType.WEIGHT);
        //nodes.stream().forEach(node -> results.insert(new HistogramEntry<String>(node.getKey(), (int)node.getWeight())));
        LinkedList<WeightedBinaryTree<String>> nodes = new LinkedList<>();
        PriorityQueue<WeightedBinaryTree<String>> pq = new PriorityQueue<>(new WeightComparator());
        results.add(new HistogramEntry<String>(tree.getKey(), (int)tree.getWeight()));
        if(tree.getLeftChild() != null && this.isMatch(match, tree.getLeftChild())) {
            pq.add(tree.getLeftChild());
        }
        if(tree.getRightChild() != null && this.isMatch(match, tree.getRightChild())) {
            pq.add(tree.getRightChild());
        }
        WeightedBinaryTree<String> next = null;
        while(pq.peek() != null) {
            next = pq.poll();
            results.add(new HistogramEntry<String>(next.getKey(), (int)next.getWeight()));
            if(next.getLeftChild() != null && this.isMatch(match, next.getLeftChild())) {
                pq.add(next.getLeftChild());
            }
            if(next.getRightChild() != null && this.isMatch(match, next.getRightChild())) {
                pq.add(next.getRightChild());
            }
        }
        return results;
    }
    
    /*private LinkedList<WeightedBinaryTree<T>> getAsListBreadthFirst(PriorityQueue<WeightedBinaryTree> pq) {
        LinkedList<WeightedBinaryTree<T>> result = new LinkedList<>();
        if(pq == null) {
            pq = new PriorityQueue(new WeightComparator());
        }
        result.insert(this);
        if(this.left != null) {
            pq.insert(this.left);
        }
        if(this.right != null) {
            pq.insert(this.right);
        }
        WeightedBinaryTree<T> next = null;
        while(pq.peek() != null) {
            next = pq.poll();
            result.insert(next);
            if(next.getLeftChild() != null) {
                pq.insert(next.getLeftChild());
            }
            if(next.getRightChild() != null) {
                pq.insert(next.getRightChild());
            }
        }
        return result;
    }*/
    
    private boolean isMatch(String value, WeightedBinaryTree<String> node, MatchType matchType) {
        /*if(matchType == MatchType.STARTS_WITH) {
            return node.getKey() != null && node.getKey().startsWith(value);
        } else if(matchType == MatchType.CONTAINS) {
            return node.getKey() != null && node.getKey().contains(value);
        } else if(matchType == MatchType.EQUALS) {
            return node.getKey() != null && node.getKey().endsWith(value);
        }*/
        switch(matchType) {
            case STARTS_WITH:
                return node.getKey() != null && node.getKey().startsWith(value);
            /*case CONTAINS:
                return node.getKey() != null && node.getKey().contains(value);*/
            case EQUALS:
                return node.getKey() != null && node.getKey().equals(value);
            default:
                return false;
        }
    }
    
    private boolean isMatch(String value, WeightedBinaryTree<String> node) {
        return node.getKey() != null && node.getKey().startsWith(value);
    }
    
    protected WeightedBinaryTree<String> findFirst(String value) {
        return this.findFirst(value, histogram.getData());
    }
    
    protected WeightedBinaryTree<String> findFirst(String value, WeightedBinaryTree<String> tree) {
        if(!this.isMatch(value, tree)) {
            if(value.compareTo(tree.getKey()) < 0) {
                //TODO:  ternary statement?
                if(tree.getLeftChild() != null) {
                    return findFirst(value, tree.getLeftChild()); 
                } else {
                    return new WeightedBinaryTree<>("");
                }
            } else {
                if(tree.getRightChild() != null) {
                    return findFirst(value, tree.getRightChild());
                } else {
                    return new WeightedBinaryTree<>("");
                }
            }
        } else {
            return tree;
        }
    }
    
     public List<HistogramEntry<String>> getAsList() {
        return this.getAsList(Sort.ITEM);
    }
    
    public List<HistogramEntry<String>> getAsList(Sort sortedBy) {
        return this.histogram.getAsList(TreeHistogram.Sort.ITEM);
    }
    
    public void display() {
        if(histogram.getData() != null) {
            histogram.getData().display();
        }
    }
}
