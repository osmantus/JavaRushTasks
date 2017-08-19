package com.javarush.task.task35.task3507;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Constructor;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
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
        String[] classNames = new String[listOfFiles.length];
        String withoutSuffix = "";
        ClassLoader clldr = null;
        Class<?> cl = null;

        for (int i = 0; i < listOfFiles.length; i++) {
            classNames[i] = listOfFiles[i].getName();
            try {
                withoutSuffix = classNames[i].split("\\.class")[0];
                clldr = Thread.currentThread().getContextClassLoader();
                //cl = clldr.loadClass(Solution.class.getPackage().getName() + "." + "data." + withoutSuffix);
                String packageName = Solution.class.getPackage().getName() + "." + "data";
                cl = new ClassFromPath().load(listOfFiles[i].toPath(), packageName);

                if (Animal.class.isAssignableFrom(cl.getClass()))
                {
                    for (Constructor<?> constructor : cl.getConstructors())
                    {
                        if (constructor.getParameterTypes().length == 0)
                        {
                            Object obj = cl.newInstance();
                        }
                    }
                }

            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            } catch (InstantiationException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }

        Class<? extends Animal> classObj = null;

        return null;
    }

    public static class ClassFromPath extends ClassLoader {
        public Class<?> load(Path path, String packageName) {
            try {
                String className = packageName + "." + path.getFileName().toString().replace(".class", "");
                byte[] b = Files.readAllBytes(path);
                return defineClass(className, b, 0, b.length); //here main magic
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }
    }
}
