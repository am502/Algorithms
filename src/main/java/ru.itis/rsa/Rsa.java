package ru.itis.rsa;

import java.io.Serializable;
import java.math.BigInteger;
import java.security.SecureRandom;

public class Rsa implements Serializable {
    private final static SecureRandom random = new SecureRandom();
    private final static BigInteger ONE = BigInteger.ONE;
    private final static BigInteger ZERO = BigInteger.ZERO;
    private final static BigInteger SYMBIT = BigInteger.valueOf(256);

    private BigInteger d;
    private BigInteger e;
    private BigInteger n;

    public Rsa(int keySize) {
        e = new BigInteger("65537");
        BigInteger phi;

        while (true) {
            BigInteger p = BigInteger.probablePrime(keySize / 2, random);
            BigInteger q = BigInteger.probablePrime(keySize / 2, random);
            if (e.gcd(p).equals(ONE) && e.gcd(q).equals(ONE)) {
                phi = (p.subtract(ONE)).multiply(q.subtract(ONE));
                n = p.multiply(q);
                break;
            }
        }

        d = e.modInverse(phi);
    }

    public BigInteger getD() {
        return d;
    }

    public BigInteger getE() {
        return e;
    }

    public BigInteger getN() {
        return n;
    }

    public static void main(String[] args) {
//        Rsa sender = new Rsa(2048);
//        Rsa receiver = new Rsa(2048);
//
//        int maxTweetSize = Math.min(sender.getN().bitLength(), receiver.getN().bitLength()) >> 3;
//
//        String text = "sssaaaaaan performed posing by. Garden agreed matter are should formed temper had. " +
//                "Full held gay now roof whom such next wassssaaaaaan performed supposing by. Garden agreed " +
//                "matter are should formed temper had. Full held gay now roof whom such next wasgg";
//        System.out.println(text.length());
//
//        StringBuilder sb = new StringBuilder();
//        for (int i = 0; i < maxTweetSize - text.length() % maxTweetSize; i++) {
//            sb.append((char) 0);
//        }
//        text += sb.toString();
//
//        int tweetSize = 0;
//        List<BigInteger> tweets = new ArrayList<>();
//        BigInteger x = BigInteger.ZERO;
//        for (int c : text.toCharArray()) {
//            x = x.multiply(SYMBIT).add(BigInteger.valueOf(c));
//            tweetSize++;
//            if (tweetSize == maxTweetSize) {
//                tweets.add(x);
//                x = BigInteger.ZERO;
//                tweetSize = 0;
//            }
//        }
//
//        List<BigInteger> encrypted = MessageProvider.getInstance().encrypt(tweets, sender, receiver);
//
//        List<BigInteger> decrypted = MessageProvider.getInstance().decrypt(encrypted, sender, receiver);
//
//        StringBuilder messageWithSignature = new StringBuilder();
//        for (BigInteger tweet : decrypted) {
//            while (!tweet.equals(ZERO)) {
//                messageWithSignature.append((char) tweet.mod(SYMBIT).intValue());
//                tweet = tweet.divide(SYMBIT);
//            }
//        }
//
//        char[] chars = messageWithSignature.toString().toCharArray();
//        for (int i = chars.length - 1; i >= 0; i--) {
//            System.out.print(chars[i]);
//        }
    }
}
