package com.backend.service.impl;


import com.backend.configuration.JwtUtils;
import com.backend.persistence.entity.UserEntity;
import com.backend.persistence.inputDTO.CredentialDTO;
import com.backend.persistence.inputDTO.UserInputDTO;
import com.backend.persistence.outputdto.AuthResponse;
import com.backend.repository.UserRepository;
import com.backend.service.GoogleAuthService;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdTokenVerifier;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.json.jackson2.JacksonFactory;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.Collections;
import java.util.Date;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class GoogleAuthServiceImpl implements GoogleAuthService {

    private final UserRepository userRepository;
    private final JwtUtils jwtUtils;
    @Value("${GOOGLE_CLIENT_ID}")
    private String googleClientId;
    @Override
    public ResponseEntity<AuthResponse> authenticateWithGoogle(CredentialDTO credentialDTO) {
        String idToken = credentialDTO.getCredential();

        GoogleIdToken.Payload payload = validateGoogleToken(idToken);

        if (payload == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        String email = payload.getEmail();
        String username = (String) payload.get("name");
        String avatarUrl = (String) payload.get("picture");

        Optional<UserEntity> optionalUser = userRepository.findByEmail(email);

        UserEntity user = optionalUser.orElseGet(() -> {
            UserEntity newUser = new UserEntity();
            newUser.setUsername(username);
            newUser.setName(username);
            newUser.setEmail(email);
            newUser.setRole("USER");
            newUser.setPassword("");
            newUser.setAvatarUrl(avatarUrl);
            newUser.setCreatedAt(new Date());
            return userRepository.save(newUser);
        });
        String jwt = jwtUtils.generateToken(user.getEmail());

        UserInputDTO userInputDTO = UserInputDTO.builder()
                .username(user.getUsername())
                .email(user.getEmail())
                .password(user.getPassword())
                .about(user.getAbout())
                .avatarUrl(user.getAvatarUrl())
                .build();

        return ResponseEntity.ok(new AuthResponse(jwt, user));
    }

    private GoogleIdToken.Payload validateGoogleToken(String idTokenString) {
        try {
            GoogleIdTokenVerifier verifier = new GoogleIdTokenVerifier.Builder(
                    GoogleNetHttpTransport.newTrustedTransport(),
                    JacksonFactory.getDefaultInstance())
                    .setAudience(Collections.singletonList("513343928845-hs8muladv0dlt2m84o7u1691isnjgafe.apps.googleusercontent.com")) 
                    .build();

            GoogleIdToken idToken = verifier.verify(idTokenString);
            if (idToken != null) {
                return idToken.getPayload();
            } else {
                System.out.println("Invalid ID token.");
                return null;
            }
        } catch (GeneralSecurityException | IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}
