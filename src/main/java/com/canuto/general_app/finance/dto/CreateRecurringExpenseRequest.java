package com.canuto.general_app.finance.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

import com.canuto.general_app.finance.enums.Frequency;

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
}
