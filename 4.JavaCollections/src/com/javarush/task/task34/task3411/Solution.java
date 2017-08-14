package com.javarush.task.task34.task3411;

/* 
Ханойские башни
*/

import java.util.HashMap;
import java.util.Stack;

public class Solution {

    public static HashMap<String, Stack<Integer>> from = new HashMap<>();
    public static HashMap<String, Stack<Integer>> help = new HashMap<>();
    public static HashMap<String, Stack<Integer>> to = new HashMap<>();

    public static void main(String[] args) {
        int count = 3;
        moveRing('A', 'B', 'C', count);
    }

    public static void moveRing(char a, char b, char c, int count) {
        //напишите тут ваш код

        if (count > 0) {

            for (int i = 1; i <= count; i++)
                //from.put("", i);

            System.out.println("from " + a);
            //moveRing(from, );
            System.out.println(" to " + b);
        }
    }
}