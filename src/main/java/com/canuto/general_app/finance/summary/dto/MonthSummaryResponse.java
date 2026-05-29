package com.canuto.general_app.finance.summary.dto;

import java.math.BigDecimal;

import lombok.Data;

@Data
public class MonthSummaryResponse {
    
    private BigDecimal currentBalance;

    private BigDecimal projectedEndMonthBalance;

    private BigDecimal pendingRecurringExpenses;

    private BigDecimal pendingRecurringIncome;
}
