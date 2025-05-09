package com.backend.persistence.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.*;

@Entity
@Table(name = "post_images")
@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PostImageEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "post_id", nullable = false)
    @JsonBackReference(value = "post-image")
    private PostEntity post;

    private String imageUrl;

    @Column(name = "created_at")
    private Date createdAt;
}