package com.shifat63.webfluxrestapi.controllers;

import com.shifat63.webfluxrestapi.domain.Category;
import com.shifat63.webfluxrestapi.services.service.CategoryService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

// Author: Shifat63

@RestController
@RequestMapping(CategoryController.BASE_URL)
public class CategoryController {

    public static final String BASE_URL = "/api/v1/categories";

    private final CategoryService categoryService;

    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public Flux<Category> getAllCategories() throws Exception{
        return categoryService.findAll();
    }

    @GetMapping({"/{id}"})
    @ResponseStatus(HttpStatus.OK)
    public Mono<Category> getCategoryById(@PathVariable String id) throws Exception{
        return categoryService.findById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<Void> saveOrUpdateCategory(@RequestBody Category category) throws Exception{
        return categoryService.saveOrUpdate(category);
    }

    @DeleteMapping({"/{id}"})
    @ResponseStatus(HttpStatus.OK)
    public Mono<Void> deleteCategory(@PathVariable String id) throws Exception{
        return categoryService.deleteById(id);
    }
}
