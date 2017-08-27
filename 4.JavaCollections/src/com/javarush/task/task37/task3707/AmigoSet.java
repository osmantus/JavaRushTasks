package com.javarush.task.task37.task3707;

import java.io.*;
import java.util.*;

/**
 * Created by Alex on 27.03.2017.
 */
public class AmigoSet<E> extends AbstractSet<E> implements Serializable, Cloneable, Set<E>
{
    private static final Object PRESENT = new Object();
    private transient HashMap<E, Object> map;

    public AmigoSet()
    {
        map = new HashMap<>();
    }

    public AmigoSet(Collection<? extends E> collection)
    {
        map = new HashMap<>(Math.max(16, (int) (collection.size() / .75f + 1)));
        this.addAll(collection);
    }

    @Override
    public Object clone() {
        try
        {
            AmigoSet set = (AmigoSet) super.clone();
            set.map = (HashMap) map.clone();
            return set;
        }
        catch (Exception e)
        {
            throw new InternalError();
        }
    }

    @Override
    public boolean add(E e) {
        return null == map.put(e, PRESENT);
    }

    @Override
    public Iterator<E> iterator()
    {
        Set<E> set = map.keySet();
        return set.iterator();
    }

    @Override
    public int size()
    {
        return map.size();
    }

    @Override
    public boolean isEmpty() {
        return map.isEmpty();
    }

    @Override
    public boolean contains(Object o) {
        return map.containsKey(o);
    }

    @Override
    public void clear() {
        map.clear();
    }

    @Override
    public boolean remove(Object o) {
        return map.remove(o) == null;
    }

    private void writeObject(ObjectOutputStream outputStream) throws IOException
    {
        outputStream.defaultWriteObject();
        Set keys = (Set) map.keySet();
        //outputStream.writeObject(map.keySet());
        outputStream.writeInt(map.size());
        for (Object key : keys) {
            outputStream.writeObject((E) key);
        }

        //outputStream.writeInt(HashMapReflectionHelper.callHiddenMethod(map, "capacity"));
        //outputStream.writeFloat(HashMapReflectionHelper.callHiddenMethod(map, "loadFactor"));
    }

    private void readObject(ObjectInputStream inputStream) throws IOException, ClassNotFoundException
    {
        inputStream.defaultReadObject();
        int mapSize = inputStream.readInt();

        //Set keys = (Set) inputStream.readObject();
        Set keys = new HashSet<E>();
        Object key1 = null;
        for (int i = 0; i < mapSize; i++) {
            key1 = (E) inputStream.readObject();
            keys.add(key1);
        }

        int capacity = inputStream.readInt();
        float loadFactor = inputStream.readFloat();
        map = new HashMap<E, Object>(capacity, loadFactor);
        for (Object key2 : keys) {
            map.put((E) key2, null);
        }
    }

    public static void main(String[] args)
    {
        AmigoSet<Integer> amigoSet = new AmigoSet<>();
        amigoSet.add(1);
        amigoSet.add(5);

        try {
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(new FileOutputStream("D:\\Work\\Test\\amigoset.txt"));

            objectOutputStream.writeObject(amigoSet);
            objectOutputStream.close();
            //amigoSet.writeObject();

            ObjectInputStream objectInputStream = new ObjectInputStream(new FileInputStream("D:\\Work\\Test\\amigoset.txt"));
            AmigoSet<Integer> amigoSet2 = (AmigoSet<Integer>) objectInputStream.readObject();
            objectInputStream.close();
            System.out.println();

        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
