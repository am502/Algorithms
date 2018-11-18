package ru.itis.lesson5;

import java.util.Arrays;
import java.util.Random;

public class Array {
    private static final int AMOUNT = 20;
    private static final int SIZE = 16;

    public static void main(String[] args) {
        int[] x = new int[SIZE];

        Random random = new Random();

        for (int i = 0; i < SIZE; i++) {
            if (i < SIZE / 2 - 1) {
                x[i] = i;
            } else {
                x[i] = SIZE;
            }
        }

        System.out.println(Arrays.toString(x));

        for (int i = 0; i < AMOUNT; i++) {
            int count = 0;
            int index = random.nextInt(SIZE);
            int a = x[index];
            for (int j = 0; j < SIZE; j++) {
                if (x[j] == a) {
                    count++;
                }
            }
            if (count > SIZE / 2) {
                System.out.println(a);
                System.exit(0);
            }
        }

        System.out.println("No");
    }
}
