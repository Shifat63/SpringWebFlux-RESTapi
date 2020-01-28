package com.shifat63.webfluxrestapi.services.serviceImpl;

import com.shifat63.webfluxrestapi.domain.Recipe;
import com.shifat63.webfluxrestapi.repositories.RecipeRepository;
import com.shifat63.webfluxrestapi.services.service.RecipeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

// Author: Shifat63

@Slf4j
@Service
public class RecipeServiceImpl implements RecipeService {

    private RecipeRepository recipeRepository;

    public RecipeServiceImpl(RecipeRepository recipeRepository) {
        this.recipeRepository = recipeRepository;
    }

    @Override
    public Flux<Recipe> findAll() throws Exception {
        log.info("start: findAll method of RecipeServiceImpl");
        Flux<Recipe> recipeFlux = recipeRepository.findAll();
        log.info("end: findAll method of RecipeServiceImpl");
        return recipeFlux;
    }

    @Override
    public Mono<Recipe> findById(String recipeId) throws Exception {
        log.info("start: findById method of RecipeServiceImpl");
        Mono<Recipe> recipeMono = recipeRepository.findById(recipeId);
        log.info("end: findById method of RecipeServiceImpl");
        return recipeMono;
    }

    @Override
    public Mono<Void> saveOrUpdate(Recipe recipe) throws Exception {
        log.info("start: saveOrUpdate method of RecipeServiceImpl");
        recipeRepository.save(recipe).subscribe();
        log.info("end: saveOrUpdate method of RecipeServiceImpl");
        return Mono.empty();
    }

    @Override
    public Mono<Void> deleteById(String recipeId) throws Exception {
        log.info("start: deleteById method of RecipeServiceImpl");
        recipeRepository.deleteById(recipeId).subscribe();
        log.info("end: deleteById method of RecipeServiceImpl");
        return Mono.empty();
    }

    @Override
    public Mono<Void> deleteAll() throws Exception {
        log.info("start: deleteAll method of RecipeServiceImpl");
        recipeRepository.deleteAll().subscribe();
        log.info("start: deleteAll method of RecipeServiceImpl");
        return Mono.empty();
    }
}
