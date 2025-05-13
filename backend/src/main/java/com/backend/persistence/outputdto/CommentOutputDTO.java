package com.backend.persistence.outputdto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


import java.util.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CommentOutputDTO {

    private Long id;
    private String author;
    private String authorProfilePicture;
    private Date createdAt;
    private String content;
    private int votes;
    private int commentCount;

    private boolean accepted; //??
    
    

}