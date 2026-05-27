package com.canuto.general_app.finance.telegram.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.canuto.general_app.finance.expense.model.Expense;
import com.canuto.general_app.finance.expense.parser.TextParserService;
import com.canuto.general_app.finance.expense.service.FinanceService;
import com.canuto.general_app.finance.recurring.expense.service.RecurringExpenseService;
import com.canuto.general_app.finance.recurring.income.service.RecurringIncomeService;
import com.canuto.general_app.finance.telegram.enums.TelegramCommandType;

@Service
public class TelegramCommandService {

    private final TelegramCommandResolver commandResolver;
    private final TextParserService parserService;
    private final FinanceService financeService;
    private final RecurringExpenseService recurringExpenseService;
    private final RecurringIncomeService recurringIncomeService;

    public TelegramCommandService(TelegramCommandResolver commandResolver, TextParserService parserService,
            FinanceService financeService, RecurringExpenseService recurringExpenseService,
            RecurringIncomeService recurringIncomeService) {
        this.commandResolver = commandResolver;
        this.parserService = parserService;
        this.financeService = financeService;
        this.recurringExpenseService = recurringExpenseService;
        this.recurringIncomeService = recurringIncomeService;
    }

    public String process(String text) {
        TelegramCommandType commandType = commandResolver.resolve(text);

        return switch (commandType) {
            case EXPENSE -> handleExpense(text);

            case PAY_RECURRING -> handlePayRecurring(text);

            case RECEIVE_INCOME -> handleReceiveIncome(text);

            case SUMMARY -> "Summary command not implemented yet.";

            case BALANCE -> "Balance command not implemented yet.";

            case PENDING -> "Pending command not implemented yet.";

            case HELP -> getHelpMessage();

            case UNKNOWN -> "Unknown command.";
        };
    }

    private String handleExpense(String text) {

        List<Expense> expenses = parserService.parseMultipleExpenses(text);

        for (Expense expense : expenses) {
            financeService.saveExpense(expense);
        }

        return "Saved " + expenses.size() + " expense(s).";
    }

    private String handlePayRecurring(String text) {

        String recurringName = text
                .replaceFirst("paid ", "")
                .trim();

        recurringExpenseService.payRecurringExpense(recurringName);

        return "Recurring expense paid: " + recurringName;
    }

    private String handleReceiveIncome(String text) {

        String incomeName = text
                .replaceFirst("received ", "")
                .trim();

        recurringIncomeService.receiveRecurringIncome(incomeName);

        return "Recurring income received: " + incomeName;
    }

    private String getHelpMessage() {

        return """
                Available commands:

                30k burger
                paid rent
                received salary
                summary
                balance
                pending
                help
                """;
    }
}
