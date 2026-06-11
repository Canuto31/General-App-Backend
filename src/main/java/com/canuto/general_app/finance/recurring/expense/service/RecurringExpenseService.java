package com.canuto.general_app.finance.recurring.expense.service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.YearMonth;
import java.util.List;

import org.springframework.stereotype.Service;

import com.canuto.general_app.finance.category.model.Category;
import com.canuto.general_app.finance.category.repository.CategoryRepository;
import com.canuto.general_app.finance.expense.model.Expense;
import com.canuto.general_app.finance.expense.repository.ExpenseRepository;
import com.canuto.general_app.finance.recurring.expense.dto.CreateRecurringExpenseRequest;
import com.canuto.general_app.finance.recurring.expense.dto.PayRecurringExpenseRequest;
import com.canuto.general_app.finance.recurring.expense.model.RecurringExpense;
import com.canuto.general_app.finance.recurring.expense.repository.RecurringExpenseRepository;

@Service
public class RecurringExpenseService {

    private final RecurringExpenseRepository recurringExpenseRepository;
    private final CategoryRepository categoryRepository;
    private final ExpenseRepository expenseRepository;

    public RecurringExpenseService(
            RecurringExpenseRepository recurringExpenseRepository,
            CategoryRepository categoryRepository, ExpenseRepository expenseRepository) {
        this.recurringExpenseRepository = recurringExpenseRepository;
        this.categoryRepository = categoryRepository;
        this.expenseRepository = expenseRepository;
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
    
            if (!isApplicableNow(
                    recurringExpense)) {
    
                continue;
            }
    
            boolean alreadyPaid =
                    isAlreadyPaidThisPeriod(
                            recurringExpense);
    
            if (!alreadyPaid) {
    
                totalPending = totalPending.add(
                        recurringExpense.getAmount());
            }
        }
    
        return totalPending;
    }

    public Expense payRecurringExpense(PayRecurringExpenseRequest request) {

        RecurringExpense recurringExpense = recurringExpenseRepository
                .findByNameIgnoreCase(request.getRecurringName())
                .orElseThrow(() -> new RuntimeException("Recurring expense not found"));

        if (isAlreadyPaidThisPeriod(recurringExpense)) {
            throw new RuntimeException("Recurring expense already paid this period");
        }

        Expense expense = new Expense();

        expense.setAmount(request.getAmount());
        expense.setDate(LocalDate.now());
        expense.setNote("Recurring payment: " + recurringExpense.getName());
        expense.setCategory(recurringExpense.getCategory());

        Expense savedExpense = expenseRepository.save(expense);

        recurringExpense.setLastPaymentDate(LocalDate.now());

        recurringExpenseRepository.save(recurringExpense);

        return savedExpense;
    }

    public Expense payRecurringExpense(String recurringName) {

        RecurringExpense recurringExpense = recurringExpenseRepository
                .findByNameIgnoreCase(recurringName)
                .orElseThrow(() ->
                        new RuntimeException("Recurring expense not found"));
    
        PayRecurringExpenseRequest request =
                new PayRecurringExpenseRequest();
    
        request.setRecurringName(recurringName);
        request.setAmount(recurringExpense.getAmount());
    
        return payRecurringExpense(request);
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

    public List<RecurringExpense>
        getPendingRecurringExpenseList() {

    return recurringExpenseRepository.findAll()
            .stream()
            .filter(this::isApplicableNow)
            .filter(expense ->
                    !isAlreadyPaidThisPeriod(
                            expense))
            .sorted((a, b) ->
                    Integer.compare(
                            a.getDayOfMonth(),
                            b.getDayOfMonth()))
            .toList();
}

    private boolean isApplicableNow(
            RecurringExpense recurringExpense) {

        LocalDate today = LocalDate.now();

        if (!Boolean.TRUE.equals(
                recurringExpense.getActive())) {
            return false;
        }

        if (recurringExpense.getStartDate() != null
                && today.isBefore(
                        recurringExpense.getStartDate())) {

            return false;
        }

        if (recurringExpense.getEndDate() != null
                && today.isAfter(
                        recurringExpense.getEndDate())) {

            return false;
        }

        return true;
    }

    public List<RecurringExpense> getActiveRecurringExpenses() {
        return recurringExpenseRepository.findByActiveTrue();
    }
}