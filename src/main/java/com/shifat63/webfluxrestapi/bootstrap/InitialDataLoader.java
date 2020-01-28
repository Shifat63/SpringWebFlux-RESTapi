package com.shifat63.webfluxrestapi.bootstrap;

import com.shifat63.webfluxrestapi.domain.Category;
import com.shifat63.webfluxrestapi.domain.Recipe;
import com.shifat63.webfluxrestapi.repositories.CategoryRepository;
import com.shifat63.webfluxrestapi.repositories.RecipeRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import java.util.ArrayList;
import java.util.List;

// Author: Shifat63

@Component
public class InitialDataLoader implements CommandLineRunner {

    private RecipeRepository recipeRepository;
    private CategoryRepository categoryRepository;

    private List<Category> categoryList = new ArrayList<>();

    public InitialDataLoader(RecipeRepository recipeRepository, CategoryRepository categoryRepository) {
        this.recipeRepository = recipeRepository;
        this.categoryRepository = categoryRepository;
    }


    @Override
    @Transactional
    public void run(String... args) throws Exception {
        clearAllData();
        loadCategoryData();
        loadRecipeData();
        categoryList.removeAll(categoryList);
    }

    private void clearAllData() throws Exception
    {
        recipeRepository.deleteAll().block();
        categoryRepository.deleteAll().block();
    }

    private void loadCategoryData() throws Exception
    {
        //START: Category data
        Category category1 = new Category();
        category1.setName("American");
        categoryList.add(categoryRepository.save(category1).block());

        Category category2 = new Category();
        category2.setName("Italian");
        categoryList.add(categoryRepository.save(category2).block());

        Category category3 = new Category();
        category3.setName("Mexican");
        categoryList.add(categoryRepository.save(category3).block());

        Category category4 = new Category();
        category4.setName("Fast Food");
        categoryList.add(categoryRepository.save(category4).block());
        //END: Category data
    }

    private void loadRecipeData() throws Exception
    {
        //START: Recipe data
        Recipe recipe1 = new Recipe();
        recipe1.setName("Perfect Guacamole");
        recipe1.getCategories().add(categoryList.get(0));
        recipe1.getCategories().add(categoryList.get(2));
        recipeRepository.save(recipe1).block();

        Recipe recipe2 = new Recipe();
        recipe2.setName("Spicy Grilled Chicken Taco");
        recipe2.getCategories().add(categoryList.get(1));
        recipe2.getCategories().add(categoryList.get(3));
        recipeRepository.save(recipe2).block();
        //END: Recipe data
    }
}
