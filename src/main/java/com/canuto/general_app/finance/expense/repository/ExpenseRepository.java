package com.canuto.general_app.finance.expense.repository;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.canuto.general_app.finance.expense.model.Expense;

@Repository
public interface ExpenseRepository extends JpaRepository<Expense, Long> {

    List<Expense> findByDateBetween(LocalDate start, LocalDate end);

    @Query("""
                SELECT COALESCE(SUM(e.amount), 0)
                FROM Expense e
            """)
    BigDecimal getTotalExpenses();

    @Query("""
                SELECT
                    e.category.name,
                    SUM(e.amount)
                FROM Expense e
                GROUP BY e.category.name
                ORDER BY SUM(e.amount) DESC
            """)
    List<Object[]> getExpensesGroupedByCategory();

    @Query("""
                SELECT
                    c.name,
                    SUM(e.amount)
                FROM Expense e
                JOIN e.category c
                WHERE YEAR(e.date) = :year
                  AND MONTH(e.date) = :month
                GROUP BY c.name
            """)
    List<Object[]> getExpensesByCategoryForMonth(
            Integer year,
            Integer month);

    @Query("""
                SELECT
                    c.name,
                    SUM(e.amount)
                FROM Expense e
                JOIN e.category c
                WHERE YEAR(e.date) = :year
                GROUP BY c.name
            """)
    List<Object[]> getExpensesByCategoryForYear(
            Integer year);

    List<Expense> findTop10ByOrderByDateDesc();

    Optional<Expense> findTopByOrderByDateDesc();
}
