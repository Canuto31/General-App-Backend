package com.canuto.general_app.finance.incomeCategory.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.canuto.general_app.finance.incomeCategory.enums.IncomeCategoryType;
import com.canuto.general_app.finance.incomeCategory.model.IncomeCategory;

@Repository
public interface IncomeCategoryRepository extends JpaRepository<IncomeCategory, Long>{
    
    Optional<IncomeCategory> findByType(IncomeCategoryType type);
}
