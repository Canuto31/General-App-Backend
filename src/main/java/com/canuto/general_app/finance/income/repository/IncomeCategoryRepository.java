package com.canuto.general_app.finance.income.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.canuto.general_app.finance.income.model.IncomeCategory;

@Repository
public interface IncomeCategoryRepository extends JpaRepository<IncomeCategory, Long>{
    
    Optional<IncomeCategory> findByNameIgnoreCase(String name);
}
