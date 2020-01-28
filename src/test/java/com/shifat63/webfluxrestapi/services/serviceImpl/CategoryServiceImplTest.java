package com.shifat63.webfluxrestapi.services.serviceImpl;

import com.shifat63.webfluxrestapi.domain.Category;
import com.shifat63.webfluxrestapi.repositories.CategoryRepository;
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
import static org.mockito.Mockito.*;

// Author: Shifat63

@ExtendWith(MockitoExtension.class)
class CategoryServiceImplTest {
    @Mock
    CategoryRepository categoryRepository;

    @InjectMocks
    CategoryServiceImpl categoryService;

    Category category = new Category();

    private static final String categoryId = "abcd";
    private String name = "category 1";
    private static final String categoryId2 = "efgh";
    private String name2 = "category 2";

    @BeforeEach
    void setUp() {
        category.setCategoryId(categoryId);
        category.setName(name);
    }

    @Test
    void findAllTest() throws Exception {
        Category category2 = new Category();
        category2.setCategoryId(categoryId2);
        category2.setName(name2);
        when(categoryRepository.findAll()).thenReturn(Flux.just(category, category2));
        assertNotNull(categoryService.findAll());
        List<Category> categoryList = categoryService.findAll().collectList().block();
        assertEquals(2, categoryList.size());
        assertEquals(true, categoryList.contains(category));
        assertEquals(true, categoryList.contains(category2));
        verify(categoryRepository, times(2)).findAll();
    }

    @Test
    void findByIdTest() throws Exception {
        when(categoryRepository.findById(anyString())).thenReturn(Mono.just(category));
        assertNotNull(categoryService.findById(categoryId));
        Category categoryFromMono = categoryService.findById(categoryId).block();
        assertEquals(categoryId, categoryFromMono.getCategoryId());
        assertEquals(name, categoryFromMono.getName());
        verify(categoryRepository, times(2)).findById(categoryId);
    }

    @Test
    void saveOrUpdateTest() throws Exception {
        when(categoryRepository.save(any())).thenReturn(Mono.empty());
        assertNotNull(categoryService.saveOrUpdate(category));
        verify(categoryRepository, times(1)).save(category);
    }

    @Test
    void deleteByIdTest() throws Exception {
        when(categoryRepository.deleteById(anyString())).thenReturn(Mono.empty());
        assertNotNull(categoryService.deleteById(categoryId));
        verify(categoryRepository, times(1)).deleteById(categoryId);
    }

    @Test
    void deleteAllTest() throws Exception {
        when(categoryRepository.deleteAll()).thenReturn(Mono.empty());
        assertNotNull(categoryService.deleteAll());
        verify(categoryRepository, times(1)).deleteAll();
    }
}