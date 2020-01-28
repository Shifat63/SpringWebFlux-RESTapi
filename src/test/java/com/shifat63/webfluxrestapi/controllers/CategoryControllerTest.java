package com.shifat63.webfluxrestapi.controllers;

import com.shifat63.webfluxrestapi.domain.Category;
import com.shifat63.webfluxrestapi.services.service.CategoryService;
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
import static org.mockito.Mockito.*;

// Author: Shifat63

class CategoryControllerTest {

    @Mock
    CategoryService categoryService;

    @InjectMocks
    CategoryController categoryController;

    WebTestClient webTestClient;

    private static final String categoryId = "abcd";
    private String name = "category 1";
    private static final String categoryId2 = "efgh";
    private String name2 = "category 2";

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        webTestClient = WebTestClient.bindToController(categoryController)
                .controllerAdvice(new ExceptionHandlerController()).build();
    }

    @Test
    void getAllCategoriesTest() throws Exception {
        Category category = new Category();
        category.setCategoryId(categoryId);
        category.setName(name);

        Category category2 = new Category();
        category2.setCategoryId(categoryId2);
        category2.setName(name2);

        when(categoryService.findAll()).thenReturn(Flux.just(category, category2));
        webTestClient.get()
                .uri(categoryController.BASE_URL)
                .header(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_VALUE)
                .exchange()
                .expectStatus().isOk()
                .expectBodyList(Category.class)
                .hasSize(2)
                .contains(category)
                .contains(category2);

        verify(categoryService, times(1)).findAll();
    }

    @Test
    void getCategoryByIdTest() throws Exception {
        Category category = new Category();
        category.setCategoryId(categoryId);
        category.setName(name);

        when(categoryService.findById(anyString())).thenReturn(Mono.just(category));
        webTestClient.get()
                .uri(categoryController.BASE_URL + "/1")
                .header(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_VALUE)
                .exchange()
                .expectStatus().isOk()
                .expectBodyList(Category.class)
                .hasSize(1)
                .contains(category);

        verify(categoryService, times(1)).findById(anyString());
    }

    @Test
    void saveOrUpdateCategoryTest() throws Exception {
        Category category = new Category();
        category.setCategoryId(categoryId);
        category.setName(name);

        Mono<Category> categoryMono = Mono.just(category);

        when(categoryService.saveOrUpdate(any())).thenReturn(Mono.empty());
        webTestClient.post()
                .uri(categoryController.BASE_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .body(categoryMono, Category.class)
                .exchange()
                .expectStatus().isCreated();

        verify(categoryService, times(1)).saveOrUpdate(any());
    }

    @Test
    void deleteCategoryTest() throws Exception {
        when(categoryService.deleteById(anyString())).thenReturn(Mono.empty());
        webTestClient.delete()
                .uri(categoryController.BASE_URL + "/1")
                .header(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_VALUE)
                .exchange()
                .expectStatus().isOk();

        verify(categoryService, times(1)).deleteById(anyString());
    }

    @Test
    void exceptionTest() throws Exception {

        when(categoryService.findById(anyString())).thenThrow(MockitoException.class);

        webTestClient.get()
                .uri(categoryController.BASE_URL + "/3456")
                .header(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_VALUE)
                .exchange()
                .expectStatus().isBadRequest();
    }
}