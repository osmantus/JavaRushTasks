package com.javarush.task.task37.task3707;

import java.io.Serializable;
import java.util.*;

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

    @Override
    public boolean equals(Object o) {
        return super.equals(o);
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }

    @Override
    public boolean removeAll(Collection<?> c) {
        return super.removeAll(c);
    }
}
