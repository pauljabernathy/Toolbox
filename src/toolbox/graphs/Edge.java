/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package toolbox.graphs;

/**
 *
 * @author paul
 */
public class Edge implements Comparable {
    
    public Node source;
    public Node destination;
    public int weight;
    
    //TODO:  throw an exception when someone tries to put in a null source or destination?
    public Edge(Node source, Node destination, int weight) {
        this.source = source;
        this.destination = destination;
        this.weight = weight;
    }

    public Node getSource() {
        return source;
    }

    public void setSource(Node source) {
        this.source = source;
    }

    public Node getDestination() {
        return destination;
    }

    public void setDestination(Node destination) {
        this.destination = destination;
    }

    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }
    
    @Override
    public int compareTo(Object o) {
        if(o instanceof Edge) {
            Edge e = (Edge)o;
            if(this.weight < e.weight) {
                return -1;
            } else if(this.weight == e.weight) {
                return 0;
            } else {
                return 1;
            }
        } else {
            return 0;
        }
    }
    
    public String toString() {
        return (source != null?source.getId():"null") + " -- " + weight + " --> " + destination.getId();
    }
    
    public boolean equals(Object o) {
        if(o instanceof Edge) {
            Edge e = (Edge)o;
            if(e.source == this.source && e.weight == this.weight && e.destination == this.destination) {
                return true;
            }
        }
        return false;
    }
    
    public int hashcode() {
        return this.source.hashCode() + this.weight + this.destination.hashCode();
    }
}
