package com.canuto.general_app.finance.recurring.income.model;

import java.math.BigDecimal;
import java.time.LocalDate;

import com.canuto.general_app.finance.income.model.IncomeCategory;
import com.canuto.general_app.finance.shared.enums.Frequency;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Data
@Table(name = "recurring_income")
public class RecurringIncome {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private BigDecimal amount;

    @ManyToOne
    private IncomeCategory category;

    @Column(name = "day_of_month")
    private Integer dayOfMonth;

    @Enumerated(EnumType.STRING)
    private Frequency frequency;

    @Column(name = "start_date")
    private LocalDate startDate;

    @Column(name = "end_date")
    private LocalDate endDate;

    @Column(name = "last_received_date")
    private LocalDate lastReceivedDate;

    private Boolean active = true;
}
