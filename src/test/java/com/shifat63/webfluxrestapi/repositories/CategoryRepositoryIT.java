package com.shifat63.webfluxrestapi.repositories;

import com.shifat63.webfluxrestapi.bootstrap.InitialDataLoader;
import com.shifat63.webfluxrestapi.domain.Category;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import java.util.List;
import static org.junit.jupiter.api.Assertions.assertEquals;

// Author: Shifat63

@ExtendWith(SpringExtension.class)
@DataMongoTest
class CategoryRepositoryIT {
    @Autowired
    CategoryRepository categoryRepository;
    @Autowired
    private RecipeRepository recipeRepository;

    Category category;

    @BeforeEach
    void setUp() throws Exception {
        InitialDataLoader initialDataLoader = new InitialDataLoader(recipeRepository, categoryRepository);
        initialDataLoader.run();
        category = new Category();
    }

    @Test
    void findAllTest(){
        List<Category> categoryList = categoryRepository.findAll().collectList().block();
        assertEquals(4, categoryList.size());
    }

    @Test
    void findByIdTest(){
        String categoryId = categoryRepository.findAll().collectList().block().get(0).getCategoryId();
        assertEquals(categoryId, categoryRepository.findById(categoryId).block().getCategoryId());
    }

    @Test
    void saveTest(){
        String categoryId = categoryRepository.findAll().collectList().block().get(0).getCategoryId();
        category = categoryRepository.findById(categoryId).block();
        categoryRepository.save(category);
        assertEquals(4, categoryRepository.findAll().collectList().block().size());
        assertEquals(category.getCategoryId(), categoryRepository.findById(categoryId).block().getCategoryId()); //While editing id remains same
        category = new Category();
        category.setName("Category 1");
        categoryRepository.save(category).block();
        assertEquals(5, categoryRepository.findAll().collectList().block().size()); //While new insertion id should be the next number
    }

    @Test
    void deleteByIdTest(){
        String categoryId = categoryRepository.findAll().collectList().block().get(0).getCategoryId();
        categoryRepository.deleteById(categoryId).block();
        assertEquals(3, categoryRepository.findAll().collectList().block().size());
    }

    @Test
    void deleteAllTest(){
        categoryRepository.deleteAll().block();
        assertEquals(0, categoryRepository.findAll().collectList().block().size());
    }
}