package com.canuto.general_app.finance.telegram.service;

import java.math.BigDecimal;
import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;

import org.springframework.stereotype.Service;

import com.canuto.general_app.finance.expense.model.Expense;
import com.canuto.general_app.finance.expense.parser.TextExpenseParserService;
import com.canuto.general_app.finance.expense.service.ExpenseService;
import com.canuto.general_app.finance.income.model.Income;
import com.canuto.general_app.finance.income.parser.TextIncomeParserService;
import com.canuto.general_app.finance.income.service.IncomeService;
import com.canuto.general_app.finance.recurring.expense.model.RecurringExpense;
import com.canuto.general_app.finance.recurring.expense.service.RecurringExpenseService;
import com.canuto.general_app.finance.recurring.income.model.RecurringIncome;
import com.canuto.general_app.finance.recurring.income.service.RecurringIncomeService;
import com.canuto.general_app.finance.shared.utils.MoneyFormatter;
import com.canuto.general_app.finance.statistics.dto.CategoryExpenseDto;
import com.canuto.general_app.finance.statistics.service.ExpenseStatsService;
import com.canuto.general_app.finance.summary.dto.CategorySummaryResponse;
import com.canuto.general_app.finance.summary.dto.CurrentBalanceResponse;
import com.canuto.general_app.finance.summary.dto.MonthSummaryResponse;
import com.canuto.general_app.finance.summary.dto.PendingItemResponse;
import com.canuto.general_app.finance.summary.dto.PendingSummaryResponse;
import com.canuto.general_app.finance.summary.service.CategorySummaryService;
import com.canuto.general_app.finance.summary.service.FinancialSummarySerivice;
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
        private final FinancialSummarySerivice financialSummarySerivice;
        private final CategorySummaryService categorySummaryService;
        private final ExpenseStatsService expenseStatsService;

        public TelegramCommandService(TelegramCommandResolver commandResolver,
                        TextExpenseParserService expenseParserService, TextIncomeParserService incomeParserService,
                        ExpenseService expenseService, IncomeService incomeService,
                        RecurringExpenseService recurringExpenseService,
                        RecurringIncomeService recurringIncomeService,
                        FinancialSummarySerivice financialSummarySerivice,
                        CategorySummaryService categorySummaryService,
                        ExpenseStatsService expenseStatsService) {
                this.commandResolver = commandResolver;
                this.expenseParserService = expenseParserService;
                this.incomeParserService = incomeParserService;
                this.expenseService = expenseService;
                this.incomeService = incomeService;
                this.recurringExpenseService = recurringExpenseService;
                this.recurringIncomeService = recurringIncomeService;
                this.financialSummarySerivice = financialSummarySerivice;
                this.categorySummaryService = categorySummaryService;
                this.expenseStatsService = expenseStatsService;
        }

        public String process(String text) {
                TelegramCommandType commandType = commandResolver.resolve(text);

                return switch (commandType) {
                        case EXPENSE -> handleExpense(text);

                        case PAY_RECURRING -> handlePayRecurring(text);

                        case INCOME -> handleIncomes(text);

                        case RECURRING_INCOME -> handleRecurringIncomes(text);

                        case SUMMARY -> handleSummary();

                        case CATEGORIES -> handleCategories();

                        case INCOME_CATEGORIES -> handleIncomeCategories();

                        case MONTH_EXPENSES -> handleMonthExpenses();

                        case YEAR_EXPENSES -> handleYearExpenses();
                        case EXPENSES -> handleExpenses();

                        case INCOMES -> handleIncomesList();

                        case RECURRING_EXPENSES -> handleRecurringExpenses();

                        case RECURRING_INCOMES -> handleRecurringIncomesList();

                        case LAST_EXPENSE -> handleLastExpense();

                        case LAST_INCOME -> handleLastIncome();

                        case DELETE_LAST_EXPENSE -> handleDeleteLastExpense();

                        case DELETE_LAST_INCOME -> handleDeleteLastIncome();

                        case BALANCE -> handleBalance();

                        case PENDING -> handlePending();

                        case HELP -> getHelpMessage();

                        case UNKNOWN ->
                                """
                                                Unknown command.

                                                Type 'help' to see available commands.
                                                """;
                };
        }

        private String handleExpense(String text) {

                List<Expense> expenses = expenseParserService.parseMultipleExpenses(text);

                for (Expense expense : expenses) {
                        expenseService.saveExpense(expense);
                }

                return "Saved " + expenses.size() + " expense(s).";
        }

        private String handleExpenses() {
                List<Expense> expenses = expenseService.getLastExpenses();

                if (expenses.isEmpty()) {
                        return "No expenses found.";
                }

                StringBuilder response = new StringBuilder("Last expenses:\n\n");

                for (Expense expense : expenses) {
                        response.append("- ")
                                        .append(expense.getDate())
                                        .append(" | ")
                                        .append(expense.getCategory().getName())
                                        .append(" | ")
                                        .append(formatCurrency(expense.getAmount()))
                                        .append("\n");
                }

                return response.toString();
        }

        private String handleRecurringExpenses() {
                List<RecurringExpense> expenses = recurringExpenseService.getActiveRecurringExpenses();

                if (expenses.isEmpty()) {
                        return "No recurring expenses found.";
                }

                StringBuilder response = new StringBuilder("Recurring expenses:\n\n");

                for (RecurringExpense expense : expenses) {

                        response.append("- ")
                                        .append(expense.getName())
                                        .append(" | ")
                                        .append(formatCurrency(expense.getAmount()))
                                        .append(" | Day ")
                                        .append(expense.getDayOfMonth())
                                        .append("\n");
                }

                return response.toString();
        }

        private String handleLastExpense() {

                Expense expense = expenseService.getLastExpense();

                return """
                                Last expense:

                                Date: %s
                                Category: %s
                                Amount: %s
                                Note: %s
                                """
                                .formatted(
                                                expense.getDate(),
                                                expense.getCategory().getName(),
                                                formatCurrency(expense.getAmount()),
                                                expense.getNote());
        }

        private String handleDeleteLastExpense() {

                Expense expense = expenseService.deleteLastExpense();

                return """
                                Deleted expense:

                                %s
                                %s
                                """
                                .formatted(
                                                expense.getNote(),
                                                formatCurrency(
                                                                expense.getAmount()));
        }

        private String handlePayRecurring(String text) {

                String recurringName = text
                                .toLowerCase()
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

        private String handleIncomesList() {
                List<Income> incomes = incomeService.getLastIncomes();

                if (incomes.isEmpty()) {
                        return "No incomes found.";
                }

                StringBuilder response = new StringBuilder("Last incomes:\n\n");

                for (Income income : incomes) {
                        response.append("- ")
                                        .append(income.getDate())
                                        .append(" | ")
                                        .append(income.getCategory().getName())
                                        .append(" | ")
                                        .append(formatCurrency(income.getAmount()))
                                        .append("\n");
                }

                return response.toString();
        }

        private String handleRecurringIncomesList() {

                List<RecurringIncome> incomes = recurringIncomeService.getActiveRecurringIncomes();

                if (incomes.isEmpty()) {
                        return "No recurring incomes found.";
                }

                StringBuilder response = new StringBuilder("Recurring incomes:\n\n");

                for (RecurringIncome income : incomes) {

                        response.append("- ")
                                        .append(income.getName())
                                        .append(" | ")
                                        .append(formatCurrency(income.getAmount()))
                                        .append(" | Day ")
                                        .append(income.getDayOfMonth())
                                        .append("\n");
                }

                return response.toString();
        }

        private String handleLastIncome() {

                Income income = incomeService.getLastIncome();

                return """
                                Last income:

                                Date: %s
                                Category: %s
                                Amount: %s
                                Note: %s
                                """
                                .formatted(
                                                income.getDate(),
                                                income.getCategory().getName(),
                                                formatCurrency(income.getAmount()),
                                                income.getNote());
        }

        private String handleDeleteLastIncome() {

                Income income = incomeService.deleteLastIncome();

                return """
                                Deleted income:

                                %s
                                %s
                                """
                                .formatted(
                                                income.getNote(),
                                                formatCurrency(
                                                                income.getAmount()));
        }

        private String formatCurrency(BigDecimal amount) {
                return NumberFormat
                                .getNumberInstance(new Locale("es", "CO"))
                                .format(amount);
        }

        private String handleRecurringIncomes(String text) {

                String recurringIncomeName = text
                                .toLowerCase()
                                .replaceFirst("received ", "")
                                .trim();

                recurringIncomeService
                                .receiveRecurringIncome(recurringIncomeName);

                return "Recurring income received: "
                                + recurringIncomeName;
        }

        private String handleSummary() {
                MonthSummaryResponse summary = financialSummarySerivice.getMonthSummary();

                return """
                                Current balance: %s
                                Pending recurring expenses: %s
                                Pending recurring income: %s
                                Projected end month balance: %s
                                """
                                .formatted(
                                                MoneyFormatter.format(summary.getCurrentBalance()),
                                                MoneyFormatter.format(summary.getPendingRecurringExpenses()),
                                                MoneyFormatter.format(summary.getPendingRecurringIncome()),
                                                MoneyFormatter.format(summary.getProjectedEndMonthBalance()));

        }

        private String handleCategories() {

                List<CategorySummaryResponse> categories = categorySummaryService
                                .getExpenseCategories();

                StringBuilder sb = new StringBuilder();

                sb.append("Expenses by category\n\n");

                for (CategorySummaryResponse category : categories) {

                        sb.append(category.getCategoryName())
                                        .append(" -> ")
                                        .append(
                                                        MoneyFormatter.format(
                                                                        category.getTotalAmount()))
                                        .append("\n");
                }

                return sb.toString();
        }

        private String handleIncomeCategories() {

                List<CategorySummaryResponse> categories = categorySummaryService
                                .getIncomeCategories();

                StringBuilder sb = new StringBuilder();

                sb.append("Income by category\n\n");

                for (CategorySummaryResponse category : categories) {

                        sb.append(category.getCategoryName())
                                        .append(" -> ")
                                        .append(
                                                        MoneyFormatter.format(
                                                                        category.getTotalAmount()))
                                        .append("\n");
                }

                return sb.toString();
        }

        private String handleMonthExpenses() {

                List<CategoryExpenseDto> categories = expenseStatsService
                                .getCurrentMonthExpensesByCategory();

                StringBuilder sb = new StringBuilder();

                sb.append("Expenses this month\n\n");

                for (CategoryExpenseDto category : categories) {

                        sb.append("- ")
                                        .append(category.getCategoryName())
                                        .append(" -> ")
                                        .append(MoneyFormatter.format(
                                                        category.getAmount()))
                                        .append("\n");
                }

                return sb.toString();
        }

        private String handleYearExpenses() {

                List<CategoryExpenseDto> categories = expenseStatsService
                                .getCurrentYearExpensesByCategory();

                StringBuilder sb = new StringBuilder();

                sb.append("Expenses this year\n\n");

                for (CategoryExpenseDto category : categories) {

                        sb.append("- ")
                                        .append(category.getCategoryName())
                                        .append(" -> ")
                                        .append(MoneyFormatter.format(
                                                        category.getAmount()))
                                        .append("\n");
                }

                return sb.toString();
        }

        private String handleBalance() {

                CurrentBalanceResponse balance = financialSummarySerivice.getCurrentBalance();

                return """
                                Current balance: %s
                                Total income: %s
                                Total expenses: %s
                                """
                                .formatted(
                                                MoneyFormatter.format(balance.getCurrentBalance()),
                                                MoneyFormatter.format(balance.getTotalIncome()),
                                                MoneyFormatter.format(balance.getTotalExpenses()));
        }

        private String handlePending() {

                PendingSummaryResponse pending = financialSummarySerivice
                                .getPendingSummary();

                StringBuilder response = new StringBuilder();

                response.append("Pending expenses:\n");

                for (PendingItemResponse expense : pending.getPendingExpenses()) {

                        response.append("- ")
                                        .append(expense.getName())
                                        .append(" | Day ")
                                        .append(expense.getDayOfMonth())
                                        .append(" -> ")
                                        .append(
                                                        MoneyFormatter.format(
                                                                        expense.getAmount()))
                                        .append("\n");
                }

                response.append("\nPending income:\n");

                for (PendingItemResponse income : pending.getPendingIncomes()) {

                        response.append("- ")
                                        .append(income.getName())
                                        .append(" | Day ")
                                        .append(income.getDayOfMonth())
                                        .append(" -> ")
                                        .append(
                                                        MoneyFormatter.format(
                                                                        income.getAmount()))
                                        .append("\n");
                }

                response.append("\nNet pending balance: ")
                                .append(
                                                MoneyFormatter.format(
                                                                pending.getNetPendingBalance()));

                return response.toString();
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
                                categories
                                income categories
                                month expenses
                                year expenses
                                expenses
                                incomes
                                recurring expenses
                                recurring incomes
                                last expense
                                last income
                                delete last expense
                                delete last income
                                help
                                """;
        }
}
