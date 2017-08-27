package com.javarush.task.task36.task3610;

import java.io.Serializable;
import java.util.*;

public class MyMultiMap<K, V> extends HashMap<K, V> implements Cloneable, Serializable {
    static final long serialVersionUID = 123456789L;
    private HashMap<K, List<V>> map;
    private int repeatCount;

    public MyMultiMap(int repeatCount) {
        this.repeatCount = repeatCount;
        map = new HashMap<>();
    }

    @Override
    public int size() {
        //напишите тут ваш код
        int count = 0;
        for (List<?> value : map.values()) {
            for (Object val : value) {
                count++;
            }
        }
        return count;
    }

    @Override
    public V put(K key, V value) {
        //напишите тут ваш код

        if (map.containsKey(key))
        {
            List<V> list = map.get(key);
            if (list.size() < repeatCount) {
                V lastValue = (V) list.get(list.size()-1);
                list.add(value);
                return lastValue;
            }
            else if (list.size() == repeatCount) {
                list.remove(0);
                V lastValue = (V) list.get(list.size()-1);
                list.add(value);
                return lastValue;
            }
            else
                return null;
        }
        else
        {
            List<V> list = new ArrayList<>();
            list.add(value);
            map.put(key, list);
            return null;
        }
    }

    @Override
    public V remove(Object key) {
        //напишите тут ваш код
        if (containsKey(key)) {
            if (map.get(key).size() > 0) {
                V val = map.get(key).remove(0);
                if (map.get(key).size() == 0)
                    map.remove(key);
                return val;
            }
            else
                map.remove(key);
        }
        return null;
    }

    @Override
    public Set<K> keySet() {
        //напишите тут ваш код
        return map.keySet();
    }

    @Override
    public Collection<V> values() {
        //напишите тут ваш код
        ArrayList<V> array = new ArrayList<>();
        for (List<?> value : map.values()) {
            for (Object val : value) {
                array.add((V) val);
            }
        }
        return array;
    }

    @Override
    public boolean containsKey(Object key) {
        //напишите тут ваш код
        return keySet().contains(key);
    }

    @Override
    public boolean containsValue(Object value) {
        //напишите тут ваш код
        for (List<V> list : map.values()) {
            if (list.contains(value))
                return true;
        }
        return false;
        //return map.values().contains(value);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("{");
        for (Map.Entry<K, List<V>> entry : map.entrySet()) {
            sb.append(entry.getKey());
            sb.append("=");
            for (V v : entry.getValue()) {
                sb.append(v);
                sb.append(", ");
            }
        }
        String substring = sb.substring(0, sb.length() - 2);
        return substring + "}";
    }
}