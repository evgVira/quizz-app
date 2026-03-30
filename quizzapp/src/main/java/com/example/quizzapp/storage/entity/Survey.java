package com.example.quizzapp.storage.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Entity
@Table(name = "survey", schema = "quizz_sc")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Survey {

    // опрос

    @Id
    @Column(name = "id")
    private String id;

    @Column(name = "title")
    private String title;

    @Column(name = "description")
    private String description;

    @Column(name = "create_dt")
    private Instant createDt;

    @Column(name = "update_dt")
    private Instant updateDt;

    @Column(name = "is_active")
    private Boolean isActive;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "created_by_id")
    private UserEntity createdBy;

}
