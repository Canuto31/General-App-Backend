package com.canuto.general_app.finance.incomeCategory.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.canuto.general_app.finance.incomeCategory.model.IncomeCategoryKeyword;

@Repository
public interface IncomeCategoryKeywordRepository extends JpaRepository<IncomeCategoryKeyword, Long>{
    
}
