package com.javarush.task.task33.task3310;

import java.math.BigInteger;
import java.security.SecureRandom;

/**
 * Created by Alex on 27.08.2017.
 */
public class Helper {
    public static String generateRandomString()
    {
        SecureRandom sRandom = new SecureRandom();
        BigInteger bigInteger = new BigInteger(130, sRandom);

        return bigInteger.toString(32);
    }

    public static void printMessage(String message)
    {
        System.out.println(message);
    }
}
