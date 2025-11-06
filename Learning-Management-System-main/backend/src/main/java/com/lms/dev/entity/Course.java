package com.lms.dev.entity;
import java.util.List;
import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.LocalDateTime;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Course {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "course_id", updatable = false, nullable = false, columnDefinition = "BINARY(16)")
    private UUID course_id;

    @JsonProperty("course_name")
    private String course_name;

    private int price;

    private String instructor; // Legacy field - kept for backward compatibility

    @ManyToOne
    @JoinColumn(name = "instructor_id")
    @JsonIgnore
    private User instructorUser; // New field - links to User entity

    private String description;

    private String p_link;

    private String y_link;

    @OneToMany(mappedBy = "course", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<Feedback> feedbacks;
    
    @OneToMany(mappedBy = "course")
    @JsonIgnore
    private List<Questions> questions;
    
    @CreatedDate
    @Column(name = "created_at", nullable = true, updatable = false)
    private LocalDateTime createdAt;

    @LastModifiedDate
    @Column(name = "updated_at", nullable = true)
    private LocalDateTime updatedAt;

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
        updatedAt = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }
}
