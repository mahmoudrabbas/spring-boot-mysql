package com.store.repository;

import com.store.base.repo.BaseRepo;
import com.store.entity.Author;
import org.springframework.stereotype.Repository;

@Repository
public interface AuthorRepo extends BaseRepo<Author, Long> {
    public Author findByEmail(String email);
}
