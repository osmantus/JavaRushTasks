package com.javarush.task.task37.task3702;

import com.javarush.task.task37.task3702.female.FemaleFactory;
import com.javarush.task.task37.task3702.male.MaleFactory;

/**
 * Created by ua053202 on 23.08.2017.
 */
public class FactoryProducer {
    public enum HumanFactoryType {MALE, FEMALE};

    public static AbstractFactory getFactory(HumanFactoryType humanType)
    {
        if (humanType == HumanFactoryType.MALE)
            return new MaleFactory();
        else if (humanType == HumanFactoryType.FEMALE)
            return new FemaleFactory();
        else
            return null;
    }
}
