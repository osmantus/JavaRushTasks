package com.javarush.task.task33.task3303;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.StringReader;

/* 
Десериализация JSON объекта
*/
public class Solution {

    public static <T> T convertFromJsonToNormal(String fileName, Class<T> clazz) throws IOException {
        /*FileReader reader = new FileReader(new File(fileName));
        StringBuilder jsonString = new StringBuilder();
        char[] buffer = new char[1024];
        while (reader.read(buffer, 0, buffer.length) != -1) {
            jsonString.append(buffer);
        }
        reader.close();*/

        ObjectMapper mapper = new ObjectMapper();
        //StringReader reader1 = new StringReader(jsonString.toString());

        return mapper.readValue(new FileReader(new File(fileName)), clazz);
    }

    public static void main(String[] args) {

    }
}
