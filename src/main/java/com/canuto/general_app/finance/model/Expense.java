package com.canuto.general_app.finance.model;

import java.math.BigDecimal;
import java.time.LocalDate;

import com.canuto.general_app.finance.category.model.Category;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.Data;

@Entity
@Data
public class Expense {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private BigDecimal amount;

    private String note;

    private LocalDate date;

    @ManyToOne
    private Category category;
}
