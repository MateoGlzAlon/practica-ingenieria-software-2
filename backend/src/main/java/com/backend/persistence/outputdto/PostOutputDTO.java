package com.backend.persistence.outputdto;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;
import java.util.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PostOutputDTO {

    private Long id;
    private String title;
    private String content;
    private int likes;
    private Long answers;
    private Date date;
    private List<String> imageLinks;
    private String tag;

}