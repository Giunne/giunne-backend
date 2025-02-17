package com.giunne.itemservice.domain.category.application;

import com.giunne.itemservice.domain.category.application.dto.request.CreateCategoryRequestDto;
import com.giunne.itemservice.domain.category.application.dto.request.MoveWithSubCategoryRequestDto;
import com.giunne.itemservice.domain.category.application.dto.request.UpdateCategoryRequestDto;
import com.giunne.itemservice.domain.category.application.dto.response.CategoryResponseDto;
import com.giunne.itemservice.domain.category.application.interfaces.CategoryRepository;
import com.giunne.itemservice.domain.category.domain.Category;
import com.giunne.itemservice.domain.category.domain.CategoryPath;
import com.giunne.itemservice.domain.category.domain.type.CategoryName;
import com.giunne.itemservice.domain.category.repository.entity.CategoryEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CategoryService {

    private final CategoryRepository categoryRepository;

    public void insertCategory(CreateCategoryRequestDto dto){
        Category parents = categoryRepository.findById(dto.parentsId());
        Category category = Category.builder().categoryName(CategoryName.from(dto.categoryName())).build();
        categoryRepository.insertCategory(parents, category);
    }

    public void insertBetween(UpdateCategoryRequestDto dto){
        Category category = Category.builder()
                .categoryName(
                        CategoryName.from(dto.categoryName())
                ).build();
        Category parents = categoryRepository.findById(dto.currentId());
        categoryRepository.insertBetween(category, parents);
    }

    public void moveWithSubTree(MoveWithSubCategoryRequestDto dto){
        Category current = categoryRepository.findById(dto.currentId());
        Category move = categoryRepository.findById(dto.moveId());
        categoryRepository.moveWithSubTree(current, move);
    }


    public Map<Long, List<CategoryResponseDto>> getCategories() {

        Map<Long, List<Category>> categories = categoryRepository.getCategories();

        return categories.entrySet().stream()
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        entry -> entry.getValue().stream()
                                .map(category -> new CategoryResponseDto(
                                        category.getId(),
                                        category.getCategoryName().getCategoryName(),
                                        category.getIsRoot(),
                                        category.getIsLeaf()
                                ))
                                .collect(Collectors.toList())
                ));
    }
}
