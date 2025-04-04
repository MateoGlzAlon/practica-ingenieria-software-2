package com.backend.persistence.outputdto;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;
import java.util.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserOutputDTO {
    private Long id;

    private String username;

    private String email;

    private String password;

    private String about;

    private String avatarUrl;

    private LocalDateTime createdAt = LocalDateTime.now();

    //private List<PostEntity> posts = new ArrayList<>();

    //private List<CommentEntity> comments = new ArrayList<>();
}










