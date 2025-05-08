package com.backend.persistence.outputdto;

import com.backend.persistence.entity.TipEntity;
import com.backend.persistence.outputdto.TipReceivedOutputDTO;
import com.backend.persistence.outputdto.TipSentOutputDTO;
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
    private String role;
    private String email;
    private String about;
    private String avatarUrl;
    private String memberSince; //createdAt

    private List<TipSentOutputDTO> tipsSent;
    private List<TipReceivedOutputDTO> tipsReceived;
   
    private Map<String,String> links;
    private Map<String,Integer> stats;
    
    
}










