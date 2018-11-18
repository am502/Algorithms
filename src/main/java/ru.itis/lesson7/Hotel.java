package ru.itis.lesson7;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

public class Hotel {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        int n = sc.nextInt();
        int[][] array = new int[n][3];
        HashMap<Integer, ArrayList<Integer>> map = new HashMap<>();

        for (int i = 0; i < n; i++) {
            array[i][0] = sc.nextInt();
            array[i][1] = sc.nextInt();
            array[i][2] = sc.nextInt();
            if (map.containsKey(array[i][1])) {
                map.get(array[i][1]).add(i);
            } else {
                ArrayList<Integer> list = new ArrayList<>();
                list.add(i);
                map.put(array[i][1], list);
            }
        }

        show(array);

        int[] f = new int[n + 1];
        f[0] = 0;
        for (int i = 1; i < f.length; i++) {
            f[i] = f[i - 1];
            if (map.containsKey(i)) {
                for (Integer j : map.get(i)) {
                    f[i] = Math.max(f[array[j][0]] + array[j][2], f[i - 1]);
                    break;
                }
            }
        }
        System.out.println(f[n]);
    }

    public static void show(int[][] array) {
        for (int i = 0; i < array.length; i++) {
            System.out.print("from " + array[i][0] + " to " + array[i][1] + " | c = " + array[i][2] + "\n");
        }
    }
}
