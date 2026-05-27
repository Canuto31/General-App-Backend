package com.canuto.general_app.finance.projection.controller;

import java.math.BigDecimal;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.canuto.general_app.finance.projection.service.FinancialProjectionService;

@RestController
@RequestMapping("/projection")
public class ProjectionController {
    
    private final FinancialProjectionService financialProjectionService;

    public ProjectionController(FinancialProjectionService financialProjectionService) {
        this.financialProjectionService = financialProjectionService;
    }

    @PostMapping("/recalculate")
    public String recalculate(@RequestParam Integer month, @RequestParam Integer year, @RequestParam BigDecimal income) {
        financialProjectionService.recalculateMonth(month, year, income);
        return "Projection recalculated";
    }
}
