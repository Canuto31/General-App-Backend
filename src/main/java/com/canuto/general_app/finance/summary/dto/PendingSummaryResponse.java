package com.canuto.general_app.finance.summary.dto;

import java.math.BigDecimal;
import java.util.List;

import lombok.Data;

@Data
public class PendingSummaryResponse {
    
    private BigDecimal pendingRecurringExpenses;
    private BigDecimal pendingRecurringIncomes;
    private BigDecimal netPendingBalance;
    List<PendingItemResponse> pendingExpenses;
    List<PendingItemResponse> pendingIncomes;
}
