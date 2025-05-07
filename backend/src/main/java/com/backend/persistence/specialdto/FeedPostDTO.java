package com.backend.persistence.specialdto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FeedPostDTO {

    private Long id;
    private String title;
    private String imageURL;
    private String state;
    private String authorUsername;
    private int commentCount;
    private int likes;
    private Date createdAt;

}
