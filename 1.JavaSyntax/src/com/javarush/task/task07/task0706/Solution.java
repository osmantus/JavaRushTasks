package com.javarush.task.task07.task0706;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/* 
Улицы и дома
*/

public class Solution {
    public static void main(String[] args) throws Exception {
        //напишите тут ваш код
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        int b = 0, c = 0;
        int[] a = new int[15];
        for (int i = 0; i < a.length; i++) {
            a[i] = Integer.valueOf(reader.readLine());//напишите тут ваш код
            if (i % 2 == 0) {
                b = b + a[i];
            } else c = c + a[i];
        }
        if (b > c) {
            System.out.println("В домах с четными номерами проживает больше жителей.");
        } else
            System.out.println("В домах с нечетными номерами проживает больше жителей.");
    }
}
