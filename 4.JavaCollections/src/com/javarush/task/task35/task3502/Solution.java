package com.javarush.task.task35.task3502;

import java.util.ArrayList;
import java.util.List;

/*
Знакомство с дженериками
*/
public class Solution <T1 extends List<Solution.SomeClass>> {

    //public static List<Solution.SomeClass <? extends Number>> innerList = new ArrayList<>();

    public static class SomeClass <T2 extends Number> {
    }

    public static void main(String[] args) {

        /*List<Solution.SomeClass> list = new ArrayList<>();
        list.add(new Solution.SomeClass<Integer>());

        Solution.innerList.add((SomeClass) list);
        System.out.println("");*/
    }
}
