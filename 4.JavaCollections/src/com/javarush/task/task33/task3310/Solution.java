package com.javarush.task.task33.task3310;

import com.javarush.task.task33.task3310.strategy.*;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by Alex on 27.08.2017.
 */
public class Solution {
    public static void main(String[] args) {
        HashMapStorageStrategy strategy = new HashMapStorageStrategy();
        testStrategy(strategy, 10000);
        OurHashMapStorageStrategy strategy1 = new OurHashMapStorageStrategy();
        testStrategy(strategy1, 10000);
        FileStorageStrategy strategy2 = new FileStorageStrategy();
        testStrategy(strategy2, 10);
        OurHashBiMapStorageStrategy strategy3 = new OurHashBiMapStorageStrategy();
        testStrategy(strategy3, 10000);

        HashBiMapStorageStrategy strategy4 = new HashBiMapStorageStrategy();
        testStrategy(strategy4, 10000);

        DualHashBidiMapStorageStrategy strategy5 = new DualHashBidiMapStorageStrategy();
        testStrategy(strategy5, 10000);
    }

    public static Set<Long> getIds(Shortener shortener, Set<String> strings)
    {
        Set<Long> longSet = new HashSet<>();
        for (String str : strings)
        {
            Long id = shortener.getId(str);
            longSet.add(id);
        }
        return longSet;
    }

    public static Set<String> getStrings(Shortener shortener, Set<Long> keys)
    {
        Set<String> stringSet = new HashSet<>();
        for (Long id : keys)
        {
            String str = shortener.getString(id);
            stringSet.add(str);
        }
        return stringSet;
    }

    public static void testStrategy(StorageStrategy strategy, long elementsNumber)
    {
        System.out.println(strategy.getClass().getSimpleName());

        Set<String> strings = new HashSet<>();
        Long[] elements = new Long[(int) elementsNumber];

        for (int i = 0; i < elementsNumber; i++)
            strings.add(Helper.generateRandomString());

        Shortener shortener = new Shortener(strategy);

        Date startDateTime = new Date();
        Set<Long> ids = getIds(shortener, strings);
        Date finishDateTime = new Date();

        long deltaTime = finishDateTime.getTime() - startDateTime.getTime();
        Helper.printMessage(Long.toString(deltaTime));

        startDateTime = new Date();
        Set<String> strs = getStrings(shortener, ids);
        finishDateTime = new Date();

        deltaTime = finishDateTime.getTime() - startDateTime.getTime();
        Helper.printMessage(Long.toString(deltaTime));

        if (strings.equals(strs))
            Helper.printMessage("Тест пройден.");
        else
            Helper.printMessage("Тест не пройден.");
    }
}
