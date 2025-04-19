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
public class PostInputDTO {

    //endpoint /posts/focus/{id}
    private Long id;
    private String title;
    private String content;
    private int likes;

}