package com.backend.service;

import com.backend.persistence.inputDTO.GoogleLoginDTO;
import org.springframework.http.ResponseEntity;

public interface GoogleAuthService {
    ResponseEntity<?> authenticateWithGoogle(GoogleLoginDTO googleLoginDTO);
}
