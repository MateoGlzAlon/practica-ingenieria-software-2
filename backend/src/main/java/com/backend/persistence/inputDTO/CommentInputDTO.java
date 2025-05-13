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
    private Long id;
    private int post_id;
    private String content;
    private int votes;
    

    
    //mockdata has status true/false
    //private boolean status;

}