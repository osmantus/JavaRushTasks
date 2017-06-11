package com.javarush.task.task32.task3210;

import java.io.IOException;
import java.io.RandomAccessFile;

/* 
Используем RandomAccessFile
*/

public class Solution {
    public static void main(String... args) throws IOException
    {
        String fileName = args[0];
        int number = Integer.parseInt(args[1]);
        String text = args[2];
        int lenOfText = text.length();

        RandomAccessFile file = new RandomAccessFile(fileName, "rw");
        file.seek(number);

        byte[] buffer = new byte[lenOfText];
        file.read(buffer, 0, lenOfText);

        file.seek(file.length());
        String str = convertByteToString(buffer);
        if (str != null)
        {
            String strToWrite = "";
            if (str.equals(text))
                strToWrite = "true";
            else
                strToWrite = "false";

            file.write(strToWrite.getBytes());
        }
    }

    public static String convertByteToString(byte readBytes[])
    {
        return new String(readBytes);
    }
}
