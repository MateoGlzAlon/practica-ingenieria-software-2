package com.backend.persistence.outputdto;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;
import java.util.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TipReceivedOutputDTO {

    private String sender;
    private int amount;
    private Date date;
    private Long postId;
    private Long commentId;

}