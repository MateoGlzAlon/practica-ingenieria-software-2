package com.backend.service;

import com.backend.persistence.entity.UserEntity;

public interface UserService {

    UserEntity findUserById(Long id);

}
