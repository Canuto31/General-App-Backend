package com.canuto.general_app.finance.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.canuto.general_app.finance.dto.CreateIncomeCategoryRequest;
import com.canuto.general_app.finance.dto.CreateIncomeRequest;
import com.canuto.general_app.finance.dto.CreateRecurringIncomeRequest;
import com.canuto.general_app.finance.model.Income;
import com.canuto.general_app.finance.model.IncomeCategory;
import com.canuto.general_app.finance.model.RecurringIncome;
import com.canuto.general_app.finance.service.IncomeCategoryService;
import com.canuto.general_app.finance.service.IncomeService;
import com.canuto.general_app.finance.service.RecurringIncomeService;

@RestController
@RequestMapping("/income")
public class IncomeController {

    private final IncomeService incomeService;

    private final IncomeCategoryService incomeCategoryService;

    private final RecurringIncomeService recurringIncomeService;

    public IncomeController(
            IncomeService incomeService,
            IncomeCategoryService incomeCategoryService,
            RecurringIncomeService recurringIncomeService) {
        this.incomeService = incomeService;
        this.incomeCategoryService = incomeCategoryService;
        this.recurringIncomeService = recurringIncomeService;
    }

    @GetMapping
    public List<Income> getAllIncome() {
        return incomeService.getAll();
    }

    @PostMapping
    public Income createIncome(
            @RequestBody CreateIncomeRequest request) {
        return incomeService.create(request);
    }

    @PostMapping("/categories")
    public IncomeCategory createCategory(
            @RequestBody CreateIncomeCategoryRequest request) {
        return incomeCategoryService
                .create(request);
    }

    @GetMapping("/categories")
    public List<IncomeCategory> getCategories() {
        return incomeCategoryService.getAll();
    }

    @PostMapping("/recurring")
    public RecurringIncome createRecurringIncome(
            @RequestBody CreateRecurringIncomeRequest request) {
        return recurringIncomeService
                .create(request);
    }

    @GetMapping("/recurring")
    public List<RecurringIncome> getRecurringIncome() {

        return recurringIncomeService
                .getAll();
    }

    @PostMapping("/recurring/{name}/receive")
    public Income receiveRecurringIncome(
            @PathVariable String name) {

        return recurringIncomeService
                .receiveRecurringIncome(name);
    }
}
