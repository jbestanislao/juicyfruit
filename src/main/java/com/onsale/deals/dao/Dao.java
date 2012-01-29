package com.onsale.deals.dao;

public interface Dao<E, K> {
    E findById(K  id);
}
