package com.backend.persistence.outputdto;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;
import java.util.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PostOutputDTO {

    private Long id;
    private String title;
    private int votes;
    private Long answers;
    private Date date;




    public PostOutputDTO(Long id,
                         String title,
                         int votes,
                         long answers,
                         Date date) {
        this.id      = id;
        this.title   = title;
        this.votes   = votes;
        this.answers = answers;
        this.date    = date;
    }

}