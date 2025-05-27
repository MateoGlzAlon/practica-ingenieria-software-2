package com.backend.persistence.inputDTO;

import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GoogleLoginDTO {
    private String username;
    public String name;
    private String email;
    private String avatarUrl;
}
