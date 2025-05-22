package com.backend.controller.impl;

import com.backend.controller.GoogleAuthController;
import com.backend.persistence.inputDTO.CredentialDTO;
import com.backend.persistence.inputDTO.GoogleLoginDTO;
import com.backend.persistence.inputDTO.UserInputDTO;
import com.backend.persistence.outputdto.AuthResponse;
import com.backend.service.GoogleAuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
public class GoogleAuthControllerImpl implements GoogleAuthController {

    private final GoogleAuthService googleAuthService;

    @PostMapping("/google")
    @Override
    public ResponseEntity<AuthResponse> loginWithGoogle(@RequestBody CredentialDTO googleLoginDTO) {
        return googleAuthService.authenticateWithGoogle(googleLoginDTO);
    }
}
