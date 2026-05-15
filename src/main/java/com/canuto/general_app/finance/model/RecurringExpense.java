package com.canuto.general_app.finance.model;

import java.math.BigDecimal;
import java.time.LocalDate;

import com.canuto.general_app.finance.enums.Frequency;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name = "recurring_expense")
@Data
public class RecurringExpense {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @Column(nullable = false)
    private BigDecimal amount;

    @Column(name = "day_of_month")
    private Integer dayOfMonth;

    @Enumerated(EnumType.STRING)
    private Frequency frequency;

    @Column(name = "start_date")
    private LocalDate startDate;

    @Column(name = "end_date")
    private LocalDate endDate;

    private Boolean active = true;
}
