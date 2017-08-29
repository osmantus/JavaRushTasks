package com.javarush.task.task38.task3803;

/* 
Runtime исключения (unchecked exception)
*/

public class VeryComplexClass {
    public void methodThrowsClassCastException() {
        B b = new B();
        A a = (B) new A();
    }

    public void methodThrowsNullPointerException() {
        Integer a = null;
        Integer b = new Integer(a);
    }

    public static void main(String[] args) {
        VeryComplexClass obj = new VeryComplexClass();
        try {
            obj.methodThrowsClassCastException();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

        try {
            obj.methodThrowsNullPointerException();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    private class A
    {

    }

    private class B extends A
    {

    }
}
