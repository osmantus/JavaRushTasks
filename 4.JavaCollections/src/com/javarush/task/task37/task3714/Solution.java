package com.javarush.task.task37.task3714;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/* 
Древний Рим
*/
public class Solution {
    public static void main(String[] args) throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
        System.out.println("Input a roman number to be converted to decimal: ");
        String romanString = bufferedReader.readLine();
        System.out.println("Conversion result equals " + romanToInteger(romanString));
    }

    public static int romanToInteger(String s) {

        final int num1 = 1;
        final int num5 = 5;
        final int num10 = 10;
        final int num50 = 50;
        final int num100 = 100;
        final int num500 = 500;
        final int num1000 = 1000;

        String bigS = s.toUpperCase();

        int result = 0;

        char ch = 0, nextChar = 0;
        for (int i = 0; i < bigS.length(); i++) {
            ch = s.charAt(i);
            if (i < bigS.length()-1)
                nextChar = s.charAt(i+1);
            else
                nextChar = 0;

            if (ch == 'I') {
                if (nextChar != 'V' && nextChar != 'X')
                    result += num1;
                else if (nextChar == 'V') {
                    result += 4;
                    i++;
                }
                else if (nextChar == 'X') {
                    result += 9;
                    i++;
                }
            }
            else if (ch == 'V')
                result += num5;
            else if (ch == 'X') {
                if (nextChar != 'L' && nextChar != 'C')
                    result += num10;
                else if (nextChar == 'L') {
                    result += 40;
                    i++;
                }
                else if (nextChar == 'C') {
                    result += 90;
                    i++;
                }
            }
            else if (ch == 'L')
                result += num50;
            else if (ch == 'C') {
                if (nextChar != 'D' && nextChar != 'M')
                    result += num100;
                else if (nextChar == 'D') {
                    result += 400;
                    i++;
                }
                else if (nextChar == 'M') {
                    result += 900;
                    i++;
                }
            }
            else if (ch == 'D')
                result += num500;
            else if (ch == 'M')
                result += num1000;

            else {
                return 0;
            }
        }

        return result;
    }
}
