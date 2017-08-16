package com.javarush.task.task35.task3505;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ConvertableUtil {

    public static Map convert(List<? extends Convertable> list) {
        Map result = new HashMap();
        for (Convertable eachElem : list) {
            result.put(eachElem.getKey(), eachElem);
        }
        return result;
    }
}
