package ru.itis.lesson2.trapezoid;

import java.util.Objects;

public class Node {
    private double from;
    private double to;
    private String info;

    public Node(double from, double to, String info) {
        this.from = from;
        this.to = to;
        this.info = info;
    }

    public Node(double from, double to) {
        this.from = from;
        this.to = to;
        this.info = "";
    }

    public double getFrom() {
        return from;
    }

    public double getTo() {
        return to;
    }

    public String getInfo() {
        return info;
    }

    public String getVertexInfo() {
        return new StringBuilder()
                .append('(')
                .append(from)
                .append(',')
                .append(' ')
                .append(to)
                .append(')')
                .toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Node node = (Node) o;
        return Double.compare(node.from, from) == 0 &&
                Double.compare(node.to, to) == 0 &&
                Objects.equals(info, node.info);
    }

    @Override
    public int hashCode() {
        return Objects.hash(from, to, info);
    }
}
