/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package toolbox.trees;

import java.util.List;
import java.util.ArrayList;
import java.util.LinkedList;
import org.apache.logging.log4j.*;

import toolbox.util.ListArrayUtil;

/**
 *
 * @author paul
 */
public class BreadthFirstSearch {
    
    private static Logger logger;
    
    static {
        logger = ListArrayUtil.getLogger(BreadthFirstSearch.class, Level.INFO);
        
    }
    /**
     * Finds node inside of the given Tree and returns the path in the form a list of Tree nodes, starting with the root and ending with the target node
     * @param node
     * @param tree
     * @return a List giving the path, starting with the root and ending with the target (both inclusive); an empty list if no path was found
     */
    public static List<Tree> find(Tree target, Tree tree) {
        //logger.info("find(" + target + ", " + tree + ")");
        if(tree == null) {
            return new ArrayList<Tree>();
        }
        
        List<Tree> children = tree.getChildren();
        logger.debug("children.size() == " + children.size());
        
        ArrayList<Tree> queue = new ArrayList<Tree>();  //The list of upcoming nodes to look at.
        for(int i = 0; i < children.size(); i++) {
            queue.add(children.get(i));
        }
        
        Tree current = null;
        while(queue.size() > 0) {
            current = queue.get(0);
            logger.debug("looking at " + current);
            if(current.equals(target)) {
                return target.getPathFromRoot();
            } else {
                for(int j = 0; j < current.getChildren().size(); j++) {
                    //current wasn't the one we are looking for, but add it's children while we are here
                    queue.add(current.getChildren().get(j));
                }
                //now remove current from the queue because we no longer need to look at it
                queue.remove(current);
            }
        }
        
        return new LinkedList<Tree>();
    }
    
    public static List<Tree> find(Tree target, Tree tree, int level) {
        
        return null;
    }
}
