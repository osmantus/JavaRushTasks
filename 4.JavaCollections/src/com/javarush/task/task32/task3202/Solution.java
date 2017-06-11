package com.javarush.task.task32.task3202;

import java.io.*;

/* 
Читаем из потока
*/
public class Solution {
    public static void main(String[] args) throws IOException {
        StringWriter writer = getAllDataFromInputStream(new FileInputStream("D:\\Work\\Java\\Temp\\text.txt"));
        System.out.println(writer.toString());
    }

    public static StringWriter getAllDataFromInputStream(InputStream is) throws IOException {

        StringWriter result = new StringWriter();
        if (is != null)
        {
            if (is.available() > 0)
            {
                //InputStreamReader sr = new InputStreamReader(is);
                //while (sr.ready())
                while (is.available() > 0)
                {
                    result.write(is.read());
                    result.flush();
                }
            }
            result.close();
        }

        return result;
    }
}