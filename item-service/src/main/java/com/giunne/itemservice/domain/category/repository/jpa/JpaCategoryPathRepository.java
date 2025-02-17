package com.giunne.itemservice.domain.category.repository.jpa;

import com.giunne.itemservice.domain.category.repository.entity.CategoryEntity;
import com.giunne.itemservice.domain.category.repository.entity.CategoryPathEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface JpaCategoryPathRepository extends JpaRepository<CategoryPathEntity, Long> {
    List<CategoryPathEntity> findAllByChild(CategoryEntity category);
    void deleteAllByChild(CategoryEntity category);

    @Modifying
    @Query(
            value = "insert into category_path (parents_id,child_id) " +
                    "select sup.parents_id,sub.child_id from category_path sup " +
                    "cross join category_path sub "+
                    "where sup.child_id = :parentsId and sub.parents_id = :childId",
            nativeQuery = true
    )
    void shiftInsertData(@Param("parentsId") Long parentsId, @Param("childId") Long childId);

}
