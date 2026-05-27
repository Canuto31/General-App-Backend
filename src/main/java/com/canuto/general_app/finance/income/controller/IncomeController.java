package com.canuto.general_app.finance.income.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.canuto.general_app.finance.income.dto.CreateIncomeCategoryRequest;
import com.canuto.general_app.finance.income.dto.CreateIncomeRequest;
import com.canuto.general_app.finance.income.model.Income;
import com.canuto.general_app.finance.income.model.IncomeCategory;
import com.canuto.general_app.finance.income.service.IncomeCategoryService;
import com.canuto.general_app.finance.income.service.IncomeService;

@RestController
@RequestMapping("/income")
public class IncomeController {

    private final IncomeService incomeService;

    private final IncomeCategoryService incomeCategoryService;

    public IncomeController(
            IncomeService incomeService,
            IncomeCategoryService incomeCategoryService) {
        this.incomeService = incomeService;
        this.incomeCategoryService = incomeCategoryService;
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
}
