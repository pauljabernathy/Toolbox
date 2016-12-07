/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package toolbox.trees;
import java.util.Comparator;

/**
 *
 * @author pabernathy
 */
public class WeightComparator implements Comparator {
    public int compare(WeightedBinaryTree left, WeightedBinaryTree right) {
        //return (int)(right.weight - left.weight);  won't quite work because weight is a double, not and int, and if the difference is between -1.0 and 1.0 but not 0.0, it will be changed to 0 when it could be something like .03
        double diff = right.weight - left.weight;
        if(Math.abs(diff) > 1.0 || diff == 0.0) {
            return (int)diff;
        } else {
            if(diff < 0) {
                return -1;
            } else if(diff > 0) {
                return 1;
            } else {
                return 0; //should not get here, the the compiler wanted something here or at the bottom
            }
        }
        //return 0;   //should not get here, the the compiler wanted something here
    }
    
    public int compare(Object left, Object right) {
        if(left instanceof WeightedBinaryTree && right instanceof WeightedBinaryTree) {
            return compare((WeightedBinaryTree)left, (WeightedBinaryTree)right);
        } else {
            return 0;
        }
    }
}
