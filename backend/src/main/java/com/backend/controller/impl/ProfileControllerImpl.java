package com.backend.controller.impl;

import com.backend.controller.ProfileController;
import com.backend.persistence.specialdto.ProfileDTO;
import com.backend.service.ProfileService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/profile")
@AllArgsConstructor
public class ProfileControllerImpl implements ProfileController {

    private final ProfileService profileService;

    @Override
    @GetMapping("/{id}")
    public ProfileDTO getProfileByUserId(@PathVariable Long id) {
        return profileService.getProfileByUserId(id);
    }

}