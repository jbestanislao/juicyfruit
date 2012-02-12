package com.onsale.deals.bo;

import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;

public interface MongoEntity<E> {
    BasicDBObject toMongoDocument();
    E getObject(DBObject doc);
}
