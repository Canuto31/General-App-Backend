package com.canuto.general_app.finance.recurring.income.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

import com.canuto.general_app.finance.shared.enums.Frequency;

import lombok.Data;

@Data
public class CreateRecurringIncomeRequest {
    
    private String name;

    private BigDecimal amount;

    private Long categoryId;

    private Integer dayOfMonth;

    private Frequency frequency;

    private LocalDate startDate;

    private LocalDate endDate;
}
