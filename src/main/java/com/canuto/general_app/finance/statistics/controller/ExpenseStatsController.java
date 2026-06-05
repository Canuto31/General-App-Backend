package com.canuto.general_app.finance.statistics.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.canuto.general_app.finance.statistics.dto.CategoryExpenseDto;
import com.canuto.general_app.finance.statistics.service.ExpenseStatsService;

@RestController
@RequestMapping("/stats")
public class ExpenseStatsController {

    private final ExpenseStatsService expenseStatsService;

    public ExpenseStatsController(
            ExpenseStatsService expenseStatsService) {
        this.expenseStatsService = expenseStatsService;
    }

    @GetMapping("/month-expenses")
    public List<CategoryExpenseDto> getMonthExpenses() {

        return expenseStatsService
                .getCurrentMonthExpensesByCategory();
    }

    @GetMapping("/year-expenses")
    public List<CategoryExpenseDto> getYearExpenses() {

        return expenseStatsService
                .getCurrentYearExpensesByCategory();
    }
}