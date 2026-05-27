package com.canuto.general_app.finance.expense.service;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.stereotype.Service;

import com.canuto.general_app.finance.expense.dto.CurrentBalanceResponse;
import com.canuto.general_app.finance.expense.dto.MonthSummaryResponse;
import com.canuto.general_app.finance.expense.model.Expense;
import com.canuto.general_app.finance.expense.repository.ExpenseRepository;
import com.canuto.general_app.finance.income.repository.IncomeRepository;
import com.canuto.general_app.finance.recurring.expense.service.RecurringExpenseService;
import com.canuto.general_app.finance.recurring.income.service.RecurringIncomeService;

@Service
public class FinanceService {

    private final ExpenseRepository expenseRepository;
    private final IncomeRepository incomeRepository;
    private final RecurringExpenseService recurringExpenseService;
    private final RecurringIncomeService recurringIncomeService;

    public FinanceService(ExpenseRepository expenseRepository, IncomeRepository incomeRepository,
            RecurringExpenseService recurringExpenseService, RecurringIncomeService recurringIncomeService) {
        this.expenseRepository = expenseRepository;
        this.incomeRepository = incomeRepository;
        this.recurringExpenseService = recurringExpenseService;
        this.recurringIncomeService = recurringIncomeService;
    }

    public Expense saveExpense(Expense expense) {
        return expenseRepository.save(expense);
    }

    public List<Expense> getAllExpenses() {
        return expenseRepository.findAll();
    }

    public CurrentBalanceResponse getCurrentBalance() {

        BigDecimal totalIncome = incomeRepository.getTotalIncome();

        BigDecimal totalExpenses = expenseRepository.getTotalExpenses();

        BigDecimal balance = totalIncome.subtract(totalExpenses);

        CurrentBalanceResponse response = new CurrentBalanceResponse();

        response.setTotalIncome(totalIncome);
        response.setTotalExpenses(totalExpenses);
        response.setCurrentBalance(balance);

        return response;
    }

    public MonthSummaryResponse getMonthSummary() {

        CurrentBalanceResponse currentBalance = getCurrentBalance();

        BigDecimal pendingExpenses = recurringExpenseService
                .getPendingRecurringExpenses();

        BigDecimal pendingIncome = recurringIncomeService
                .getPendingRecurringIncome();

        BigDecimal projectedEndMonthBalance = currentBalance.getCurrentBalance()
                .add(pendingIncome)
                .subtract(pendingExpenses);

        MonthSummaryResponse response = new MonthSummaryResponse();

        response.setCurrentBalance(
                currentBalance.getCurrentBalance());

        response.setPendingRecurringExpenses(
                pendingExpenses);

        response.setPendingRecurringIncome(
                pendingIncome);

        response.setProjectedEndMonthBalance(
                projectedEndMonthBalance);

        return response;
    }
}
