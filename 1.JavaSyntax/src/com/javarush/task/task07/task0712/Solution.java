package com.javarush.task.task07.task0712;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

/* 
Самые-самые
*/

public class Solution {
    public static void main(String[] args) throws Exception {
        //напишите тут ваш код

        ArrayList<String> list = new ArrayList<String>();
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        for (int i=0; i<10; i++)
        {
            String s = reader.readLine();
            list.add(s);
        }
        String adl = list.get(0);
        int jdl = 0;
        for (int i=1; i<list.size(); i++) {
            if (adl.length() < list.get(i).length()) {
                adl = list.get(i);
                jdl = i;
            }
        }
        String acor = list.get(0);
        int jcor = 0;
        for (int i=1; i<list.size(); i++) {
            if (acor.length() > list.get(i).length()) {
                acor = list.get(i);
                jcor = i;
            }
        }
        if (jdl < jcor)
            System.out.println(list.get(jdl));
        else if (jdl > jcor)
            System.out.println(list.get(jcor));
    }
}
