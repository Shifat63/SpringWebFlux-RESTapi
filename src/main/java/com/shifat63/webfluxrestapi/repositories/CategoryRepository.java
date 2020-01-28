package com.shifat63.webfluxrestapi.repositories;

import com.shifat63.webfluxrestapi.domain.Category;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;

// Author: Shifat63

@Repository
public interface CategoryRepository extends ReactiveMongoRepository<Category, String> {

}
