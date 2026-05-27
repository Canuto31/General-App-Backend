package com.canuto.general_app.finance.income.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

import lombok.Data;

@Data
public class CreateIncomeRequest {
    
    private BigDecimal amount;

    private String note;

    private LocalDate date;

    private Long categoryId;
}
