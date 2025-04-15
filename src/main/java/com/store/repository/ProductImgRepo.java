package com.store.repository;

import com.store.base.repo.BaseRepo;
import com.store.entity.ProductImage;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductImgRepo extends BaseRepo<ProductImage, Long> {
}
