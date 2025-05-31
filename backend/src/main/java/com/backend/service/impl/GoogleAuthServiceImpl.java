package com.backend.service.impl;

import com.backend.persistence.entity.UserEntity;
import com.backend.persistence.inputDTO.GoogleLoginDTO;
import com.backend.repository.UserRepository;
import com.backend.service.GoogleAuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class GoogleAuthServiceImpl implements GoogleAuthService {

    private final UserRepository userRepository;

    @Override
    public ResponseEntity<?> authenticateWithGoogle(GoogleLoginDTO googleLoginDTO) {
        if (googleLoginDTO.getEmail() == null || googleLoginDTO.getUsername() == null || googleLoginDTO.getAvatarUrl() == null) {
            return ResponseEntity.badRequest().body("Faltan campos en los datos de Google");
        }
        try{
            Optional<UserEntity> optionalUser = userRepository.findByEmail(googleLoginDTO.getEmail());

            UserEntity user = optionalUser.orElseGet(() -> {
                UserEntity newUser = new UserEntity();
                newUser.setName(googleLoginDTO.getUsername());
                newUser.setUsername(googleLoginDTO.getEmail().substring(0, googleLoginDTO.getEmail().indexOf("@")));
                newUser.setEmail(googleLoginDTO.getEmail());
                newUser.setPassword("");
                newUser.setAvatarUrl(googleLoginDTO.getAvatarUrl());
                newUser.setRole("USER");
                newUser.setCreatedAt(new Date());
                newUser.setWallet(1000.0);

                return userRepository.save(newUser);
            });

            return ResponseEntity.ok(user);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("Error interno al autenticar con Google");
        }
    }
}
