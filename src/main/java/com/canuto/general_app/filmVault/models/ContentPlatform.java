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
@Table(name = "content_platform")
public class ContentPlatform {

    @EmbeddedId
    private ContentPlatformId id;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("contentId")
    @JoinColumn(name = "content_id")
    @JsonIgnore
    private Content content;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("platformId")
    @JoinColumn(name = "platform_id")
    @JsonIgnore
    private Platform platform;

    public ContentPlatform() {}

    public ContentPlatform(Content content, Platform platform) {
        this.content = content;
        this.platform = platform;
        this.id = new ContentPlatformId(content.getId(), platform.getId());
    }
}