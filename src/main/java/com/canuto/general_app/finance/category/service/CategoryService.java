package com.canuto.general_app.finance.category.service;

import org.springframework.stereotype.Service;

import com.canuto.general_app.finance.category.model.Category;
import com.canuto.general_app.finance.category.repository.CategoryRepository;
import com.canuto.general_app.finance.category.utils.CategoryKeywordResolver;
import com.canuto.general_app.finance.category.utils.CategoryType;

@Service
public class CategoryService {
    
    private final CategoryRepository categoryRepository;
    private final CategoryKeywordResolver keywordResolver;

    public CategoryService(CategoryRepository categoryRepository, CategoryKeywordResolver keywordResolver) {
        this.categoryRepository = categoryRepository;
        this.keywordResolver = keywordResolver;
    }

    public Category resolveCategory(String text) {

        CategoryType type = keywordResolver.resolve(text);

        return categoryRepository.findByType(type)
                .orElseThrow(() ->
                        new RuntimeException("Category not found: " + type)
                );
    }
}
