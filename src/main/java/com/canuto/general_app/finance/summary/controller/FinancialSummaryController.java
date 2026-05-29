package com.canuto.general_app.finance.summary.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.canuto.general_app.finance.summary.dto.CurrentBalanceResponse;
import com.canuto.general_app.finance.summary.dto.MonthSummaryResponse;
import com.canuto.general_app.finance.summary.dto.PendingSummaryResponse;
import com.canuto.general_app.finance.summary.service.FinancialSummarySerivice;

@RestController
@RequestMapping("/summary")
public class FinancialSummaryController {

    private final FinancialSummarySerivice service;

    public FinancialSummaryController(FinancialSummarySerivice service) {
        this.service = service;
    }

    @GetMapping("/balance")
    public CurrentBalanceResponse getBalance() {

        return service
                .getCurrentBalance();
    }

    @GetMapping("/month")
    public MonthSummaryResponse getMonthSummary() {

        return service
                .getMonthSummary();
    }

    @GetMapping("/pending")
    public PendingSummaryResponse getPendingSummary() {

        return service
                .getPendingSummary();
    }
}
