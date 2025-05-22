package com.backend.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PostMapping;

import com.backend.persistence.inputDTO.GoogleLoginDTO;

public interface GoogleAuthController {

    @PostMapping("/auth/google")
    ResponseEntity<?> loginWithGoogle(@RequestBody GoogleLoginDTO googleLoginDTO);
}
