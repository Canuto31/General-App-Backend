package com.canuto.general_app.finance.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.canuto.general_app.finance.model.RecurringExpense;

@Repository
public interface RecurringExpenseRepository  extends JpaRepository<RecurringExpense, Long>{
    
}
