package com.example.filtro_meta.domain.entities;

import java.time.LocalDateTime;
import java.util.List;

import com.example.filtro_meta.utils.enums.State;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Lob;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/* Anotaciones */
@Entity(name = "survey")
@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Survey {
    /* Atributos */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;
    
    @Lob
    private String description;

    @Column(nullable = false, columnDefinition = "TIMESTAMP")
    private LocalDateTime creation_date;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private State active;

    /* Relación con la tabla Question */
    @OneToMany(mappedBy = "survey", fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = false)
    private List<Question> questions;

    /* Relación con la tabla User */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "creator_id", referencedColumnName = "id")
    private User user;
}
