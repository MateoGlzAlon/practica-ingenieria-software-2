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
    private int votes;
    private String authorUsername;
    private int commentCount;
    private int votes;
    private Date createdAt;
    private String content;

    private boolean voted;

}
