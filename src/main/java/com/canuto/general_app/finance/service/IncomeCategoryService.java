package com.canuto.general_app.finance.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.canuto.general_app.finance.dto.CreateIncomeCategoryRequest;
import com.canuto.general_app.finance.model.IncomeCategory;
import com.canuto.general_app.finance.repository.IncomeCategoryRepository;

@Service
public class IncomeCategoryService {
    
    private final IncomeCategoryRepository repository;

    public IncomeCategoryService(IncomeCategoryRepository repository) {
        this.repository = repository;
    }

    public IncomeCategory create(CreateIncomeCategoryRequest request) {
        IncomeCategory category = new IncomeCategory();

        category.setName(request.getName());

        return repository.save(category);
    }

    public List<IncomeCategory> getAll() {
        return repository.findAll();
    }
}
