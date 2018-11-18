package ru.itis.lesson6;

import java.util.ArrayList;

/**
 * Not working
 */
public class Bag {
    public static void main(String[] args) {
        int n = 6;
        int[] x = {1, 7, 9, 6, 2, 3};
        int sum = 0;
        for (int i = 0; i < n; i++) {
            sum += x[i];
        }
        if (sum % 2 == 1) {
            System.out.println("Разбить на 2 части нельзя.");
            System.exit(0);
        }

        int m = sum / 2;
        Boolean[] b = new Boolean[m + 1];
        ArrayList[] s = new ArrayList[m + 1];
        for (int i = 0; i < m + 1; i++) {
            s[i] = new ArrayList<Integer>();
            b[i] = false;
        }
        b[0] = true;

        for (int i = 0; i < n; i++) {
            for (int k = m; k >= x[i]; k--) {
                if (b[k - x[i]]) {
                    System.out.println("k = " + k + " x[i] = " + x[i]);
                    b[k] = true;
                    s[k].add(x[i]);
                }
            }
        }

        if (!b[m]) {
            System.out.println("Разбить на 2 части нельзя");
            System.exit(0);
        }

        for (int i = 0; i < s[m].size(); i++) {
            System.out.println(s[m].get(i));
        }
    }
}
