package com.canuto.general_app.finance.dto;

import java.util.Map;

import lombok.Data;

@Data
public class FinanceSummary {

    private Double total;
    private Map<String, Double> byCategory;
    
}
