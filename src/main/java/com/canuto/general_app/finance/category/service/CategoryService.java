package com.canuto.general_app.finance.category.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.canuto.general_app.finance.category.dto.CreateCategoryRequest;
import com.canuto.general_app.finance.category.enums.CategoryType;
import com.canuto.general_app.finance.category.model.Category;
import com.canuto.general_app.finance.category.model.CategoryKeyword;
import com.canuto.general_app.finance.category.repository.CategoryKeywordRepository;
import com.canuto.general_app.finance.category.repository.CategoryRepository;

@Service
public class CategoryService {
    
    private final CategoryRepository categoryRepository;
    private final CategoryKeywordRepository categoryKeywordRepository;

    public CategoryService(CategoryRepository categoryRepository, CategoryKeywordRepository categoryKeywordRepository) {
        this.categoryRepository = categoryRepository;
        this.categoryKeywordRepository = categoryKeywordRepository;
    }

    public Category create (CreateCategoryRequest request) {
        Category category = new Category();

        category.setName(request.getName());
        category.setType(request.getType());

        return categoryRepository.save(category);
    }

    public List<Category> getAll() {
        return categoryRepository.findAll();
    }

    public Category resolveCategory(String text) {

        String lowerText = text.toLowerCase();

        List<CategoryKeyword> keywords = categoryKeywordRepository.findAll();

        for (CategoryKeyword keyword : keywords) {
            if (lowerText.contains(keyword.getKeyword().toLowerCase())) {
                return keyword.getCategory();
            }
        }

        return categoryRepository.findByType(CategoryType.OTHER).orElseThrow(() -> new RuntimeException("Category OTHER not found"));
    }
}
