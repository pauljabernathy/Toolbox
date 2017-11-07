/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package toolbox.stats;

/**
 *
 * @author pabernathy
 */
public class HistogramEntry<T> {
    public T item;
    public int count;
    
    public HistogramEntry(T item, Integer count) {
        this.item = item;
        this.count = count;
    }
    
    public String toString() {
        return item.toString() + " " + count;
    }
}
