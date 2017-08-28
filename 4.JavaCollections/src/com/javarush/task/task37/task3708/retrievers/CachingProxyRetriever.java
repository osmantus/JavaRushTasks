package com.javarush.task.task37.task3708.retrievers;

import com.javarush.task.task37.task3708.cache.LRUCache;
import com.javarush.task.task37.task3708.storage.Storage;

/**
 * Created by ua053202 on 28.08.2017.
 */
public class CachingProxyRetriever implements Retriever {

    private Storage storage;
    private OriginalRetriever originalRetriever;
    private LRUCache<Long, Object> lruCache = new LRUCache(100);

    public CachingProxyRetriever(Storage storage) {
        this.storage = storage;
        originalRetriever = new OriginalRetriever(storage);
    }

    @Override
    public Object retrieve(long id) {
        //return super.retrieve(id);

        Object obj = lruCache.find(id);
        if (obj == null) {
            obj = originalRetriever.retrieve(id);
            lruCache.set(id, obj);
        }

        return obj;
    }
}
