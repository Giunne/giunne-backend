package com.giunne.itemservice.domain.category.repository.jpa;

import com.giunne.itemservice.domain.category.repository.entity.CategoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface JpaCategoryRepository extends JpaRepository<CategoryEntity, Long> {
    Optional<CategoryEntity> findByCategoryName_CategoryName(String name);
}
