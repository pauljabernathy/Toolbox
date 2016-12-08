/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package toolbox.trees;

import java.util.LinkedList;
import java.util.PriorityQueue;

/**
 *
 * @author pabernathy
 */
public class WeightedBinaryTree<T extends Comparable> {
    
    public static final double DEFAULT_WEIGHT = 1.0;
    public static final DuplicateEntryOption DEFAULT_DUPLICATE_ENTRY_OPTION = DuplicateEntryOption.UPDATE;
    public static enum SortType {
        NATURAL_ORDER, WEIGHT;
    }
    
    public T value;
    public double weight;
    public WeightedBinaryTree parent;
    public WeightedBinaryTree left;
    public WeightedBinaryTree right;
    
    public WeightedBinaryTree(T value) {
        this(value, DEFAULT_WEIGHT, null, null, null);
    }
    
    public WeightedBinaryTree(T value, double weight) {
        this(value, weight, null, null, null);
    }
    
    /*public BalancedBinaryTree(T value, BalancedBinaryTree<T> parent) {
        this(value, parent, null, null);
    }*/
    
    public WeightedBinaryTree(T value, double weight, WeightedBinaryTree parent) {
        this(value, weight, parent, null, null);
    }
    
    //TODO:  Consider if this should know about the parent or not.
    /*public BalancedBinaryTree(T value, BalancedBinaryTree<T> left, BalancedBinaryTree<T> right) {
        this(value, null, left, right);
    }
    
    public BalancedBinaryTree(T value, BalancedBinaryTree<T> parent, BalancedBinaryTree<T> left, BalancedBinaryTree<T> right) {
        this(value, DEFAULT_WEIGHT, parent, left, right);
    }*/
    
    public WeightedBinaryTree(T value, double weight, WeightedBinaryTree<T> parent, WeightedBinaryTree<T> left, WeightedBinaryTree<T> right) {
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
    public boolean valueEquals(WeightedBinaryTree<T> node) {
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
    
    public WeightedBinaryTree<T> get(T value) {
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
    public InsertionResult<T> insert(T value) {
        return insert(value, DEFAULT_WEIGHT, DEFAULT_DUPLICATE_ENTRY_OPTION);
    }
    
    public InsertionResult<T> insert(T value, double weight) {
        return this.insert(value, weight, DEFAULT_DUPLICATE_ENTRY_OPTION);
    }
    
    //TODO:  at some point, maybe an insert(BalancedBinaryTree<T> node); would have to deal with inserting nodes that could have children and reshuffling some connections; low priority at the moment
    public InsertionResult<T> insertWeighted(T value, double weight) {
        return insert(value, weight, DEFAULT_DUPLICATE_ENTRY_OPTION);
    }
    
    
    /**
     * inserts the given value, or updates the node in the tree if one is found with the given value
     * For example, if the class T here is a node that stores key value pairs, if we find the key we could update the value in some way which might be specific to class T.
     * @param value
     * @return 
     */
    public InsertionResult insert(T value, double weight, DuplicateEntryOption option) {
        InsertionResult result = new InsertionResult();
        System.out.println("insertWeighted(" + value + ", " + weight + ") into " + this);
        if(value == null) {
            result.pathFromRoot = new LinkedList<WeightedBinaryTree<T>>();
        }
        int compare = value.compareTo(this.value);
        System.out.println("insertWeighted(" + value + ", " + weight + ") into " + this + ";  compare == " + compare);
        if(compare == 0) {
            System.out.println("compare is 0");
            switch(option) {
                case IGNORE:
                    return result.setInsertedNode(this);
                case REPLACE:
                    this.weight = weight;
                    return result.setInsertedNode(this);
                case UPDATE:
                    this.weight += weight;
                    return result.setInsertedNode(this);
            }
            this.weight += weight;
            return result.setInsertedNode(this);
        }
        if(compare < 0) {
            if(this.parent == null && weight > this.weight) {
                //need to make the new node the root
                WeightedBinaryTree newNode = new WeightedBinaryTree<>(value, weight, null);
                this.parent = newNode;
                newNode.right = this;
                return result.setInsertedNode(newNode);
            }
            if(this.left != null && weight > this.left.weight && !value.equals(this.left.value)) {
                //insert the new node between this and the left child
                WeightedBinaryTree newNode = new WeightedBinaryTree<>(value, weight, this);
                    WeightedBinaryTree formerLeftChild = this.left;
                    formerLeftChild.parent = newNode;
                this.left = newNode;
                    if(value.compareTo(formerLeftChild.value) < 0) {
                        newNode.right = formerLeftChild;
                    } else {
                        newNode.left = formerLeftChild;
                    }
                    return result.setInsertedNode(newNode);
            } else {
                //insert it as a descendant of this, same as a simple binary tree
                if(left != null) {
                    return left.insert(value, weight);
                } else {
                    left = new WeightedBinaryTree<>(value, weight, this);
                    return result.setInsertedNode(left);
                }
            }
        } else if(compare > 0) {
            if(parent == null && weight > this.weight) {
                WeightedBinaryTree newNode = new WeightedBinaryTree<>(value, weight, null);
                this.parent = newNode;
                newNode.left = this;
                return result.setInsertedNode(newNode);
            }
            if(this.right != null && weight > this.right.weight && !value.equals(this.right.value)) {
                System.out.println("this.right != null && " + weight + " > " + this.right.weight + " && !" + value + ".equals(" + this.right.value + ")");
                WeightedBinaryTree newNode = new WeightedBinaryTree(value, weight, this);
                WeightedBinaryTree formerRightChild = this.right;
                formerRightChild.parent = newNode;
                this.right = newNode;
                if(value.compareTo(formerRightChild.value) < 0) {
                    newNode.right = formerRightChild;
                } else {
                    newNode.left = formerRightChild;
                }
                return result.setInsertedNode(newNode);
            } else {
                if(right != null) {
                    System.out.println("right != null => right.insertOrAddWeight(" + value + "," + weight + ")");
                    return right.insert(value, weight);
                } else {
                    System.out.println("recreate right");
                    this.right = new WeightedBinaryTree<>(value, weight, this);
                    return result.setInsertedNode(this.right);
                }
            }
        }
        //TODO:  Determine if we should ever get here.  I don't think we should.  If not, determine where the compiler is finding the logical hole and fill it.  Then determine why you failed to find the logical hole!
        return result;
    }
    
    public InsertionResult<T> insertOrAddWeight(T value, double weight) {
        return this.insert(value, weight, DuplicateEntryOption.UPDATE);
    }
    
    public LinkedList<WeightedBinaryTree<T>> getPathToRoot() {
        LinkedList<WeightedBinaryTree<T>> result = new LinkedList<>();
        if(this.parent != null) {
            result = this.parent.getPathToRoot();
        }
        result.addFirst(this);
        return result;
    }
    
    public LinkedList<WeightedBinaryTree<T>> getPathFromRoot() {
        LinkedList<WeightedBinaryTree<T>> result = new LinkedList<>();
        if(this.parent != null) {
            result = this.parent.getPathFromRoot();
        }
        result.add(this);
        return result;
    }
    
    public WeightedBinaryTree<T> getRoot() {
        return this.getPathFromRoot().get(0);
    }
    
    public String toString() {
        return new StringBuilder(this.value.toString()).append(" ").append(this.weight).toString();
    }
    
    public LinkedList<WeightedBinaryTree<T>> getAsList(SortType sortType) {
        if(sortType == SortType.NATURAL_ORDER) {
            return this.getAsListDepthFirst();
        } else {
            return this.getAsListBreadthFirst(null);
        }
    }
    
    private LinkedList<WeightedBinaryTree<T>> getAsListDepthFirst() {
        LinkedList<WeightedBinaryTree<T>> result = new LinkedList<>();
        if(this.left != null) {
            result.addAll(this.left.getAsListDepthFirst());
        }
        result.add(this);
        if(this.right != null) {
            result.addAll(this.right.getAsListDepthFirst());
        }
        return result;
    }
    
    //TODO:  complete;  And a simple depth first search probably will not give things in weighted order so make on that gives things in weighted order
    /**
     * Does sort of a breadth first search of the tree in order to return a list of nodes on order of weight;
     * Assumes the tree is such that at every level, the children are of equal or less weight than the parent.
     * @param pq
     * @return 
     */
    private LinkedList<WeightedBinaryTree<T>> getAsListBreadthFirst(PriorityQueue<WeightedBinaryTree> pq) {
        LinkedList<WeightedBinaryTree<T>> result = new LinkedList<>();
        if(pq == null) {
            pq = new PriorityQueue(new WeightComparator());
        }
        result.add(this);
        if(this.left != null) {
            pq.add(this.left);
        }
        if(this.right != null) {
            pq.add(this.right);
        }
        if(pq.peek() != null) {
            result.addAll(pq.poll().getAsListBreadthFirst(pq));
        }
        return result;
    }
    
    public void display() {
        this.display(0);
    }
    
    public void display(int depth) {
        String indent = "";
        for(int i = 0; i < depth; i ++) {
            indent += ".";
        }
        System.out.println(indent + this);
        if(this.left != null) {
            this.left.display(depth + 1);
        }
        if(this.right != null) {
            this.right.display(depth + 1);
        }
    }
}
