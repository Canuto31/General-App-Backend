package com.canuto.general_app.finance.incomeCategory.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.canuto.general_app.finance.incomeCategory.dto.CreateIncomeCategoryRequest;
import com.canuto.general_app.finance.incomeCategory.enums.IncomeCategoryType;
import com.canuto.general_app.finance.incomeCategory.model.IncomeCategory;
import com.canuto.general_app.finance.incomeCategory.model.IncomeCategoryKeyword;
import com.canuto.general_app.finance.incomeCategory.repository.IncomeCategoryKeywordRepository;
import com.canuto.general_app.finance.incomeCategory.repository.IncomeCategoryRepository;

@Service
public class IncomeCategoryService {
    
    private final IncomeCategoryRepository repository;
    private final IncomeCategoryKeywordRepository incomeCategoryKeywordRepository;

    public IncomeCategoryService(IncomeCategoryRepository repository,IncomeCategoryKeywordRepository incomeCategoryKeywordRepository) {
        this.repository = repository;
        this.incomeCategoryKeywordRepository = incomeCategoryKeywordRepository;
    }

    public IncomeCategory create(CreateIncomeCategoryRequest request) {
        IncomeCategory incomeCategory = new IncomeCategory();

        incomeCategory.setName(request.getName());
        incomeCategory.setType(request.getType());

        return repository.save(incomeCategory);
    }

    public List<IncomeCategory> getAll() {
        return repository.findAll();
    }

    public IncomeCategory resolveIncomeCategory(String text) {

        String lowerText = text.toLowerCase();

        List<IncomeCategoryKeyword> keywords = incomeCategoryKeywordRepository.findAll();

        for (IncomeCategoryKeyword keyword : keywords) {
            if (lowerText.contains(keyword.getKeyword().toLowerCase())) {
                return keyword.getIncomeCategory();
            }
        }

        return repository.findByType(IncomeCategoryType.OTHERS).orElseThrow(() -> new RuntimeException("Income Category OTHERS not found"));
    }
}
