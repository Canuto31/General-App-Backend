package com.canuto.general_app.finance.controller;

import com.canuto.general_app.finance.parser.TextParserService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.canuto.general_app.finance.dto.CreateRecurringExpenseRequest;
import com.canuto.general_app.finance.dto.CurrentBalanceResponse;
import com.canuto.general_app.finance.dto.MonthSummaryResponse;
import com.canuto.general_app.finance.model.Expense;
import com.canuto.general_app.finance.model.RecurringExpense;
import com.canuto.general_app.finance.service.FinanceService;
import com.canuto.general_app.finance.service.RecurringExpenseService;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@RestController
@RequestMapping("/finance")
public class FinanceController {

    private final FinanceService financeService;
    private final TextParserService parserService;
    private final RecurringExpenseService recurringExpenseService;

    public FinanceController(
            FinanceService financeService,
            TextParserService parserService,
            RecurringExpenseService recurringExpenseService) {
        this.financeService = financeService;
        this.parserService = parserService;
        this.recurringExpenseService = recurringExpenseService;
    }

    @PostMapping("/smart-expense")
    public List<Expense> addSmartExpense(@RequestBody String text) {

        List<Expense> expenses = parserService.parseMultipleExpenses(text);

        return expenses.stream()
                .map(financeService::saveExpense)
                .toList();
    }

    @GetMapping("/expenses")
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

    @PostMapping("/recurring-expenses")
    public RecurringExpense createRecurringExpense(
            @RequestBody CreateRecurringExpenseRequest request) {
        return recurringExpenseService.create(request);
    }

    @GetMapping("/recurring-expenses")
    public List<RecurringExpense> getRecurringExpenses() {
        return recurringExpenseService.getAll();
    }

    @PostMapping("/recurring-expenses/{name}/pay")
    public Expense payRecurringExpense(@PathVariable String name) {
        return recurringExpenseService.payRecurringExpense(name);
    }
}