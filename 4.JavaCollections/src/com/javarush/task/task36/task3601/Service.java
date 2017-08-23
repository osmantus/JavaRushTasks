package com.javarush.task.task36.task3601;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ua053202 on 23.08.2017.
 */
public class Service {

    public Model model;

    public List<String> getData() {
        ArrayList<String> data = new ArrayList<String>() {{
            add("First string");
            add("Second string");
            add("Third string");
        }};
        return data;
    }
}
