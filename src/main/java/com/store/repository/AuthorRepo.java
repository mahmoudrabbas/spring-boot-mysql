package com.store.repository;

import com.store.base.repo.BaseRepo;
import com.store.entity.Author;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AuthorRepo extends BaseRepo<Author, Long> {
    Optional<Author> findByEmail(String email);
}
