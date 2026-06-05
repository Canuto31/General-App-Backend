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

                        case BALANCE -> handleBalance();

                        case PENDING -> handlePending();

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
                                help
                                """;
        }
}
