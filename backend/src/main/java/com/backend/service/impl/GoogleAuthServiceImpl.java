package com.backend.service.impl;

import com.backend.persistence.entity.UserEntity;
import com.backend.persistence.inputDTO.GoogleLoginDTO;
import com.backend.repository.UserRepository;
import com.backend.service.GoogleAuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class GoogleAuthServiceImpl implements GoogleAuthService {

    private final UserRepository userRepository;

    @Override
    public ResponseEntity<?> authenticateWithGoogle(GoogleLoginDTO googleLoginDTO) {
        Optional<UserEntity> optionalUser = userRepository.findByEmail(googleLoginDTO.getEmail());

        UserEntity user = optionalUser.orElseGet(() -> {
            UserEntity newUser = new UserEntity();
            newUser.setUsername(googleLoginDTO.getUsername());
            newUser.setEmail(googleLoginDTO.getEmail());
            newUser.setPassword(""); // puedes dejarlo vacío o poner un valor dummy
            newUser.setAvatarUrl(googleLoginDTO.getAvatarUrl());
            newUser.setCreatedAt(LocalDateTime.now());
            return userRepository.save(newUser);
        });

        return ResponseEntity.ok(user);
    }
}
