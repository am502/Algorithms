package ru.itis.lesson1;

import java.util.ArrayList;
import java.util.Random;

public class Median {
    private static Random random = new Random();

    public static void main(String[] args) {

        int n = 1001;

        ArrayList<Integer> array = new ArrayList<>();

        for (int i = 0; i < n; i++) {
            array.add(i);
        }

        System.out.println(array);

        System.out.println("M = " + searchMedian(array, array.size() / 2));
    }

    public static int searchMedian(ArrayList<Integer> array, int k) {
        if (array.size() == 1) {
            return array.get(0);
        }
        ArrayList<Integer> left = new ArrayList<>();
        ArrayList<Integer> right = new ArrayList<>();

        int pivot = array.get(random.nextInt(array.size()));

        for (int i = 0; i < array.size(); i++) {
            if (pivot > array.get(i)) {
                left.add(array.get(i));
            } else if (pivot < array.get(i)) {
                right.add(array.get(i));
            }
        }

        if (k < left.size()) {
            return searchMedian(left, k);
        } else if (k < left.size() + 1) {
            return pivot;
        } else {
            return searchMedian(right, k - left.size() - 1);
        }
    }
}
