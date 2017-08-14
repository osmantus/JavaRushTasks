package com.javarush.task.task34.task3408;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.util.Map;
import java.util.WeakHashMap;

public class Cache<K, V> {
    private Map<K, V> cache = new WeakHashMap<>();

    public V getByKey(K key, Class<V> clazz) throws Exception {
        if (cache.containsKey(key)) {
            V result = cache.get(key);
            return result;
        }
        else {
            try {
                Constructor[] allConstructors = clazz.getDeclaredConstructors();
                for (Constructor constructor : allConstructors)
                {
                    if (constructor.getParameterTypes().length == 1)
                    {
                        if (constructor.getParameterTypes()[0].equals(key.getClass())) {
                            V newValue = (V) constructor.newInstance(key);
                            cache.put(key, newValue);
                            return newValue;
                        }
                    }
                }
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
            return null;
        }
    }

    public boolean put(V obj) {
        try {
            Method method = obj.getClass().getDeclaredMethod("getKey");
            method.setAccessible(true);
            K key = (K) method.invoke(obj);
            cache.put(key, obj);
            return true;
        }
        catch (Exception e)
        {}
        return false;
    }

    public int size() {
        return cache.size();
    }
}
