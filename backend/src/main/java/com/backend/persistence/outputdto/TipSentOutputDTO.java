package com.backend.persistence.outputdto;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;
import java.util.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TipSentOutputDTO {

    private String receiver;
    private int amount;
    private String date;
    private Long postId;
    private Long commentId;

}