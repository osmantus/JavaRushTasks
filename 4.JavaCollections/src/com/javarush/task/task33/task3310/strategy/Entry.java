package com.javarush.task.task33.task3310.strategy;

import java.io.Serializable;
import java.util.Map;
import java.util.Objects;

/**
 * Created by Alex on 27.08.2017.
 */
public class Entry implements Serializable {
    Long key;
    String value;
    Entry next;
    int hash;

    public Entry(int hash, Long key, String value, Entry next)
    {
        this.hash = hash;
        this.key = key;
        this.value = value;
        this.next = next;
    }

    public Long getKey()
    {
        return key;
    }

    public String getValue()
    {
        return value;
    }

    @Override
    public int hashCode()
    {
        //return Objects.hashCode(key) ^ Objects.hashCode(value);
        return (key == null ? 0 : key.hashCode()) ^ (value == null ? 0 : value.hashCode());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Entry entry = (Entry) o;

        if (getKey() != null ? !getKey().equals(entry.getKey()) : entry.getKey() != null) return false;
        return getValue() != null ? getValue().equals(entry.getValue()) : entry.getValue() == null;
    }

    public String toString()
    {
        return key + "=" + value;
    }
}
