package com.canuto.general_app.finance.expense.dto;

import java.math.BigDecimal;

import lombok.Data;

@Data
public class CurrentBalanceResponse {
    
    private BigDecimal totalIncome;

    private BigDecimal totalExpenses;

    private BigDecimal currentBalance;
}
