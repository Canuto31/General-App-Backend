package com.canuto.general_app.finance.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

    public FinanceController(FinanceService financeService) {
        this.financeService = financeService;
    }

    @PostMapping("/expense")
    public String addExpense(@RequestBody Expense expense) {
        financeService.addExpense(expense);
        return "Expense saved successfully";
    }

    @GetMapping("/expenses")
    public List<Expense> getExpenses() {
        return financeService.getExpenses();
    }
    
    
}
