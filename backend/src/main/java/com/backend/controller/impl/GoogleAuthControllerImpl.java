package com.backend.controller.impl;

import com.backend.controller.GoogleAuthController;
import com.backend.persistence.inputDTO.GoogleLoginDTO;
import com.backend.service.GoogleAuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class GoogleAuthControllerImpl implements GoogleAuthController {

    private final GoogleAuthService googleAuthService;

    @Override
    public ResponseEntity<?> loginWithGoogle(@RequestBody GoogleLoginDTO googleLoginDTO) {
        return googleAuthService.authenticateWithGoogle(googleLoginDTO);
    }
}
