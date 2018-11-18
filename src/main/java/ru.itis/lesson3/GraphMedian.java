package ru.itis.lesson3;

import ru.itis.lesson3.graph.Graph;

import java.util.ArrayList;

public class GraphMedian {
    public static void main(String[] args) {
        int n = 6;

        Graph graph = new Graph(n);

        graph.addDirectedEdge(0, 1, 5);
        graph.addDirectedEdge(1, 2, 3);
        graph.addDirectedEdge(2, 3, 2);
        graph.addDirectedEdge(2, 0, 4);
        graph.addDirectedEdge(3, 4, 1);
        graph.addDirectedEdge(4, 2, 5);
        graph.addDirectedEdge(4, 5, 3);
        graph.addDirectedEdge(5, 0, 7);
        graph.addDirectedEdge(5, 1, 3);

        ArrayList<int[][]> list = graph.runFloyd();
        int[][] matrix = list.get(0);

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (matrix[i][j] > 9) {
                    System.out.print(matrix[i][j] + " ");
                } else {
                    System.out.print(matrix[i][j] + "  ");
                }
            }
            System.out.println();
        }

        int minInMedian = 999;
        int indexInMedian = -1;

        int minOutMedian = 999;
        int indexOutMedian = -1;

        for (int i = 0; i < n; i++) {
            int sumInMedian = 0;
            int sumOutMedian = 0;
            for (int j = 0; j < n; j++) {
                sumInMedian += matrix[i][j];
                sumOutMedian += matrix[j][i];
            }
            if (sumInMedian < minInMedian) {
                minInMedian = sumInMedian;
                indexInMedian = i;
            }
            if (sumOutMedian < minOutMedian) {
                minOutMedian = sumOutMedian;
                indexOutMedian = i;
            }
        }
        System.out.println("index in median = " + indexInMedian);
        for (int i = 0; i < n; i++) {
            if (indexInMedian != i) {
                System.out.println("from " + indexInMedian + " to " + i + " "
                        + getShortestPath(indexInMedian, i, list.get(1)));
            }
        }
        System.out.println("index out median = " + indexOutMedian);
        for (int i = 0; i < n; i++) {
            if (indexOutMedian != i) {
                System.out.println("from " + indexOutMedian + " to " + i + " "
                        + getShortestPath(indexOutMedian, i, list.get(1)));
            }
        }

        int minInCenter = 999;
        int indexInCenter = -1;

        int minOutCenter = 999;
        int indexOutCenter = -1;

        for (int i = 0; i < n; i++) {
            int maxInCenter = -1;
            int maxOutCenter = -1;
            for (int j = 0; j < n; j++) {
                if (matrix[i][j] > maxInCenter) {
                    maxInCenter = matrix[i][j];
                }
                if (matrix[j][i] > maxOutCenter) {
                    maxOutCenter = matrix[j][i];
                }
            }
            if (maxInCenter < minInCenter) {
                minInCenter = maxInCenter;
                indexInCenter = i;
            }
            if (maxOutCenter < minOutCenter) {
                minOutCenter = maxOutCenter;
                indexOutCenter = i;
            }
        }
        System.out.println("index in center = " + indexInCenter);
        for (int i = 0; i < n; i++) {
            if (indexInCenter != i) {
                System.out.println("from " + indexInCenter + " to " + i + " "
                        + getShortestPath(indexInCenter, i, list.get(1)));
            }
        }
        System.out.println("index out center = " + indexOutCenter);
        for (int i = 0; i < n; i++) {
            if (indexOutCenter != i) {
                System.out.println("from " + indexOutCenter + " to " + i + " "
                        + getShortestPath(indexOutCenter, i, list.get(1)));
            }
        }
    }

    private static ArrayList<Integer> getShortestPath(int u, int v, int[][] p) {
        ArrayList<Integer> path = new ArrayList<>();
        path.add(u);
        while (u != v) {
            u = p[u][v];
            path.add(u);
        }
        return path;
    }
}
