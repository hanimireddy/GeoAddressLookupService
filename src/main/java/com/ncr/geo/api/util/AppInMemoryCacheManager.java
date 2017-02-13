package com.ncr.geo.api.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AppInMemoryCacheManager<K,T> {
	private static final Logger logger = LoggerFactory.getLogger(AppInMemoryCacheManager.class);
	private long timeToLive;
    private Map<K,CacheObject> cacheMap;
    
    protected class CacheObject {
        public long lastAccessed = System.currentTimeMillis();
        public T value;
 
        protected CacheObject(T value) {
            this.value = value;
        }
    }
    
    public AppInMemoryCacheManager(long timeToLive, final long timerInterval) {
        this.timeToLive = timeToLive * 1000;
 
        cacheMap = new HashMap<>();
 
        if (timeToLive > 0 && timerInterval > 0) {
 
            Thread t = new Thread(new Runnable() {
                public void run() {
                    while (true) {
                        try {
                            Thread.sleep(timerInterval * 1000);
                        } catch (InterruptedException ex) {
                        	logger.error("Exception in AppInMemoryCacheManager: ", ex.getMessage());
                        }
                        cleanup();
                    }
                }
            });
 
            t.setDaemon(true);
            t.start();
        }
    }
    
    public void put(K key, T value) {
            cacheMap.put(key, new CacheObject(value));
    }
 
    public T get(K key) {
        	CacheObject c = (CacheObject) cacheMap.get(key);
            if (c == null)
                return null;
            else {
                c.lastAccessed = System.currentTimeMillis();
                return c.value;
            }
    }
    

    public List<T> getCachedMapObjectList(){
    		final List<T>  cachedObjList = new ArrayList<>();
    		  cacheMap.forEach((k,v)->{
    			  CacheObject c = (CacheObject)v;
    			  cachedObjList.add(c.value);
    				
    			});
    		  return cachedObjList;
    }
    
    public void remove(K key) {
            cacheMap.remove(key);        
    }
 
    public int size() {
            return cacheMap.size();
    }
    

    public void cleanup() {
 
        long now = System.currentTimeMillis();
        logger.info("AppInMemoryCacheManager: cleanup method started");
        final List<K> deleteKey = new ArrayList<K>((cacheMap.size() / 2) + 1);
        	cacheMap.forEach((k,v)->{
			  CacheObject c = (CacheObject)v;
			  if (c != null && (now > (timeToLive + c.lastAccessed))) {
                  deleteKey.add(k);
              }
				
			});
        	
         for (K delKey : deleteKey) {
                cacheMap.remove(delKey);
        }
        
        Thread.yield();
    }
}
