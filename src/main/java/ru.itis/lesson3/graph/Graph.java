package ru.itis.lesson3.graph;

import java.util.ArrayList;

public class Graph {
    private static final int INF = 999;

    private int[][] matrix;
    private int size;

    public Graph(int size) {
        this.matrix = new int[size][size];
        this.size = size;
    }

    public void addEdge(int a, int b, int weight) {
        this.matrix[a][b] = weight;
        this.matrix[b][a] = weight;
    }

    public void addDirectedEdge(int a, int b, int weight) {
        this.matrix[a][b] = weight;
    }

    public ArrayList<int[][]> runFloyd() {
        ArrayList<int[][]> list = new ArrayList<>();
        int[][] p = new int[size][size];

        generateMatrix();
        int[][] matrixCopy = getMatrix();

        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                if (matrixCopy[i][j] >= 1 && matrixCopy[i][j] < INF) {
                    p[i][j] = j;
                }
            }
        }

        for (int k = 0; k < size; k++) {
            for (int i = 0; i < size; i++) {
                for (int j = 0; j < size; j++) {
                    if (matrixCopy[i][j] > matrixCopy[i][k] + matrixCopy[k][j]) {
                        matrixCopy[i][j] = matrixCopy[i][k] + matrixCopy[k][j];
                        p[i][j] = p[i][k];
                    }
                }
            }
        }

        list.add(matrixCopy);
        list.add(p);
        return list;
    }

    public ArrayList<int[]> runDijkstra(int number) {
        ArrayList<int[]> list = new ArrayList<>();
        int[] p = new int[size];

        generateMatrix();
        int[] dist = new int[size];
        int[] used = new int[size];
        used[number] = 1;
        for (int i = 0; i < size; i++) {
            dist[i] = INF;
        }
        dist[number] = 0;
        int count = 0;
        int g = 0;
        int ch = number;
        while (count != size - 1) {
            int min = INF;
            for (int i = 0; i < size; i++) {
                if (used[i] != 1) {
                    if (g + matrix[number][i] < dist[i]) {
                        dist[i] = g + matrix[number][i];
                        p[i] = number;
                    }
                    if (dist[i] < min) {
                        min = dist[i];
                        ch = i;
                    }
                }
            }
            number = ch;
            used[number] = 1;
            g = min;
            count++;
        }

        list.add(dist);
        list.add(p);
        return list;
    }

    private void generateMatrix() {
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                if (matrix[i][j] == 0 && i != j)
                    matrix[i][j] = INF;
            }
        }
    }

    public int[][] getMatrix() {
        int[][] matrixCopy = new int[size][size];
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                matrixCopy[i][j] = matrix[i][j];
            }
        }
        return matrixCopy;
    }
}
