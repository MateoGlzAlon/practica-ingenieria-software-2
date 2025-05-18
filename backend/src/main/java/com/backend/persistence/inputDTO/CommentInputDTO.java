package com.backend.persistence.inputDTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CommentInputDTO {

    //endpoint /comments/post/{id}
    //also used in the creation of comments
    private Long id;
    private Long postId;
    private Long userId;
    private String content;
    private int votes;
   
}