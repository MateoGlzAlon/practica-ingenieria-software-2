package com.backend.persistence.outputdto;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;
import java.util.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ActivityOutputDTO {

    private String month;
    private int contributions;

}