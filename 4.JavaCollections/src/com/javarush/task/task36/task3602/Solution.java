package com.javarush.task.task36.task3602;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Modifier;
import java.util.Collections;
import java.util.List;

/* 
Найти класс по описанию
*/
public class Solution {
    public static void main(String[] args) {
        System.out.println(getExpectedClass());
    }

    public static Class getExpectedClass() {
        for (Class cls : Collections.class.getDeclaredClasses()) {
            if (List.class.isAssignableFrom(cls)
                    && Modifier.isPrivate(cls.getModifiers())
                    && Modifier.isStatic(cls.getModifiers())) {
                try {
                    Class curClass = Solution.class.getClassLoader().loadClass(cls.getName());
                    Constructor constructor = curClass.getDeclaredConstructor();
                    constructor.setAccessible(true);
                    List list = (List) constructor.newInstance();
                    try {
                        list.get(0);
                    } catch (IndexOutOfBoundsException e) {
                        return cls;
                    }
                } catch (Exception none) {
                }
            }
        }
        return null;//Collections.EMPTY_LIST.getClass();
    }

    public static Class getExpectedClass1() {

        Class[] classes = Collections.class.getDeclaredClasses();
        for (Class clazz : classes) {
            //System.out.println("Класс " + clazz);

            for (Class interfaze : clazz.getInterfaces()) {
                if (interfaze.getSimpleName().equals("List")) {
                    //System.out.println("Нашли List " + clazz);
                    int modifier = clazz.getModifiers();
                    if (Modifier.isStatic(modifier) && Modifier.isPrivate(modifier)/* && !Modifier.isPublic(modifier) && !Modifier.isFinal(modifier) && !Modifier.isProtected(modifier)*/) {
                        //System.out.println("Нашли приватный статический List " + clazz);

                        try {
                            if (clazz.getMethod("get", int.class).getExceptionTypes().length > 0)
                            {
                                Class[] exceptions = clazz.getMethod("get", int.class).getExceptionTypes();
                                int eCount = 0;
                                for (Class exception : exceptions) {
                                    if (exception.getSimpleName().equals("IndexOutOfBoundsException"))
                                        eCount++;
                                }
                                if (eCount == 0) {
                                    Constructor constructor = clazz.getDeclaredConstructor();
                                    List list = null;
                                    try {
                                        list = (List) constructor.newInstance();
                                    } catch (InstantiationException e) {
                                        e.printStackTrace();
                                    } catch (IllegalAccessException e) {
                                        e.printStackTrace();
                                    } catch (InvocationTargetException e) {
                                        e.printStackTrace();
                                    }
                                    try
                                    {
                                        list.get(0);
                                    }
                                    catch (IndexOutOfBoundsException e)
                                    {
                                        return clazz;
                                    }
                                }
                            }
                            else
                            {
                                return clazz;
                            }
                        } catch (NoSuchMethodException e) {
                            //e.printStackTrace();
                        }
                    }
                } else {
                    interfaze = getParentInterfaceAsList(interfaze);
                    //System.out.println();
                    //System.out.println(interfaze.getSuperclass().getClass().getSimpleName());

                    if (interfaze != null) {
                        int modifier = clazz.getModifiers();
                        if (Modifier.isStatic(modifier) && Modifier.isPrivate(modifier)/* && !Modifier.isPublic(modifier) && !Modifier.isFinal(modifier) && !Modifier.isProtected(modifier)*/) {

                            try {
                                if (clazz.getMethod("get", int.class).getExceptionTypes().length > 0)
                                {
                                    Class[] exceptions = clazz.getMethod("get", int.class).getExceptionTypes();
                                    int eCount = 0;
                                    for (Class exception : exceptions) {
                                        if (exception.getSimpleName().equals("IndexOutOfBoundsException"))
                                            eCount++;
                                    }
                                    if (eCount == 0) {
                                        Constructor constructor = clazz.getDeclaredConstructor();
                                        List list = null;
                                        try {
                                            list = (List) constructor.newInstance();
                                        } catch (InstantiationException e) {
                                            e.printStackTrace();
                                        } catch (IllegalAccessException e) {
                                            e.printStackTrace();
                                        } catch (InvocationTargetException e) {
                                            e.printStackTrace();
                                        }
                                        try
                                        {
                                            list.get(0);
                                        }
                                        catch (IndexOutOfBoundsException e)
                                        {
                                            return clazz;
                                        }
                                    }
                                }
                                else
                                {
                                    return clazz;
                                }
                            } catch (NoSuchMethodException e) {
                                //e.printStackTrace();
                            }
                        }
                    }
                }
            }

            Class parentClazz = clazz.getSuperclass();

            Class interfaze = getParentInterfaceAsList(parentClazz);
            if (interfaze != null) {
                int modifier = clazz.getModifiers();
                if (Modifier.isStatic(modifier) && Modifier.isPrivate(modifier)/* && !Modifier.isPublic(modifier) && !Modifier.isFinal(modifier) && !Modifier.isProtected(modifier)*/) {

                    try {
                        if (clazz.getMethod("get", int.class).getExceptionTypes().length > 0)
                        {
                            Class[] exceptions = clazz.getMethod("get", int.class).getExceptionTypes();
                            int eCount = 0;
                            for (Class exception : exceptions) {
                                if (exception.getSimpleName().equals("IndexOutOfBoundsException"))
                                    eCount++;
                            }
                            if (eCount == 0) {
                                Constructor constructor = clazz.getDeclaredConstructor();
                                List list = null;
                                try {
                                    list = (List) constructor.newInstance();
                                } catch (InstantiationException e) {
                                    e.printStackTrace();
                                } catch (IllegalAccessException e) {
                                    e.printStackTrace();
                                } catch (InvocationTargetException e) {
                                    e.printStackTrace();
                                }
                                try
                                {
                                    list.get(0);
                                }
                                catch (IndexOutOfBoundsException e)
                                {
                                    return clazz;
                                }
                            }
                        }
                        else
                        {
                            return clazz;
                        }
                    } catch (NoSuchMethodException e) {
                        //e.printStackTrace();
                    }
                }
            }
        }
        return null;
    }

    private static Class getParentInterfaceAsList(Class interfaze)
    {
        Class oneOfInterfaces = null;
        if (interfaze.getInterfaces().length > 0) {
            for (Class interfaze2 : interfaze.getInterfaces()) {
                //System.out.println(interfaze2.getSimpleName());
                if (!interfaze2.getSimpleName().equals("List")) {
                    return getParentInterfaceAsList(interfaze2);
                }
                else
                    return interfaze2;
            }
        }
        else
        {
            if (interfaze.getSimpleName().equals("List"))
                return interfaze;
            else
                return null;
        }

        return null;
    }
}
