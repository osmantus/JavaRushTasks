package com.javarush.task.task34.task3411;

/* 
Ханойские башни
*/

public class Solution {

    public static void main(String[] args) {
        int count = 4;
        moveRing('A', 'B', 'C', count);
    }

    public static void moveRing(char from, char to, char help, int count) {
        if (count > 0) {

            moveRing(from, help, to, count - 1);
            System.out.println("from " + from + " to " + to);
            moveRing(help, to, from, count - 1);
        }
    }
}