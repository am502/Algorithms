package ru.itis.lesson8;

import java.util.ArrayList;
import java.util.Arrays;

public class BestTeam {
    public static void main(String[] args) {
        double[][] players = {
                {1, 2, 3, 4, 5.5, 10, 11, 15, 27},
                {1, 1, 1, 1, 1, 1, 1, 99},
                {7, 20, 15, 13, 10, 6, 3},
                {2, 3, 4, 5, 6, 7, 8}
        };
        for (int i = 0; i < players.length; i++) {
            createTeam(players[i]);
        }
    }

    public static void createTeam(double[] players) {
        Arrays.sort(players);
        int n = players.length;

        double[] s = new double[n + 1];
        s[0] = 0;
        for (int i = 1; i < s.length; i++) {
            s[i] = s[i - 1] + players[i - 1];
        }

        ArrayList<Double> team = null;
        double efBest = 0;
        for (int i = 0; i < n - 1; i++) {
            int j = binarySearch(players, players[i] + players[i + 1]);
            double sum = s[j + 1] - s[i];
            if (sum > efBest) {
                efBest = sum;
                team = new ArrayList<>();
                for (int k = i; k < j + 1; k++) {
                    team.add(players[k]);
                }
            }
        }

        System.out.println("Result: " + efBest);
        System.out.print("Team: ");
        for (int i = 0; i < team.size() - 1; i++) {
            System.out.print(team.get(i) + ", ");
        }
        System.out.println(team.get(team.size() - 1) + "\n");
    }

    public static int binarySearch(double[] array, double element) {
        int low = 0;
        int high = array.length;
        int mid = 0;
        while (low < high) {
            mid = (low + high) / 2;
            if (array[mid] == element) {
                return mid;
            } else if (array[mid] < element) {
                low = mid + 1;
            } else if (array[mid] > element) {
                high = mid;
            }
        }
        while (array[mid] > element) {
            mid--;
        }
        return mid;
    }
}
