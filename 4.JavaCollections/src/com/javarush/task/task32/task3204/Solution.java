package com.javarush.task.task32.task3204;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Collections;

/* 
Генератор паролей
*/
public class Solution {

    public static ArrayList<String> passwords = new ArrayList<>();

    public static void main(String[] args) {
        ByteArrayOutputStream password = getPassword();
        System.out.println(password.toString());
    }

    public static ByteArrayOutputStream getPassword()
    {
        String password = "";
        while (true)
        {
            int numsNumber = 1 + (int) (Math.random() * 3);
            int charsNumber = 8 - numsNumber;

            ArrayList<String> numbers = new ArrayList<>();

            for (int i = 0; i < numsNumber; i++)
            {
                int genNumber = 48 + (int) (Math.random() * 9);
                numbers.add(Character.toString((char) genNumber));
            }

            ArrayList<String> chars = new ArrayList<>();

            for (int i = 0; i < charsNumber/2; i++)
            {
                int genChar = 65 + (int) (Math.random() * 25);
                chars.add(Character.toString((char) genChar));
            }
            for (int i = 0; i < charsNumber - charsNumber/2; i++)
            {
                int genChar = 97 + (int) (Math.random() * 25);
                chars.add(Character.toString((char) genChar));
            }

            ArrayList<String> allSymbols = new ArrayList<>();
            allSymbols.addAll(numbers);
            allSymbols.addAll(chars);

            Collections.shuffle(allSymbols);

            for (String str : allSymbols)
                password = password + str;

            if (!passwords.contains(password))
            {
                passwords.add(password);
                break;
            }
        }

        ByteArrayInputStream inStream = new ByteArrayInputStream(password.getBytes());
        ByteArrayOutputStream outStream = new ByteArrayOutputStream();

        while (inStream.available() > 0)
            outStream.write(inStream.read());
        try
        {
            outStream.flush();
            outStream.close();
        } catch (IOException e)
        {
            e.printStackTrace();
        }

        return outStream;
    }
}