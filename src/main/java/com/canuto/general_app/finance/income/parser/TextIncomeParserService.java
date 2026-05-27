package com.canuto.general_app.finance.income.parser;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.stereotype.Service;

import com.canuto.general_app.finance.income.model.Income;
import com.canuto.general_app.finance.incomeCategory.model.IncomeCategory;
import com.canuto.general_app.finance.incomeCategory.service.IncomeCategoryService;

@Service
public class TextIncomeParserService {
    
    private final IncomeCategoryService incomeCategoryService;

    public TextIncomeParserService(IncomeCategoryService incomeCategoryService) {
        this.incomeCategoryService = incomeCategoryService;
    }

    public Income parseIncome(String text) {

        Pattern pattern = Pattern.compile("(\\d+)(k)?");
        Matcher matcher = pattern.matcher(text);

        BigDecimal amount = BigDecimal.ZERO;

        if (matcher.find()) {
            amount = new BigDecimal(matcher.group(1));

            if (matcher.group(2) != null) {
                amount = amount.multiply(new BigDecimal(1000));
            }
        }

        IncomeCategory incomeCategory = incomeCategoryService.resolveIncomeCategory(text);

        Income income = new Income();
        income.setAmount(amount);
        income.setCategory(incomeCategory);
        income.setDate(LocalDate.now());
        income.setNote(text);

        return income;
    }

    public List<Income> parseMultipleIncomes(String text) {
        List<Income> incomes = new ArrayList<>();

        String[] parts = text.split(",|\\s+y\\s+|\\s+and\\s+|\\s*&\\s*");

        for (String part : parts) {
            Income income = parseIncome(part.trim());
            incomes.add(income);
        }

        return incomes;
    }
}
