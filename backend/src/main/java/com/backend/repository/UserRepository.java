package com.backend.repository;

import com.backend.persistence.entity.UserEntity;
import com.backend.persistence.inputDTO.UserInputDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.util.Optional;

import java.util.*;
import org.springframework.data.domain.Pageable;

public interface UserRepository extends JpaRepository<UserEntity, Long> {

    @Query("SELECT new com.backend.persistence.inputDTO.UserInputDTO(u.username,u.email,u.password, u.about, u.avatarUrl) " +
            "FROM UserEntity u " +
            "WHERE u.id = :userId")
    UserInputDTO findInputUserById(@Param("userId") Long id);
    Optional<UserEntity> findByEmail(String email);


    //this is for top users
    @Query("""
        SELECT 
        u.id,
        u.username,
        COUNT(DISTINCT p.id) + COUNT(DISTINCT c.id) AS total
        FROM UserEntity u
        LEFT JOIN u.posts p
        LEFT JOIN u.comments c
        GROUP BY u.id, u.username
        ORDER BY total DESC
    """)
    List<Object[]> findTopUsersByActivity(Pageable pageable);
}
