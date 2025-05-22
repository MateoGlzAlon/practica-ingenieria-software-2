package com.backend.persistence.inputDTO;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;
import java.util.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserInputDTO {

    private String username;
    private String email;

    private String password;

    private String about;

    private String avatarUrl;

}










