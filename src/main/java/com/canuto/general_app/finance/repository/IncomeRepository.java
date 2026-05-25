package com.canuto.general_app.finance.repository;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.canuto.general_app.finance.model.Income;

@Repository
public interface IncomeRepository extends JpaRepository<Income, Long> {

    List<Income> findByDateBetween(LocalDate startDate, LocalDate endDate);

    @Query("""
                SELECT COALESCE(SUM(i.amount), 0)
                FROM Income i
            """)
    BigDecimal getTotalIncome();
}
