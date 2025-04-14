package com.store.base.service;

import com.store.base.entity.BaseEntity;

import java.io.Serializable;
import java.util.List;
import java.util.Optional;

public interface BaseService<T extends BaseEntity<ID>, ID extends Serializable> {
    T save(T entity);
    Optional<T> findById(ID id);
    void deleteById(ID id);
    List<T> findAll();
}
