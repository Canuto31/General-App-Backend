package com.canuto.general_app.finance.category.dto;

import com.canuto.general_app.finance.category.utils.CategoryType;

import lombok.Data;

@Data
public class CreateCategoryRequest {
    
    private String name;

    private CategoryType type;
}
