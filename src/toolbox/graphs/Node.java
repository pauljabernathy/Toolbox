/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package toolbox.graphs;

import java.util.List;
import java.util.ArrayList;

/**
 *
 * @author paul
 */
public class Node {
    
    private String id;
    //TODO:  remove neighbors and weights and use only edges?  keeping for now to make accessing neighbor list with getNeighbors() faster
    private List<Node> neighbors;
    private List<Integer> weights;
    private List<Edge> edges;
    
    public Node(String id) {
        this(id, new ArrayList<Node>());
    }
    
    public Node(String id, List<Node> neighbors) {
        this.id = id;
        this.neighbors = neighbors;
        this.weights = new ArrayList<Integer>();
        for(int i = 0; i < neighbors.size(); i++) {
            weights.add(1);
        }
        this.edges = new ArrayList<Edge>();
        for(int i = 0; i < neighbors.size(); i++) {
            edges.add(new Edge(this, neighbors.get(i), weights.get(i)));
        }
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public List<Node> getNeighbors() {
        return neighbors;
    }
    
    public void addNeighbor(Node newNeighbor, boolean bidirectional) {
        addNeighbor(newNeighbor, 1, bidirectional);
    }
    
    public void addNeighbor(Node newNeighbor, int weight, boolean bidirectional) {
        if(newNeighbor != null && !this.neighbors.contains(newNeighbor)) {
            this.neighbors.add(newNeighbor);
            this.weights.add(weight);
            if(bidirectional) {
                newNeighbor.addNeighbor(this, weight, true);        // This should be done only if edges are non directed
            }
            this.edges.add(new Edge(this, newNeighbor, weight));
        }
    }
    
    public List<Integer> getWeights() {
        return this.weights;
    }
    
    public List<Edge> getEdges() {
        return this.edges;
    }
    
    public String toString() {
        return this.id;
    }
    
    public boolean equals(Object o) {
        if(o instanceof Node) {
            return this.id.equals(((Node)(o)).getId());
        } else {
            return false;
        }
    }
    
    public int hashCode() {
        return this.id.hashCode();
    }
}
