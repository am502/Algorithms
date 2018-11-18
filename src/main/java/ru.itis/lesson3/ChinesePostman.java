package ru.itis.lesson3;

import ru.itis.lesson3.graph.Edge;
import ru.itis.lesson3.graph.Graph;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Stack;

public class ChinesePostman {
    public static void main(String[] args) {
        int n = 6;
//        n += 2;

        Graph graph = new Graph(n);

        graph.addEdge(0, 1, 1);
        graph.addEdge(0, 3, 2);
        graph.addEdge(1, 2, 3);
        graph.addEdge(1, 3, 5);
        graph.addEdge(2, 5, 2);
        graph.addEdge(2, 4, 6);
        graph.addEdge(5, 4, 1);
        graph.addEdge(4, 3, 4);

//        graph.addEdge(0, 6, 3);
//        graph.addEdge(3, 6, 4);
//        graph.addEdge(4, 7, 3);
//        graph.addEdge(5, 7, 2);
//        graph.addEdge(6, 7, 2);

        int[][] matrix = graph.getMatrix();

        ArrayList<Integer> odds = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            int count = 0;
            for (int j = 0; j < n; j++) {
                if (matrix[i][j] > 0) {
                    count++;
                }
            }
            if (count % 2 == 1) {
                odds.add(i);
            }
        }

        int[][] floydMatrix = graph.runFloyd().get(0);

        ArrayList<ArrayList<Integer>> paths = permutation(odds);
        int min = 999;
        ArrayList<Integer> minArray = new ArrayList<>();
        for (int i = 0; i < paths.size(); i++) {
            int sum = 0;
            ArrayList<Integer> curArray = paths.get(i);
            for (int j = 0; j < curArray.size(); j += 2) {
                int first = curArray.get(j);
                int second = curArray.get(j + 1);
                sum += floydMatrix[first][second];
            }
            if (sum < min) {
                min = sum;
                minArray = curArray;
            }
        }

        System.out.println(minArray);

        ArrayList<ArrayList<Integer>> shortestPaths = new ArrayList<>();
        for (int i = 0; i < minArray.size(); i += 2) {
            int first = minArray.get(i);
            int second = minArray.get(i + 1);
            ArrayList<int[]> distances = graph.runDijkstra(first);
            shortestPaths.add(getShortestPath(first, second, distances.get(1)));
        }

        int[][][] matrixMultipleEdges = new int[n][n][2];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                matrixMultipleEdges[i][j][0] = matrix[i][j];
                matrixMultipleEdges[i][j][1] = 0;
            }
        }
        for (int i = 0; i < shortestPaths.size(); i++) {
            ArrayList<Integer> cur = shortestPaths.get(i);
            for (int j = 0; j < cur.size(); j++) {
                System.out.print(cur.get(j) + " ");
                if (j != cur.size() - 1) {
                    int first = cur.get(j);
                    int second = cur.get(j + 1);
                    matrixMultipleEdges[first][second][1] = 1;
                    matrixMultipleEdges[second][first][1] = 1;
                }
            }
            System.out.println();
        }

        ArrayList<Edge> result = new ArrayList<>();

        Stack<Edge> stack = new Stack<>();
        int first = 0;
        int second = 1;
        if (matrixMultipleEdges[first][second][1] == 1) {
            matrixMultipleEdges[first][second][1] = 0;
            matrixMultipleEdges[second][first][1] = 0;
        } else {
            matrixMultipleEdges[first][second][0] = 0;
            matrixMultipleEdges[second][first][0] = 0;
        }
        stack.push(new Edge(first, second));
        while (!stack.isEmpty()) {
            boolean path = false;
            int index = stack.peek().getB();
            for (int i = 0; i < n; i++) {
                if (matrixMultipleEdges[index][i][0] >= 1 && !path) {
                    if (matrixMultipleEdges[index][i][1] == 1) {
                        matrixMultipleEdges[index][i][1] = 0;
                        matrixMultipleEdges[i][index][1] = 0;
                    } else {
                        matrixMultipleEdges[index][i][0] = 0;
                        matrixMultipleEdges[i][index][0] = 0;
                    }
                    stack.push(new Edge(index, i));
                    path = true;
                }
            }
            if (!path) {
                result.add(stack.pop());
            }
        }

        Collections.reverse(result);

        System.out.println(result);
    }

    private static ArrayList<Integer> getShortestPath(int from, int to, int[] p) {
        ArrayList<Integer> path = new ArrayList<>();
        path.add(to);
        int v = to;
        while (v != from) {
            path.add(p[v]);
            v = p[v];
        }
        Collections.reverse(path);
        return path;
    }

    private static ArrayList<ArrayList<Integer>> permutation(ArrayList<Integer> array) {
        ArrayList<ArrayList<Integer>> list = new ArrayList<>();
        if (array.size() == 2) {
            ArrayList<Integer> twoList = new ArrayList<>();
            twoList.add(array.get(0));
            twoList.add(array.get(1));
            ArrayList<ArrayList<Integer>> listTwoList = new ArrayList<>();
            listTwoList.add(twoList);
            return listTwoList;
        } else {
            int firstIndex = 0;
            int secondIndex = 1;
            while (firstIndex != array.size() - 3) {
                ArrayList<Integer> subList = new ArrayList<>();
                subList.add(array.get(firstIndex));
                subList.add(array.get(secondIndex));
                ArrayList<Integer> subArray = new ArrayList<>();
                for (int i = 0; i < array.size(); i++) {
                    if (i != firstIndex && i != secondIndex) {
                        subArray.add(array.get(i));
                    }
                }
                ArrayList<ArrayList<Integer>> listF = permutation(subArray);
                for (int i = 0; i < listF.size(); i++) {
                    listF.get(i).addAll(0, subList);
                    list.add(listF.get(i));
                }
                secondIndex++;
                if (secondIndex == array.size()) {
                    firstIndex++;
                    secondIndex = firstIndex + 1;
                }
            }
        }
        return list;
    }
}
