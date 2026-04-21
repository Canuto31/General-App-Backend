package com.canuto.general_app.finance.model;

import java.time.LocalDate;

import lombok.Data;

@Data
public class Expense {
    private Double amount;
    private String category;
    private String note;
    private LocalDate date;
}
