package com.backend.persistence.specialdto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Builder
@Data
@AllArgsConstructor
public class PostDetailsDTO {

    private Long id;
    private String author;
    private Long authorId;
    private List<String> postImages;
    private String title;
    private String content;
    private int votes;
    private Date date;
    private String state;
}
