package com.canuto.general_app.finance.service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.YearMonth;
import java.util.List;

import org.springframework.stereotype.Service;

import com.canuto.general_app.finance.enums.Frequency;
import com.canuto.general_app.finance.model.Expense;
import com.canuto.general_app.finance.model.MonthlyStatus;
import com.canuto.general_app.finance.model.RecurringExpense;
import com.canuto.general_app.finance.repository.ExpenseRepository;
import com.canuto.general_app.finance.repository.MonthlyStatusRepository;
import com.canuto.general_app.finance.repository.RecurringExpenseRepository;

@Service
public class FinancialProjectionService {

    private final ExpenseRepository expenseRepository;
    private final RecurringExpenseRepository recurringExpenseRepository;
    private final MonthlyStatusRepository monthlyStatusRepository;

    public FinancialProjectionService(ExpenseRepository expenseRepository, RecurringExpenseRepository recurringExpenseRepository, MonthlyStatusRepository monthlyStatusRepository) {
        this.expenseRepository = expenseRepository;
        this.recurringExpenseRepository = recurringExpenseRepository;
        this.monthlyStatusRepository = monthlyStatusRepository;
    }

    public void recalculateMonth(Integer month, Integer year, BigDecimal income) {
        YearMonth yearMonth = YearMonth.of(year, month);

        LocalDate start = yearMonth.atDay(1);
        LocalDate end = yearMonth.atEndOfMonth();

        List<Expense> expenses = expenseRepository.findByDateBetween(start, end);

        List<RecurringExpense> recurringExpenses = recurringExpenseRepository.findAll();

        BigDecimal totalExpenses = BigDecimal.ZERO;

        // Gastos reales
        for (Expense expense : expenses) {
            totalExpenses = totalExpenses.add(expense.getAmount());
        }

        // Gastos recurrentes
        for (RecurringExpense recurring : recurringExpenses) {
            if (shouldApplyToMonth(recurring, month, year)) {
                totalExpenses = totalExpenses.add(recurring.getAmount());
            }
        }

        BigDecimal balance = income.subtract(totalExpenses);

        boolean deficit = balance.compareTo(BigDecimal.ZERO) < 0;

        MonthlyStatus monthlyStatus = monthlyStatusRepository.findByMonthAndYear(month, year).orElse(new MonthlyStatus());

        monthlyStatus.setMonth(month);
        monthlyStatus.setYear(year);
        monthlyStatus.setIncome(income);
        monthlyStatus.setExpenses(totalExpenses);
        monthlyStatus.setBalance(balance);
        monthlyStatus.setDeficit(deficit);
        
        monthlyStatusRepository.save(monthlyStatus);
    }

    private boolean shouldApplyToMonth(RecurringExpense recurring, Integer month, Integer year) {
        if (!Boolean.TRUE.equals(recurring.getActive())) {
            return false;
        }

        LocalDate targetDate = LocalDate.of(year, month, 1);

        // Validar fecha inicio
        if (recurring.getStartDate() != null && targetDate.isBefore(recurring.getStartDate())) {
            return false;
        }

        // Validar fecha fin
        if (recurring.getEndDate() != null && targetDate.isAfter(recurring.getEndDate())) {
            return false;
        }

        // Monthly
        if (recurring.getFrequency() == Frequency.MONTHLY) {
            return true;
        }

        // Yearly
        if (recurring.getFrequency() == Frequency.YEARLY) {
            return recurring.getMonthOfYear() != null && recurring.getMonthOfYear().equals(month);
        }

        return false;
    }
}
