package com.javarush.task.task01.task0140;

import java.util.Scanner;

/* 
Выводим квадрат числа
*/

public class Solution {
    public static void main(String[] args) {
        int a;

        Scanner scannner = new Scanner(System.in);
        //if (scannner.hasNextInt()) {
            a = scannner.nextInt();
            System.out.println(a * a);
        //}
    }
}