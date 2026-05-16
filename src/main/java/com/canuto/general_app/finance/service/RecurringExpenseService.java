package com.canuto.general_app.finance.service;

import com.canuto.general_app.finance.repository.RecurringExpenseRepository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.stereotype.Service;

import com.canuto.general_app.finance.dto.CreateRecurringExpenseRequest;
import com.canuto.general_app.finance.model.RecurringExpense;

@Service
public class RecurringExpenseService {
    
    private final RecurringExpenseRepository recurringExpenseRepository;

    public RecurringExpenseService(RecurringExpenseRepository recurringExpenseRepository) {
        this.recurringExpenseRepository = recurringExpenseRepository;
    }

    public RecurringExpense create (CreateRecurringExpenseRequest request) {
        RecurringExpense recurringExpense = new RecurringExpense();
        recurringExpense.setName(request.getName());
        recurringExpense.setAmount(request.getAmount());
        recurringExpense.setDayOfMonth(request.getDayOfMonth());
        recurringExpense.setMonthOfYear(request.getMonthOfYear());
        recurringExpense.setFrequency(request.getFrequency());
        recurringExpense.setStartDate(request.getStartDate());
        recurringExpense.setEndDate(request.getEndDate());
        recurringExpense.setActive(true);
        recurringExpense.setPaidThisMonth(false);
        recurringExpense.setLastPaymentDate(null);

        return recurringExpenseRepository.save(recurringExpense);
    }

    public RecurringExpense markAsPaid(Long id) {
        RecurringExpense recurringExpense = recurringExpenseRepository.findById(id).orElseThrow(() -> new RuntimeException("Recurring expense not found"));

        recurringExpense.setPaidThisMonth(true);
        recurringExpense.setLastPaymentDate(LocalDate.now());

        return recurringExpenseRepository.save(recurringExpense);
    }

    public List<RecurringExpense> getAll() {
        return recurringExpenseRepository.findAll();
    }
}
