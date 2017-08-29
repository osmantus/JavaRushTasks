package com.javarush.task.task38.task3802;

/* 
Проверяемые исключения (checked exception)
*/

import java.io.FileInputStream;

public class VeryComplexClass {
    public void veryComplexMethod() throws Exception {
        //напишите тут ваш код
        try(FileInputStream input = new FileInputStream("")) {

            int data = input.read();
            while(data != -1){
                System.out.print((char) data);
                data = input.read();
            }
        }
    }

    public static void main(String[] args) {
        VeryComplexClass obj = new VeryComplexClass();
        try {
            obj.veryComplexMethod();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
