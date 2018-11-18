package ru.itis.rsa;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

public class MessageProvider {
    private final static BigInteger SYMBIT = BigInteger.valueOf(256);
    private final static BigInteger ZERO = BigInteger.ZERO;

    private static volatile MessageProvider instance;

    public static MessageProvider getInstance() {
        MessageProvider localInstance = instance;
        if (localInstance == null) {
            synchronized (MessageProvider.class) {
                localInstance = instance;
                if (localInstance == null) {
                    instance = localInstance = new MessageProvider();
                }
            }
        }
        return localInstance;
    }

    private List<BigInteger> encrypt(List<BigInteger> message, Rsa sender, Rsa receiver) {
        List<BigInteger> encrypted = new ArrayList<>();
        for (BigInteger tweet : message) {
            encrypted.add(tweet.modPow(receiver.getE(), receiver.getN()));
        }
        encrypted.add(message.get(0).modPow(sender.getD(), sender.getN()));
        return encrypted;
    }

//    public List<BigInteger> decrypt(List<BigInteger> encrypted, Rsa sender, Rsa receiver) {
//        List<BigInteger> decrypted = new ArrayList<>();
//        int size = encrypted.size();
//        for (int i = 0; i < size - 1; i++) {
//            decrypted.add(encrypted.get(i).modPow(receiver.getD(), receiver.getN()));
//        }
//        decrypted.add(encrypted.get(size - 1).modPow(sender.getE(), sender.getN()));
//        return decrypted;
//    }

    public List<BigInteger> encrypt(String message, Rsa sender, Rsa receiver) {
        int maxTweetSize = Math.min(sender.getN().bitLength(), receiver.getN().bitLength()) >> 3;

        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < maxTweetSize - message.length() % maxTweetSize; i++) {
            sb.append((char) 0);
        }
        message += sb.toString();

        int tweetSize = 0;
        List<BigInteger> tweets = new ArrayList<>();
        BigInteger x = BigInteger.ZERO;
        for (int c : message.toCharArray()) {
            x = x.multiply(SYMBIT).add(BigInteger.valueOf(c));
            tweetSize++;
            if (tweetSize == maxTweetSize) {
                tweets.add(x);
                x = BigInteger.ZERO;
                tweetSize = 0;
            }
        }

        return MessageProvider.getInstance().encrypt(tweets, sender, receiver);
    }

    public String decrypt(List<BigInteger> encrypted, Rsa sender, Rsa receiver) {
        List<BigInteger> decrypted = new ArrayList<>();
        int size = encrypted.size();
        for (int i = 0; i < size - 1; i++) {
            decrypted.add(encrypted.get(i).modPow(receiver.getD(), receiver.getN()));
        }
        decrypted.add(encrypted.get(size - 1).modPow(sender.getE(), sender.getN()));

        StringBuilder result = new StringBuilder();

        StringBuilder message = new StringBuilder();
        for (int i = 0; i < size - 1; i++) {
            BigInteger tweet = decrypted.get(i);
            while (!tweet.equals(ZERO)) {
                message.append((char) tweet.mod(SYMBIT).intValue());
                tweet = tweet.divide(SYMBIT);
            }
        }
        char[] messageChars = message.toString().toCharArray();
        for (int i = messageChars.length - 1; i >= 0; i--) {
            result.append(messageChars[i]);
        }

//        result.append("\n").append("Подпись: ").append("\n");
//        StringBuilder signature = new StringBuilder();
//        BigInteger tweet = decrypted.get(size - 1);
//        while (!tweet.equals(ZERO)) {
//            signature.append((char) tweet.mod(SYMBIT).intValue());
//            tweet = tweet.divide(SYMBIT);
//        }
//        char[] signatureChars = signature.toString().toCharArray();
//        for (int i = signatureChars.length - 1; i >= 0; i--) {
//            result.append(signatureChars[i]);
//        }

        return result.toString().replaceAll(String.valueOf((char) 0), "");
    }
}
