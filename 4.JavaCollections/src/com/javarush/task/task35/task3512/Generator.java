package com.javarush.task.task35.task3512;

public class Generator<T> {

    Class<T> innerClassObj;

    public Generator(Class<T> classObj) {
        innerClassObj = classObj;
    }

    T newInstance() {
        try {
            return (T) innerClassObj.newInstance();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return null;
    }
}
