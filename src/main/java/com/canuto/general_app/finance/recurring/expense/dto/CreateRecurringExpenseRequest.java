package com.canuto.general_app.finance.recurring.expense.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

import com.canuto.general_app.finance.shared.enums.Frequency;

import lombok.Data;

@Data
public class CreateRecurringExpenseRequest {
    
    private String name;
    private BigDecimal amount;
    private Integer dayOfMonth;
    private Frequency frequency;
    private LocalDate startDate;
    private LocalDate endDate;
    private Integer monthOfYear;
    private Long categoryId;
}
