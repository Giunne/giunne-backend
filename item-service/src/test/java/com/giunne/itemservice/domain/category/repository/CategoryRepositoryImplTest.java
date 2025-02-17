package com.giunne.itemservice.domain.category.repository;

import com.giunne.itemservice.domain.category.application.interfaces.CategoryRepository;
import com.giunne.itemservice.domain.category.domain.Category;
import com.giunne.itemservice.domain.category.domain.CategoryPath;
import com.giunne.itemservice.domain.category.domain.type.CategoryName;
import com.giunne.itemservice.domain.category.repository.entity.CategoryEntity;
import com.giunne.itemservice.domain.category.repository.entity.CategoryPathEntity;
import com.giunne.itemservice.domain.category.repository.jpa.JpaCategoryPathRepository;
import com.giunne.itemservice.domain.category.repository.jpa.JpaCategoryRepository;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@SpringBootTest
@Transactional
class CategoryRepositoryImplTest {

    @Autowired
    private CategoryRepository categoryRepository;
    @Autowired
    private JpaCategoryRepository jpaCategoryRepository;
    @Autowired
    private JpaCategoryPathRepository jpaCategoryPathRepository;
    @Autowired
    private JpaCategoryPathRepository categoryPathRepository;
    @Autowired
    private EntityManager em;


//    @Test
//    void insertCategoryTest() {
//        CategoryEntity cloth = new CategoryEntity(Category.builder()
//                .categoryName(
//                        CategoryName.from("옷")
//                ).build());
//        CategoryEntity man = new CategoryEntity(Category.builder()
//                .categoryName(
//                        CategoryName.from("남성")
//                ).build());
//        CategoryEntity woman = new CategoryEntity(Category.builder()
//                .categoryName(
//                        CategoryName.from("여성")
//                ).build());
//
//        List<CategoryEntity> categoryEntities = jpaCategoryRepository.saveAll(List.of(cloth, man, woman));
//
//        CategoryEntity clothCategory = categoryEntities.stream().filter(i -> i.getCategoryName().getCategoryName().equals("옷")).findFirst().get();
//        CategoryEntity manCategory = categoryEntities.stream().filter(i -> i.getCategoryName().getCategoryName().equals("남성")).findFirst().get();
//        CategoryEntity womanCategory = categoryEntities.stream().filter(i -> i.getCategoryName().getCategoryName().equals("여성")).findFirst().get();
//
//        CategoryPathEntity clothToCloth =new CategoryPathEntity(CategoryPath.builder()
//                .parents(clothCategory.toCategory())
//                .child(clothCategory.toCategory())
//                .build()) ;
//
//        CategoryPathEntity manToMan = new CategoryPathEntity(CategoryPath.builder()
//                .parents(manCategory.toCategory())
//                .child(manCategory.toCategory())
//                .build());
//
//        CategoryPathEntity womanToWoman =new CategoryPathEntity(CategoryPath.builder()
//                .parents(womanCategory.toCategory())
//                .child(womanCategory.toCategory())
//                .build()) ;
//
//        CategoryPathEntity clothToMan =new CategoryPathEntity(CategoryPath.builder()
//                .parents(clothCategory.toCategory())
//                .child(manCategory.toCategory())
//                .build()) ;
//
//        CategoryPathEntity clothToWoman = new CategoryPathEntity(CategoryPath.builder()
//                .parents(clothCategory.toCategory())
//                .child(womanCategory.toCategory())
//                .build()) ;
//
//
//        List<CategoryPathEntity> categoryPathEntities = jpaCategoryPathRepository.saveAll(List.of(clothToCloth, manToMan, womanToWoman, clothToMan, clothToWoman));
//        em.flush();
//        em.clear();
//
//    }

    @Test
    @Rollback(false)
    void initCategory() {
        CategoryEntity avatar = new CategoryEntity(Category.builder()
                .categoryName(
                        CategoryName.from("아바타")
                ).build());
        CategoryEntity cloth = new CategoryEntity(Category.builder()
                .categoryName(
                        CategoryName.from("옷")
                ).build());
        CategoryEntity hat = new CategoryEntity(Category.builder()
                .categoryName(
                        CategoryName.from("모자")
                ).build());
        CategoryEntity face = new CategoryEntity(Category.builder()
                .categoryName(
                        CategoryName.from("얼굴")
                ).build());
        CategoryEntity colorGacha = new CategoryEntity(Category.builder()
                .categoryName(
                        CategoryName.from("색상 뽑기")
                ).build());
        CategoryEntity colorAvatar = new CategoryEntity(Category.builder()
                .categoryName(
                        CategoryName.from("아바타 색상")
                ).build());

        List<CategoryEntity> categoryEntities = jpaCategoryRepository.saveAll(List.of(avatar, cloth, hat, face, colorGacha, colorAvatar));

        CategoryEntity avatarCategory = categoryEntities.stream().filter(i -> i.getCategoryName().getCategoryName().equals("아바타")).findFirst().get();
        CategoryEntity clothCategory = categoryEntities.stream().filter(i -> i.getCategoryName().getCategoryName().equals("옷")).findFirst().get();
        CategoryEntity hatCategory = categoryEntities.stream().filter(i -> i.getCategoryName().getCategoryName().equals("모자")).findFirst().get();
        CategoryEntity faceCategory = categoryEntities.stream().filter(i -> i.getCategoryName().getCategoryName().equals("얼굴")).findFirst().get();
        CategoryEntity colorGachaCategory = categoryEntities.stream().filter(i -> i.getCategoryName().getCategoryName().equals("색상 뽑기")).findFirst().get();
        CategoryEntity colorAvatarCategory = categoryEntities.stream().filter(i -> i.getCategoryName().getCategoryName().equals("아바타 색상")).findFirst().get();

        CategoryPathEntity avatarToAvatar =new CategoryPathEntity(CategoryPath.builder()
                .parents(avatarCategory.toCategory())
                .child(avatarCategory.toCategory())
                .build()) ;
        CategoryPathEntity clothToCloth =new CategoryPathEntity(CategoryPath.builder()
                .parents(clothCategory.toCategory())
                .child(clothCategory.toCategory())
                .build()) ;
        CategoryPathEntity hatToHat =new CategoryPathEntity(CategoryPath.builder()
                .parents(hatCategory.toCategory())
                .child(hatCategory.toCategory())
                .build()) ;
        CategoryPathEntity faceToFace =new CategoryPathEntity(CategoryPath.builder()
                .parents(faceCategory.toCategory())
                .child(faceCategory.toCategory())
                .build()) ;
        CategoryPathEntity colorGachaToColorGacha =new CategoryPathEntity(CategoryPath.builder()
                .parents(colorGachaCategory.toCategory())
                .child(colorGachaCategory.toCategory())
                .build()) ;
        CategoryPathEntity colorAvatarTocolorAvatar =new CategoryPathEntity(CategoryPath.builder()
                .parents(colorAvatarCategory.toCategory())
                .child(colorAvatarCategory.toCategory())
                .build()) ;

        jpaCategoryPathRepository.saveAll(List.of(avatarToAvatar
                , clothToCloth
                , hatToHat
                , faceToFace
                , colorGachaToColorGacha
                , colorAvatarTocolorAvatar));

        em.flush();
        em.clear();
    }

}