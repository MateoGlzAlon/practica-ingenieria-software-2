package com.backend.repository;

import com.backend.persistence.entity.UserEntity;
import com.backend.persistence.outputdto.UserOutputDTO;
import com.backend.persistence.inputDTO.UserInputDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.util.Optional;


public interface UserRepository extends JpaRepository<UserEntity, Long> {

    @Query("SELECT new com.backend.persistence.inputDTO.UserInputDTO(u.username,u.email,u.password, u.about, u.avatarUrl) " +
            "FROM UserEntity u " +
            "WHERE u.id = :userId")
    UserInputDTO findInputUserById(@Param("userId") Long id);
    Optional<UserEntity> findByEmail(String email);

    @Query("FROM UserEntity u " +
            "WHERE u.id = :userId")
    UserEntity findUserById(@Param("userId") Long id);
}
