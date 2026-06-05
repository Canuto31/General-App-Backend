package com.canuto.general_app.finance.summary.service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.canuto.general_app.finance.expense.repository.ExpenseRepository;
import com.canuto.general_app.finance.income.repository.IncomeRepository;
import com.canuto.general_app.finance.summary.dto.CategorySummaryResponse;

@Service
public class CategorySummaryService {
    
    private final ExpenseRepository expenseRepository;
    private final IncomeRepository incomeRepository;

    public CategorySummaryService(ExpenseRepository expenseRepository, IncomeRepository incomeRepository) {
        this.expenseRepository = expenseRepository;
        this.incomeRepository = incomeRepository;
    }

    public List<CategorySummaryResponse> getExpenseCategories() {
        List<Object[]> results = expenseRepository.getExpensesGroupedByCategory();

        List<CategorySummaryResponse> response = new ArrayList<>();

        for (Object[] row : results) {
            CategorySummaryResponse item = new CategorySummaryResponse();

            item.setCategoryName((String) row[0]);
            item.setTotalAmount((BigDecimal) row[1]);

            response.add(item);
        }

        return response;
    }

    public List<CategorySummaryResponse> getIncomeCategories() {
        List<Object[]> results = incomeRepository.getIncomeGroupedByCategory();

        List<CategorySummaryResponse> response = new ArrayList<>();

        for (Object[] row : results) {
            CategorySummaryResponse item = new CategorySummaryResponse();

            item.setCategoryName((String) row[0]);
            item.setTotalAmount((BigDecimal) row[1]);

            response.add(item);
        }

        return response;
    }
}
