package com.mchtcavas.simplebank.util;

import java.util.Random;

public class RandomAccountNumberGenerator {
    public static String create() {
        Random random = new Random();

        int firstPart = 100 + random.nextInt(900);
        int secondPart = 1000 + random.nextInt(9000);

        return String.format("%03d-%04d", firstPart, secondPart);
    }
}
