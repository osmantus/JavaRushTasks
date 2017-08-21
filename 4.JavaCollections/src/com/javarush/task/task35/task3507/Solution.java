package com.javarush.task.task35.task3507;

import java.io.File;
import java.io.FileFilter;
import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.Modifier;
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

        Set<Animal> resultingSet = new HashSet<>();

        File folder = new File(pathToAnimals);

        File[] listOfFiles = new File(pathToAnimals).listFiles(new FileFilter() {
            @Override
            public boolean accept(File pathname) {
                return pathname.isFile() && pathname.getName().toLowerCase().endsWith(".class");
            }
        });

        String[] classNames = new String[listOfFiles.length];
        String classNameWithoutSuffix = "";
        ClassLoader clldr = null;
        Class cl = null;

        for (int i = 0; i < listOfFiles.length; i++) {
            classNames[i] = listOfFiles[i].getName();
            try {
                classNameWithoutSuffix = classNames[i].split("\\.class")[0];
                clldr = Thread.currentThread().getContextClassLoader();

                String packageName = Solution.class.getPackage().getName() + "." + "data";
                Path path = listOfFiles[i].toPath();
                cl = new ClassFromPath().load(path, packageName);

                for (Class iface : cl.getInterfaces()) {
                    if (iface.getSimpleName().equals("Animal")) {
                        for (Constructor constructor : cl.getConstructors()) {
                            if (Modifier.isPublic(constructor.getModifiers()) && constructor.getParameterTypes().length == 0) {
                                resultingSet.add((Animal) cl.newInstance());
                                break;
                            }
                        }
                        break;
                    }
                }
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
        }

        return resultingSet;
    }

    public static class ClassFromPath extends ClassLoader {
        public Class<?> load(Path path, String packageName) {
            try {
                String className = packageName + "." + path.getFileName().toString().replace(".class", "");
                byte[] b = Files.readAllBytes(path);
                return defineClass(className, b, 0, b.length);
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }
    }
}
