package com.canuto.general_app.finance.recurring.income.service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.YearMonth;
import java.util.Comparator;
import java.util.List;

import org.springframework.stereotype.Service;

import com.canuto.general_app.finance.income.model.Income;
import com.canuto.general_app.finance.income.repository.IncomeRepository;
import com.canuto.general_app.finance.incomeCategory.model.IncomeCategory;
import com.canuto.general_app.finance.incomeCategory.repository.IncomeCategoryRepository;
import com.canuto.general_app.finance.recurring.income.dto.CreateRecurringIncomeRequest;
import com.canuto.general_app.finance.recurring.income.model.RecurringIncome;
import com.canuto.general_app.finance.recurring.income.repository.RecurringIncomeRepository;

@Service
public class RecurringIncomeService {

        private final RecurringIncomeRepository recurringIncomeRepository;
        private final IncomeCategoryRepository incomeCategoryRepository;
        private final IncomeRepository incomeRepository;

        public RecurringIncomeService(RecurringIncomeRepository recurringIncomeRepository,
                        IncomeCategoryRepository incomeCategoryRepository, IncomeRepository incomeRepository) {
                this.recurringIncomeRepository = recurringIncomeRepository;
                this.incomeCategoryRepository = incomeCategoryRepository;
                this.incomeRepository = incomeRepository;
        }

        public RecurringIncome create(CreateRecurringIncomeRequest request) {
                IncomeCategory category = incomeCategoryRepository.findById(request.getCategoryId())
                                .orElseThrow(() -> new RuntimeException("Income category not found"));

                RecurringIncome recurringIncome = new RecurringIncome();

                recurringIncome.setName(request.getName());
                recurringIncome.setAmount(request.getAmount());
                recurringIncome.setCategory(category);
                recurringIncome.setDayOfMonth(request.getDayOfMonth());
                recurringIncome.setFrequency(request.getFrequency());
                recurringIncome.setStartDate(request.getStartDate());
                recurringIncome.setEndDate(request.getEndDate());
                recurringIncome.setActive(true);

                return recurringIncomeRepository.save(recurringIncome);
        }

        public List<RecurringIncome> getAll() {
                return recurringIncomeRepository.findAll();
        }

        public Income receiveRecurringIncome(
                        String recurringIncomeName) {

                RecurringIncome recurringIncome = recurringIncomeRepository
                                .findByNameIgnoreCase(
                                                recurringIncomeName)
                                .orElseThrow(() -> new RuntimeException(
                                                "Recurring income not found."));

                if (isAlreadyReceivedThisPeriod(
                                recurringIncome)) {
                        throw new RuntimeException(
                                        "Recurring income already received.");
                }

                Income income = new Income();

                income.setAmount(
                                recurringIncome.getAmount());

                income.setDate(LocalDate.now());

                income.setNote(
                                "Recurring income: "
                                                + recurringIncome.getName());

                income.setCategory(
                                recurringIncome.getCategory());

                Income savedIncome = incomeRepository.save(income);

                recurringIncome.setLastReceivedDate(
                                LocalDate.now());

                recurringIncomeRepository.save(
                                recurringIncome);

                return savedIncome;
        }

        private boolean isAlreadyReceivedThisPeriod(
                        RecurringIncome recurringIncome) {

                if (recurringIncome.getLastReceivedDate() == null) {
                        return false;
                }

                LocalDate now = LocalDate.now();

                LocalDate lastReceived = recurringIncome.getLastReceivedDate();

                return switch (recurringIncome.getFrequency()) {

                        case MONTHLY ->
                                YearMonth.from(lastReceived)
                                                .equals(
                                                                YearMonth.from(now));

                        case YEARLY ->
                                lastReceived.getYear() == now.getYear();

                        case WEEKLY ->
                                lastReceived.plusDays(7)
                                                .isAfter(now);
                };
        }

        public BigDecimal getPendingRecurringIncome() {

                List<RecurringIncome> recurringIncomeList = recurringIncomeRepository.findAll();

                BigDecimal totalPending = BigDecimal.ZERO;

                for (RecurringIncome recurringIncome : recurringIncomeList) {

                        if (!isApplicableNow(
                                        recurringIncome)) {

                                continue;
                        }

                        boolean alreadyReceived = isAlreadyReceivedThisPeriod(
                                        recurringIncome);

                        if (!alreadyReceived) {

                                totalPending = totalPending.add(
                                                recurringIncome.getAmount());
                        }
                }

                return totalPending;
        }

        public List<RecurringIncome> getPendingRecurringIncomeList() {

                return recurringIncomeRepository.findAll()
                                .stream()
                                .filter(this::isApplicableNow)
                                .filter(income -> !isAlreadyReceivedThisPeriod(
                                                income))
                                .sorted((a, b) -> Integer.compare(
                                                a.getDayOfMonth(),
                                                b.getDayOfMonth()))
                                .toList();
        }

        private boolean isApplicableNow(
                        RecurringIncome recurringIncome) {

                LocalDate today = LocalDate.now();

                if (!Boolean.TRUE.equals(
                                recurringIncome.getActive())) {

                        return false;
                }

                if (recurringIncome.getStartDate() != null
                                && today.isBefore(
                                                recurringIncome.getStartDate())) {

                        return false;
                }

                if (recurringIncome.getEndDate() != null
                                && today.isAfter(
                                                recurringIncome.getEndDate())) {

                        return false;
                }

                return true;
        }

        public List<RecurringIncome> getActiveRecurringIncomes() {
                return recurringIncomeRepository.findAll()
                .stream()
                .filter(r -> Boolean.TRUE.equals(r.getActive()))
                .toList();
        }
}
