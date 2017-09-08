package com.javarush.task.task39.task3910;

/* 
isPowerOfThree
*/
public class Solution {
    public static void main(String[] args) {
        System.out.println(isPowerOfThree(-3));
        System.out.println(isPowerOfThree(-2));
        System.out.println(isPowerOfThree(-1));
        System.out.println(isPowerOfThree(0));
        System.out.println(isPowerOfThree(1));
        System.out.println(isPowerOfThree(2));
        System.out.println(isPowerOfThree(3));
        System.out.println(isPowerOfThree(8));
        System.out.println(isPowerOfThree(9));
    }

    public static boolean isPowerOfThree(int n) {
        if (n == 0)
            return false;

        while (n % 3 == 0) {
            n /= 3;
        }
        return n == 1;
    }
}
