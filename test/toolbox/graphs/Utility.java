/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package toolbox.graphs;

import java.util.List;
import java.util.ArrayList;
import java.util.HashMap;

/**
 *
 * @author paul
 */
public class Utility {
    
    public static List<Node> generateGraph() {
        
        //based partly on graph on page 471 of Algorithms (1st edition)
        Node r = new Node("r");
        Node s = new Node("s");
        Node t = new Node("t");
        Node u = new Node("u");
        Node v = new Node("v");
        Node w = new Node("w");
        Node x = new Node("x");
        Node y = new Node("y");
        
        List<Node> graph = new ArrayList<Node>();
        graph.add(r);
        graph.add(s);
        graph.add(t);
        graph.add(u);
        graph.add(v);
        graph.add(w);
        graph.add(x);
        graph.add(y);
        
        r.addNeighbor(s, true);
        r.addNeighbor(v, true);
        s.addNeighbor(w, true);
        w.addNeighbor(t, true);
        w.addNeighbor(x, true);
        t.addNeighbor(u, true);
        t.addNeighbor(x, true);
        u.addNeighbor(y, true);
        y.addNeighbor(x, true);
        
        Node z = new Node("z");
        Node a = new Node("a");
        u.addNeighbor(z, true);
        u.addNeighbor(w, true);
        z.addNeighbor(a, true);
        graph.add(z);
        graph.add(a);
        return graph;
    }
    
    public static HashMap<Node, Node> getSampleTraversal() {
        HashMap<Node, Node> history = new HashMap<Node, Node>();
        List<Node> graph = generateGraph();
        
        Node r = graph.get(0);
        Node s = graph.get(1);
        Node t = graph.get(2);
        Node u = graph.get(3);
        Node v = graph.get(4);
        Node w = graph.get(5);
        Node x = graph.get(6);
        Node y = graph.get(7);
        Node z = graph.get(8);
        Node a = graph.get(9);
        
        history.put(r, s);
        history.put(w, s);
        history.put(t, w);
        history.put(x, w);
        history.put(v, r);
        history.put(u, t);
        history.put(y, x);
        history.put(z, u);
        history.put(a, z);
        return history;
    }
    
    /**
     * returns the graph on page 528 of Algorithms, 1st Edition
     * @return 
     */
    public static List<Node> getWeightedDirectedGraph1() {
        List<Node> graph = new ArrayList<Node>();
        
        Node s = new Node("s");
        Node u = new Node("u");
        Node v = new Node("v");
        Node x = new Node("x");
        Node y = new Node("y");
        
        graph.add(s);
        graph.add(u);
        graph.add(v);
        graph.add(x);
        graph.add(y);
        
        s.addNeighbor(u, 10, false);
        s.addNeighbor(x, 5, false);
        u.addNeighbor(v, 1, false);
        u.addNeighbor(x, 2, false);
        v.addNeighbor(y, 4, false);
        x.addNeighbor(u, 3, false);
        x.addNeighbor(v, 9, false);
        x.addNeighbor(y, 2, false);
        y.addNeighbor(s, 7, false);
        y.addNeighbor(v, 6, false);
        
        return graph;
    }
}
