package com.canuto.general_app.filmVault.models;

import java.io.Serializable;
import java.util.Objects;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.Data;

@Data
@Embeddable
public class ContentGenreId implements Serializable {
    
    @Column(name = "content_id")
    private Long contentId;
    
    @Column(name = "genre_id")
    private Long genreId;

    public ContentGenreId() {}

    public ContentGenreId(Long contentId, Long genreId) {
        this.contentId = contentId;
        this.genreId = genreId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ContentGenreId)) return false;
        ContentGenreId that = (ContentGenreId) o;
        return Objects.equals(contentId, that.contentId) &&
               Objects.equals(genreId, that.genreId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(contentId, genreId);
    }
}
