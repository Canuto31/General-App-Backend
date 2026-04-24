package com.canuto.general_app.filmVault.models;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.Data;
import jakarta.persistence.FetchType;

@Data
@Entity
@Table(name = "user_content", uniqueConstraints = {@UniqueConstraint(name = "uq_user_content", columnNames = {"user_id", "content_id"})})
public class UserContent {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "content_id", nullable = false)
    private Content content;

    @Column(length = 20, nullable = false)
    private String status;

    @Column(precision = 3, scale = 1)
    private BigDecimal rating;

    @Column(columnDefinition = "TEXT")
    private String notes;

    @Column(name = "added_at", nullable = false)
    private LocalDateTime addedAt;

    @Column(name = "watched_at")
    private LocalDateTime watchedAt;
}
