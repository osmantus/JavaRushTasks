package com.javarush.task.task09.task0918;

/* 
Все свои, даже исключения
*/

import java.lang.instrument.IllegalClassFormatException;

public class Solution {
    public static void main(String[] args) {
    }
    
    static class MyException extends IllegalClassFormatException {
    }

    static class MyException2 extends CloneNotSupportedException {
    }

    static class MyException3 extends IndexOutOfBoundsException {
    }

    static class MyException4 extends ClassCastException {
    }
}

