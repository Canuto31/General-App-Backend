package com.canuto.general_app.finance.recurring.expense.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.canuto.general_app.finance.expense.model.Expense;
import com.canuto.general_app.finance.recurring.expense.dto.CreateRecurringExpenseRequest;
import com.canuto.general_app.finance.recurring.expense.model.RecurringExpense;
import com.canuto.general_app.finance.recurring.expense.service.RecurringExpenseService;

@RestController
@RequestMapping("/recurring-expenses")
public class RecurringExpenseController {

    private final RecurringExpenseService recurringExpenseService;

    public RecurringExpenseController(
            RecurringExpenseService recurringExpenseService) {
        this.recurringExpenseService = recurringExpenseService;
    }

    @PostMapping
    public RecurringExpense createRecurringExpense(
            @RequestBody CreateRecurringExpenseRequest request) {
        return recurringExpenseService.create(request);
    }

    @GetMapping
    public List<RecurringExpense> getRecurringExpenses() {
        return recurringExpenseService.getAll();
    }

    @PostMapping("/{name}/pay")
    public Expense payRecurringExpense(@PathVariable String name) {
        return recurringExpenseService.payRecurringExpense(name);
    }

}
