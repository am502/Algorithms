package ru.itis.lesson2;

import java.util.ArrayList;
import java.util.Iterator;

public class Graph {
    private int matrix[][];
    private ArrayList<Double> vertexes;

    public Graph(int n) {
        matrix = new int[n][n];
        vertexes = new ArrayList<>();
    }

    public void addEdge(Edge edge, int weight) {
        if (!vertexes.contains(edge.getA())) {
            vertexes.add(edge.getA());
        }
        if (!vertexes.contains(edge.getB())) {
            vertexes.add(edge.getB());
        }
        int indexA = getIndex(edge.getA());
        int indexB = getIndex(edge.getB());
        matrix[indexA][indexB] = weight;
        matrix[indexB][indexA] = weight;
    }

    public void addDirectedEgde(Edge edge, int weight) {
        if (!vertexes.contains(edge.getA())) {
            vertexes.add(edge.getA());
        }
        if (!vertexes.contains(edge.getB())) {
            vertexes.add(edge.getB());
        }
        int indexA = getIndex(edge.getA());
        int indexB = getIndex(edge.getB());
        matrix[indexA][indexB] = weight;
    }

    public int getIndex(double element) {
        Iterator<Double> iterator = vertexes.iterator();
        int count = 0;
        while (iterator.hasNext()) {
            if (iterator.next() == element) {
                return count;
            } else {
                count++;
            }
        }
        return -1;
    }

    public double getElement(int index) {
        Iterator<Double> iterator = vertexes.iterator();
        int count = 0;
        while (iterator.hasNext()) {
            double element = iterator.next();
            if (count == index) {
                return element;
            } else {
                count++;
            }
        }
        return -1;
    }

    public int[][] getMatrix() {
        int[][] matrixCopy = new int[vertexes.size()][vertexes.size()];
        for (int i = 0; i < vertexes.size(); i++) {
            for (int j = 0; j < vertexes.size(); j++) {
                matrixCopy[i][j] = matrix[i][j];
            }
        }
        return matrixCopy;
    }

    public int getVertexCount() {
        return vertexes.size();
    }

    public void showVertexes() {
        Iterator iterator = vertexes.iterator();
        while (iterator.hasNext()) {
            System.out.print(iterator.next() + " ");
        }
        System.out.println();
    }
}
