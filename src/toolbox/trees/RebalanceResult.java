/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package toolbox.trees;

import java.util.LinkedList;

/**
 *
 * @author pabernathy
 */
public class RebalanceResult {
    
    public LinkedList<BalancedBinaryTree> beforePathFromRoot;
    public LinkedList<BalancedBinaryTree> afterPathFromRoot;
    public boolean success;
    
    public RebalanceResult() {
        this.beforePathFromRoot = new LinkedList<>();
        this.afterPathFromRoot = new LinkedList<>();
        this.success = true;
    }
}
