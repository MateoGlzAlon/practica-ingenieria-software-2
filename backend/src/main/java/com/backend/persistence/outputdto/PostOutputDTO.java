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
    private int votes;
    private Long answers;
    private Date date;

}