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

    private String title;
    private String summary;
    private String content;
    private Long tagId;

    private Long userId;
    private String state;

    //optional value
    private List<String> imageLinks;
    private List<String> comments;

}