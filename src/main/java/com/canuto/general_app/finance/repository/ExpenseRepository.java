package com.canuto.general_app.finance.repository;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.canuto.general_app.finance.model.Expense;

@Repository
public interface ExpenseRepository extends JpaRepository<Expense, Long> {

    List<Expense> findByDateBetween(LocalDate start, LocalDate end);

    @Query("""
                SELECT COALESCE(SUM(e.amount), 0)
                FROM Expense e
            """)
    BigDecimal getTotalExpenses();
}
