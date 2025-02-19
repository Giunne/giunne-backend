package com.giunne.itemservice.domain.category.repository;

import com.giunne.itemservice.domain.category.application.interfaces.CategoryRepository;
import com.giunne.itemservice.domain.category.domain.Category;
import com.giunne.itemservice.domain.category.domain.CategoryPath;
import com.giunne.itemservice.domain.category.repository.entity.CategoryEntity;
import com.giunne.itemservice.domain.category.repository.entity.CategoryPathEntity;
import com.giunne.itemservice.domain.category.repository.entity.QCategoryEntity;
import com.giunne.itemservice.domain.category.repository.entity.QCategoryPathEntity;
import com.giunne.itemservice.domain.category.repository.jpa.JpaCategoryPathRepository;
import com.giunne.itemservice.domain.category.repository.jpa.JpaCategoryRepository;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

import static com.querydsl.jpa.JPAExpressions.select;


@Slf4j
@Repository
@RequiredArgsConstructor
public class CategoryRepositoryImpl implements CategoryRepository {
    private final JPAQueryFactory queryFactory;
    private final JpaCategoryPathRepository categoryPathRepository;
    private final JpaCategoryRepository categoryRepository;
    private final QCategoryPathEntity categoryPathEntity = QCategoryPathEntity.categoryPathEntity;
    private final QCategoryEntity categoryEntity = QCategoryEntity.categoryEntity;

    @Transactional
    public void insertCategory(Category node, Category parents) {
        // 자기자신 추가
        saveCategoryPath(node);
        List<CategoryEntity> fetch = getCategoryByChild(parents);
        List<CategoryPathEntity> list = new ArrayList<>();
        for (CategoryEntity category : fetch) {
            list.add(CategoryPathEntity.builder()
                    .parents(category)
                    .child(new CategoryEntity(node))
                    .build());
        }
        categoryPathRepository.saveAll(list);
    }

    @Transactional
    public void updateQuery(Category node, CategoryPath nodePath) {
        queryFactory.update(categoryPathEntity)
                .set(categoryPathEntity.child, new CategoryEntity(node))
                .where(categoryPathEntity.id.eq(nodePath.getId()))
                .execute();
    }

    @Transactional
    public void insertBetween(Category newNode, Category currentNode) {
        saveCategoryPath(newNode);
        List<CategoryEntity> fetch = queryFactory.select(categoryPathEntity.child)
                .from(categoryPathEntity)
                .where(categoryPathEntity.id.eq(currentNode.getId()))
                .fetch();

        List<CategoryPathEntity> list = new ArrayList<>();
        for (CategoryEntity paths : fetch) {
            list.add(
                    CategoryPathEntity.builder()
                            .parents(new CategoryEntity(newNode))
                            .child(paths)
                            .build()
            );
        }
        updateQuery(new CategoryEntity(currentNode), new CategoryEntity(newNode));
        categoryPathRepository.saveAll(list);
    }

    @Transactional
    public void moveWithSubTree(Category target, Category move) {
        deleteByCategoryPath(target);
        categoryPathRepository.shiftInsertData(move.getId(), target.getId());
    }

    private void updateQuery(CategoryEntity currentNode, CategoryEntity newNode) {
        queryFactory.update(categoryPathEntity)
                .set(categoryPathEntity.child, newNode)
                .where(categoryPathEntity.child.id.eq(currentNode.getId()),
                        categoryPathEntity.child.id.ne(categoryPathEntity.parents.id))
                .execute();
    }

    private List<CategoryEntity> getCategoryByChild(Category parents) {
        return queryFactory.select(categoryPathEntity.parents)
                .from(categoryPathEntity)
                .where(categoryPathEntity.child.id.eq(parents.getId()))
                .fetch()
                ;
    }

    private void saveCategoryPath(Category node) {
        categoryPathRepository.save(
                CategoryPathEntity.builder()
                        .parents(new CategoryEntity(node))
                        .child(new CategoryEntity(node))
                        .build()
        );
    }

    private void deleteByCategoryPath(Category target) {
        QCategoryPathEntity q2 = new QCategoryPathEntity("q2");
        QCategoryPathEntity q3 = new QCategoryPathEntity("q3");
        queryFactory
                .delete(categoryPathEntity)
                .where(
                        categoryPathEntity.child.in(
                                select(q2.child)
                                        .from(q2)
                                        .where(q2.parents.id.eq(target.getId()))
                        ),
                        categoryPathEntity.parents.in(
                                select(q3.parents)
                                        .from(q3)
                                        .where(q3.child.id.eq(target.getId()),
                                                q3.parents.ne(q3.child))
                        )
                ).execute();
    }

    public void deleteCategoryById(Category target) {
        categoryRepository.deleteById(target.getId());
    }

    public Category findById(Long id) {
        CategoryEntity categoryEntity = categoryRepository.findById(id)
                .orElseThrow(IllegalArgumentException::new);
        return categoryEntity.toCategory();
    }

    @Override
    public void deleteCategory(Category target) {
        deleteCategoryById(target);
    }

    List<CategoryPathEntity> findAllByChild(Category category) {
        CategoryEntity categoryEntity = categoryRepository.findById(category.getId())
                .orElseThrow(IllegalArgumentException::new);

        return categoryPathRepository.findAllByChild(categoryEntity);
    }

    @Override
    public Map<Long, List<Category>> getCategories() {
        List<CategoryEntity> allCategories = categoryRepository.findAll();
        Map<Long, List<CategoryEntity>> collect = allCategories.stream()
                .collect(Collectors.toMap(
                        CategoryEntity::getId,
                        this::findDirectChildren
                ));

        List<CategoryEntity> leafs = findLeaf();
        List<Category> roots = findRoot().stream().map(i -> {
                    if (leafs.stream()
                            .map(CategoryEntity::getId)
                            .anyMatch(id ->
                                    Objects.equals(id, i.getId())
                            )) {
                        return i.toCategory(true, true);
                    }
                    return i.toCategory(true, false);
                }
        ).toList();


        Map<Long, List<Category>> categories = collect.entrySet().stream()
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        entry -> entry.getValue().stream()
                                .map(item ->
                                        {
                                            if (leafs.stream()
                                                    .map(CategoryEntity::getId)
                                                    .anyMatch(id ->
                                                            Objects.equals(id, item.getId())
                                                    )) {
                                                return item.toCategory(false, true);
                                            } else if (roots.stream()
                                                    .map(Category::getId)
                                                    .anyMatch(id ->
                                                            Objects.equals(id, item.getId())
                                                    )) {
                                                return item.toCategory(true, false);
                                            }
                                            return item.toCategory();
                                        }
                                        )
                                .collect(Collectors.toList())
                ));
        categories.put(0L, roots);

        return categories;

    }

    public List<CategoryEntity> findDirectChildren(CategoryEntity category) {
        return queryFactory
                .selectFrom(categoryEntity)
                .join(categoryPathEntity).on(categoryEntity.id.eq(categoryPathEntity.child.id))
                .where(
                        categoryPathEntity.parents.id.eq(category.getId())
                                .and(categoryEntity.id.ne(categoryPathEntity.parents.id))
                )
                .fetch();

    }

    public List<CategoryEntity> findRoot() {
        return queryFactory
                .selectFrom(categoryEntity)
                .where(
                        JPAExpressions.selectFrom(categoryPathEntity)
                                .where(
                                        categoryPathEntity.child.id.eq(categoryEntity.id)
                                                .and(categoryPathEntity.parents.id.ne(categoryEntity.id))
                                ).notExists())
                .fetch();
    }

    public List<CategoryEntity> findLeaf() {
        return queryFactory
                .selectFrom(categoryEntity)
                .where(
                        JPAExpressions.selectFrom(categoryPathEntity)
                                .where(
                                        categoryPathEntity.parents.id.eq(categoryEntity.id)
                                                .and(categoryPathEntity.child.id.ne(categoryEntity.id))
                                ).notExists())
                .fetch();
    }

}
