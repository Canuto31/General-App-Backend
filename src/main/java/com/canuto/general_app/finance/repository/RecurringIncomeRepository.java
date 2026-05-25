package com.canuto.general_app.finance.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.canuto.general_app.finance.model.RecurringIncome;

@Repository
public interface RecurringIncomeRepository extends JpaRepository<RecurringIncome, Long>{
    
    Optional<RecurringIncome> findByNameIgnoreCase(String name);
}
