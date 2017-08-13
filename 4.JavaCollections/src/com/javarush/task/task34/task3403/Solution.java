package com.javarush.task.task34.task3403;

/* 
Разложение на множители с помощью рекурсии
*/
public class Solution {
    public void recursion(int n) {

        int res = 0;
        boolean isDividedSuccessfully = false;

        if (n > 1) {
            if (n == 2) {
                System.out.println("2");
            } else {
                for (int i = 2; i <= n; i++) {
                    if (isDividedSuccessfully)
                        break;
                    if ((res = n % i) == 0)
                    {
                        isDividedSuccessfully = true;
                        System.out.print(i + " ");
                        recursion(n / i);
                    }
                }
            }
        }
    }

    public static void main(String[] args) {
        Solution sol = new Solution();
        sol.recursion(5320);
    }
}
