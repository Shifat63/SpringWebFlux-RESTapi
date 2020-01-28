package com.shifat63.webfluxrestapi.controllers;

import com.shifat63.webfluxrestapi.domain.Recipe;
import com.shifat63.webfluxrestapi.services.service.RecipeService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

// Author: Shifat63

@RestController
@RequestMapping(RecipeController.BASE_URL)
public class RecipeController {

    public static final String BASE_URL = "/api/v1/recipes";

    private final RecipeService recipeService;

    public RecipeController(RecipeService recipeService) {
        this.recipeService = recipeService;
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public Flux<Recipe> getAllRecipes() throws Exception{
        return recipeService.findAll();
    }

    @GetMapping({"/{id}"})
    @ResponseStatus(HttpStatus.OK)
    public Mono<Recipe> getRecipeById(@PathVariable String id) throws Exception{
        return recipeService.findById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<Void> saveOrUpdateRecipe(@RequestBody Recipe recipe) throws Exception{
        return recipeService.saveOrUpdate(recipe);
    }

    @DeleteMapping({"/{id}"})
    @ResponseStatus(HttpStatus.OK)
    public Mono<Void> deleteRecipe(@PathVariable String id) throws Exception{
        return recipeService.deleteById(id);
    }
}
