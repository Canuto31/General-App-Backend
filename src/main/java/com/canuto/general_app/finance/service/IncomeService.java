package com.canuto.general_app.finance.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.canuto.general_app.finance.model.Income;
import com.canuto.general_app.finance.repository.IncomeRepository;

@Service
public class IncomeService {
    
    private final IncomeRepository repository;

    public IncomeService(IncomeRepository repository) {
        this.repository = repository;
    }

    public Income save(Income income) {
        return repository.save(income);
    }

    public List<Income> getAll() {
        return repository.findAll();
    }
}
