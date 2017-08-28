package com.javarush.task.task33.task3310.tests;

import com.javarush.task.task33.task3310.Helper;
import com.javarush.task.task33.task3310.Shortener;
import com.javarush.task.task33.task3310.strategy.HashBiMapStorageStrategy;
import com.javarush.task.task33.task3310.strategy.HashMapStorageStrategy;
import org.junit.Assert;
import org.junit.Test;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by ua053202 on 28.08.2017.
 */
public class SpeedTest {
    public long getTimeForGettingIds(Shortener shortener, Set<String> strings, Set<Long> ids)
    {
        Long id;
        Date startDateTime = new Date();
        for (String str : strings) {
            id = shortener.getId(str);
            ids.add(id);
        }
        Date finishDateTime = new Date();

        long deltaTime = finishDateTime.getTime() - startDateTime.getTime();

        return deltaTime;
    }

    public long getTimeForGettingStrings(Shortener shortener, Set<Long> ids, Set<String> strings)
    {
        String str;
        Date startDateTime = new Date();
        for (Long id : ids) {
            str = shortener.getString(id);
            strings.add(str);
        }

        Date finishDateTime = new Date();
        long deltaTime = finishDateTime.getTime() - startDateTime.getTime();

        return deltaTime;
    }

    @Test
    public void testHashMapStorage()
    {
        Shortener shortener1 = new Shortener(new HashMapStorageStrategy());
        Shortener shortener2 = new Shortener(new HashBiMapStorageStrategy());

        String str;
        Set<String> origStrings = new HashSet<>();

        for(int i = 0; i < 10000; i++) {
            str = Helper.generateRandomString();
            origStrings.add(str);
        }

        Set<Long> origIds = new HashSet<>();
        long delta1 = getTimeForGettingIds(shortener1, origStrings, origIds);

        origIds = new HashSet<>();
        long delta2 = getTimeForGettingIds(shortener2, origStrings, origIds);

        Assert.assertNotEquals(delta1, delta2);

        origIds = new HashSet<>();
        delta1 = getTimeForGettingStrings(shortener1, origIds, origStrings);

        origIds = new HashSet<>();
        delta2 = getTimeForGettingStrings(shortener2, origIds, origStrings);

        Assert.assertEquals(delta1, delta2, 30);
    }
}
