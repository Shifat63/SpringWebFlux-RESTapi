package com.shifat63.webfluxrestapi.repositories;

import com.shifat63.webfluxrestapi.bootstrap.InitialDataLoader;
import com.shifat63.webfluxrestapi.domain.Category;
import com.shifat63.webfluxrestapi.domain.Recipe;
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
class RecipeRepositoryIT {
    @Autowired
    CategoryRepository categoryRepository;
    @Autowired
    private RecipeRepository recipeRepository;

    Recipe recipe;

    @BeforeEach
    void setUp() throws Exception {
        InitialDataLoader initialDataLoader = new InitialDataLoader(recipeRepository, categoryRepository);
        initialDataLoader.run();
        recipe = new Recipe();
    }

    @Test
    void findAllTest(){
        List<Recipe> recipeList = recipeRepository.findAll().collectList().block();
        assertEquals(2, recipeList.size());
    }

    @Test
    void findByIdTest(){
        String recipeId = recipeRepository.findAll().collectList().block().get(0).getRecipeId();
        assertEquals(recipeId, recipeRepository.findById(recipeId).block().getRecipeId());
    }

    @Test
    void saveTest(){
        String recipeId = recipeRepository.findAll().collectList().block().get(0).getRecipeId();
        recipe = recipeRepository.findById(recipeId).block();
        recipeRepository.save(recipe);
        assertEquals(2, recipeRepository.findAll().collectList().block().size());
        assertEquals(recipe.getRecipeId(), recipeRepository.findById(recipeId).block().getRecipeId()); //While editing id remains same

        Category category = new Category();
        category.setCategoryId("ijkl");
        category.setName("category 1");
        Recipe recipe = new Recipe();
        recipe.setName("recipe 1");
        recipe.getCategories().add(category);
        recipeRepository.save(recipe).block();
        assertEquals(3, recipeRepository.findAll().collectList().block().size()); //While new insertion id should be the next number
    }

    @Test
    void deleteByIdTest(){
        String recipeId = recipeRepository.findAll().collectList().block().get(0).getRecipeId();
        recipeRepository.deleteById(recipeId).block();
        assertEquals(1, recipeRepository.findAll().collectList().block().size());
    }

    @Test
    void deleteAllTest(){
        recipeRepository.deleteAll().block();
        assertEquals(0, recipeRepository.findAll().collectList().block().size());
    }
}