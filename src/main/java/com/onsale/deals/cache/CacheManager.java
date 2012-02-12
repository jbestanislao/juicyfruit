package com.onsale.deals.cache;

import com.mongodb.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public interface CacheManager {
    void put(String tableName, BasicDBObject doc);
    DBObject getById(String tableName, Object value);
    DBObject getBy(String tableName, String field, Object value);
    void remove(String tableName, Object value);
    void cleanUpCacheLRU();
}
