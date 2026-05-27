package com.canuto.general_app.finance.telegram.dto;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;

@Entity
@Data
public class TelegramUpdateEntity {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "update_id", unique = true, nullable = false)
    private Long updateId;

    @Column(name = "processed_at")
    private LocalDateTime processedAt = LocalDateTime.now();
}
