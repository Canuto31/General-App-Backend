package com.canuto.general_app.finance.telegram.service;

import java.util.regex.Pattern;

import org.springframework.stereotype.Service;

import com.canuto.general_app.finance.telegram.enums.TelegramCommandType;

@Service
public class TelegramCommandResolver {

    private static final Pattern AMOUNT_PATTERN =
            Pattern.compile(".*\\d+(k)?.*");

    public TelegramCommandType resolve(String text) {

        if (text == null || text.isBlank()) {
            return TelegramCommandType.UNKNOWN;
        }

        String lowerText = text.toLowerCase().trim();

        if (lowerText.startsWith("paid ")) {
            return TelegramCommandType.PAY_RECURRING;
        }

        if (lowerText.startsWith("received ")) {

            boolean containsAmount =
                    AMOUNT_PATTERN.matcher(lowerText)
                            .matches();

            if (containsAmount) {
                return TelegramCommandType.INCOME;
            }

            return TelegramCommandType.RECURRING_INCOME;
        }

        if (lowerText.equals("summary")) {
            return TelegramCommandType.SUMMARY;
        }

        if (lowerText.equals("expenses")) {
            return TelegramCommandType.EXPENSES;
        }

        if (lowerText.equals("incomes")) {
            return TelegramCommandType.INCOMES;
        }

        if (lowerText.equals("recurring expenses")) {
            return TelegramCommandType.RECURRING_EXPENSES;
        }

        if (lowerText.equals("recurring incomes")) {
            return TelegramCommandType.RECURRING_INCOMES;
        }

        if (lowerText.equals("last expense")) {
            return TelegramCommandType.LAST_EXPENSE;
        }

        if (lowerText.equals("last income")) {
            return TelegramCommandType.LAST_INCOME;
        }

        if (lowerText.equals("delete last expense")) {
            return TelegramCommandType.DELETE_LAST_EXPENSE;
        }

        if (lowerText.equals("delete last income")) {
            return TelegramCommandType.DELETE_LAST_INCOME;
        }

        if (lowerText.equals("balance")) {
            return TelegramCommandType.BALANCE;
        }

        if (lowerText.equals("pending")) {
            return TelegramCommandType.PENDING;
        }

        if (lowerText.equals("categories")) {
            return TelegramCommandType.CATEGORIES;
        }

        if (lowerText.equals("income categories")) {
            return TelegramCommandType.INCOME_CATEGORIES;
        }

        if (lowerText.equals("month expenses")) {
            return TelegramCommandType.MONTH_EXPENSES;
        }

        if (lowerText.equals("year expenses")) {
            return TelegramCommandType.YEAR_EXPENSES;
        }

        if (lowerText.equals("help")) {
            return TelegramCommandType.HELP;
        }

        if (looksLikeExpense(lowerText)) {
            return TelegramCommandType.EXPENSE;
        }
        
        return TelegramCommandType.UNKNOWN;
    }

    private boolean looksLikeExpense(String text) {
        return text.matches("^\\d+[kKmM]?\\s+.+");
    }
}