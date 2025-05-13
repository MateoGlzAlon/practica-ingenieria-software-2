package com.backend.persistence.outputdto;
import com.backend.persistence.entity.UserEntity;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class AuthResponse {
    private String token;
    private UserEntity user;
}