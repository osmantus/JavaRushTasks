package com.javarush.task.task34.task3413;

import java.lang.ref.SoftReference;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class SoftCache {
    private Map<Long, SoftReference<AnyObject>> cacheMap = new ConcurrentHashMap<>();

    public AnyObject get(Long key) {
        SoftReference<AnyObject> softReference;// = cacheMap.get(key);

        AnyObject anyObject;
        if (cacheMap.containsKey(key)) {
            softReference = cacheMap.get(key);
            anyObject = softReference.get();
            return anyObject;
        }
        else {
            softReference = cacheMap.get(key);
            return null;
        }
    }

    public AnyObject put(Long key, AnyObject value) {
        SoftReference<AnyObject> softReference;// = cacheMap.put(key, new SoftReference<>(value));

        if (cacheMap.containsKey(key)) {
            softReference = cacheMap.get(key);
            AnyObject prevValue = softReference.get();
            softReference.clear();
            cacheMap.put(key, new SoftReference<>(value));
            return prevValue;
        }
        else {
            cacheMap.put(key, new SoftReference<>(value));
            return null;
        }
    }

    public AnyObject remove(Long key) {
        SoftReference<AnyObject> softReference;// = cacheMap.remove(key);

        if (cacheMap.containsKey(key)) {
            softReference = cacheMap.get(key);
            AnyObject prevValue = softReference.get();
            softReference.clear();
            cacheMap.remove(key);
            return prevValue;
        }
        else {
            cacheMap.remove(key);
            return null;
        }
    }
}