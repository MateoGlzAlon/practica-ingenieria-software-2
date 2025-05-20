package com.backend.persistence.entity;

import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.*;

import lombok.*;

@Entity
@Table(name = "post_votes")
@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PostVoteEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "post_id", nullable = false)
    private PostEntity post;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private UserEntity user;

}