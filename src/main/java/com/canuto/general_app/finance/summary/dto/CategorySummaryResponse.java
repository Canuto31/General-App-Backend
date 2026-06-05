package com.canuto.general_app.finance.summary.dto;

import java.math.BigDecimal;

import lombok.Data;

@Data
public class CategorySummaryResponse {
    private String categoryName;
    private BigDecimal totalAmount;
}