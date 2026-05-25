package com.canuto.general_app.finance.service;

import com.canuto.general_app.finance.repository.RecurringExpenseRepository;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.YearMonth;
import java.util.List;

import org.springframework.stereotype.Service;

import com.canuto.general_app.finance.category.model.Category;
import com.canuto.general_app.finance.category.repository.CategoryRepository;
import com.canuto.general_app.finance.dto.CreateRecurringExpenseRequest;
import com.canuto.general_app.finance.model.Expense;
import com.canuto.general_app.finance.model.RecurringExpense;

@Service
public class RecurringExpenseService {

    private final RecurringExpenseRepository recurringExpenseRepository;
    private final FinanceService financeService;
    private final CategoryRepository categoryRepository;

    public RecurringExpenseService(
            RecurringExpenseRepository recurringExpenseRepository,
            FinanceService financeService,
            CategoryRepository categoryRepository
    ) {
        this.recurringExpenseRepository = recurringExpenseRepository;
        this.financeService = financeService;
        this.categoryRepository = categoryRepository;
    }

    public RecurringExpense create(CreateRecurringExpenseRequest request) {

        Category category = categoryRepository.findById(request.getCategoryId())
                .orElseThrow(() -> new RuntimeException("Category not found"));

        RecurringExpense recurringExpense = new RecurringExpense();

        recurringExpense.setName(request.getName());
        recurringExpense.setAmount(request.getAmount());
        recurringExpense.setDayOfMonth(request.getDayOfMonth());
        recurringExpense.setFrequency(request.getFrequency());
        recurringExpense.setStartDate(request.getStartDate());
        recurringExpense.setEndDate(request.getEndDate());
        recurringExpense.setMonthOfYear(request.getMonthOfYear());
        recurringExpense.setCategory(category);
        recurringExpense.setActive(true);

        return recurringExpenseRepository.save(recurringExpense);
    }

    public List<RecurringExpense> getAll() {
        return recurringExpenseRepository.findAll();
    }

    public BigDecimal getPendingRecurringExpenses() {

        List<RecurringExpense> recurringExpenses =
                recurringExpenseRepository.findAll();

        BigDecimal totalPending =
                BigDecimal.ZERO;

        for (RecurringExpense recurringExpense
                : recurringExpenses) {

            if (!Boolean.TRUE.equals(
                    recurringExpense.getActive()
            )) {
                continue;
            }

            boolean alreadyPaid =
                    isAlreadyPaidThisPeriod(
                            recurringExpense
                    );

            if (!alreadyPaid) {

                totalPending = totalPending.add(
                        recurringExpense.getAmount()
                );
            }
        }

        return totalPending;
    }

    public Expense payRecurringExpense(String recurringName) {

        RecurringExpense recurringExpense = recurringExpenseRepository
                .findByNameIgnoreCase(recurringName)
                .orElseThrow(() -> new RuntimeException("Recurring expense not found"));

        if (isAlreadyPaidThisPeriod(recurringExpense)) {
            throw new RuntimeException("Recurring expense already paid this period");
        }

        Expense expense = new Expense();

        expense.setAmount(recurringExpense.getAmount());
        expense.setDate(LocalDate.now());
        expense.setNote("Recurring payment: " + recurringExpense.getName());
        expense.setCategory(recurringExpense.getCategory());

        Expense savedExpense = financeService.saveExpense(expense);

        recurringExpense.setLastPaymentDate(LocalDate.now());

        recurringExpenseRepository.save(recurringExpense);

        return savedExpense;
    }

    private boolean isAlreadyPaidThisPeriod(RecurringExpense recurringExpense) {

        if (recurringExpense.getLastPaymentDate() == null) {
            return false;
        }

        LocalDate now = LocalDate.now();
        LocalDate lastPayment = recurringExpense.getLastPaymentDate();

        return switch (recurringExpense.getFrequency()) {

            case MONTHLY ->
                YearMonth.from(lastPayment).equals(YearMonth.from(now));

            case YEARLY ->
                lastPayment.getYear() == now.getYear();

            case WEEKLY ->
                lastPayment.plusDays(7).isAfter(now);
        };
    }
}