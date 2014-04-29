/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package toolbox.graphs;

import org.apache.log4j.*;
import java.util.List;
import java.util.LinkedList;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.PriorityQueue;

/**
 *
 * @author paul
 */
public class ShortestPath {
    
    private static Logger logger;
    static {
        logger = Logger.getLogger(toolbox.graphs.ShortestPath.class);
        logger.addAppender(new ConsoleAppender(new PatternLayout("%m%n")));
        logger.setLevel(Level.INFO);
    }
    
    public static List<List<Node>> getOneToAllPaths(Node source) {
        
        return null;
    }
    
    /**
     * an attempt to make a shortest path algorithm that can handle negative paths like Bellman/Ford, not yet complete or correct
     * @param source
     * @return 
     */
    public static HashMap<Node, Node> getOneToAllPathsMap(Node source) {
        
        LinkedList<Node> queue = new LinkedList<Node>();
        HashMap<Node, Node> parents = new HashMap<Node, Node>();        //parent in best path to the Node (child/target is key, parent/source is value)
        HashMap<Node, Integer> weights = new HashMap<Node, Integer>();  //total distance/cost/weight to the Node, using the corresponding parent
        parents.put(source, null);
        weights.put(source, 0);

        for(int i = 0; i < source.getNeighbors().size(); i++) {
            queue.add(source.getNeighbors().get(i));
        }
        
        while(queue.size() > 0) {
            Node current = queue.getFirst();
            int currentWeight = weights.get(current);   //total best weight/cost/distance to the Node currently being expanded
            List<Node> currentNeighbors = current.getNeighbors();
            List<Integer> neighborWeights = current.getWeights();
            for(int i = 0; i < currentNeighbors.size(); i++) {
                Node currentNeighbor = current.getNeighbors().get(i);
                int currentNeighborWeight = neighborWeights.get(i);     //edge weight from current to currentNeighbor
                if(!queue.contains(currentNeighbor)) {
                    queue.addLast(currentNeighbor);
                }
                if(weights.get(currentNeighbor) == null) {
                    //have not visited currentNeighbor yet, so this is the best path so far
                    parents.put(current, currentNeighbor);
                    weights.put(currentNeighbor, currentWeight + currentNeighborWeight);
                }
                if(currentWeight + currentNeighborWeight < weights.get(currentNeighbor)) {
                    //We visited currentNeighbor before but this path is better than the old one
                    parents.remove(currentNeighbor);
                    parents.put(current, currentNeighbor);
                    weights.remove(currentNeighbor);
                    weights.put(currentNeighbor, currentWeight + currentNeighborWeight);
                }
            }
        }
        
        return null;
    }
    
    public static List<LinkedList<Edge>> getDijkstraPaths(Node source) {
        //TODO:  check for negative weight paths
        
        HashMap<Node, LinkedList<Edge>> paths = new HashMap<Node, LinkedList<Edge>>();  //the paths, with the destination node as the key
        
        PriorityQueue<Edge> queue = new PriorityQueue<Edge>();
        ArrayList<Node> finalized = new ArrayList<Node>();
        HashMap<Node, Node> parents = new HashMap<Node, Node>();        //parent in best path to the Node (child/target is key, parent/source is value)
        parents.put(source, null);
        
        //have a set of nodes that have their path fully set
        //follow the shortest path out from that set and add that node to the finalized group
        
        Edge dummyEdgeToSource = new Edge(null, source, 0);
        finalized.add(source);
        LinkedList<Edge> dummyPathToSource = new LinkedList<Edge>();
        dummyPathToSource.add(dummyEdgeToSource);
        paths.put(source, dummyPathToSource);
        for(int i = 0; i < source.getEdges().size(); i++) {
            logger.debug(source);
            //logger.debug(source.getNeighbors().get(i));
            //logger.debug(source.getWeights().get(i));
            Edge current = source.getEdges().get(i);//new Edge(source, source.getNeighbors().get(i), source.getWeights().get(i));
            logger.debug("adding edge " + current + " to queue");
            queue.add(current);
        }
        while(!queue.isEmpty()) {
            Edge smallest = queue.poll();
            if(finalized.contains(smallest.destination)) {
                //if this was added to the queue before a better one was found
                //queue.remove(smallest.destination);
                continue;
            }
            //The destination of this edge should be added to the group of finalized nodes.
            finalized.add(smallest.destination);
            logger.debug(smallest + " was the smallest");
            //parents.put(smallest.destination, smallest.source); //parent of smallest.destination is smallest.source
            //weights.put(smallest.destination, smallest.weight);
            LinkedList<Edge> pathToSmallest = (LinkedList<Edge>)paths.get(smallest.source).clone();
            pathToSmallest.add(smallest);
            paths.put(smallest.destination, pathToSmallest);
            List<Edge> edgesFromSmallest = smallest.destination.getEdges();
            for(int i = 0; i < edgesFromSmallest.size(); i++) {
                Edge current = edgesFromSmallest.get(i);
                if(!finalized.contains(current.destination)) {
                    logger.debug("adding edge " + current + " to queue");
                    queue.add(new Edge(smallest.destination, current.destination, current.weight));
                }
            }
        }
        
        List<LinkedList<Edge>> result = new ArrayList<LinkedList<Edge>>();
        /*for(Edge edge: finalized) {
            result.add(paths.get(edge));
        }*/
        java.util.Iterator<LinkedList<Edge>> iterator = paths.values().iterator();
        while(iterator.hasNext()) {
            result.add(iterator.next());
        }
        return result;
        
    }
    
    /**
     * requires no negative weight paths
     * @param source
     * @return 
     */
    public static HashMap<Node, Node> getDijkstraMap(Node source) {
        //TODO:  check for negative weight paths
        
        PriorityQueue<Edge> queue = new PriorityQueue<Edge>();
        ArrayList<Node> finalized = new ArrayList<Node>();
        HashMap<Node, Node> parents = new HashMap<Node, Node>();        //parent in best path to the Node (child/target is key, parent/source is value)
        parents.put(source, null);
        
        //have a set of nodes that have their path fully set
        //follow the shortest path out from that set and add that node to the finalized group
        
        finalized.add(source);
        for(int i = 0; i < source.getEdges().size(); i++) {
            logger.debug(source);
            //logger.debug(source.getNeighbors().get(i));
            //logger.debug(source.getWeights().get(i));
            Edge current = source.getEdges().get(i);//new Edge(source, source.getNeighbors().get(i), source.getWeights().get(i));
            logger.debug("adding edge " + current + " to queue");
            queue.add(current);
        }
        while(!queue.isEmpty()) {
            Edge smallest = queue.poll();
            if(finalized.contains(smallest.destination)) {
                //should be redundant
                continue;
            }
            //The destination of this edge should be added to the group of finalized nodes.
            finalized.add(smallest.destination);
            parents.put(smallest.destination, smallest.source); //parent of smallest.destination is smallest.source
            List<Edge> edgesFromSmallest = smallest.destination.getEdges();
            for(int i = 0; i < edgesFromSmallest.size(); i++) {
                Edge current = edgesFromSmallest.get(i);
                if(!finalized.contains(current.destination)) {
                    logger.debug("adding edge " + current + " to queue");
                    queue.add(new Edge(smallest.destination, current.destination, current.weight));
                }
            }
        }
        return parents;
    }
}
