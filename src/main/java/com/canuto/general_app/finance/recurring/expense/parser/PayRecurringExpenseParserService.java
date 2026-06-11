package com.canuto.general_app.finance.recurring.expense.parser;

import java.math.BigDecimal;

import org.springframework.stereotype.Service;

import com.canuto.general_app.finance.recurring.expense.dto.PayRecurringExpenseRequest;

@Service
public class PayRecurringExpenseParserService {
    
    public PayRecurringExpenseRequest parse(String text) {

        String content = text
                .replaceFirst("(?i)paid", "")
                .trim();

        String[] parts = content.split("\\s+", 2);

        PayRecurringExpenseRequest request =
                new PayRecurringExpenseRequest();

        request.setAmount(new BigDecimal(parts[0]));
        request.setRecurringName(parts[1]);

        return request;
    }
}
