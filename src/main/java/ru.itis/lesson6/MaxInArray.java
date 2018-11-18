package ru.itis.lesson6;

import java.util.Random;

public class MaxInArray {
    private static final Random RND = new Random();

    public static void main(String[] args) {
        int n = 1000000;

        int[] array = new int[n];

        for (int i = 0; i < n; i++) {
            array[i] = i + 1;
        }

        int k = 100;
        int count = 0;
        for (int i = 0; i < k; i++) {
            shuffle(array);
            int max = array[0];
            for (int j = 1; j < n; j++) {
                if (array[j] > max) {
                    max = array[j];
                    count++;
                }
            }
        }
        System.out.println(1 + (double) count / k);
        System.out.println(Math.log(n) + 1);
    }

    public static void shuffle(int[] array) {
        int n = array.length;
        for (int i = n - 1; i > 0; i--) {
            int j = RND.nextInt(i);
            int temp = array[i];
            array[i] = array[j];
            array[j] = temp;
        }
    }
}
