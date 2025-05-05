package com.backend.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.PostMapping;

import com.backend.persistence.inputDTO.GoogleLoginDTO;
import com.backend.persistence.inputDTO.UserInputDTO;

public interface GoogleAuthController {

    @RequestMapping("/auth/google")
    ResponseEntity<UserInputDTO> loginWithGoogle(@RequestBody GoogleLoginDTO googleLoginDTO);
}
