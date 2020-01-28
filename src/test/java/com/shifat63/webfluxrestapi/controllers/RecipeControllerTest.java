package com.shifat63.webfluxrestapi.controllers;

import com.shifat63.webfluxrestapi.domain.Category;
import com.shifat63.webfluxrestapi.domain.Recipe;
import com.shifat63.webfluxrestapi.services.service.RecipeService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.exceptions.base.MockitoException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

class RecipeControllerTest {

    @Mock
    RecipeService recipeService;

    @InjectMocks
    RecipeController recipeController;

    WebTestClient webTestClient;

    private static final String recipeId = "abcd";
    private String name = "recipe 1";
    private static final String recipeId2 = "efgh";
    private String name2 = "recipe 2";
    private static final String categoryId = "ijkl";
    private String categoryName = "category 1";
    private static final String categoryId2 = "mnop";
    private String categoryName2 = "category 2";

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        webTestClient = WebTestClient.bindToController(recipeController)
                .controllerAdvice(new ExceptionHandlerController()).build();
    }

    @Test
    void getAllRecipesTest() throws Exception {
        Category category = new Category();
        category.setCategoryId(categoryId);
        category.setName(categoryName);

        Category category2 = new Category();
        category2.setCategoryId(categoryId2);
        category2.setName(categoryName2);

        Recipe recipe = new Recipe();
        recipe.setRecipeId(recipeId);
        recipe.setName(name);
        recipe.getCategories().add(category);

        Recipe recipe2 = new Recipe();
        recipe2.setRecipeId(recipeId2);
        recipe2.setName(name2);
        recipe2.getCategories().add(category2);

        when(recipeService.findAll()).thenReturn(Flux.just(recipe, recipe2));
        webTestClient.get()
                .uri(recipeController.BASE_URL)
                .header(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_VALUE)
                .exchange()
                .expectStatus().isOk()
                .expectBodyList(Recipe.class)
                .hasSize(2)
                .contains(recipe)
                .contains(recipe2);

        verify(recipeService, times(1)).findAll();
    }

    @Test
    void getRecipeByIdTest() throws Exception {
        Category category = new Category();
        category.setCategoryId(categoryId);
        category.setName(categoryName);

        Recipe recipe = new Recipe();
        recipe.setRecipeId(recipeId);
        recipe.setName(name);
        recipe.getCategories().add(category);

        when(recipeService.findById(anyString())).thenReturn(Mono.just(recipe));
        webTestClient.get()
                .uri(recipeController.BASE_URL + "/1")
                .header(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_VALUE)
                .exchange()
                .expectStatus().isOk()
                .expectBodyList(Recipe.class)
                .hasSize(1)
                .contains(recipe);

        verify(recipeService, times(1)).findById(anyString());
    }

    @Test
    void saveOrUpdateRecipeTest() throws Exception {
        Category category = new Category();
        category.setCategoryId(categoryId);
        category.setName(categoryName);

        Recipe recipe = new Recipe();
        recipe.setRecipeId(recipeId);
        recipe.setName(name);
        recipe.getCategories().add(category);

        Mono<Recipe> recipeMono = Mono.just(recipe);

        when(recipeService.saveOrUpdate(any())).thenReturn(Mono.empty());
        webTestClient.post()
                .uri(recipeController.BASE_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .body(recipeMono, Recipe.class)
                .exchange()
                .expectStatus().isCreated();

        verify(recipeService, times(1)).saveOrUpdate(any());
    }

    @Test
    void deleteRecipeTest() throws Exception {
        when(recipeService.deleteById(anyString())).thenReturn(Mono.empty());
        webTestClient.delete()
                .uri(recipeController.BASE_URL + "/1")
                .header(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_VALUE)
                .exchange()
                .expectStatus().isOk();

        verify(recipeService, times(1)).deleteById(anyString());
    }

    @Test
    void exceptionTest() throws Exception {

        when(recipeService.findById(anyString())).thenThrow(MockitoException.class);

        webTestClient.get()
                .uri(recipeController.BASE_URL + "/3456")
                .header(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_VALUE)
                .exchange()
                .expectStatus().isBadRequest();
    }
}