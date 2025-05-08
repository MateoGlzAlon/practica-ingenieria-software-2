package com.backend.persistence.outputdto;

import com.backend.persistence.entity.TipEntity;
import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;
import java.util.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserOutputDTO {

    private Long id;
    private String name;
    private String username;
    private String email;
    private String about;
    private String avatarUrl;
    private Date memberSince; //createdAt

    private List<TipEntity> tipsSent;
    private List<TipEntity> tipsReceived;
   
    //todo: links/stats/name
    private List<String> links;
    private List<Integer> stats;
    
    
}










