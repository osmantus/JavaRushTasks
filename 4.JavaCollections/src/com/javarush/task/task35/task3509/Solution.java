package com.javarush.task.task35.task3509;

import java.util.*;


/* 
Collections & Generics
*/
public class Solution {

    public static void main(String[] args) {
        List<Integer> list0 = newArrayList(new Integer(1), new Integer(3));
        newHashSet(new Integer(1), new Integer(3));
        List<String> list1 = new ArrayList<String>();
        list1.add("1");
        List<Integer> list2 = new ArrayList<Integer>();
        list2.add(5);
        newHashMap(list1, list2);
    }

    public static <T> ArrayList<T> newArrayList(T... elements) {
        //напишите тут ваш код
        ArrayList<T> list = new ArrayList<>();
        if (elements.length > 0)
        {
            for (Object o : elements) {
                list.add((T) o);
            }
        }
        return list;
    }

    public static <T> HashSet<T> newHashSet(T... elements) {
        //напишите тут ваш код
        HashSet<T> set = new HashSet<>();
        if (elements.length > 0)
        {
            for (Object o : elements) {
                set.add((T) o);
            }
        }

        return set;
    }

    public static <K, V> HashMap<K, V> newHashMap(List<? extends K> keys, List<? extends V> values) {
        //напишите тут ваш код

        if (keys.size() == values.size())
        {
            HashMap<K, V> map = new HashMap<>();

            if (keys.size() > 0)
            {
                for (int i = 0; i < keys.size(); i++) {
                    map.put(keys.get(i), values.get(i));
                }
            }
            return map;
        }
        else {
            throw new IllegalArgumentException();
        }
    }
}
