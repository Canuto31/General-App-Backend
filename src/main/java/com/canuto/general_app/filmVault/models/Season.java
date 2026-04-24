package com.canuto.general_app.filmVault.models;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.Data;

@Data
@Entity
@Table(name = "season", uniqueConstraints = {
        @UniqueConstraint(name = "uq_content_season", columnNames = { "content_id", "season_number" }) })
public class Season {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "content_id", nullable = false)
    private Content content;

    @Column(name = "season_number", nullable = false)
    private Integer seasonNumber;

    @Column(name = "release_year")
    private Integer releaseYear;

    @JsonIgnore
    @OneToMany(mappedBy = "season")
    private List<Episode> episodes;
}
