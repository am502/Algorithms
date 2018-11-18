package ru.itis.lesson2;

import java.util.ArrayList;
import java.util.Scanner;
import java.util.Stack;

public class Citites {
    private static ArrayList<Edge> edges = new ArrayList<>();

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        int n = sc.nextInt();

        Graph cities = new Graph(2 * n);

        for (int i = 0; i < n; i++) {
            String city = sc.next();
            Edge edge = new Edge(city.toLowerCase().charAt(0), city.charAt(city.toLowerCase().length() - 1), city);
            edges.add(edge);
            cities.addDirectedEgde(edge, 1);
        }

        int[][] matrix = cities.getMatrix();
        int size = cities.getVertexCount();

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
                System.out.println("No");
                System.exit(0);
            }
        }

        Edge fictEdge = null;
        if (indexP.size() == 1 && indexQ.size() == 1) {
            double a = cities.getElement(indexQ.get(0));
            double b = cities.getElement(indexP.get(0));
            fictEdge = new Edge(a, b);
            cities.addDirectedEgde(fictEdge, 1);
        } else if (indexP.size() > 1 || indexQ.size() > 1) {
            System.out.println("No");
            System.exit(0);
        }

        matrix = cities.getMatrix();

        Stack<Edge> stack = new Stack<>();
        Stack<Integer> markers = new Stack<>();
        Edge firstEdge = edges.get(0);
        if (fictEdge != null) {
            firstEdge = fictEdge;
        }
        stack.push(firstEdge);
        int indexA = cities.getIndex(firstEdge.getA());
        int indexB = cities.getIndex(firstEdge.getB());
        markers.push(indexB);
        matrix[indexA][indexB] = 0;

        ArrayList<Edge> result = new ArrayList<>();

        while (!stack.isEmpty()) {
            boolean path = false;
            int index = markers.peek();
            for (int i = 0; i < size; i++) {
                if (matrix[index][i] >= 1 && !path) {
                    double a = cities.getElement(index);
                    double b = cities.getElement(i);
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

        for (int i = result.size() - 1; i >= 0; i--) {
            System.out.println(result.get(i).getInfo());
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
