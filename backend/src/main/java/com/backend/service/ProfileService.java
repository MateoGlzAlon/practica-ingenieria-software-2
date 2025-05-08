package com.backend.service;

import com.backend.persistence.entity.UserEntity;
import com.backend.persistence.specialdto.ProfileDTO;

public interface ProfileService {

    ProfileDTO getProfileByUserId(Long id);

}