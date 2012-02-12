package com.onsale.deals.cache;

import com.mongodb.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.UnknownHostException;

public class MongoCacheManager implements CacheManager {
    private static final Logger log = LoggerFactory.getLogger(MongoCacheManager.class);

    private static final String mongoDbName = "onsale";

    private static MongoCacheManager instance;
    private static Mongo mongo;
    private static DB dbCache;

    private MongoCacheManager() throws CacheException {
        try {
            mongo = new Mongo("localhost", 27017);
            dbCache = mongo.getDB(mongoDbName);
        } catch (UnknownHostException uhe) {
            throw new CacheException(uhe);
        }
    }

    public static MongoCacheManager getInstance() {
        if (instance == null) {
            instance = new MongoCacheManager();
        }

        return instance;
    }

    public void put(String tableName, BasicDBObject doc) {
        DBCollection coll = null;

        if (!dbCache.getCollectionNames().contains(tableName)) {
            coll = dbCache.createCollection(tableName,
                    BasicDBObjectBuilder.start().add("capped", true).add("size", 5).add("max", 5).get());
        } else {
            coll = dbCache.getCollection(tableName);
        }

        coll.insert(doc);
    }

    public DBObject getById(String tableName, Object value) {
        return getBy(tableName, "id", value);
    }

    public DBObject getBy(String tableName, String field, Object value) {
        BasicDBObject query = new BasicDBObject();
        query.put(field, value);

        if (dbCache.getCollectionNames().contains(tableName)) {
            DBCollection coll = dbCache.getCollection(tableName);
            return coll.findOne(query);
        }

        return null;
    }

    public void remove(String tableName, Object value) {
        if (dbCache.getCollectionNames().contains(tableName)) {
            DBCollection coll = dbCache.getCollection(tableName);

            DBObject obj = getById(tableName, value);
            if (obj != null) {
                coll.remove(obj);
            }
        }
    }

    public void cleanUpCacheLRU() {

    }
}
