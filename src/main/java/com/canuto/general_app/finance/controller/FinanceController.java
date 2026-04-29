package com.canuto.general_app.finance.controller;

import com.canuto.general_app.finance.parser.TextParserService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.canuto.general_app.finance.dto.FinanceSummary;
import com.canuto.general_app.finance.model.Expense;
import com.canuto.general_app.finance.service.FinanceService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;

@RestController
@RequestMapping("/finance")
public class FinanceController {
    
    private final FinanceService financeService;
    private final TextParserService parserService;

    public FinanceController(FinanceService financeService,
                             TextParserService parserService) {
        this.financeService = financeService;
        this.parserService = parserService;
    }

    @PostMapping("/smart-expense")
    public List<Expense> addSmartExpense(@RequestBody String text) {

        List<Expense> expenses = parserService.parseMultipleExpenses(text);

        return expenses.stream().map(financeService::saveExpense).toList();
    }

    @GetMapping("/expenses")
    public List<Expense> getAll() {
        return financeService.getAllExpenses();
    }
}
