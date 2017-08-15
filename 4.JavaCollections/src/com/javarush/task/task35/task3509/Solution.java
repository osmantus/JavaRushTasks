package com.javarush.task.task35.task3509;

import java.util.*;


/* 
Collections & Generics
*/
public class Solution {

    public static void main(String[] args) {
    }

    public static <T> ArrayList newArrayList(Object... elements) {
        //напишите тут ваш код
        ArrayList<T> list = new ArrayList<>();
        return list;
    }

    public static <T> HashSet newHashSet(Object... elements) {
        //напишите тут ваш код
        HashSet<T> set = new HashSet<>();
        return set;
    }

    public static <K, V> HashMap newHashMap(List<? extends K> keys, List<? extends V> values) {
        //напишите тут ваш код

        if (keys.size() == values.size())
        {
            HashMap<K, V> map = new HashMap<>();
            return map;
        }
        else {
            throw new IllegalArgumentException();
        }
    }
}
