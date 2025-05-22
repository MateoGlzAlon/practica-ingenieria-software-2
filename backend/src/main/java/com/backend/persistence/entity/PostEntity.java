package com.backend.persistence.entity;

import java.util.Date;
import java.util.*;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "posts")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PostEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    @JsonBackReference(value = "user-post")
    private UserEntity user;

    private String title;
    private String content;
    private int votes;
    private String state;

    @Column(name = "created_at")
    private Date createdAt;

    @OneToMany(mappedBy = "post", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference(value = "post-image")
    private List<PostImageEntity> images;

    @OneToMany(mappedBy = "post", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference(value = "post-comments")
    private List<CommentEntity> comments;

    @ManyToOne
    @JoinColumn(name = "tag_id")
    private TagEntity tag;

    @Override
    public String toString() {
        return "PostEntity{id=" + id + ", title='" + title + "'}";
    }


    public void increaseVotes() {
        this.votes++;
    }

    public void decreaseVotes() {
        this.votes--;
    }

}


