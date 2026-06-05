package com.canuto.general_app.finance.expense.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.canuto.general_app.finance.expense.model.Expense;
import com.canuto.general_app.finance.expense.repository.ExpenseRepository;

@Service
public class ExpenseService {

    private final ExpenseRepository expenseRepository;

    public ExpenseService(ExpenseRepository expenseRepository) {
        this.expenseRepository = expenseRepository;
    }

    public Expense saveExpense(Expense expense) {
        return expenseRepository.save(expense);
    }

    public List<Expense> getAllExpenses() {
        return expenseRepository.findAll();
    }

    public List<Expense> getLastExpenses() {
        return expenseRepository.findTop10ByOrderByDateDesc();
    }

    public Expense getLastExpense() {

        return expenseRepository
                .findTopByOrderByDateDesc()
                .orElseThrow(() -> new RuntimeException("No expenses found"));
    }

    public Expense deleteLastExpense() {

        Expense expense = expenseRepository
                .findTopByOrderByDateDesc()
                .orElseThrow(() -> new RuntimeException("No expenses found"));

        expenseRepository.delete(expense);

        return expense;
    }
}
