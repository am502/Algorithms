package ru.itis.lesson2;

import java.util.ArrayList;
import java.util.Scanner;
import java.util.Stack;

public class Dominoes {
    private static ArrayList<Edge> edges = new ArrayList<>();

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        int n = sc.nextInt();

        Graph graph = new Graph(2 * n);

        for (int i = 0; i < n; i++) {
            double a = sc.nextDouble();
            double b = sc.nextDouble();
            Edge edge = new Edge(a, b, "(" + a + " " + b + ")");
            graph.addEdge(edge, 1);
            edges.add(edge);
        }

        int size = graph.getVertexCount();

        ArrayList<Integer> indexesOdd = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            int sum = 0;
            for (int j = 0; j < size; j++) {
                if (i != j) {
                    sum += graph.getMatrix()[i][j];
                }
            }
            if (sum % 2 == 1) {
                indexesOdd.add(i);
            }
        }

        if (indexesOdd.size() != 0) {
            System.out.println("No");
            System.exit(0);
        }

        Stack<Edge> stack = new Stack<>();
        Stack<Integer> markers = new Stack<>();
        stack.push(edges.get(0));
        int indexA = graph.getIndex(edges.get(0).getA());
        int indexB = graph.getIndex(edges.get(0).getB());
        markers.push(indexB);
        int[][] matrix = graph.getMatrix();
        matrix[indexA][indexB] = 0;
        matrix[indexB][indexA] = 0;

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
                    matrix[i][index] = 0;
                    path = true;
                }
            }
            if (!path) {
                System.out.println(stack.pop().getInfo());
                markers.pop();
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
}
