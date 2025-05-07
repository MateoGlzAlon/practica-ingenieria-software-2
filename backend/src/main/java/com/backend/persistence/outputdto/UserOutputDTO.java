package com.backend.persistence.outputdto;

import jakarta.persistence.*;
import lombok.*;
import java.util.Date;
import java.util.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserOutputDTO {

    private Long id;
    private String username;
    private String email;
    private String about;
    private String avatarUrl;
    private Date createdAt;

    //private List<PostEntity> posts = new ArrayList<>();
    //private List<CommentEntity> comments = new ArrayList<>();

}










