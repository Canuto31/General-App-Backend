package com.canuto.general_app.finance.expense.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.canuto.general_app.finance.expense.dto.CurrentBalanceResponse;
import com.canuto.general_app.finance.expense.dto.MonthSummaryResponse;
import com.canuto.general_app.finance.expense.model.Expense;
import com.canuto.general_app.finance.expense.parser.TextParserService;
import com.canuto.general_app.finance.expense.service.FinanceService;

@RestController
@RequestMapping("/expenses")
public class ExpenseController {
    
    private final FinanceService financeService;
    private final TextParserService parserService;

    public ExpenseController(FinanceService financeService, TextParserService parserService) {
        this.financeService = financeService;
        this.parserService = parserService;
    }

    @PostMapping("/smart")
    public List<Expense> addSmartExpense(@RequestBody String text) {

        List<Expense> expenses = parserService.parseMultipleExpenses(text);

        return expenses.stream()
                .map(financeService::saveExpense)
                .toList();
    }

    @GetMapping
    public List<Expense> getAll() {
        return financeService.getAllExpenses();
    }

    @GetMapping("/current-balance")
    public CurrentBalanceResponse getCurrentBalance() {
        return financeService
                .getCurrentBalance();
    }

    @GetMapping("/month-summary")
    public MonthSummaryResponse getMonthSummary() {

        return financeService.getMonthSummary();
    }
}
