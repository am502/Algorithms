package ru.itis.lesson3.graph;

public class Edge {
    private int a;
    private int b;

    public Edge(int a, int b) {
        this.a = a;
        this.b = b;
    }

    public int getA() {
        return a;
    }

    public int getB() {
        return b;
    }

    @Override
    public String toString() {
        return "(" + a + " " + b + ")";
    }
}
