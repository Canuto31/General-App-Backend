package com.canuto.general_app.finance.model;

import java.math.BigDecimal;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name = "monthly_status")
@Data
public class MonthlyStatus {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Integer month;

    private Integer year;

    @Column(nullable = false)
    private BigDecimal income = BigDecimal.ZERO;

    @Column(nullable = false)
    private BigDecimal expenses = BigDecimal.ZERO;

    @Column(nullable = false)
    private BigDecimal balance = BigDecimal.ZERO;

    @Column(name = "is_deficit")
    private Boolean deficit = false;

    @Column(name = "alert_sent")
    private Boolean alertSent = false;
}
