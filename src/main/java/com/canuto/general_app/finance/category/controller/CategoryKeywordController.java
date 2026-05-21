package com.canuto.general_app.finance.category.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.canuto.general_app.finance.category.dto.CreateCategoryKeywordRequest;
import com.canuto.general_app.finance.category.model.CategoryKeyword;
import com.canuto.general_app.finance.category.service.CategoryKeywordService;

@RestController
@RequestMapping("/category-keywords")
public class CategoryKeywordController {

    private final CategoryKeywordService categoryKeywordService;

    public CategoryKeywordController(CategoryKeywordService categoryKeywordService) {
        this.categoryKeywordService = categoryKeywordService;
    }

    @PostMapping
    public CategoryKeyword create(@RequestBody CreateCategoryKeywordRequest request) {
        return categoryKeywordService.create(request);
    }

    @GetMapping
    public List<CategoryKeyword> getAll() {
        return categoryKeywordService.getAll();
    }
}
