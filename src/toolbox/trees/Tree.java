/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package toolbox.trees;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 *
 * @author paul
 */
public class Tree {
    
    private String id;
    private ArrayList<Tree> children;
    private Tree parent;
    
    public Tree(String name) {
        this(name, null);
    }
    
    public Tree(String name, Tree parent) {
        this.id = name;
        this.children = new ArrayList<Tree>();
        this.parent = parent;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public List<Tree> getChildren() {
        return children;
    }
    
    public Tree addChild(Tree child) {
        child.setParent(this);
        this.children.add(child);
        return this;
    }
    
    public Tree getParent() {
        return this.parent;
    }
    
    public void setParent(Tree newParent) {
        this.parent = newParent;
    }
    
    public List<Tree> getPathFromRoot() {
        
        LinkedList<Tree> result = new LinkedList<Tree>();
        result.add(this);
        Tree current = this;
        //System.out.println(current + ".getParent() == " + current.getParent());
        while(current.getParent() != null) {
            //System.out.println(current + ".getParent() == " + current.getParent());
            result.addFirst(current.getParent());
            current = current.getParent();
        }
        return result;
    }
    //TODO:  I don't like this.  You can have two completely different trees with different parents and children, but if you use setId()
    //to se the id of one to the other's id, this function will say they are equal.
    @Override
    public boolean equals(Object o) {
        //TODO:  what if this's id is null?
        //What about checking the child list?  
        if(o instanceof Tree) {
            //System.out.println(this.id + " =? " + ((Tree)o).getId());
            if(this.id != null && this.id.equals(((Tree)o).getId())) {
                //System.out.println(this.id + " = " + ((Tree)o).getId());
                return true;
            } else if(this.id == null && ((Tree)o).getId() == null) {
                return true;
            } /*else if(this.id == null && ((Tree)o).getId() != null) {
                System.out.println("this.id == null && ((Tree)o).getId() != null");
                return false;
            } */else {
                return false;
            }
        } else {
            return false;
        }
    }
    
    @Override
    public int hashCode() {
        return this.id.hashCode();
    }
    
    public String toString() {
        return this.id;
    }
}
