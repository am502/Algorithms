package ru.itis.lesson2.trapezoid;

import java.util.ArrayList;
import java.util.HashMap;

public class Graph {
    private HashMap<Double, ArrayList<Double>> vertexes;

    public Graph() {
        this.vertexes = new HashMap<>();
    }

    public void addDirectedEdge(Node node) {
        addVertex(node.getFrom(), node.getTo());
    }

    private void addVertex(double from, double to) {
        if (vertexes.containsKey(from)) {
            getVertexLinks(from).add(to);
        } else {
            ArrayList<Double> vertexLink = new ArrayList<>();
            vertexLink.add(to);
            vertexes.put(from, vertexLink);
        }
    }

    public ArrayList<Double> getVertexLinks(double vertex) {
        return vertexes.get(vertex);
    }

    public HashMap<Double, ArrayList<Double>> getVertexes() {
        return vertexes;
    }
}
