package com.backend.persistence.specialdto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.sql.Timestamp;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FeedPostDTO {

    private Long id;
    private String title;
    
    //added for landingpage
    private String content;
    private String author;
    private String createdAt;
    private int comments;

    private String imageURL;
    private String state;
    private int likes;

}
