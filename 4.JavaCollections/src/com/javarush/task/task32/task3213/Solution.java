package com.javarush.task.task32.task3213;

import java.io.IOException;
import java.io.StringReader;

/* 
Шифр Цезаря
*/

public class Solution {
    public static void main(String[] args) throws IOException {
        StringReader reader = new StringReader("Khoor Dpljr");
        System.out.println(decode(reader, -3));  //Hello Amigo

    }

    public static String decode(StringReader reader, int key) throws IOException {

        if (reader == null)
            return "";

        int byteFromReader = 0;
        int newByteValue = 0;
        StringBuilder sb = new StringBuilder("");

        char symbol = 0;
        String str = "";

        while ((byteFromReader = reader.read()) != -1)
        {
            newByteValue = byteFromReader + key;

            symbol = (char) newByteValue;
            str = Character.toString(symbol);

            sb.append(str);
        }

        return sb.toString();
    }

}
