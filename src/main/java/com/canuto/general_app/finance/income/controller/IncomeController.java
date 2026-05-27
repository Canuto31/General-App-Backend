package com.canuto.general_app.finance.income.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.canuto.general_app.finance.income.dto.CreateIncomeRequest;
import com.canuto.general_app.finance.income.model.Income;
import com.canuto.general_app.finance.income.parser.TextIncomeParserService;
import com.canuto.general_app.finance.income.service.IncomeService;

@RestController
@RequestMapping("/income")
public class IncomeController {

    private final IncomeService incomeService;
    private final TextIncomeParserService parserService;

    public IncomeController(
            IncomeService incomeService, TextIncomeParserService parserService) {
        this.incomeService = incomeService;
        this.parserService = parserService;
    }

    @PostMapping("/smart")
    public List<Income> createIncomeSmart(@RequestBody String text) {
        List<Income> incomes = parserService.parseMultipleIncomes(text);

        return incomes.stream()
                .map(incomeService::save)
                .toList();
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
}
