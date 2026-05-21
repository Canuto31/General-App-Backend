package com.canuto.general_app.finance.category.dto;

import lombok.Data;

@Data
public class CreateCategoryKeywordRequest {
    
    private String keyword;
    private Long categoryId;
}
