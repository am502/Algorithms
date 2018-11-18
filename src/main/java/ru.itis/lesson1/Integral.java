package ru.itis.lesson1;

import java.util.Random;

public class Integral {

    static final int D = 4;
    static final int N = 100000;
    static Random random = new Random();

    public static void main(String[] args) {
        double[] points = new double[D];
        int count = 0;

        for (int i = 0; i < N; i++) {
            double denominator = 0;
            for (int j = 0; j < D; j++) {
                points[j] = random.nextDouble();
                denominator += points[j];
            }
            denominator += D;
            if (random.nextDouble() < (points[0] + 1) / denominator) {
                count++;
            }
        }

        System.out.println((double) count / N);
    }
}
