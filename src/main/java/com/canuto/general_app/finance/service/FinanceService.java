package com.canuto.general_app.finance.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.canuto.general_app.finance.model.Expense;

@Service
public class FinanceService {
    
    private final List<Expense> expenses = new ArrayList<>();

    public void addExpense(Expense expense) {
        expenses.add(expense);
    }

    public List<Expense> getExpenses() {
        return expenses;
    }
}
