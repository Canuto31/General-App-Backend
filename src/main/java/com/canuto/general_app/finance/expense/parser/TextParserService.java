package com.canuto.general_app.finance.expense.parser;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.stereotype.Service;

import com.canuto.general_app.finance.category.model.Category;
import com.canuto.general_app.finance.category.service.CategoryService;
import com.canuto.general_app.finance.expense.model.Expense;

@Service
public class TextParserService {
    
    private final CategoryService categoryService;

    public TextParserService(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    public Expense parseExpense(String text) {

        Pattern pattern = Pattern.compile("(\\d+)(k)?");
        Matcher matcher = pattern.matcher(text);

        BigDecimal amount = BigDecimal.ZERO;

        if (matcher.find()) {
            amount = new BigDecimal(matcher.group(1));

            if (matcher.group(2) != null) {
                amount = amount.multiply(new BigDecimal(1000));
            }
        }

        Category category = categoryService.resolveCategory(text);

        Expense expense = new Expense();
        expense.setAmount(amount);
        expense.setCategory(category);
        expense.setDate(LocalDate.now());
        expense.setNote(text);

        return expense;
    }

    public List<Expense> parseMultipleExpenses(String text) {
        List<Expense> expenses = new ArrayList<>();

        String[] parts = text.split(",|\\s+y\\s+|\\s+and\\s+|\\s*&\\s*");

        for (String part : parts) {
            Expense expense = parseExpense(part.trim());
            expenses.add(expense);
        }

        return expenses;
    }
}
