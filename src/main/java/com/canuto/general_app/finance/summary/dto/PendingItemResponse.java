package com.canuto.general_app.finance.summary.dto;

import java.math.BigDecimal;

import lombok.Data;

@Data
public class PendingItemResponse {
    
    private String name;
    private BigDecimal amount;
    private Integer dayOfMonth;
}
