package ru.itis.lesson5;

import java.util.Scanner;

public class MinimumCut {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        Graph graph = new Graph();

        int size = sc.nextInt();
        int edgesCount = sc.nextInt();
        int[][] matrix = new int[size][size];
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                matrix[i][j] = sc.nextInt();
                if (j >= i && matrix[i][j] > 0) {
                    graph.addEdge(i, j, matrix[i][j]);
                }
            }
        }

        int min = 999;
        int minCutEdgesCount = 0;

        for (int i = 0; i < 5 * edgesCount * (edgesCount - 1); i++) {
            Graph tempGraph = new Graph(graph.getVertexes(), graph.getMaxVertexNumber(), graph.getTotalWeight());

            while (tempGraph.getVertexCount() > 2) {
                tempGraph.removeAndConstrictRandomEdge();
            }
            int sum = 0;
            int count = 0;
            for (int[] entry : tempGraph.getVertexes().get(tempGraph.getMaxVertexNumber() - 1)) {
                sum += entry[1];
                count++;
            }
            if (sum < min) {
                min = sum;
                minCutEdgesCount = count;
            }
        }

        System.out.println("Min weight = " + min);
        System.out.println("Edges = " + minCutEdgesCount);
    }
}
