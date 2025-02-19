package com.giunne.itemservice.domain.category.application.interfaces;

import com.giunne.itemservice.domain.category.domain.Category;
import com.giunne.itemservice.domain.category.domain.CategoryPath;

import java.util.List;
import java.util.Map;

public interface CategoryRepository {
    void insertCategory(Category node, Category parents);
    void updateQuery(Category node, CategoryPath nodePath);
    void insertBetween(Category newNode, Category currentNode);
    void moveWithSubTree(Category target, Category move);
    Category findById(Long id);
    void deleteCategory(Category target);
    Map<Long, List<Category>> getCategories();
}
