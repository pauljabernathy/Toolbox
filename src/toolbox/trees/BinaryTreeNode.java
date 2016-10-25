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
public class BinaryTreeNode<T extends Comparable> {
    
    public T value;
    public BinaryTreeNode parent;
    public BinaryTreeNode left;
    public BinaryTreeNode right;
    
    public BinaryTreeNode(T value) {
        /*this.value = value;
        this.left = null;
        this.right = null;*/
        this(value, null, null);
    }
    
    public BinaryTreeNode(T value, BinaryTreeNode<T> parent) {
        this(value, parent, null, null);
    }
    
    //TODO:  Consider if this should know about the parent or not.
    public BinaryTreeNode(T value, BinaryTreeNode<T> left, BinaryTreeNode<T> right) {
        /*this.value = value;
        this.left = left;
        this.right = right;*/
        this(value, null, left, right);
    }
    
    public BinaryTreeNode(T value, BinaryTreeNode<T> parent, BinaryTreeNode<T> left, BinaryTreeNode<T> right) {
        this.value = value;
        this.parent = parent;
        this.left = left;
        this.right = right;
    }
    
    /**
     * tests if the values inside the nodes are equal but does not test the child nodes because that could take a long time for a large tree
     * @param node
     * @return 
     */
    public boolean valueEquals(BinaryTreeNode<T> node) {
        if(this.value != null) {
            if(node != null) {
                return this.value.equals(node.value);
            } else {
                return false;
            }
        } else if (node != null) {
            //this's value is null
            if(node.value == null) {
                //other node's value is null so return true
                return true;
            } else {
                //other node's value is not null; null and not null are not equals
                return false;
            }
        } else {
            //could go either way here but if the other node is null, returning true could be a little deceptive
            return false;
        }
        //value null        node null       --not relevant
        //value null        node not null   node value null
        //value null        node not null   node value not null
        //value not null    node null       --not relevent
        //value not null    node not null   node value null
        //value not null    node not null   node value not null
    }
    
    /**
     * tests if the value in this node equals the given object, using the .equals() method of class T
     * @param otherValue
     * @return 
     */
    public boolean valueEquals(T otherValue) {
        if(this.value == null) {
            return otherValue == null;
        } else {
            return this.value.equals(otherValue);
        }
    }
    
    public boolean contains(T value) {
        return false;
    }
    
    public BinaryTreeNode<T> get(T value) {
        if(value == null && this.value != null) {
            return null;
        } else if(value == null && this.value == null) {
            return this;
        }
        int compare = value.compareTo(this.value);
        if(compare < 0) {
            return this.left != null ? left.get(value) : null;
        } else if(compare > 0) {
            return this.right != null ? right.get(value) : null;
        } else {
            return this.valueEquals(value) ? this : null;
        }
    }
    
    //TODO: get with a functional interface, more of a query;
    //converting to a list to do stream().filter would be nlogn, right?  A simple query to go get a list of nodes that match
    //not a high priority
    
    //TODO:  what sort of checking should there be for repetitions?
    //could check the parent chain to the root to make sure you aren't trying to insert a parent value
    //If insert(BinaryTreeNode<T> node) is implemented, will need to check its decendents to see if those values are in the existing tree, or ensure that it has no decendents.
    public BinaryTreeNode insert(T value) {
        int compare = value.compareTo(this.value);
        if(compare < 0) {
            if(left != null) {
                return left.insert(value);
            } else {
                left = new BinaryTreeNode<>(value, this);
                return left;
            }
            //return left;
        } else if(compare > 0) {
            if(right != null) {
                return right.insert(value);
            } else {
                right = new BinaryTreeNode<>(value, this);
                return right;
            }
            //return right;
        }
        return null;
    }
    
    //TODO:  at some point, maybe an insert(BinaryTreeNode<T> node); would have to deal with inserting nodes that could have children and reshuffling some connections; low priority at the moment
    
    /**
     * inserts the given value, or updates the node in the tree if one is found with the given value
     * For example, if the class T here is a node that stores key value pairs, if we find the key we could update the value in some way which might be specific to class T.
     * @param value
     * @return 
     */
    public BinaryTreeNode insertOrUpdate(T value) {
        int compare = value.compareTo(this.value);
        BinaryTreeNode node = null;
        if(compare < 0) {
            node = this.left;
        } else if(compare > 0) {
            node = this.right;
        } else {
            //the item being inserted "equals" this
            //TODO:  maybe force T to be an interface that can handle updates
            this.value = value;
            return this;
        }
        
        if(node != null) {
            node.insert(value);
        } else {
            node = new BinaryTreeNode<>(value);
        }
        return node;
    }
    
}
