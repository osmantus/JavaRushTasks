package com.javarush.task.task32.task3201;

import java.io.IOException;
import java.io.RandomAccessFile;

/*
Запись в существующий файл
*/
public class Solution {
    public static void main(String... args) throws IOException
    {
        if (args.length >= 2)
        {
            String fileName = args[0];
            int filePos = Integer.parseInt(args[1]);
            String newTextToAdd = args[2];

            RandomAccessFile raf = new RandomAccessFile(fileName, "rw");

            if (raf.length() >= filePos)
            {
                raf.seek(filePos);
                raf.write(newTextToAdd.getBytes());
            }
            else
            {
                raf.seek(raf.length());
                raf.write(newTextToAdd.getBytes());
            }

            raf.close();
        }
    }
}
