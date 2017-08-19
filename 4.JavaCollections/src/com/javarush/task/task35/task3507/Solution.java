package com.javarush.task.task35.task3507;

import java.io.File;
import java.util.HashSet;
import java.util.Set;

/* 
ClassLoader - что это такое?
*/
public class Solution {
    public static void main(String[] args) {
        Set<? extends Animal> allAnimals = getAllAnimals(Solution.class.getProtectionDomain().getCodeSource().getLocation().getPath() + Solution.class.getPackage().getName().replaceAll("[.]", "/") + "/data");
        System.out.println(allAnimals);
    }

    public static Set<? extends Animal> getAllAnimals(String pathToAnimals) {

        Set<? extends Animal> resultingSet = new HashSet<>();

        File folder = new File(pathToAnimals);
        File[] listOfFiles = folder.listFiles();
        Class<? extends Animal> classObj = null;

        //Reflections reflections = new Reflections(pathToAnimals);

        /*for (int i = 0; i < listOfFiles.length; i++) {
            if (listOfFiles[i].isFile()) {
                classObj = ClassLoader.
            }
        }*/

        return null;
    }
}
