package ru.itis.rsa.prime;

import java.math.BigInteger;
import java.util.Random;

public class MillerRabinTest {
    private static final BigInteger ZERO = BigInteger.ZERO;
    private static final BigInteger ONE = BigInteger.ONE;
    private static final BigInteger TWO = new BigInteger("2");

    public static void main(String[] args) {
        MillerRabinTest millerRabinTest = new MillerRabinTest();
        System.out.println(millerRabinTest.isPrime(new BigInteger("170141183460469231731687303715884105727")));
    }

    public boolean isPrime(BigInteger n) {
        int k = 10;

        int s = 0;
        BigInteger t = n.subtract(ONE);
        while (t.mod(TWO).equals(ZERO)) {
            s++;
            t = t.divide(TWO);
        }
        for (int i = 0; i < k; i++) {
            BigInteger a = uniformRandom(TWO, n.subtract(ONE));
            BigInteger x = modPow(a, t, n);
            if (x.equals(ONE) || x.equals(n.subtract(ONE)))
                continue;
            for (int r = 0; r < s; r++) {
                x = modPow(x, TWO, n);
                if (x.equals(ONE))
                    return false;
                if (x.equals(n.subtract(ONE)))
                    break;
            }
            return false;
        }
        return true;
    }

    private BigInteger modPow(BigInteger a, BigInteger b, BigInteger c) {
        BigInteger result = BigInteger.ONE;
        String bi = b.toString(2);
        for (int i = 0; i < bi.length(); i++) {
            result = (result.multiply(result)).mod(c);
            if (bi.charAt(i) == '1') {
                result = (result.multiply(a)).mod(c);
            }
        }
        return result;
    }

    private BigInteger uniformRandom(BigInteger down, BigInteger up) {
        Random random = new Random();
        BigInteger result;
        do {
            result = new BigInteger(up.bitLength(), random);
        } while (result.compareTo(down) < 0 || result.compareTo(up) > 0);
        return result;
    }
}