package com.store.service;

import com.store.base.repo.BaseRepo;
import com.store.base.service.BaseServiceImpl;
import com.store.entity.Author;
import com.store.repository.AuthorRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

@Service
public class AuthorService extends BaseServiceImpl<Author, Long> {


    @Autowired
    private AuthorRepo authorRepo;

    public AuthorService(BaseRepo<Author, Long> baseRepo) {
        super(baseRepo);
    }


    public Author findByEmail(String email){
        return authorRepo.findByEmail(email);
    }
}
