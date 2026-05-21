package com.canuto.general_app.finance.category.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.canuto.general_app.finance.category.dto.CreateCategoryKeywordRequest;
import com.canuto.general_app.finance.category.model.Category;
import com.canuto.general_app.finance.category.model.CategoryKeyword;
import com.canuto.general_app.finance.category.repository.CategoryKeywordRepository;
import com.canuto.general_app.finance.category.repository.CategoryRepository;

@Service
public class CategoryKeywordService {

     private final CategoryKeywordRepository categoryKeywordRepository;
     private final CategoryRepository categoryRepository;

     public CategoryKeywordService(CategoryKeywordRepository categoryKeywordRepository, CategoryRepository categoryRepository) {
        this.categoryKeywordRepository = categoryKeywordRepository;
        this.categoryRepository = categoryRepository;
     }

     public CategoryKeyword create(CreateCategoryKeywordRequest request) {
        Category category = categoryRepository.findById(request.getCategoryId()).orElseThrow(() -> new RuntimeException("Category not found"));

        CategoryKeyword keyword = new CategoryKeyword();

        keyword.setKeyword(request.getKeyword());
        keyword.setCategory(category);

        return categoryKeywordRepository.save(keyword);
     }

     public List<CategoryKeyword> getAll() {
        return categoryKeywordRepository.findAll();
     }
}
