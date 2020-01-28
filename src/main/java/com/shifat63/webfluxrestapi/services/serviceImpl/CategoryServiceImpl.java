package com.shifat63.webfluxrestapi.services.serviceImpl;

import com.shifat63.webfluxrestapi.domain.Category;
import com.shifat63.webfluxrestapi.repositories.CategoryRepository;
import com.shifat63.webfluxrestapi.services.service.CategoryService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

// Author: Shifat63

@Slf4j
@Service
public class CategoryServiceImpl implements CategoryService {

    private CategoryRepository categoryRepository;

    public CategoryServiceImpl(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @Override
    public Flux<Category> findAll() throws Exception {
        log.info("start: findAll method of CategoryServiceImpl");
        Flux<Category> categoryFlux = categoryRepository.findAll();
        log.info("end: findAll method of CategoryServiceImpl");
        return categoryFlux;
    }

    @Override
    public Mono<Category> findById(String categoryId) throws Exception {
        log.info("start: findById method of CategoryServiceImpl");
        Mono<Category> categoryMono = categoryRepository.findById(categoryId);
        log.info("end: findById method of CategoryServiceImpl");
        return categoryMono;
    }

    @Override
    public Mono<Void> saveOrUpdate(Category category) throws Exception {
        log.info("start: saveOrUpdate method of CategoryServiceImpl");
        categoryRepository.save(category).subscribe();
        log.info("end: saveOrUpdate method of CategoryServiceImpl");
        return Mono.empty();
    }

    @Override
    public Mono<Void> deleteById(String categoryId) throws Exception {
        log.info("start: deleteById method of CategoryServiceImpl");
        categoryRepository.deleteById(categoryId).subscribe();
        log.info("end: deleteById method of CategoryServiceImpl");
        return Mono.empty();
    }

    @Override
    public Mono<Void> deleteAll() throws Exception {
        log.info("start: deleteAll method of CategoryServiceImpl");
        categoryRepository.deleteAll().subscribe();
        log.info("start: deleteAll method of CategoryServiceImpl");
        return Mono.empty();
    }
}
