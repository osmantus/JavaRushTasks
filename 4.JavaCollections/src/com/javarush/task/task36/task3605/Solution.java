package com.javarush.task.task36.task3605;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.util.TreeSet;

/* 
Использование TreeSet
*/
public class Solution {
    public static void main(String[] args) throws IOException {

        if (args.length == 0)
            return;

        String filePath = args[0];

        try (BufferedReader reader = new BufferedReader(new FileReader(args[0]))) {
            TreeSet<Character> set = new TreeSet<>();
            for (String line; (line = reader.readLine()) != null; ) {
                for (char c : line.toLowerCase().toCharArray()) {
                    if (c >= 'a' && c <= 'z') set.add(c);
                }
            }
            int count = 5;
            for (Character ch : set) {
                if (count > 0) {
                    System.out.print(ch);
                    count--;
                }
            }
        }
    }
}
