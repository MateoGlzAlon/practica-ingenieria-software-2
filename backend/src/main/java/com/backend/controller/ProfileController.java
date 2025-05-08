package com.backend.controller;

import com.backend.persistence.entity.UserEntity;
import com.backend.persistence.specialdto.ProfileDTO;
import com.backend.persistence.specialdto.ProfileDTO;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/profile")
public interface ProfileController {

    @GetMapping("/{id}")
    ProfileDTO getProfileByUserId(@PathVariable Long id);

}