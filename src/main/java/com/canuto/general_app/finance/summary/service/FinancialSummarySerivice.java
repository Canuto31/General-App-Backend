package com.canuto.general_app.finance.summary.service;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.stereotype.Service;

import com.canuto.general_app.finance.expense.repository.ExpenseRepository;
import com.canuto.general_app.finance.income.repository.IncomeRepository;
import com.canuto.general_app.finance.recurring.expense.model.RecurringExpense;
import com.canuto.general_app.finance.recurring.expense.service.RecurringExpenseService;
import com.canuto.general_app.finance.recurring.income.model.RecurringIncome;
import com.canuto.general_app.finance.recurring.income.service.RecurringIncomeService;
import com.canuto.general_app.finance.summary.dto.CurrentBalanceResponse;
import com.canuto.general_app.finance.summary.dto.MonthSummaryResponse;
import com.canuto.general_app.finance.summary.dto.PendingItemResponse;
import com.canuto.general_app.finance.summary.dto.PendingSummaryResponse;

@Service
public class FinancialSummarySerivice {

        private final ExpenseRepository expenseRepository;
        private final IncomeRepository incomeRepository;
        private final RecurringExpenseService recurringExpenseService;
        private final RecurringIncomeService recurringIncomeService;

        public FinancialSummarySerivice(ExpenseRepository expenseRepository, IncomeRepository incomeRepository,
                        RecurringExpenseService recurringExpenseService,
                        RecurringIncomeService recurringIncomeService) {
                this.expenseRepository = expenseRepository;
                this.incomeRepository = incomeRepository;
                this.recurringExpenseService = recurringExpenseService;
                this.recurringIncomeService = recurringIncomeService;
        }

        public CurrentBalanceResponse getCurrentBalance() {

                BigDecimal totalIncome = incomeRepository.getTotalIncome();

                BigDecimal totalExpenses = expenseRepository.getTotalExpenses();

                BigDecimal balance = totalIncome.subtract(totalExpenses);

                CurrentBalanceResponse response = new CurrentBalanceResponse();

                response.setTotalIncome(totalIncome);
                response.setTotalExpenses(totalExpenses);
                response.setCurrentBalance(balance);

                return response;
        }

        public MonthSummaryResponse getMonthSummary() {

                CurrentBalanceResponse currentBalance = getCurrentBalance();

                BigDecimal pendingExpenses = recurringExpenseService
                                .getPendingRecurringExpenses();

                BigDecimal pendingIncome = recurringIncomeService
                                .getPendingRecurringIncome();

                BigDecimal projectedEndMonthBalance = currentBalance.getCurrentBalance()
                                .add(pendingIncome)
                                .subtract(pendingExpenses);

                MonthSummaryResponse response = new MonthSummaryResponse();

                response.setCurrentBalance(
                                currentBalance.getCurrentBalance());

                response.setPendingRecurringExpenses(
                                pendingExpenses);

                response.setPendingRecurringIncome(
                                pendingIncome);

                response.setProjectedEndMonthBalance(
                                projectedEndMonthBalance);

                return response;
        }

        public PendingSummaryResponse getPendingSummary() {

                BigDecimal pendingExpenses =
                        recurringExpenseService
                                .getPendingRecurringExpenses();
            
                BigDecimal pendingIncome =
                        recurringIncomeService
                                .getPendingRecurringIncome();
            
                BigDecimal netPendingBalance =
                        pendingIncome.subtract(
                                pendingExpenses);
            
                List<RecurringExpense> pendingExpenseList =
                        recurringExpenseService
                                .getPendingRecurringExpenseList();
            
                List<RecurringIncome> pendingIncomeList =
                        recurringIncomeService
                                .getPendingRecurringIncomeList();
            
                List<PendingItemResponse> pendingExpensesItems =
                        pendingExpenseList.stream()
                                .map(recurringExpense -> {
            
                                    PendingItemResponse item =
                                            new PendingItemResponse();
            
                                    item.setName(
                                            recurringExpense.getName());
            
                                    item.setAmount(
                                            recurringExpense.getAmount());
            
                                    item.setDayOfMonth(
                                            recurringExpense.getDayOfMonth());
            
                                    return item;
                                })
                                .toList();
            
                List<PendingItemResponse> pendingIncomeItems =
                        pendingIncomeList.stream()
                                .map(recurringIncome -> {
            
                                    PendingItemResponse item =
                                            new PendingItemResponse();
            
                                    item.setName(
                                            recurringIncome.getName());
            
                                    item.setAmount(
                                            recurringIncome.getAmount());
            
                                    item.setDayOfMonth(
                                            recurringIncome.getDayOfMonth());
            
                                    return item;
                                })
                                .toList();
            
                PendingSummaryResponse response =
                        new PendingSummaryResponse();
            
                response.setPendingRecurringExpenses(
                        pendingExpenses);
            
                response.setPendingRecurringIncomes(
                        pendingIncome);
            
                response.setNetPendingBalance(
                        netPendingBalance);
            
                response.setPendingExpenses(
                        pendingExpensesItems);
            
                response.setPendingIncomes(
                        pendingIncomeItems);
            
                return response;
            }
}
