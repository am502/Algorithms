package ru.itis.lesson2.trapezoid;

import java.util.*;

public class TaskA {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        Graph graph = new Graph();

        int n = sc.nextInt();

        ArrayList<Node> start = new ArrayList<>();
        ArrayList<Node> end = new ArrayList<>();

        for (int i = 0; i < n; i++) {
            double b = sc.nextDouble();
            double c = sc.nextDouble();
            double d = sc.nextDouble();
            Node node = new Node(b, c - d);
            graph.addDirectedEdge(node);
            if (b == 0) {
                start.add(node);
            }
            if (c - d == 0) {
                end.add(node);
            }
        }
        if (start.size() == 0 || end.size() == 0 || start.size() != end.size()) {
            System.out.println("No, no start or end");
            System.exit(0);
        }

        HashMap<Double, ArrayList<Double>> vertexes = graph.getVertexes();

        ArrayList<Double> p = new ArrayList<>();
        ArrayList<Double> q = new ArrayList<>();

        for (Map.Entry<Double, ArrayList<Double>> entry : vertexes.entrySet()) {
            double currentVertex = entry.getKey();
            int outdeg = entry.getValue().size();
            int indeg = 0;
            for (Double vertex : vertexes.keySet()) {
                for (Double connectedVertex : graph.getVertexLinks(vertex)) {
                    if (connectedVertex == currentVertex) {
                        indeg++;
                    }
                }
            }
            if (indeg == outdeg + 1) {
                q.add(currentVertex);
            } else if (indeg == outdeg - 1) {
                p.add(currentVertex);
            } else if (indeg != outdeg) {
                System.out.println("No, no Euler cycle or path");
                System.exit(0);
            }
        }

        Node fictEdge = null;
        if (q.size() == 1 && p.size() == 1) {
            fictEdge = new Node(p.get(0), q.get(0));
            graph.addDirectedEdge(fictEdge);
        } else if (q.size() > 1 || p.size() > 1) {
            System.out.println("No, no Euler cycle or path");
            System.exit(0);
        }

        for (int i = 0; i < start.size(); i++) {
            Stack<Node> stack = new Stack<>();
            double from = start.get(i).getFrom();
            double to = start.get(i).getTo();
            Node firstEdge = new Node(from, to);
            graph.getVertexLinks(from).remove(to);
            stack.push(firstEdge);

            ArrayList<Node> result = new ArrayList<>();

            while (!stack.isEmpty()) {
                double currentVertex = stack.peek().getTo();

                ArrayList<Double> currentList = vertexes.get(currentVertex);
                if (currentList.size() > 0) {
                    stack.push(new Node(currentVertex, currentList.get(0)));
                    currentList.remove(0);
                } else {
                    result.add(stack.pop());
                }
            }

            if (fictEdge != null) {
                result.remove(fictEdge);
            }

            if (result.get(0).getTo() == 0) {
                StringBuilder sb = new StringBuilder();
                for (int j = result.size() - 1; j >= 0; j--) {
                    sb.append(result.get(j).getVertexInfo());
                    sb.append(' ');
                }
                System.out.println(sb.toString());
            }
        }
    }
}
