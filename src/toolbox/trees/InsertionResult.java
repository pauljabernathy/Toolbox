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
public class InsertionResult<T extends Comparable> {
    
    public enum Status { CREATED, IGNORED, REPLACED, UPDATED, FAILED, UNKNOWN };
    
    public WeightedBinaryTree<T> insertedNode;
    public LinkedList<WeightedBinaryTree<T>> pathFromRoot;
    public Status status;
    public double previousWeight;
    
    //TODO: make these variables Optional
    public InsertionResult() {
        this.insertedNode = null;
        this.pathFromRoot = new LinkedList<WeightedBinaryTree<T>>();
        this.status = Status.UNKNOWN;
        this.previousWeight = 0.0;
    }

    public WeightedBinaryTree<T> getInsertedNode() {
        return insertedNode;
    }

    public InsertionResult<T> setInsertedNode(WeightedBinaryTree<T> insertedNode) {
        this.insertedNode = insertedNode;
        return this;
    }

    public LinkedList<WeightedBinaryTree<T>> getPathFromRoot() {
        return this.insertedNode.getPathFromRoot();
    }

    private InsertionResult<T> setPathFromRoot(LinkedList<WeightedBinaryTree<T>> pathFromRoot) {
        this.pathFromRoot = pathFromRoot;
        return this;
    }

    public Status getStatus() {
        return status;
    }

    public InsertionResult<T> setStatus(Status status) {
        this.status = status;
        return this;
    }
    
    public WeightedBinaryTree<T> getRoot() {
        return insertedNode.getRoot();
    }

    public double getPreviousWeight() {
        return previousWeight;
    }

    public InsertionResult<T> setPreviousWeight(double previousWeight) {
        this.previousWeight = previousWeight;
        return this;
    }
    
    public String toString() {
        return this.status + " " + this.insertedNode + "; " + this.pathFromRoot;
    }
    
    public boolean wasChanged() {
        return this.status == Status.CREATED || this.status == Status.UPDATED || this.status == Status.REPLACED;
    }
}
