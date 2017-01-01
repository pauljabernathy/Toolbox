/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package toolbox.trees;

import java.util.LinkedList;
import java.util.PriorityQueue;
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
    
    public T value;
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
    
    public WeightedBinaryTree(T value) {
        this(value, DEFAULT_WEIGHT, null, null, null);
    }
    
    public WeightedBinaryTree(T value, double weight) {
        this(value, weight, null, null, null);
    }
    
    /*public BalancedBinaryTree(T value, BalancedBinaryTree<T> parent) {
        this(value, parent, null, null);
    }*/
    
    
    //TODO:  looks like we need to not specify the parent in the constructor and only specify it with setLeftChild() and setRightChild()!
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
        this.subTreeWeight = 0.0;
    }
    
    public WeightedBinaryTree<T> getRightChild() {
        return this.right;
    }
    
    public WeightedBinaryTree<T> getLeftChild() {
        return this.left;
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
        } else if(newParent != null && !newParent.getPathToRoot().contains(this) && !this.getPathToRoot().contains(newParent)) {
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
        return other != null && other != this && this.getPathFromRoot().contains(other);
    }
    
    public boolean isAncestorOf(WeightedBinaryTree<T> other) {
        return other != null && other != this && other.getPathFromRoot().contains(this);
    }
    
    public boolean isDistantAncestorOf(WeightedBinaryTree<T> other) {
        return this.isAncestorOf(other) && other != null && !other.isChildOf(this);
    }
    
    //TODO: get with a functional interface, more of a query;
    //converting to a list to do stream().filter would be nlogn, right?  A simple query to go get a list of nodes that match
    //not a high priority
    
    //TODO:  what sort of checking should there be for repetitions?
    //could check the parent chain to the root to make sure you aren't trying to insert a parent value
    //If insert(BalancedBinaryTree<T> node) is implemented, will need to check its decendents to see if those values are in the existing tree, or ensure that it has no decendents.
    //TODO: this is copied from the basic binary tree; adapt to this class
    public InsertionResult<T> insert(T value) {
        return insert_old(value, DEFAULT_WEIGHT, DEFAULT_DUPLICATE_ENTRY_OPTION);
    }
    
    public InsertionResult<T> insert(T value, double weight) {
        return this.insert(value, weight, DEFAULT_DUPLICATE_ENTRY_OPTION);
    }
    
    //TODO:  at some point, maybe an insert(BalancedBinaryTree<T> node); would have to deal with inserting nodes that could have children and reshuffling some connections; low priority at the moment
    public InsertionResult<T> insertWeighted(T value, double weight) {
        return insert_old(value, weight, DEFAULT_DUPLICATE_ENTRY_OPTION);
    }
    
    
    /**
     * inserts the given value, or updates the node in the tree if one is found with the given value
     * For example, if the class T here is a node that stores key value pairs, if we find the key we could update the value in some way which might be specific to class T.
     * @param value
     * @return 
     */
    public InsertionResult insert(T value, double weight, DuplicateEntryOption option) {       
        //First, insert like a regular binary tree.
        //Find where it should go (either the exiting entry, or where a new one will go).
        //Add the new entry or update the existing one, as needed.
        InsertionResult result = this.simpleBinaryInsert(value, weight, option);
        
        //Next, look at the path to the root, starting with the node just entered or updated and going to the root,
        //check and see if it should be bumped up a level.
        //If so, move it up and set the previous parent to be that one's right or left child, as appropriate, and set that one's
        //old right or left child to be the old parent's child, as appropriate.
        //Continue the process.  
        return result;
    }
    public InsertionResult insert_old(T value, double weight, DuplicateEntryOption option) {
        InsertionResult result = new InsertionResult();
        //System.out.println("insert(" + value + ", " + weight + ") into " + this);
        if(value == null) {
            result.pathFromRoot = new LinkedList<WeightedBinaryTree<T>>();
        }
        int compare = value.compareTo(this.value);
        //System.out.println("insert(" + value + ", " + weight + ") into " + this + ";  compare == " + compare);
        if(compare == 0) {
            //System.out.println("compare is 0");
            switch(option) {
                case IGNORE:
                    result.status = InsertionResult.Status.IGNORED;
                    return result.setInsertedNode(this);
                case REPLACE:
                    this.weight = weight;
                    result.status = InsertionResult.Status.REPLACED;
                    return result.setInsertedNode(this);
                case UPDATE:
                    result.status = InsertionResult.Status.UPDATED;
                    this.weight += weight;
                    return result.setInsertedNode(this);
            }
            //TODO:  it shouldn't get here should it?  remove after more testing
            this.weight += weight;
            return result.setInsertedNode(this);
        }
        if(compare < 0) {
            if(this.parent == null && weight > this.weight) {
                //need to make the new node the root
                WeightedBinaryTree newNode = new WeightedBinaryTree<>(value, weight, null);
                this.parent = newNode;
                newNode.right = this;
                //now switch the left connection
                if(this.left != null && value.compareTo(this.left.value) > 0) {
                    newNode.left = this.left;
                    this.left = null;
                }
                return result.setInsertedNode(newNode).setStatus(InsertionResult.Status.CREATED);
            }
            if(this.left != null && weight > this.left.weight && !value.equals(this.left.value)) {
                //insert the new node between this and the left child
                WeightedBinaryTree newNode = new WeightedBinaryTree<>(value, weight, this);
                    WeightedBinaryTree formerLeftChild = this.left;
                    formerLeftChild.parent = newNode;
                this.left = newNode;
                if(value.compareTo(formerLeftChild.value) < 0) {
                    newNode.right = formerLeftChild;
                    newNode.left = formerLeftChild.left;
                    formerLeftChild.left = null;
                } else {
                    newNode.left = formerLeftChild;
                    newNode.right = formerLeftChild.right;
                    if(formerLeftChild.left != null) {
                        formerLeftChild.left = null;
                    }
                }
                return result.setInsertedNode(newNode).setStatus(InsertionResult.Status.CREATED);
            } else {
                //insert it as a descendant of this, same as a simple binary tree
                if(left != null) {
                    return left.insert(value, weight);
                } else {
                    left = new WeightedBinaryTree<>(value, weight, this);
                    return result.setInsertedNode(left).setStatus(InsertionResult.Status.CREATED);
                }
            }
        } else if(compare > 0) {
            if(this.parent == null && weight > this.weight) {
                //This is the root but the new node will become the root.
                WeightedBinaryTree newNode = new WeightedBinaryTree<>(value, weight, null);
                this.parent = newNode;
                newNode.left = this;
                //now switch the right connection
                if(this.right != null && value.compareTo(this.right.value) < 0) {
                    //Make this's right child the right child of the new
                    newNode.right = this.right;
                    this.right = null;
                }
                return result.setInsertedNode(newNode).setStatus(InsertionResult.Status.CREATED);
            }
            if(this.right != null && weight > this.right.weight && !value.equals(this.right.value)) {
                //System.out.println("this.right != null && " + weight + " > " + this.right.weight + " && !" + value + ".equals(" + this.right.value + ")");
                WeightedBinaryTree newNode = new WeightedBinaryTree(value, weight, this);
                WeightedBinaryTree formerRightChild = this.right;
                formerRightChild.parent = newNode;
                this.right = newNode;
                //The below still doesn't quite work.  
                if(value.compareTo(formerRightChild.value) < 0) {
                    newNode.right = formerRightChild;
                    newNode.left = formerRightChild.left;
                    formerRightChild.left = null;
                } else {
                    newNode.left = formerRightChild;
                    newNode.right = formerRightChild.right;
                    if(formerRightChild != null) {
                        formerRightChild.right = null;
                    }
                }
                return result.setInsertedNode(newNode).setStatus(InsertionResult.Status.CREATED);
            } else {
                if(right != null) {
                    //System.out.println("right != null => right.insertOrAddWeight(" + value + "," + weight + ")");
                    return right.insert(value, weight);
                } else {
                    //System.out.println("recreate right");
                    this.right = new WeightedBinaryTree<>(value, weight, this);
                    return result.setInsertedNode(this.right).setStatus(InsertionResult.Status.CREATED);
                }
            }
        }
        //TODO:  Determine if we should ever get here.  I don't think we should.  If not, determine where the compiler is finding the logical hole and fill it.  Then determine why you failed to find the logical hole!
        return result;
    }
    
    public InsertionResult<T> insertOrAddWeight(T value, double weight) {
        return this.insert_old(value, weight, DuplicateEntryOption.UPDATE);
    }
    
    protected InsertionResult<T> simpleBinaryInsert(T value, double weight, DuplicateEntryOption option) {
        InsertionResult<T> result = new InsertionResult<>();
        if(value == null) {
            //result.pathFromRoot = new LinkedList<WeightedBinaryTree<T>>();
        }
        int compare = value.compareTo(this.value);
        //System.out.println("insert(" + value + ", " + weight + ", " + option + ") into " + this + ";  compare == " + compare);
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
        this.updateSubTreeWeight(result, weight);
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
            //result.beforePathFromRoot = null;
            result.success = false;
            return result;
        }
        double otherWeight = parent.getTreeWeight() - this.getTreeWeight();
        WeightedBinaryTree<T> sibling = this.getSibling();
        //System.out.println("sibling of " + this + " is " + sibling);
        double siblingWeight = sibling.getTreeWeight();
        WeightedBinaryTree<T> oldParent = this.parent;
        //System.out.println("this is " + this);
        //System.out.println("oldParent is " + oldParent);
        if(this.getTreeWeight() > siblingWeight * this.rebalanceCoefficient) {
            //six cases
            //parent is root and this is left child
            if(parent.isRoot() && this.isLeftChild()) {
                //System.out.println("parent is root and this is left");
                //oldParent.setLeftChild(this.right);
                WeightedBinaryTree<T> oldRight = this.right;
                this.setRightChild(oldParent);
                this.setParent(null);
                oldParent.setLeftChild(oldRight);
                //oldParent.left = oldRight;
                //System.out.println("\noldParent tree is:");
                //oldParent.display();
                /*System.out.println("new right is " + this.right);
                System.out.println("this.right.right is " + this.right.right);
                System.out.println("this.right.left is " + this.right.left);
                System.out.println("oldRight is " + oldRight);
                System.out.println("this.left is " + this.left);*/
                oldRight.updateSubTreeWeight();
                //this.updateSubTreeWeight();
            }
            //parent is root and this is right child
            else if(parent.isRoot() && this.isRightChild()) {
                WeightedBinaryTree<T> oldLeft = this.left;
                this.setLeftChild(oldParent);
                this.setParent(null);
                oldParent.setRightChild(oldLeft);
                oldParent.updateSubTreeWeight();
            }
            
            //parent is left child and this is left child
            else if(parent.isLeftChild() && this.isLeftChild()) {
                WeightedBinaryTree<T> oldRight = this.right;
                WeightedBinaryTree<T> oldGrandParent = oldParent.parent;
                this.setRightChild(oldParent);
                oldGrandParent.setLeftChild(this);
                oldParent.setLeftChild(oldRight);
            }
            //parent is left child and this is right child
            else if(parent.isLeftChild() && this.isRightChild()) {
                WeightedBinaryTree<T> oldLeft = this.left;
                WeightedBinaryTree<T> oldGrandParent = oldParent.parent;
                this.setLeftChild(oldParent);
                oldGrandParent.setLeftChild(this);
                oldParent.setRightChild(oldLeft);
                oldParent.updateSubTreeWeight();
            }
            //parent is right child and this is left child
            else if(parent.isRightChild() && this.isLeftChild()) {
                
            }
            //parent is right child and this is right child
            else if(parent.isRightChild() && this.isRightChild()) {
                
                WeightedBinaryTree<T> oldLeftChild = this.left;
                oldParent.parent.setRightChild(this);
                oldParent.setRightChild(oldLeftChild);
                this.setLeftChild(oldParent);
            }
            this.updateSubTreeWeight();
            //oldParent.updateSubTreeWeight();
        }
        return null;
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
        return new StringBuilder("[").append(this.value.toString()).append(" ").append(this.weight).append(" ").append(this.subTreeWeight).append(" ").append(this.getTreeWeight()).append(" ").append("]").toString();
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
        System.out.println("");
        System.out.println(this + ".display()");
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
