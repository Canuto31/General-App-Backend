package com.canuto.general_app.finance.incomeCategory.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.canuto.general_app.finance.incomeCategory.dto.CreateIncomeCategoryKeywordRequest;
import com.canuto.general_app.finance.incomeCategory.model.IncomeCategory;
import com.canuto.general_app.finance.incomeCategory.model.IncomeCategoryKeyword;
import com.canuto.general_app.finance.incomeCategory.repository.IncomeCategoryKeywordRepository;
import com.canuto.general_app.finance.incomeCategory.repository.IncomeCategoryRepository;

@Service
public class IncomeCategoryKeywordService {
    
    private final IncomeCategoryKeywordRepository incomeCategoryKeywordRepository;
    private final IncomeCategoryRepository incomeCategoryRepository;

    public IncomeCategoryKeywordService(IncomeCategoryKeywordRepository incomeCategoryKeywordRepository, IncomeCategoryRepository incomeCategoryRepository) {
        this.incomeCategoryKeywordRepository = incomeCategoryKeywordRepository;
        this.incomeCategoryRepository = incomeCategoryRepository;
    }

    public IncomeCategoryKeyword create (CreateIncomeCategoryKeywordRequest request) {
        IncomeCategory incomeCategory = incomeCategoryRepository.findById(request.getIncomeCategoryId()).orElseThrow(() -> new RuntimeException("Income category not found"));

        IncomeCategoryKeyword keyword = new IncomeCategoryKeyword();

        keyword.setKeyword(request.getKeyword());
        keyword.setIncomeCategory(incomeCategory);

        return incomeCategoryKeywordRepository.save(keyword);
    }

    public List<IncomeCategoryKeyword> getAll() {
        return incomeCategoryKeywordRepository.findAll();
    }
}
