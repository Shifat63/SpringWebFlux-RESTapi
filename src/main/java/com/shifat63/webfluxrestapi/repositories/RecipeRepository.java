package com.shifat63.webfluxrestapi.repositories;

import com.shifat63.webfluxrestapi.domain.Recipe;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;

// Author: Shifat63

@Repository
public interface RecipeRepository extends ReactiveMongoRepository<Recipe, String> {
}
