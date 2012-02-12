package com.onsale.deals.dao.impl;

import com.google.inject.Inject;
import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;
import com.onsale.deals.bo.MongoEntity;
import com.onsale.deals.cache.CacheException;
import com.onsale.deals.cache.CacheManager;
import com.onsale.deals.cache.MongoCacheManager;
import com.onsale.deals.cache.aspect.Cacheable;
import com.onsale.deals.dao.Dao;

import javax.persistence.EntityManager;

public abstract class AbstractDao<E, K> implements Dao<E,K> {
    @Inject
    protected EntityManager em;
    private Class<E> entityClass;
    private CacheManager cacheManager;

    AbstractDao(Class<E> entityClass) {
        this.entityClass = entityClass;
        if (entityClass.isAnnotationPresent(Cacheable.class)) {
            cacheManager = MongoCacheManager.getInstance();
        }
    }

    public E findById(K id) {
        if (entityClass.isAnnotationPresent(Cacheable.class)) {
            DBObject mongoEntity = cacheManager.getById(entityClass.getName(), id);

            if (mongoEntity == null) {
                saveToCache(id);
            } else {
                return getFromCache(mongoEntity);
            }
        }

        return em.find(entityClass, id);
    }

    public void remove(K id) {
        if (entityClass.isAnnotationPresent(Cacheable.class)) {
            DBObject mongoEntity = cacheManager.getById(entityClass.getName(), id);

            if (mongoEntity != null) {
                cacheManager.remove(entityClass.getName(), id);
            }
        }

        em.remove(em.find(entityClass, id));
    }

    private void saveToCache(K id) {
        Object o = em.find(entityClass, id);
        MongoEntity m = (MongoEntity) o;
        BasicDBObject doc = m.toMongoDocument();
        cacheManager.put(entityClass.getName(), doc);
    }

    private E getFromCache(DBObject mongoEntity) {
        try {
            System.out.println("cached hit..." + entityClass.getName());
            MongoEntity m = (MongoEntity)(entityClass.newInstance());
            return (E) (m.getObject(mongoEntity));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
