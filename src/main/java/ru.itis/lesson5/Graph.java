package ru.itis.lesson5;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class Graph {
    private Random random;
    private HashMap<Integer, ArrayList<int[]>> vertexes;
    private int maxVertexNumber;
    private int totalWeight;

    public Graph() {
        this.vertexes = new HashMap<>();
        this.maxVertexNumber = 0;
        this.totalWeight = 0;
        this.random = new Random();
    }

    public Graph(HashMap<Integer, ArrayList<int[]>> vertexes, int maxVertexNumber, int totalWeight) {
        this.vertexes = vertexes;
        this.maxVertexNumber = maxVertexNumber;
        this.totalWeight = totalWeight;
        this.random = new Random();
    }

    public void addEdge(int from, int to, int weight) {
        addVertex(from, to, weight);
        addVertex(to, from, weight);
        totalWeight += weight;
    }

    private void addEdgeOneSide(int from, int to, int weight) {
        addVertex(from, to, weight);
    }

    private void addVertex(int from, int to, int weight) {
        int[] array = {to, weight};
        if (vertexes.containsKey(from)) {
            vertexes.get(from).add(array);
        } else {
            ArrayList<int[]> vertexLink = new ArrayList<>();
            vertexLink.add(array);
            vertexes.put(from, vertexLink);
            maxVertexNumber++;
        }
    }

    public void showVertexLinks() {
        StringBuilder sb = new StringBuilder();
        for (Map.Entry<Integer, ArrayList<int[]>> entry : vertexes.entrySet()) {
            sb.append(entry.getKey() + " : ");
            for (int[] ints : entry.getValue()) {
                sb.append(ints[0]).append(" ");
                sb.append("(").append(ints[1]).append(") ");
            }
            sb.append("\n");
        }
        System.out.println(sb);
    }

    public void removeAndConstrictRandomEdge() {
        int weight = random.nextInt(totalWeight) + 1;
        int tempMax = maxVertexNumber;
        int tempWeight = totalWeight;
        int a = -1;
        int b = -1;
        outer:
        for (Map.Entry<Integer, ArrayList<int[]>> entry : vertexes.entrySet()) {
            if (weight > 0) {
                for (int[] vertex : entry.getValue()) {
                    if (weight <= vertex[1]) {
                        a = entry.getKey();
                        b = vertex[0];
                        break outer;
                    } else {
                        weight -= vertex[1];
                    }
                }
            }
        }
        for (int[] vertex : vertexes.get(a)) {
            if (vertex[0] == a || vertex[0] == b) {
                tempWeight -= vertex[1];
            }
        }
        vertexes.remove(a);
        vertexes.remove(b);
        HashMap<Integer, ArrayList<int[]>> tempVertexes = getVertexes();
        vertexes = new HashMap<>();
        for (Map.Entry<Integer, ArrayList<int[]>> entry : tempVertexes.entrySet()) {
            for (int[] vertex : entry.getValue()) {
                if (vertex[0] == a || vertex[0] == b) {
                    addEdge(entry.getKey(), tempMax, vertex[1]);
                } else {
                    addEdgeOneSide(entry.getKey(), vertex[0], vertex[1]);
                }
            }
        }
        totalWeight = tempWeight;
        maxVertexNumber = tempMax + 1;
    }

    public int getMaxVertexNumber() {
        return maxVertexNumber;
    }

    public int getEdgesCount() {
        int count = 0;
        for (ArrayList<int[]> list : vertexes.values()) {
            count += list.size();
        }
        return count / 2;
    }

    public int getVertexCount() {
        return vertexes.size();
    }

    public int getTotalWeight() {
        return totalWeight;
    }

    public HashMap<Integer, ArrayList<int[]>> getVertexes() {
        HashMap<Integer, ArrayList<int[]>> vertexesClone = new HashMap<>();
        for (Map.Entry<Integer, ArrayList<int[]>> entry : vertexes.entrySet()) {
            ArrayList<int[]> vertexLinks = new ArrayList<>();
            for (int[] vertex : entry.getValue()) {
                int[] array = {vertex[0], vertex[1]};
                vertexLinks.add(array);
            }
            vertexesClone.put(entry.getKey(), vertexLinks);
        }
        return vertexesClone;
    }
}
