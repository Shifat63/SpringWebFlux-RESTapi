package com.shifat63.webfluxrestapi.services.serviceImpl;

import com.shifat63.webfluxrestapi.domain.Recipe;
import com.shifat63.webfluxrestapi.repositories.RecipeRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import java.util.List;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

// Author: Shifat63

@ExtendWith(MockitoExtension.class)
class RecipeServiceImplTest {
    @Mock
    RecipeRepository recipeRepository;

    @InjectMocks
    RecipeServiceImpl recipeService;

    Recipe recipe = new Recipe();

    private static final String recipeId = "abcd";
    private String name = "recipe 1";
    private static final String recipeId2 = "efgh";
    private String name2 = "recipe 2";

    @BeforeEach
    void setUp() {
        recipe.setRecipeId(recipeId);
        recipe.setName(name);
    }

    @Test
    void findAllTest() throws Exception {
        Recipe recipe2 = new Recipe();
        recipe2.setRecipeId(recipeId2);
        recipe2.setName(name2);
        when(recipeRepository.findAll()).thenReturn(Flux.just(recipe, recipe2));
        assertNotNull(recipeService.findAll());
        List<Recipe> recipeList = recipeService.findAll().collectList().block();
        assertEquals(2, recipeList.size());
        assertEquals(true, recipeList.contains(recipe));
        assertEquals(true, recipeList.contains(recipe2));
        verify(recipeRepository, times(2)).findAll();
    }

    @Test
    void findByIdTest() throws Exception {
        when(recipeRepository.findById(anyString())).thenReturn(Mono.just(recipe));
        assertNotNull(recipeService.findById(recipeId));
        Recipe recipeFromMono = recipeService.findById(recipeId).block();
        assertEquals(recipeId, recipeFromMono.getRecipeId());
        assertEquals(name, recipeFromMono.getName());
        verify(recipeRepository, times(2)).findById(recipeId);
    }

    @Test
    void saveOrUpdateTest() throws Exception {
        when(recipeRepository.save(any())).thenReturn(Mono.empty());
        assertNotNull(recipeService.saveOrUpdate(recipe));
        verify(recipeRepository, times(1)).save(recipe);
    }

    @Test
    void deleteByIdTest() throws Exception {
        when(recipeRepository.deleteById(anyString())).thenReturn(Mono.empty());
        assertNotNull(recipeService.deleteById(recipeId));
        verify(recipeRepository, times(1)).deleteById(recipeId);
    }

    @Test
    void deleteAllTest() throws Exception {
        when(recipeRepository.deleteAll()).thenReturn(Mono.empty());
        assertNotNull(recipeService.deleteAll());
        verify(recipeRepository, times(1)).deleteAll();
    }
}