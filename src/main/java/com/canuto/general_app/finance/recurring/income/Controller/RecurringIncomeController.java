package com.canuto.general_app.finance.recurring.income.Controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.canuto.general_app.finance.income.model.Income;
import com.canuto.general_app.finance.recurring.income.dto.CreateRecurringIncomeRequest;
import com.canuto.general_app.finance.recurring.income.model.RecurringIncome;
import com.canuto.general_app.finance.recurring.income.service.RecurringIncomeService;

@RestController
@RequestMapping("/recurring-incomes")
public class RecurringIncomeController {

    private final RecurringIncomeService recurringIncomeService;
    
    public RecurringIncomeController(RecurringIncomeService recurringIncomeService) {
        this.recurringIncomeService = recurringIncomeService;
    }

    @PostMapping
    public RecurringIncome createRecurringIncome(
            @RequestBody CreateRecurringIncomeRequest request) {
        return recurringIncomeService
                .create(request);
    }

    @GetMapping
    public List<RecurringIncome> getRecurringIncome() {

        return recurringIncomeService
                .getAll();
    }

    @PostMapping("/{name}/receive")
    public Income receiveRecurringIncome(
            @PathVariable String name) {

        return recurringIncomeService
                .receiveRecurringIncome(name);
    }
}
