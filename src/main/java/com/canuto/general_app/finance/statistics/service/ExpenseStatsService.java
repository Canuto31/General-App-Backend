package com.canuto.general_app.finance.statistics.service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.canuto.general_app.finance.expense.repository.ExpenseRepository;
import com.canuto.general_app.finance.statistics.dto.CategoryExpenseDto;

@Service
public class ExpenseStatsService {

    private final ExpenseRepository expenseRepository;

    public ExpenseStatsService(
            ExpenseRepository expenseRepository) {
        this.expenseRepository = expenseRepository;
    }

    public List<CategoryExpenseDto> getCurrentMonthExpensesByCategory() {

        LocalDate now = LocalDate.now();

        List<Object[]> result = expenseRepository.getExpensesByCategoryForMonth(
                now.getYear(),
                now.getMonthValue());

        List<CategoryExpenseDto> response = new ArrayList<>();

        for (Object[] row : result) {

            CategoryExpenseDto dto = new CategoryExpenseDto();

            dto.setCategoryName(
                    (String) row[0]);

            dto.setAmount(
                    (BigDecimal) row[1]);

            response.add(dto);
        }

        return response;
    }

    public List<CategoryExpenseDto> getCurrentYearExpensesByCategory() {

        int year = LocalDate.now().getYear();

        List<Object[]> result = expenseRepository
                .getExpensesByCategoryForYear(year);

        List<CategoryExpenseDto> response = new ArrayList<>();

        for (Object[] row : result) {

            CategoryExpenseDto dto = new CategoryExpenseDto();

            dto.setCategoryName((String) row[0]);

            dto.setAmount((BigDecimal) row[1]);

            response.add(dto);
        }

        return response;
    }
}
