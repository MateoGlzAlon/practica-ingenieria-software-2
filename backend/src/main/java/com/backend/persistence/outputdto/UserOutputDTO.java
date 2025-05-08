package com.backend.persistence.outputdto;

import com.backend.persistence.entity.TipEntity;
import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;
import java.util.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserOutputDTO {

    //private Long id;
    private String username;
    private String email;
    private String about;
    private String avatarUrl;
    private Date memberSince; //createdAt

    private List<TipEntity> tipsSent;
    private List<TipEntity> tipsReceived;

    //todo: links/stats/name
    


    public UserOutputDTO(String username,
                         String email,
                         String about,
                         String avatarUrl,
                         Date memberSince) {
        this.username     = username;
        this.email        = email;
        this.about        = about;
        this.avatarUrl    = avatarUrl;
        this.memberSince  = memberSince;
    }


    
}










