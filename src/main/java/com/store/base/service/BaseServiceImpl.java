package com.store.base.service;

import com.store.base.entity.BaseEntity;
import com.store.base.repo.BaseRepo;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.Serializable;
import java.util.List;
import java.util.Optional;


public abstract class BaseServiceImpl<T extends BaseEntity<ID>, ID extends Serializable> implements BaseService<T,ID> {
    private BaseRepo<T, ID> baseRepo;

    public BaseServiceImpl(){

    }

    public BaseServiceImpl(BaseRepo<T, ID> baseRepo) {
        this.baseRepo = baseRepo;
    }

    public abstract T save(T entity);

    public abstract Optional<T> findById(ID id);

    public abstract void deleteById(ID id);

    public abstract List<T> findAll();

    public abstract T update(T entity);

}
