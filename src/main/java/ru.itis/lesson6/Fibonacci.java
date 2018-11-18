package ru.itis.lesson6;

import java.math.BigInteger;

public class Fibonacci {
    public static void main(String[] args) {
        int n = 150000;

        long start = System.currentTimeMillis();
        BigInteger f1 = fibonacci1(n);
        System.out.println("time = " + (System.currentTimeMillis() - start));
        System.out.println(f1);

        start = System.currentTimeMillis();
        BigInteger f2 = fibonacci2(n);
        System.out.println("time = " + (System.currentTimeMillis() - start));
        System.out.println(f2);

        start = System.currentTimeMillis();
        BigInteger f3 = fibonacci3(n);
        System.out.println("time = " + (System.currentTimeMillis() - start));
        System.out.println(f3);
    }

    public static BigInteger fibonacci1(int n) {
        n += 1;
        BigInteger[] f = new BigInteger[n];
        f[0] = BigInteger.ZERO;
        f[1] = BigInteger.ONE;
        for (int i = 2; i < n; i++) {
            f[i] = f[i - 1].add(f[i - 2]);
        }
        return f[n - 1];
    }

    public static BigInteger fibonacci2(int n) {
        n += 1;
        BigInteger fLastLast = BigInteger.ZERO;
        BigInteger fLast = BigInteger.ONE;
        BigInteger fI = null;
        for (int i = 2; i < n; i++) {
            fI = fLast.add(fLastLast);
            fLastLast = fLast;
            fLast = fI;
        }
        return fI;
    }

    public static BigInteger fibonacci3(int n) {
        BigInteger zero = BigInteger.ZERO;
        BigInteger one = BigInteger.ONE;
        BigInteger[][] matrix = {{zero, one}, {one, one}};
        BigInteger[][] result = pow(matrix, n);
        return result[1][0];
    }

    public static BigInteger[][] multiply(BigInteger[][] a, BigInteger[][] b) {
        int size = 2;
        BigInteger zero = BigInteger.ZERO;
        BigInteger[][] result = {{zero, zero}, {zero, zero}};
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                for (int l = 0; l < size; l++) {
                    result[i][j] = result[i][j].add(a[i][l].multiply(b[l][j]));
                }
            }
        }
        return result;
    }

    public static BigInteger[][] pow(BigInteger[][] a, int n) {
        if (n == 1) {
            return a;
        }
        if (n % 2 == 1) {
            return multiply(a, multiply(pow(a, n / 2), pow(a, n / 2)));
        } else {
            return multiply(pow(a, n / 2), pow(a, n / 2));
        }
    }
}
