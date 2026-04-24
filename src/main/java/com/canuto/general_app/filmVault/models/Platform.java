package com.canuto.general_app.filmVault.models;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "platform")
public class Platform {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 100, nullable = false, unique = true)
    private String name;

    @Column(name = "logo_url", length = 500)
    private String logoUrl;

    @JsonIgnore
    @OneToMany(mappedBy = "platform")
    private List<ContentPlatform> contentPlatforms;

    public Long getId() {
        return id;
    }
}
