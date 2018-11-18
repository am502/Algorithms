package ru.itis.lesson2;

import java.util.ArrayList;
import java.util.Scanner;
import java.util.Stack;

public class TaskA {
    private static ArrayList<Edge> edges = new ArrayList<>();

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        int n = sc.nextInt();

        Graph graph = new Graph(2 * n);

        ArrayList<Edge> start = new ArrayList<>();
        ArrayList<Edge> end = new ArrayList<>();

        for (int i = 0; i < n; i++) {
            double b = sc.nextDouble();
            double c = sc.nextDouble();
            double d = sc.nextDouble();
            Edge edge = new Edge(b, c - d, "(" + b + " " + (c - d) + ")");
            edges.add(edge);
            graph.addDirectedEgde(edge, 1);
            if (b == 0) {
                start.add(edge);
            }
            if (c - d == 0) {
                end.add(edge);
            }
        }

        if (start.size() == 0 || end.size() == 0 || start.size() != end.size()) {
            System.out.println("No, no start or end");
            System.exit(0);
        }

        int[][] matrix = graph.getMatrix();
        int size = graph.getVertexCount();

        ArrayList<Integer> indexP = new ArrayList<>();
        ArrayList<Integer> indexQ = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            int sumLine = 0;
            int sumColumn = 0;
            for (int j = 0; j < size; j++) {
                if (i != j) {
                    sumLine += matrix[i][j];
                    sumColumn += matrix[j][i];
                }
            }
            if (sumLine - sumColumn == 1) {
                indexP.add(i);
            } else if (sumLine - sumColumn == -1) {
                indexQ.add(i);
            } else if (sumLine != sumColumn) {
                System.out.println("No, no Euler cycle or path");
                System.exit(0);
            }
        }

        Edge fictEdge = null;
        if (indexP.size() == 1 && indexQ.size() == 1) {
            double a = graph.getElement(indexQ.get(0));
            double b = graph.getElement(indexP.get(0));
            fictEdge = new Edge(a, b);
            graph.addDirectedEgde(fictEdge, 1);
        } else if (indexP.size() > 1 || indexQ.size() > 1) {
            System.out.println("No, no Euler cycle or path");
            System.exit(0);
        }

        for (int k = 0; k < start.size(); k++) {
            Stack<Edge> stack = new Stack<>();
            Stack<Integer> markers = new Stack<>();
            matrix = graph.getMatrix();
            Edge firstEdge = start.get(k);
            stack.push(firstEdge);
            int indexA = graph.getIndex(firstEdge.getA());
            int indexB = graph.getIndex(firstEdge.getB());
            markers.push(indexB);
            matrix[indexA][indexB] = 0;

            ArrayList<Edge> result = new ArrayList<>();

            while (!stack.isEmpty()) {
                boolean path = false;
                int index = markers.peek();
                for (int i = 0; i < size; i++) {
                    if (matrix[index][i] >= 1 && !path) {
                        double a = graph.getElement(index);
                        double b = graph.getElement(i);
                        Edge edge = getEdge(a, b);
                        stack.push(edge);
                        markers.push(i);
                        matrix[index][i] = 0;
                        path = true;
                    }
                }
                if (!path) {
                    result.add(stack.pop());
                    markers.pop();
                }
            }

            if (fictEdge != null) {
                result.remove(fictEdge);
            }

            if (checkMatrix(size, matrix)) {
                for (int i = result.size() - 1; i >= 0; i--) {
                    System.out.print(result.get(i).getInfo() + " ");
                }
                System.out.println();
            }
        }
    }

    private static void showMatrix(int n, int[][] matrix) {
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                System.out.print(matrix[i][j] + " ");
            }
            System.out.println();
        }
    }

    private static Edge getEdge(double a, double b) {
        for (int i = 0; i < edges.size(); i++) {
            if (edges.get(i).getA() == a && edges.get(i).getB() == b) {
                return edges.get(i);
            }
        }
        return edges.get(0);
    }

    private static boolean checkMatrix(int n, int[][] matrix) {
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (matrix[i][j] > 0) {
                    return false;
                }
            }
        }
        return true;
    }
}
