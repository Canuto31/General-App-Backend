package com.canuto.general_app.finance.statistics.dto;

import java.math.BigDecimal;

import lombok.Data;

@Data
public class CategoryExpenseDto {

    private String categoryName;
    private BigDecimal amount;
}