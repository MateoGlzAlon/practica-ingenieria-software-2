package com.backend.service;

import com.backend.persistence.inputDTO.CredentialDTO;
import com.backend.persistence.inputDTO.GoogleLoginDTO;
import com.backend.persistence.inputDTO.UserInputDTO;

import org.springframework.http.ResponseEntity;

public interface GoogleAuthService {
    ResponseEntity<UserInputDTO> authenticateWithGoogle(CredentialDTO googleLoginDTO);
}
