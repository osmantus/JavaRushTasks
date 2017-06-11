package com.javarush.task.task37.task3707;

import java.io.Serializable;
import java.util.AbstractSet;
import java.util.Iterator;
import java.util.Set;
import java.util.Spliterator;

/**
 * Created by Alex on 27.03.2017.
 */
public class AmigoSet<E> extends AbstractSet<E> implements Serializable, Cloneable, Set<E>
{
    public AmigoSet()
    {
    }

    @Override
    public Spliterator spliterator()
    {
        return null;
    }

    @Override
    public Iterator iterator()
    {
        return null;
    }

    @Override
    public int size()
    {
        return 0;
    }
}
