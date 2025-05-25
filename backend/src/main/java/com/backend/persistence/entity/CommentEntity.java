package com.backend.persistence.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.*;

import com.fasterxml.jackson.annotation.JsonManagedReference;

@Entity
@Table(name = "comments")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CommentEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "post_id", nullable = false)
    @JsonBackReference(value = "post-comment")
    private PostEntity post;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    @JsonBackReference(value = "user-comment")
    private UserEntity user;

    private String content;
    private int votes;

    @Column(name = "created_at")
    private Date createdAt;


    //this is for commentVotes
    public void increaseVotes() {
        this.votes++;
    }

    public void decreaseVotes() {
        this.votes--;
    }
}