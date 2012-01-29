package com.onsale.deals.dao.impl;

import com.google.inject.Inject;
import com.onsale.deals.dao.Dao;

import javax.persistence.EntityManager;

public abstract class AbstractDao<E, K> implements Dao<E,K> {
    @Inject
    protected EntityManager em;
    private Class<E> entityClass;

    AbstractDao(Class<E> entityClass) {
        this.entityClass = entityClass;
    }

    public E findById(K id) {
        return em.find(entityClass, id);
    }
}
