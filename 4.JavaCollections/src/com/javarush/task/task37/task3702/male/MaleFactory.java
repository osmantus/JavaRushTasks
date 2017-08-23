package com.javarush.task.task37.task3702.male;

import com.javarush.task.task37.task3702.AbstractFactory;
import com.javarush.task.task37.task3702.Human;

/**
 * Created by ua053202 on 23.08.2017.
 */
public class MaleFactory<T extends Human> implements AbstractFactory<T> {

    public T getPerson (int age)
    {
        if (age <= KidBoy.MAX_AGE)
            return (T) new KidBoy();
        else if (age <= TeenBoy.MAX_AGE)
            return (T) new TeenBoy();
        else
            return (T) new Man();
    }
}
