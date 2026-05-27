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
import com.canuto.general_app.finance.expense.parser.TextExpenseParserService;
import com.canuto.general_app.finance.expense.service.ExpenseService;

@RestController
@RequestMapping("/expenses")
public class ExpenseController {

    private final ExpenseService expenseService;
    private final TextExpenseParserService parserService;

    public ExpenseController(ExpenseService financeService, TextExpenseParserService parserService) {
        this.expenseService = financeService;
        this.parserService = parserService;
    }

    @PostMapping("/smart")
    public List<Expense> addSmartExpense(@RequestBody String text) {

        List<Expense> expenses = parserService.parseMultipleExpenses(text);

        return expenses.stream()
                .map(expenseService::saveExpense)
                .toList();
    }

    @GetMapping
    public List<Expense> getAll() {
        return expenseService.getAllExpenses();
    }

    @GetMapping("/current-balance")
    public CurrentBalanceResponse getCurrentBalance() {
        return expenseService
                .getCurrentBalance();
    }

    @GetMapping("/month-summary")
    public MonthSummaryResponse getMonthSummary() {

        return expenseService.getMonthSummary();
    }
}
