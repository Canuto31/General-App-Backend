package com.canuto.general_app.finance.incomeCategory.Controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.canuto.general_app.finance.incomeCategory.dto.CreateIncomeCategoryKeywordRequest;
import com.canuto.general_app.finance.incomeCategory.model.IncomeCategoryKeyword;
import com.canuto.general_app.finance.incomeCategory.service.IncomeCategoryKeywordService;

@RestController
@RequestMapping("/income-category_keywords")
public class IncomeCategoryKeywordController {
    
    private final IncomeCategoryKeywordService incomeCategoryKeywordService;

    public IncomeCategoryKeywordController(IncomeCategoryKeywordService incomeCategoryKeywordService) {
        this.incomeCategoryKeywordService = incomeCategoryKeywordService;
    }

    @PostMapping
    public IncomeCategoryKeyword create(@RequestBody CreateIncomeCategoryKeywordRequest request) {
        return incomeCategoryKeywordService.create(request);
    }

    @GetMapping
    public List<IncomeCategoryKeyword> getAll() {
        return incomeCategoryKeywordService.getAll();
    }
}
