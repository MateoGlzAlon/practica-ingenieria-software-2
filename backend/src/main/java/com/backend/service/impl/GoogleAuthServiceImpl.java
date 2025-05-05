package com.backend.service.impl;

import com.backend.persistence.entity.UserEntity;
import com.backend.persistence.inputDTO.GoogleLoginDTO;
import com.backend.persistence.inputDTO.UserInputDTO;
import com.backend.repository.UserRepository;
import com.backend.service.GoogleAuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class GoogleAuthServiceImpl implements GoogleAuthService {

    private final UserRepository userRepository;

    @Override
    public ResponseEntity<UserInputDTO> authenticateWithGoogle(GoogleLoginDTO googleLoginDTO) {
        Optional<UserEntity> optionalUser = userRepository.findByEmail(googleLoginDTO.getEmail());

        UserEntity user = optionalUser.orElseGet(() -> {
            UserEntity newUser = new UserEntity();
            newUser.setUsername(googleLoginDTO.getUsername());
            newUser.setEmail(googleLoginDTO.getEmail());
            newUser.setPassword(""); 
            newUser.setAvatarUrl(googleLoginDTO.getAvatarUrl());
            newUser.setCreatedAt(new Date());
            return userRepository.save(newUser);
        });
        UserInputDTO userInputDTO = UserInputDTO.builder()
            .username(user.getUsername())
            .email(user.getEmail())
            .password(user.getPassword())
            .about(user.getAbout())
            .avatarUrl(user.getAvatarUrl())
            .build();

        return ResponseEntity.ok(userInputDTO);
    }
}
