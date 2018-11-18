package ru.itis.rsa.prime;

import java.math.BigInteger;
import java.util.Random;

public class NaiveTest {
    private static final BigInteger ZERO = BigInteger.ZERO;
    private static final BigInteger ONE = BigInteger.ONE;
    private static final BigInteger TWO = new BigInteger("2");

    public static void main(String[] args) {
        System.out.println(isPrime(new BigInteger("170141183460469231731687303715884105727")));
    }

    private static boolean isPrime(BigInteger n) {
        int k = 20;
        for (int i = 0; i < k; i++) {
            BigInteger x = uniformRandom(ONE, n);
            if (!x.modPow(n.subtract(ONE), n).equals(ONE)) {
                return false;
            }
        }
        return true;
    }

    private static BigInteger modPow(BigInteger x, BigInteger y, BigInteger c) {
        if (y.equals(ZERO)) {
            return BigInteger.ONE;
        }
        if ((y.mod(TWO)).equals(ONE)) {
            return x.multiply(modPow(x, y.divide(TWO), c)).multiply(modPow(x, y.divide(TWO), c)).mod(c);
        } else {
            return modPow(x, y.divide(TWO), c).multiply(modPow(x, y.divide(TWO), c)).mod(c);
        }
    }

    private static int pow(int x, int y) {
        if (y == 0) {
            return 1;
        }
        if (y % 2 == 1) {
            return x * pow(x, y / 2) * pow(x, y / 2);
        } else {
            return pow(x, y / 2) * pow(x, y / 2);
        }
    }

    private static BigInteger uniformRandom(BigInteger down, BigInteger up) {
        Random random = new Random();
        BigInteger result;
        do {
            result = new BigInteger(up.bitLength(), random);
        } while (result.compareTo(down) < 0 || result.compareTo(up) > 0);
        return result;
    }
}
