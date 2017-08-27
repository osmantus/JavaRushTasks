package com.javarush.task.task36.task3606;

import java.io.File;
import java.io.FileFilter;
import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;

/* 
Осваиваем ClassLoader и Reflection
*/
public class Solution {
    private List<Class> hiddenClasses = new ArrayList<>();
    private String packageName;

    public Solution(String packageName) {
        this.packageName = packageName;
    }

    public static void main(String[] args) throws ClassNotFoundException {
        Solution solution = new Solution(Solution.class.getProtectionDomain().getCodeSource().getLocation().getPath() + "com/javarush/task/task36/task3606/data/second");
        solution.scanFileSystem();
        System.out.println(solution.getHiddenClassObjectByKey("hiddenclassimplse"));
        System.out.println(solution.getHiddenClassObjectByKey("hiddenclassimplf"));
        System.out.println(solution.getHiddenClassObjectByKey("packa"));
    }

    public void scanFileSystem() throws ClassNotFoundException {

        File[] files = new File(packageName).listFiles(new FileFilter() {
            @Override
            public boolean accept(File pathname) {
                return pathname.isFile() && pathname.getName().toLowerCase().endsWith(".class");
            }
        });

        if (files != null) {
            for (final File curFile : files) {
                ClassLoader cl = new MyClassLoader(curFile);
                Class cls = cl.loadClass(curFile.getName().split("\\.")[0]);

                if (HiddenClass.class.isAssignableFrom(cls)) {
                    for (Constructor constructor : cls.getDeclaredConstructors()) {
                        if (constructor.getParameterTypes().length == 0) {
                            hiddenClasses.add(cls);
                        }
                    }
                }
            }
        }
    }

    public HiddenClass getHiddenClassObjectByKey(String key) {

        HiddenClass hiddenClass = null;
        try {
            for (Class cls : hiddenClasses) {
                if (cls.getSimpleName().toLowerCase().startsWith(key.toLowerCase())) {
                    Constructor[] constructors = cls.getDeclaredConstructors();
                    for (Constructor constructor : constructors) {
                        if (constructor.getParameterTypes().length == 0) {
                            constructor.setAccessible(true);
                            hiddenClass = (HiddenClass) constructor.newInstance();
                        }
                    }
                }
            }
        } catch (InstantiationException | InvocationTargetException | IllegalAccessException ignored) {}

        return hiddenClass;
    }

    private class MyClassLoader extends ClassLoader {
        private File classPath;

        MyClassLoader(File classPath) {
            this.classPath = classPath;
        }

        @Override
        protected Class<?> findClass(String name) throws ClassNotFoundException {
            try {
                byte[] buffer = Files.readAllBytes(classPath.toPath());
                return defineClass(null, buffer, 0, buffer.length);

            } catch (IOException e) {
                return super.findClass(name);
            }
        }
    }
}

