package com.canuto.general_app.finance.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.canuto.general_app.finance.model.Expense;
import com.canuto.general_app.finance.repository.ExpenseRepository;

@Service
public class FinanceService {
    
    private final ExpenseRepository expenseRepository;

    public FinanceService(ExpenseRepository expenseRepository) {
        this.expenseRepository = expenseRepository;
    }

    public Expense saveExpense(Expense expense) {
        return expenseRepository.save(expense);
    }

    public List<Expense> getAllExpenses() {
        return expenseRepository.findAll();
    }
}
