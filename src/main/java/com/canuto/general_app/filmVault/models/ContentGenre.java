package com.canuto.general_app.filmVault.models;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MapsId;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "content_genre")
public class ContentGenre {
    
    @EmbeddedId
    private ContentGenreId id;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("contentId")
    @JoinColumn(name = "content_id")
    @JsonIgnore
    private Content content;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("genreId")
    @JoinColumn(name = "genre_id")
    @JsonIgnore
    private Genre genre;

    public ContentGenre() {}

    public ContentGenre(Content content, Genre genre) {
        this.content = content;
        this.genre = genre;
        this.id = new ContentGenreId(content.getId(), genre.getId());
    }
}
