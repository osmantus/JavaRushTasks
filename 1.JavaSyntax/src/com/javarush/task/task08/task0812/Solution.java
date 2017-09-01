package com.javarush.task.task08.task0812;

import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

/* 
Cамая длинная последовательность
*/
public class Solution {
    public static void main(String[] args) throws IOException {
        //напишите тут ваш код

        ArrayList<Integer> list = new ArrayList<>();
        ArrayList<Integer> tmp = new ArrayList<>();
        Scanner sc = new Scanner(System.in);
        for (int i = 0; i < 10; i++) {
            list.add(sc.nextInt());
        }

        tmp = (ArrayList<Integer>) list.clone();

        int count = 1;
        int max = 1;
        for (int i = 0; i < list.size()-1; i++) {
            if (tmp.get(i) == list.get(i+1))
            {
                count++;
                if (max < count)
                    max = count;
            }
            else
                count = 1;
        }
        System.out.println(max);

    }
}