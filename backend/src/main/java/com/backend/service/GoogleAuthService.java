package com.backend.service;

import com.backend.persistence.inputDTO.CredentialDTO;
import com.backend.persistence.inputDTO.GoogleLoginDTO;
import com.backend.persistence.inputDTO.UserInputDTO;
import com.backend.persistence.outputdto.AuthResponse;

import org.springframework.http.ResponseEntity;

public interface GoogleAuthService {
    ResponseEntity<AuthResponse> authenticateWithGoogle(CredentialDTO googleLoginDTO);
}
