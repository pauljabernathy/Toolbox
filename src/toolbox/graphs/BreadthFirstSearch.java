/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package toolbox.graphs;

import java.util.List;
import java.util.ArrayList;
import java.util.LinkedList;
import org.apache.log4j.*;
import java.util.HashMap;

/**
 *
 * @author paul
 */
public class BreadthFirstSearch {
    
    private static Logger logger;
    
    static {
        logger = Logger.getLogger(toolbox.trees.BreadthFirstSearch.class);
        logger.addAppender(new ConsoleAppender(new PatternLayout("%m%n")));
        logger.setLevel(Level.INFO);
        
    }
    
    public static List<Node> find(Node target, Node start) {

        if(target == null || start == null) {
            return new ArrayList<Node>();
        }
        ArrayList<Node> queue = new ArrayList<Node>();
        ArrayList<Node> visited = new ArrayList<Node>();
        queue.add(start);
        for(int i = 0; i < start.getNeighbors().size(); i++) {
            //queue.add(start.getNeighbors().get(i));
        }
        
        ArrayList<Node> result = new ArrayList<Node>();
        Node current = null;
        while(queue.size() > 0) {
            current = queue.get(0);
            logger.debug("examining " + current);
            if(current == target) {
                //found it
                result.add(current);
                return result;
                //TODO:  need to return the path
            } else {
                //this isn't it, so add current's neighbors and remove current
                for(int i = 0; i < current.getNeighbors().size(); i++) {
                    if(!visited.contains(current.getNeighbors().get(i)) && !queue.contains(current.getNeighbors().get(i))) {
                        //add this neighbor if it has not been visited yet and is not on the queue
                        queue.add(current.getNeighbors().get(i));
                    }
                }
                queue.remove(current);
                visited.add(current);   //so we don't visit it again
            }
        }      
        
        return result;
    }
    
    public static List<Node> findWithPath(Node target, Node start) {
        return null;
    }
    
    /**
     * returns the path from start to target, using the visited map to trace the history of what node was visited from which node
     * visited must have the destination node as the key and the node it was visited from as the value
     * @param target
     * @param start
     * @param visited
     * @return 
     */
    public static LinkedList<Node> getPath(Node target, Node start, HashMap<Node, Node> visited) {
        logger.debug("getPath(" + target + ", " + start + ", history)");
        if(target == null || start == null || visited == null || visited.isEmpty()) {
            return new LinkedList<Node>();
        }
        if(!visited.containsKey(target)) {
            logger.debug("visited history did not contain " + target.getId());
            return new LinkedList<Node>();
        } else {
            visited = (HashMap<Node, Node>)visited.clone();
            LinkedList<Node> path = new LinkedList<Node>();
            Node parent = visited.get(target);
            logger.debug("parent of " + target.getId() + " is " + parent.getId());
            if(target.equals(start)) {
                path.add(target);
                return path;
            }
            if(parent.equals(start)) {
                //target was visited directly from start, so the chain is just the two nodes
                path.add(start);
                path.add(target);
                return path;
            }
            visited.remove(target);
            if(visited.size() > 0) {
                //There are still more visited nodes to examine.
                path = getPath(parent, start, visited);
                //path.add(parent);
            } else {
                //There are no more visited nodes, which means start is not here.
                //TODO:  what do do?
            }
            path.add(target);
            return path;
        }
    }
}
