/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package toolbox.trees;

/**
 *
 * @author paul
 */
public class Utility {
    
    public static Tree generateTree() {
        Tree root = new Tree("root");
        Tree c1 = new Tree("c1");
        Tree c2 = new Tree("c2");
        Tree c3 = new Tree("c3");
        root.addChild(c1).addChild(c2).addChild(c3);
        
        Tree c1c1 = new Tree("c1c1");
        Tree c1c2 = new Tree("c1c2");
        c1.addChild(c1c1).addChild(c1c2);
        
        Tree c2c1 = new Tree("c2c1");
        Tree c2c2 = new Tree("c2c2");
        Tree c2c3 = new Tree("c2c3");
        Tree c2c4 = new Tree("c2c4");
        c2.addChild(c2c1).addChild(c2c2).addChild(c2c3).addChild(c2c4);
        
        Tree c3c1 = new Tree("c3c1");
        Tree c3c2 = new Tree("c3c2");
        Tree c3c3 = new Tree("c3c3");
        c3.addChild(c3c1).addChild(c3c2).addChild(c3c3);
        
        Tree c1c1c1 = new Tree("c1c1c1");
        Tree c1c1c2 = new Tree("c1c1c2");
        Tree c1c1c3 = new Tree("c1c1c3");
        c1c1.addChild(c1c1c1).addChild(c1c1c2).addChild(c1c1c3);
        Tree c1c2c1 = new Tree("c1c2c1");
        Tree c1c2c2 = new Tree("c1c2c2");
        Tree c1c2c3 = new Tree("c1c2c3");
        c1c2.addChild(c1c2c1).addChild(c1c2c2).addChild(c1c2c3);
        
        
        Tree c1c3c1 = new Tree("c1c3c1");
        Tree c1c3c2 = new Tree("c1c3c2");
        Tree c1c3c3 = new Tree("c1c3c3");
        
        return root;
    }
}
