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
public class BalancedBinaryTree<T extends Comparable> {
    
    public static double DEFAULT_WEIGHT = 1.0;
    
    public T value;
    public double weight;
    public BalancedBinaryTree parent;
    public BalancedBinaryTree left;
    public BalancedBinaryTree right;
    
    public BalancedBinaryTree(T value) {
        this(value, DEFAULT_WEIGHT, null, null, null);
    }
    
    public BalancedBinaryTree(T value, double weight) {
        this(value, weight, null, null, null);
    }
    
    /*public BalancedBinaryTree(T value, BalancedBinaryTree<T> parent) {
        this(value, parent, null, null);
    }*/
    
    public BalancedBinaryTree(T value, double weight, BalancedBinaryTree parent) {
        this(value, weight, parent, null, null);
    }
    
    //TODO:  Consider if this should know about the parent or not.
    /*public BalancedBinaryTree(T value, BalancedBinaryTree<T> left, BalancedBinaryTree<T> right) {
        this(value, null, left, right);
    }
    
    public BalancedBinaryTree(T value, BalancedBinaryTree<T> parent, BalancedBinaryTree<T> left, BalancedBinaryTree<T> right) {
        this(value, DEFAULT_WEIGHT, parent, left, right);
    }*/
    
    public BalancedBinaryTree(T value, double weight, BalancedBinaryTree<T> parent, BalancedBinaryTree<T> left, BalancedBinaryTree<T> right) {
        this.value = value;
        this.weight = weight;
        this.parent = parent;
        this.left = left;
        this.right = right;
    }
    
    /**
     * tests if the values inside the nodes are equal but does not test the child nodes because that could take a long time for a large tree
     * @param node
     * @return 
     */
    public boolean valueEquals(BalancedBinaryTree<T> node) {
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
    
    public BalancedBinaryTree<T> get(T value) {
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
    //If insert(BalancedBinaryTree<T> node) is implemented, will need to check its decendents to see if those values are in the existing tree, or ensure that it has no decendents.
    //TODO: this is copied from the basic binary tree; adapt to this class
    public LinkedList<BalancedBinaryTree> insert(T value) {
        /*int compare = value.compareTo(this.value);
        if(compare < 0) {
            if(left != null) {
                return left.insert(value);
            } else {
                left = new BalancedBinaryTree<>(value, this);
                return left;
            }
            //return left;
        } else if(compare > 0) {
            if(right != null) {
                return right.insert(value);
            } else {
                right = new BalancedBinaryTree<>(value, this);
                return right;
            }
            //return right;
        }
        return null;
        */
        return insertWeighted(value, DEFAULT_WEIGHT);
    }
    
    //TODO:  at some point, maybe an insert(BalancedBinaryTree<T> node); would have to deal with inserting nodes that could have children and reshuffling some connections; low priority at the moment
    
    //TODO: ensure that inserting a value that is already present causes no affect - it is not inserted
    public LinkedList<BalancedBinaryTree> insertWeighted(T value, double weight) {
        if(value == null) {
            return new LinkedList<BalancedBinaryTree>();
        }
        int compare = value.compareTo(this.value);
        //System.out.println("insertWeighted(" + value + ", " + weight + ") into " + this + ";  compare == " + compare);
        if(compare == 0) {
            //System.out.println("compare is 0");
            return this.getPathFromRoot();
        }
        if(compare < 0) {
            if(this.left != null && weight > this.left.weight && !value.equals(this.left.value)) {
                //insert the new node between this and the left child
                BalancedBinaryTree newNode = new BalancedBinaryTree<>(value, weight, this);
                BalancedBinaryTree formerLeftChild = this.left;
                formerLeftChild.parent = newNode;
                this.left = newNode;
                if(value.compareTo(formerLeftChild.value) < 0) {
                    newNode.right = formerLeftChild;
                } else {
                    newNode.left = formerLeftChild;
                }
                return newNode.getPathFromRoot();
            } else {
                //insert it as a descendant of this, same as a simple binary tree
                if(left != null) {
                    return left.insertWeighted(value, weight);
                } else {
                    left = new BalancedBinaryTree<>(value, weight, this);
                    return left.getPathFromRoot();
                }
            }
        } else if(compare > 0) {
            if(this.right != null && weight > this.right.weight && !value.equals(this.right.value)) {
                BalancedBinaryTree newNode = new BalancedBinaryTree(value, weight, this);
                BalancedBinaryTree formerRightChild = this.right;
                formerRightChild.parent = newNode;
                this.right = newNode;
                if(value.compareTo(formerRightChild.value) < 0) {
                    newNode.right = formerRightChild;
                } else {
                    newNode.left = formerRightChild;
                }
                return newNode.getPathFromRoot();
            } else {
                if(right != null) {
                    return right.insertWeighted(value, weight);
                } else {
                    right = new BalancedBinaryTree<>(value, weight, this);
                    return right.getPathFromRoot();
                }
            }
            //return right;
        }
        return getPathFromRoot();
    }
    
    public LinkedList<BalancedBinaryTree> insertAndBalance(T value) {
        return getPathFromRoot();
    }
    /**
     * inserts the given value, or updates the node in the tree if one is found with the given value
     * For example, if the class T here is a node that stores key value pairs, if we find the key we could update the value in some way which might be specific to class T.
     * @param value
     * @return 
     */
    public BalancedBinaryTree insertOrUpdate(T value) {
        int compare = value.compareTo(this.value);
        BalancedBinaryTree node = null;
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
            node = new BalancedBinaryTree<>(value);
        }
        return node;
    }
    
    public LinkedList<BalancedBinaryTree> insertOrAddWeight(T value, double weight) {
        System.out.println("insertWeighted(" + value + ", " + weight + ") into " + this);
        if(value == null) {
            return new LinkedList<BalancedBinaryTree>();
        }
        int compare = value.compareTo(this.value);
        System.out.println("insertWeighted(" + value + ", " + weight + ") into " + this + ";  compare == " + compare);
        if(compare == 0) {
            System.out.println("compare is 0");
            this.weight += weight;
            return this.getPathFromRoot();
        }
        if(compare < 0) {
            if(this.left != null && weight > this.left.weight && !value.equals(this.left.value)) {
                //insert the new node between this and the left child
                BalancedBinaryTree newNode = new BalancedBinaryTree<>(value, weight, this);
                BalancedBinaryTree formerLeftChild = this.left;
                formerLeftChild.parent = newNode;
                this.left = newNode;
                if(value.compareTo(formerLeftChild.value) < 0) {
                    newNode.right = formerLeftChild;
                } else {
                    newNode.left = formerLeftChild;
                }
                return newNode.getPathFromRoot();
            } else {
                //insert it as a descendant of this, same as a simple binary tree
                if(left != null) {
                    return left.insertWeighted(value, weight);
                } else {
                    left = new BalancedBinaryTree<>(value, weight, this);
                    return left.getPathFromRoot();
                }
            }
        } else if(compare > 0) {
            if(this.right != null && weight > this.right.weight && !value.equals(this.right.value)) {
                System.out.println("this.right != null && " + weight + " > " + this.right.weight + " && !" + value + ".equals(" + this.right.value + ")");
                BalancedBinaryTree newNode = new BalancedBinaryTree(value, weight, this);
                BalancedBinaryTree formerRightChild = this.right;
                formerRightChild.parent = newNode;
                this.right = newNode;
                if(value.compareTo(formerRightChild.value) < 0) {
                    newNode.right = formerRightChild;
                } else {
                    newNode.left = formerRightChild;
                }
                return newNode.getPathFromRoot();
            } else {
                if(right != null) {
                    System.out.println("right != null => right.insertOrAddWeight(" + value + "," + weight + ")");
                    return right.insertOrAddWeight(value, weight);
                } else {
                    System.out.println("recreate right");
                    right = new BalancedBinaryTree<>(value, weight, this);
                    return right.getPathFromRoot();
                }
            }
            //return right;
        }
        return getPathFromRoot();
    }
    
    public LinkedList<BalancedBinaryTree> getPathToRoot() {
        LinkedList<BalancedBinaryTree> result = new LinkedList<>();
        if(this.parent != null) {
            result = this.parent.getPathToRoot();
            result.addFirst(this.parent);
        }
        return result;
    }
    
    public LinkedList<BalancedBinaryTree> getPathFromRoot() {
        LinkedList<BalancedBinaryTree> result = new LinkedList<>();
        if(this.parent != null) {
            result = this.parent.getPathToRoot();
            result.add(this.parent);
        }
        result.add(this);
        return result;
    }
    
    public String toString() {
        return new StringBuilder(this.value.toString()).append(" ").append(this.weight).toString();
    }
    
    //TODO: at some point, add a RebalanceResult class, which tells before and after paths
    public RebalanceResult rebalance() {
        RebalanceResult result = new RebalanceResult();
        result.beforePathFromRoot = this.getPathFromRoot();
        
        return result;
    }
}
