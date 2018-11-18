package ru.itis.lesson5;

import java.util.Random;

public class TaskWithRequests {
    private static final int AMOUNT = 1000000;

    public static void main(String[] args) {
        Random random = new Random();

        int M = 30;

        int count = 0;

        for (int i = 0; i < AMOUNT; i++) {
            double sum = 0;
            for (int j = 0; j < 3; j++) {
                double x = -M * Math.log(random.nextDouble());
                sum += x;
            }
            if (sum <= 60) {
                count++;
            }
        }

        System.out.println((double) count / AMOUNT);
    }
}
