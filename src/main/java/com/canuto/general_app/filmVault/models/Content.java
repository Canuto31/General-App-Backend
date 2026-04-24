package com.canuto.general_app.filmVault.models;

import java.time.LocalDateTime;
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
import lombok.Data;

@Data
@Entity
@Table(name = "content")
public class Content {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "source", nullable = false)
    private String source;

    @Column(name = "tmdb_id")
    private Long tmdbId;

    @Column(length = 255, nullable = false)
    private String title;

    @Column(columnDefinition = "TEXT")
    private String description;

    @Column(name = "release_year")
    private Integer releaseYear;

    @Column(name = "duration_minutes")
    private Integer durationMinutes;

    @Column(length = 10, nullable = false)
    private String type;

    @Column(name = "poster_url", length = 500)
    private String posterUrl;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "created_by_user_id")
    private User createdByUser;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @JsonIgnore
    @OneToMany(mappedBy = "content")
    private List<ContentGenre> contentGenres;

    @JsonIgnore
    @OneToMany(mappedBy = "content")
    private List<ContentPlatform> contentPlatforms;

    @JsonIgnore
    @OneToMany(mappedBy = "content")
    private List<Season> seasons;

    @JsonIgnore
    @OneToMany(mappedBy = "content")
    private List<UserContent> userContents;

    public Long getId() {
        return id;
    }
}
