/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package toolbox.trees;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.PriorityQueue;
import java.util.function.Predicate;
import toolbox.trees.InsertionResult.Status;

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
    private double rebalanceCoefficient = 1.0;
    
    public T key;
    public double weight;
    public WeightedBinaryTree parent;
    public WeightedBinaryTree left;
    public WeightedBinaryTree right;
    
    //caching variables - these are redundant but stored here so we don't have to do a search of the whole tree to find them whenever they are needed
    private double subTreeWeight;
    private double treeTorque;
    //TODO: a depth variable; need to update every time parent is changed
    //TODO: keep track of relative sub tree torque; I think that will be easier than keeping track of total torque relative to the root
    //and decicions about rotating trees only needs to know about torque relative to the node we are rotating beneath
    //Actually, keep track of tree weight and don't track torque directly.  If you keep track of tree weight and let the heavier items
    //filter toward the root, the trees with greater torque should end up near the root.
    
    public WeightedBinaryTree(T key) {
        this(key, DEFAULT_WEIGHT, null, null, null);
    }
    
    public WeightedBinaryTree(T key, double weight) {
        this(key, weight, null, null, null);
    }
    
    /*public BalancedBinaryTree(T key, BalancedBinaryTree<T> parent) {
        this(key, parent, null, null);
    }*/
    
    
    //TODO:  looks like we need to not specify the parent in the constructor and only specify it with setLeftChild() and setRightChild()!
    public WeightedBinaryTree(T key, double weight, WeightedBinaryTree parent) {
        this(key, weight, parent, null, null);
    }
    
    //TODO:  Consider if this should know about the parent or not.
    /*public BalancedBinaryTree(T key, BalancedBinaryTree<T> left, BalancedBinaryTree<T> right) {
        this(key, null, left, right);
    }
    
    public BalancedBinaryTree(T key, BalancedBinaryTree<T> parent, BalancedBinaryTree<T> left, BalancedBinaryTree<T> right) {
        this(key, DEFAULT_WEIGHT, parent, left, right);
    }*/
    
    public WeightedBinaryTree(T key, double weight, WeightedBinaryTree<T> parent, WeightedBinaryTree<T> left, WeightedBinaryTree<T> right) {
        this.key = key;
        this.weight = weight;
        this.parent = parent;
        this.left = left;
        this.right = right;
        this.subTreeWeight = 0.0;
    }
    
    public WeightedBinaryTree<T> getRightChild() {
        return this.right;
    }
    
    public WeightedBinaryTree<T> getLeftChild() {
        return this.left;
    }
    
    public T getKey() {
        return this.key;
    }
    
    /**
     * tests if the values inside the nodes are equal but does not test the child nodes because that could take a long time for a large tree
     * @param node
     * @return 
     */
    public boolean keyEquals(WeightedBinaryTree<T> node) {
        if(this.key != null) {
            if(node != null) {
                return this.key.equals(node.key);
            } else {
                return false;
            }
        } else if (node != null) {
            //this's key is null
            if(node.key == null) {
                //other node's key is null so return true
                return true;
            } else {
                //other node's key is not null; null and not null are not equals
                return false;
            }
        } else {
            //could go either way here but if the other node is null, returning true could be a little deceptive
            return false;
        }
        //value null        node null       --not relevant
        //value null        node not null   node key null
        //value null        node not null   node key not null
        //value not null    node null       --not relevent
        //value not null    node not null   node key null
        //value not null    node not null   node key not null
    }
    
    /**
     * tests if the key in this node equals the given object, using the .equals() method of class T
     * @param otherValue
     * @return 
     */
    public boolean keyEquals(T otherValue) {
        if(this.key == null) {
            return otherValue == null;
        } else {
            return this.key.equals(otherValue);
        }
    }
    
    public int compareTo(WeightedBinaryTree<T> other) {
        return this.key.compareTo(other.getKey());
    }
    
    //TODO:  complete
    public boolean contains(T value) {
        return false;
    }
    //TODO: return an Optional?
    public WeightedBinaryTree<T> get(T value) {
        if(value == null && this.key != null) {
            return null;
        } else if(value == null && this.key == null) {
            return this;
        }
        int compare = value.compareTo(this.key);
        if(compare < 0) {
            return this.left != null ? left.get(value) : null;
        } else if(compare > 0) {
            return this.right != null ? right.get(value) : null;
        } else {
            return this.keyEquals(value) ? this : null;
        }
    }
    
    protected int getDepth() {
        return this.getPathFromRoot().size();
    }
    
    public double getSubTreeWeight() {
        return this.subTreeWeight;
    }
    
    public WeightedBinaryTree<T> addToSubTeeWeight(double addition) {
        this.subTreeWeight += addition;
        return this;
    }
       
    public double getTreeWeight() {
        return this.subTreeWeight + this.weight;
    }
    
    public WeightedBinaryTree<T> getParent() {
        return this.parent;
    }
    
    //TODO:  return something other than this?
    //TODO:  should we even use setParent?  might should so all parent setting through setXChild
    public WeightedBinaryTree<T> setParent(WeightedBinaryTree<T> newParent) {
        if(newParent == null) {
            if(this.parent != null) {
                if(this.isLeftChild()) {
                    this.parent.left = null;
                } else {
                    this.parent.right = null;
                }
                this.parent.updateSubTreeWeight();
            }
            this.parent = null;
        } else if(newParent != null && !newParent.isDescendentOf(this) && !newParent.isAncestorOf(this)) {
            int previousDepth = this.getDepth();
            if(this.parent != null) {
                if(this.isLeftChild()) {
                    this.parent.left = null;
                } else {
                    this.parent.right = null;
                }
                this.parent.updateSubTreeWeight();
            }
            this.parent = newParent;
        } else {
            //TODO:  some error message here?
        }
        return this;
    }
    
    //TODO:  consider returning an InsertionResult from these
    //For setting child nodes, we need to be careful about nodes that are this tree's ancestor or decendant.  Cycles should not be allowed.
    //We'll have an option to either ignore such entries or to replace the old child with the new one.
    //For now, we'll limit such replacements to parent child relationships for the purpose of rebalancing.
    public WeightedBinaryTree<T> setLeftChild(WeightedBinaryTree<T> newLeft) {
        return this.setLeftChild(newLeft, DuplicateEntryOption.REPLACE);
    }
    
    public WeightedBinaryTree<T> setLeftChild(WeightedBinaryTree<T> newLeft, DuplicateEntryOption option) {
        //System.out.println(this + ".setLeftChild(" + newLeft + ")");
        //System.out.println(newLeft + " " + newLeft.getPathFromRoot().contains(this) + " " + this.getPathFromRoot().contains(newLeft));
        if(newLeft == null) {
            this.doSetLeftChild(newLeft);
        } else if(newLeft == this) {
            return this;
        } else if(!newLeft.isAncestorOf(this) || (this.parent == newLeft && option == DuplicateEntryOption.REPLACE)) {
            this.doSetLeftChild(newLeft);
        } else {
            //do nothing
        }
        return this;
    }
    
    private WeightedBinaryTree<T> doSetLeftChild(WeightedBinaryTree<T> newLeft) {
        if(this.left != null) {
            this.subTreeWeight -= this.left.getTreeWeight();
            //this.treeTorque -= this.left.treeTorque;
            this.left.setParent(null);
        }
        this.left = newLeft;
        if(newLeft != null) {
            newLeft.setParent(this);
            this.subTreeWeight += this.left.getTreeWeight();
            //this.treeTorque += this.left.treeTorque;
        }
        if(this.parent != null) {
            this.parent.updateSubTreeWeight();
        }
        return this;
    }
    
    public WeightedBinaryTree<T> setRightChild(WeightedBinaryTree<T> newRight) {
        return this.setRightChild(newRight, DuplicateEntryOption.REPLACE);
    }
    
    public WeightedBinaryTree<T> setRightChild(WeightedBinaryTree<T> newRight, DuplicateEntryOption option) {
        /*if(newRight != null && !((newRight.getPathFromRoot().contains(this) || this.getPathFromRoot().contains(newRight)) && !(this.parent == newRight && option == DuplicateEntryOption.REPLACE))) {
            doSetRightChild(newRight);
        }*/
        if(newRight == null) {
            this.doSetRightChild(newRight);
        } else if(newRight == this) {
            return this;
        } else if(!newRight.isAncestorOf(this) || (this.parent == newRight && option == DuplicateEntryOption.REPLACE)) {
            this.doSetRightChild(newRight);
        } else {
            //do nothing
        }
        return this;
    }
    
    private WeightedBinaryTree<T> doSetRightChild(WeightedBinaryTree<T> newRight) {
        if(this.right != null) {
            this.subTreeWeight -= right.getTreeWeight();
            //this.treeTorque -= right.treeTorque;
            this.right.setParent(null);
        }
        this.right = newRight;
        if(this.right != null) {
            newRight.setParent(this);
            this.subTreeWeight += right.getTreeWeight();
            //this.treeTorque += right.treeTorque;
        }
        if(this.parent != null) {
            this.parent.updateSubTreeWeight();
        }
        return this;
    }
    
    public double getWeight() {
        return this.weight;
    }
    
    public WeightedBinaryTree<T> setWeight(double newWeight) {
        this.weight = newWeight;
        return this;
    }
    /*public WeightedBinaryTree<T> setSubTreeWeight(double newWeight) {
        
        return this;
    }*/
    
    //TODO:  would like to find a way of making sure this is only called from this or a child node
    /*public WeightedBinaryTree<T> setSubTreeTorque(double newTorque) {
        if(this.left == null && this.right == null) {
            //a bogus request if newTorque is not 0
            this.treeTorque = this.getIndividualTorque();
            this.subTreeWeight = this.weight;
            return this;
        }
        this.treeTorque = this.getIndividualTorque() + newTorque;
        return this;
    }*/
    
    public WeightedBinaryTree<T> getSibling() {
        if(parent == null) {
            return null;
        } else if(this == parent.left) {
            return parent.right;
        } else {
            return parent.left;
        }
    }
    
    public boolean isRoot() {
        return this.parent == null;
    }
    
    public boolean isLeftChild() {
        if(this.parent == null) {
            return false;
        } else if(this == this.parent.right) {
            return false;
        }
        return true;
    }
    
    public boolean isRightChild() {
        if(this.parent == null) {
            return false;
        } else if(this == this.parent.left) {
            return false;
        }
        return true;
    }
    
    public boolean isChildOf(WeightedBinaryTree<T> other) {
        return other != null && this.parent == other;
    }
    
    public boolean isDescendentOf(WeightedBinaryTree<T> other) {
        //return other != null && other != this && this.getPathFromRoot().contains(other);
        if(other == null || other == this) {
            return false;
        }
        WeightedBinaryTree<T> ancestor = this.getParent();
        while(ancestor != null) {
            if(ancestor == other){
                return true;
            }
            ancestor = ancestor.getParent();
        }
        return false;
    }
    
    public boolean isAncestorOf(WeightedBinaryTree<T> other) {
        //return other != null && other != this && other.getPathFromRoot().contains(this);
        return other.isDescendentOf(this);
    }
    
    public boolean isDistantAncestorOf(WeightedBinaryTree<T> other) {
        return this.isAncestorOf(other) && other != null && !other.isChildOf(this);
    }
    
    //TODO: get with a functional interface, more of a query;
    //converting to a list to do stream().filter would be nlogn, right?  A simple query to go get a list of nodes that match
    //not a high priority
    
    //TODO:  what sort of checking should there be for repetitions?
    //could check the parent chain to the root to make sure you aren't trying to insert a parent key
    //If insert(BalancedBinaryTree<T> node) is implemented, will need to check its decendents to see if those values are in the existing tree, or ensure that it has no decendents.
    //TODO: this is copied from the basic binary tree; adapt to this class
    public InsertionResult<T> insert(T value) {
        return insert(value, DEFAULT_WEIGHT, DEFAULT_DUPLICATE_ENTRY_OPTION);
    }
    
    public InsertionResult<T> insert(T value, double weight) {
        return this.insert(value, weight, DEFAULT_DUPLICATE_ENTRY_OPTION);
    }    
    
    /**
     * inserts the given key, or updates the node in the tree if one is found with the given key
 For example, if the class T here is a node that stores key key pairs, if we find the key we could update the key in some way which might be specific to class T.
     * @param value
     * @return 
     */
    public InsertionResult insert(T value, double weight, DuplicateEntryOption option) {
        //System.out.println("\n---\ninsert(" + key + ", " + option + ")");
        //First, insert like a regular binary tree.
        //Find where it should go (either the exiting entry, or where a new one will go).
        //Add the new entry or update the existing one, as needed.
        InsertionResult result = this.simpleBinaryInsert(value, weight, option);
        
        //Next, look at the path to the root, starting with the node just entered or updated and going to the root,
        //check and see if it should be bumped up a level.
        //If so, move it up and set the previous parent to be that one's right or left child, as appropriate, and set that one's
        //old right or left child to be the old parent's child, as appropriate.
        //Continue the process.  
        //LinkedList<WeightedBinaryTree<T>> pathFromRoot = result.getPathFromRoot();
        WeightedBinaryTree<T> current = result.getInsertedNode();
        current.rebalance();
        return result;
    }
    
    
    protected InsertionResult<T> simpleBinaryInsert(T value) {
        return simpleBinaryInsert(value, DEFAULT_WEIGHT);
    }
    protected InsertionResult<T> simpleBinaryInsert(T value, double weight) {
        return simpleBinaryInsert(value, weight, DuplicateEntryOption.UPDATE);
    }
    
    protected InsertionResult<T> simpleBinaryInsert(T value, double weight, DuplicateEntryOption option) {
        InsertionResult<T> result = new InsertionResult<>();
        if(value == null) {
            //result.pathFromRoot = new LinkedList<WeightedBinaryTree<T>>();
        }
        int compare = value.compareTo(this.key);
        //System.out.println("insert(" + key + ", " + weight + ", " + option + ") into " + this + ";  compare == " + compare);
        if(compare == 0) {
            //System.out.println("compare is 0");
            double previousWeight = this.weight;
            result.setInsertedNode(this);
            switch(option) {
                case IGNORE:
                    result.setStatus(InsertionResult.Status.IGNORED).setPreviousWeight(this.weight);
                    break;
                case REPLACE:
                    this.weight = weight;
                    result.status = InsertionResult.Status.REPLACED;
                    result.setStatus(InsertionResult.Status.REPLACED).setPreviousWeight(previousWeight);
                    break;
                case UPDATE:
                    this.weight += weight;
                    result.setStatus(InsertionResult.Status.UPDATED).setPreviousWeight(previousWeight);
                    break;
                default:
                    result.setStatus(InsertionResult.Status.IGNORED).setPreviousWeight(this.weight);
            }
            //this.updateSubTreeWeight(result, weight);
        } else if(compare < 0) {
            if(this.left != null) {
                result = this.left.simpleBinaryInsert(value, weight, option);
            } else {
                this.setLeftChild(new WeightedBinaryTree(value, weight));
                result.setInsertedNode(this.left).setStatus(InsertionResult.Status.CREATED);
            }
        } else {
            if(this.right != null) {
                result = this.right.simpleBinaryInsert(value, weight, option);
            } else {
                this.setRightChild(new WeightedBinaryTree(value, weight));
                result.setInsertedNode(this.right).setStatus(InsertionResult.Status.CREATED);
            }
        }
        this.updateSubTreeWeight();//result, weight);
        return result;
    }
    
    //TODO:  determine if this should be removed and replaced with updateSubTreeWeight()
    protected void updateSubTreeWeight(InsertionResult result, double newWeight) {
        if(this == result.getInsertedNode()) {// || !this.getPathFromRoot().contains(result.getInsertedNode())) {
            //simply updated this node, so no need to update the sub tree weight
            //or updated a node that is not a descendant
            //TODO:  test for descendant, not simply in path from root!
            return;
        }
        if(result.status == Status.CREATED || result.status == Status.UPDATED) {
            this.subTreeWeight += newWeight;
        } else if(result.status == Status.REPLACED) {
            this.subTreeWeight -= result.previousWeight;
            this.subTreeWeight += newWeight;
        }
    }
    
    //TODO:  consider having this automatically update the parent's weight, or doing it in setXChild().  Currently, 
    //it only filters up toward the root in simpleBinaryInsert, but I think that means that if you call simpleBinaryInsert()
    //on a non root none, the weight updates won't make it higher up than the node you called in on.  Of course, you should
    //not be calling insert on a non root node.  So maybe the TODO is to change simpleBinaryInsert() to only do inserts on
    //the root.
    public WeightedBinaryTree<T> updateSubTreeWeight() {
        this.subTreeWeight = this.left != null ? this.left.getTreeWeight() : 0.0;
        this.subTreeWeight += this.right != null ? this.right.getTreeWeight() : 0.0;
        return this;
    }
    
    public RebalanceResult<T> rebalance() {
        RebalanceResult<T> result = new RebalanceResult<>();
        result.beforePathFromRoot = this.getPathFromRoot();
        if(this.parent == null) {
            result.afterPathFromRoot = null;
            result.success = false;
            return result;
        }
        
        while(shouldRebalance()) {
            rebalanceOneLevel();
        }
        result.afterPathFromRoot = this.getPathFromRoot();
        result.success = true;
        return result;
    }
    
    //TODO:  IMPORTANT!  It was using this.parent != null && this.getTreeWeight() > this.getRebalanceThresholdWeight() for the test
    //and parent.getTreeWeight() - this.getTreeWeight()) * this.rebalanceCoefficient for the threshold weight.  That it, it was moving stuff up
    //based on a node's entire tree weight, not the individual weight.  This was for performance reasons, on the idea that that will lead to the 
    //fewest number or node traversals.  But I found that when the tree was sorted by weight, it didn't always sort things correctly.  The nodes with
    //the highest weight were not guaranteed to be at the time.
    //=>Investigate 
    //1.  performance difference between the two (and possibly others) ways of determine when to rebalance
    //2.  If there is a way to rebalance based of the tree weight and not the individual weight and still sort correctly (but only if there is a major performance gain by using the tree weight).
    public boolean shouldRebalance() {
        return this.parent != null && this.getWeight() > this.getRebalanceThresholdWeight();
    }
    
    public double getRebalanceThresholdWeight() {
        //assume parent is never null for now, for performance
        return parent.getWeight();
    }
    
    public void rebalanceOneLevel() {
        WeightedBinaryTree<T> oldParent = this.parent;
        //six cases 
        //parent is root and this is left child
        if(parent.isRoot() && this.isLeftChild()) {
            WeightedBinaryTree<T> oldRight = this.right;
            this.right = oldParent;
            oldParent.parent = this;
            this.setParent(null);
            oldParent.left = oldRight;
            if(oldRight != null) {
                oldRight.parent = oldParent;
            }
        }
        //parent is root and this is right child
        else if(parent.isRoot() && this.isRightChild()) {
            WeightedBinaryTree<T> oldLeft = this.left;
            this.left = oldParent;
            oldParent.parent = this;
            this.setParent(null);
            oldParent.right = oldLeft;
            if(oldLeft != null) {
                oldLeft.parent = oldParent;
            }
        }

        //parent is left child and this is left child
        else if(parent.isLeftChild() && this.isLeftChild()) {
            WeightedBinaryTree<T> oldRight = this.right;
            WeightedBinaryTree<T> oldGrandParent = oldParent.parent;
            this.right = oldParent;
            oldParent.parent = this;
            oldGrandParent.left = this;
            this.parent = oldGrandParent;
            oldParent.left = oldRight;
            if(oldRight != null) {
                oldRight.parent = oldParent;
            }
        }
        //parent is left child and this is right child
        else if(parent.isLeftChild() && this.isRightChild()) {
            WeightedBinaryTree<T> oldLeft = this.left;
            WeightedBinaryTree<T> oldGrandParent = oldParent.parent;
            this.left = oldParent;
            oldParent.parent = this;
            oldGrandParent.left = this;
            this.parent = oldGrandParent;
            oldParent.right = oldLeft;
            if(oldLeft != null) {
                oldLeft.parent = oldParent;
            }
        }
        //parent is right child and this is left child
        else if(parent.isRightChild() && this.isLeftChild()) {
            WeightedBinaryTree<T> oldRightChild = this.right;
            WeightedBinaryTree<T> oldGrandParent = oldParent.parent;
            this.right = oldParent;
            oldParent.parent = this;
            oldGrandParent.right = this;
            this.parent = oldGrandParent;
            oldParent.left = oldRightChild;
            if(oldRightChild != null) {
                oldRightChild.parent = oldParent;
            }
        }
        //parent is right child and this is right child
        else if(parent.isRightChild() && this.isRightChild()) {
            WeightedBinaryTree<T> oldLeftChild = this.left;
            WeightedBinaryTree<T> oldGrandParent = oldParent.parent;
            this.left = oldParent;
            oldParent.parent = this;
            oldGrandParent.right = this;
            this.parent = oldGrandParent;

            oldParent.right = oldLeftChild;
            if(oldLeftChild != null) {
                oldLeftChild.parent = oldParent;
            }
            oldParent.updateSubTreeWeight();
        }
        oldParent.updateSubTreeWeight();
        this.updateSubTreeWeight();
        if(this.parent != null) {
            this.parent.updateSubTreeWeight();
        }  
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
        WeightedBinaryTree<T> root = this;
        while(root.getParent() != null) {
            root = root.getParent();
        }
        return root;
    }    
      
    public String toString() {
        return new StringBuilder("[").append(this.key.toString()).append(" ").append(this.weight).append(" ").append(this.subTreeWeight).append(" ").append(this.getTreeWeight()).append(" ").append("]").toString();
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
        //TODO:  remove this pq parameter since this is no longer a recursive function
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
        /*if(pq.peek() != null) {
            result.addAll(pq.poll().getAsListBreadthFirst(pq));
        }*/
        WeightedBinaryTree<T> next = null;
        while(pq.peek() != null) {
            next = pq.poll();
            result.add(next);
            if(next.getLeftChild() != null) {
                pq.add(next.getLeftChild());
            }
            if(next.getRightChild() != null) {
                pq.add(next.getRightChild());
            }
        }
        return result;
    }
    
    //TODO:  reset the root to itself, so the code that gets this won't have access to the rest of the original tree
    //TODO:  return as a copy of the original, so the original can't be changed by the code that gets this
    public Optional<WeightedBinaryTree<T>> findFirst(Predicate p) {
        //System.out.println(this + ".findFirst");
        
        if(p == null) {
            return Optional.ofNullable(null);
        }
        Optional result = p.test(this) ? Optional.of(this) : Optional.ofNullable(null);
        //System.out.println("this result = " + result);
        if(result.isPresent()) {
            return result;
        }
        result = this.getLeftChild() != null ? this.getLeftChild().findFirst(p) : Optional.ofNullable(null);
        //System.out.println("left result = " + result);
        if(result.isPresent())  {
            return result;
        }
        result = this.getRightChild() != null ? this.getRightChild().findFirst(p) : Optional.ofNullable(null);
        //System.out.println("right result = " + result);
        if(result.isPresent()) {
            return result;
        }
        //System.out.println("result empty");
        return Optional.ofNullable(null);
    }
    
    public List<WeightedBinaryTree<T>> queryFromFirst(Predicate<WeightedBinaryTree<T>> p) {
        Optional<WeightedBinaryTree<T>> first = this.findFirst(p);
        LinkedList<WeightedBinaryTree<T>> result = new LinkedList<>();
        PriorityQueue<WeightedBinaryTree<T>> pq = new PriorityQueue<>();
        if(!first.isPresent()) {
            return result;
        }
        
        //result.add(first.get());
        pq.add(first.get());

        WeightedBinaryTree<T> next = null;
        while(pq.peek() != null) {
            next = pq.poll();
            result.add(next);
            if(next.getLeftChild() != null && p.test(next.getLeftChild())) {
                pq.add(next.getLeftChild());
            }
            if(next.getRightChild() != null && p.test(next.getRightChild())) {
                pq.add(next.getRightChild());
            }
        }
        return result;
    }
    
    public Optional<WeightedBinaryTree<T>> getSubTree(Predicate p) {
        Optional<WeightedBinaryTree<T>> first = this.findFirst(p);
        if(!first.isPresent()) {
            return Optional.ofNullable(null);
        } else {
            return null;
        }
    }
    
    public void display() {
        System.out.println("");
        System.out.println(this.key + ".display()");
        this.display("");
    }
    
    public void display(int depth) {
        String indent = "";
        for(int i = 0; i < depth; i ++) {
            indent += ".";
        }
        //System.out.println(indent + this);
        if(this.left != null) {
            this.left.display(depth + 1);
        }
        if(this.right != null) {
            this.right.display(depth + 1);
        }
    }
    
    public void display(String prefix) {
        System.out.println(prefix + this);
        if(this.left != null) {
            this.left.display(prefix + "l");
        }
        if(this.right != null) {
            this.right.display(prefix + "r");
        }
    }
}
