package com.backend.persistence.inputDTO;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;
import java.util.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserLinksInputDTO {

    private Long userId;
    private String github_link;
    private String website_link;
    private String twitter_link;

}