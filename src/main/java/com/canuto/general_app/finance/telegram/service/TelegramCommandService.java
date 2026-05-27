package com.canuto.general_app.finance.telegram.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.canuto.general_app.finance.expense.model.Expense;
import com.canuto.general_app.finance.expense.parser.TextExpenseParserService;
import com.canuto.general_app.finance.expense.service.ExpenseService;
import com.canuto.general_app.finance.income.model.Income;
import com.canuto.general_app.finance.income.parser.TextIncomeParserService;
import com.canuto.general_app.finance.income.service.IncomeService;
import com.canuto.general_app.finance.recurring.expense.service.RecurringExpenseService;
import com.canuto.general_app.finance.recurring.income.service.RecurringIncomeService;
import com.canuto.general_app.finance.telegram.enums.TelegramCommandType;

@Service
public class TelegramCommandService {

    private final TelegramCommandResolver commandResolver;
    private final TextExpenseParserService expenseParserService;
    private final TextIncomeParserService incomeParserService;
    private final ExpenseService expenseService;
    private final IncomeService incomeService;
    private final RecurringExpenseService recurringExpenseService;
    private final RecurringIncomeService recurringIncomeService;

    public TelegramCommandService(TelegramCommandResolver commandResolver,
            TextExpenseParserService expenseParserService, TextIncomeParserService incomeParserService,
            ExpenseService expenseService, IncomeService incomeService, RecurringExpenseService recurringExpenseService,
            RecurringIncomeService recurringIncomeService) {
        this.commandResolver = commandResolver;
        this.expenseParserService = expenseParserService;
        this.incomeParserService = incomeParserService;
        this.expenseService = expenseService;
        this.incomeService = incomeService;
        this.recurringExpenseService = recurringExpenseService;
        this.recurringIncomeService = recurringIncomeService;
    }

    public String process(String text) {
        TelegramCommandType commandType = commandResolver.resolve(text);

        return switch (commandType) {
            case EXPENSE -> handleExpense(text);

            case PAY_RECURRING -> handlePayRecurring(text);

            case INCOME -> handleIncomes(text);
            
            case RECURRING_INCOME -> handleRecurringIncomes(text);

            case SUMMARY -> "Summary command not implemented yet.";

            case BALANCE -> "Balance command not implemented yet.";

            case PENDING -> "Pending command not implemented yet.";

            case HELP -> getHelpMessage();

            case UNKNOWN -> "Unknown command.";
        };
    }

    private String handleExpense(String text) {

        List<Expense> expenses = expenseParserService.parseMultipleExpenses(text);

        for (Expense expense : expenses) {
            expenseService.saveExpense(expense);
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

    private String handleIncomes(String text) {

        List<Income> incomes = incomeParserService.parseMultipleIncomes(text);

        for (Income income : incomes) {
            incomeService.save(income);
        }

        return "Received " + incomes.size() + " income(s).";
    }

    private String handleRecurringIncomes(String text) {

        String recurringIncomeName = text
                .replaceFirst("received ", "")
                .trim();
    
        recurringIncomeService
                .receiveRecurringIncome(recurringIncomeName);
    
        return "Recurring income received: "
                + recurringIncomeName;
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
