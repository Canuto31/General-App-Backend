package com.canuto.general_app.finance.recurring.expense.dto;

import java.math.BigDecimal;

import lombok.Data;

@Data
public class PayRecurringExpenseRequest {
    
    private BigDecimal amount;
    private String recurringName;
}
