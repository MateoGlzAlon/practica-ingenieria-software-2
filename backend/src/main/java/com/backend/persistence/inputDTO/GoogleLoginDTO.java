package com.backend.persistence.inputDTO;

import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GoogleLoginDTO {
    private String username;
    private String email;
    private String avatarUrl;
}
