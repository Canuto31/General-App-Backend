package com.canuto.general_app.finance.incomeCategory.Controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.canuto.general_app.finance.incomeCategory.dto.CreateIncomeCategoryRequest;
import com.canuto.general_app.finance.incomeCategory.model.IncomeCategory;
import com.canuto.general_app.finance.incomeCategory.service.IncomeCategoryService;

@RestController
@RequestMapping("/income-categories")
public class IncomeCategoryController {
    
    private IncomeCategoryService incomeCategoryService;

    public IncomeCategoryController(IncomeCategoryService incomeCategoryService) {
        this.incomeCategoryService = incomeCategoryService;
    }

    @PostMapping
    public IncomeCategory create(@RequestBody CreateIncomeCategoryRequest request) {
        return incomeCategoryService.create(request);
    }

    @GetMapping
    public List<IncomeCategory> getAll() {
        return incomeCategoryService.getAll();
    }
}
