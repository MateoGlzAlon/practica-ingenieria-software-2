package com.backend.persistence.inputDTO;

import jakarta.persistence.*;
import lombok.*;
import java.util.Date;
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










