package com.canuto.general_app.finance.incomeCategory.dto;

import lombok.Data;

@Data
public class CreateIncomeCategoryKeywordRequest {
    
    private String keyword;
    private Long incomeCategoryId;
}
