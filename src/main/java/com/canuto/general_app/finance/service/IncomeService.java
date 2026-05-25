package com.canuto.general_app.finance.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.canuto.general_app.finance.dto.CreateIncomeRequest;
import com.canuto.general_app.finance.model.Income;
import com.canuto.general_app.finance.model.IncomeCategory;
import com.canuto.general_app.finance.repository.IncomeCategoryRepository;
import com.canuto.general_app.finance.repository.IncomeRepository;

@Service
public class IncomeService {

    private final IncomeRepository repository;
    private final IncomeCategoryRepository incomeCategoryRepository;

    public IncomeService(IncomeRepository repository, IncomeCategoryRepository incomeCategoryRepository) {
        this.repository = repository;
        this.incomeCategoryRepository = incomeCategoryRepository;
    }

    public Income save(Income income) {
        return repository.save(income);
    }

    public List<Income> getAll() {
        return repository.findAll();
    }

    public Income create(
            CreateIncomeRequest request) {

        IncomeCategory category = incomeCategoryRepository.findById(
                request.getCategoryId()).orElseThrow(
                        () -> new RuntimeException(
                                "Income category not found."));

        Income income = new Income();

        income.setAmount(request.getAmount());
        income.setNote(request.getNote());
        income.setDate(request.getDate());
        income.setCategory(category);

        return repository.save(income);
    }
}
