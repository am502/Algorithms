package ru.itis.lesson2;

import java.util.Objects;

public class Edge {
    private double a;
    private double b;
    private String info;

    public Edge(double a, double b, String info) {
        this.a = a;
        this.b = b;
        this.info = info;
    }

    public Edge(double a, double b) {
        this.a = a;
        this.b = b;
        this.info = "";
    }

    public double getA() {
        return a;
    }

    public double getB() {
        return b;
    }

    public String getInfo() {
        return info;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Edge edge = (Edge) o;
        return Double.compare(edge.a, a) == 0 &&
                Double.compare(edge.b, b) == 0 &&
                Objects.equals(info, edge.info);
    }

    @Override
    public int hashCode() {
        return Objects.hash(a, b, info);
    }
}
