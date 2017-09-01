package com.javarush.task.task38.task3812;

/* 
Обработка аннотаций
*/

import org.powermock.core.classloader.annotations.PrepareForTest;

import java.lang.annotation.Annotation;
import java.lang.reflect.AnnotatedType;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;

public class Solution {
    public static void main(String[] args) {
        printFullyQualifiedNames(Solution.class);
        printFullyQualifiedNames(SomeTest.class);

        printValues(Solution.class);
        printValues(SomeTest.class);
    }

    public static boolean printFullyQualifiedNames(Class c) {
        if (c.isAnnotationPresent(PrepareMyTest.class)) {
            PrepareMyTest test = (PrepareMyTest) c.getAnnotation(PrepareMyTest.class);
            for (String s : test.fullyQualifiedNames())
                System.out.println(s);
            return true;
        }
        else
            return false;
    }

    public static boolean printValues(Class c) {
        if (c.isAnnotationPresent(PrepareMyTest.class)) {
            PrepareMyTest test = (PrepareMyTest) c.getAnnotation(PrepareMyTest.class);
            for (Class classObj : test.value())
                System.out.println(classObj.getSimpleName());
            return true;
        }
        else
            return false;
    }
}
