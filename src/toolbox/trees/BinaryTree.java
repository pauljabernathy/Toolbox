/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package toolbox.trees;

/**
 *
 * @author pabernathy
 */
public class BinaryTree<T extends Comparable> {
    
    private BinaryTreeNode<T> root;
    
    public BinaryTreeNode getRoot() {
        return this.root;
    }
    
    public boolean insert(BinaryTreeNode node) {
        if(node == null) {
            return false;
        }
        
        if(root == null) {
            root = node;
            return true;
        }
            
        return false;
    }
    
    public BinaryTree insert(T value) {
        if(root == null) {
            //root = new Binary
        }
        return this;
    }
    
    public boolean contains(BinaryTreeNode node) {
        return false;
    }
    
    public boolean contains(T value) {
        return false;
    }
}
