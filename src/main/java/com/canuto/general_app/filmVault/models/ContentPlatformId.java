package com.canuto.general_app.filmVault.models;

import java.io.Serializable;
import java.util.Objects;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.Data;

@Data
@Embeddable
public class ContentPlatformId implements Serializable {
    
    @Column(name = "content_id")
    private Long contentId;
    
    @Column(name = "platform_id")
    private Long platformId;

    public ContentPlatformId() {}

    public ContentPlatformId(Long contentId, Long platformId) {
        this.contentId = contentId;
        this.platformId = platformId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ContentPlatformId)) return false;
        ContentPlatformId that = (ContentPlatformId) o;
        return Objects.equals(contentId, that.contentId) &&
               Objects.equals(platformId, that.platformId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(contentId, platformId);
    }
}
