package com.store.base.service;

import com.store.base.entity.BaseEntity;
import com.store.base.repo.BaseRepo;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.Serializable;
import java.util.List;
import java.util.Optional;

public abstract class BaseServiceImpl<T extends BaseEntity<ID>, ID extends Serializable> implements BaseService<T,ID> {

    private BaseRepo<T, ID> baseRepo;

    public BaseServiceImpl(BaseRepo<T, ID> baseRepo) {
        this.baseRepo = baseRepo;
    }

    @Override
    public T save(T entity) {
        return baseRepo.save(entity);
    }

    @Override
    public Optional<T> findById(ID id) {
        return baseRepo.findById(id);
    }

    @Override
    public void deleteById(ID id) {
        baseRepo.deleteById(id);
    }

    @Override
    public List<T> findAll() {
        return baseRepo.findAll();
    }

}
