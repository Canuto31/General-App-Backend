package com.canuto.general_app.finance.incomeCategory.dto;

import com.canuto.general_app.finance.incomeCategory.enums.IncomeCategoryType;

import lombok.Data;

@Data
public class CreateIncomeCategoryRequest {
    private String name;
    private IncomeCategoryType type;
}
